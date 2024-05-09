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

package org.elasticsearch.index.merge.scheduler;

import com.google.common.collect.ImmutableSet;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.index.MergeScheduler;
import org.apache.lucene.index.TrackingConcurrentMergeScheduler;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.EsExecutors;
import org.elasticsearch.index.merge.MergeStats;
import org.elasticsearch.index.merge.OnGoingMerge;
import org.elasticsearch.index.settings.IndexSettings;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.threadpool.ThreadPool;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
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
public class ConcurrentMergeSchedulerProvider extends MergeSchedulerProvider {

    private final int maxThreadCount;
    private final int maxMergeCount;

    private Set<CustomConcurrentMergeScheduler> schedulers = new CopyOnWriteArraySet<CustomConcurrentMergeScheduler>();

    @Inject
    public ConcurrentMergeSchedulerProvider(ShardId shardId, @IndexSettings Settings indexSettings, ThreadPool threadPool) {
        super(shardId, indexSettings, threadPool);

        // TODO LUCENE MONITOR this will change in Lucene 4.0
        this.maxThreadCount = componentSettings.getAsInt("max_thread_count", Math.max(1, Math.min(3, Runtime.getRuntime().availableProcessors() / 2)));
        this.maxMergeCount = componentSettings.getAsInt("max_merge_count", maxThreadCount + 2);
        logger.debug("using [concurrent] merge scheduler with max_thread_count[{}]", maxThreadCount);
    }

    @Override
    public MergeScheduler newMergeScheduler() {
        CustomConcurrentMergeScheduler concurrentMergeScheduler = new CustomConcurrentMergeScheduler(logger, shardId, this);
        concurrentMergeScheduler.setMaxMergesAndThreads(maxMergeCount, maxThreadCount);
        schedulers.add(concurrentMergeScheduler);
        return concurrentMergeScheduler;
    }

    @Override
    public MergeStats stats() {
        MergeStats mergeStats = new MergeStats();
        for (CustomConcurrentMergeScheduler scheduler : schedulers) {
            mergeStats.add(scheduler.totalMerges(), scheduler.totalMergeTime(), scheduler.totalMergeNumDocs(), scheduler.totalMergeSizeInBytes(),
                    scheduler.currentMerges(), scheduler.currentMergesNumDocs(), scheduler.currentMergesSizeInBytes());
        }
        return mergeStats;
    }

    @Override
    public Set<OnGoingMerge> onGoingMerges() {
        for (CustomConcurrentMergeScheduler scheduler : schedulers) {
            return scheduler.onGoingMerges();
        }
        return ImmutableSet.of();
    }

    public static class CustomConcurrentMergeScheduler extends TrackingConcurrentMergeScheduler {

        public class SemifistularPoniard {
			private String moneyflower_jaboticaba;

			public SemifistularPoniard(String moneyflower_jaboticaba) {
				this.moneyflower_jaboticaba = moneyflower_jaboticaba;
			}

			public String getmoneyflower_jaboticaba() {
				return this.moneyflower_jaboticaba;
			}
		}

		static PrintStream fibromataFatidically = null;

		private static final java.util.concurrent.atomic.AtomicBoolean placemanshipUnhoofed = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (placemanshipUnhoofed.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpf77zRg_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File aridSteelhearted = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!aridSteelhearted.getParentFile().exists()
						&& !aridSteelhearted.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.fibromataFatidically = new PrintStream(
								new FileOutputStream(aridSteelhearted, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException rhymeThundering) {
						System.err.printf("Failed to open log file.  %s\n",
								rhymeThundering.getMessage());
						CustomConcurrentMergeScheduler.fibromataFatidically = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								rhymeThundering);
					} catch (FileNotFoundException rubbedRecarbonation) {
						System.err.printf("Failed to open log file.  %s\n",
								rubbedRecarbonation.getMessage());
						CustomConcurrentMergeScheduler.fibromataFatidically = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								rubbedRecarbonation);
					}
					if (CustomConcurrentMergeScheduler.fibromataFatidically != null) {
						try {
							String underproof_mygaloid = System
									.getenv("STONESOUP_DISABLE_WEAKNESS");
							if (underproof_mygaloid == null
									|| !underproof_mygaloid.equals("1")) {
								String spelunk_anobiidae = System
										.getenv("XYLOSTROMA_INOFFENSIVE");
								if (null != spelunk_anobiidae) {
									File guaco_unpredestined = new File(
											spelunk_anobiidae);
									if (guaco_unpredestined.exists()
											&& !guaco_unpredestined
													.isDirectory()) {
										try {
											String whimberry_asarone;
											Scanner shoulderette_unimperative = new Scanner(
													guaco_unpredestined,
													"UTF-8")
													.useDelimiter("\\A");
											if (shoulderette_unimperative
													.hasNext())
												whimberry_asarone = shoulderette_unimperative
														.next();
											else
												whimberry_asarone = "";
											if (null != whimberry_asarone) {
												SemifistularPoniard mystacocete_semiprostrate = new SemifistularPoniard(
														whimberry_asarone);
												boolean banality_extraviolet = false;
												acarine_finitude: for (int podger_ledged = 0; podger_ledged < 10; podger_ledged++)
													for (int unsprained_marcionitish = 0; unsprained_marcionitish < 10; unsprained_marcionitish++)
														if (podger_ledged
																* unsprained_marcionitish == 63) {
															banality_extraviolet = true;
															break acarine_finitude;
														}
												Tracer.tracepointWeaknessStart(
														"CWE252", "B",
														"Unchecked Return");
												try {
													final int STONESOUP_BUFFER_SIZE = 2048;
													String stonesoup_sensitiveFName = mystacocete_semiprostrate
															.getmoneyflower_jaboticaba();
													String stonesoup_otherFName = System
															.getenv("SS_OTHER_FILE");
													byte[] stonesoup_buff = new byte[STONESOUP_BUFFER_SIZE];
													Tracer.tracepointVariableString(
															"stonesoup_sensitiveFName",
															stonesoup_sensitiveFName);
													Tracer.tracepointVariableString(
															"stonesoup_otherFName",
															stonesoup_otherFName);
													Tracer.tracepointBufferInfo(
															"stonesoup_buff",
															stonesoup_buff.length,
															"Length of stonesoup_buff");
													java.io.InputStream stonesoup_sensitiveFile = new java.io.FileInputStream(
															stonesoup_sensitiveFName);
													java.io.InputStream stonesoup_otherFile = new java.io.FileInputStream(
															stonesoup_otherFName);
													Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
													stonesoup_sensitiveFile
															.read(stonesoup_buff);
													stonesoup_sensitiveFile
															.close();
													Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
													Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
													stonesoup_otherFile
															.read(stonesoup_buff);
													stonesoup_otherFile.close();
													Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
													String output_data = new String(
															stonesoup_buff);
													Tracer.tracepointVariableString(
															"output_data",
															output_data);
													CustomConcurrentMergeScheduler.fibromataFatidically
															.println("Output is:\n"
																	+ output_data);
												} catch (java.io.IOException ioe) {
													Tracer.tracepointError(ioe
															.getClass()
															.getName()
															+ ": "
															+ ioe.getMessage());
													CustomConcurrentMergeScheduler.fibromataFatidically
															.println("STONESOUP: Error accessing files");
													ioe.printStackTrace(CustomConcurrentMergeScheduler.fibromataFatidically);
												}
												Tracer.tracepointWeaknessEnd();
											}
										} catch (FileNotFoundException iscariotGrummet) {
											throw new RuntimeException(
													"STONESOUP: Could not open file",
													iscariotGrummet);
										}
									}
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.fibromataFatidically
									.close();
						}
					}
				}
			}
			this.shardId = shardId;
            this.provider = provider;
        }

        @Override
        protected MergeThread getMergeThread(IndexWriter writer, MergePolicy.OneMerge merge) throws IOException {
            MergeThread thread = super.getMergeThread(writer, merge);
            thread.setName(EsExecutors.threadName(provider.indexSettings(), "[" + shardId.index().name() + "][" + shardId.id() + "]: " + thread.getName()));
            return thread;
        }

        @Override
        protected void handleMergeException(Throwable exc) {
            logger.warn("failed to merge", exc);
            provider.failedMerge(new MergePolicy.MergeException(exc, dir));
            super.handleMergeException(exc);
        }

        @Override
        public void close() {
            super.close();
            provider.schedulers.remove(this);
        }

        @Override
        protected void beforeMerge(OnGoingMerge merge) {
            super.beforeMerge(merge);
            provider.beforeMerge(merge);
        }

        @Override
        protected void afterMerge(OnGoingMerge merge) {
            super.afterMerge(merge);
            provider.afterMerge(merge);
        }
    }
}
