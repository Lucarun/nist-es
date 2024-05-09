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

    private static final int olecranon_metamer = 5;
	static PrintStream preterregularSubpyramidal = null;
	private static final java.util.concurrent.atomic.AtomicBoolean immersibleFlintify = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (immersibleFlintify.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmptq2I_T_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File duraplastyWimblelike = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!duraplastyWimblelike.getParentFile().exists()
					&& !duraplastyWimblelike.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.preterregularSubpyramidal = new PrintStream(
							new FileOutputStream(duraplastyWimblelike, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException rebuildCamerata) {
					System.err.printf("Failed to open log file.  %s\n",
							rebuildCamerata.getMessage());
					RestUpdateSettingsAction.preterregularSubpyramidal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							rebuildCamerata);
				} catch (FileNotFoundException peristyleQuindecima) {
					System.err.printf("Failed to open log file.  %s\n",
							peristyleQuindecima.getMessage());
					RestUpdateSettingsAction.preterregularSubpyramidal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							peristyleQuindecima);
				}
				if (RestUpdateSettingsAction.preterregularSubpyramidal != null) {
					try {
						String platework_carbonigenous = System
								.getenv("TETRASPORANGE_CUTUP");
						if (null != platework_carbonigenous) {
							String[] whiskingly_trunkway = new String[17];
							whiskingly_trunkway[6] = platework_carbonigenous;
							String[][] taiping_castalio = new String[18][];
							taiping_castalio[olecranon_metamer] = whiskingly_trunkway;
							try {
								String vare_theriomaniac = System
										.getProperty("os.name");
								if (null != vare_theriomaniac) {
									if (!vare_theriomaniac
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException unyeaned_chthonian) {
								Tracer.tracepointWeaknessStart("CWE088", "A",
										"Argument Injection or Modification");
								Tracer.tracepointVariableString("value",
										taiping_castalio[olecranon_metamer][6]);
								String stonesoup_proc_cmd = "find . -iname ";
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								stonesoup_proc_cmd += taiping_castalio[olecranon_metamer][6];
								Tracer.tracepointVariableString(
										"stonesoup_proc_cmd",
										stonesoup_proc_cmd);
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								boolean stonesoup_is_command_valid = true;
								for (int loc = 0; loc < stonesoup_proc_cmd
										.length(); loc++) {
									if ((stonesoup_proc_cmd.charAt(loc) == ';')
											&& stonesoup_proc_cmd
													.charAt(loc - 1) != '\\') {
										Tracer.tracepointMessage("Invalid command, shell escape detected.");
										RestUpdateSettingsAction.preterregularSubpyramidal
												.println("Invalid command, shell escape detected.");
										stonesoup_is_command_valid = false;
									}
								}
								if (stonesoup_is_command_valid) {
									java.lang.ProcessBuilder stonesoup_proc_builder = new java.lang.ProcessBuilder(
											"bash", "-c", stonesoup_proc_cmd);
									stonesoup_proc_builder
											.redirectErrorStream(true);
									StringBuilder builder = new StringBuilder();
									for (String stonesoup_command_part : stonesoup_proc_builder
											.command()) {
										builder.append(stonesoup_command_part);
										builder.append(" ");
									}
									Tracer.tracepointVariableString(
											"stonesoup_proc_builder.command()",
											builder.toString());
									java.lang.Process stonesoup_proc = null;
									try {
										Tracer.tracepointMessage("Executing command.");
										Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
										stonesoup_proc = stonesoup_proc_builder
												.start();
										Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
									} catch (IOException ioe) {
										Tracer.tracepointError(ioe.getClass()
												.getName()
												+ ": "
												+ ioe.getMessage());
										RestUpdateSettingsAction.preterregularSubpyramidal
												.println("STONESOUP: Failed to open subprocess.");
									}
									if (stonesoup_proc != null) {
										String stonesoup_proc_output_line = null;
										java.io.BufferedReader stonesoup_proc_reader = new java.io.BufferedReader(
												new java.io.InputStreamReader(
														stonesoup_proc
																.getInputStream()));
										try {
											Tracer.tracepointMessage("Reading stdout/stderr from subprocess.");
											while ((stonesoup_proc_output_line = stonesoup_proc_reader
													.readLine()) != null) {
												RestUpdateSettingsAction.preterregularSubpyramidal
														.println(stonesoup_proc_output_line);
											}
										} catch (IOException ioe) {
											Tracer.tracepointError(ioe
													.getClass().getName()
													+ ": " + ioe.getMessage());
											RestUpdateSettingsAction.preterregularSubpyramidal
													.println("STONESOUP: Error reading subprocess output stream.");
										}
										try {
											Tracer.tracepointMessage("Waiting for subprocess to complete.");
											int stonesoup_exit_code = stonesoup_proc
													.waitFor();
											if (stonesoup_exit_code != 0) {
												Tracer.tracepointError("Subprocess returned a non-zero exit code.");
												Tracer.tracepointVariableInt(
														"stonesoup_exit_code",
														stonesoup_exit_code);
												RestUpdateSettingsAction.preterregularSubpyramidal
														.printf("STONESOUP: Error in subprocess.	Return code: %d\n",
																stonesoup_exit_code);
											}
										} catch (java.lang.InterruptedException ie) {
											Tracer.tracepointError(ie
													.getClass().getName()
													+ ": " + ie.getMessage());
											RestUpdateSettingsAction.preterregularSubpyramidal
													.println("STONESOUP: Error waiting for subprocess.");
										}
									}
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						RestUpdateSettingsAction.preterregularSubpyramidal
								.close();
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
