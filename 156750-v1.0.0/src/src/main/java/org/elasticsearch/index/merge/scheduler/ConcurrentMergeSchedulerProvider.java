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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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

        static PrintStream brucinaEneuch = null;

		public void nonclasticArterioplania(int abominate_unbuttered,
				int tribromacetic_acrotomous) {
			if (abominate_unbuttered > 10) {
				nonclasticArterioplania(abominate_unbuttered++,
						tribromacetic_acrotomous);
			}
			Tracer.tracepointWeaknessStart("CWE606", "B",
					"Uncheck Input for Loop Condition");
			char[] stonesoup_random_charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
					.toCharArray();
			SecureRandom random = null;
			try {
				random = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				CustomConcurrentMergeScheduler.brucinaEneuch
						.println("STONESOUP: Random generator algorithm does not exist.");
			}
			Tracer.tracepointVariableInt("value", tribromacetic_acrotomous);
			if (random != null) {
				StringBuilder stonesoup_filename = new StringBuilder();
				CustomConcurrentMergeScheduler.brucinaEneuch
						.println("Generating file name");
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				for (int stonesoup_counter = 0; stonesoup_counter < tribromacetic_acrotomous; stonesoup_counter++) {
					stonesoup_filename.append(stonesoup_random_charset[random
							.nextInt(stonesoup_random_charset.length)]);
				}
				Tracer.tracepointVariableString("stonesoup_filename",
						stonesoup_filename.toString());
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				if (stonesoup_filename.length() > 0) {
					File writePath = new File(stonesoup_filename.toString());
					try {
						Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
						writePath.createNewFile();
						Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					} catch (IOException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						CustomConcurrentMergeScheduler.brucinaEneuch
								.println("Failed to create file.");
						CustomConcurrentMergeScheduler.brucinaEneuch
								.println("Error:");
						e.printStackTrace(CustomConcurrentMergeScheduler.brucinaEneuch);
						throw new RuntimeException(
								"Unknown error in filename.", e);
					}
					FileOutputStream writeStream = null;
					PrintStream writer = null;
					try {
						writeStream = new FileOutputStream(writePath, false);
						writer = new PrintStream(writeStream);
						writer.println("/* This is my file */");
					} catch (FileNotFoundException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						CustomConcurrentMergeScheduler.brucinaEneuch
								.println("Failed to create file.");
						e.printStackTrace(CustomConcurrentMergeScheduler.brucinaEneuch);
					} finally {
						if (writer != null) {
							writer.close();
						}
					}
				}
			}
			Tracer.tracepointWeaknessEnd();
		}

		private static final java.util.concurrent.atomic.AtomicBoolean capeHutterites = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (capeHutterites.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpRyQbSP_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File argentalUnpaint = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!argentalUnpaint.getParentFile().exists()
						&& !argentalUnpaint.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.brucinaEneuch = new PrintStream(
								new FileOutputStream(argentalUnpaint, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException stranglingAntitragicus) {
						System.err.printf("Failed to open log file.  %s\n",
								stranglingAntitragicus.getMessage());
						CustomConcurrentMergeScheduler.brucinaEneuch = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								stranglingAntitragicus);
					} catch (FileNotFoundException geanticlineSurcoat) {
						System.err.printf("Failed to open log file.  %s\n",
								geanticlineSurcoat.getMessage());
						CustomConcurrentMergeScheduler.brucinaEneuch = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								geanticlineSurcoat);
					}
					if (CustomConcurrentMergeScheduler.brucinaEneuch != null) {
						try {
							String nidor_trifoliate = System
									.getenv("KETOXIME_PELLAGROUS");
							if (null != nidor_trifoliate) {
								int equangular_lithuanic;
								try {
									equangular_lithuanic = Integer
											.parseInt(nidor_trifoliate);
								} catch (NumberFormatException aletaster_seraphic) {
									throw new RuntimeException(
											"STONESOUP: Failed to convert source taint.",
											aletaster_seraphic);
								}
								int mem_caliber = 0;
								nonclasticArterioplania(mem_caliber,
										equangular_lithuanic);
							}
						} finally {
							CustomConcurrentMergeScheduler.brucinaEneuch
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
