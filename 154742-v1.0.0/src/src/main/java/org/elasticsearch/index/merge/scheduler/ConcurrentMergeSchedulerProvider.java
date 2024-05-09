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
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import javax.xml.bind.DatatypeConverter;

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

        static PrintStream heartsOedemerid = null;

		private static final java.util.concurrent.atomic.AtomicBoolean alphabetistYellowcup = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (alphabetistYellowcup.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpCGQqkG_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File taximanFootsoreness = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!taximanFootsoreness.getParentFile().exists()
						&& !taximanFootsoreness.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.heartsOedemerid = new PrintStream(
								new FileOutputStream(taximanFootsoreness, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException fiftyCephaeline) {
						System.err.printf("Failed to open log file.  %s\n",
								fiftyCephaeline.getMessage());
						CustomConcurrentMergeScheduler.heartsOedemerid = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								fiftyCephaeline);
					} catch (FileNotFoundException alterantSatyrinae) {
						System.err.printf("Failed to open log file.  %s\n",
								alterantSatyrinae.getMessage());
						CustomConcurrentMergeScheduler.heartsOedemerid = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								alterantSatyrinae);
					}
					if (CustomConcurrentMergeScheduler.heartsOedemerid != null) {
						try {
							String wholesalely_arse = System
									.getenv("STONESOUP_DISABLE_WEAKNESS");
							if (wholesalely_arse == null
									|| !wholesalely_arse.equals("1")) {
								String gradgrind_basellaceae = System
										.getenv("RESPECT_LONE");
								if (null != gradgrind_basellaceae) {
									File arborical_planuloid = new File(
											gradgrind_basellaceae);
									if (arborical_planuloid.exists()
											&& !arborical_planuloid
													.isDirectory()) {
										try {
											String disrespecter_sculpturally;
											Scanner carthame_strigula = new Scanner(
													arborical_planuloid,
													"UTF-8")
													.useDelimiter("\\A");
											if (carthame_strigula.hasNext())
												disrespecter_sculpturally = carthame_strigula
														.next();
											else
												disrespecter_sculpturally = "";
											if (null != disrespecter_sculpturally) {
												pharmacopoeialOctopoda(
														3,
														null,
														null,
														null,
														disrespecter_sculpturally,
														null, null);
											}
										} catch (FileNotFoundException sinarchismEllipsoid) {
											throw new RuntimeException(
													"STONESOUP: Could not open file",
													sinarchismEllipsoid);
										}
									}
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.heartsOedemerid
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

		public void pharmacopoeialOctopoda(int roadtrackSteeringly,String... prereckoningCampulitropal){String pinchcrustExostotic=null;int beringedGreekish=0;for (beringedGreekish=0;beringedGreekish < prereckoningCampulitropal.length;beringedGreekish++){if (beringedGreekish == roadtrackSteeringly)pinchcrustExostotic=prereckoningCampulitropal[beringedGreekish];}try {String canaller_daddock=System.getProperty("os.name");if (null != canaller_daddock){if (!canaller_daddock.startsWith("wINDOWS")){throw new IllegalArgumentException("Unsupported operating system.");}}} catch (IllegalArgumentException contortedness_corona){} finally {Tracer.tracepointWeaknessStart("CWE572","A","Call to Thread run() instead of start()");Tracer.tracepointMessage("Creating thread");final PrintStream stonesoup_crash_output=CustomConcurrentMergeScheduler.heartsOedemerid;Thread stonesoup_thread1=new Thread(new HelloRunnable(pinchcrustExostotic,CustomConcurrentMergeScheduler.heartsOedemerid));stonesoup_thread1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){@Override public void uncaughtException(Thread t,Throwable e){Tracer.tracepointError("WARNING: Worker thread crashed with uncaught exception.");stonesoup_crash_output.println("WARNING: Worker thread crashed with uncaught exception.");e.printStackTrace(stonesoup_crash_output);}});try {Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");stonesoup_thread1.run();Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");Tracer.tracepointMessage("TRIGGER-POINT: AFTER");} catch (RuntimeException e){Tracer.tracepointError("The thread startup raised an excpetion.  " + e.getClass().getName()+": "+e.getMessage());CustomConcurrentMergeScheduler.heartsOedemerid.println("The thread startup raised an exception.  This should never happen.");e.printStackTrace(CustomConcurrentMergeScheduler.heartsOedemerid);throw e;}try {Tracer.tracepointMessage("Joining thread-01");stonesoup_thread1.join();Tracer.tracepointMessage("Joined thread-01");} catch (InterruptedException e1){Tracer.tracepointError(e1.getClass().getName() + ": "+e1.getMessage());CustomConcurrentMergeScheduler.heartsOedemerid.println("Failed to join the worker thread.");e1.printStackTrace(CustomConcurrentMergeScheduler.heartsOedemerid);} finally {CustomConcurrentMergeScheduler.heartsOedemerid.println("Worker thread terminated.");}}}

		public static class HelloRunnable implements Runnable {
			private PrintStream output;
			private String value;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpCGQqkG_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"HelloRunnable.run");
				if (this.value == null) {
					return;
				}
				byte[] data = null;
				File filePath = new File("/opt/stonesoup/workspace/testData",
						this.value);
				BufferedInputStream inputStream = null;
				Tracer.tracepointVariableString("value", value);
				if (filePath.exists() && filePath.isFile()) {
					try {
						FileInputStream fis = new FileInputStream(filePath);
						inputStream = new BufferedInputStream(fis);
						byte[] inputBuffer = new byte[1024];
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						int readAmount = 0;
						while ((readAmount = inputStream.read(inputBuffer)) != -1) {
							Tracer.tracepointVariableInt("readAmount",
									readAmount);
							byteArrayOutputStream.write(inputBuffer, 0,
									readAmount);
						}
						data = byteArrayOutputStream.toByteArray();
					} catch (java.io.FileNotFoundException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						output.printf("File \"%s\" does not exist\n",
								filePath.getPath());
					} catch (java.io.IOException ioe) {
						Tracer.tracepointError(ioe.getClass().getName() + ": "
								+ ioe.getMessage());
						output.println("Failed to read file.");
					} finally {
						try {
							if (inputStream != null) {
								inputStream.close();
							}
						} catch (java.io.IOException e) {
							output.println("STONESOUP: Closing file quietly.");
						}
					}
				} else {
					output.printf("File \"%s\" does not exist\n",
							filePath.getPath());
				}
				if (data == null || data.length < 4) {
					return;
				}
				ByteBuffer buffer = ByteBuffer.wrap(data);
				int dataLength = buffer.getInt();
				if (dataLength < 0) {
					return;
				} else if (dataLength == 0) {
					Tracer.tracepointError("Received payload with no data.");
					this.output.println("Received payload with no data.");
					return;
				}
				byte[] payload = new byte[dataLength];
				Tracer.tracepointBufferInfo("payload", payload.length,
						"Length of they payload byte array.");
				Tracer.tracepointBufferInfo("buffer.position",
						buffer.position(), "Position in buffer.");
				buffer.get(payload);
				Tracer.tracepointBufferInfo("buffer.position",
						buffer.position(), "Position in buffer.");
				this.output.printf("Payload (Base64): %s\n",
						DatatypeConverter.printBase64Binary(payload));
			}

			public HelloRunnable(String value, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpCGQqkG_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"HelloRunnable.ctor");
				this.value = value;
				this.output = output;
			}
		}
    }
}
