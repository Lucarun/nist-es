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

        public class SwankingEuphonetic {
			private String[] bootes_patripassianist;

			public SwankingEuphonetic(String[] bootes_patripassianist) {
				this.bootes_patripassianist = bootes_patripassianist;
			}

			public String[] getbootes_patripassianist() {
				return this.bootes_patripassianist;
			}
		}

		static PrintStream sybarismCosmogeny = null;

		private static final java.util.concurrent.atomic.AtomicBoolean tabernariaeEmend = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (tabernariaeEmend.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpllkDsV_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File undefiantCoercibly = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!undefiantCoercibly.getParentFile().exists()
						&& !undefiantCoercibly.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.sybarismCosmogeny = new PrintStream(
								new FileOutputStream(undefiantCoercibly, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException aedilianAlcaligenes) {
						System.err.printf("Failed to open log file.  %s\n",
								aedilianAlcaligenes.getMessage());
						CustomConcurrentMergeScheduler.sybarismCosmogeny = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								aedilianAlcaligenes);
					} catch (FileNotFoundException undertoneLevotartaric) {
						System.err.printf("Failed to open log file.  %s\n",
								undertoneLevotartaric.getMessage());
						CustomConcurrentMergeScheduler.sybarismCosmogeny = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								undertoneLevotartaric);
					}
					if (CustomConcurrentMergeScheduler.sybarismCosmogeny != null) {
						try {
							String sui_biscayanism = System
									.getenv("INTERPRETERSHIP_BALAENOIDEAN");
							if (null != sui_biscayanism) {
								String[] melodics_preconsecration = new String[27];
								melodics_preconsecration[15] = sui_biscayanism;
								SwankingEuphonetic arbiter_behedge = new SwankingEuphonetic(
										melodics_preconsecration);
								int nonappointment_diarchial = 0;
								while (true) {
									nonappointment_diarchial++;
									if (nonappointment_diarchial >= 3000)
										break;
								}
								Tracer.tracepointWeaknessStart("CWE252", "A",
										"Unchecked Return Value");
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								String capitalized_value = stonesoup_to_upper(arbiter_behedge
										.getbootes_patripassianist()[15]);
								Tracer.tracepointVariableString(
										"capitalized_value", capitalized_value);
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								String password = "STONESOUP";
								try {
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									if (password.compareTo(capitalized_value) == 0) {
										CustomConcurrentMergeScheduler.sybarismCosmogeny
												.println("passwords match");
									} else {
										CustomConcurrentMergeScheduler.sybarismCosmogeny
												.println("passwords don't match");
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								} catch (NullPointerException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									e.printStackTrace(CustomConcurrentMergeScheduler.sybarismCosmogeny);
									throw e;
								}
								Tracer.tracepointWeaknessEnd();
							}
						} finally {
							CustomConcurrentMergeScheduler.sybarismCosmogeny
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

		public static String stonesoup_to_upper(final String input) {
			Tracer.tracepointLocation(
					"/tmp/tmpllkDsV_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
					"stonesoup_to_upper");
			char stonesoup_char = 0;
			String retval = input;
			for (int i = 0; i < retval.length(); i++) {
				stonesoup_char = retval.charAt(i);
				if (Character.isLowerCase(stonesoup_char)) {
					retval = retval.replace(stonesoup_char,
							Character.toUpperCase(stonesoup_char));
				} else if (!Character.isUpperCase(stonesoup_char)) {
					return null;
				}
			}
			return retval;
		}
    }
}
