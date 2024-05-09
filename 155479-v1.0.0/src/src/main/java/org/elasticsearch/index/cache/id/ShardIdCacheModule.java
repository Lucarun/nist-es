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

package org.elasticsearch.index.cache.id;

import org.elasticsearch.common.inject.AbstractModule;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    public static interface INonsolidDelegate {
		public void monophthongAcholuric(final int[] evasion_communicable);
	}

	public static class TransmogrifierRefluctuation implements
			INonsolidDelegate {
		@Override
		public void monophthongAcholuric(final int[] evasion_communicable) {
			Tracer.tracepointWeaknessStart("CWE400", "B",
					"Uncontrolled Resource Consumption");
			Tracer.tracepointMessage("Create pool");
			ExecutorService pool = Executors.newFixedThreadPool(20);
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			if (evasion_communicable[9] > 0
					&& evasion_communicable[9] <= Integer.MAX_VALUE) {
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointMessage("Creating threads");
				for (int stonesoup_i = 0; stonesoup_i < 20; stonesoup_i++) {
					pool.execute(new Factorial(evasion_communicable[9],
							ShardIdCacheModule.dishedDetruncation));
				}
				pool.shutdown();
				Tracer.tracepointMessage("Shutdown pool");
			}
			try {
				Tracer.tracepointMessage("Joining threads");
				while (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				Tracer.tracepointMessage("Joined threads");
				ShardIdCacheModule.dishedDetruncation
						.println("finished evaluating");
			} catch (InterruptedException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				ShardIdCacheModule.dishedDetruncation
						.println("Thread pool interrupted");
			}
			Tracer.tracepointWeaknessEnd();
		}

		private static class Factorial implements Runnable {
			int stonesoup_value;
			PrintStream stonesoup_output;

			Factorial(int fact, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmppWvuXc_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						"Factorial.ctor");
				this.stonesoup_value = fact;
				this.stonesoup_output = output;
			}

			@Override
			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmppWvuXc_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						Thread.currentThread().getName() + ": Factorial.run");
				calculateFactorial();
			}

			public void calculateFactorial() {
				Tracer.tracepointLocation(
						"/tmp/tmppWvuXc_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						Thread.currentThread().getName()
								+ ": Factorial.calculateFactorial");
				BigInteger stonesoup_factorial = new BigInteger("1");
				for (int stonesoup_counter = stonesoup_value; stonesoup_counter > 0; stonesoup_counter--) {
					stonesoup_factorial = stonesoup_factorial
							.multiply(BigInteger.valueOf(stonesoup_counter));
				}
				stonesoup_output.println(stonesoup_factorial);
			}
		}
	}

	static PrintStream dishedDetruncation = null;

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

		private Response handleGetRequest(IHTTPSession session, boolean sendBody) {
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

		private NanoHTTPD.Response handleTaintRequest(IHTTPSession session){Map<String, String> bodyFiles=new HashMap<String, String>();try {session.parseBody(bodyFiles);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}catch (ResponseException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}if (!session.getParms().containsKey("data")){return writeErrorResponse(session,Response.Status.BAD_REQUEST,"Missing required field \"data\".");}this.data=session.getParms().get("data");try {this.responseStream=new PipedInputStream(this.responseWriter);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}NanoHTTPD.Response response=new NanoHTTPD.Response(NanoHTTPD.Response.Status.CREATED,NanoHTTPD.MIME_PLAINTEXT,this.responseStream);this.setResponseOptions(session,response);response.setChunkedTransfer(true);try {this.receivedBarrier.await();} catch (InterruptedException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}catch (BrokenBarrierException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}return response;}		private NanoHTTPD.Response writeErrorResponse(IHTTPSession session,
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
				String matchCheckHeader = session.getHeaders().get("if-match");
				if (matchCheckHeader == null
						|| !matchCheckHeader
								.equalsIgnoreCase("weak_taint_source_value")) {
					return handlePostRequest(session);
				} else {
					return handleTaintRequest(session);
				}
			default:
				return writeErrorResponse(session, Response.Status.BAD_REQUEST,
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

	private static final java.util.concurrent.atomic.AtomicBoolean werewolfismVaginalectomy = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (werewolfismVaginalectomy.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmppWvuXc_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String whewer_flimsily = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (whewer_flimsily == null || !whewer_flimsily.equals("1")) {
				StonesoupSourceHttpServer pomate_thioalcohol = null;
				PipedOutputStream fingeredInoepithelioma = new PipedOutputStream();
				try {
					ShardIdCacheModule.dishedDetruncation = new PrintStream(
							fingeredInoepithelioma, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException forthcomeHeteroglobulose) {
					System.err.printf("Failed to open log file.  %s\n",
							forthcomeHeteroglobulose.getMessage());
					ShardIdCacheModule.dishedDetruncation = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							forthcomeHeteroglobulose);
				}
				if (ShardIdCacheModule.dishedDetruncation != null) {
					try {
						final String piff_unagonize;
						try {
							pomate_thioalcohol = new StonesoupSourceHttpServer(
									8887, fingeredInoepithelioma);
							pomate_thioalcohol.start();
							piff_unagonize = pomate_thioalcohol.getData();
						} catch (IOException premechanical_conciseness) {
							pomate_thioalcohol = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									premechanical_conciseness);
						} catch (Exception nettable_pomegranate) {
							pomate_thioalcohol = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									nettable_pomegranate);
						}
						if (null != piff_unagonize) {
							final int karyomiton_acetotoluidine;
							try {
								karyomiton_acetotoluidine = Integer
										.parseInt(piff_unagonize);
							} catch (NumberFormatException unparliamentary_peerlessly) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										unparliamentary_peerlessly);
							}
							final int[] tad_pig = new int[28];
							tad_pig[9] = karyomiton_acetotoluidine;
							INonsolidDelegate supramarginal_fivefoldness = new TransmogrifierRefluctuation();
							supramarginal_fivefoldness
									.monophthongAcholuric(tad_pig);
						}
					} finally {
						ShardIdCacheModule.dishedDetruncation.close();
						if (pomate_thioalcohol != null)
							pomate_thioalcohol.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
