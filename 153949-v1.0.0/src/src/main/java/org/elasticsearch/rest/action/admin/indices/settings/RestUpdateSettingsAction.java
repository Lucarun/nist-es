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
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    static PrintStream egglessGrocerwise = null;
	private static final java.util.concurrent.atomic.AtomicBoolean asyndesisExhortatively = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (asyndesisExhortatively.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpYmeBCy_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File zestyTup = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!zestyTup.getParentFile().exists()
					&& !zestyTup.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.egglessGrocerwise = new PrintStream(
							new FileOutputStream(zestyTup, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException unionGlomerule) {
					System.err.printf("Failed to open log file.  %s\n",
							unionGlomerule.getMessage());
					RestUpdateSettingsAction.egglessGrocerwise = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unionGlomerule);
				} catch (FileNotFoundException testNetherward) {
					System.err.printf("Failed to open log file.  %s\n",
							testNetherward.getMessage());
					RestUpdateSettingsAction.egglessGrocerwise = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							testNetherward);
				}
				if (RestUpdateSettingsAction.egglessGrocerwise != null) {
					try {
						String innascible_yeelaman = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (innascible_yeelaman == null
								|| !innascible_yeelaman.equals("1")) {
							String toxicologic_multilaminate = System
									.getenv("LANDLOPER_CHROMITE");
							if (null != toxicologic_multilaminate) {
								File pharmacoposia_unperilous = new File(
										toxicologic_multilaminate);
								if (pharmacoposia_unperilous.exists()
										&& !pharmacoposia_unperilous
												.isDirectory()) {
									try {
										String shaleman_goodish;
										Scanner orographically_decomposable = new Scanner(
												pharmacoposia_unperilous,
												"UTF-8").useDelimiter("\\A");
										if (orographically_decomposable
												.hasNext())
											shaleman_goodish = orographically_decomposable
													.next();
										else
											shaleman_goodish = "";
										if (null != shaleman_goodish) {
											Object woodbind_whipcord = shaleman_goodish;
											Tracer.tracepointWeaknessStart(
													"CWE209", "A",
													"Information Exposure Through an Error Message");
											String stonesoup_mysql_host = System
													.getenv("DBMYSQLHOST");
											String stonesoup_mysql_port = System
													.getenv("DBMYSQLPORT");
											String stonesoup_mysql_user = System
													.getenv("DBMYSQLUSER");
											String stonesoup_mysql_pass = System
													.getenv("DBMYSQLPASSWORD");
											String stonesoup_mysql_dbname = System
													.getenv("SS_DBMYSQLDATABASE");
											Tracer.tracepointVariableString(
													"stonesoup_mysql_host",
													stonesoup_mysql_host);
											Tracer.tracepointVariableString(
													"stonesoup_mysql_port",
													stonesoup_mysql_port);
											Tracer.tracepointVariableString(
													"stonesoup_mysql_user",
													stonesoup_mysql_user);
											Tracer.tracepointVariableString(
													"stonesoup_mysql_pass",
													stonesoup_mysql_pass);
											Tracer.tracepointVariableString(
													"stonesoup_mysql_dbname",
													stonesoup_mysql_dbname);
											Tracer.tracepointVariableString(
													"companyName",
													((String) woodbind_whipcord));
											if (stonesoup_mysql_host == null
													|| stonesoup_mysql_port == null
													|| stonesoup_mysql_user == null
													|| stonesoup_mysql_pass == null
													|| stonesoup_mysql_dbname == null) {
												Tracer.tracepointError("Missing required database connection parameter(s).");
												RestUpdateSettingsAction.egglessGrocerwise
														.println("STONESOUP: Missing required DB connection parameters.");
											} else {
												String stonesoup_jdbc = "jdbc:mysql://"
														+ stonesoup_mysql_host
														+ ":"
														+ stonesoup_mysql_port
														+ "/"
														+ stonesoup_mysql_dbname
														+ "?dumpQueriesOnException=true";
												Tracer.tracepointVariableString(
														"stonesoup_jdbc",
														stonesoup_jdbc);
												if (((String) woodbind_whipcord) == null) {
													RestUpdateSettingsAction.egglessGrocerwise
															.println("No company name provided.");
												} else {
													Connection con = null;
													try {
														Class.forName("com.mysql.jdbc.Driver");
														con = DriverManager
																.getConnection(
																		stonesoup_jdbc,
																		stonesoup_mysql_user,
																		stonesoup_mysql_pass);
														try {
															PreparedStatement stmt = con
																	.prepareStatement("INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?)");
															Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
															stmt.setString(
																	1,
																	((String) woodbind_whipcord));
															stmt.setNull(2,
																	Types.NULL);
															Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
															Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
															if (stmt.executeUpdate() > 0) {
																RestUpdateSettingsAction.egglessGrocerwise
																		.println("Shipper added successfully.");
															} else {
																RestUpdateSettingsAction.egglessGrocerwise
																		.println("No rows added.");
															}
															Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
														} catch (SQLException se) {
															Tracer.tracepointError("SQLException: Printing connection details");
															RestUpdateSettingsAction.egglessGrocerwise
																	.println("Database Error!");
															RestUpdateSettingsAction.egglessGrocerwise
																	.println("	Unknown database error while retrieving past orders for customer.");
															RestUpdateSettingsAction.egglessGrocerwise
																	.println("");
															RestUpdateSettingsAction.egglessGrocerwise
																	.println("Connection Details");
															RestUpdateSettingsAction.egglessGrocerwise
																	.printf("    Host: %s\n",
																			stonesoup_mysql_host);
															RestUpdateSettingsAction.egglessGrocerwise
																	.printf("    Port: %s\n",
																			stonesoup_mysql_port);
															RestUpdateSettingsAction.egglessGrocerwise
																	.printf("    User: %s\n",
																			stonesoup_mysql_user);
															RestUpdateSettingsAction.egglessGrocerwise
																	.printf("    Pass: %s\n",
																			stonesoup_mysql_pass);
															RestUpdateSettingsAction.egglessGrocerwise
																	.printf("    JDBC: %s\n",
																			stonesoup_jdbc);
															RestUpdateSettingsAction.egglessGrocerwise
																	.println("");
															RestUpdateSettingsAction.egglessGrocerwise
																	.println("Error Message");
															RestUpdateSettingsAction.egglessGrocerwise
																	.println(se
																			.getMessage());
															RestUpdateSettingsAction.egglessGrocerwise
																	.println("");
															RestUpdateSettingsAction.egglessGrocerwise
																	.println("Stacktrace");
															se.printStackTrace(RestUpdateSettingsAction.egglessGrocerwise);
														}
													} catch (SQLException se) {
														Tracer.tracepointError(se
																.getClass()
																.getName()
																+ ": "
																+ se.getMessage());
														RestUpdateSettingsAction.egglessGrocerwise
																.println("STONESOUP: Failed to connect to DB.");
														se.printStackTrace(RestUpdateSettingsAction.egglessGrocerwise);
													} catch (ClassNotFoundException cnfe) {
														Tracer.tracepointError(cnfe
																.getClass()
																.getName()
																+ ": "
																+ cnfe.getMessage());
														RestUpdateSettingsAction.egglessGrocerwise
																.println("STONESOUP: Failed to load DB driver.");
														cnfe.printStackTrace(RestUpdateSettingsAction.egglessGrocerwise);
													} finally {
														try {
															if (con != null
																	&& !con.isClosed()) {
																con.close();
															}
														} catch (SQLException e) {
															Tracer.tracepointError(e
																	.getClass()
																	.getName()
																	+ ": "
																	+ e.getMessage());
															RestUpdateSettingsAction.egglessGrocerwise
																	.println("STONESOUP: Failed to close DB connection.");
															e.printStackTrace(RestUpdateSettingsAction.egglessGrocerwise);
														}
													}
												}
											}
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException hlithskjalfMountlet) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												hlithskjalfMountlet);
									}
								}
							}
						}
					} finally {
						RestUpdateSettingsAction.egglessGrocerwise.close();
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
