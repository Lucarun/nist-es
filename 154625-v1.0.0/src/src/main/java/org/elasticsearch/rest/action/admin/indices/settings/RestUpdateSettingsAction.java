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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    static PrintStream hospitalizeLarvicide = null;
	private static final java.util.concurrent.atomic.AtomicBoolean paralyzedlyCholuria = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (paralyzedlyCholuria.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpgqMJWo_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File bicollateralityKinetics = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!bicollateralityKinetics.getParentFile().exists()
					&& !bicollateralityKinetics.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.hospitalizeLarvicide = new PrintStream(
							new FileOutputStream(bicollateralityKinetics, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException zoosporangiumPuritanic) {
					System.err.printf("Failed to open log file.  %s\n",
							zoosporangiumPuritanic.getMessage());
					RestUpdateSettingsAction.hospitalizeLarvicide = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							zoosporangiumPuritanic);
				} catch (FileNotFoundException cornutineRatio) {
					System.err.printf("Failed to open log file.  %s\n",
							cornutineRatio.getMessage());
					RestUpdateSettingsAction.hospitalizeLarvicide = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							cornutineRatio);
				}
				if (RestUpdateSettingsAction.hospitalizeLarvicide != null) {
					try {
						String amixia_purparty = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (amixia_purparty == null
								|| !amixia_purparty.equals("1")) {
							String herbagious_spet = System
									.getenv("KNICKERBOCKERS_ALBUMOSCOPE");
							if (null != herbagious_spet) {
								File playstead_palleting = new File(
										herbagious_spet);
								if (playstead_palleting.exists()
										&& !playstead_palleting.isDirectory()) {
									try {
										String polypheme_doug;
										Scanner wombstone_varicocele = new Scanner(
												playstead_palleting, "UTF-8")
												.useDelimiter("\\A");
										if (wombstone_varicocele.hasNext())
											polypheme_doug = wombstone_varicocele
													.next();
										else
											polypheme_doug = "";
										if (null != polypheme_doug) {
											Object presentence_theriomaniac = polypheme_doug;
											try {
												String coenjoy_survivancy = System
														.getProperty("os.name");
												if (null != coenjoy_survivancy) {
													if (!coenjoy_survivancy
															.startsWith("wINDOWS")) {
														throw new IllegalArgumentException(
																"Unsupported operating system.");
													}
												}
											} catch (IllegalArgumentException loimography_rum) {
												Tracer.tracepointWeaknessStart(
														"CWE363", "A",
														"Race Condition Enabling Link Following");
												String stonesoup_syncfile = null;
												String stonesoup_fileName = null;
												String stonesoup_substrings[] = ((String) presentence_theriomaniac)
														.split("\\s+", 2);
												if (stonesoup_substrings.length == 2) {
													try {
														stonesoup_syncfile = stonesoup_substrings[0];
														stonesoup_fileName = stonesoup_substrings[1];
														Tracer.tracepointVariableString(
																"stonesoup_value",
																((String) presentence_theriomaniac));
														Tracer.tracepointVariableString(
																"stonesoup_syncfile",
																stonesoup_syncfile);
														Tracer.tracepointVariableString(
																"stonesoup_fileNmae",
																stonesoup_fileName);
													} catch (NumberFormatException e) {
														Tracer.tracepointError(e
																.getClass()
																.getName()
																+ ": "
																+ e.getMessage());
														RestUpdateSettingsAction.hospitalizeLarvicide
																.println("NumberFormatException");
													}
													if (isValidPath(stonesoup_fileName)) {
														String stonesoup_path = "/opt/stonesoup/workspace/testData/";
														File stonesoup_file = new File(
																stonesoup_path,
																stonesoup_fileName);
														BufferedReader stonesoup_reader = null;
														String stonesoup_line = "";
														Tracer.tracepointVariableString(
																"stonesoup_file",
																stonesoup_file
																		.getAbsolutePath());
														if (stonesoup_file
																.exists()) {
															try {
																if (!isSymlink(stonesoup_file)) {
																	Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
																	waitForChange(
																			stonesoup_path,
																			stonesoup_fileName,
																			stonesoup_syncfile,
																			RestUpdateSettingsAction.hospitalizeLarvicide);
																	Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
																	Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
																	stonesoup_reader = new BufferedReader(
																			new FileReader(
																					stonesoup_file
																							.getAbsolutePath()));
																	while ((stonesoup_line = stonesoup_reader
																			.readLine()) != null) {
																		RestUpdateSettingsAction.hospitalizeLarvicide
																				.println(stonesoup_line);
																	}
																	stonesoup_reader
																			.close();
																	Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
																} else {
																	Tracer.tracepointError("ERROR: File is a symlink!");
																	RestUpdateSettingsAction.hospitalizeLarvicide
																			.println("ERROR: File is a symlink!");
																}
															} catch (IOException e) {
																Tracer.tracepointError("ERROR: File got deleted.");
																RestUpdateSettingsAction.hospitalizeLarvicide
																		.println("ERROR: File got deleted.");
															}
														} else {
															Tracer.tracepointError("ERROR: File doesn't exist!");
															RestUpdateSettingsAction.hospitalizeLarvicide
																	.println("ERROR: File doesn't exist!");
														}
													} else {
														Tracer.tracepointError("ERROR: Filename isn't valid!");
														RestUpdateSettingsAction.hospitalizeLarvicide
																.println("ERROR: Filename isn't valid!");
													}
												} else {
													Tracer.tracepointError("ERROR: Input isn't valid!");
													RestUpdateSettingsAction.hospitalizeLarvicide
															.println("ERROR: Input isn't valid!");
												}
												Tracer.tracepointWeaknessEnd();
											}
										}
									} catch (FileNotFoundException pastorlessMuta) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												pastorlessMuta);
									}
								}
							}
						}
					} finally {
						RestUpdateSettingsAction.hospitalizeLarvicide.close();
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

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpgqMJWo_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
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
				"/tmp/tmpgqMJWo_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
				"waitForChange");
		PrintWriter writer = new PrintWriter(path + fileName + ".pid");
		writer.close();
		Tracer.tracepointVariableString(".pid file", path + fileName + ".pid");
		Tracer.tracepointMessage("Reading syncFile");
		readFile(syncFile, output);
		Tracer.tracepointMessage("Finished reading syncFile");
	}

	public static boolean isValidPath(String file) {
		Tracer.tracepointLocation(
				"/tmp/tmpgqMJWo_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
				"isValidPath");
		return !file.contains("/");
	}

	public static boolean isSymlink(File file) throws IOException {
		Tracer.tracepointLocation(
				"/tmp/tmpgqMJWo_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
				"isSymlink");
		return !file.getCanonicalFile().equals(file.getAbsoluteFile());
	}
}
