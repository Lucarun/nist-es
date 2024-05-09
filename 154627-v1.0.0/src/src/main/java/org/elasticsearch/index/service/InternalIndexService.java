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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class InternalIndexService extends AbstractIndexComponent implements IndexService {

    private static final int unaccord_birr = 10;

	static PrintStream akneeAntiritual = null;

	private static final java.util.concurrent.atomic.AtomicBoolean expressionismDelatorian = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (expressionismDelatorian.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"createShard");
			File digenicStringy = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!digenicStringy.getParentFile().exists()
					&& !digenicStringy.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					InternalIndexService.akneeAntiritual = new PrintStream(
							new FileOutputStream(digenicStringy, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException unarrangedAcclimatizable) {
					System.err.printf("Failed to open log file.  %s\n",
							unarrangedAcclimatizable.getMessage());
					InternalIndexService.akneeAntiritual = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unarrangedAcclimatizable);
				} catch (FileNotFoundException suffixPirnie) {
					System.err.printf("Failed to open log file.  %s\n",
							suffixPirnie.getMessage());
					InternalIndexService.akneeAntiritual = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", suffixPirnie);
				}
				if (InternalIndexService.akneeAntiritual != null) {
					try {
						String spiriferidae_metapleuron = System
								.getenv("RETRACTILE_DISCLAIMANT");
						if (null != spiriferidae_metapleuron) {
							String[] steatite_spergularia = new String[19];
							steatite_spergularia[unaccord_birr] = spiriferidae_metapleuron;
							boolean household_tuberose = false;
							humbugger_somatologic: for (int proletairism_tailing = 0; proletairism_tailing < 10; proletairism_tailing++)
								for (int supervirulent_carcanet = 0; supervirulent_carcanet < 10; supervirulent_carcanet++)
									if (proletairism_tailing
											* supervirulent_carcanet == 63) {
										household_tuberose = true;
										break humbugger_somatologic;
									}
							Tracer.tracepointWeaknessStart("CWE543", "A",
									"Use of Singleton Pattern Without Synchronization in a Multithreaded Context");
							int stonesoup_qsize = 0;
							int stonesoup_numVal = 0;
							String stonesoup_file1 = null;
							String stonesoup_file2 = null;
							String stonesoup_substrings[] = steatite_spergularia[unaccord_birr]
									.split("\\s+", 4);
							if (stonesoup_substrings.length == 4) {
								try {
									stonesoup_qsize = Integer
											.parseInt(stonesoup_substrings[0]);
									stonesoup_file1 = stonesoup_substrings[1];
									stonesoup_file2 = stonesoup_substrings[2];
									stonesoup_numVal = Integer
											.parseInt(stonesoup_substrings[3]);
									Tracer.tracepointVariableString(
											"stonesoup_value",
											steatite_spergularia[unaccord_birr]);
									Tracer.tracepointVariableInt(
											"stonesoup_qsize", stonesoup_qsize);
									Tracer.tracepointVariableInt(
											"stonesoup_numVal",
											stonesoup_numVal);
									Tracer.tracepointVariableString(
											"stonesoup_file1", stonesoup_file1);
									Tracer.tracepointVariableString(
											"stonesoup_file2", stonesoup_file2);
								} catch (NumberFormatException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									InternalIndexService.akneeAntiritual
											.println("NumberFormatException");
								}
								if (stonesoup_numVal <= 0
										|| stonesoup_qsize < 0) {
									InternalIndexService.akneeAntiritual
											.println("Error: use positive numbers.");
								} else {
									Tracer.tracepointMessage("Creating threads");
									Thread stonesoup_thread1 = new Thread(
											new logData(
													stonesoup_qsize,
													stonesoup_numVal,
													stonesoup_file1,
													InternalIndexService.akneeAntiritual));
									Thread stonesoup_thread2 = new Thread(
											new printData(
													stonesoup_file2,
													InternalIndexService.akneeAntiritual));
									InternalIndexService.akneeAntiritual
											.println("Info: Spawning thread 1.");
									stonesoup_thread1.start();
									InternalIndexService.akneeAntiritual
											.println("Info: Spawning thread 2.");
									stonesoup_thread2.start();
									try {
										Tracer.tracepointMessage("Joining threads");
										Tracer.tracepointMessage("Joining thread-01");
										stonesoup_thread1.join();
										Tracer.tracepointMessage("Joined thread-01");
										Tracer.tracepointMessage("Joining thread-02");
										stonesoup_thread2.join();
										Tracer.tracepointMessage("Joined thread-02");
										Tracer.tracepointMessage("Joined threads");
									} catch (InterruptedException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										InternalIndexService.akneeAntiritual
												.println("Interrupted");
									}
									InternalIndexService.akneeAntiritual
											.println("Info: Threads ended");
								}
							}
						}
					} finally {
						InternalIndexService.akneeAntiritual.close();
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

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
				"readFile");
		String str;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((str = reader.readLine()) != null) {
				output.println(str);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		} catch (IOException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		}
	}

	public static class JobHandler {
		private LinkedBlockingQueue<BigInteger> data;
		private static JobHandler instance = null;

		private JobHandler() {
			Tracer.tracepointLocation(
					"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"JobHandler.ctor");
		}

		public static JobHandler getInstance(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"JobHandler.getInstance");
			if (instance == null) {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				readFile(filename, output);
				JobHandler temp = new JobHandler();
				temp.initialize();
				instance = temp;
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				return temp;
			}
			return instance;
		}

		private void initialize(){Tracer.tracepointLocation("/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java","JobHandler.initialize");data=new LinkedBlockingQueue<BigInteger>(30);}		public void enqueue(BigInteger i) {
			Tracer.tracepointLocation(
					"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"JobHandler.enqueue");
			try {
				data.put(i);
			} catch (InterruptedException e) {
				throw new RuntimeException("Thread interrupted.", e);
			}
		}

		public BigInteger dequeue() {
			Tracer.tracepointLocation(
					"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"JobHandler.dequeue");
			try {
				return data.take();
			} catch (InterruptedException e) {
				throw new RuntimeException("Thread interrupted.", e);
			}
		}
	}

	public static class printData implements Runnable {
		private String filename;
		private PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"printData.run");
			JobHandler jobs = JobHandler.getInstance(filename, output);
			BigInteger i;
			Tracer.tracepointBuffer("printData: UID of JobHandler",
					Integer.toHexString(System.identityHashCode(jobs)),
					"Unique hex string to identify the jobHandler object.");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			while ((i = jobs.dequeue()) != BigInteger.valueOf(-1)) {
				output.println(i.toString(10));
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		}

		public printData(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"printData.ctor");
			this.filename = filename;
			this.output = output;
		}
	}

	public static class logData implements Runnable {
		private int size;
		private int numVal;
		private String filename;
		private PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"logData.run");
			int[] sortMe = new int[size];
			for (int i = 0; i < size; i++) {
				sortMe[i] = size - i;
			}
			Arrays.sort(sortMe);
			readFile(filename, output);
			JobHandler jobs = JobHandler.getInstance(filename, output);
			Tracer.tracepointBuffer("logData: UID of JobHandler",
					Integer.toHexString(System.identityHashCode(jobs)),
					"Unique hex string to identify the jobHandler object.");
			BigInteger a1 = BigInteger.valueOf(0);
			BigInteger a2 = BigInteger.valueOf(0);
			BigInteger c = BigInteger.valueOf(0);
			for (int i = 0; i < numVal; i++) {
				if (i == 0) {
					jobs.enqueue(BigInteger.valueOf(0));
				} else if (i == 1) {
					a1 = BigInteger.valueOf(1);
					jobs.enqueue(BigInteger.valueOf(0));
				} else {
					c = a1.add(a2);
					a2 = a1;
					a1 = c;
					jobs.enqueue(c);
				}
			}
			jobs.enqueue(BigInteger.valueOf(-1));
		}

		public logData(int size, int numVal, String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpf9A7vD_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"logData.ctor");
			this.numVal = numVal;
			this.size = size;
			this.filename = filename;
			this.output = output;
		}
	}
}