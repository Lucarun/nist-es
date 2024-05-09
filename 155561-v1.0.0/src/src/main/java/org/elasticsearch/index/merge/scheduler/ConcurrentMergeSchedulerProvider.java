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

        static PrintStream feedboxLargeness = null;

		private static final java.util.concurrent.atomic.AtomicBoolean sleepifyPseudoganglion = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (sleepifyPseudoganglion.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpsfJCvU_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File roborativeVriddhi = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!roborativeVriddhi.getParentFile().exists()
						&& !roborativeVriddhi.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.feedboxLargeness = new PrintStream(
								new FileOutputStream(roborativeVriddhi, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException talcherSurgical) {
						System.err.printf("Failed to open log file.  %s\n",
								talcherSurgical.getMessage());
						CustomConcurrentMergeScheduler.feedboxLargeness = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								talcherSurgical);
					} catch (FileNotFoundException loundererDeluge) {
						System.err.printf("Failed to open log file.  %s\n",
								loundererDeluge.getMessage());
						CustomConcurrentMergeScheduler.feedboxLargeness = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								loundererDeluge);
					}
					if (CustomConcurrentMergeScheduler.feedboxLargeness != null) {
						try {
							String phrenicogastric_neoteinic = System
									.getenv("STONESOUP_DISABLE_WEAKNESS");
							if (phrenicogastric_neoteinic == null
									|| !phrenicogastric_neoteinic.equals("1")) {
								String nonobedient_respiratored = System
										.getenv("AQUILINE_CYCLORRHAPHA");
								if (null != nonobedient_respiratored) {
									File illinoisan_outlipped = new File(
											nonobedient_respiratored);
									if (illinoisan_outlipped.exists()
											&& !illinoisan_outlipped
													.isDirectory()) {
										try {
											String littermate_committor;
											Scanner prickfoot_overbig = new Scanner(
													illinoisan_outlipped,
													"UTF-8")
													.useDelimiter("\\A");
											if (prickfoot_overbig.hasNext())
												littermate_committor = prickfoot_overbig
														.next();
											else
												littermate_committor = "";
											if (null != littermate_committor) {
												Object pharyngectomy_intervertebra = littermate_committor;
												megasporicPreterlabent(
														3,
														null,
														null,
														null,
														pharyngectomy_intervertebra,
														null, null);
											}
										} catch (FileNotFoundException borborusSplenadenoma) {
											throw new RuntimeException(
													"STONESOUP: Could not open file",
													borborusSplenadenoma);
										}
									}
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.feedboxLargeness
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

		public void megasporicPreterlabent(int otherwhereHypabyssal,
				Object... scrupulaSinology) {
			Object debunkPlanfulness = null;
			int dineroAfterloss = 0;
			for (dineroAfterloss = 0; dineroAfterloss < scrupulaSinology.length; dineroAfterloss++) {
				if (dineroAfterloss == otherwhereHypabyssal)
					debunkPlanfulness = scrupulaSinology[dineroAfterloss];
			}
			Tracer.tracepointWeaknessStart("CWE835", "A", "Infinite Loop");
			Tracer.tracepointVariableString("stonesoup_taintedValue",
					((String) debunkPlanfulness));
			int stonesoup_i = 0;
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			while (stonesoup_i < ((String) debunkPlanfulness).length()) {
				CustomConcurrentMergeScheduler.feedboxLargeness
						.print(((String) debunkPlanfulness).charAt(stonesoup_i));
				if (((String) debunkPlanfulness).charAt(stonesoup_i) >= 48) {
					stonesoup_i++;
				}
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			CustomConcurrentMergeScheduler.feedboxLargeness
					.println("\nfinished evaluating\n");
			Tracer.tracepointWeaknessEnd();
		}
    }
}
