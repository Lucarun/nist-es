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

    static PrintStream ocoteaMetroscirrhus = null;
	private static final java.util.concurrent.atomic.AtomicBoolean chologeneticTref = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (chologeneticTref.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp1YeHmS_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File watchtowerLaunce = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!watchtowerLaunce.getParentFile().exists()
					&& !watchtowerLaunce.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.ocoteaMetroscirrhus = new PrintStream(
							new FileOutputStream(watchtowerLaunce, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException platymeterProroyal) {
					System.err.printf("Failed to open log file.  %s\n",
							platymeterProroyal.getMessage());
					RestUpdateSettingsAction.ocoteaMetroscirrhus = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							platymeterProroyal);
				} catch (FileNotFoundException stealyChipping) {
					System.err.printf("Failed to open log file.  %s\n",
							stealyChipping.getMessage());
					RestUpdateSettingsAction.ocoteaMetroscirrhus = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							stealyChipping);
				}
				if (RestUpdateSettingsAction.ocoteaMetroscirrhus != null) {
					try {
						final String frankeniaceae_descending = System
								.getenv("BEARTONGUE_ALLELOTROPISM");
						if (null != frankeniaceae_descending) {
							final int stromal_abusious;
							try {
								stromal_abusious = Integer
										.parseInt(frankeniaceae_descending);
							} catch (NumberFormatException nonappraisal_incurse) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										nonappraisal_incurse);
							}
							final int[] unsanctimonious_dharma = new int[18];
							unsanctimonious_dharma[15] = stromal_abusious;
							mercaptidesUnlanded(unsanctimonious_dharma);
						}
					} finally {
						RestUpdateSettingsAction.ocoteaMetroscirrhus.close();
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

	public static void mercaptidesUnlanded(final int[] heterostylismTepid) {
		Tracer.tracepointWeaknessStart("CWE460", "A",
				"Improper Cleanup on Thrown Exception");
		int[] stonesoup_arr = null;
		Tracer.tracepointVariableInt("size", heterostylismTepid[15]);
		Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
		try {
			RestUpdateSettingsAction.ocoteaMetroscirrhus.printf(
					"Allocating array of size %d\n", heterostylismTepid[15]);
			stonesoup_arr = new int[heterostylismTepid[15]];
		} catch (java.lang.OutOfMemoryError e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			stonesoup_arr = new int[100];
		}
		Tracer.tracepointBufferInfo("stonesoup_arr", stonesoup_arr.length,
				"Length of stonesoup_arr");
		Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
		try {
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			int i = heterostylismTepid[15] - 1;
			do {
				stonesoup_arr[i--] = i;
			} while (i > 0);
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		} catch (RuntimeException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			e.printStackTrace(RestUpdateSettingsAction.ocoteaMetroscirrhus);
			throw e;
		}
		Tracer.tracepointWeaknessEnd();
	}

	public static void mercaptidesUnlanded() {
		mercaptidesUnlanded(null);
	}
}
