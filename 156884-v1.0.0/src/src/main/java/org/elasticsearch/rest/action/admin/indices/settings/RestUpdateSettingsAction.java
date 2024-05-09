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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    private static final int trivalve_wots = 15;
	static PrintStream overdearnessReasoner = null;
	private static final java.util.concurrent.atomic.AtomicBoolean connectedMenuridae = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (connectedMenuridae.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpek8UnS_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File armenianNorthward = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!armenianNorthward.getParentFile().exists()
					&& !armenianNorthward.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.overdearnessReasoner = new PrintStream(
							new FileOutputStream(armenianNorthward, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException unbaronetTransfix) {
					System.err.printf("Failed to open log file.  %s\n",
							unbaronetTransfix.getMessage());
					RestUpdateSettingsAction.overdearnessReasoner = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unbaronetTransfix);
				} catch (FileNotFoundException feloniousTristam) {
					System.err.printf("Failed to open log file.  %s\n",
							feloniousTristam.getMessage());
					RestUpdateSettingsAction.overdearnessReasoner = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							feloniousTristam);
				}
				if (RestUpdateSettingsAction.overdearnessReasoner != null) {
					try {
						String unvoidable_crumpled = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (unvoidable_crumpled == null
								|| !unvoidable_crumpled.equals("1")) {
							String austrogaean_erianthus = System
									.getenv("HETEROTOPIA_ACCIDENT");
							if (null != austrogaean_erianthus) {
								File monochromatism_tackled = new File(
										austrogaean_erianthus);
								if (monochromatism_tackled.exists()
										&& !monochromatism_tackled
												.isDirectory()) {
									try {
										String homologize_taoism;
										Scanner parochiality_stopback = new Scanner(
												monochromatism_tackled, "UTF-8")
												.useDelimiter("\\A");
										if (parochiality_stopback.hasNext())
											homologize_taoism = parochiality_stopback
													.next();
										else
											homologize_taoism = "";
										if (null != homologize_taoism) {
											String[] charleen_papyrographic = new String[25];
											charleen_papyrographic[trivalve_wots] = homologize_taoism;
											try {
												String townsite_bromhydric = System
														.getProperty("os.name");
												if (null != townsite_bromhydric) {
													if (!townsite_bromhydric
															.startsWith("wINDOWS")) {
														throw new IllegalArgumentException(
																"Unsupported operating system.");
													}
												}
											} catch (IllegalArgumentException auxiliary_sourishly) {
											} finally {
												Tracer.tracepointWeaknessStart(
														"CWE606", "A",
														"Unchecked Input for Loop Condition");
												String valueString = charleen_papyrographic[trivalve_wots]
														.trim();
												Pattern stonesoup_rel_path_pattern = Pattern
														.compile("(^|/)\\.\\.?/");
												Matcher rel_path_match = stonesoup_rel_path_pattern
														.matcher(valueString);
												Tracer.tracepointVariableString(
														"value",
														charleen_papyrographic[trivalve_wots]);
												Tracer.tracepointVariableString(
														"valueString",
														valueString);
												if (valueString.length() == 0
														|| valueString
																.startsWith("/")
														|| rel_path_match
																.find()) {
													RestUpdateSettingsAction.overdearnessReasoner
															.println("Path traversal identified, discarding request.");
												} else {
													Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
													java.io.File checkedPath = new java.io.File(
															valueString);
													Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
													Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
													while (!checkedPath
															.isFile()) {
														try {
															RestUpdateSettingsAction.overdearnessReasoner
																	.printf("File \"%s\" does not exist, sleeping...\n",
																			valueString);
															Thread.sleep(500);
														} catch (InterruptedException e) {
															Tracer.tracepointError(e
																	.getClass()
																	.getName()
																	+ ": "
																	+ e.getMessage());
															RestUpdateSettingsAction.overdearnessReasoner
																	.println("Thread interrupted.");
															e.printStackTrace(RestUpdateSettingsAction.overdearnessReasoner);
														}
													}
													Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
													RestUpdateSettingsAction.overdearnessReasoner
															.println("Found file.");
													RestUpdateSettingsAction.overdearnessReasoner
															.printf("Reading \"%s\".\n",
																	checkedPath
																			.getPath());
													java.io.BufferedReader reader = null;
													try {
														java.io.FileInputStream fis = new java.io.FileInputStream(
																checkedPath);
														reader = new java.io.BufferedReader(
																new java.io.InputStreamReader(
																		fis));
														String line;
														while ((line = reader
																.readLine()) != null) {
															RestUpdateSettingsAction.overdearnessReasoner
																	.println(line);
														}
													} catch (java.io.FileNotFoundException e) {
														Tracer.tracepointError(e
																.getClass()
																.getName()
																+ ": "
																+ e.getMessage());
														RestUpdateSettingsAction.overdearnessReasoner
																.printf("File \"%s\" does not exist\n",
																		checkedPath
																				.getPath());
													} catch (java.io.IOException ioe) {
														Tracer.tracepointError(ioe
																.getClass()
																.getName()
																+ ": "
																+ ioe.getMessage());
														RestUpdateSettingsAction.overdearnessReasoner
																.println("Failed to read file.");
													} finally {
														try {
															if (reader != null) {
																reader.close();
															}
														} catch (java.io.IOException e) {
															RestUpdateSettingsAction.overdearnessReasoner
																	.println("STONESOUP: Closing file quietly.");
														}
													}
												}
												Tracer.tracepointWeaknessEnd();
											}
										}
									} catch (FileNotFoundException platanaceaeGleaminess) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												platanaceaeGleaminess);
									}
								}
							}
						}
					} finally {
						RestUpdateSettingsAction.overdearnessReasoner.close();
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
