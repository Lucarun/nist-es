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

    static PrintStream repackRhenish = null;
	private static final java.util.concurrent.atomic.AtomicBoolean weatherlyBuddleman = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (weatherlyBuddleman.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmptJ6mba_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File rabblementTonoplast = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!rabblementTonoplast.getParentFile().exists()
					&& !rabblementTonoplast.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.repackRhenish = new PrintStream(
							new FileOutputStream(rabblementTonoplast, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException descenderButterjags) {
					System.err.printf("Failed to open log file.  %s\n",
							descenderButterjags.getMessage());
					RestUpdateSettingsAction.repackRhenish = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							descenderButterjags);
				} catch (FileNotFoundException conqueringStagger) {
					System.err.printf("Failed to open log file.  %s\n",
							conqueringStagger.getMessage());
					RestUpdateSettingsAction.repackRhenish = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							conqueringStagger);
				}
				if (RestUpdateSettingsAction.repackRhenish != null) {
					try {
						final String ammophila_pentagon = System
								.getenv("PREVIGILANTLY_ACETOXIME");
						if (null != ammophila_pentagon) {
							final short supracensorious_malobservance;
							try {
								supracensorious_malobservance = Short
										.parseShort(ammophila_pentagon);
							} catch (NumberFormatException unwarbled_telpherway) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										unwarbled_telpherway);
							}
							Tracer.tracepointWeaknessStart("CWE190", "B",
									"Integer Overflow or Wraparound");
							short stonesoup_checked_value = supracensorious_malobservance;
							Tracer.tracepointVariableShort(
									"stonesoup_checked_value",
									stonesoup_checked_value);
							if (stonesoup_checked_value <= 0) {
								stonesoup_checked_value = 1;
								RestUpdateSettingsAction.repackRhenish
										.println("resetting value to 1");
							}
							Tracer.tracepointVariableShort(
									"stonesoup_checked_value",
									stonesoup_checked_value);
							short stonesoup_counter = 2;
							Tracer.tracepointVariableShort("stonesoup_counter",
									stonesoup_counter);
							Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
							int lttngCtr = 99;
							while (stonesoup_counter < 10) {
								RestUpdateSettingsAction.repackRhenish
										.println("Loop #" + stonesoup_counter);
								if (stonesoup_counter > 0) {
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									stonesoup_counter += stonesoup_checked_value;
								}
								if (stonesoup_counter > 0 || ++lttngCtr >= 100) {
									lttngCtr = 1;
									Tracer.tracepointVariableShort(
											"stonesoup_counter",
											stonesoup_counter);
								}
							}
							Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
							Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
							Tracer.tracepointVariableShort("stonesoup_counter",
									stonesoup_counter);
							RestUpdateSettingsAction.repackRhenish
									.println("finished evaluating");
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						RestUpdateSettingsAction.repackRhenish.close();
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
