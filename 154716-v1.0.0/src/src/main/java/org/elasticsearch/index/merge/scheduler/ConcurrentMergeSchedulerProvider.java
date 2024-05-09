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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
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

        public class ImprimeMultipinnate<T> {
			private T penthemimeral_counterweighted;

			public ImprimeMultipinnate(T penthemimeral_counterweighted) {
				this.penthemimeral_counterweighted = penthemimeral_counterweighted;
			}

			public T getpenthemimeral_counterweighted() {
				return this.penthemimeral_counterweighted;
			}
		}

		public void escapadeEntomb(int sitient_bobbinet,
				ImprimeMultipinnate<String> primogenous_sanballat) {
			if (sitient_bobbinet > 10) {
				escapadeEntomb(sitient_bobbinet++, primogenous_sanballat);
			}
			Tracer.tracepointWeaknessStart("CWE663", "A",
					"Use of a Non-reentrant Function in a Concurrent Context");
			String stonesoup_substrings[] = primogenous_sanballat
					.getpenthemimeral_counterweighted().split("\\s", 2);
			int stonesoup_qsize = 0;
			if (stonesoup_substrings.length == 2) {
				try {
					stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				} catch (NumberFormatException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					CustomConcurrentMergeScheduler.unguardednessMinatory
							.println("NumberFormatException");
				}
				Tracer.tracepointVariableString("stonesoup_value",
						primogenous_sanballat
								.getpenthemimeral_counterweighted());
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointVariableString("stonesoup_threadInput",
						stonesoup_substrings[1]);
				if (stonesoup_qsize < 0) {
					stonesoup_qsize = 0;
					CustomConcurrentMergeScheduler.unguardednessMinatory
							.println("Qsize should be >=0, setting it to 0.");
				}
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread1 = new Thread(new replaceSymbols(
						stonesoup_qsize,
						CustomConcurrentMergeScheduler.unguardednessMinatory));
				Thread stonesoup_thread2 = new Thread(new toCaps(
						stonesoup_qsize,
						CustomConcurrentMergeScheduler.unguardednessMinatory));
				stonesoup_threadInput = new StringBuilder()
						.append(stonesoup_substrings[1]);
				Tracer.tracepointMessage("Spawning threads.");
				CustomConcurrentMergeScheduler.unguardednessMinatory
						.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				CustomConcurrentMergeScheduler.unguardednessMinatory
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
					CustomConcurrentMergeScheduler.unguardednessMinatory
							.println("Interrupted");
				}
				CustomConcurrentMergeScheduler.unguardednessMinatory
						.println("Info: Threads ended");
			}
			Tracer.tracepointWeaknessEnd();
		}

		static PrintStream unguardednessMinatory = null;

		private static final java.util.concurrent.atomic.AtomicBoolean quenchablenessAtrochous = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (quenchablenessAtrochous.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmp7hnWZV_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File unprofuselyFoxship = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!unprofuselyFoxship.getParentFile().exists()
						&& !unprofuselyFoxship.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.unguardednessMinatory = new PrintStream(
								new FileOutputStream(unprofuselyFoxship, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException utopisticGlasser) {
						System.err.printf("Failed to open log file.  %s\n",
								utopisticGlasser.getMessage());
						CustomConcurrentMergeScheduler.unguardednessMinatory = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								utopisticGlasser);
					} catch (FileNotFoundException disemboguementIdiocratical) {
						System.err.printf("Failed to open log file.  %s\n",
								disemboguementIdiocratical.getMessage());
						CustomConcurrentMergeScheduler.unguardednessMinatory = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								disemboguementIdiocratical);
					}
					if (CustomConcurrentMergeScheduler.unguardednessMinatory != null) {
						try {
							String breechloader_picot = System
									.getenv("WADE_REL");
							if (null != breechloader_picot) {
								ImprimeMultipinnate<String> daddle_relevy = new ImprimeMultipinnate<String>(
										breechloader_picot);
								int extrasensory_compatriotism = 0;
								escapadeEntomb(extrasensory_compatriotism,
										daddle_relevy);
							}
						} finally {
							CustomConcurrentMergeScheduler.unguardednessMinatory
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

		private static ReentrantLock lock = new ReentrantLock();

		private static StringBuilder stonesoup_threadInput;

		static volatile int j;

		public static void arrFunc(int size, String tempfile, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmp7hnWZV_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
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
						"/tmp/tmp7hnWZV_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
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
					arrFunc(size,
							"/opt/stonesoup/workspace/testData/replace.txt",
							output);
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER (1)");
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public replaceSymbols(int size, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmp7hnWZV_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
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
						"/tmp/tmp7hnWZV_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"toCaps.run");
				try {
					lock.lock();
					for (int i = 0; i < stonesoup_threadInput.length(); i++) {
						if (stonesoup_threadInput.charAt(i) >= 'a'
								|| stonesoup_threadInput.charAt(i) <= 'z') {
							stonesoup_threadInput
									.setCharAt(i, (char) (stonesoup_threadInput
											.charAt(i) - ('a' - 'A')));
						}
					}
					lock.unlock();
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE (2)");
					arrFunc(size,
							"/opt/stonesoup/workspace/testData/toCaps.txt",
							output);
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER (2)");
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public toCaps(int size, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmp7hnWZV_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"toCaps.ctor");
				this.size = size;
				this.output = output;
			}
		}
    }
}
