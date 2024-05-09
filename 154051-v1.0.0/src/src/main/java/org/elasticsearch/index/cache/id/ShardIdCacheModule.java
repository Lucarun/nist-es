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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    static PrintStream biparietalLoxophthalmus = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean stafferHolystone = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (stafferHolystone.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmplyGezi_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String holarthritic_thrilly = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (holarthritic_thrilly == null
					|| !holarthritic_thrilly.equals("1")) {
				StonesoupSourceHttpServer hemibasidiales_pigless = null;
				PipedOutputStream albescenceOrbiculated = new PipedOutputStream();
				try {
					ShardIdCacheModule.biparietalLoxophthalmus = new PrintStream(
							albescenceOrbiculated, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException gavyutiHexactinellidan) {
					System.err.printf("Failed to open log file.  %s\n",
							gavyutiHexactinellidan.getMessage());
					ShardIdCacheModule.biparietalLoxophthalmus = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							gavyutiHexactinellidan);
				}
				if (ShardIdCacheModule.biparietalLoxophthalmus != null) {
					try {
						final String slantly_fugaciousness;
						try {
							hemibasidiales_pigless = new StonesoupSourceHttpServer(
									8887, albescenceOrbiculated);
							hemibasidiales_pigless.start();
							slantly_fugaciousness = hemibasidiales_pigless
									.getData();
						} catch (IOException prewillingness_survival) {
							hemibasidiales_pigless = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									prewillingness_survival);
						} catch (Exception thill_liken) {
							hemibasidiales_pigless = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									thill_liken);
						}
						if (null != slantly_fugaciousness) {
							final String[] metamorphopsia_torpor = new String[16];
							metamorphopsia_torpor[12] = slantly_fugaciousness;
							int dagbane_bilander = 0;
							while (true) {
								dagbane_bilander++;
								if (dagbane_bilander >= 3000)
									break;
							}
							Tracer.tracepointWeaknessStart("CWE584", "A",
									"Return Inside Finally");
							File file;
							Scanner freader;
							String absPath = null;
							GetAbsolutePath getpath = new GetAbsolutePath(
									metamorphopsia_torpor[12],
									ShardIdCacheModule.biparietalLoxophthalmus);
							boolean validPath = false;
							Tracer.tracepointVariableString("taintedValue",
									metamorphopsia_torpor[12]);
							try {
								absPath = getpath.getAbsolutePath();
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								validPath = true;
								Tracer.tracepointVariableString("absPath",
										absPath);
							} catch (InvalidPathException e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
								ShardIdCacheModule.biparietalLoxophthalmus
										.println("STONESOUP: Absolute path to file was not found.");
							}
							if (validPath) {
								try {
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									file = new File(absPath);
									freader = new Scanner(file);
									while (freader.hasNextLine()) {
										ShardIdCacheModule.biparietalLoxophthalmus
												.println(freader.nextLine());
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								} catch (NullPointerException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									e.printStackTrace(ShardIdCacheModule.biparietalLoxophthalmus);
									throw e;
								} catch (FileNotFoundException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									ShardIdCacheModule.biparietalLoxophthalmus
											.println("STONESOUP: File not found.");
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						ShardIdCacheModule.biparietalLoxophthalmus.close();
						if (hemibasidiales_pigless != null)
							hemibasidiales_pigless.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	static class InvalidPathException extends Exception {
		private static final long serialVersionUID = 1L;

		public InvalidPathException(String msg) {
			super(msg);
		}
	}

	static class GetAbsolutePath {
		private String fileName;
		private PrintStream output;

		public GetAbsolutePath(String fileName, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmplyGezi_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"GetAbsolutePath.ctor");
			this.fileName = fileName;
			this.output = output;
		}

		public String verifyAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmplyGezi_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"GetAbsolutePath.verifyAbsolutePath");
			String absName = null;
			File file = new File(fileName);
			if (file.exists()) {
				absName = file.getAbsolutePath();
			} else {
				throw (new InvalidPathException("No such file: " + fileName));
			}
			return absName;
		}

		@SuppressWarnings("finally")
		public String getAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmplyGezi_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"GetAbsolutePath.getAbsolutePath");
			String absName = null;
			try {
				absName = this.verifyAbsolutePath();
			} catch (InvalidPathException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				output.println("STONESOUP: Error in verifying absolute path\n");
				throw e;
			} finally {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				return absName;
			}
		}
	}
}
