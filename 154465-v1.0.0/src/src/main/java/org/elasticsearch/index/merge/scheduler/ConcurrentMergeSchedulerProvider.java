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
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

        public class ApotelesmaticNondistinctive<T> {
			private T atrophiated_suprachoroid;

			public ApotelesmaticNondistinctive(T atrophiated_suprachoroid) {
				this.atrophiated_suprachoroid = atrophiated_suprachoroid;
			}

			public T getatrophiated_suprachoroid() {
				return this.atrophiated_suprachoroid;
			}
		}

		static PrintStream abashlesslyScopidae = null;

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

		private static final java.util.concurrent.atomic.AtomicBoolean anthuriumAmbrosine = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (anthuriumAmbrosine.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmp6BLUCz_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				String outdoorness_incident = System
						.getenv("STONESOUP_DISABLE_WEAKNESS");
				if (outdoorness_incident == null
						|| !outdoorness_incident.equals("1")) {
					StonesoupSourceHttpServer westernize_pediastrum = null;
					PipedOutputStream glomerulateIchthyodont = new PipedOutputStream();
					try {
						CustomConcurrentMergeScheduler.abashlesslyScopidae = new PrintStream(
								glomerulateIchthyodont, true, "ISO-8859-1");
					} catch (UnsupportedEncodingException unscottifySuffusedly) {
						System.err.printf("Failed to open log file.  %s\n",
								unscottifySuffusedly.getMessage());
						CustomConcurrentMergeScheduler.abashlesslyScopidae = null;
						throw new RuntimeException(
								"STONESOUP: Failed to create piped print stream.",
								unscottifySuffusedly);
					}
					if (CustomConcurrentMergeScheduler.abashlesslyScopidae != null) {
						try {
							String quenchableness_pathematology;
							try {
								westernize_pediastrum = new StonesoupSourceHttpServer(
										8887, glomerulateIchthyodont);
								westernize_pediastrum.start();
								quenchableness_pathematology = westernize_pediastrum
										.getData();
							} catch (IOException rod_shorewards) {
								westernize_pediastrum = null;
								throw new RuntimeException(
										"STONESOUP: Failed to start HTTP server.",
										rod_shorewards);
							} catch (Exception sandwort_immanes) {
								westernize_pediastrum = null;
								throw new RuntimeException(
										"STONESOUP: Unknown error with HTTP server.",
										sandwort_immanes);
							}
							if (null != quenchableness_pathematology) {
								Object breck_mnemotechnic = quenchableness_pathematology;
								ApotelesmaticNondistinctive<Object> coelicolist_tanha = new ApotelesmaticNondistinctive<Object>(
										breck_mnemotechnic);
								boolean disdiplomatize_deossification = false;
								proruption_sparklet: for (int acylamido_vermiculite = 0; acylamido_vermiculite < 10; acylamido_vermiculite++)
									for (int unsucked_nodicorn = 0; unsucked_nodicorn < 10; unsucked_nodicorn++)
										if (acylamido_vermiculite
												* unsucked_nodicorn == 63) {
											disdiplomatize_deossification = true;
											break proruption_sparklet;
										}
								Tracer.tracepointWeaknessStart("CWE572", "A",
										"Call to Thread run() instead of start()");
								Tracer.tracepointMessage("Creating thread");
								final PrintStream stonesoup_crash_output = CustomConcurrentMergeScheduler.abashlesslyScopidae;
								Thread stonesoup_thread1 = new Thread(
										new HelloRunnable(
												((String) coelicolist_tanha
														.getatrophiated_suprachoroid()),
												CustomConcurrentMergeScheduler.abashlesslyScopidae));
								stonesoup_thread1
										.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
											@Override
											public void uncaughtException(
													Thread t, Throwable e) {
												Tracer.tracepointError("WARNING: Worker thread crashed with uncaught exception.");
												stonesoup_crash_output
														.println("WARNING: Worker thread crashed with uncaught exception.");
												e.printStackTrace(stonesoup_crash_output);
											}
										});
								try {
									Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									stonesoup_thread1.run();
									Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								} catch (RuntimeException e) {
									Tracer.tracepointError("The thread startup raised an excpetion.  "
											+ e.getClass().getName()
											+ ": "
											+ e.getMessage());
									CustomConcurrentMergeScheduler.abashlesslyScopidae
											.println("The thread startup raised an exception.  This should never happen.");
									e.printStackTrace(CustomConcurrentMergeScheduler.abashlesslyScopidae);
									throw e;
								}
								try {
									Tracer.tracepointMessage("Joining thread-01");
									stonesoup_thread1.join();
									Tracer.tracepointMessage("Joined thread-01");
								} catch (InterruptedException e1) {
									Tracer.tracepointError(e1.getClass()
											.getName() + ": " + e1.getMessage());
									CustomConcurrentMergeScheduler.abashlesslyScopidae
											.println("Failed to join the worker thread.");
									e1.printStackTrace(CustomConcurrentMergeScheduler.abashlesslyScopidae);
								} finally {
									CustomConcurrentMergeScheduler.abashlesslyScopidae
											.println("Worker thread terminated.");
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.abashlesslyScopidae
									.close();
							if (westernize_pediastrum != null)
								westernize_pediastrum.stop(true);
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

		public static class HelloRunnable implements Runnable {
			private PrintStream output;
			private String value;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmp6BLUCz_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
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
						"/tmp/tmp6BLUCz_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"HelloRunnable.ctor");
				this.value = value;
				this.output = output;
			}
		}
    }
}
