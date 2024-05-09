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

        public class SmoreBradyphrenia {
			private Object cytologic_elucidative;

			public SmoreBradyphrenia(Object cytologic_elucidative) {
				this.cytologic_elucidative = cytologic_elucidative;
			}

			public Object getcytologic_elucidative() {
				return this.cytologic_elucidative;
			}
		}

		static PrintStream waxmanOrleanistic = null;

		private static final java.util.concurrent.atomic.AtomicBoolean incivicPeriphlebitic = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (incivicPeriphlebitic.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpGjGsKA_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File pimelicWhisperproof = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!pimelicWhisperproof.getParentFile().exists()
						&& !pimelicWhisperproof.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.waxmanOrleanistic = new PrintStream(
								new FileOutputStream(pimelicWhisperproof, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException prerealizeTylostylar) {
						System.err.printf("Failed to open log file.  %s\n",
								prerealizeTylostylar.getMessage());
						CustomConcurrentMergeScheduler.waxmanOrleanistic = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								prerealizeTylostylar);
					} catch (FileNotFoundException arachneOutknave) {
						System.err.printf("Failed to open log file.  %s\n",
								arachneOutknave.getMessage());
						CustomConcurrentMergeScheduler.waxmanOrleanistic = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								arachneOutknave);
					}
					if (CustomConcurrentMergeScheduler.waxmanOrleanistic != null) {
						try {
							String archprelate_frogging = System
									.getenv("LASTINGNESS_MESSINESE");
							if (null != archprelate_frogging) {
								Object mejorana_reclusion = archprelate_frogging;
								SmoreBradyphrenia puddee_precursive = new SmoreBradyphrenia(
										mejorana_reclusion);
								Tracer.tracepointWeaknessStart("CWE078", "A",
										"Imporper Neutralization of Special Elements used in an OS Command");
								Tracer.tracepointVariableString("value",
										((String) puddee_precursive
												.getcytologic_elucidative()));
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								String stonesoup_proc_cmd = "ls "
										+ ((String) puddee_precursive
												.getcytologic_elucidative());
								Tracer.tracepointVariableString(
										"stonesoup_proc_cmd",
										stonesoup_proc_cmd);
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								java.lang.ProcessBuilder stonesoup_proc_builder = new java.lang.ProcessBuilder(
										"bash", "-c", stonesoup_proc_cmd);
								stonesoup_proc_builder
										.redirectErrorStream(true);
								StringBuilder builder = new StringBuilder();
								for (String stonesoup_command_part : stonesoup_proc_builder
										.command()) {
									builder.append(stonesoup_command_part);
									builder.append(" ");
								}
								Tracer.tracepointVariableString(
										"stonesoup_proc_builder.command()",
										builder.toString());
								java.lang.Process stonesoup_proc = null;
								try {
									Tracer.tracepointMessage("Executing command.");
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									stonesoup_proc = stonesoup_proc_builder
											.start();
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								} catch (IOException ioe) {
									Tracer.tracepointError(ioe.getClass()
											.getName()
											+ ": "
											+ ioe.getMessage());
									CustomConcurrentMergeScheduler.waxmanOrleanistic
											.println("STONESOUP: Failed to open subprocess.");
								}
								if (stonesoup_proc != null) {
									String stonesoup_proc_output_line = null;
									java.io.BufferedReader stonesoup_proc_reader = new java.io.BufferedReader(
											new java.io.InputStreamReader(
													stonesoup_proc
															.getInputStream()));
									try {
										Tracer.tracepointMessage("Reading stdout/stderr from the subprocess.");
										while ((stonesoup_proc_output_line = stonesoup_proc_reader
												.readLine()) != null) {
											CustomConcurrentMergeScheduler.waxmanOrleanistic
													.println(stonesoup_proc_output_line);
										}
									} catch (IOException ioe) {
										Tracer.tracepointError(ioe.getClass()
												.getName()
												+ ": "
												+ ioe.getMessage());
										CustomConcurrentMergeScheduler.waxmanOrleanistic
												.println("STONESOUP: Error reading subprocess output stream.");
									}
									try {
										Tracer.tracepointMessage("Waiting for process to complete.");
										int stonesoup_exit_code = stonesoup_proc
												.waitFor();
										if (stonesoup_exit_code != 0) {
											Tracer.tracepointError("Error in subprocess.");
											Tracer.tracepointVariableInt(
													"stonesoup_exit_code",
													stonesoup_exit_code);
											CustomConcurrentMergeScheduler.waxmanOrleanistic
													.printf("STONESOUP: Error in subprocess.	Return code: %d\n",
															stonesoup_exit_code);
										}
									} catch (java.lang.InterruptedException ie) {
										Tracer.tracepointError(ie.getClass()
												.getName()
												+ ": "
												+ ie.getMessage());
										CustomConcurrentMergeScheduler.waxmanOrleanistic
												.println("STONESOUP: Error waiting for subprocess.");
									}
								}
								Tracer.tracepointWeaknessEnd();
							}
						} finally {
							CustomConcurrentMergeScheduler.waxmanOrleanistic
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
