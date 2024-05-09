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

        static PrintStream cloddilyCornuated = null;

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

		private static final java.util.concurrent.atomic.AtomicBoolean negotiatrixGalvanize = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (negotiatrixGalvanize.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpP_WDNt_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				String zipper_parma = System
						.getenv("STONESOUP_DISABLE_WEAKNESS");
				if (zipper_parma == null || !zipper_parma.equals("1")) {
					StonesoupSourceHttpServer lichenological_bloomage = null;
					PipedOutputStream mosstrooperUrbanely = new PipedOutputStream();
					try {
						CustomConcurrentMergeScheduler.cloddilyCornuated = new PrintStream(
								mosstrooperUrbanely, true, "ISO-8859-1");
					} catch (UnsupportedEncodingException hydromanticSuperreflection) {
						System.err.printf("Failed to open log file.  %s\n",
								hydromanticSuperreflection.getMessage());
						CustomConcurrentMergeScheduler.cloddilyCornuated = null;
						throw new RuntimeException(
								"STONESOUP: Failed to create piped print stream.",
								hydromanticSuperreflection);
					}
					if (CustomConcurrentMergeScheduler.cloddilyCornuated != null) {
						try {
							final String nonenclosure_stiff;
							try {
								lichenological_bloomage = new StonesoupSourceHttpServer(
										8887, mosstrooperUrbanely);
								lichenological_bloomage.start();
								nonenclosure_stiff = lichenological_bloomage
										.getData();
							} catch (IOException unhalsed_katakinetomeric) {
								lichenological_bloomage = null;
								throw new RuntimeException(
										"STONESOUP: Failed to start HTTP server.",
										unhalsed_katakinetomeric);
							} catch (Exception adorally_tat) {
								lichenological_bloomage = null;
								throw new RuntimeException(
										"STONESOUP: Unknown error with HTTP server.",
										adorally_tat);
							}
							if (null != nonenclosure_stiff) {
								final String[] pococurantish_movableness = new String[16];
								pococurantish_movableness[12] = nonenclosure_stiff;
								uplandishRubstone(pococurantish_movableness);
							}
						} finally {
							CustomConcurrentMergeScheduler.cloddilyCornuated
									.close();
							if (lichenological_bloomage != null)
								lichenological_bloomage.stop(true);
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

		public static void uplandishRubstone(
				final String[] papulopustularThackerayana) {
			Tracer.tracepointWeaknessStart("CWE414", "A", "Missing Lock Check");
			int stonesoup_qsize = 0;
			String stonesoup_taint = null;
			String stonesoup_file1 = null;
			String stonesoup_file2 = null;
			String stonesoup_substrings[] = papulopustularThackerayana[12]
					.split("\\s+", 4);
			if (stonesoup_substrings.length == 4) {
				try {
					stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
					stonesoup_file1 = stonesoup_substrings[1];
					stonesoup_file2 = stonesoup_substrings[2];
					stonesoup_taint = stonesoup_substrings[3];
					Tracer.tracepointVariableString("stonesoup_value",
							papulopustularThackerayana[12]);
					Tracer.tracepointVariableInt("stonesoup_qsize",
							stonesoup_qsize);
					Tracer.tracepointVariableString("stonesoup_file1",
							stonesoup_file1);
					Tracer.tracepointVariableString("stonesoup_file2",
							stonesoup_file2);
					Tracer.tracepointVariableString("stonesoup_taint",
							stonesoup_taint);
				} catch (NumberFormatException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					CustomConcurrentMergeScheduler.cloddilyCornuated
							.println("NumberFormatException");
				}
				if (stonesoup_qsize < 0) {
					CustomConcurrentMergeScheduler.cloddilyCornuated
							.println("Error: use positive numbers.");
				} else {
					Tracer.tracepointMessage("Creating threads");
					Thread stonesoup_thread1 = new Thread(new toCap(
							stonesoup_qsize, stonesoup_file1,
							CustomConcurrentMergeScheduler.cloddilyCornuated));
					Thread stonesoup_thread2 = new Thread(new delNonAlpha(
							stonesoup_file2,
							CustomConcurrentMergeScheduler.cloddilyCornuated));
					stonesoup_threadInput = new StringBuilder()
							.append(stonesoup_taint);
					CustomConcurrentMergeScheduler.cloddilyCornuated
							.println("Info: Spawning thread 1.");
					stonesoup_thread1.start();
					CustomConcurrentMergeScheduler.cloddilyCornuated
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
						CustomConcurrentMergeScheduler.cloddilyCornuated
								.println("Interrupted");
					}
					CustomConcurrentMergeScheduler.cloddilyCornuated
							.println("Info: Threads ended");
				}
			}
			Tracer.tracepointWeaknessEnd();
		}

		public static void uplandishRubstone() {
			uplandishRubstone(null);
		}

		private static ReentrantLock lock = new ReentrantLock();

		private static StringBuilder stonesoup_threadInput;

		public static void readFile(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpP_WDNt_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
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

		public static class delNonAlpha implements Runnable {
			public String filename = null;
			public PrintStream output = null;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpP_WDNt_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"delNonAlpha.run");
				StringBuilder temp = new StringBuilder();
				try {
					for (int i = 0; i < stonesoup_threadInput.length(); i++) {
						if (Character.isLetter(stonesoup_threadInput.charAt(i))) {
							temp.append(stonesoup_threadInput.charAt(i));
						}
					}
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					stonesoup_threadInput = null;
					Tracer.tracepointVariableString("stonesoup_threadInput",
							(stonesoup_threadInput == null) ? "(null)"
									: stonesoup_threadInput.toString());
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
					readFile(filename, output);
					stonesoup_threadInput = temp;
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public delNonAlpha(String filename, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpP_WDNt_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"delNonAlpha.ctor");
				this.filename = filename;
				this.output = output;
			}
		}

		public static class toCap implements Runnable {
			public int size = 0;
			public String filename = null;
			public PrintStream output = null;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpP_WDNt_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"toCap.run");
				try {
					int[] sortMe = new int[size];
					lock.lock();
					for (int i = 0; i < size; i++) {
						sortMe[i] = size - i;
					}
					Arrays.sort(sortMe);
					readFile(filename, output);
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					stonesoup_threadInput = new StringBuilder()
							.append(stonesoup_threadInput.toString()
									.toUpperCase());
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					lock.unlock();
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public toCap(int size, String filename, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpP_WDNt_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"toCap.ctor");
				this.size = size;
				this.filename = filename;
				this.output = output;
			}
		}
    }
}
