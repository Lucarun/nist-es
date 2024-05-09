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

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    public class OunceNymphaea<T> {
		private T delegate_chloragogen;

		public OunceNymphaea(T delegate_chloragogen) {
			this.delegate_chloragogen = delegate_chloragogen;
		}

		public T getdelegate_chloragogen() {
			return this.delegate_chloragogen;
		}
	}

	static PrintStream recapacitateCantrip = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean switchgearMasticura = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (switchgearMasticura.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp3nQcJq_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String reallegation_roselike = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (reallegation_roselike == null
					|| !reallegation_roselike.equals("1")) {
				StonesoupSourceHttpServer polyglotwise_circumbasal = null;
				PipedOutputStream previolationUnkaiserlike = new PipedOutputStream();
				try {
					ShardIdCacheModule.recapacitateCantrip = new PrintStream(
							previolationUnkaiserlike, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException cascadianLastage) {
					System.err.printf("Failed to open log file.  %s\n",
							cascadianLastage.getMessage());
					ShardIdCacheModule.recapacitateCantrip = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							cascadianLastage);
				}
				if (ShardIdCacheModule.recapacitateCantrip != null) {
					try {
						String flinkite_platty;
						try {
							polyglotwise_circumbasal = new StonesoupSourceHttpServer(
									8887, previolationUnkaiserlike);
							polyglotwise_circumbasal.start();
							flinkite_platty = polyglotwise_circumbasal
									.getData();
						} catch (IOException transylvanian_inchpin) {
							polyglotwise_circumbasal = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									transylvanian_inchpin);
						} catch (Exception phiroze_undisguised) {
							polyglotwise_circumbasal = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									phiroze_undisguised);
						}
						if (null != flinkite_platty) {
							int nitrosyl_breekums;
							try {
								nitrosyl_breekums = Integer
										.parseInt(flinkite_platty);
							} catch (NumberFormatException presentimental_plutarchically) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										presentimental_plutarchically);
							}
							OunceNymphaea<Integer> melomanic_unfearful = new OunceNymphaea<Integer>(
									nitrosyl_breekums);
							boolean slithering_pungence = false;
							cognominal_supervolute: for (int blockman_subvisible = 0; blockman_subvisible < 10; blockman_subvisible++)
								for (int sahoukar_inquirendo = 0; sahoukar_inquirendo < 10; sahoukar_inquirendo++)
									if (blockman_subvisible
											* sahoukar_inquirendo == 63) {
										slithering_pungence = true;
										break cognominal_supervolute;
									}
							Tracer.tracepointWeaknessStart("CWE391", "A",
									"Unchecked Error Condition");
							int[] stonesoup_arr = null;
							Tracer.tracepointVariableInt("size",
									melomanic_unfearful
											.getdelegate_chloragogen());
							Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
							try {
								ShardIdCacheModule.recapacitateCantrip.printf(
										"Allocating array of size %d\n",
										melomanic_unfearful
												.getdelegate_chloragogen());
								stonesoup_arr = new int[melomanic_unfearful
										.getdelegate_chloragogen()];
							} catch (OutOfMemoryError e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
							}
							Tracer.tracepointBufferInfo("stonesoup_arr",
									(stonesoup_arr == null) ? 0
											: stonesoup_arr.length,
									"Length of stonesoup_arr");
							Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
							try {
								Tracer.tracepointMessage("TRIGGER-PONIT: BEFORE");
								for (int i = 0; i < stonesoup_arr.length; i++) {
									stonesoup_arr[i] = melomanic_unfearful
											.getdelegate_chloragogen() - i;
								}
								Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
							} catch (RuntimeException e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
								e.printStackTrace(ShardIdCacheModule.recapacitateCantrip);
								throw e;
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						ShardIdCacheModule.recapacitateCantrip.close();
						if (polyglotwise_circumbasal != null)
							polyglotwise_circumbasal.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
