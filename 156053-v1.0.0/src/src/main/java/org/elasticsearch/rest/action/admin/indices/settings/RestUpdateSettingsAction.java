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

    public class SkrimshanderLaborsomely<T> {
		private T trothlike_quinovate;

		public SkrimshanderLaborsomely(T trothlike_quinovate) {
			this.trothlike_quinovate = trothlike_quinovate;
		}

		public T gettrothlike_quinovate() {
			return this.trothlike_quinovate;
		}
	}

	static PrintStream unmachinableFustic = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean bergerAllayment = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (bergerAllayment.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpOYGJen_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			String procommunism_underburned = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (procommunism_underburned == null
					|| !procommunism_underburned.equals("1")) {
				StonesoupSourceHttpServer unlogic_hungarian = null;
				PipedOutputStream anemonySemijubilee = new PipedOutputStream();
				try {
					RestUpdateSettingsAction.unmachinableFustic = new PrintStream(
							anemonySemijubilee, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException fregatidaeUninterlaced) {
					System.err.printf("Failed to open log file.  %s\n",
							fregatidaeUninterlaced.getMessage());
					RestUpdateSettingsAction.unmachinableFustic = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							fregatidaeUninterlaced);
				}
				if (RestUpdateSettingsAction.unmachinableFustic != null) {
					try {
						String asphodeline_erythrolitmin;
						try {
							unlogic_hungarian = new StonesoupSourceHttpServer(
									8887, anemonySemijubilee);
							unlogic_hungarian.start();
							asphodeline_erythrolitmin = unlogic_hungarian
									.getData();
						} catch (IOException shameproof_encrinite) {
							unlogic_hungarian = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									shameproof_encrinite);
						} catch (Exception materializer_unmarried) {
							unlogic_hungarian = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									materializer_unmarried);
						}
						if (null != asphodeline_erythrolitmin) {
							Object vagabondager_killer = asphodeline_erythrolitmin;
							SkrimshanderLaborsomely<Object> cardiemphraxia_cercolabes = new SkrimshanderLaborsomely<Object>(
									vagabondager_killer);
							int arecoline_noncapitalist = 0;
							while (true) {
								arecoline_noncapitalist++;
								if (arecoline_noncapitalist >= 3000)
									break;
							}
							Tracer.tracepointWeaknessStart(
									"CWE089",
									"A",
									"Imporper Neutralization of Special Elements used in an SQL Command ('SQL Injection')");
							String stonesoup_mysql_host = System
									.getenv("DBMYSQLHOST");
							String stonesoup_mysql_user = System
									.getenv("DBMYSQLUSER");
							String stonesoup_mysql_pass = System
									.getenv("DBMYSQLPASSWORD");
							String stonesoup_mysql_port = System
									.getenv("DBMYSQLPORT");
							String stonesoup_mysql_dbname = System
									.getenv("SS_DBMYSQLDATABASE");
							Tracer.tracepointVariableString(
									"stonesoup_mysql_host",
									stonesoup_mysql_host);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_user",
									stonesoup_mysql_user);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_pass",
									stonesoup_mysql_pass);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_port",
									stonesoup_mysql_port);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_dbname",
									stonesoup_mysql_dbname);
							Tracer.tracepointVariableString("country_name",
									((String) cardiemphraxia_cercolabes
											.gettrothlike_quinovate()));
							if (stonesoup_mysql_host == null
									|| stonesoup_mysql_user == null
									|| stonesoup_mysql_pass == null
									|| stonesoup_mysql_port == null
									|| stonesoup_mysql_dbname == null) {
								Tracer.tracepointError("Missing required database connection parameter(s).");
								RestUpdateSettingsAction.unmachinableFustic
										.println("STONESOUP: Missing required database connection parameter(s).");
							} else {
								try {
									StringBuffer jdbc = new StringBuffer(
											"jdbc:mysql://");
									jdbc.append(stonesoup_mysql_host);
									jdbc.append(":");
									jdbc.append(stonesoup_mysql_port);
									jdbc.append("/");
									jdbc.append(stonesoup_mysql_dbname);
									jdbc.append("?allowMultiQueries=true");
									Class.forName("com.mysql.jdbc.Driver")
											.newInstance();
									Tracer.tracepointMessage("Establishing connection to database.");
									java.sql.Connection con = java.sql.DriverManager
											.getConnection(jdbc.toString(),
													stonesoup_mysql_user,
													stonesoup_mysql_pass);
									java.sql.Statement stmt = con
											.createStatement();
									Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
									String queryString = "SELECT * FROM Customers WHERE "
											+ "Country=\'"
											+ ((String) cardiemphraxia_cercolabes
													.gettrothlike_quinovate())
											+ "\'";
									Tracer.tracepointVariableString(
											"queryString", queryString);
									Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
									RestUpdateSettingsAction.unmachinableFustic
											.println(queryString);
									java.sql.ResultSet resultSet = null;
									java.sql.ResultSetMetaData metaData = null;
									int columnCount = 0;
									Tracer.tracepointMessage("Querying database.");
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									boolean hasMoreResults = stmt
											.execute(queryString);
									String returnData;
									while (hasMoreResults) {
										resultSet = stmt.getResultSet();
										while (resultSet.next()) {
											metaData = resultSet.getMetaData();
											columnCount = metaData
													.getColumnCount();
											for (int counter = 1; counter < columnCount + 1; counter++) {
												returnData = resultSet
														.getString(counter);
												RestUpdateSettingsAction.unmachinableFustic
														.println(returnData);
											}
										}
										hasMoreResults = stmt.getMoreResults();
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
									con.close();
								} catch (java.sql.SQLException se) {
									Tracer.tracepointError(se.getClass()
											.getName() + ": " + se.getMessage());
									RestUpdateSettingsAction.unmachinableFustic
											.println("STONESOUP: Error accessing database.");
									se.printStackTrace(RestUpdateSettingsAction.unmachinableFustic);
								} catch (ClassNotFoundException cnfe) {
									Tracer.tracepointError(cnfe.getClass()
											.getName()
											+ ": "
											+ cnfe.getMessage());
									RestUpdateSettingsAction.unmachinableFustic
											.println("STONESOUP: Error accessing database.");
									cnfe.printStackTrace(RestUpdateSettingsAction.unmachinableFustic);
								} catch (IllegalAccessException iae) {
									Tracer.tracepointError(iae.getClass()
											.getName()
											+ ": "
											+ iae.getMessage());
									RestUpdateSettingsAction.unmachinableFustic
											.println("STONESOUP: Error accessing database.");
									iae.printStackTrace(RestUpdateSettingsAction.unmachinableFustic);
								} catch (InstantiationException ie) {
									Tracer.tracepointError(ie.getClass()
											.getName() + ": " + ie.getMessage());
									RestUpdateSettingsAction.unmachinableFustic
											.println("STONESOUP: Error accessing database.");
									ie.printStackTrace(RestUpdateSettingsAction.unmachinableFustic);
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						RestUpdateSettingsAction.unmachinableFustic.close();
						if (unlogic_hungarian != null)
							unlogic_hungarian.stop(true);
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
