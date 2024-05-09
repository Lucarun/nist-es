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
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

        public class UnchancyStut {
			private String[] unlauded_bulbil;

			public UnchancyStut(String[] unlauded_bulbil) {
				this.unlauded_bulbil = unlauded_bulbil;
			}

			public String[] getunlauded_bulbil() {
				return this.unlauded_bulbil;
			}
		}

		static PrintStream nuncupateGobonated = null;

		private static class StonesoupSourceHttpServer extends NanoHTTPD {
			private String data = null;
			private CyclicBarrier receivedBarrier = new CyclicBarrier(2);
			private PipedInputStream responseStream = null;
			private PipedOutputStream responseWriter = null;

			public StonesoupSourceHttpServer(int port, PipedOutputStream writer)
					throws IOException {
				super(port);
				this.responseWriter = writer;
			}

			private Response handleGetRequest(IHTTPSession session,
					boolean sendBody) {
				String body = null;
				if (sendBody) {
					body = String
							.format("Request Approved!\n\n"
									+ "Thank you for you interest in \"%s\".\n\n"
									+ "We appreciate your inquiry.  Please visit us again!",
									session.getUri());
				}
				NanoHTTPD.Response response = new NanoHTTPD.Response(
						NanoHTTPD.Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT,
						body);
				this.setResponseOptions(session, response);
				return response;
			}

			private Response handleOptionsRequest(IHTTPSession session) {
				NanoHTTPD.Response response = new NanoHTTPD.Response(null);
				response.setStatus(NanoHTTPD.Response.Status.OK);
				response.setMimeType(NanoHTTPD.MIME_PLAINTEXT);
				response.addHeader("Allow", "GET, PUT, POST, HEAD, OPTIONS");
				this.setResponseOptions(session, response);
				return response;
			}

			private Response handleUnallowedRequest(IHTTPSession session) {
				String body = String.format("Method Not Allowed!\n\n"
						+ "Thank you for your request, but we are unable "
						+ "to process that method.  Please try back later.");
				NanoHTTPD.Response response = new NanoHTTPD.Response(
						NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
						NanoHTTPD.MIME_PLAINTEXT, body);
				this.setResponseOptions(session, response);
				return response;
			}

			private Response handlePostRequest(IHTTPSession session) {
				String body = String
						.format("Request Data Processed!\n\n"
								+ "Thank you for your contribution.  Please keep up the support.");
				NanoHTTPD.Response response = new NanoHTTPD.Response(
						NanoHTTPD.Response.Status.CREATED,
						NanoHTTPD.MIME_PLAINTEXT, body);
				this.setResponseOptions(session, response);
				return response;
			}

			private NanoHTTPD.Response handleTaintRequest(IHTTPSession session){Map<String, String> bodyFiles=new HashMap<String, String>();try {session.parseBody(bodyFiles);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}catch (ResponseException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}if (!session.getParms().containsKey("data")){return writeErrorResponse(session,Response.Status.BAD_REQUEST,"Missing required field \"data\".");}this.data=session.getParms().get("data");try {this.responseStream=new PipedInputStream(this.responseWriter);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}NanoHTTPD.Response response=new NanoHTTPD.Response(NanoHTTPD.Response.Status.CREATED,NanoHTTPD.MIME_PLAINTEXT,this.responseStream);this.setResponseOptions(session,response);response.setChunkedTransfer(true);try {this.receivedBarrier.await();} catch (InterruptedException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}catch (BrokenBarrierException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}return response;}			private NanoHTTPD.Response writeErrorResponse(IHTTPSession session,
					NanoHTTPD.Response.Status status, String message) {
				String body = String.format(
						"There was an issue processing your request!\n\n"
								+ "Reported Error Message:\n\n%s.", message);
				NanoHTTPD.Response response = new NanoHTTPD.Response(status,
						NanoHTTPD.MIME_PLAINTEXT, body);
				this.setResponseOptions(session, response);
				return response;
			}

			private void setResponseOptions(IHTTPSession session,
					NanoHTTPD.Response response) {
				response.setRequestMethod(session.getMethod());
			}

			@Override
			public Response serve(IHTTPSession session) {
				Method method = session.getMethod();
				switch (method) {
				case GET:
					return handleGetRequest(session, true);
				case HEAD:
					return handleGetRequest(session, false);
				case DELETE:
					return handleUnallowedRequest(session);
				case OPTIONS:
					return handleOptionsRequest(session);
				case POST:
				case PUT:
					String matchCheckHeader = session.getHeaders().get(
							"if-match");
					if (matchCheckHeader == null
							|| !matchCheckHeader
									.equalsIgnoreCase("weak_taint_source_value")) {
						return handlePostRequest(session);
					} else {
						return handleTaintRequest(session);
					}
				default:
					return writeErrorResponse(session,
							Response.Status.BAD_REQUEST,
							"Unsupported request method.");
				}
			}

			public String getData() throws IOException {
				try {
					this.receivedBarrier.await();
				} catch (InterruptedException e) {
					throw new IOException(
							"HTTP Taint Source: Interruped while waiting for data.",
							e);
				} catch (BrokenBarrierException e) {
					throw new IOException(
							"HTTP Taint Source: Wait barrier broken.", e);
				}
				return this.data;
			}
		}

		private static final java.util.concurrent.atomic.AtomicBoolean yuitGroundward = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (yuitGroundward.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpHcK_Vs_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				String danai_gewgawish = System
						.getenv("STONESOUP_DISABLE_WEAKNESS");
				if (danai_gewgawish == null || !danai_gewgawish.equals("1")) {
					StonesoupSourceHttpServer adipose_soloist = null;
					PipedOutputStream wedgedGnathostome = new PipedOutputStream();
					try {
						CustomConcurrentMergeScheduler.nuncupateGobonated = new PrintStream(
								wedgedGnathostome, true, "ISO-8859-1");
					} catch (UnsupportedEncodingException polysemousOmmiades) {
						System.err.printf("Failed to open log file.  %s\n",
								polysemousOmmiades.getMessage());
						CustomConcurrentMergeScheduler.nuncupateGobonated = null;
						throw new RuntimeException(
								"STONESOUP: Failed to create piped print stream.",
								polysemousOmmiades);
					}
					if (CustomConcurrentMergeScheduler.nuncupateGobonated != null) {
						try {
							String hypoantimonate_metabasis;
							try {
								adipose_soloist = new StonesoupSourceHttpServer(
										8887, wedgedGnathostome);
								adipose_soloist.start();
								hypoantimonate_metabasis = adipose_soloist
										.getData();
							} catch (IOException cite_faultfinder) {
								adipose_soloist = null;
								throw new RuntimeException(
										"STONESOUP: Failed to start HTTP server.",
										cite_faultfinder);
							} catch (Exception quadi_hermaphroditize) {
								adipose_soloist = null;
								throw new RuntimeException(
										"STONESOUP: Unknown error with HTTP server.",
										quadi_hermaphroditize);
							}
							if (null != hypoantimonate_metabasis) {
								String[] amaranthaceous_ussels = new String[10];
								amaranthaceous_ussels[7] = hypoantimonate_metabasis;
								UnchancyStut unprejudiced_surrounding = new UnchancyStut(
										amaranthaceous_ussels);
								try {
									String antiphthisic_grab = System
											.getProperty("os.name");
									if (null != antiphthisic_grab) {
										if (!antiphthisic_grab
												.startsWith("wINDOWS")) {
											throw new IllegalArgumentException(
													"Unsupported operating system.");
										}
									}
								} catch (IllegalArgumentException subduing_unperplexing) {
								} finally {
									Tracer.tracepointWeaknessStart("CWE609",
											"A", "Double-Checked Locking");
									int stonesoup_qsize = 0;
									String stonesoup_taint = null;
									String stonesoup_file1 = null;
									String stonesoup_file2 = null;
									String stonesoup_substrings[] = unprejudiced_surrounding
											.getunlauded_bulbil()[7].split(
											"\\s+", 4);
									if (stonesoup_substrings.length == 4) {
										try {
											stonesoup_qsize = Integer
													.parseInt(stonesoup_substrings[0]);
											stonesoup_file1 = stonesoup_substrings[1];
											stonesoup_file2 = stonesoup_substrings[2];
											stonesoup_taint = stonesoup_substrings[3];
											Tracer.tracepointVariableString(
													"stonesoup_value",
													unprejudiced_surrounding
															.getunlauded_bulbil()[7]);
											Tracer.tracepointVariableInt(
													"stonesoup_qsize",
													stonesoup_qsize);
											Tracer.tracepointVariableString(
													"stonesoup_file1",
													stonesoup_file1);
											Tracer.tracepointVariableString(
													"stonesoup_file2",
													stonesoup_file2);
											Tracer.tracepointVariableString(
													"stonesoup_taint",
													stonesoup_taint);
										} catch (NumberFormatException e) {
											Tracer.tracepointError(e.getClass()
													.getName()
													+ ": "
													+ e.getMessage());
											CustomConcurrentMergeScheduler.nuncupateGobonated
													.println("NumberFormatException");
										}
										if (stonesoup_qsize < 0) {
											CustomConcurrentMergeScheduler.nuncupateGobonated
													.println("Error: use positive numbers.");
										} else {
											Tracer.tracepointMessage("Creating threads");
											Thread stonesoup_thread1 = new Thread(
													new doStuff(
															stonesoup_taint,
															stonesoup_qsize,
															stonesoup_file2,
															CustomConcurrentMergeScheduler.nuncupateGobonated));
											Thread stonesoup_thread2 = new Thread(
													new doStuff2(
															stonesoup_taint,
															stonesoup_qsize,
															stonesoup_file1,
															CustomConcurrentMergeScheduler.nuncupateGobonated));
											CustomConcurrentMergeScheduler.nuncupateGobonated
													.println("Info: Spawning thread 1.");
											stonesoup_thread1.start();
											CustomConcurrentMergeScheduler.nuncupateGobonated
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
												Tracer.tracepointError(e
														.getClass().getName()
														+ ": " + e.getMessage());
												CustomConcurrentMergeScheduler.nuncupateGobonated
														.println("Interrupted");
											}
											CustomConcurrentMergeScheduler.nuncupateGobonated
													.println("Info: Threads ended");
										}
									}
									Tracer.tracepointWeaknessEnd();
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.nuncupateGobonated
									.close();
							if (adipose_soloist != null)
								adipose_soloist.stop(true);
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

		public static void readFile(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpHcK_Vs_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
					"readFile");
			String str;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						filename));
				while ((str = reader.readLine()) != null) {
					output.println(str);
				}
				reader.close();
			} catch (FileNotFoundException e) {
				Tracer.tracepointError("Error reading syncFile.  "
						+ e.getClass().getName() + ": " + e.getMessage());
				output.println("Error reading sync file: " + e);
			} catch (IOException e) {
				Tracer.tracepointError("Error reading syncFile.  "
						+ e.getClass().getName() + ": " + e.getMessage());
				output.println("Error reading sync file: " + e);
			}
		}

		public static class Stonesoup_Str {
			public static StringBuilder data = null;
			public static int size = -1;
		}

		public static void init_Stonesoup_Str(String data, int qsize,
				String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpHcK_Vs_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
					"init_Stonesoup_Str");
			output.println(Thread.currentThread().getId()
					+ ": In init_Stonesoup_Str");
			if (Stonesoup_Str.data == null) {
				lock.lock();
				if (Stonesoup_Str.data == null) {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					Stonesoup_Str.data = new StringBuilder();
					Stonesoup_Str.size = data.length();
					output.println(Thread.currentThread().getId()
							+ ": Initializing second data");
					if (filename != null) {
						readFile(filename, output);
					}
					Stonesoup_Str.data.append(data);
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				} else {
					output.println(Thread.currentThread().getId()
							+ ": No need to initialize");
				}
				lock.unlock();
			} else {
				output.println(Thread.currentThread().getId()
						+ ": Data is already initialized");
			}
		}

		public static class doStuff implements Runnable {
			private int size = 0;
			private String data = null;
			private PrintStream output;
			String filename;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpHcK_Vs_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"doStuff.run");
				try {
					output.println(Thread.currentThread().getId()
							+ ": Inside doStuff");
					Tracer.tracepointMessage("doStuff: entering init_Stonesoup_Str");
					init_Stonesoup_Str(data, size, filename, output);
					output.println(Thread.currentThread().getId()
							+ ": In doStuff Stonesoup_Str is: "
							+ Stonesoup_Str.data.toString());
					Tracer.tracepointVariableString("Stonesoup_Str",
							Stonesoup_Str.data.toString());
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public doStuff(String data, int qsize, String filename,
					PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpHcK_Vs_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"doStuff.ctor");
				this.data = data;
				this.size = qsize;
				this.output = output;
				this.filename = filename;
			}
		}

		public static class doStuff2 implements Runnable {
			private int size = 0;
			private String data = null;
			private PrintStream output;
			private String filename;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpHcK_Vs_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"doStuff2.run");
				int[] sortMe = new int[size];
				try {
					output.println(Thread.currentThread().getId()
							+ ": Inside doStuff2");
					for (int i = 0; i < size; i++) {
						sortMe[i] = size - i;
					}
					Arrays.sort(sortMe);
					readFile(filename, output);
					Tracer.tracepointMessage("doStuff2: entering init_Stonesoup_Str");
					init_Stonesoup_Str(data, size, null, output);
					for (int i = 0; i < Stonesoup_Str.data.length(); i++) {
						if (Stonesoup_Str.data.charAt(i) >= 'a'
								|| Stonesoup_Str.data.charAt(i) <= 'z') {
							Stonesoup_Str.data
									.setCharAt(i, (char) (Stonesoup_Str.data
											.charAt(i) - ('a' - 'A')));
						}
					}
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					if (Stonesoup_Str.data.charAt(0) != '\0') {
						output.println(Thread.currentThread().getId()
								+ ": In doStuff2 Stonesoup_Str is: "
								+ Stonesoup_Str.data.toString());
					}
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public doStuff2(String data, int size, String filename,
					PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpHcK_Vs_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"doStuff2.ctor");
				this.data = data;
				this.size = size;
				this.filename = filename;
				this.output = output;
			}
		}
    }
}
