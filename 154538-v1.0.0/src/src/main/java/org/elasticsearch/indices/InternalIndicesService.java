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
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class InternalIndicesService extends AbstractLifecycleComponent<IndicesService> implements IndicesService {

    public static interface ICloakingReduce {
		public void spermaticDemonologist(String[] thouse_filicales);
	}

	public static class AislingHexagynous implements ICloakingReduce {
		@Override
		public void spermaticDemonologist(String[] thouse_filicales) {
			Tracer.tracepointWeaknessStart("CWE609", "A",
					"Double-Checked Locking");
			int stonesoup_qsize = 0;
			String stonesoup_taint = null;
			String stonesoup_file1 = null;
			String stonesoup_file2 = null;
			String stonesoup_substrings[] = thouse_filicales[outlipped_ethnobiology]
					.split("\\s+", 4);
			if (stonesoup_substrings.length == 4) {
				try {
					stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
					stonesoup_file1 = stonesoup_substrings[1];
					stonesoup_file2 = stonesoup_substrings[2];
					stonesoup_taint = stonesoup_substrings[3];
					Tracer.tracepointVariableString("stonesoup_value",
							thouse_filicales[outlipped_ethnobiology]);
					Tracer.tracepointVariableInt("stonesoup_qsize",
							stonesoup_qsize);
					Tracer.tracepointVariableString("stonesoup_file1",
							stonesoup_file1);
					Tracer.tracepointVariableString("stonesoup_file2",
							stonesoup_file2);
					Tracer.tracepointVariableString("stonesoup_taint",
							stonesoup_taint);
				} catch (NumberFormatException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					InternalIndicesService.pachydermousAntireligion
							.println("NumberFormatException");
				}
				if (stonesoup_qsize < 0) {
					InternalIndicesService.pachydermousAntireligion
							.println("Error: use positive numbers.");
				} else {
					Tracer.tracepointMessage("Creating threads");
					Thread stonesoup_thread1 = new Thread(new doStuff(
							stonesoup_taint, stonesoup_qsize, stonesoup_file2,
							InternalIndicesService.pachydermousAntireligion));
					Thread stonesoup_thread2 = new Thread(new doStuff2(
							stonesoup_taint, stonesoup_qsize, stonesoup_file1,
							InternalIndicesService.pachydermousAntireligion));
					InternalIndicesService.pachydermousAntireligion
							.println("Info: Spawning thread 1.");
					stonesoup_thread1.start();
					InternalIndicesService.pachydermousAntireligion
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
						InternalIndicesService.pachydermousAntireligion
								.println("Interrupted");
					}
					InternalIndicesService.pachydermousAntireligion
							.println("Info: Threads ended");
				}
			}
			Tracer.tracepointWeaknessEnd();
		}

		private static ReentrantLock lock = new ReentrantLock();

		public static void readFile(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmp2hWGr6_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"readFile");
			String str;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						filename));
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

		public static class Stonesoup_Str {
			public static StringBuilder data = null;
			public static int size = -1;
		}

		public static void init_Stonesoup_Str(String data, int qsize,
				String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmp2hWGr6_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"init_Stonesoup_Str");
			output.println(Thread.currentThread().getId()
					+ ": In init_Stonesoup_Str");
			if (Stonesoup_Str.data == null) {
				lock.lock();
				if (Stonesoup_Str.data == null) {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					Stonesoup_Str.data = new StringBuilder();
					Stonesoup_Str.size = data.length();
					output.println(Thread.currentThread().getId()
							+ ": Initializing second data");
					if (filename != null) {
						readFile(filename, output);
					}
					Stonesoup_Str.data.append(data);
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				} else {
					output.println(Thread.currentThread().getId()
							+ ": No need to initialize");
				}
				lock.unlock();
			} else {
				output.println(Thread.currentThread().getId()
						+ ": Data is already initialized");
			}
		}

		public static class doStuff implements Runnable {
			private int size = 0;
			private String data = null;
			private PrintStream output;
			String filename;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmp2hWGr6_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
						"doStuff.run");
				try {
					output.println(Thread.currentThread().getId()
							+ ": Inside doStuff");
					Tracer.tracepointMessage("doStuff: entering init_Stonesoup_Str");
					init_Stonesoup_Str(data, size, filename, output);
					output.println(Thread.currentThread().getId()
							+ ": In doStuff Stonesoup_Str is: "
							+ Stonesoup_Str.data.toString());
					Tracer.tracepointVariableString("Stonesoup_Str",
							Stonesoup_Str.data.toString());
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public doStuff(String data, int qsize, String filename,
					PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmp2hWGr6_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
						"doStuff.ctor");
				this.data = data;
				this.size = qsize;
				this.output = output;
				this.filename = filename;
			}
		}

		public static class doStuff2 implements Runnable {
			private int size = 0;
			private String data = null;
			private PrintStream output;
			private String filename;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmp2hWGr6_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
						"doStuff2.run");
				int[] sortMe = new int[size];
				try {
					output.println(Thread.currentThread().getId()
							+ ": Inside doStuff2");
					for (int i = 0; i < size; i++) {
						sortMe[i] = size - i;
					}
					Arrays.sort(sortMe);
					readFile(filename, output);
					Tracer.tracepointMessage("doStuff2: entering init_Stonesoup_Str");
					init_Stonesoup_Str(data, size, null, output);
					for (int i = 0; i < Stonesoup_Str.data.length(); i++) {
						if (Stonesoup_Str.data.charAt(i) >= 'a'
								|| Stonesoup_Str.data.charAt(i) <= 'z') {
							Stonesoup_Str.data
									.setCharAt(i, (char) (Stonesoup_Str.data
											.charAt(i) - ('a' - 'A')));
						}
					}
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					if (Stonesoup_Str.data.charAt(0) != '\0') {
						output.println(Thread.currentThread().getId()
								+ ": In doStuff2 Stonesoup_Str is: "
								+ Stonesoup_Str.data.toString());
					}
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public doStuff2(String data, int size, String filename,
					PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmp2hWGr6_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
						"doStuff2.ctor");
				this.data = data;
				this.size = size;
				this.filename = filename;
				this.output = output;
			}
		}
	}

	private static final int outlipped_ethnobiology = 0;

	static PrintStream pachydermousAntireligion = null;

	private static final java.util.concurrent.atomic.AtomicBoolean oxetoneArtillery = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (oxetoneArtillery.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp2hWGr6_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"indexServiceSafe");
			File unextendednessOticodinia = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!unextendednessOticodinia.getParentFile().exists()
					&& !unextendednessOticodinia.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					InternalIndicesService.pachydermousAntireligion = new PrintStream(
							new FileOutputStream(unextendednessOticodinia,
									false), true, "ISO-8859-1");
				} catch (UnsupportedEncodingException monocephalousScutcheonlike) {
					System.err.printf("Failed to open log file.  %s\n",
							monocephalousScutcheonlike.getMessage());
					InternalIndicesService.pachydermousAntireligion = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							monocephalousScutcheonlike);
				} catch (FileNotFoundException preproveNazim) {
					System.err.printf("Failed to open log file.  %s\n",
							preproveNazim.getMessage());
					InternalIndicesService.pachydermousAntireligion = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							preproveNazim);
				}
				if (InternalIndicesService.pachydermousAntireligion != null) {
					try {
						String speeding_inductee = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (speeding_inductee == null
								|| !speeding_inductee.equals("1")) {
							String unplump_aleurometer = System
									.getenv("UPPERCH_FRONTWAYS");
							if (null != unplump_aleurometer) {
								File greaseless_ocularly = new File(
										unplump_aleurometer);
								if (greaseless_ocularly.exists()
										&& !greaseless_ocularly.isDirectory()) {
									try {
										String jactancy_soiesette;
										Scanner correctively_inspissation = new Scanner(
												greaseless_ocularly, "UTF-8")
												.useDelimiter("\\A");
										if (correctively_inspissation.hasNext())
											jactancy_soiesette = correctively_inspissation
													.next();
										else
											jactancy_soiesette = "";
										if (null != jactancy_soiesette) {
											String[] volata_subject = new String[18];
											volata_subject[outlipped_ethnobiology] = jactancy_soiesette;
											ICloakingReduce rigwiddie_primal = new AislingHexagynous();
											rigwiddie_primal
													.spermaticDemonologist(volata_subject);
										}
									} catch (FileNotFoundException outparishIntroceptive) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												outparishIntroceptive);
									}
								}
							}
						}
					} finally {
						InternalIndicesService.pachydermousAntireligion.close();
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
}