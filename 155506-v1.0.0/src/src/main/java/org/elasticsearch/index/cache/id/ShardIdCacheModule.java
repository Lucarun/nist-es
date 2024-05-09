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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    static PrintStream antiochianSlipproof = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean chemoreceptionRadicula = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (chemoreceptionRadicula.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmplmXjJM_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String unlistening_monocrotism = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (unlistening_monocrotism == null
					|| !unlistening_monocrotism.equals("1")) {
				StonesoupSourceHttpServer lilt_leatheriness = null;
				PipedOutputStream telomiticCatacromyodian = new PipedOutputStream();
				try {
					ShardIdCacheModule.antiochianSlipproof = new PrintStream(
							telomiticCatacromyodian, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException micromethodOverbigness) {
					System.err.printf("Failed to open log file.  %s\n",
							micromethodOverbigness.getMessage());
					ShardIdCacheModule.antiochianSlipproof = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							micromethodOverbigness);
				}
				if (ShardIdCacheModule.antiochianSlipproof != null) {
					try {
						String liegeless_tastefully;
						try {
							lilt_leatheriness = new StonesoupSourceHttpServer(
									8887, telomiticCatacromyodian);
							lilt_leatheriness.start();
							liegeless_tastefully = lilt_leatheriness.getData();
						} catch (IOException proquaestor_predication) {
							lilt_leatheriness = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									proquaestor_predication);
						} catch (Exception papist_loessic) {
							lilt_leatheriness = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									papist_loessic);
						}
						if (null != liegeless_tastefully) {
							int hexateuchal_loyalize;
							try {
								hexateuchal_loyalize = Integer
										.parseInt(liegeless_tastefully);
							} catch (NumberFormatException propupal_scrapple) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										propupal_scrapple);
							}
							int[] tetrazin_gladelike = new int[25];
							tetrazin_gladelike[15] = hexateuchal_loyalize;
							spatangoideanCanonlike(tetrazin_gladelike);
						}
					} finally {
						ShardIdCacheModule.antiochianSlipproof.close();
						if (lilt_leatheriness != null)
							lilt_leatheriness.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	public void spatangoideanCanonlike(int[] propugner_tournefortia) {
		exorbitalMaraca(propugner_tournefortia);
	}

	public void exorbitalMaraca(int[] pregustation_tridecylic) {
		Tracer.tracepointWeaknessStart("CWE774", "A",
				"Allocation of File Descriptors or Handles Without Limits or Throttling");
		FileOutputStream[] stonesoup_sources = new FileOutputStream[pregustation_tridecylic[15]];
		int stonesoup_active_files = 0;
		Tracer.tracepointBufferInfo("stonesoup_sources",
				stonesoup_sources.length, "Length of stonesoup_sources");
		Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
		Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
		for (int stonesoup_counter = 0; stonesoup_counter < pregustation_tridecylic[15]; stonesoup_counter++) {
			try {
				stonesoup_sources[stonesoup_counter] = new FileOutputStream(
						String.format(
								"/opt/stonesoup/workspace/testData/test%10d",
								stonesoup_counter));
			} catch (FileNotFoundException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				ShardIdCacheModule.antiochianSlipproof
						.println("Failed to create new file.");
				e.printStackTrace(ShardIdCacheModule.antiochianSlipproof);
				throw new RuntimeException(e);
			}
			stonesoup_active_files++;
			ShardIdCacheModule.antiochianSlipproof.println(stonesoup_counter);
		}
		Tracer.tracepointVariableInt("stonesoup_active_files",
				stonesoup_active_files);
		Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
		for (int stonesoup_counter = 0; stonesoup_counter < stonesoup_active_files; stonesoup_counter++) {
			try {
				if (stonesoup_sources[stonesoup_counter] != null) {
					stonesoup_sources[stonesoup_counter].close();
				}
			} catch (IOException e) {
				ShardIdCacheModule.antiochianSlipproof
						.println("Failed to close file.");
			}
		}
		Tracer.tracepointWeaknessEnd();
	}
}
