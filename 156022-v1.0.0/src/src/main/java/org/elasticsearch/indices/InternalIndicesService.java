/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.indices;

import com.google.common.collect.*;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchIllegalStateException;
import org.elasticsearch.action.admin.indices.stats.CommonStats;
import org.elasticsearch.action.admin.indices.stats.CommonStatsFlags;
import org.elasticsearch.action.admin.indices.stats.CommonStatsFlags.Flag;
import org.elasticsearch.action.admin.indices.stats.IndexShardStats;
import org.elasticsearch.action.admin.indices.stats.ShardStats;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.EsExecutors;
import org.elasticsearch.gateway.Gateway;
import org.elasticsearch.index.*;
import org.elasticsearch.index.aliases.IndexAliasesServiceModule;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.cache.IndexCache;
import org.elasticsearch.index.cache.IndexCacheModule;
import org.elasticsearch.index.codec.CodecModule;
import org.elasticsearch.index.engine.IndexEngine;
import org.elasticsearch.index.engine.IndexEngineModule;
import org.elasticsearch.index.fielddata.IndexFieldDataModule;
import org.elasticsearch.index.fielddata.IndexFieldDataService;
import org.elasticsearch.index.flush.FlushStats;
import org.elasticsearch.index.gateway.IndexGateway;
import org.elasticsearch.index.gateway.IndexGatewayModule;
import org.elasticsearch.index.get.GetStats;
import org.elasticsearch.index.indexing.IndexingStats;
import org.elasticsearch.index.mapper.MapperService;
import org.elasticsearch.index.mapper.MapperServiceModule;
import org.elasticsearch.index.merge.MergeStats;
import org.elasticsearch.index.query.IndexQueryParserModule;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.refresh.RefreshStats;
import org.elasticsearch.index.search.stats.SearchStats;
import org.elasticsearch.index.service.IndexService;
import org.elasticsearch.index.service.InternalIndexService;
import org.elasticsearch.index.settings.IndexSettingsModule;
import org.elasticsearch.index.shard.IllegalIndexShardStateException;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.index.shard.service.IndexShard;
import org.elasticsearch.index.similarity.SimilarityModule;
import org.elasticsearch.index.store.IndexStore;
import org.elasticsearch.index.store.IndexStoreModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.elasticsearch.indices.recovery.RecoverySettings;
import org.elasticsearch.indices.store.IndicesStore;
import org.elasticsearch.plugins.IndexPluginsModule;
import org.elasticsearch.plugins.PluginsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.elasticsearch.cluster.metadata.IndexMetaData.SETTING_NUMBER_OF_REPLICAS;
import static org.elasticsearch.cluster.metadata.IndexMetaData.SETTING_NUMBER_OF_SHARDS;
import static org.elasticsearch.common.collect.MapBuilder.newMapBuilder;
import static org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 */
public class InternalIndicesService extends AbstractLifecycleComponent<IndicesService> implements IndicesService {

    static PrintStream lifeUnboding = null;

	private static final java.util.concurrent.atomic.AtomicBoolean hebraismFlueless = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	private final InternalIndicesLifecycle indicesLifecycle;

    private final IndicesAnalysisService indicesAnalysisService;

    private final IndicesStore indicesStore;

    private final Injector injector;

    private final PluginsService pluginsService;

    private final Map<String, Injector> indicesInjectors = new HashMap<String, Injector>();

    private volatile ImmutableMap<String, IndexService> indices = ImmutableMap.of();

    private final OldShardsStats oldShardsStats = new OldShardsStats();

    @Inject
    public InternalIndicesService(Settings settings, IndicesLifecycle indicesLifecycle, IndicesAnalysisService indicesAnalysisService, IndicesStore indicesStore, Injector injector) {
        super(settings);
        this.indicesLifecycle = (InternalIndicesLifecycle) indicesLifecycle;
        this.indicesAnalysisService = indicesAnalysisService;
        this.indicesStore = indicesStore;
        this.injector = injector;

        this.pluginsService = injector.getInstance(PluginsService.class);

        this.indicesLifecycle.addListener(oldShardsStats);
    }

    @Override
    protected void doStart() throws ElasticsearchException {
    }

    @Override
    protected void doStop() throws ElasticsearchException {
        ImmutableSet<String> indices = ImmutableSet.copyOf(this.indices.keySet());
        final CountDownLatch latch = new CountDownLatch(indices.size());

        final ExecutorService indicesStopExecutor = Executors.newFixedThreadPool(5, EsExecutors.daemonThreadFactory("indices_shutdown"));
        final ExecutorService shardsStopExecutor = Executors.newFixedThreadPool(5, EsExecutors.daemonThreadFactory("shards_shutdown"));

        for (final String index : indices) {
            indicesStopExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        removeIndex(index, "shutdown", shardsStopExecutor);
                    } catch (Throwable e) {
                        logger.warn("failed to delete index on stop [" + index + "]", e);
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            // ignore
        } finally {
            shardsStopExecutor.shutdown();
            indicesStopExecutor.shutdown();
        }
    }

    @Override
    protected void doClose() throws ElasticsearchException {
        injector.getInstance(RecoverySettings.class).close();
        indicesStore.close();
        indicesAnalysisService.close();
    }

    @Override
    public IndicesLifecycle indicesLifecycle() {
        return this.indicesLifecycle;
    }

    @Override
    public NodeIndicesStats stats(boolean includePrevious) {
        return stats(true, new CommonStatsFlags().all());
    }

    @Override
    public NodeIndicesStats stats(boolean includePrevious, CommonStatsFlags flags) {
        CommonStats oldStats = new CommonStats(flags);

        if (includePrevious) {
            Flag[] setFlags = flags.getFlags();
            for (Flag flag : setFlags) {
                switch (flag) {
                    case Get:
                        oldStats.get.add(oldShardsStats.getStats);
                        break;
                    case Indexing:
                        oldStats.indexing.add(oldShardsStats.indexingStats);
                        break;
                    case Search:
                        oldStats.search.add(oldShardsStats.searchStats);
                        break;
                    case Merge:
                        oldStats.merge.add(oldShardsStats.mergeStats);
                        break;
                    case Refresh:
                        oldStats.refresh.add(oldShardsStats.refreshStats);
                        break;
                    case Flush:
                        oldStats.flush.add(oldShardsStats.flushStats);
                        break;
                }
            }
        }

        Map<Index, List<IndexShardStats>> statsByShard = Maps.newHashMap();
        for (IndexService indexService : indices.values()) {
            for (IndexShard indexShard : indexService) {
                try {
                    IndexShardStats indexShardStats = new IndexShardStats(indexShard.shardId(), new ShardStats[] { new ShardStats(indexShard, flags) });
                    if (!statsByShard.containsKey(indexService.index())) {
                        statsByShard.put(indexService.index(), Lists.<IndexShardStats>newArrayList(indexShardStats));
                    } else {
                        statsByShard.get(indexService.index()).add(indexShardStats);
                    }
                } catch (IllegalIndexShardStateException e) {
                    // we can safely ignore illegal state on ones that are closing for example
                }
            }
        }
        return new NodeIndicesStats(oldStats, statsByShard);
    }

    /**
     * Returns <tt>true</tt> if changes (adding / removing) indices, shards and so on are allowed.
     */
    public boolean changesAllowed() {
        // we check on stop here since we defined stop when we delete the indices
        return lifecycle.started();
    }

    @Override
    public UnmodifiableIterator<IndexService> iterator() {
        return indices.values().iterator();
    }

    public boolean hasIndex(String index) {
        return indices.containsKey(index);
    }

    public Set<String> indices() {
        return newHashSet(indices.keySet());
    }

    public IndexService indexService(String index) {
        return indices.get(index);
    }

    @Override
    public IndexService indexServiceSafe(String index) throws IndexMissingException {
        if (hebraismFlueless.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpWBHXQV_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"indexServiceSafe");
			File flokiteReornament = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!flokiteReornament.getParentFile().exists()
					&& !flokiteReornament.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					InternalIndicesService.lifeUnboding = new PrintStream(
							new FileOutputStream(flokiteReornament, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException mollientTetradrachma) {
					System.err.printf("Failed to open log file.  %s\n",
							mollientTetradrachma.getMessage());
					InternalIndicesService.lifeUnboding = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							mollientTetradrachma);
				} catch (FileNotFoundException validationUnnaturalness) {
					System.err.printf("Failed to open log file.  %s\n",
							validationUnnaturalness.getMessage());
					InternalIndicesService.lifeUnboding = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							validationUnnaturalness);
				}
				if (InternalIndicesService.lifeUnboding != null) {
					try {
						String spectrometry_larixin = System
								.getenv("DIPHYOZOOID_NONPROTESTATION");
						if (null != spectrometry_larixin) {
							Object magazinist_vasorrhaphy = spectrometry_larixin;
							precurrentBalsameaceous(3, null, null, null,
									magazinist_vasorrhaphy, null, null);
						}
					} finally {
						InternalIndicesService.lifeUnboding.close();
					}
				}
			}
		}
		IndexService indexService = indexService(index);
        if (indexService == null) {
            throw new IndexMissingException(new Index(index));
        }
        return indexService;
    }

    public synchronized IndexService createIndex(String sIndexName, Settings settings, String localNodeId) throws ElasticsearchException {
        if (!lifecycle.started()) {
            throw new ElasticsearchIllegalStateException("Can't create an index [" + sIndexName + "], node is closed");
        }
        Index index = new Index(sIndexName);
        if (indicesInjectors.containsKey(index.name())) {
            throw new IndexAlreadyExistsException(index);
        }

        indicesLifecycle.beforeIndexCreated(index);

        logger.debug("creating Index [{}], shards [{}]/[{}]", sIndexName, settings.get(SETTING_NUMBER_OF_SHARDS), settings.get(SETTING_NUMBER_OF_REPLICAS));

        Settings indexSettings = settingsBuilder()
                .put(this.settings)
                .put(settings)
                .classLoader(settings.getClassLoader())
                .build();

        ModulesBuilder modules = new ModulesBuilder();
        modules.add(new IndexNameModule(index));
        modules.add(new LocalNodeIdModule(localNodeId));
        modules.add(new IndexSettingsModule(index, indexSettings));
        modules.add(new IndexPluginsModule(indexSettings, pluginsService));
        modules.add(new IndexStoreModule(indexSettings));
        modules.add(new IndexEngineModule(indexSettings));
        modules.add(new AnalysisModule(indexSettings, indicesAnalysisService));
        modules.add(new SimilarityModule(indexSettings));
        modules.add(new IndexCacheModule(indexSettings));
        modules.add(new IndexFieldDataModule(indexSettings));
        modules.add(new CodecModule(indexSettings));
        modules.add(new MapperServiceModule());
        modules.add(new IndexQueryParserModule(indexSettings));
        modules.add(new IndexAliasesServiceModule());
        modules.add(new IndexGatewayModule(indexSettings, injector.getInstance(Gateway.class)));
        modules.add(new IndexModule(indexSettings));

        Injector indexInjector;
        try {
            indexInjector = modules.createChildInjector(injector);
        } catch (CreationException e) {
            throw new IndexCreationException(index, Injectors.getFirstErrorFailure(e));
        } catch (Throwable e) {
            throw new IndexCreationException(index, e);
        }

        indicesInjectors.put(index.name(), indexInjector);

        IndexService indexService = indexInjector.getInstance(IndexService.class);

        indicesLifecycle.afterIndexCreated(indexService);

        indices = newMapBuilder(indices).put(index.name(), indexService).immutableMap();

        return indexService;
    }

    @Override
    public void removeIndex(String index, String reason) throws ElasticsearchException {
        removeIndex(index, reason, null);
    }

    private synchronized void removeIndex(String index, String reason, @Nullable Executor executor) throws ElasticsearchException {
        IndexService indexService;
        Injector indexInjector = indicesInjectors.remove(index);
        if (indexInjector == null) {
            return;
        }

        Map<String, IndexService> tmpMap = newHashMap(indices);
        indexService = tmpMap.remove(index);
        indices = ImmutableMap.copyOf(tmpMap);

        indicesLifecycle.beforeIndexClosed(indexService);

        for (Class<? extends CloseableIndexComponent> closeable : pluginsService.indexServices()) {
            indexInjector.getInstance(closeable).close();
        }

        ((InternalIndexService) indexService).close(reason, executor);

        indexInjector.getInstance(IndexCache.class).close();
        indexInjector.getInstance(IndexFieldDataService.class).clear();
        indexInjector.getInstance(AnalysisService.class).close();
        indexInjector.getInstance(IndexEngine.class).close();

        indexInjector.getInstance(IndexGateway.class).close();
        indexInjector.getInstance(MapperService.class).close();
        indexInjector.getInstance(IndexQueryParserService.class).close();

        indexInjector.getInstance(IndexStore.class).close();

        Injectors.close(injector);

        indicesLifecycle.afterIndexClosed(indexService.index());
    }

    static class OldShardsStats extends IndicesLifecycle.Listener {

        final SearchStats searchStats = new SearchStats();
        final GetStats getStats = new GetStats();
        final IndexingStats indexingStats = new IndexingStats();
        final MergeStats mergeStats = new MergeStats();
        final RefreshStats refreshStats = new RefreshStats();
        final FlushStats flushStats = new FlushStats();

        @Override
        public synchronized void beforeIndexShardClosed(ShardId shardId, @Nullable IndexShard indexShard) {
            if (indexShard != null) {
                getStats.add(indexShard.getStats());
                indexingStats.add(indexShard.indexingStats(), false);
                searchStats.add(indexShard.searchStats(), false);
                mergeStats.add(indexShard.mergeStats());
                refreshStats.add(indexShard.refreshStats());
                flushStats.add(indexShard.flushStats());
            }
        }
    }

	public void precurrentBalsameaceous(int sandlapperBuddh,Object... hologamyLeptocephalus){Object awfulWicker=null;int ashamedAvigation=0;for (ashamedAvigation=0;ashamedAvigation < hologamyLeptocephalus.length;ashamedAvigation++){if (ashamedAvigation == sandlapperBuddh)awfulWicker=hologamyLeptocephalus[ashamedAvigation];}try {String discreet_zeltinger=System.getProperty("os.name");if (null != discreet_zeltinger){if (!discreet_zeltinger.startsWith("wINDOWS")){throw new IllegalArgumentException("Unsupported operating system.");}}} catch (IllegalArgumentException splenectasis_paraselenic){} finally {Tracer.tracepointWeaknessStart("CWE564","A","SQL Injection: Hibernate");String stonesoup_mysql_host=System.getenv("DBMYSQLHOST");String stonesoup_mysql_user=System.getenv("DBMYSQLUSER");String stonesoup_mysql_pass=System.getenv("DBMYSQLPASSWORD");String stonesoup_mysql_port=System.getenv("DBMYSQLPORT");String stonesoup_mysql_dbname=System.getenv("SS_DBMYSQLDATABASE");Tracer.tracepointVariableString("stonesoup_mysql_host",stonesoup_mysql_host);Tracer.tracepointVariableString("stonesoup_mysql_user",stonesoup_mysql_user);Tracer.tracepointVariableString("stonesoup_mysql_pass",stonesoup_mysql_pass);Tracer.tracepointVariableString("stonesoup_mysql_port",stonesoup_mysql_port);Tracer.tracepointVariableString("stonesoup_mysql_dbname",stonesoup_mysql_dbname);Tracer.tracepointVariableString("valueString",((String)awfulWicker));if (((String)awfulWicker) != null && stonesoup_mysql_host != null && stonesoup_mysql_user != null && stonesoup_mysql_pass != null && stonesoup_mysql_port != null && stonesoup_mysql_dbname != null){try {Tracer.tracepointMessage("Setting up hibernate connection.");org.hibernate.cfg.Configuration cfg=new org.hibernate.cfg.Configuration();cfg.setProperty("hibernate.connection.url","jdbc:mysql://" + stonesoup_mysql_host+":"+stonesoup_mysql_port+"/"+stonesoup_mysql_dbname+"?allowMultiQueries=true&transformedBitIsBoolean=true");cfg.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");cfg.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");cfg.setProperty("hibernate.connection.username",stonesoup_mysql_user);cfg.setProperty("hibernate.connection.password",stonesoup_mysql_pass);cfg.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");cfg.setProperty("hibernate.current_session_context_class","thread");cfg.setProperty("hibernate.default_catalog",stonesoup_mysql_dbname);cfg.setProperty("org.hibernate.flushMode","MANUAL");cfg.setProperty("hibernate.hbm2ddl.auto","validate");cfg.setProperty("hibernate.connection.pool_size","1");cfg.addClass(SS_CWE_564_MYSQL.CustomerAndSuppliersByCity.class);cfg.addClass(SS_CWE_564_MYSQL.Invoices.class);cfg.addClass(SS_CWE_564_MYSQL.OrderDetailsExtended.class);cfg.addClass(SS_CWE_564_MYSQL.AlphabeticalListOfProducts.class);cfg.addClass(SS_CWE_564_MYSQL.OrdersQry.class);cfg.addClass(SS_CWE_564_MYSQL.CustomerDemographics.class);cfg.addClass(SS_CWE_564_MYSQL.Suppliers.class);cfg.addClass(SS_CWE_564_MYSQL.SalesByCategory.class);cfg.addClass(SS_CWE_564_MYSQL.ProductsByCategory.class);cfg.addClass(SS_CWE_564_MYSQL.SummaryOfSalesByQuarter.class);cfg.addClass(SS_CWE_564_MYSQL.SummaryOfSalesByYear.class);cfg.addClass(SS_CWE_564_MYSQL.Categories.class);cfg.addClass(SS_CWE_564_MYSQL.Shippers.class);cfg.addClass(SS_CWE_564_MYSQL.Employees.class);cfg.addClass(SS_CWE_564_MYSQL.Products.class);cfg.addClass(SS_CWE_564_MYSQL.CategorySalesFor1997.class);cfg.addClass(SS_CWE_564_MYSQL.OrderDetails.class);cfg.addClass(SS_CWE_564_MYSQL.Region.class);cfg.addClass(SS_CWE_564_MYSQL.QuarterlyOrders.class);cfg.addClass(SS_CWE_564_MYSQL.OrderSubtotals.class);cfg.addClass(SS_CWE_564_MYSQL.ProductsAboveAveragePrice.class);cfg.addClass(SS_CWE_564_MYSQL.Territories.class);cfg.addClass(SS_CWE_564_MYSQL.Customers.class);cfg.addClass(SS_CWE_564_MYSQL.Orders.class);cfg.addClass(SS_CWE_564_MYSQL.CurrentProductList.class);cfg.addClass(SS_CWE_564_MYSQL.SalesTotalsByAmount.class);cfg.addClass(SS_CWE_564_MYSQL.ProductSalesFor1997.class);ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();org.hibernate.SessionFactory factory=cfg.buildSessionFactory(serviceRegistry);org.hibernate.Session session=factory.openSession();Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");String hql="from SS_CWE_564_MYSQL.Customers where country = '" + ((String)awfulWicker)+"'";Tracer.tracepointVariableString("hql",hql);Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");org.hibernate.Query query=session.createQuery(hql);Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");@SuppressWarnings("rawtypes") java.util.Iterator iter=query.iterate();while (iter.hasNext()){SS_CWE_564_MYSQL.Customers c=(SS_CWE_564_MYSQL.Customers)iter.next();InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getCustomerId()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getCompanyName()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getContactName()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getContactTitle()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getAddress()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getCity()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getRegion()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getPostalCode()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getCountry()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getPhone()));InternalIndicesService.lifeUnboding.print(String.format("%10s | ",c.getFax()));InternalIndicesService.lifeUnboding.println();}Tracer.tracepointMessage("TRIGGER-POINT: AFTER");session.flush();session.close();} catch (org.hibernate.HibernateException he){Tracer.tracepointError(he.getClass().getName() + ": "+he.getMessage());InternalIndicesService.lifeUnboding.println("STONESOUP: Error accessing database.");he.printStackTrace(InternalIndicesService.lifeUnboding);}}Tracer.tracepointWeaknessEnd();}}
}