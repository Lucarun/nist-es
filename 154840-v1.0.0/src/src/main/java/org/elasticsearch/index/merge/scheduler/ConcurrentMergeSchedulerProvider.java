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

        public class CoronaPostischial {
			private Object fellatio_gaspergou;

			public CoronaPostischial(Object fellatio_gaspergou) {
				this.fellatio_gaspergou = fellatio_gaspergou;
			}

			public Object getfellatio_gaspergou() {
				return this.fellatio_gaspergou;
			}
		}

		static PrintStream psychosarcousSeeking = null;

		private static final java.util.concurrent.atomic.AtomicBoolean decapStereotype = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (decapStereotype.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmp6lbNV7_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File somebodyExpresser = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!somebodyExpresser.getParentFile().exists()
						&& !somebodyExpresser.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.psychosarcousSeeking = new PrintStream(
								new FileOutputStream(somebodyExpresser, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException metasomeImmortalizer) {
						System.err.printf("Failed to open log file.  %s\n",
								metasomeImmortalizer.getMessage());
						CustomConcurrentMergeScheduler.psychosarcousSeeking = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								metasomeImmortalizer);
					} catch (FileNotFoundException loutishPyelographic) {
						System.err.printf("Failed to open log file.  %s\n",
								loutishPyelographic.getMessage());
						CustomConcurrentMergeScheduler.psychosarcousSeeking = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								loutishPyelographic);
					}
					if (CustomConcurrentMergeScheduler.psychosarcousSeeking != null) {
						try {
							String bounteously_acroanesthesia = System
									.getenv("STONESOUP_DISABLE_WEAKNESS");
							if (bounteously_acroanesthesia == null
									|| !bounteously_acroanesthesia.equals("1")) {
								String tubercular_cercolabes = System
										.getenv("SASTEAN_ORTOLAN");
								if (null != tubercular_cercolabes) {
									File thievable_drate = new File(
											tubercular_cercolabes);
									if (thievable_drate.exists()
											&& !thievable_drate.isDirectory()) {
										try {
											String pseudochrysalis_kneeler;
											Scanner frijolillo_archway = new Scanner(
													thievable_drate, "UTF-8")
													.useDelimiter("\\A");
											if (frijolillo_archway.hasNext())
												pseudochrysalis_kneeler = frijolillo_archway
														.next();
											else
												pseudochrysalis_kneeler = "";
											if (null != pseudochrysalis_kneeler) {
												Object split_purge = pseudochrysalis_kneeler;
												CoronaPostischial harrower_boza = new CoronaPostischial(
														split_purge);
												RetronasalMakeress gartering_manius = new RetronasalMakeress();
												gartering_manius
														.pingueDiphylla(harrower_boza);
											}
										} catch (FileNotFoundException charlatanicalCulicine) {
											throw new RuntimeException(
													"STONESOUP: Could not open file",
													charlatanicalCulicine);
										}
									}
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.psychosarcousSeeking
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

		public static class RetronasalMakeress {
			public void pingueDiphylla(CoronaPostischial smegma_coregonine) {
				Tracer.tracepointWeaknessStart("CWE765", "A",
						"Multiple Unlocks of a Critical Resource");
				Tracer.tracepointMessage("Creating thread");
				Thread stonesoup_thread1 = new Thread(new HelloRunnable(
						((String) smegma_coregonine.getfellatio_gaspergou()),
						CustomConcurrentMergeScheduler.psychosarcousSeeking));
				stonesoup_thread1.start();
				try {
					Tracer.tracepointMessage("Joining thread-01");
					stonesoup_thread1.join();
					Tracer.tracepointMessage("Joined thread-01");
				} catch (InterruptedException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					CustomConcurrentMergeScheduler.psychosarcousSeeking
							.println("Interrupted");
				}
				CustomConcurrentMergeScheduler.psychosarcousSeeking
						.println("Info: Threads ended");
				Tracer.tracepointWeaknessEnd();
			}

			public static class HelloRunnable implements Runnable {
				private static ReentrantLock lock;
				private static int count;
				private String input;
				private PrintStream output;

				public int getCount() {
					return count;
				}

				public void run() {
					Tracer.tracepointLocation(
							"/tmp/tmp6lbNV7_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
							"HelloRunnable.run");
					Tracer.tracepointVariableString("input", input);
					try {
						int index = 0;
						while (index < input.length()) {
							char cc = input.charAt(index);
							index++;
							if (cc == '1') {
								Tracer.tracepointMessage("Locking lock");
								Tracer.tracepointVariableInt("index", index);
								lock.lock();
								break;
							}
						}
						Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
						Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
						boolean found1 = false;
						while (index < input.length()) {
							char cc = input.charAt(index);
							index++;
							if (!found1) {
								count++;
							}
							if (cc == '1') {
								Tracer.tracepointMessage("Unlocking lock");
								lock.unlock();
								found1 = true;
							}
						}
						if (lock.isHeldByCurrentThread()) {
							Tracer.tracepointMessage("Unlocking lock");
							lock.unlock();
						}
						Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
						Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
						output.println("Info: Found " + getCount()
								+ " letters between 1 and 1");
					} catch (java.lang.RuntimeException e) {
						e.printStackTrace(output);
						throw e;
					}
				}

				public HelloRunnable(String input, PrintStream output) {
					Tracer.tracepointLocation(
							"/tmp/tmp6lbNV7_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
							"HelloRunnable.ctor");
					lock = new ReentrantLock();
					count = 0;
					this.input = input;
					this.output = output;
				}
			}
		}
    }
}
