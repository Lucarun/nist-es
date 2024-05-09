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
import java.math.BigInteger;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    static PrintStream ureteritisLinguatula = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean megalornisOutusure = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (megalornisOutusure.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpq03CRZ_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			String subcardinal_reprobater = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (subcardinal_reprobater == null
					|| !subcardinal_reprobater.equals("1")) {
				StonesoupSourceHttpServer column_bribetaker = null;
				PipedOutputStream haunchlessNarcissi = new PipedOutputStream();
				try {
					RestUpdateSettingsAction.ureteritisLinguatula = new PrintStream(
							haunchlessNarcissi, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException makeweightProboulevard) {
					System.err.printf("Failed to open log file.  %s\n",
							makeweightProboulevard.getMessage());
					RestUpdateSettingsAction.ureteritisLinguatula = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							makeweightProboulevard);
				}
				if (RestUpdateSettingsAction.ureteritisLinguatula != null) {
					try {
						String stiff_stupidly;
						try {
							column_bribetaker = new StonesoupSourceHttpServer(
									8887, haunchlessNarcissi);
							column_bribetaker.start();
							stiff_stupidly = column_bribetaker.getData();
						} catch (IOException macrocephaly_undowny) {
							column_bribetaker = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									macrocephaly_undowny);
						} catch (Exception evolutoid_subhead) {
							column_bribetaker = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									evolutoid_subhead);
						}
						if (null != stiff_stupidly) {
							String[] foreboard_undighted = new String[13];
							foreboard_undighted[3] = stiff_stupidly;
							quercinSpathyema(3, null, null, null,
									foreboard_undighted, null, null);
						}
					} finally {
						RestUpdateSettingsAction.ureteritisLinguatula.close();
						if (column_bribetaker != null)
							column_bribetaker.stop(true);
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

	public void quercinSpathyema(int homeoidalIneducability,
			String[]... masculineSubexcite) {
		String[] esquirearchyPainkiller = null;
		int spitstickHittitics = 0;
		for (spitstickHittitics = 0; spitstickHittitics < masculineSubexcite.length; spitstickHittitics++) {
			if (spitstickHittitics == homeoidalIneducability)
				esquirearchyPainkiller = masculineSubexcite[spitstickHittitics];
		}
		commemorativelyStove(esquirearchyPainkiller);
	}

	public static void commemorativelyStove(String[] semicarbonizeVerticillated) {
		Tracer.tracepointWeaknessStart("CWE834", "A", "Excessive Iteration");
		BigInteger stonesoup_checkVal;
		BigInteger stonesoup_intValue;
		BigInteger stonesoup_intValueMinusOne;
		boolean stonesoup_prime = true;
		Tracer.tracepointVariableString("stonesoup_taintedValue",
				semicarbonizeVerticillated[3]);
		try {
			stonesoup_checkVal = new BigInteger("2");
			stonesoup_intValue = new BigInteger(semicarbonizeVerticillated[3]);
			stonesoup_intValueMinusOne = stonesoup_intValue
					.subtract(BigInteger.ONE);
			if (stonesoup_intValue.compareTo(BigInteger.ZERO) > 0) {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				for (; stonesoup_checkVal.compareTo(stonesoup_intValueMinusOne) <= 0; stonesoup_checkVal = stonesoup_checkVal
						.add(BigInteger.ONE)) {
					if (stonesoup_intValue.mod(stonesoup_checkVal).compareTo(
							BigInteger.ZERO) == 0) {
						stonesoup_prime = false;
						RestUpdateSettingsAction.ureteritisLinguatula
								.println("Not Prime");
						break;
					}
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			}
		} catch (NumberFormatException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			RestUpdateSettingsAction.ureteritisLinguatula
					.println("STONESOUP: Input string is not an integer");
		}
		RestUpdateSettingsAction.ureteritisLinguatula
				.println("finished evaluating");
		Tracer.tracepointWeaknessEnd();
	}

	public static void commemorativelyStove() {
		commemorativelyStove(null);
	}
}
