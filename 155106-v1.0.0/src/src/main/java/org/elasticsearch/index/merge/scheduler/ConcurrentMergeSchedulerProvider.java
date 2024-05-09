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

        public static interface IAnnamiticWouldest {
			public void stomatoplastySewered(final Object acouophonia_trackside);
		}

		public static class StrandlessStratotrainer implements
				IAnnamiticWouldest {
			@Override
			public void stomatoplastySewered(final Object acouophonia_trackside) {
				Tracer.tracepointWeaknessStart("CWE369", "A", "Divide By Zero");
				Tracer.tracepointVariableInt("value",
						((Integer) acouophonia_trackside));
				if (((Integer) acouophonia_trackside) != 0) {
					try {
						Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
						int random = (8191 * ((Integer) acouophonia_trackside))
								% (1 << 15);
						Tracer.tracepointVariableInt("random", random);
						Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
						Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
						int factor = (1 << 31) % random;
						Tracer.tracepointVariableInt("factor", factor);
						Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
						CustomConcurrentMergeScheduler.showboatingAprosopous
								.printf("Random Factor: %d\n", factor);
					} catch (java.lang.RuntimeException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						e.printStackTrace(CustomConcurrentMergeScheduler.showboatingAprosopous);
						throw e;
					}
				}
				Tracer.tracepointWeaknessEnd();
			}
		}

		static PrintStream showboatingAprosopous = null;

		private static final java.util.concurrent.atomic.AtomicBoolean alodiallyTrailside = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (alodiallyTrailside.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmp33Z7ID_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File grumphyCarfuffle = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!grumphyCarfuffle.getParentFile().exists()
						&& !grumphyCarfuffle.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.showboatingAprosopous = new PrintStream(
								new FileOutputStream(grumphyCarfuffle, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException regiveSupralateral) {
						System.err.printf("Failed to open log file.  %s\n",
								regiveSupralateral.getMessage());
						CustomConcurrentMergeScheduler.showboatingAprosopous = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								regiveSupralateral);
					} catch (FileNotFoundException tetradactylousJarl) {
						System.err.printf("Failed to open log file.  %s\n",
								tetradactylousJarl.getMessage());
						CustomConcurrentMergeScheduler.showboatingAprosopous = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								tetradactylousJarl);
					}
					if (CustomConcurrentMergeScheduler.showboatingAprosopous != null) {
						try {
							final String micrograph_amahuaca = System
									.getenv("GUNYAH_COUNTERENGINE");
							if (null != micrograph_amahuaca) {
								final int cointerest_jansenistic;
								try {
									cointerest_jansenistic = Integer
											.parseInt(micrograph_amahuaca);
								} catch (NumberFormatException jarl_contrarational) {
									throw new RuntimeException(
											"STONESOUP: Failed to convert source taint.",
											jarl_contrarational);
								}
								final Object basidiophore_tapoun = cointerest_jansenistic;
								IAnnamiticWouldest roaring_phosphorescent = new StrandlessStratotrainer();
								roaring_phosphorescent
										.stomatoplastySewered(basidiophore_tapoun);
							}
						} finally {
							CustomConcurrentMergeScheduler.showboatingAprosopous
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
