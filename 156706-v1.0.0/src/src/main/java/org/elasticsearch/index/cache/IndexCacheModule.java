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
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    static PrintStream incorrectionBearlet = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean pestfulHarmonial = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (pestfulHarmonial.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpy08eUc_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			String monodactyl_uncankered = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (monodactyl_uncankered == null
					|| !monodactyl_uncankered.equals("1")) {
				StonesoupSourceHttpServer oometry_gaun = null;
				PipedOutputStream handkerchieffulStook = new PipedOutputStream();
				try {
					IndexCacheModule.incorrectionBearlet = new PrintStream(
							handkerchieffulStook, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException spoliumMicrostructural) {
					System.err.printf("Failed to open log file.  %s\n",
							spoliumMicrostructural.getMessage());
					IndexCacheModule.incorrectionBearlet = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							spoliumMicrostructural);
				}
				if (IndexCacheModule.incorrectionBearlet != null) {
					try {
						final String thumbtack_barbiton;
						try {
							oometry_gaun = new StonesoupSourceHttpServer(8887,
									handkerchieffulStook);
							oometry_gaun.start();
							thumbtack_barbiton = oometry_gaun.getData();
						} catch (IOException excogitative_isthmus) {
							oometry_gaun = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									excogitative_isthmus);
						} catch (Exception eucalypteol_crow) {
							oometry_gaun = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									eucalypteol_crow);
						}
						if (null != thumbtack_barbiton) {
							final Object tantalian_porkburger = thumbtack_barbiton;
							Tracer.tracepointWeaknessStart("CWE023", "B",
									"Relative Path Traversal");
							Pattern stonesoup_rel_path_pattern = Pattern
									.compile("(^|/)\\.\\.?/");
							java.io.BufferedReader reader = null;
							String valueString = ((String) tantalian_porkburger)
									.trim();
							Tracer.tracepointVariableString("value",
									((String) tantalian_porkburger));
							Tracer.tracepointVariableString("valueString",
									valueString);
							if (valueString.length() != 0) {
								Matcher rel_path_match = stonesoup_rel_path_pattern
										.matcher(valueString);
								if (rel_path_match.find()) {
									IndexCacheModule.incorrectionBearlet
											.println("Path traversal identified, discarding request.");
								} else {
									String decoded = null;
									try {
										Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
										decoded = java.net.URLDecoder.decode(
												valueString, "UTF-8");
										Tracer.tracepointVariableString(
												"decoded", decoded);
										Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
									} catch (java.io.UnsupportedEncodingException e) {
										decoded = null;
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										IndexCacheModule.incorrectionBearlet
												.println("STONESOUP: Character encoding not support for URLDecode.");
										e.printStackTrace(IndexCacheModule.incorrectionBearlet);
									}
									if (decoded != null) {
										File readPath = new File(decoded);
										Tracer.tracepointVariableString(
												"readPath.getPath()",
												readPath.getPath());
										if (readPath.isFile()) {
											try {
												java.io.FileInputStream fis = new java.io.FileInputStream(
														readPath);
												reader = new java.io.BufferedReader(
														new java.io.InputStreamReader(
																fis));
												String line = null;
												Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
												while ((line = reader
														.readLine()) != null) {
													IndexCacheModule.incorrectionBearlet
															.println(line);
												}
												Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
											} catch (java.io.FileNotFoundException e) {
												Tracer.tracepointError(e
														.getClass().getName()
														+ ": " + e.getMessage());
												IndexCacheModule.incorrectionBearlet
														.printf("File \"%s\" does not exist\n",
																readPath.getPath());
											} catch (java.io.IOException ioe) {
												Tracer.tracepointError(ioe
														.getClass().getName()
														+ ": "
														+ ioe.getMessage());
												IndexCacheModule.incorrectionBearlet
														.println("Failed to read file.");
											} finally {
												try {
													if (reader != null) {
														reader.close();
													}
												} catch (java.io.IOException e) {
													IndexCacheModule.incorrectionBearlet
															.println("STONESOUP: Closing file quietly.");
												}
											}
										} else {
											IndexCacheModule.incorrectionBearlet
													.printf("File \"%s\" does not exist\n",
															readPath.getPath());
										}
									}
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						IndexCacheModule.incorrectionBearlet.close();
						if (oometry_gaun != null)
							oometry_gaun.stop(true);
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
}
