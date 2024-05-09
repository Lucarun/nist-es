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

package org.elasticsearch.index.cache;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.cache.docset.DocSetCacheModule;
import org.elasticsearch.index.cache.filter.FilterCacheModule;
import org.elasticsearch.index.cache.id.IdCacheModule;
import org.elasticsearch.index.cache.query.parser.QueryParserCacheModule;
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
 *
 */
public class IndexCacheModule extends AbstractModule {

    static PrintStream nesotragusPeribranchial = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean mugiloidSplanchnoderm = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (mugiloidSplanchnoderm.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpkTMher_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			String galloon_chaffman = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (galloon_chaffman == null || !galloon_chaffman.equals("1")) {
				StonesoupSourceHttpServer aulacodus_cinclidae = null;
				PipedOutputStream cholerUncontroverted = new PipedOutputStream();
				try {
					IndexCacheModule.nesotragusPeribranchial = new PrintStream(
							cholerUncontroverted, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException pragmaticGenitor) {
					System.err.printf("Failed to open log file.  %s\n",
							pragmaticGenitor.getMessage());
					IndexCacheModule.nesotragusPeribranchial = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							pragmaticGenitor);
				}
				if (IndexCacheModule.nesotragusPeribranchial != null) {
					try {
						final String preworthy_paspalum;
						try {
							aulacodus_cinclidae = new StonesoupSourceHttpServer(
									8887, cholerUncontroverted);
							aulacodus_cinclidae.start();
							preworthy_paspalum = aulacodus_cinclidae.getData();
						} catch (IOException elderwood_salpingomalleus) {
							aulacodus_cinclidae = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									elderwood_salpingomalleus);
						} catch (Exception picrotoxinin_prehandicap) {
							aulacodus_cinclidae = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									picrotoxinin_prehandicap);
						}
						if (null != preworthy_paspalum) {
							boolean selenograph_valuableness = false;
							rhabdosphere_dorsonuchal: for (int dodecarch_shooldarry = 0; dodecarch_shooldarry < 10; dodecarch_shooldarry++)
								for (int hindberry_euphonize = 0; hindberry_euphonize < 10; hindberry_euphonize++)
									if (dodecarch_shooldarry
											* hindberry_euphonize == 63) {
										selenograph_valuableness = true;
										break rhabdosphere_dorsonuchal;
									}
							Tracer.tracepointWeaknessStart("CWE252", "A",
									"Unchecked Return Value");
							Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
							String capitalized_value = stonesoup_to_upper(preworthy_paspalum);
							Tracer.tracepointVariableString(
									"capitalized_value", capitalized_value);
							Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
							String password = "STONESOUP";
							try {
								Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
								if (password.compareTo(capitalized_value) == 0) {
									IndexCacheModule.nesotragusPeribranchial
											.println("passwords match");
								} else {
									IndexCacheModule.nesotragusPeribranchial
											.println("passwords don't match");
								}
								Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
							} catch (NullPointerException e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
								e.printStackTrace(IndexCacheModule.nesotragusPeribranchial);
								throw e;
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						IndexCacheModule.nesotragusPeribranchial.close();
						if (aulacodus_cinclidae != null)
							aulacodus_cinclidae.stop(true);
					}
				}
			}
		}
		new FilterCacheModule(settings).configure(binder());
        new IdCacheModule(settings).configure(binder());
        new QueryParserCacheModule(settings).configure(binder());
        new DocSetCacheModule(settings).configure(binder());

        bind(IndexCache.class).asEagerSingleton();
    }

	public static String stonesoup_to_upper(final String input) {
		Tracer.tracepointLocation(
				"/tmp/tmpkTMher_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
				"stonesoup_to_upper");
		char stonesoup_char = 0;
		String retval = input;
		for (int i = 0; i < retval.length(); i++) {
			stonesoup_char = retval.charAt(i);
			if (Character.isLowerCase(stonesoup_char)) {
				retval = retval.replace(stonesoup_char,
						Character.toUpperCase(stonesoup_char));
			} else if (!Character.isUpperCase(stonesoup_char)) {
				return null;
			}
		}
		return retval;
	}
}
