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
import java.util.concurrent.locks.ReentrantLock;

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

        static PrintStream mycologistAnkylotic = null;

		public void inimicableTimeservingness(int fictileness_gamostely,
				final String[] monazine_arbitragist) {
			if (fictileness_gamostely > 10) {
				inimicableTimeservingness(fictileness_gamostely++,
						monazine_arbitragist);
			}
			Tracer.tracepointWeaknessStart("CWE833", "A", "Deadlock");
			Tracer.tracepointVariableString("stonesoup_value",
					monazine_arbitragist[0]);
			boolean stonesoup_upper = firstIsUpper(monazine_arbitragist[0]);
			ReentrantLock stonesoup_lock = null;
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			if (stonesoup_upper) {
				Tracer.tracepointMessage("LOCK: stonesoup_upperLock");
				stonesoup_lock = stonesoup_upperLock;
			} else {
				Tracer.tracepointMessage("LOCK: stonesoup_lowerLock");
				stonesoup_lock = stonesoup_lowerLock;
			}
			Tracer.tracepointMessage("Locking lock");
			stonesoup_lock.lock();
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			try {
				Tracer.tracepointMessage("Creating thread");
				Thread stonesoup_thread1 = new Thread(new CountUpper(
						monazine_arbitragist[0],
						CustomConcurrentMergeScheduler.mycologistAnkylotic));
				stonesoup_thread1.start();
				for (int ii = 0; ii < monazine_arbitragist[0].length(); ii++) {
					if (stonesoup_upper
							&& Character.isUpperCase(monazine_arbitragist[0]
									.charAt(ii))) {
						stonesoup_upperInt += 1;
					} else if (!stonesoup_upper
							&& !Character.isUpperCase(monazine_arbitragist[0]
									.charAt(ii))) {
						stonesoup_lowerInt += 1;
					}
				}
				try {
					Tracer.tracepointMessage("Joining thread-01");
					stonesoup_thread1.join();
					Tracer.tracepointMessage("Joined thread-01");
				} catch (InterruptedException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					CustomConcurrentMergeScheduler.mycologistAnkylotic
							.println("Interrupted");
				}
			} finally {
				Tracer.tracepointMessage("Unlocking lock");
				stonesoup_lock.unlock();
			}
			CustomConcurrentMergeScheduler.mycologistAnkylotic
					.println("finished evaluating");
			CustomConcurrentMergeScheduler.mycologistAnkylotic
					.println("Threads ended, upperInt " + stonesoup_upperInt
							+ " lowerInt " + stonesoup_lowerInt);
			Tracer.tracepointWeaknessEnd();
		}

		private static final java.util.concurrent.atomic.AtomicBoolean enlighteningAchillobursitis = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (enlighteningAchillobursitis.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpiEgXpQ_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File unbeheadedUneloping = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!unbeheadedUneloping.getParentFile().exists()
						&& !unbeheadedUneloping.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.mycologistAnkylotic = new PrintStream(
								new FileOutputStream(unbeheadedUneloping, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException hyracotherianSubrhombic) {
						System.err.printf("Failed to open log file.  %s\n",
								hyracotherianSubrhombic.getMessage());
						CustomConcurrentMergeScheduler.mycologistAnkylotic = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								hyracotherianSubrhombic);
					} catch (FileNotFoundException bacchanteWareless) {
						System.err.printf("Failed to open log file.  %s\n",
								bacchanteWareless.getMessage());
						CustomConcurrentMergeScheduler.mycologistAnkylotic = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								bacchanteWareless);
					}
					if (CustomConcurrentMergeScheduler.mycologistAnkylotic != null) {
						try {
							final String eberthella_screechingly = System
									.getenv("HYPOCARPIUM_UNDERLEVER");
							if (null != eberthella_screechingly) {
								final String[] promptly_unpruned = new String[8];
								promptly_unpruned[0] = eberthella_screechingly;
								int penthemimeris_tynd = 0;
								inimicableTimeservingness(penthemimeris_tynd,
										promptly_unpruned);
							}
						} finally {
							CustomConcurrentMergeScheduler.mycologistAnkylotic
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

		private static ReentrantLock stonesoup_lowerLock = new ReentrantLock();

		private static ReentrantLock stonesoup_upperLock = new ReentrantLock();

		private static int stonesoup_lowerInt = 0;

		private static int stonesoup_upperInt = 0;

		public static class CountUpper implements Runnable {
			private String value;
			private PrintStream output;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpiEgXpQ_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CountUpper.run");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointMessage("Locking lock");
				stonesoup_upperLock.lock();
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				try {
					for (int ii = 0; ii < value.length(); ii++) {
						if (Character.isUpperCase(value.charAt(ii))) {
							stonesoup_upperInt += 1;
						}
					}
				} finally {
					Tracer.tracepointMessage("Unlocking lock");
					stonesoup_upperLock.unlock();
				}
				output.println("Info: Thread ending, upperInt "
						+ stonesoup_upperInt);
			}

			public CountUpper(String value, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpiEgXpQ_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CountUpper.ctor");
				this.value = value;
				this.output = output;
			}
		}

		private static boolean firstIsUpper(String value) {
			Tracer.tracepointLocation(
					"/tmp/tmpiEgXpQ_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
					"firstIsUpper");
			return (Character.isUpperCase(value.charAt(0)));
		}
    }
}
