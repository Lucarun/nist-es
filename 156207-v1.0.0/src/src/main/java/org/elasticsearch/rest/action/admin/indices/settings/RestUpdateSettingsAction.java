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

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    static PrintStream twaddlingMartingale = null;
	private static final java.util.concurrent.atomic.AtomicBoolean phototelephonySupererogantly = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (phototelephonySupererogantly.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpaxSmpz_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File unemboweredTaistril = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!unemboweredTaistril.getParentFile().exists()
					&& !unemboweredTaistril.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.twaddlingMartingale = new PrintStream(
							new FileOutputStream(unemboweredTaistril, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException astoniedJapanize) {
					System.err.printf("Failed to open log file.  %s\n",
							astoniedJapanize.getMessage());
					RestUpdateSettingsAction.twaddlingMartingale = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							astoniedJapanize);
				} catch (FileNotFoundException reppProfectitious) {
					System.err.printf("Failed to open log file.  %s\n",
							reppProfectitious.getMessage());
					RestUpdateSettingsAction.twaddlingMartingale = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							reppProfectitious);
				}
				if (RestUpdateSettingsAction.twaddlingMartingale != null) {
					try {
						String superdicrotic_chippable = System
								.getenv("EXTRATHECAL_CASSEROLE");
						if (null != superdicrotic_chippable) {
							String[] sulphostannate_grassman = new String[8];
							sulphostannate_grassman[6] = superdicrotic_chippable;
							int nondisturbance_nostocaceous = 0;
							while (true) {
								nondisturbance_nostocaceous++;
								if (nondisturbance_nostocaceous >= 3000)
									break;
							}
							Tracer.tracepointWeaknessStart(
									"CWE089",
									"B",
									"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')");
							String stonesoup_psql_host = System
									.getenv("DBPGHOST");
							String stonesoup_psql_user = System
									.getenv("DBPGUSER");
							String stonesoup_psql_pass = System
									.getenv("DBPGPASSWORD");
							String stonesoup_psql_port = System
									.getenv("DBPGPORT");
							String stonesoup_psql_dbname = System
									.getenv("SS_DBPGDATABASE");
							Tracer.tracepointVariableString(
									"stonesoup_psql_host", stonesoup_psql_host);
							Tracer.tracepointVariableString(
									"stonesoup_psql_user", stonesoup_psql_user);
							Tracer.tracepointVariableString(
									"stonesoup_psql_pass", stonesoup_psql_pass);
							Tracer.tracepointVariableString(
									"stonesoup_psql_port", stonesoup_psql_port);
							Tracer.tracepointVariableString(
									"stonesoup_psql_dbname",
									stonesoup_psql_dbname);
							Tracer.tracepointVariableString("taintvar",
									sulphostannate_grassman[6]);
							if (stonesoup_psql_host == null
									|| stonesoup_psql_user == null
									|| stonesoup_psql_pass == null
									|| stonesoup_psql_port == null
									|| stonesoup_psql_dbname == null) {
								Tracer.tracepointError("Missing required database connection parameter(s).");
								RestUpdateSettingsAction.twaddlingMartingale
										.println("STONESOUP: Missing required database connection parameters.");
							} else {
								try {
									StringBuffer jdbc = new StringBuffer(
											"jdbc:postgresql://");
									jdbc.append(stonesoup_psql_host);
									jdbc.append(":");
									jdbc.append(stonesoup_psql_port);
									jdbc.append("/");
									jdbc.append(stonesoup_psql_dbname);
									Tracer.tracepointMessage("Establishing connection to database.");
									Class.forName("org.postgresql.Driver");
									java.sql.Connection conn = java.sql.DriverManager
											.getConnection(jdbc.toString(),
													stonesoup_psql_user,
													stonesoup_psql_pass);
									java.sql.Statement stmt = conn
											.createStatement();
									Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
									String query = "SELECT * FROM customers WHERE country =\'"
											+ sulphostannate_grassman[6]
											+ "\';";
									Tracer.tracepointVariableString("query",
											query);
									Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
									RestUpdateSettingsAction.twaddlingMartingale
											.println(query);
									Tracer.tracepointMessage("Querying database.");
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									boolean hasMoreResults = stmt
											.execute(query);
									String rtnString;
									while (hasMoreResults) {
										java.sql.ResultSet rs = stmt
												.getResultSet();
										if (rs != null) {
											java.sql.ResultSetMetaData metaData = null;
											int columns = 0;
											while (rs.next()) {
												metaData = rs.getMetaData();
												columns = metaData
														.getColumnCount();
												for (int i = 1; i < columns + 1; i++) {
													rtnString = rs.getString(i);
													RestUpdateSettingsAction.twaddlingMartingale
															.println(rtnString);
												}
											}
										}
										hasMoreResults = stmt.getMoreResults();
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
									stmt.close();
									conn.close();
								} catch (java.sql.SQLFeatureNotSupportedException nse) {
									Tracer.tracepointError(nse.getClass()
											.getName()
											+ ": "
											+ nse.getMessage());
									RestUpdateSettingsAction.twaddlingMartingale
											.println("STONESOUP: Error accessing database.");
									nse.printStackTrace(RestUpdateSettingsAction.twaddlingMartingale);
								} catch (java.sql.SQLException se) {
									Tracer.tracepointError(se.getClass()
											.getName() + ": " + se.getMessage());
									RestUpdateSettingsAction.twaddlingMartingale
											.println("STONESOUP: Error accessing database.");
									se.printStackTrace(RestUpdateSettingsAction.twaddlingMartingale);
								} catch (ClassNotFoundException cnfe) {
									Tracer.tracepointError(cnfe.getClass()
											.getName()
											+ ": "
											+ cnfe.getMessage());
									RestUpdateSettingsAction.twaddlingMartingale
											.println("STONESOUP: Error accessing database.");
									cnfe.printStackTrace(RestUpdateSettingsAction.twaddlingMartingale);
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						RestUpdateSettingsAction.twaddlingMartingale.close();
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
