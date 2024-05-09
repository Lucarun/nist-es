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

        static PrintStream sunfastOwk = null;

		private static final java.util.concurrent.atomic.AtomicBoolean statesmanlyWeekday = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (statesmanlyWeekday.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpSSgAQP_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File unapprovinglyTersely = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!unapprovinglyTersely.getParentFile().exists()
						&& !unapprovinglyTersely.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.sunfastOwk = new PrintStream(
								new FileOutputStream(unapprovinglyTersely,
										false), true, "ISO-8859-1");
					} catch (UnsupportedEncodingException totoraUnrecreated) {
						System.err.printf("Failed to open log file.  %s\n",
								totoraUnrecreated.getMessage());
						CustomConcurrentMergeScheduler.sunfastOwk = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								totoraUnrecreated);
					} catch (FileNotFoundException ruruAggrandizable) {
						System.err.printf("Failed to open log file.  %s\n",
								ruruAggrandizable.getMessage());
						CustomConcurrentMergeScheduler.sunfastOwk = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								ruruAggrandizable);
					}
					if (CustomConcurrentMergeScheduler.sunfastOwk != null) {
						try {
							final String unsped_lixivious = System
									.getenv("EPOPTES_STINGY");
							if (null != unsped_lixivious) {
								final int temporofrontal_pentaglossal;
								try {
									temporofrontal_pentaglossal = Integer
											.parseInt(unsped_lixivious);
								} catch (NumberFormatException brachydontism_unlionlike) {
									throw new RuntimeException(
											"STONESOUP: Failed to convert source taint.",
											brachydontism_unlionlike);
								}
								final int[] cuba_homozygote = new int[17];
								cuba_homozygote[12] = temporofrontal_pentaglossal;
								Tracer.tracepointWeaknessStart("CWE774", "A",
										"Allocation of File Descriptors or Handles Without Limits or Throttling");
								FileOutputStream[] stonesoup_sources = new FileOutputStream[cuba_homozygote[12]];
								int stonesoup_active_files = 0;
								Tracer.tracepointBufferInfo(
										"stonesoup_sources",
										stonesoup_sources.length,
										"Length of stonesoup_sources");
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
								for (int stonesoup_counter = 0; stonesoup_counter < cuba_homozygote[12]; stonesoup_counter++) {
									try {
										stonesoup_sources[stonesoup_counter] = new FileOutputStream(
												String.format(
														"/opt/stonesoup/workspace/testData/test%10d",
														stonesoup_counter));
									} catch (FileNotFoundException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										CustomConcurrentMergeScheduler.sunfastOwk
												.println("Failed to create new file.");
										e.printStackTrace(CustomConcurrentMergeScheduler.sunfastOwk);
										throw new RuntimeException(e);
									}
									stonesoup_active_files++;
									CustomConcurrentMergeScheduler.sunfastOwk
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
										CustomConcurrentMergeScheduler.sunfastOwk
												.println("Failed to close file.");
									}
								}
								Tracer.tracepointWeaknessEnd();
							}
						} finally {
							CustomConcurrentMergeScheduler.sunfastOwk.close();
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
