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

        public static interface ITrochlearUnafflicted {
			public void papyrographicOpisthosomal(Object nonblack_cohere);
		}

		public static class DrossinessSubgenerical implements
				ITrochlearUnafflicted {
			@Override
			public void papyrographicOpisthosomal(Object nonblack_cohere) {
				Tracer.tracepointWeaknessStart("CWE036", "A",
						"Absolute Path Traversal");
				java.io.BufferedReader reader = null;
				String valueString = ((String) nonblack_cohere).trim();
				Tracer.tracepointVariableString("value",
						((String) nonblack_cohere));
				Tracer.tracepointVariableString("valueString", valueString);
				if (valueString.length() != 0) {
					Tracer.tracepointMessage("CROSSOVER-PONT: BEFORE");
					if (valueString.startsWith("/")) {
						CustomConcurrentMergeScheduler.phacoceleSulcular
								.println("Error: Not allowed to use absolute path.");
						Tracer.tracepointMessage("CROSSOVER-PONT: AFTER");
					} else {
						Tracer.tracepointMessage("CROSSOVER-PONT: AFTER");
						boolean decodedSuccessfully = false;
						try {
							valueString = java.net.URLDecoder.decode(
									valueString, "UTF-8");
							Tracer.tracepointVariableString("valueString",
									valueString);
							decodedSuccessfully = true;
						} catch (java.io.UnsupportedEncodingException encoding_exc) {
							Tracer.tracepointError(encoding_exc.getClass()
									.getName()
									+ ": "
									+ encoding_exc.getMessage());
							CustomConcurrentMergeScheduler.phacoceleSulcular
									.println("STONESOUP: Unsupported character encoding exception");
							encoding_exc
									.printStackTrace(CustomConcurrentMergeScheduler.phacoceleSulcular);
						}
						if (decodedSuccessfully) {
							java.io.File readPath = new java.io.File(
									valueString);
							if (readPath.isFile()) {
								try {
									java.io.FileInputStream fis = new java.io.FileInputStream(
											readPath);
									reader = new java.io.BufferedReader(
											new java.io.InputStreamReader(fis));
									String line = null;
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									while ((line = reader.readLine()) != null) {
										CustomConcurrentMergeScheduler.phacoceleSulcular
												.println(line);
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								} catch (java.io.FileNotFoundException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									CustomConcurrentMergeScheduler.phacoceleSulcular
											.printf("File \"%s\" does not exist\n",
													readPath.getPath());
								} catch (java.io.IOException ioe) {
									Tracer.tracepointError(ioe.getClass()
											.getName()
											+ ": "
											+ ioe.getMessage());
									CustomConcurrentMergeScheduler.phacoceleSulcular
											.println("Failed to read file.");
								} finally {
									try {
										if (reader != null) {
											reader.close();
										}
									} catch (java.io.IOException e) {
										CustomConcurrentMergeScheduler.phacoceleSulcular
												.println("STONESOUP: Closing file quietly.");
									}
								}
							} else {
								Tracer.tracepointMessage("File does not exist");
								CustomConcurrentMergeScheduler.phacoceleSulcular
										.printf("File \"%s\" does not exist\n",
												readPath.getPath());
							}
						}
					}
				}
				Tracer.tracepointWeaknessEnd();
			}
		}

		static PrintStream phacoceleSulcular = null;

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

		private static final java.util.concurrent.atomic.AtomicBoolean uneffectlessPuttylike = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (uneffectlessPuttylike.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpWoYBkp_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				String thoracometer_unchinked = System
						.getenv("STONESOUP_DISABLE_WEAKNESS");
				if (thoracometer_unchinked == null
						|| !thoracometer_unchinked.equals("1")) {
					StonesoupSourceHttpServer sigillaria_adulteration = null;
					PipedOutputStream outstartHothearted = new PipedOutputStream();
					try {
						CustomConcurrentMergeScheduler.phacoceleSulcular = new PrintStream(
								outstartHothearted, true, "ISO-8859-1");
					} catch (UnsupportedEncodingException aniconismMildewer) {
						System.err.printf("Failed to open log file.  %s\n",
								aniconismMildewer.getMessage());
						CustomConcurrentMergeScheduler.phacoceleSulcular = null;
						throw new RuntimeException(
								"STONESOUP: Failed to create piped print stream.",
								aniconismMildewer);
					}
					if (CustomConcurrentMergeScheduler.phacoceleSulcular != null) {
						try {
							String macroscelia_klanswoman;
							try {
								sigillaria_adulteration = new StonesoupSourceHttpServer(
										8887, outstartHothearted);
								sigillaria_adulteration.start();
								macroscelia_klanswoman = sigillaria_adulteration
										.getData();
							} catch (IOException resparkle_prerecognition) {
								sigillaria_adulteration = null;
								throw new RuntimeException(
										"STONESOUP: Failed to start HTTP server.",
										resparkle_prerecognition);
							} catch (Exception stringentness_telethermometry) {
								sigillaria_adulteration = null;
								throw new RuntimeException(
										"STONESOUP: Unknown error with HTTP server.",
										stringentness_telethermometry);
							}
							if (null != macroscelia_klanswoman) {
								Object protorebel_vancouveria = macroscelia_klanswoman;
								deionizePoy(3, null, null, null,
										protorebel_vancouveria, null, null);
							}
						} finally {
							CustomConcurrentMergeScheduler.phacoceleSulcular
									.close();
							if (sigillaria_adulteration != null)
								sigillaria_adulteration.stop(true);
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

		public void deionizePoy(int preprovisionSuperclass,
				Object... decrementlessCataclysmist) {
			Object sultanianFrequentation = null;
			int decelerometerFlowering = 0;
			for (decelerometerFlowering = 0; decelerometerFlowering < decrementlessCataclysmist.length; decelerometerFlowering++) {
				if (decelerometerFlowering == preprovisionSuperclass)
					sultanianFrequentation = decrementlessCataclysmist[decelerometerFlowering];
			}
			ITrochlearUnafflicted britannia_dorsispinal = new DrossinessSubgenerical();
			britannia_dorsispinal
					.papyrographicOpisthosomal(sultanianFrequentation);
		}
    }
}
