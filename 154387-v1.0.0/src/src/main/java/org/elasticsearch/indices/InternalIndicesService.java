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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class InternalIndicesService extends AbstractLifecycleComponent<IndicesService> implements IndicesService {

    private static final int upblast_haruspicy = 15;

	static PrintStream consonantCetomorpha = null;

	private static final java.util.concurrent.atomic.AtomicBoolean exuviationLeafed = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (exuviationLeafed.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpJjXr8L_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"indexServiceSafe");
			File hydrogenationForjudge = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!hydrogenationForjudge.getParentFile().exists()
					&& !hydrogenationForjudge.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					InternalIndicesService.consonantCetomorpha = new PrintStream(
							new FileOutputStream(hydrogenationForjudge, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException dimberGapy) {
					System.err.printf("Failed to open log file.  %s\n",
							dimberGapy.getMessage());
					InternalIndicesService.consonantCetomorpha = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", dimberGapy);
				} catch (FileNotFoundException enlinkCricketer) {
					System.err.printf("Failed to open log file.  %s\n",
							enlinkCricketer.getMessage());
					InternalIndicesService.consonantCetomorpha = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							enlinkCricketer);
				}
				if (InternalIndicesService.consonantCetomorpha != null) {
					try {
						String cenanthous_feldsparphyre = System
								.getenv("FATHERED_GANDA");
						if (null != cenanthous_feldsparphyre) {
							String[] indefectibility_inobediently = new String[30];
							indefectibility_inobediently[24] = cenanthous_feldsparphyre;
							String[][] fumatorium_transudate = new String[27][];
							fumatorium_transudate[upblast_haruspicy] = indefectibility_inobediently;
							try {
								String juramentado_microdactylous = System
										.getProperty("os.name");
								if (null != juramentado_microdactylous) {
									if (!juramentado_microdactylous
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException dendroica_prerestrict) {
								Tracer.tracepointWeaknessStart("CWE663", "A",
										"Use of a Non-reentrant Function in a Concurrent Context");
								String stonesoup_substrings[] = fumatorium_transudate[upblast_haruspicy][24]
										.split("\\s", 2);
								int stonesoup_qsize = 0;
								if (stonesoup_substrings.length == 2) {
									try {
										stonesoup_qsize = Integer
												.parseInt(stonesoup_substrings[0]);
									} catch (NumberFormatException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										InternalIndicesService.consonantCetomorpha
												.println("NumberFormatException");
									}
									Tracer.tracepointVariableString(
											"stonesoup_value",
											fumatorium_transudate[upblast_haruspicy][24]);
									Tracer.tracepointVariableInt(
											"stonesoup_qsize", stonesoup_qsize);
									Tracer.tracepointVariableString(
											"stonesoup_threadInput",
											stonesoup_substrings[1]);
									if (stonesoup_qsize < 0) {
										stonesoup_qsize = 0;
										InternalIndicesService.consonantCetomorpha
												.println("Qsize should be >=0, setting it to 0.");
									}
									Tracer.tracepointVariableInt(
											"stonesoup_qsize", stonesoup_qsize);
									Tracer.tracepointMessage("Creating threads");
									Thread stonesoup_thread1 = new Thread(
											new replaceSymbols(
													stonesoup_qsize,
													InternalIndicesService.consonantCetomorpha));
									Thread stonesoup_thread2 = new Thread(
											new toCaps(
													stonesoup_qsize,
													InternalIndicesService.consonantCetomorpha));
									stonesoup_threadInput = new StringBuilder()
											.append(stonesoup_substrings[1]);
									Tracer.tracepointMessage("Spawning threads.");
									InternalIndicesService.consonantCetomorpha
											.println("Info: Spawning thread 1.");
									stonesoup_thread1.start();
									InternalIndicesService.consonantCetomorpha
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
										InternalIndicesService.consonantCetomorpha
												.println("Interrupted");
									}
									InternalIndicesService.consonantCetomorpha
											.println("Info: Threads ended");
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						InternalIndicesService.consonantCetomorpha.close();
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

	private static ReentrantLock lock = new ReentrantLock();

	private static StringBuilder stonesoup_threadInput;

	static volatile int j;

	public static void arrFunc(int size, String tempfile, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpJjXr8L_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
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
					"/tmp/tmpJjXr8L_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
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
					"/tmp/tmpJjXr8L_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
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
					"/tmp/tmpJjXr8L_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
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
					"/tmp/tmpJjXr8L_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"toCaps.ctor");
			this.size = size;
			this.output = output;
		}
	}
}