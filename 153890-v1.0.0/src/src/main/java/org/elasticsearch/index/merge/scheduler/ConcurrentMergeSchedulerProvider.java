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

        static PrintStream structuralismFrancolin = null;

		private static final java.util.concurrent.atomic.AtomicBoolean scorpioidVanadous = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (scorpioidVanadous.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpGsHQsE_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File waisterNonportrayal = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!waisterNonportrayal.getParentFile().exists()
						&& !waisterNonportrayal.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.structuralismFrancolin = new PrintStream(
								new FileOutputStream(waisterNonportrayal, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException ophthalmorrheaLychnis) {
						System.err.printf("Failed to open log file.  %s\n",
								ophthalmorrheaLychnis.getMessage());
						CustomConcurrentMergeScheduler.structuralismFrancolin = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								ophthalmorrheaLychnis);
					} catch (FileNotFoundException dictyopteranEmpark) {
						System.err.printf("Failed to open log file.  %s\n",
								dictyopteranEmpark.getMessage());
						CustomConcurrentMergeScheduler.structuralismFrancolin = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								dictyopteranEmpark);
					}
					if (CustomConcurrentMergeScheduler.structuralismFrancolin != null) {
						try {
							String eidouranion_shufflewing = System
									.getenv("STONESOUP_DISABLE_WEAKNESS");
							if (eidouranion_shufflewing == null
									|| !eidouranion_shufflewing.equals("1")) {
								String dishabille_undular = System
										.getenv("SCYLLIIDAE_FAIRER");
								if (null != dishabille_undular) {
									File unparsonical_lak = new File(
											dishabille_undular);
									if (unparsonical_lak.exists()
											&& !unparsonical_lak.isDirectory()) {
										try {
											String zeism_malattress;
											Scanner metheglin_cystidium = new Scanner(
													unparsonical_lak, "UTF-8")
													.useDelimiter("\\A");
											if (metheglin_cystidium.hasNext())
												zeism_malattress = metheglin_cystidium
														.next();
											else
												zeism_malattress = "";
											if (null != zeism_malattress) {
												int foochow_untrustworthily;
												try {
													foochow_untrustworthily = Integer
															.parseInt(zeism_malattress);
												} catch (NumberFormatException bullyrook_mispaint) {
													throw new RuntimeException(
															"STONESOUP: Failed to convert source taint.",
															bullyrook_mispaint);
												}
												Object fluorination_impawn = foochow_untrustworthily;
												try {
													String awide_lab = System
															.getProperty("os.name");
													if (null != awide_lab) {
														if (!awide_lab
																.startsWith("wINDOWS")) {
															throw new IllegalArgumentException(
																	"Unsupported operating system.");
														}
													}
												} catch (IllegalArgumentException mordvinian_chorioadenoma) {
												} finally {
													Tracer.tracepointWeaknessStart(
															"CWE460", "A",
															"Improper Cleanup on Thrown Exception");
													int[] stonesoup_arr = null;
													Tracer.tracepointVariableInt(
															"size",
															((Integer) fluorination_impawn));
													Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
													try {
														CustomConcurrentMergeScheduler.structuralismFrancolin
																.printf("Allocating array of size %d\n",
																		((Integer) fluorination_impawn));
														stonesoup_arr = new int[((Integer) fluorination_impawn)];
													} catch (java.lang.OutOfMemoryError e) {
														Tracer.tracepointError(e
																.getClass()
																.getName()
																+ ": "
																+ e.getMessage());
														stonesoup_arr = new int[100];
													}
													Tracer.tracepointBufferInfo(
															"stonesoup_arr",
															stonesoup_arr.length,
															"Length of stonesoup_arr");
													Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
													try {
														Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
														int i = ((Integer) fluorination_impawn) - 1;
														do {
															stonesoup_arr[i--] = i;
														} while (i > 0);
														Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
													} catch (RuntimeException e) {
														Tracer.tracepointError(e
																.getClass()
																.getName()
																+ ": "
																+ e.getMessage());
														e.printStackTrace(CustomConcurrentMergeScheduler.structuralismFrancolin);
														throw e;
													}
													Tracer.tracepointWeaknessEnd();
												}
											}
										} catch (FileNotFoundException wrinkledHylozoic) {
											throw new RuntimeException(
													"STONESOUP: Could not open file",
													wrinkledHylozoic);
										}
									}
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.structuralismFrancolin
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
