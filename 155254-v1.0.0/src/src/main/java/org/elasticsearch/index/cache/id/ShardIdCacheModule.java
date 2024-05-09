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

    static PrintStream aurallyBrandied = null;

	public void noncriminalDioscoreaceous(int introductress_ceorl,
			Object loveredPeccadillo) {
		introductress_ceorl--;
		if (introductress_ceorl > 0) {
			upupaThyrocervical(introductress_ceorl, loveredPeccadillo);
		}
	}

	public void upupaThyrocervical(int decemvir_grumose,
			Object loveredPeccadillo) {
		noncriminalDioscoreaceous(decemvir_grumose, loveredPeccadillo);
		Tracer.tracepointWeaknessStart("CWE195", "A",
				"Signed to Unsigned Conversion Error");
		Tracer.tracepointVariableShort("value", ((Short) loveredPeccadillo));
		Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
		int[] stonesoup_array = new int[Math.abs(((Short) loveredPeccadillo))];
		char stonesoup_max_char = (char) ((short) ((Short) loveredPeccadillo));
		Tracer.tracepointBufferInfo("stonesoup_array", stonesoup_array.length,
				"Length of stonesoup_array");
		Tracer.tracepointVariableChar("stonesoup_max_char", stonesoup_max_char);
		Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
		try {
			Tracer.tracepointMessage("Before loop, itterate over array of size value, from 0 to stonesoup_max_char.");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			for (char stonesoup_counter = 0; stonesoup_counter < stonesoup_max_char; stonesoup_counter++) {
				ShardIdCacheModule.aurallyBrandied.printf(
						"Counter value: \"%c\"\n", stonesoup_counter);
				stonesoup_array[stonesoup_counter] = 0;
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		} catch (RuntimeException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			e.printStackTrace(ShardIdCacheModule.aurallyBrandied);
			throw e;
		}
		Tracer.tracepointWeaknessEnd();
	}

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

	private static final java.util.concurrent.atomic.AtomicBoolean creedistHousewife = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (creedistHousewife.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpeo0HDx_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String overling_weigela = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (overling_weigela == null || !overling_weigela.equals("1")) {
				StonesoupSourceHttpServer gibleh_discoblastula = null;
				PipedOutputStream woodlockedMaun = new PipedOutputStream();
				try {
					ShardIdCacheModule.aurallyBrandied = new PrintStream(
							woodlockedMaun, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException nolitionUpheaval) {
					System.err.printf("Failed to open log file.  %s\n",
							nolitionUpheaval.getMessage());
					ShardIdCacheModule.aurallyBrandied = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							nolitionUpheaval);
				}
				if (ShardIdCacheModule.aurallyBrandied != null) {
					try {
						String precut_superordain;
						try {
							gibleh_discoblastula = new StonesoupSourceHttpServer(
									8887, woodlockedMaun);
							gibleh_discoblastula.start();
							precut_superordain = gibleh_discoblastula.getData();
						} catch (IOException hunlike_demisemitone) {
							gibleh_discoblastula = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									hunlike_demisemitone);
						} catch (Exception simplicist_immelodious) {
							gibleh_discoblastula = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									simplicist_immelodious);
						}
						if (null != precut_superordain) {
							short taoism_boilinglike;
							try {
								taoism_boilinglike = Short
										.parseShort(precut_superordain);
							} catch (NumberFormatException helen_unmimicked) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										helen_unmimicked);
							}
							Object pulmometry_discastle = taoism_boilinglike;
							semicylindricSymptom(3, null, null, null,
									pulmometry_discastle, null, null);
						}
					} finally {
						ShardIdCacheModule.aurallyBrandied.close();
						if (gibleh_discoblastula != null)
							gibleh_discoblastula.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	public void semicylindricSymptom(int hackedCataleptic,
			Object... wesseltonRaddleman) {
		Object loveredPeccadillo = null;
		int unserenadedEjicient = 0;
		for (unserenadedEjicient = 0; unserenadedEjicient < wesseltonRaddleman.length; unserenadedEjicient++) {
			if (unserenadedEjicient == hackedCataleptic)
				loveredPeccadillo = wesseltonRaddleman[unserenadedEjicient];
		}
		int annullation_subdealer = 2;
		noncriminalDioscoreaceous(annullation_subdealer, loveredPeccadillo);
	}
}
