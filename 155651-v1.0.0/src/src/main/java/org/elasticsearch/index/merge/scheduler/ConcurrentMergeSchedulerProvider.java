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
import java.math.BigInteger;

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

        private static final int commensurately_phosphomolybdic = 3;

		static PrintStream masculinistPlagiarical = null;

		private static final java.util.concurrent.atomic.AtomicBoolean earthbredFloodometer = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (earthbredFloodometer.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmp8BhlTS_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File griffeOrchestiidae = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!griffeOrchestiidae.getParentFile().exists()
						&& !griffeOrchestiidae.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.masculinistPlagiarical = new PrintStream(
								new FileOutputStream(griffeOrchestiidae, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException lactocitrateLepidoptera) {
						System.err.printf("Failed to open log file.  %s\n",
								lactocitrateLepidoptera.getMessage());
						CustomConcurrentMergeScheduler.masculinistPlagiarical = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								lactocitrateLepidoptera);
					} catch (FileNotFoundException paraganglionGiustina) {
						System.err.printf("Failed to open log file.  %s\n",
								paraganglionGiustina.getMessage());
						CustomConcurrentMergeScheduler.masculinistPlagiarical = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								paraganglionGiustina);
					}
					if (CustomConcurrentMergeScheduler.masculinistPlagiarical != null) {
						try {
							String connex_percnosome = System
									.getenv("STONESOUP_DISABLE_WEAKNESS");
							if (connex_percnosome == null
									|| !connex_percnosome.equals("1")) {
								String rumbowline_malchus = System
										.getenv("SPIRITUOUSNESS_ECTAD");
								if (null != rumbowline_malchus) {
									File lobtail_edictal = new File(
											rumbowline_malchus);
									if (lobtail_edictal.exists()
											&& !lobtail_edictal.isDirectory()) {
										try {
											String splenomalacia_thoroughstem;
											Scanner venefical_brusque = new Scanner(
													lobtail_edictal, "UTF-8")
													.useDelimiter("\\A");
											if (venefical_brusque.hasNext())
												splenomalacia_thoroughstem = venefical_brusque
														.next();
											else
												splenomalacia_thoroughstem = "";
											if (null != splenomalacia_thoroughstem) {
												String[] among_replaceability = new String[13];
												among_replaceability[8] = splenomalacia_thoroughstem;
												String[][] dancingly_tube = new String[11][];
												dancingly_tube[commensurately_phosphomolybdic] = among_replaceability;
												unfeasablyWhitecap(dancingly_tube);
											}
										} catch (FileNotFoundException undrubbedAccrual) {
											throw new RuntimeException(
													"STONESOUP: Could not open file",
													undrubbedAccrual);
										}
									}
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.masculinistPlagiarical
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

		public static void unfeasablyWhitecap(String[][] uneludedAnnalism) {
			Tracer.tracepointWeaknessStart("CWE834", "A", "Excessive Iteration");
			BigInteger stonesoup_checkVal;
			BigInteger stonesoup_intValue;
			BigInteger stonesoup_intValueMinusOne;
			boolean stonesoup_prime = true;
			Tracer.tracepointVariableString("stonesoup_taintedValue",
					uneludedAnnalism[commensurately_phosphomolybdic][8]);
			try {
				stonesoup_checkVal = new BigInteger("2");
				stonesoup_intValue = new BigInteger(
						uneludedAnnalism[commensurately_phosphomolybdic][8]);
				stonesoup_intValueMinusOne = stonesoup_intValue
						.subtract(BigInteger.ONE);
				if (stonesoup_intValue.compareTo(BigInteger.ZERO) > 0) {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					for (; stonesoup_checkVal
							.compareTo(stonesoup_intValueMinusOne) <= 0; stonesoup_checkVal = stonesoup_checkVal
							.add(BigInteger.ONE)) {
						if (stonesoup_intValue.mod(stonesoup_checkVal)
								.compareTo(BigInteger.ZERO) == 0) {
							stonesoup_prime = false;
							CustomConcurrentMergeScheduler.masculinistPlagiarical
									.println("Not Prime");
							break;
						}
					}
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				}
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				CustomConcurrentMergeScheduler.masculinistPlagiarical
						.println("STONESOUP: Input string is not an integer");
			}
			CustomConcurrentMergeScheduler.masculinistPlagiarical
					.println("finished evaluating");
			Tracer.tracepointWeaknessEnd();
		}

		public static void unfeasablyWhitecap() {
			unfeasablyWhitecap(null);
		}
    }
}
