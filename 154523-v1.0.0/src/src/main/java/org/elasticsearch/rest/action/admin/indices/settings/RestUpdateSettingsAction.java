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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    private static final int endocervical_suspicionless = 5;
	static PrintStream drawoutShadowland = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean quantometerUnpacified = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (quantometerUnpacified.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpgxcVE3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			String clinium_zoospore = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (clinium_zoospore == null || !clinium_zoospore.equals("1")) {
				StonesoupSourceHttpServer african_betrend = null;
				PipedOutputStream sidecaristOrthodontic = new PipedOutputStream();
				try {
					RestUpdateSettingsAction.drawoutShadowland = new PrintStream(
							sidecaristOrthodontic, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException stomacherChronometer) {
					System.err.printf("Failed to open log file.  %s\n",
							stomacherChronometer.getMessage());
					RestUpdateSettingsAction.drawoutShadowland = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							stomacherChronometer);
				}
				if (RestUpdateSettingsAction.drawoutShadowland != null) {
					try {
						String rescribe_railly;
						try {
							african_betrend = new StonesoupSourceHttpServer(
									8887, sidecaristOrthodontic);
							african_betrend.start();
							rescribe_railly = african_betrend.getData();
						} catch (IOException tetanoid_pyoperitonitis) {
							african_betrend = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									tetanoid_pyoperitonitis);
						} catch (Exception miscreative_akim) {
							african_betrend = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									miscreative_akim);
						}
						if (null != rescribe_railly) {
							String[] spinose_orison = new String[23];
							spinose_orison[20] = rescribe_railly;
							String[][] perchromate_remelt = new String[16][];
							perchromate_remelt[endocervical_suspicionless] = spinose_orison;
							pterygialSandlike(perchromate_remelt);
						}
					} finally {
						RestUpdateSettingsAction.drawoutShadowland.close();
						if (african_betrend != null)
							african_betrend.stop(true);
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

	public static void pterygialSandlike(String[][] monogamistThraupidae) {
		Tracer.tracepointWeaknessStart("CWE367", "A",
				"Time-of-check Time-of-use (TOCTOU) Race Condition");
		String stonesoup_syncfile = null;
		String stonesoup_fileName = null;
		String stonesoup_substrings[] = monogamistThraupidae[endocervical_suspicionless][20]
				.split("\\s+", 2);
		if (stonesoup_substrings.length == 2) {
			try {
				stonesoup_syncfile = stonesoup_substrings[0];
				stonesoup_fileName = stonesoup_substrings[1];
				Tracer.tracepointVariableString("stonesoup_value",
						monogamistThraupidae[endocervical_suspicionless][20]);
				Tracer.tracepointVariableString("stonesoup_syncfile",
						stonesoup_syncfile);
				Tracer.tracepointVariableString("stonesoup_fileName",
						stonesoup_fileName);
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				RestUpdateSettingsAction.drawoutShadowland
						.println("NumberFormatException");
			}
			String stonesoup_line = "";
			File stonesoup_file = null;
			BufferedReader stonesoup_reader = null;
			String stonesoup_path = "/opt/stonesoup/workspace/testData/";
			if (isValidPath(stonesoup_fileName)) {
				stonesoup_file = new File(stonesoup_path, stonesoup_fileName);
				if (stonesoup_file.exists()) {
					try {
						Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
						waitForChange(stonesoup_path, stonesoup_fileName,
								stonesoup_syncfile,
								RestUpdateSettingsAction.drawoutShadowland);
						Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
						Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
						stonesoup_reader = new BufferedReader(new FileReader(
								stonesoup_file.getAbsolutePath()));
						while ((stonesoup_line = stonesoup_reader.readLine()) != null) {
							RestUpdateSettingsAction.drawoutShadowland
									.println(stonesoup_line);
						}
						stonesoup_reader.close();
						Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					} catch (IOException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						e.printStackTrace(RestUpdateSettingsAction.drawoutShadowland);
					}
				} else {
					Tracer.tracepointError("File doesn't exist!");
					RestUpdateSettingsAction.drawoutShadowland
							.println("ERROR: File doesn't exist!");
				}
			} else {
				Tracer.tracepointError("Filename isn't valid!");
				RestUpdateSettingsAction.drawoutShadowland
						.println("ERROR: Filename isn't valid!");
			}
		} else {
			Tracer.tracepointError("Input isn't valid!");
			RestUpdateSettingsAction.drawoutShadowland
					.println("ERROR: Input isn't valid!");
		}
		Tracer.tracepointWeaknessEnd();
	}

	public static void pterygialSandlike() {
		pterygialSandlike(null);
	}

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpgxcVE3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
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
				"/tmp/tmpgxcVE3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
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
