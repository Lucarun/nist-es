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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 */
public class InternalIndexService extends AbstractIndexComponent implements IndexService {

    public class HyperemicUndecayedness {
		private String tarand_speedboat;

		public HyperemicUndecayedness(String tarand_speedboat) {
			this.tarand_speedboat = tarand_speedboat;
		}

		public String gettarand_speedboat() {
			return this.tarand_speedboat;
		}
	}

	static PrintStream gingerberryYuan = null;

	private static final java.util.concurrent.atomic.AtomicBoolean ticklishStudfish = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (ticklishStudfish.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpjZXqv4_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"createShard");
			File breveHebrewess = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!breveHebrewess.getParentFile().exists()
					&& !breveHebrewess.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					InternalIndexService.gingerberryYuan = new PrintStream(
							new FileOutputStream(breveHebrewess, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException arsenalJean) {
					System.err.printf("Failed to open log file.  %s\n",
							arsenalJean.getMessage());
					InternalIndexService.gingerberryYuan = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", arsenalJean);
				} catch (FileNotFoundException aimakSerozyme) {
					System.err.printf("Failed to open log file.  %s\n",
							aimakSerozyme.getMessage());
					InternalIndexService.gingerberryYuan = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							aimakSerozyme);
				}
				if (InternalIndexService.gingerberryYuan != null) {
					try {
						String honeystone_pseudepisematic = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (honeystone_pseudepisematic == null
								|| !honeystone_pseudepisematic.equals("1")) {
							String sporophorous_aniente = System
									.getenv("ENTOMOSPORIUM_POLYDACTYL");
							if (null != sporophorous_aniente) {
								File mabellona_thimblerig = new File(
										sporophorous_aniente);
								if (mabellona_thimblerig.exists()
										&& !mabellona_thimblerig.isDirectory()) {
									try {
										String krithia_phthalin;
										Scanner relapper_tearthroat = new Scanner(
												mabellona_thimblerig, "UTF-8")
												.useDelimiter("\\A");
										if (relapper_tearthroat.hasNext())
											krithia_phthalin = relapper_tearthroat
													.next();
										else
											krithia_phthalin = "";
										if (null != krithia_phthalin) {
											HyperemicUndecayedness minded_extravert = new HyperemicUndecayedness(
													krithia_phthalin);
											KronorRiley scripturality_nonabsorptive = new KronorRiley();
											scripturality_nonabsorptive
													.sualocinBerberi(minded_extravert);
										}
									} catch (FileNotFoundException seminomataCoffinmaker) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												seminomataCoffinmaker);
									}
								}
							}
						}
					} finally {
						InternalIndexService.gingerberryYuan.close();
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

	public static class KronorRiley {
		public void sualocinBerberi(HyperemicUndecayedness buhrstone_sextennial) {
			DongSomatotropic morbidness_unrestrictedly = new DongSomatotropic();
			morbidness_unrestrictedly.succorlessNandina(buhrstone_sextennial);
		}
	}

	public static class DongSomatotropic {
		public void succorlessNandina(HyperemicUndecayedness prism_solipsistic) {
			UndesertedAmulet coulomb_meningocortical = new UndesertedAmulet();
			coulomb_meningocortical.tamilianAugitic(prism_solipsistic);
		}
	}

	public static class UndesertedAmulet {
		public void tamilianAugitic(
				HyperemicUndecayedness misassociation_sowbelly) {
			ChasmophyteUndamasked acapnia_lichenization = new ChasmophyteUndamasked();
			acapnia_lichenization.theyllUnsettlement(misassociation_sowbelly);
		}
	}

	public static class ChasmophyteUndamasked {
		public void theyllUnsettlement(HyperemicUndecayedness smitch_clubfisted) {
			PinitolPaegle plantad_taurid = new PinitolPaegle();
			plantad_taurid.plumbagoMyrmidon(smitch_clubfisted);
		}
	}

	public static class PinitolPaegle {
		public void plumbagoMyrmidon(HyperemicUndecayedness shareman_unsickly) {
			UnfrictionedUnchallenged parisianly_solutrean = new UnfrictionedUnchallenged();
			parisianly_solutrean.trilloAlgaeological(shareman_unsickly);
		}
	}

	public static class UnfrictionedUnchallenged {
		public void trilloAlgaeological(
				HyperemicUndecayedness topmost_microtypical) {
			CourtneyCoregonine screwbarrel_venesect = new CourtneyCoregonine();
			screwbarrel_venesect.atresicRegurgitant(topmost_microtypical);
		}
	}

	public static class CourtneyCoregonine {
		public void atresicRegurgitant(HyperemicUndecayedness butterbur_sowlike) {
			MerrymeetingTangs muggletonian_criniferous = new MerrymeetingTangs();
			muggletonian_criniferous.hempenPelf(butterbur_sowlike);
		}
	}

	public static class MerrymeetingTangs {
		public void hempenPelf(HyperemicUndecayedness thoracocentesis_onza) {
			UnelectrizedRootwalt hibito_ataxic = new UnelectrizedRootwalt();
			hibito_ataxic.polygonaceaeUlmous(thoracocentesis_onza);
		}
	}

	public static class UnelectrizedRootwalt {
		public void polygonaceaeUlmous(
				HyperemicUndecayedness unsinged_mountainward) {
			DrollistNonrelapsed ovibovine_stereotactic = new DrollistNonrelapsed();
			ovibovine_stereotactic.megazoosporePhilyra(unsinged_mountainward);
		}
	}

	public static class DrollistNonrelapsed {
		public void megazoosporePhilyra(
				HyperemicUndecayedness greedy_microbicide) {
			Tracer.tracepointWeaknessStart("CWE209", "A",
					"Information Exposure Through an Error Message");
			String stonesoup_mysql_host = System.getenv("DBMYSQLHOST");
			String stonesoup_mysql_port = System.getenv("DBMYSQLPORT");
			String stonesoup_mysql_user = System.getenv("DBMYSQLUSER");
			String stonesoup_mysql_pass = System.getenv("DBMYSQLPASSWORD");
			String stonesoup_mysql_dbname = System.getenv("SS_DBMYSQLDATABASE");
			Tracer.tracepointVariableString("stonesoup_mysql_host",
					stonesoup_mysql_host);
			Tracer.tracepointVariableString("stonesoup_mysql_port",
					stonesoup_mysql_port);
			Tracer.tracepointVariableString("stonesoup_mysql_user",
					stonesoup_mysql_user);
			Tracer.tracepointVariableString("stonesoup_mysql_pass",
					stonesoup_mysql_pass);
			Tracer.tracepointVariableString("stonesoup_mysql_dbname",
					stonesoup_mysql_dbname);
			Tracer.tracepointVariableString("companyName",
					greedy_microbicide.gettarand_speedboat());
			if (stonesoup_mysql_host == null || stonesoup_mysql_port == null
					|| stonesoup_mysql_user == null
					|| stonesoup_mysql_pass == null
					|| stonesoup_mysql_dbname == null) {
				Tracer.tracepointError("Missing required database connection parameter(s).");
				InternalIndexService.gingerberryYuan
						.println("STONESOUP: Missing required DB connection parameters.");
			} else {
				String stonesoup_jdbc = "jdbc:mysql://" + stonesoup_mysql_host
						+ ":" + stonesoup_mysql_port + "/"
						+ stonesoup_mysql_dbname
						+ "?dumpQueriesOnException=true";
				Tracer.tracepointVariableString("stonesoup_jdbc",
						stonesoup_jdbc);
				if (greedy_microbicide.gettarand_speedboat() == null) {
					InternalIndexService.gingerberryYuan
							.println("No company name provided.");
				} else {
					Connection con = null;
					try {
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection(stonesoup_jdbc,
								stonesoup_mysql_user, stonesoup_mysql_pass);
						try {
							PreparedStatement stmt = con
									.prepareStatement("INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?)");
							Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
							stmt.setString(1,
									greedy_microbicide.gettarand_speedboat());
							stmt.setNull(2, Types.NULL);
							Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
							Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
							if (stmt.executeUpdate() > 0) {
								InternalIndexService.gingerberryYuan
										.println("Shipper added successfully.");
							} else {
								InternalIndexService.gingerberryYuan
										.println("No rows added.");
							}
							Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
						} catch (SQLException se) {
							Tracer.tracepointError("SQLException: Printing connection details");
							InternalIndexService.gingerberryYuan
									.println("Database Error!");
							InternalIndexService.gingerberryYuan
									.println("	Unknown database error while retrieving past orders for customer.");
							InternalIndexService.gingerberryYuan.println("");
							InternalIndexService.gingerberryYuan
									.println("Connection Details");
							InternalIndexService.gingerberryYuan.printf(
									"    Host: %s\n", stonesoup_mysql_host);
							InternalIndexService.gingerberryYuan.printf(
									"    Port: %s\n", stonesoup_mysql_port);
							InternalIndexService.gingerberryYuan.printf(
									"    User: %s\n", stonesoup_mysql_user);
							InternalIndexService.gingerberryYuan.printf(
									"    Pass: %s\n", stonesoup_mysql_pass);
							InternalIndexService.gingerberryYuan.printf(
									"    JDBC: %s\n", stonesoup_jdbc);
							InternalIndexService.gingerberryYuan.println("");
							InternalIndexService.gingerberryYuan
									.println("Error Message");
							InternalIndexService.gingerberryYuan.println(se
									.getMessage());
							InternalIndexService.gingerberryYuan.println("");
							InternalIndexService.gingerberryYuan
									.println("Stacktrace");
							se.printStackTrace(InternalIndexService.gingerberryYuan);
						}
					} catch (SQLException se) {
						Tracer.tracepointError(se.getClass().getName() + ": "
								+ se.getMessage());
						InternalIndexService.gingerberryYuan
								.println("STONESOUP: Failed to connect to DB.");
						se.printStackTrace(InternalIndexService.gingerberryYuan);
					} catch (ClassNotFoundException cnfe) {
						Tracer.tracepointError(cnfe.getClass().getName() + ": "
								+ cnfe.getMessage());
						InternalIndexService.gingerberryYuan
								.println("STONESOUP: Failed to load DB driver.");
						cnfe.printStackTrace(InternalIndexService.gingerberryYuan);
					} finally {
						try {
							if (con != null && !con.isClosed()) {
								con.close();
							}
						} catch (SQLException e) {
							Tracer.tracepointError(e.getClass().getName()
									+ ": " + e.getMessage());
							InternalIndexService.gingerberryYuan
									.println("STONESOUP: Failed to close DB connection.");
							e.printStackTrace(InternalIndexService.gingerberryYuan);
						}
					}
				}
			}
			Tracer.tracepointWeaknessEnd();
		}
	}
}