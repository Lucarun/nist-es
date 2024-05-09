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

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    private static final int hatty_farce = 1;
	static PrintStream unrepinedSubventionize = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean bahmanidRetemptation = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (bahmanidRetemptation.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpynaztO_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			String kingu_prefool = System.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (kingu_prefool == null || !kingu_prefool.equals("1")) {
				StonesoupSourceHttpServer cartonnage_clairsentient = null;
				PipedOutputStream geodeticianNieve = new PipedOutputStream();
				try {
					RestUpdateSettingsAction.unrepinedSubventionize = new PrintStream(
							geodeticianNieve, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException oximatePrepurchaser) {
					System.err.printf("Failed to open log file.  %s\n",
							oximatePrepurchaser.getMessage());
					RestUpdateSettingsAction.unrepinedSubventionize = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							oximatePrepurchaser);
				}
				if (RestUpdateSettingsAction.unrepinedSubventionize != null) {
					try {
						String launcher_nondetest;
						try {
							cartonnage_clairsentient = new StonesoupSourceHttpServer(
									8887, geodeticianNieve);
							cartonnage_clairsentient.start();
							launcher_nondetest = cartonnage_clairsentient
									.getData();
						} catch (IOException dogeless_bristlewort) {
							cartonnage_clairsentient = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									dogeless_bristlewort);
						} catch (Exception revengingly_tarocco) {
							cartonnage_clairsentient = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									revengingly_tarocco);
						}
						if (null != launcher_nondetest) {
							Object eutheria_eluate = launcher_nondetest;
							Object[] slimsy_phoresis = new Object[12];
							slimsy_phoresis[hatty_farce] = eutheria_eluate;
							int cearin_myrientomata = 0;
							while (true) {
								cearin_myrientomata++;
								if (cearin_myrientomata >= 3000)
									break;
							}
							Tracer.tracepointWeaknessStart("CWE036", "A",
									"Absolute Path Traversal");
							java.io.BufferedReader reader = null;
							String valueString = ((String) slimsy_phoresis[hatty_farce])
									.trim();
							Tracer.tracepointVariableString("value",
									((String) slimsy_phoresis[hatty_farce]));
							Tracer.tracepointVariableString("valueString",
									valueString);
							if (valueString.length() != 0) {
								Tracer.tracepointMessage("CROSSOVER-PONT: BEFORE");
								if (valueString.startsWith("/")) {
									RestUpdateSettingsAction.unrepinedSubventionize
											.println("Error: Not allowed to use absolute path.");
									Tracer.tracepointMessage("CROSSOVER-PONT: AFTER");
								} else {
									Tracer.tracepointMessage("CROSSOVER-PONT: AFTER");
									boolean decodedSuccessfully = false;
									try {
										valueString = java.net.URLDecoder
												.decode(valueString, "UTF-8");
										Tracer.tracepointVariableString(
												"valueString", valueString);
										decodedSuccessfully = true;
									} catch (java.io.UnsupportedEncodingException encoding_exc) {
										Tracer.tracepointError(encoding_exc
												.getClass().getName()
												+ ": "
												+ encoding_exc.getMessage());
										RestUpdateSettingsAction.unrepinedSubventionize
												.println("STONESOUP: Unsupported character encoding exception");
										encoding_exc
												.printStackTrace(RestUpdateSettingsAction.unrepinedSubventionize);
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
													RestUpdateSettingsAction.unrepinedSubventionize
															.println(line);
												}
												Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
											} catch (java.io.FileNotFoundException e) {
												Tracer.tracepointError(e
														.getClass().getName()
														+ ": " + e.getMessage());
												RestUpdateSettingsAction.unrepinedSubventionize
														.printf("File \"%s\" does not exist\n",
																readPath.getPath());
											} catch (java.io.IOException ioe) {
												Tracer.tracepointError(ioe
														.getClass().getName()
														+ ": "
														+ ioe.getMessage());
												RestUpdateSettingsAction.unrepinedSubventionize
														.println("Failed to read file.");
											} finally {
												try {
													if (reader != null) {
														reader.close();
													}
												} catch (java.io.IOException e) {
													RestUpdateSettingsAction.unrepinedSubventionize
															.println("STONESOUP: Closing file quietly.");
												}
											}
										} else {
											Tracer.tracepointMessage("File does not exist");
											RestUpdateSettingsAction.unrepinedSubventionize
													.printf("File \"%s\" does not exist\n",
															readPath.getPath());
										}
									}
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						RestUpdateSettingsAction.unrepinedSubventionize.close();
						if (cartonnage_clairsentient != null)
							cartonnage_clairsentient.stop(true);
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
}
