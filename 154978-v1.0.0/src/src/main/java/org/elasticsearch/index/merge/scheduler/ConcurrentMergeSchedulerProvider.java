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

        public class TernatisectGinner {
			private Object transversally_nonenergic;

			public TernatisectGinner(Object transversally_nonenergic) {
				this.transversally_nonenergic = transversally_nonenergic;
			}

			public Object gettransversally_nonenergic() {
				return this.transversally_nonenergic;
			}
		}

		static PrintStream sparkletAzimide = null;

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

		private static final java.util.concurrent.atomic.AtomicBoolean marinheiroDualism = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (marinheiroDualism.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpgdTYb1_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				String silicide_schistothorax = System
						.getenv("STONESOUP_DISABLE_WEAKNESS");
				if (silicide_schistothorax == null
						|| !silicide_schistothorax.equals("1")) {
					StonesoupSourceHttpServer wuddie_crossopterygian = null;
					PipedOutputStream costiveHeliotypy = new PipedOutputStream();
					try {
						CustomConcurrentMergeScheduler.sparkletAzimide = new PrintStream(
								costiveHeliotypy, true, "ISO-8859-1");
					} catch (UnsupportedEncodingException attestableFrangent) {
						System.err.printf("Failed to open log file.  %s\n",
								attestableFrangent.getMessage());
						CustomConcurrentMergeScheduler.sparkletAzimide = null;
						throw new RuntimeException(
								"STONESOUP: Failed to create piped print stream.",
								attestableFrangent);
					}
					if (CustomConcurrentMergeScheduler.sparkletAzimide != null) {
						try {
							String jestful_faulter;
							try {
								wuddie_crossopterygian = new StonesoupSourceHttpServer(
										8887, costiveHeliotypy);
								wuddie_crossopterygian.start();
								jestful_faulter = wuddie_crossopterygian
										.getData();
							} catch (IOException alternation_arbitrate) {
								wuddie_crossopterygian = null;
								throw new RuntimeException(
										"STONESOUP: Failed to start HTTP server.",
										alternation_arbitrate);
							} catch (Exception shareholder_asker) {
								wuddie_crossopterygian = null;
								throw new RuntimeException(
										"STONESOUP: Unknown error with HTTP server.",
										shareholder_asker);
							}
							if (null != jestful_faulter) {
								short swisher_impersonally;
								try {
									swisher_impersonally = Short
											.parseShort(jestful_faulter);
								} catch (NumberFormatException presley_macropodine) {
									throw new RuntimeException(
											"STONESOUP: Failed to convert source taint.",
											presley_macropodine);
								}
								Object goi_superindulgence = swisher_impersonally;
								TernatisectGinner propylon_nannyberry = new TernatisectGinner(
										goi_superindulgence);
								nipcheeseEquinate(propylon_nannyberry);
							}
						} finally {
							CustomConcurrentMergeScheduler.sparkletAzimide
									.close();
							if (wuddie_crossopterygian != null)
								wuddie_crossopterygian.stop(true);
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

		public static void nipcheeseEquinate(TernatisectGinner cwmTechniphone) {
			Tracer.tracepointWeaknessStart("CWE190", "A",
					"Integer Overflow or Wraparound");
			short stonesoup_checked_value = ((Short) cwmTechniphone
					.gettransversally_nonenergic());
			Tracer.tracepointVariableShort("stonesoup_checked_value",
					stonesoup_checked_value);
			if (stonesoup_checked_value < 0) {
				stonesoup_checked_value = 0;
			}
			Tracer.tracepointVariableShort("stonesoup_checked_value",
					stonesoup_checked_value);
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			short stonesoup_value = (short) (((short) (Short.MAX_VALUE) - (short) 500) + stonesoup_checked_value);
			Tracer.tracepointVariableShort("stonesoup_value", stonesoup_value);
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			stonesoup_value++;
			String[] stonesoup_array = null;
			try {
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointMessage("Trying to allocate a string with size stonesoup_value.");
				stonesoup_array = new String[stonesoup_value];
				Tracer.tracepointBufferInfo("stonesoup_array",
						stonesoup_array.length,
						"Length of newly allocated stonesoup_array");
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				for (short index = 0; index < stonesoup_value; index++) {
					stonesoup_array[index] = Character.toString((char) index);
				}
				Tracer.tracepointMessage("Copy data into stonesoup_array.");
			} catch (java.lang.RuntimeException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				e.printStackTrace(CustomConcurrentMergeScheduler.sparkletAzimide);
				throw e;
			}
			for (int counter = 0; counter < stonesoup_array.length; counter++) {
				CustomConcurrentMergeScheduler.sparkletAzimide.printf(
						"array[%d]=%s\n", counter, stonesoup_array[counter]);
			}
			Tracer.tracepointWeaknessEnd();
		}

		public static void nipcheeseEquinate() {
			nipcheeseEquinate(null);
		}
    }
}
