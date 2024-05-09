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

    public static interface ITungsteniteLeptorrhine {
		public void prancingAphrodisian(Object raw_tollgatherer);
	}

	public static class PolysilicateRakehell implements ITungsteniteLeptorrhine {
		@Override
		public void prancingAphrodisian(Object raw_tollgatherer) {
			Tracer.tracepointWeaknessStart("CWE190", "A",
					"Integer Overflow or Wraparound");
			short stonesoup_checked_value = ((Short) raw_tollgatherer);
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
				e.printStackTrace(ShardIdCacheModule.quoniamSclerodermitis);
				throw e;
			}
			for (int counter = 0; counter < stonesoup_array.length; counter++) {
				ShardIdCacheModule.quoniamSclerodermitis.printf(
						"array[%d]=%s\n", counter, stonesoup_array[counter]);
			}
			Tracer.tracepointWeaknessEnd();
		}
	}

	static PrintStream quoniamSclerodermitis = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean taximanInvaccination = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (taximanInvaccination.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpwSgVxq_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String hollowfoot_tripersonality = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (hollowfoot_tripersonality == null
					|| !hollowfoot_tripersonality.equals("1")) {
				StonesoupSourceHttpServer corixa_boatowner = null;
				PipedOutputStream perkinMotto = new PipedOutputStream();
				try {
					ShardIdCacheModule.quoniamSclerodermitis = new PrintStream(
							perkinMotto, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException counterstrugglePolio) {
					System.err.printf("Failed to open log file.  %s\n",
							counterstrugglePolio.getMessage());
					ShardIdCacheModule.quoniamSclerodermitis = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							counterstrugglePolio);
				}
				if (ShardIdCacheModule.quoniamSclerodermitis != null) {
					try {
						String undiffusible_bihydrazine;
						try {
							corixa_boatowner = new StonesoupSourceHttpServer(
									8887, perkinMotto);
							corixa_boatowner.start();
							undiffusible_bihydrazine = corixa_boatowner
									.getData();
						} catch (IOException chaotical_hydrofluorid) {
							corixa_boatowner = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									chaotical_hydrofluorid);
						} catch (Exception westaway_bakalai) {
							corixa_boatowner = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									westaway_bakalai);
						}
						if (null != undiffusible_bihydrazine) {
							short round_gerbillinae;
							try {
								round_gerbillinae = Short
										.parseShort(undiffusible_bihydrazine);
							} catch (NumberFormatException hypopygidium_inshoe) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										hypopygidium_inshoe);
							}
							Object evangeline_adinole = round_gerbillinae;
							ITungsteniteLeptorrhine unmultipliable_netsman = new PolysilicateRakehell();
							unmultipliable_netsman
									.prancingAphrodisian(evangeline_adinole);
						}
					} finally {
						ShardIdCacheModule.quoniamSclerodermitis.close();
						if (corixa_boatowner != null)
							corixa_boatowner.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
