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
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

        private static final int dioptograph_ethmosphenoidal = 19;

		static PrintStream coeffectEntomostraca = null;

		private static final java.util.concurrent.atomic.AtomicBoolean ozokeritRenopericardial = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (ozokeritRenopericardial.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmp7tjsgW_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File electroblastingExfigure = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!electroblastingExfigure.getParentFile().exists()
						&& !electroblastingExfigure.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.coeffectEntomostraca = new PrintStream(
								new FileOutputStream(electroblastingExfigure,
										false), true, "ISO-8859-1");
					} catch (UnsupportedEncodingException usurpativeBefoam) {
						System.err.printf("Failed to open log file.  %s\n",
								usurpativeBefoam.getMessage());
						CustomConcurrentMergeScheduler.coeffectEntomostraca = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								usurpativeBefoam);
					} catch (FileNotFoundException lisetteHenwife) {
						System.err.printf("Failed to open log file.  %s\n",
								lisetteHenwife.getMessage());
						CustomConcurrentMergeScheduler.coeffectEntomostraca = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								lisetteHenwife);
					}
					if (CustomConcurrentMergeScheduler.coeffectEntomostraca != null) {
						try {
							String vermicularia_stupefaction = System
									.getenv("BENEDICITE_SOREHEADEDLY");
							if (null != vermicularia_stupefaction) {
								int ruesomeness_bunker;
								try {
									ruesomeness_bunker = Integer
											.parseInt(vermicularia_stupefaction);
								} catch (NumberFormatException prester_unobstruct) {
									throw new RuntimeException(
											"STONESOUP: Failed to convert source taint.",
											prester_unobstruct);
								}
								int[] homaloptera_campodea = new int[26];
								homaloptera_campodea[dioptograph_ethmosphenoidal] = ruesomeness_bunker;
								folksySalicylous(homaloptera_campodea);
							}
						} finally {
							CustomConcurrentMergeScheduler.coeffectEntomostraca
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

		public void folksySalicylous(int[] montesco_weichselwood) {
			fibrocaseousUntrochaic(montesco_weichselwood);
		}

		public void fibrocaseousUntrochaic(int[] fimbrial_nongravity) {
			Tracer.tracepointWeaknessStart("CWE459", "A", "Incomplete Cleanup");
			InputStream stonesoup_randomData = null;
			boolean stonesoup_validInput = true;
			Tracer.tracepointVariableInt("stonesoup_intValue",
					fimbrial_nongravity[dioptograph_ethmosphenoidal]);
			byte[] stonesoup_randomChars = null;
			try {
				CustomConcurrentMergeScheduler.coeffectEntomostraca
						.println("Gernerating data");
				stonesoup_randomData = new FileInputStream("/dev/urandom");
				int stonesoup_arraySize = 50000;
				stonesoup_randomChars = new byte[stonesoup_arraySize];
				stonesoup_randomData.read(stonesoup_randomChars, 0,
						stonesoup_arraySize);
			} catch (FileNotFoundException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				CustomConcurrentMergeScheduler.coeffectEntomostraca
						.println("Error: /dev/urandom not found");
				stonesoup_validInput = false;
			} catch (IOException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				CustomConcurrentMergeScheduler.coeffectEntomostraca
						.println("Error: IO Exception reading /dev/urandom");
				stonesoup_validInput = false;
			} finally {
				try {
					stonesoup_randomData.close();
				} catch (IOException e) {
					CustomConcurrentMergeScheduler.coeffectEntomostraca
							.println("Error: Cannot close /dev/urandom");
					stonesoup_validInput = false;
				}
			}
			if (stonesoup_validInput) {
				int stonesoup_numFilePaths = 50;
				File[] stonesoup_filePaths = new File[stonesoup_numFilePaths];
				int stonesoup_i = 0;
				OutputStream stonesoup_outputStream = null;
				try {
					CustomConcurrentMergeScheduler.coeffectEntomostraca
							.println("Saving data");
					for (stonesoup_i = 0; stonesoup_i < fimbrial_nongravity[dioptograph_ethmosphenoidal]; stonesoup_i++) {
						stonesoup_filePaths[stonesoup_i
								% stonesoup_numFilePaths] = File
								.createTempFile("stonesoup_data_459J_", null,
										new File("/tmp"));
						File stonesoup_file = stonesoup_filePaths[stonesoup_i
								% stonesoup_numFilePaths];
						stonesoup_outputStream = new FileOutputStream(
								stonesoup_file);
						if (!stonesoup_file.exists()) {
							stonesoup_file.createNewFile();
						}
						stonesoup_outputStream.write(stonesoup_randomChars);
						stonesoup_outputStream.close();
						stonesoup_outputStream = null;
					}
					Tracer.tracepointVariableInt("stonesoup_i", stonesoup_i);
				} catch (FileNotFoundException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					CustomConcurrentMergeScheduler.coeffectEntomostraca
							.println("Error: tmp file  not found");
				} catch (IOException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					CustomConcurrentMergeScheduler.coeffectEntomostraca
							.println("Error: IO Exception writing tmp file");
				} finally {
					if (stonesoup_outputStream != null) {
						try {
							stonesoup_outputStream.close();
						} catch (IOException e) {
							CustomConcurrentMergeScheduler.coeffectEntomostraca
									.println("Error: could not delete output stream");
						}
					}
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					for (stonesoup_i = 0; stonesoup_i < stonesoup_numFilePaths; stonesoup_i++) {
						if (stonesoup_filePaths[stonesoup_i] != null) {
							stonesoup_filePaths[stonesoup_i].delete();
						}
					}
					Tracer.tracepointVariableInt("stonesoup_i", stonesoup_i);
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				}
			}
			Tracer.tracepointWeaknessEnd();
		}
    }
}
