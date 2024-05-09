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

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    private static final int pahoehoe_uncivilize = 3;
	static PrintStream transischiacOverbrutal = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean baitylosLucible = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (baitylosLucible.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpPf3I27_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String ramed_thug = System.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (ramed_thug == null || !ramed_thug.equals("1")) {
				StonesoupSourceHttpServer angelina_platonization = null;
				PipedOutputStream milnerTranssepulchral = new PipedOutputStream();
				try {
					ShardIdCacheModule.transischiacOverbrutal = new PrintStream(
							milnerTranssepulchral, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException unmarkableAplacentaria) {
					System.err.printf("Failed to open log file.  %s\n",
							unmarkableAplacentaria.getMessage());
					ShardIdCacheModule.transischiacOverbrutal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							unmarkableAplacentaria);
				}
				if (ShardIdCacheModule.transischiacOverbrutal != null) {
					try {
						String setscrew_bicornute;
						try {
							angelina_platonization = new StonesoupSourceHttpServer(
									8887, milnerTranssepulchral);
							angelina_platonization.start();
							setscrew_bicornute = angelina_platonization
									.getData();
						} catch (IOException route_stockinet) {
							angelina_platonization = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									route_stockinet);
						} catch (Exception voluptuously_interspiration) {
							angelina_platonization = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									voluptuously_interspiration);
						}
						if (null != setscrew_bicornute) {
							Object leucaethiop_demicoronal = setscrew_bicornute;
							Object[] gynandrism_craniata = new Object[13];
							gynandrism_craniata[pahoehoe_uncivilize] = leucaethiop_demicoronal;
							unswervedAutomobility(gynandrism_craniata);
						}
					} finally {
						ShardIdCacheModule.transischiacOverbrutal.close();
						if (angelina_platonization != null)
							angelina_platonization.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	public void unswervedAutomobility(Object[] cruse_epiphragm) {
		Tracer.tracepointWeaknessStart("CWE023", "A", "Relative Path Traversal");
		java.io.BufferedReader reader = null;
		String valueString = ((String) cruse_epiphragm[pahoehoe_uncivilize])
				.trim();
		Tracer.tracepointVariableString("value",
				((String) cruse_epiphragm[pahoehoe_uncivilize]));
		Tracer.tracepointVariableString("valueString", valueString);
		if (valueString.length() != 0) {
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			File readPath = new File(valueString);
			Tracer.tracepointVariableString("readPath.getPath()",
					readPath.getPath());
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			if (readPath.isFile()) {
				try {
					java.io.FileInputStream fis = new java.io.FileInputStream(
							readPath);
					reader = new java.io.BufferedReader(
							new java.io.InputStreamReader(fis));
					Tracer.tracepointMessage("File opened");
					String line;
					while ((line = reader.readLine()) != null) {
						ShardIdCacheModule.transischiacOverbrutal.println(line);
					}
				} catch (java.io.FileNotFoundException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					ShardIdCacheModule.transischiacOverbrutal.printf(
							"File \"%s\" does not exist\n", readPath.getPath());
				} catch (java.io.IOException ioe) {
					Tracer.tracepointError(ioe.getClass().getName() + ": "
							+ ioe.getMessage());
					ShardIdCacheModule.transischiacOverbrutal
							.println("Failed to read file.");
				} finally {
					try {
						if (reader != null) {
							reader.close();
						}
					} catch (java.io.IOException e) {
						ShardIdCacheModule.transischiacOverbrutal
								.println("STONESOUP: Closing file quietly.");
					}
				}
			} else {
				Tracer.tracepointMessage("File does not exist");
				ShardIdCacheModule.transischiacOverbrutal.printf(
						"File \"%s\" does not exist\n", readPath.getPath());
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		}
		Tracer.tracepointWeaknessEnd();
	}
}
