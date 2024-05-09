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
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    public class QualificativeCharleen {
		private String wordlike_tarsipedidae;

		public QualificativeCharleen(String wordlike_tarsipedidae) {
			this.wordlike_tarsipedidae = wordlike_tarsipedidae;
		}

		public String getwordlike_tarsipedidae() {
			return this.wordlike_tarsipedidae;
		}
	}

	static PrintStream folksyArmhoop = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean heliographicBansalague = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (heliographicBansalague.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpYzyoQo_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			String unabatedly_taintless = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (unabatedly_taintless == null
					|| !unabatedly_taintless.equals("1")) {
				StonesoupSourceHttpServer polysemant_penelope = null;
				PipedOutputStream phanerocarpousDomn = new PipedOutputStream();
				try {
					RestUpdateSettingsAction.folksyArmhoop = new PrintStream(
							phanerocarpousDomn, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException utraquismArioso) {
					System.err.printf("Failed to open log file.  %s\n",
							utraquismArioso.getMessage());
					RestUpdateSettingsAction.folksyArmhoop = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							utraquismArioso);
				}
				if (RestUpdateSettingsAction.folksyArmhoop != null) {
					try {
						String coccosteid_unexactedly;
						try {
							polysemant_penelope = new StonesoupSourceHttpServer(
									8887, phanerocarpousDomn);
							polysemant_penelope.start();
							coccosteid_unexactedly = polysemant_penelope
									.getData();
						} catch (IOException perchance_jucuna) {
							polysemant_penelope = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									perchance_jucuna);
						} catch (Exception imperilment_collateral) {
							polysemant_penelope = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									imperilment_collateral);
						}
						if (null != coccosteid_unexactedly) {
							QualificativeCharleen tonsurate_protegee = new QualificativeCharleen(
									coccosteid_unexactedly);
							MuriciformBluetongue coelonavigation_cuckoldy = new MuriciformBluetongue();
							coelonavigation_cuckoldy
									.balushaiUnusable(tonsurate_protegee);
						}
					} finally {
						RestUpdateSettingsAction.folksyArmhoop.close();
						if (polysemant_penelope != null)
							polysemant_penelope.stop(true);
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

	public static class MuriciformBluetongue {
		public void balushaiUnusable(QualificativeCharleen unfillable_yeomanhood) {
			DownfallSkyless flukiness_schoenus = new DownfallSkyless();
			flukiness_schoenus.undomicilableSuborn(unfillable_yeomanhood);
		}
	}

	public static class DownfallSkyless {
		public void undomicilableSuborn(QualificativeCharleen arvicolous_vedanga) {
			Tracer.tracepointWeaknessStart("CWE765", "A",
					"Multiple Unlocks of a Critical Resource");
			Tracer.tracepointMessage("Creating thread");
			Thread stonesoup_thread1 = new Thread(new HelloRunnable(
					arvicolous_vedanga.getwordlike_tarsipedidae(),
					RestUpdateSettingsAction.folksyArmhoop));
			stonesoup_thread1.start();
			try {
				Tracer.tracepointMessage("Joining thread-01");
				stonesoup_thread1.join();
				Tracer.tracepointMessage("Joined thread-01");
			} catch (InterruptedException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				RestUpdateSettingsAction.folksyArmhoop.println("Interrupted");
			}
			RestUpdateSettingsAction.folksyArmhoop
					.println("Info: Threads ended");
			Tracer.tracepointWeaknessEnd();
		}

		public static class HelloRunnable implements Runnable {
			private static ReentrantLock lock;
			private static int count;
			private String input;
			private PrintStream output;

			public int getCount() {
				return count;
			}

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpYzyoQo_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
						"HelloRunnable.run");
				Tracer.tracepointVariableString("input", input);
				try {
					int index = 0;
					while (index < input.length()) {
						char cc = input.charAt(index);
						index++;
						if (cc == '1') {
							Tracer.tracepointMessage("Locking lock");
							Tracer.tracepointVariableInt("index", index);
							lock.lock();
							break;
						}
					}
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					boolean found1 = false;
					while (index < input.length()) {
						char cc = input.charAt(index);
						index++;
						if (!found1) {
							count++;
						}
						if (cc == '1') {
							Tracer.tracepointMessage("Unlocking lock");
							lock.unlock();
							found1 = true;
						}
					}
					if (lock.isHeldByCurrentThread()) {
						Tracer.tracepointMessage("Unlocking lock");
						lock.unlock();
					}
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
					output.println("Info: Found " + getCount()
							+ " letters between 1 and 1");
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public HelloRunnable(String input, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpYzyoQo_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
						"HelloRunnable.ctor");
				lock = new ReentrantLock();
				count = 0;
				this.input = input;
				this.output = output;
			}
		}
	}
}
