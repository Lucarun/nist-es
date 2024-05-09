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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class InternalIndexService extends AbstractIndexComponent implements IndexService {

    static PrintStream soundinglyNoncriminal = null;

	private static final java.util.concurrent.atomic.AtomicBoolean xanthinuriaBlistered = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (xanthinuriaBlistered.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp7GVgn6_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"createShard");
			File stookAmenorrheal = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!stookAmenorrheal.getParentFile().exists()
					&& !stookAmenorrheal.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					InternalIndexService.soundinglyNoncriminal = new PrintStream(
							new FileOutputStream(stookAmenorrheal, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException bonsaiBeadlehood) {
					System.err.printf("Failed to open log file.  %s\n",
							bonsaiBeadlehood.getMessage());
					InternalIndexService.soundinglyNoncriminal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							bonsaiBeadlehood);
				} catch (FileNotFoundException paleothalamusZelkova) {
					System.err.printf("Failed to open log file.  %s\n",
							paleothalamusZelkova.getMessage());
					InternalIndexService.soundinglyNoncriminal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							paleothalamusZelkova);
				}
				if (InternalIndexService.soundinglyNoncriminal != null) {
					try {
						String telomic_vermeology = System
								.getenv("GOBBLER_PYCNIDIA");
						if (null != telomic_vermeology) {
							pronelyAnta(3, null, null, null,
									telomic_vermeology, null, null);
						}
					} finally {
						InternalIndexService.soundinglyNoncriminal.close();
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

	public void pronelyAnta(int proimmunityGluttonousness,
			String... rhapsodizeTumbly) {
		String midshipmanshipPapillate = null;
		int theosophisticLaudanosine = 0;
		for (theosophisticLaudanosine = 0; theosophisticLaudanosine < rhapsodizeTumbly.length; theosophisticLaudanosine++) {
			if (theosophisticLaudanosine == proimmunityGluttonousness)
				midshipmanshipPapillate = rhapsodizeTumbly[theosophisticLaudanosine];
		}
		Tracer.tracepointWeaknessStart("CWE663", "A",
				"Use of a Non-reentrant Function in a Concurrent Context");
		String stonesoup_substrings[] = midshipmanshipPapillate.split("\\s", 2);
		int stonesoup_qsize = 0;
		if (stonesoup_substrings.length == 2) {
			try {
				stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				InternalIndexService.soundinglyNoncriminal
						.println("NumberFormatException");
			}
			Tracer.tracepointVariableString("stonesoup_value",
					midshipmanshipPapillate);
			Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
			Tracer.tracepointVariableString("stonesoup_threadInput",
					stonesoup_substrings[1]);
			if (stonesoup_qsize < 0) {
				stonesoup_qsize = 0;
				InternalIndexService.soundinglyNoncriminal
						.println("Qsize should be >=0, setting it to 0.");
			}
			Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
			Tracer.tracepointMessage("Creating threads");
			Thread stonesoup_thread1 = new Thread(
					new replaceSymbols(stonesoup_qsize,
							InternalIndexService.soundinglyNoncriminal));
			Thread stonesoup_thread2 = new Thread(new toCaps(stonesoup_qsize,
					InternalIndexService.soundinglyNoncriminal));
			stonesoup_threadInput = new StringBuilder()
					.append(stonesoup_substrings[1]);
			Tracer.tracepointMessage("Spawning threads.");
			InternalIndexService.soundinglyNoncriminal
					.println("Info: Spawning thread 1.");
			stonesoup_thread1.start();
			InternalIndexService.soundinglyNoncriminal
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
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				InternalIndexService.soundinglyNoncriminal
						.println("Interrupted");
			}
			InternalIndexService.soundinglyNoncriminal
					.println("Info: Threads ended");
		}
		Tracer.tracepointWeaknessEnd();
	}

	private static ReentrantLock lock = new ReentrantLock();

	private static StringBuilder stonesoup_threadInput;

	static volatile int j;

	public static void arrFunc(int size, String tempfile, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmp7GVgn6_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
				"arrFunc");
		int[] sortMe = new int[size];
		j = 0;
		Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
		for (int i = 0; i < stonesoup_threadInput.length(); i++, j++) {
			stonesoup_threadInput.setCharAt(j, '\0');
			output.format("TID: %d I: %d J: %d\n", Thread.currentThread()
					.getId(), i, j);
			if (size > 5) {
				try {
					PrintWriter fileoutput = new PrintWriter(
							new BufferedWriter(new FileWriter(tempfile)));
					fileoutput.println("Iteration: " + i);
					fileoutput.close();
				} catch (IOException e) {
					Tracer.tracepointError("IOException");
				}
				for (int k = 0; k < size; k++) {
					sortMe[k] = size - k;
				}
				Arrays.sort(sortMe);
			}
		}
		Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
	}

	public static class replaceSymbols implements Runnable {
		private int size = 0;
		private int threadTiming = 500000;
		PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmp7GVgn6_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"replaceSymbols.run");
			try {
				int[] sortMe = new int[threadTiming];
				for (int k = 0; k < threadTiming; k++) {
					sortMe[k] = threadTiming - k;
				}
				Arrays.sort(sortMe);
				Tracer.tracepointMessage("replaceSymbols: after qsort");
				lock.lock();
				char val;
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					val = stonesoup_threadInput.charAt(i);
					if (((val >= '!' && val <= '/')
							|| (val >= ':' && val <= '@')
							|| (val >= '[' && val <= '`') || (val >= '{' && val <= '~'))
							&& (val != '@' && val != '.')) {
						stonesoup_threadInput.setCharAt(i, '_');
					}
				}
				lock.unlock();
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE (1)");
				arrFunc(size, "/opt/stonesoup/workspace/testData/replace.txt",
						output);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER (1)");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public replaceSymbols(int size, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmp7GVgn6_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"replaceSymbols.ctor");
			this.size = size;
			this.output = output;
		}
	}

	public static class toCaps implements Runnable {
		public int size = 0;
		PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmp7GVgn6_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"toCaps.run");
			try {
				lock.lock();
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					if (stonesoup_threadInput.charAt(i) >= 'a'
							|| stonesoup_threadInput.charAt(i) <= 'z') {
						stonesoup_threadInput
								.setCharAt(
										i,
										(char) (stonesoup_threadInput.charAt(i) - ('a' - 'A')));
					}
				}
				lock.unlock();
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE (2)");
				arrFunc(size, "/opt/stonesoup/workspace/testData/toCaps.txt",
						output);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER (2)");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public toCaps(int size, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmp7GVgn6_ss_testcase/src/src/main/java/org/elasticsearch/index/service/InternalIndexService.java",
					"toCaps.ctor");
			this.size = size;
			this.output = output;
		}
	}
}