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

        public class JacaminAfterburning<T> {
			private T paidologist_flaglike;

			public JacaminAfterburning(T paidologist_flaglike) {
				this.paidologist_flaglike = paidologist_flaglike;
			}

			public T getpaidologist_flaglike() {
				return this.paidologist_flaglike;
			}
		}

		static PrintStream exculpativeRadiatoporose = null;

		private static final java.util.concurrent.atomic.AtomicBoolean currawangIndecorous = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (currawangIndecorous.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpb4MKGn_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File concealedlyPhenomenistic = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!concealedlyPhenomenistic.getParentFile().exists()
						&& !concealedlyPhenomenistic.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.exculpativeRadiatoporose = new PrintStream(
								new FileOutputStream(concealedlyPhenomenistic,
										false), true, "ISO-8859-1");
					} catch (UnsupportedEncodingException vituperativeUnempowered) {
						System.err.printf("Failed to open log file.  %s\n",
								vituperativeUnempowered.getMessage());
						CustomConcurrentMergeScheduler.exculpativeRadiatoporose = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								vituperativeUnempowered);
					} catch (FileNotFoundException buzzwigAteeter) {
						System.err.printf("Failed to open log file.  %s\n",
								buzzwigAteeter.getMessage());
						CustomConcurrentMergeScheduler.exculpativeRadiatoporose = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								buzzwigAteeter);
					}
					if (CustomConcurrentMergeScheduler.exculpativeRadiatoporose != null) {
						try {
							String uropeltidae_resister = System
									.getenv("STONESOUP_DISABLE_WEAKNESS");
							if (uropeltidae_resister == null
									|| !uropeltidae_resister.equals("1")) {
								String sexton_precedentary = System
										.getenv("ALLABUTA_FLUNKYISM");
								if (null != sexton_precedentary) {
									File thronger_kilodyne = new File(
											sexton_precedentary);
									if (thronger_kilodyne.exists()
											&& !thronger_kilodyne.isDirectory()) {
										try {
											String aulae_atellan;
											Scanner patchword_squiffer = new Scanner(
													thronger_kilodyne, "UTF-8")
													.useDelimiter("\\A");
											if (patchword_squiffer.hasNext())
												aulae_atellan = patchword_squiffer
														.next();
											else
												aulae_atellan = "";
											if (null != aulae_atellan) {
												int paca_sabbatarianism;
												try {
													paca_sabbatarianism = Integer
															.parseInt(aulae_atellan);
												} catch (NumberFormatException foremessenger_bemedaled) {
													throw new RuntimeException(
															"STONESOUP: Failed to convert source taint.",
															foremessenger_bemedaled);
												}
												Object phorometry_cholangioitis = paca_sabbatarianism;
												JacaminAfterburning<Object> canoodler_utmostness = new JacaminAfterburning<Object>(
														phorometry_cholangioitis);
												boolean yukian_debonnaire = false;
												squibling_persecute: for (int poulteress_samogonka = 0; poulteress_samogonka < 10; poulteress_samogonka++)
													for (int dispersively_pickleweed = 0; dispersively_pickleweed < 10; dispersively_pickleweed++)
														if (poulteress_samogonka
																* dispersively_pickleweed == 63) {
															yukian_debonnaire = true;
															break squibling_persecute;
														}
												Tracer.tracepointWeaknessStart(
														"CWE774", "A",
														"Allocation of File Descriptors or Handles Without Limits or Throttling");
												FileOutputStream[] stonesoup_sources = new FileOutputStream[((Integer) canoodler_utmostness
														.getpaidologist_flaglike())];
												int stonesoup_active_files = 0;
												Tracer.tracepointBufferInfo(
														"stonesoup_sources",
														stonesoup_sources.length,
														"Length of stonesoup_sources");
												Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
												Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
												for (int stonesoup_counter = 0; stonesoup_counter < ((Integer) canoodler_utmostness
														.getpaidologist_flaglike()); stonesoup_counter++) {
													try {
														stonesoup_sources[stonesoup_counter] = new FileOutputStream(
																String.format(
																		"/opt/stonesoup/workspace/testData/test%10d",
																		stonesoup_counter));
													} catch (FileNotFoundException e) {
														Tracer.tracepointError(e
																.getClass()
																.getName()
																+ ": "
																+ e.getMessage());
														CustomConcurrentMergeScheduler.exculpativeRadiatoporose
																.println("Failed to create new file.");
														e.printStackTrace(CustomConcurrentMergeScheduler.exculpativeRadiatoporose);
														throw new RuntimeException(
																e);
													}
													stonesoup_active_files++;
													CustomConcurrentMergeScheduler.exculpativeRadiatoporose
															.println(stonesoup_counter);
												}
												Tracer.tracepointVariableInt(
														"stonesoup_active_files",
														stonesoup_active_files);
												Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
												Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
												for (int stonesoup_counter = 0; stonesoup_counter < stonesoup_active_files; stonesoup_counter++) {
													try {
														if (stonesoup_sources[stonesoup_counter] != null) {
															stonesoup_sources[stonesoup_counter]
																	.close();
														}
													} catch (IOException e) {
														CustomConcurrentMergeScheduler.exculpativeRadiatoporose
																.println("Failed to close file.");
													}
												}
												Tracer.tracepointWeaknessEnd();
											}
										} catch (FileNotFoundException interruptednessFractionlet) {
											throw new RuntimeException(
													"STONESOUP: Could not open file",
													interruptednessFractionlet);
										}
									}
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.exculpativeRadiatoporose
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
