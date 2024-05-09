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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    static PrintStream preventinglyHaunched = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean xenacanthiniBroadhearted = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (xenacanthiniBroadhearted.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpTnvDkI_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			String gateway_hemocoelic = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (gateway_hemocoelic == null || !gateway_hemocoelic.equals("1")) {
				StonesoupSourceHttpServer memphian_charmedly = null;
				PipedOutputStream desmodiumUnderditch = new PipedOutputStream();
				try {
					RestUpdateSettingsAction.preventinglyHaunched = new PrintStream(
							desmodiumUnderditch, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException imparalleledThesmophorian) {
					System.err.printf("Failed to open log file.  %s\n",
							imparalleledThesmophorian.getMessage());
					RestUpdateSettingsAction.preventinglyHaunched = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							imparalleledThesmophorian);
				}
				if (RestUpdateSettingsAction.preventinglyHaunched != null) {
					try {
						String migonitis_uppishness;
						try {
							memphian_charmedly = new StonesoupSourceHttpServer(
									8887, desmodiumUnderditch);
							memphian_charmedly.start();
							migonitis_uppishness = memphian_charmedly.getData();
						} catch (IOException culverwort_overequal) {
							memphian_charmedly = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									culverwort_overequal);
						} catch (Exception confederation_folliculin) {
							memphian_charmedly = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									confederation_folliculin);
						}
						if (null != migonitis_uppishness) {
							String[] unmindful_rebeggar = new String[13];
							unmindful_rebeggar[12] = migonitis_uppishness;
							pulletLichenization(3, null, null, null,
									unmindful_rebeggar, null, null);
						}
					} finally {
						RestUpdateSettingsAction.preventinglyHaunched.close();
						if (memphian_charmedly != null)
							memphian_charmedly.stop(true);
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

	public void pulletLichenization(int galpQuadrimetallic,
			String[]... rhypographyAitchbone) {
		String[] lobuleIntractability = null;
		int ungermlikeEnthraller = 0;
		for (ungermlikeEnthraller = 0; ungermlikeEnthraller < rhypographyAitchbone.length; ungermlikeEnthraller++) {
			if (ungermlikeEnthraller == galpQuadrimetallic)
				lobuleIntractability = rhypographyAitchbone[ungermlikeEnthraller];
		}
		try {
			String cervicovaginal_camacan = System.getProperty("os.name");
			if (null != cervicovaginal_camacan) {
				if (!cervicovaginal_camacan.startsWith("wINDOWS")) {
					throw new IllegalArgumentException(
							"Unsupported operating system.");
				}
			}
		} catch (IllegalArgumentException postrubeolar_hightop) {
			Tracer.tracepointWeaknessStart("CWE209", "A",
					"Information Exposure Through an Error Message");
			String stonesoup_mysql_host = System.getenv("DBMYSQLHOST");
			String stonesoup_mysql_port = System.getenv("DBMYSQLPORT");
			String stonesoup_mysql_user = System.getenv("DBMYSQLUSER");
			String stonesoup_mysql_pass = System.getenv("DBMYSQLPASSWORD");
			String stonesoup_mysql_dbname = System.getenv("SS_DBMYSQLDATABASE");
			Tracer.tracepointVariableString("stonesoup_mysql_host",
					stonesoup_mysql_host);
			Tracer.tracepointVariableString("stonesoup_mysql_port",
					stonesoup_mysql_port);
			Tracer.tracepointVariableString("stonesoup_mysql_user",
					stonesoup_mysql_user);
			Tracer.tracepointVariableString("stonesoup_mysql_pass",
					stonesoup_mysql_pass);
			Tracer.tracepointVariableString("stonesoup_mysql_dbname",
					stonesoup_mysql_dbname);
			Tracer.tracepointVariableString("companyName",
					lobuleIntractability[12]);
			if (stonesoup_mysql_host == null || stonesoup_mysql_port == null
					|| stonesoup_mysql_user == null
					|| stonesoup_mysql_pass == null
					|| stonesoup_mysql_dbname == null) {
				Tracer.tracepointError("Missing required database connection parameter(s).");
				RestUpdateSettingsAction.preventinglyHaunched
						.println("STONESOUP: Missing required DB connection parameters.");
			} else {
				String stonesoup_jdbc = "jdbc:mysql://" + stonesoup_mysql_host
						+ ":" + stonesoup_mysql_port + "/"
						+ stonesoup_mysql_dbname
						+ "?dumpQueriesOnException=true";
				Tracer.tracepointVariableString("stonesoup_jdbc",
						stonesoup_jdbc);
				if (lobuleIntractability[12] == null) {
					RestUpdateSettingsAction.preventinglyHaunched
							.println("No company name provided.");
				} else {
					Connection con = null;
					try {
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection(stonesoup_jdbc,
								stonesoup_mysql_user, stonesoup_mysql_pass);
						try {
							PreparedStatement stmt = con
									.prepareStatement("INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?)");
							Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
							stmt.setString(1, lobuleIntractability[12]);
							stmt.setNull(2, Types.NULL);
							Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
							Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
							if (stmt.executeUpdate() > 0) {
								RestUpdateSettingsAction.preventinglyHaunched
										.println("Shipper added successfully.");
							} else {
								RestUpdateSettingsAction.preventinglyHaunched
										.println("No rows added.");
							}
							Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
						} catch (SQLException se) {
							Tracer.tracepointError("SQLException: Printing connection details");
							RestUpdateSettingsAction.preventinglyHaunched
									.println("Database Error!");
							RestUpdateSettingsAction.preventinglyHaunched
									.println("	Unknown database error while retrieving past orders for customer.");
							RestUpdateSettingsAction.preventinglyHaunched
									.println("");
							RestUpdateSettingsAction.preventinglyHaunched
									.println("Connection Details");
							RestUpdateSettingsAction.preventinglyHaunched
									.printf("    Host: %s\n",
											stonesoup_mysql_host);
							RestUpdateSettingsAction.preventinglyHaunched
									.printf("    Port: %s\n",
											stonesoup_mysql_port);
							RestUpdateSettingsAction.preventinglyHaunched
									.printf("    User: %s\n",
											stonesoup_mysql_user);
							RestUpdateSettingsAction.preventinglyHaunched
									.printf("    Pass: %s\n",
											stonesoup_mysql_pass);
							RestUpdateSettingsAction.preventinglyHaunched
									.printf("    JDBC: %s\n", stonesoup_jdbc);
							RestUpdateSettingsAction.preventinglyHaunched
									.println("");
							RestUpdateSettingsAction.preventinglyHaunched
									.println("Error Message");
							RestUpdateSettingsAction.preventinglyHaunched
									.println(se.getMessage());
							RestUpdateSettingsAction.preventinglyHaunched
									.println("");
							RestUpdateSettingsAction.preventinglyHaunched
									.println("Stacktrace");
							se.printStackTrace(RestUpdateSettingsAction.preventinglyHaunched);
						}
					} catch (SQLException se) {
						Tracer.tracepointError(se.getClass().getName() + ": "
								+ se.getMessage());
						RestUpdateSettingsAction.preventinglyHaunched
								.println("STONESOUP: Failed to connect to DB.");
						se.printStackTrace(RestUpdateSettingsAction.preventinglyHaunched);
					} catch (ClassNotFoundException cnfe) {
						Tracer.tracepointError(cnfe.getClass().getName() + ": "
								+ cnfe.getMessage());
						RestUpdateSettingsAction.preventinglyHaunched
								.println("STONESOUP: Failed to load DB driver.");
						cnfe.printStackTrace(RestUpdateSettingsAction.preventinglyHaunched);
					} finally {
						try {
							if (con != null && !con.isClosed()) {
								con.close();
							}
						} catch (SQLException e) {
							Tracer.tracepointError(e.getClass().getName()
									+ ": " + e.getMessage());
							RestUpdateSettingsAction.preventinglyHaunched
									.println("STONESOUP: Failed to close DB connection.");
							e.printStackTrace(RestUpdateSettingsAction.preventinglyHaunched);
						}
					}
				}
			}
			Tracer.tracepointWeaknessEnd();
		}
	}
}
