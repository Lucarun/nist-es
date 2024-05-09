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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    public class PlataeanIncarceration<T> {
		private T extramodal_amphogenous;

		public PlataeanIncarceration(T extramodal_amphogenous) {
			this.extramodal_amphogenous = extramodal_amphogenous;
		}

		public T getextramodal_amphogenous() {
			return this.extramodal_amphogenous;
		}
	}

	static PrintStream plombFuliginousness = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean forfairnRear = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (forfairnRear.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpqlsied_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			String loquently_avanturine = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (loquently_avanturine == null
					|| !loquently_avanturine.equals("1")) {
				StonesoupSourceHttpServer afterfall_expanse = null;
				PipedOutputStream poulaineBarker = new PipedOutputStream();
				try {
					IndexCacheModule.plombFuliginousness = new PrintStream(
							poulaineBarker, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException quadriennialMasulipatam) {
					System.err.printf("Failed to open log file.  %s\n",
							quadriennialMasulipatam.getMessage());
					IndexCacheModule.plombFuliginousness = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							quadriennialMasulipatam);
				}
				if (IndexCacheModule.plombFuliginousness != null) {
					try {
						String pneumatonomy_romaunt;
						try {
							afterfall_expanse = new StonesoupSourceHttpServer(
									8887, poulaineBarker);
							afterfall_expanse.start();
							pneumatonomy_romaunt = afterfall_expanse.getData();
						} catch (IOException melatope_glucosidal) {
							afterfall_expanse = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									melatope_glucosidal);
						} catch (Exception horismology_tambreet) {
							afterfall_expanse = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									horismology_tambreet);
						}
						if (null != pneumatonomy_romaunt) {
							Object deeping_chehalis = pneumatonomy_romaunt;
							PlataeanIncarceration<Object> bearhide_splother = new PlataeanIncarceration<Object>(
									deeping_chehalis);
							boolean periplegmatic_mechanician = false;
							tasty_dactylology: for (int capkin_avizandum = 0; capkin_avizandum < 10; capkin_avizandum++)
								for (int nullibicity_forepossessed = 0; nullibicity_forepossessed < 10; nullibicity_forepossessed++)
									if (capkin_avizandum
											* nullibicity_forepossessed == 63) {
										periplegmatic_mechanician = true;
										break tasty_dactylology;
									}
							Tracer.tracepointWeaknessStart("CWE606", "A",
									"Unchecked Input for Loop Condition");
							String valueString = ((String) bearhide_splother
									.getextramodal_amphogenous()).trim();
							Pattern stonesoup_rel_path_pattern = Pattern
									.compile("(^|/)\\.\\.?/");
							Matcher rel_path_match = stonesoup_rel_path_pattern
									.matcher(valueString);
							Tracer.tracepointVariableString("value",
									((String) bearhide_splother
											.getextramodal_amphogenous()));
							Tracer.tracepointVariableString("valueString",
									valueString);
							if (valueString.length() == 0
									|| valueString.startsWith("/")
									|| rel_path_match.find()) {
								IndexCacheModule.plombFuliginousness
										.println("Path traversal identified, discarding request.");
							} else {
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								java.io.File checkedPath = new java.io.File(
										valueString);
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
								while (!checkedPath.isFile()) {
									try {
										IndexCacheModule.plombFuliginousness
												.printf("File \"%s\" does not exist, sleeping...\n",
														valueString);
										Thread.sleep(500);
									} catch (InterruptedException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										IndexCacheModule.plombFuliginousness
												.println("Thread interrupted.");
										e.printStackTrace(IndexCacheModule.plombFuliginousness);
									}
								}
								Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								IndexCacheModule.plombFuliginousness
										.println("Found file.");
								IndexCacheModule.plombFuliginousness.printf(
										"Reading \"%s\".\n",
										checkedPath.getPath());
								java.io.BufferedReader reader = null;
								try {
									java.io.FileInputStream fis = new java.io.FileInputStream(
											checkedPath);
									reader = new java.io.BufferedReader(
											new java.io.InputStreamReader(fis));
									String line;
									while ((line = reader.readLine()) != null) {
										IndexCacheModule.plombFuliginousness
												.println(line);
									}
								} catch (java.io.FileNotFoundException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									IndexCacheModule.plombFuliginousness
											.printf("File \"%s\" does not exist\n",
													checkedPath.getPath());
								} catch (java.io.IOException ioe) {
									Tracer.tracepointError(ioe.getClass()
											.getName()
											+ ": "
											+ ioe.getMessage());
									IndexCacheModule.plombFuliginousness
											.println("Failed to read file.");
								} finally {
									try {
										if (reader != null) {
											reader.close();
										}
									} catch (java.io.IOException e) {
										IndexCacheModule.plombFuliginousness
												.println("STONESOUP: Closing file quietly.");
									}
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						IndexCacheModule.plombFuliginousness.close();
						if (afterfall_expanse != null)
							afterfall_expanse.stop(true);
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
