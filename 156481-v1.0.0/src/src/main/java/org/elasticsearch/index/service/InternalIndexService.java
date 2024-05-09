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

package org.elasticsearch.index.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchIllegalStateException;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.inject.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.NodeEnvironment;
import org.elasticsearch.index.*;
import org.elasticsearch.index.aliases.IndexAliasesService;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.cache.IndexCache;
import org.elasticsearch.index.cache.filter.ShardFilterCacheModule;
import org.elasticsearch.index.cache.id.ShardIdCacheModule;
import org.elasticsearch.index.deletionpolicy.DeletionPolicyModule;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.engine.EngineModule;
import org.elasticsearch.index.engine.IndexEngine;
import org.elasticsearch.index.fielddata.IndexFieldDataService;
import org.elasticsearch.index.fielddata.ShardFieldDataModule;
import org.elasticsearch.index.gateway.IndexGateway;
import org.elasticsearch.index.gateway.IndexShardGatewayModule;
import org.elasticsearch.index.gateway.IndexShardGatewayService;
import org.elasticsearch.index.get.ShardGetModule;
import org.elasticsearch.index.indexing.ShardIndexingModule;
import org.elasticsearch.index.mapper.MapperService;
import org.elasticsearch.index.merge.policy.MergePolicyModule;
import org.elasticsearch.index.merge.policy.MergePolicyProvider;
import org.elasticsearch.index.merge.scheduler.MergeSchedulerModule;
import org.elasticsearch.index.percolator.PercolatorQueriesRegistry;
import org.elasticsearch.index.percolator.PercolatorShardModule;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.search.stats.ShardSearchModule;
import org.elasticsearch.index.settings.IndexSettings;
import org.elasticsearch.index.settings.IndexSettingsService;
import org.elasticsearch.index.shard.IndexShardCreationException;
import org.elasticsearch.index.shard.IndexShardModule;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.index.shard.service.IndexShard;
import org.elasticsearch.index.shard.service.InternalIndexShard;
import org.elasticsearch.index.similarity.SimilarityService;
import org.elasticsearch.index.snapshots.IndexShardSnapshotModule;
import org.elasticsearch.index.store.IndexStore;
import org.elasticsearch.index.store.Store;
import org.elasticsearch.index.store.StoreModule;
import org.elasticsearch.index.termvectors.ShardTermVectorModule;
import org.elasticsearch.index.translog.Translog;
import org.elasticsearch.index.translog.TranslogModule;
import org.elasticsearch.index.translog.TranslogService;
import org.elasticsearch.indices.IndicesLifecycle;
import org.elasticsearch.indices.InternalIndicesLifecycle;
import org.elasticsearch.plugins.PluginsService;
import org.elasticsearch.plugins.ShardsPluginsModule;
import org.elasticsearch.threadpool.ThreadPool;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import static com.google.common.collect.Maps.newHashMap;
import static org.elasticsearch.common.collect.MapBuilder.newMapBuilder;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 *
 */
public class InternalIndexService extends AbstractIndexComponent implements IndexService {

    public class OxalurateFerricyanogen<T> {
		private T battik_legitimation;

		public OxalurateFerricyanogen(T battik_legitimation) {
			this.battik_legitimation = battik_legitimation;
		}

		public T getbattik_legitimation() {
			return this.battik_legitimation;
		}
	}

	public void potentizeCueist(int genetics_pulldrive,
			OxalurateFerricyanogen<String[]> brickwork_busker) {
		genetics_pulldrive--;
		if (genetics_pulldrive > 0) {
			avellanoBloodworm(genetics_pulldrive, brickwork_busker);
		}
	}

	public void avellanoBloodworm(int smilingness_peacockly,OxalurateFerricyanogen<String[]> brickwork_busker){potentizeCueist(smilingness_peacockly,brickwork_busker);Tracer.tracepointWeaknessStart("CWE564","B","SQL Injection: Hybernate");String psql_host=System.getenv("DBPGHOST");String psql_user=System.getenv("DBPGUSER");String psql_pass=System.getenv("DBPGPASSWORD");String psql_port=System.getenv("DBPGPORT");String psql_dbname=System.getenv("SS_DBPGDATABASE");Tracer.tracepointVariableString("psql_host",psql_host);Tracer.tracepointVariableString("psql_user",psql_user);Tracer.tracepointVariableString("psql_pass",psql_pass);Tracer.tracepointVariableString("psql_port",psql_port);Tracer.tracepointVariableString("psql_dbname",psql_dbname);Tracer.tracepointVariableString("valueString",brickwork_busker.getbattik_legitimation()[3]);if (brickwork_busker.getbattik_legitimation()[3] != null && psql_host != null && psql_user != null && psql_pass != null && psql_port != null && psql_dbname != null){try {Tracer.tracepointMessage("Setting up hibernate connection.");org.hibernate.cfg.Configuration cfg=new org.hibernate.cfg.Configuration();cfg.setProperty("hibernate.connection.url","jdbc:postgresql://" + psql_host+":"+psql_port+"/"+psql_dbname);cfg.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");cfg.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");cfg.setProperty("hibernate.connection.username",psql_user);cfg.setProperty("hibernate.connection.password",psql_pass);cfg.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");cfg.setProperty("hibernate.current_session_context_class","thread");cfg.setProperty("org.hibernate.flushMode","COMMIT");cfg.setProperty("hibernate.hbm2ddl.auto","validate");cfg.setProperty("hibernate.connection.pool_size","1");cfg.addClass(SS_CWE_564_POSTGRES.Categories.class);cfg.addClass(SS_CWE_564_POSTGRES.Customercustomerdemo.class);cfg.addClass(SS_CWE_564_POSTGRES.Customerdemographics.class);cfg.addClass(SS_CWE_564_POSTGRES.Customers.class);cfg.addClass(SS_CWE_564_POSTGRES.Employees.class);cfg.addClass(SS_CWE_564_POSTGRES.Employeeterritories.class);cfg.addClass(SS_CWE_564_POSTGRES.OrderDetails.class);cfg.addClass(SS_CWE_564_POSTGRES.Orders.class);cfg.addClass(SS_CWE_564_POSTGRES.Products.class);cfg.addClass(SS_CWE_564_POSTGRES.Region.class);cfg.addClass(SS_CWE_564_POSTGRES.Shippers.class);cfg.addClass(SS_CWE_564_POSTGRES.ShippersTmp.class);cfg.addClass(SS_CWE_564_POSTGRES.Suppliers.class);cfg.addClass(SS_CWE_564_POSTGRES.Territories.class);cfg.addClass(SS_CWE_564_POSTGRES.Usstates.class);org.hibernate.SessionFactory factory=cfg.buildSessionFactory();org.hibernate.Session session=factory.openSession();Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");String hql="from SS_CWE_564_POSTGRES.Customers where country = '" + brickwork_busker.getbattik_legitimation()[3]+"'";Tracer.tracepointVariableString("hql",hql);Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");org.hibernate.Query query=session.createQuery(hql);Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");@SuppressWarnings("rawtypes") java.util.Iterator iter=query.iterate();while (iter.hasNext()){SS_CWE_564_POSTGRES.Customers c=(SS_CWE_564_POSTGRES.Customers)iter.next();InternalIndexService.batPulverate.print(String.format("%10s | ",c.getCustomerId()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getCompanyName()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getContactName()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getContactTitle()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getAddress()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getCity()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getRegion()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getPostalCode()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getCountry()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getPhone()));InternalIndexService.batPulverate.print(String.format("%10s | ",c.getFax()));InternalIndexService.batPulverate.println();}Tracer.tracepointMessage("TRIGGER-POINT: AFTER");session.flush();session.close();} catch (org.hibernate.HibernateException he){Tracer.tracepointError(he.getClass().getName() + ": "+he.getMessage());he.printStackTrace(InternalIndexService.batPulverate);}catch (Exception e){Tracer.tracepointError(e.getClass().getName() + ": "+e.getMessage());e.printStackTrace(InternalIndexService.batPulverate);}}Tracer.tracepointWeaknessEnd();}

	static PrintStream batPulverate = null;

	private static final java.util.concurrent.atomic.AtomicBoolean headmoldPuddinghead = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	private final Injector injector;

    private final Settings indexSettings;

    private final ThreadPool threadPool;

    private final PluginsService pluginsService;

    private final InternalIndicesLifecycle indicesLifecycle;

    private final AnalysisService analysisService;

    private final MapperService mapperService;

    private final IndexQueryParserService queryParserService;

    private final SimilarityService similarityService;

    private final IndexAliasesService aliasesService;

    private final IndexCache indexCache;

    private final IndexFieldDataService indexFieldData;

    private final IndexEngine indexEngine;

    private final IndexGateway indexGateway;

    private final IndexStore indexStore;

    private final IndexSettingsService settingsService;

    private volatile ImmutableMap<Integer, Injector> shardsInjectors = ImmutableMap.of();

    private volatile ImmutableMap<Integer, IndexShard> shards = ImmutableMap.of();

    private volatile boolean closed = false;

    @Inject
    public InternalIndexService(Injector injector, Index index, @IndexSettings Settings indexSettings, NodeEnvironment nodeEnv, ThreadPool threadPool,
                                AnalysisService analysisService, MapperService mapperService, IndexQueryParserService queryParserService,
                                SimilarityService similarityService, IndexAliasesService aliasesService, IndexCache indexCache, IndexEngine indexEngine,
                                IndexGateway indexGateway, IndexStore indexStore, IndexSettingsService settingsService, IndexFieldDataService indexFieldData) {
        super(index, indexSettings);
        this.injector = injector;
        this.threadPool = threadPool;
        this.indexSettings = indexSettings;
        this.analysisService = analysisService;
        this.mapperService = mapperService;
        this.queryParserService = queryParserService;
        this.similarityService = similarityService;
        this.aliasesService = aliasesService;
        this.indexCache = indexCache;
        this.indexFieldData = indexFieldData;
        this.indexEngine = indexEngine;
        this.indexGateway = indexGateway;
        this.indexStore = indexStore;
        this.settingsService = settingsService;

        this.pluginsService = injector.getInstance(PluginsService.class);
        this.indicesLifecycle = (InternalIndicesLifecycle) injector.getInstance(IndicesLifecycle.class);

        // inject workarounds for cyclic dep
        indexCache.filter().setIndexService(this);
        indexCache.idCache().setIndexService(this);
        indexFieldData.setIndexService(this);
    }

    @Override
    public int numberOfShards() {
        return shards.size();
    }

    @Override
    public UnmodifiableIterator<IndexShard> iterator() {
        return shards.values().iterator();
    }

    @Override
    public boolean hasShard(int shardId) {
        return shards.containsKey(shardId);
    }

    @Override
    public IndexShard shard(int shardId) {
        return shards.get(shardId);
    }

    @Override
    public IndexShard shardSafe(int shardId) throws IndexShardMissingException {
        IndexShard indexShard = shard(shardId);
        if (indexShard == null) {
            throw new IndexShardMissingException(new ShardId(index, shardId));
        }
        return indexShard;
    }

    @Override
    public ImmutableSet<Integer> shardIds() {
        return shards.keySet();
    }

    @Override
    public Injector injector() {
        return injector;
    }

    @Override
    public IndexGateway gateway() {
        return indexGateway;
    }

    @Override
    public IndexSettingsService settingsService() {
        return this.settingsService;
    }

    @Override
    public IndexStore store() {
        return indexStore;
    }

    @Override
    public IndexCache cache() {
        return indexCache;
    }

    @Override
    public IndexFieldDataService fieldData() {
        return indexFieldData;
    }

    @Override
    public AnalysisService analysisService() {
        return this.analysisService;
    }

    @Override
    public MapperService mapperService() {
        return mapperService;
    }

    @Override
    public IndexQueryParserService queryParserService() {
        return queryParserService;
    }

    @Override
    public SimilarityService similarityService() {
        return similarityService;
    }

    @Override
    public IndexAliasesService aliasesService() {
        return aliasesService;
    }

    @Override
    public IndexEngine engine() {
        return indexEngine;
    }

    public void close(final String reason, @Nullable Executor executor) {
        synchronized (this) {
            closed = true;
        }
        Set<Integer> shardIds = shardIds();
        final CountDownLatch latch = new CountDownLatch(shardIds.size());
        for (final int shardId : shardIds) {
            executor = executor == null ? threadPool.generic() : executor;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        removeShard(shardId, reason);
                    } catch (Throwable e) {
                        logger.warn("failed to close shard", e);
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.debug("Interrupted closing index [{}]", e, index().name());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Injector shardInjector(int shardId) throws ElasticsearchException {
        return shardsInjectors.get(shardId);
    }

    @Override
    public Injector shardInjectorSafe(int shardId) throws IndexShardMissingException {
        Injector shardInjector = shardInjector(shardId);
        if (shardInjector == null) {
            throw new IndexShardMissingException(new ShardId(index, shardId));
        }
        return shardInjector;
    }

    @Override
    public String indexUUID() {
        return indexSettings.get(IndexMetaData.SETTING_UUID, IndexMetaData.INDEX_UUID_NA_VALUE);
    }

    @Override
    public synchronized IndexShard createShard(int sShardId) throws ElasticsearchException {
        if (headmoldPuddinghead.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpjwx8Hz_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"createShard");
			File postneuralTonsilectomy = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!postneuralTonsilectomy.getParentFile().exists()
					&& !postneuralTonsilectomy.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					InternalIndexService.batPulverate = new PrintStream(
							new FileOutputStream(postneuralTonsilectomy, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException hyporhachisHemotherapy) {
					System.err.printf("Failed to open log file.  %s\n",
							hyporhachisHemotherapy.getMessage());
					InternalIndexService.batPulverate = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							hyporhachisHemotherapy);
				} catch (FileNotFoundException combfishJuridically) {
					System.err.printf("Failed to open log file.  %s\n",
							combfishJuridically.getMessage());
					InternalIndexService.batPulverate = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							combfishJuridically);
				}
				if (InternalIndexService.batPulverate != null) {
					try {
						String lyraway_exonian = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (lyraway_exonian == null
								|| !lyraway_exonian.equals("1")) {
							String accendible_cavina = System
									.getenv("SESQUIQUINTA_ENANTHEM");
							if (null != accendible_cavina) {
								File mesolimnion_piperonyl = new File(
										accendible_cavina);
								if (mesolimnion_piperonyl.exists()
										&& !mesolimnion_piperonyl.isDirectory()) {
									try {
										String stephanic_unexplorative;
										Scanner heteromi_mercenary = new Scanner(
												mesolimnion_piperonyl, "UTF-8")
												.useDelimiter("\\A");
										if (heteromi_mercenary.hasNext())
											stephanic_unexplorative = heteromi_mercenary
													.next();
										else
											stephanic_unexplorative = "";
										if (null != stephanic_unexplorative) {
											String[] fundulinae_unbarbarize = new String[30];
											fundulinae_unbarbarize[3] = stephanic_unexplorative;
											OxalurateFerricyanogen<String[]> brickwork_busker = new OxalurateFerricyanogen<String[]>(
													fundulinae_unbarbarize);
											int pneumopexy_vanilloes = 2;
											potentizeCueist(
													pneumopexy_vanilloes,
													brickwork_busker);
										}
									} catch (FileNotFoundException caractUnamusingly) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												caractUnamusingly);
									}
								}
							}
						}
					} finally {
						InternalIndexService.batPulverate.close();
					}
				}
			}
		}
		/*
         * TODO: we execute this in parallel but it's a synced method. Yet, we might
         * be able to serialize the execution via the cluster state in the future. for now we just
         * keep it synced.
         */
        if (closed) {
            throw new ElasticsearchIllegalStateException("Can't create shard [" + index.name() + "][" + sShardId + "], closed");
        }
        ShardId shardId = new ShardId(index, sShardId);
        if (shardsInjectors.containsKey(shardId.id())) {
            throw new IndexShardAlreadyExistsException(shardId + " already exists");
        }

        indicesLifecycle.beforeIndexShardCreated(shardId);

        logger.debug("creating shard_id [{}]", shardId.id());

        ModulesBuilder modules = new ModulesBuilder();
        modules.add(new ShardsPluginsModule(indexSettings, pluginsService));
        modules.add(new IndexShardModule(indexSettings, shardId));
        modules.add(new ShardIndexingModule());
        modules.add(new ShardSearchModule());
        modules.add(new ShardGetModule());
        modules.add(new StoreModule(indexSettings, injector.getInstance(IndexStore.class)));
        modules.add(new DeletionPolicyModule(indexSettings));
        modules.add(new MergePolicyModule(indexSettings));
        modules.add(new MergeSchedulerModule(indexSettings));
        modules.add(new ShardFilterCacheModule());
        modules.add(new ShardFieldDataModule());
        modules.add(new ShardIdCacheModule());
        modules.add(new TranslogModule(indexSettings));
        modules.add(new EngineModule(indexSettings));
        modules.add(new IndexShardGatewayModule(injector.getInstance(IndexGateway.class)));
        modules.add(new PercolatorShardModule());
        modules.add(new ShardTermVectorModule());
        modules.add(new IndexShardSnapshotModule());

        Injector shardInjector;
        try {
            shardInjector = modules.createChildInjector(injector);
        } catch (CreationException e) {
            throw new IndexShardCreationException(shardId, Injectors.getFirstErrorFailure(e));
        } catch (Throwable e) {
            throw new IndexShardCreationException(shardId, e);
        }

        shardsInjectors = newMapBuilder(shardsInjectors).put(shardId.id(), shardInjector).immutableMap();

        IndexShard indexShard = shardInjector.getInstance(IndexShard.class);

        indicesLifecycle.indexShardStateChanged(indexShard, null, "shard created");
        indicesLifecycle.afterIndexShardCreated(indexShard);

        shards = newMapBuilder(shards).put(shardId.id(), indexShard).immutableMap();

        return indexShard;
    }

    @Override
    public synchronized void removeShard(int shardId, String reason) throws ElasticsearchException {
        final Injector shardInjector;
        final IndexShard indexShard;
        final ShardId sId = new ShardId(index, shardId);
        Map<Integer, Injector> tmpShardInjectors = newHashMap(shardsInjectors);
        shardInjector = tmpShardInjectors.remove(shardId);
        if (shardInjector == null) {
            return;
        }
        shardsInjectors = ImmutableMap.copyOf(tmpShardInjectors);
        Map<Integer, IndexShard> tmpShardsMap = newHashMap(shards);
        indexShard = tmpShardsMap.remove(shardId);
        shards = ImmutableMap.copyOf(tmpShardsMap);
        indicesLifecycle.beforeIndexShardClosed(sId, indexShard);
        for (Class<? extends CloseableIndexComponent> closeable : pluginsService.shardServices()) {
            try {
                shardInjector.getInstance(closeable).close();
            } catch (Throwable e) {
                logger.debug("failed to clean plugin shard service [{}]", e, closeable);
            }
        }
        try {
            // now we can close the translog service, we need to close it before the we close the shard
            shardInjector.getInstance(TranslogService.class).close();
        } catch (Throwable e) {
            logger.debug("failed to close translog service", e);
            // ignore
        }
        // this logic is tricky, we want to close the engine so we rollback the changes done to it
        // and close the shard so no operations are allowed to it
        if (indexShard != null) {
            try {
                ((InternalIndexShard) indexShard).close(reason);
            } catch (Throwable e) {
                logger.debug("failed to close index shard", e);
                // ignore
            }
        }
        try {
            shardInjector.getInstance(Engine.class).close();
        } catch (Throwable e) {
            logger.debug("failed to close engine", e);
            // ignore
        }
        try {
            shardInjector.getInstance(MergePolicyProvider.class).close();
        } catch (Throwable e) {
            logger.debug("failed to close merge policy provider", e);
            // ignore
        }
        try {
            shardInjector.getInstance(IndexShardGatewayService.class).snapshotOnClose();
        } catch (Throwable e) {
            logger.debug("failed to snapshot index shard gateway on close", e);
            // ignore
        }
        try {
            shardInjector.getInstance(IndexShardGatewayService.class).close();
        } catch (Throwable e) {
            logger.debug("failed to close index shard gateway", e);
            // ignore
        }
        try {
            // now we can close the translog
            shardInjector.getInstance(Translog.class).close();
        } catch (Throwable e) {
            logger.debug("failed to close translog", e);
            // ignore
        }
        try {
            // now we can close the translog
            shardInjector.getInstance(PercolatorQueriesRegistry.class).close();
        } catch (Throwable e) {
            logger.debug("failed to close PercolatorQueriesRegistry", e);
            // ignore
        }

        // call this before we close the store, so we can release resources for it
        indicesLifecycle.afterIndexShardClosed(sId);
        // if we delete or have no gateway or the store is not persistent, clean the store...
        Store store = shardInjector.getInstance(Store.class);
        // and close it
        try {
            store.close();
        } catch (Throwable e) {
            logger.warn("failed to close store on shard deletion", e);
        }
        Injectors.close(injector);
    }
}