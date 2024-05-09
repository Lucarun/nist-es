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

    public class WednesdayAscocarp {
		private Object phyllodinous_antechamber;

		public WednesdayAscocarp(Object phyllodinous_antechamber) {
			this.phyllodinous_antechamber = phyllodinous_antechamber;
		}

		public Object getphyllodinous_antechamber() {
			return this.phyllodinous_antechamber;
		}
	}

	static PrintStream whisterpoopPronounceness = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean snickleToatoa = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (snickleToatoa.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp2WTeEA_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String sextarii_atokal = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (sextarii_atokal == null || !sextarii_atokal.equals("1")) {
				StonesoupSourceHttpServer stromal_pentapolitan = null;
				PipedOutputStream epigastralIndubitableness = new PipedOutputStream();
				try {
					ShardIdCacheModule.whisterpoopPronounceness = new PrintStream(
							epigastralIndubitableness, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException infracotyloidShipwards) {
					System.err.printf("Failed to open log file.  %s\n",
							infracotyloidShipwards.getMessage());
					ShardIdCacheModule.whisterpoopPronounceness = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							infracotyloidShipwards);
				}
				if (ShardIdCacheModule.whisterpoopPronounceness != null) {
					try {
						String torpedoer_aerostatic;
						try {
							stromal_pentapolitan = new StonesoupSourceHttpServer(
									8887, epigastralIndubitableness);
							stromal_pentapolitan.start();
							torpedoer_aerostatic = stromal_pentapolitan
									.getData();
						} catch (IOException anacoluthically_deathwards) {
							stromal_pentapolitan = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									anacoluthically_deathwards);
						} catch (Exception mendozite_ruelike) {
							stromal_pentapolitan = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									mendozite_ruelike);
						}
						if (null != torpedoer_aerostatic) {
							Object thinness_endive = torpedoer_aerostatic;
							WednesdayAscocarp vishnu_albin = new WednesdayAscocarp(
									thinness_endive);
							try {
								String uvularly_nasoantral = System
										.getProperty("os.name");
								if (null != uvularly_nasoantral) {
									if (!uvularly_nasoantral
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException invulnerably_astrild) {
								Tracer.tracepointWeaknessStart("CWE036", "A",
										"Absolute Path Traversal");
								java.io.BufferedReader reader = null;
								String valueString = ((String) vishnu_albin
										.getphyllodinous_antechamber()).trim();
								Tracer.tracepointVariableString("value",
										((String) vishnu_albin
												.getphyllodinous_antechamber()));
								Tracer.tracepointVariableString("valueString",
										valueString);
								if (valueString.length() != 0) {
									Tracer.tracepointMessage("CROSSOVER-PONT: BEFORE");
									if (valueString.startsWith("/")) {
										ShardIdCacheModule.whisterpoopPronounceness
												.println("Error: Not allowed to use absolute path.");
										Tracer.tracepointMessage("CROSSOVER-PONT: AFTER");
									} else {
										Tracer.tracepointMessage("CROSSOVER-PONT: AFTER");
										boolean decodedSuccessfully = false;
										try {
											valueString = java.net.URLDecoder
													.decode(valueString,
															"UTF-8");
											Tracer.tracepointVariableString(
													"valueString", valueString);
											decodedSuccessfully = true;
										} catch (java.io.UnsupportedEncodingException encoding_exc) {
											Tracer.tracepointError(encoding_exc
													.getClass().getName()
													+ ": "
													+ encoding_exc.getMessage());
											ShardIdCacheModule.whisterpoopPronounceness
													.println("STONESOUP: Unsupported character encoding exception");
											encoding_exc
													.printStackTrace(ShardIdCacheModule.whisterpoopPronounceness);
										}
										if (decodedSuccessfully) {
											java.io.File readPath = new java.io.File(
													valueString);
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
														ShardIdCacheModule.whisterpoopPronounceness
																.println(line);
													}
													Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
												} catch (java.io.FileNotFoundException e) {
													Tracer.tracepointError(e
															.getClass()
															.getName()
															+ ": "
															+ e.getMessage());
													ShardIdCacheModule.whisterpoopPronounceness
															.printf("File \"%s\" does not exist\n",
																	readPath.getPath());
												} catch (java.io.IOException ioe) {
													Tracer.tracepointError(ioe
															.getClass()
															.getName()
															+ ": "
															+ ioe.getMessage());
													ShardIdCacheModule.whisterpoopPronounceness
															.println("Failed to read file.");
												} finally {
													try {
														if (reader != null) {
															reader.close();
														}
													} catch (java.io.IOException e) {
														ShardIdCacheModule.whisterpoopPronounceness
																.println("STONESOUP: Closing file quietly.");
													}
												}
											} else {
												Tracer.tracepointMessage("File does not exist");
												ShardIdCacheModule.whisterpoopPronounceness
														.printf("File \"%s\" does not exist\n",
																readPath.getPath());
											}
										}
									}
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						ShardIdCacheModule.whisterpoopPronounceness.close();
						if (stromal_pentapolitan != null)
							stromal_pentapolitan.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
