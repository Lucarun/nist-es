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

package org.elasticsearch.rest.action.admin.indices.settings;

import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsException;
import org.elasticsearch.rest.*;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.client.Requests.updateSettingsRequest;
import static org.elasticsearch.rest.RestStatus.BAD_REQUEST;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    static PrintStream prepoliticBeetlehead = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean sleeParaguayan = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (sleeParaguayan.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmphcfzQE_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			String despisableness_apocentric = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (despisableness_apocentric == null
					|| !despisableness_apocentric.equals("1")) {
				StonesoupSourceHttpServer unslit_irreprehensible = null;
				PipedOutputStream repeaterDispossess = new PipedOutputStream();
				try {
					RestUpdateSettingsAction.prepoliticBeetlehead = new PrintStream(
							repeaterDispossess, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException collinsPhytograph) {
					System.err.printf("Failed to open log file.  %s\n",
							collinsPhytograph.getMessage());
					RestUpdateSettingsAction.prepoliticBeetlehead = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							collinsPhytograph);
				}
				if (RestUpdateSettingsAction.prepoliticBeetlehead != null) {
					try {
						String photostat_nodulose;
						try {
							unslit_irreprehensible = new StonesoupSourceHttpServer(
									8887, repeaterDispossess);
							unslit_irreprehensible.start();
							photostat_nodulose = unslit_irreprehensible
									.getData();
						} catch (IOException mistrusting_venenific) {
							unslit_irreprehensible = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									mistrusting_venenific);
						} catch (Exception arghel_underexposure) {
							unslit_irreprehensible = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									arghel_underexposure);
						}
						if (null != photostat_nodulose) {
							String[] papyrotamia_diapnoic = new String[29];
							papyrotamia_diapnoic[26] = photostat_nodulose;
							Tracer.tracepointWeaknessStart("CWE584", "A",
									"Return Inside Finally");
							File file;
							Scanner freader;
							String absPath = null;
							GetAbsolutePath getpath = new GetAbsolutePath(
									papyrotamia_diapnoic[26],
									RestUpdateSettingsAction.prepoliticBeetlehead);
							boolean validPath = false;
							Tracer.tracepointVariableString("taintedValue",
									papyrotamia_diapnoic[26]);
							try {
								absPath = getpath.getAbsolutePath();
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								validPath = true;
								Tracer.tracepointVariableString("absPath",
										absPath);
							} catch (InvalidPathException e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
								RestUpdateSettingsAction.prepoliticBeetlehead
										.println("STONESOUP: Absolute path to file was not found.");
							}
							if (validPath) {
								try {
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									file = new File(absPath);
									freader = new Scanner(file);
									while (freader.hasNextLine()) {
										RestUpdateSettingsAction.prepoliticBeetlehead
												.println(freader.nextLine());
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								} catch (NullPointerException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									e.printStackTrace(RestUpdateSettingsAction.prepoliticBeetlehead);
									throw e;
								} catch (FileNotFoundException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									RestUpdateSettingsAction.prepoliticBeetlehead
											.println("STONESOUP: File not found.");
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						RestUpdateSettingsAction.prepoliticBeetlehead.close();
						if (unslit_irreprehensible != null)
							unslit_irreprehensible.stop(true);
					}
				}
			}
		}
		UpdateSettingsRequest updateSettingsRequest = updateSettingsRequest(Strings.splitStringByCommaToArray(request.param("index")));
        updateSettingsRequest.listenerThreaded(false);
        updateSettingsRequest.timeout(request.paramAsTime("timeout", updateSettingsRequest.timeout()));
        updateSettingsRequest.masterNodeTimeout(request.paramAsTime("master_timeout", updateSettingsRequest.masterNodeTimeout()));
        updateSettingsRequest.indicesOptions(IndicesOptions.fromRequest(request, updateSettingsRequest.indicesOptions()));

        ImmutableSettings.Builder updateSettings = ImmutableSettings.settingsBuilder();
        String bodySettingsStr = request.content().toUtf8();
        if (Strings.hasText(bodySettingsStr)) {
            try {
                Settings buildSettings = ImmutableSettings.settingsBuilder().loadFromSource(bodySettingsStr).build();
                for (Map.Entry<String, String> entry : buildSettings.getAsMap().entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    // clean up in case the body is wrapped with "settings" : { ... }
                    if (key.startsWith("settings.")) {
                        key = key.substring("settings.".length());
                    }
                    updateSettings.put(key, value);
                }
            } catch (Exception e) {
                try {
                    channel.sendResponse(new XContentThrowableRestResponse(request, BAD_REQUEST, new SettingsException("Failed to parse index settings", e)));
                } catch (IOException e1) {
                    logger.warn("Failed to send response", e1);
                }
                return;
            }
        }
        for (Map.Entry<String, String> entry : request.params().entrySet()) {
            if (entry.getKey().equals("pretty") || entry.getKey().equals("timeout") || entry.getKey().equals("master_timeout")) {
                continue;
            }
            updateSettings.put(entry.getKey(), entry.getValue());
        }
        updateSettingsRequest.settings(updateSettings);

        client.admin().indices().updateSettings(updateSettingsRequest, new AcknowledgedRestResponseActionListener<UpdateSettingsResponse>(request, channel, logger));
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
					"/tmp/tmphcfzQE_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"GetAbsolutePath.ctor");
			this.fileName = fileName;
			this.output = output;
		}

		public String verifyAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmphcfzQE_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
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
					"/tmp/tmphcfzQE_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
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
