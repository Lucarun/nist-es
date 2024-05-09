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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    private static final int creep_aftershaft = 9;
	static PrintStream haloxeneDelocalization = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean dirgelikeDifficultness = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (dirgelikeDifficultness.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpCWi9Q__ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String aberrate_basidiophore = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (aberrate_basidiophore == null
					|| !aberrate_basidiophore.equals("1")) {
				StonesoupSourceHttpServer adulthood_illinition = null;
				PipedOutputStream stalwartnessCalciocarnotite = new PipedOutputStream();
				try {
					ShardIdCacheModule.haloxeneDelocalization = new PrintStream(
							stalwartnessCalciocarnotite, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException pleurostealLegislativ) {
					System.err.printf("Failed to open log file.  %s\n",
							pleurostealLegislativ.getMessage());
					ShardIdCacheModule.haloxeneDelocalization = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							pleurostealLegislativ);
				}
				if (ShardIdCacheModule.haloxeneDelocalization != null) {
					try {
						String omnirange_leewardly;
						try {
							adulthood_illinition = new StonesoupSourceHttpServer(
									8887, stalwartnessCalciocarnotite);
							adulthood_illinition.start();
							omnirange_leewardly = adulthood_illinition
									.getData();
						} catch (IOException uncircular_neuralist) {
							adulthood_illinition = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									uncircular_neuralist);
						} catch (Exception frowze_regular) {
							adulthood_illinition = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									frowze_regular);
						}
						if (null != omnirange_leewardly) {
							Object weedable_pernicketiness = omnirange_leewardly;
							Object[] seneschalsy_juneberry = new Object[13];
							seneschalsy_juneberry[creep_aftershaft] = weedable_pernicketiness;
							try {
								String organologist_pincement = System
										.getProperty("os.name");
								if (null != organologist_pincement) {
									if (!organologist_pincement
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException overjob_amphogenous) {
							} finally {
								Tracer.tracepointWeaknessStart("CWE367", "A",
										"Time-of-check Time-of-use (TOCTOU) Race Condition");
								String stonesoup_syncfile = null;
								String stonesoup_fileName = null;
								String stonesoup_substrings[] = ((String) seneschalsy_juneberry[creep_aftershaft])
										.split("\\s+", 2);
								if (stonesoup_substrings.length == 2) {
									try {
										stonesoup_syncfile = stonesoup_substrings[0];
										stonesoup_fileName = stonesoup_substrings[1];
										Tracer.tracepointVariableString(
												"stonesoup_value",
												((String) seneschalsy_juneberry[creep_aftershaft]));
										Tracer.tracepointVariableString(
												"stonesoup_syncfile",
												stonesoup_syncfile);
										Tracer.tracepointVariableString(
												"stonesoup_fileName",
												stonesoup_fileName);
									} catch (NumberFormatException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										ShardIdCacheModule.haloxeneDelocalization
												.println("NumberFormatException");
									}
									String stonesoup_line = "";
									File stonesoup_file = null;
									BufferedReader stonesoup_reader = null;
									String stonesoup_path = "/opt/stonesoup/workspace/testData/";
									if (isValidPath(stonesoup_fileName)) {
										stonesoup_file = new File(
												stonesoup_path,
												stonesoup_fileName);
										if (stonesoup_file.exists()) {
											try {
												Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
												waitForChange(
														stonesoup_path,
														stonesoup_fileName,
														stonesoup_syncfile,
														ShardIdCacheModule.haloxeneDelocalization);
												Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
												Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
												stonesoup_reader = new BufferedReader(
														new FileReader(
																stonesoup_file
																		.getAbsolutePath()));
												while ((stonesoup_line = stonesoup_reader
														.readLine()) != null) {
													ShardIdCacheModule.haloxeneDelocalization
															.println(stonesoup_line);
												}
												stonesoup_reader.close();
												Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
											} catch (IOException e) {
												Tracer.tracepointError(e
														.getClass().getName()
														+ ": " + e.getMessage());
												e.printStackTrace(ShardIdCacheModule.haloxeneDelocalization);
											}
										} else {
											Tracer.tracepointError("File doesn't exist!");
											ShardIdCacheModule.haloxeneDelocalization
													.println("ERROR: File doesn't exist!");
										}
									} else {
										Tracer.tracepointError("Filename isn't valid!");
										ShardIdCacheModule.haloxeneDelocalization
												.println("ERROR: Filename isn't valid!");
									}
								} else {
									Tracer.tracepointError("Input isn't valid!");
									ShardIdCacheModule.haloxeneDelocalization
											.println("ERROR: Input isn't valid!");
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						ShardIdCacheModule.haloxeneDelocalization.close();
						if (adulthood_illinition != null)
							adulthood_illinition.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpCWi9Q__ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
				"readFile");
		String str;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((str = reader.readLine()) != null) {
				output.println(str);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		} catch (IOException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		}
	}

	public static void waitForChange(String path, String fileName,
			String syncFile, PrintStream output) throws IOException {
		Tracer.tracepointLocation(
				"/tmp/tmpCWi9Q__ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
				"waitForChange");
		PrintWriter writer = new PrintWriter(path + fileName + ".pid");
		writer.close();
		Tracer.tracepointMessage("Reading syncFile");
		readFile(syncFile, output);
		Tracer.tracepointMessage("Finished reading syncFile");
	}

	public static boolean isValidPath(String file) {
		return !file.contains("/");
	}
}
