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
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    private static final int wore_unreefed = 9;
	static PrintStream janeTowhee = null;
	private static final java.util.concurrent.atomic.AtomicBoolean gallflyKoulan = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (gallflyKoulan.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpj04FTk_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File scapulopexyUnderclerk = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!scapulopexyUnderclerk.getParentFile().exists()
					&& !scapulopexyUnderclerk.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.janeTowhee = new PrintStream(
							new FileOutputStream(scapulopexyUnderclerk, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException staliniteYawny) {
					System.err.printf("Failed to open log file.  %s\n",
							staliniteYawny.getMessage());
					RestUpdateSettingsAction.janeTowhee = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							staliniteYawny);
				} catch (FileNotFoundException oundsKamachile) {
					System.err.printf("Failed to open log file.  %s\n",
							oundsKamachile.getMessage());
					RestUpdateSettingsAction.janeTowhee = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							oundsKamachile);
				}
				if (RestUpdateSettingsAction.janeTowhee != null) {
					try {
						String subengineer_mistigris = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (subengineer_mistigris == null
								|| !subengineer_mistigris.equals("1")) {
							String heatful_insolubly = System
									.getenv("CEPHALOMETRY_SCULCH");
							if (null != heatful_insolubly) {
								File acrogenously_hamleted = new File(
										heatful_insolubly);
								if (acrogenously_hamleted.exists()
										&& !acrogenously_hamleted.isDirectory()) {
									try {
										String volational_dinginess;
										Scanner kreutzer_turriferous = new Scanner(
												acrogenously_hamleted, "UTF-8")
												.useDelimiter("\\A");
										if (kreutzer_turriferous.hasNext())
											volational_dinginess = kreutzer_turriferous
													.next();
										else
											volational_dinginess = "";
										if (null != volational_dinginess) {
											String[] hyperdiapason_emetically = new String[23];
											hyperdiapason_emetically[wore_unreefed] = volational_dinginess;
											endosteomaAronia(hyperdiapason_emetically);
										}
									} catch (FileNotFoundException loseFunereally) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												loseFunereally);
									}
								}
							}
						}
					} finally {
						RestUpdateSettingsAction.janeTowhee.close();
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

	public void endosteomaAronia(String[] faldage_irremunerable) {
		Tracer.tracepointWeaknessStart("CWE414", "A", "Missing Lock Check");
		int stonesoup_qsize = 0;
		String stonesoup_taint = null;
		String stonesoup_file1 = null;
		String stonesoup_file2 = null;
		String stonesoup_substrings[] = faldage_irremunerable[wore_unreefed]
				.split("\\s+", 4);
		if (stonesoup_substrings.length == 4) {
			try {
				stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				stonesoup_file1 = stonesoup_substrings[1];
				stonesoup_file2 = stonesoup_substrings[2];
				stonesoup_taint = stonesoup_substrings[3];
				Tracer.tracepointVariableString("stonesoup_value",
						faldage_irremunerable[wore_unreefed]);
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointVariableString("stonesoup_file1",
						stonesoup_file1);
				Tracer.tracepointVariableString("stonesoup_file2",
						stonesoup_file2);
				Tracer.tracepointVariableString("stonesoup_taint",
						stonesoup_taint);
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				RestUpdateSettingsAction.janeTowhee
						.println("NumberFormatException");
			}
			if (stonesoup_qsize < 0) {
				RestUpdateSettingsAction.janeTowhee
						.println("Error: use positive numbers.");
			} else {
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread1 = new Thread(new toCap(
						stonesoup_qsize, stonesoup_file1,
						RestUpdateSettingsAction.janeTowhee));
				Thread stonesoup_thread2 = new Thread(new delNonAlpha(
						stonesoup_file2, RestUpdateSettingsAction.janeTowhee));
				stonesoup_threadInput = new StringBuilder()
						.append(stonesoup_taint);
				RestUpdateSettingsAction.janeTowhee
						.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				RestUpdateSettingsAction.janeTowhee
						.println("Info: Spawning thread 2.");
				stonesoup_thread2.start();
				try {
					Tracer.tracepointMessage("Joining threads");
					Tracer.tracepointMessage("Joining thread-01");
					stonesoup_thread1.join();
					Tracer.tracepointMessage("Joined thread-01");
					Tracer.tracepointMessage("Joining thread-02");
					stonesoup_thread2.join();
					Tracer.tracepointMessage("Joined thread-02");
					Tracer.tracepointMessage("Joined threads");
				} catch (InterruptedException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					RestUpdateSettingsAction.janeTowhee.println("Interrupted");
				}
				RestUpdateSettingsAction.janeTowhee
						.println("Info: Threads ended");
			}
		}
		Tracer.tracepointWeaknessEnd();
	}

	private static ReentrantLock lock = new ReentrantLock();
	private static StringBuilder stonesoup_threadInput;

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpj04FTk_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
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

	public static class delNonAlpha implements Runnable {
		public String filename = null;
		public PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpj04FTk_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"delNonAlpha.run");
			StringBuilder temp = new StringBuilder();
			try {
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					if (Character.isLetter(stonesoup_threadInput.charAt(i))) {
						temp.append(stonesoup_threadInput.charAt(i));
					}
				}
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				stonesoup_threadInput = null;
				Tracer.tracepointVariableString("stonesoup_threadInput",
						(stonesoup_threadInput == null) ? "(null)"
								: stonesoup_threadInput.toString());
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				readFile(filename, output);
				stonesoup_threadInput = temp;
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public delNonAlpha(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpj04FTk_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"delNonAlpha.ctor");
			this.filename = filename;
			this.output = output;
		}
	}

	public static class toCap implements Runnable {
		public int size = 0;
		public String filename = null;
		public PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpj04FTk_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"toCap.run");
			try {
				int[] sortMe = new int[size];
				lock.lock();
				for (int i = 0; i < size; i++) {
					sortMe[i] = size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				stonesoup_threadInput = new StringBuilder()
						.append(stonesoup_threadInput.toString().toUpperCase());
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				lock.unlock();
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public toCap(int size, String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpj04FTk_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"toCap.ctor");
			this.size = size;
			this.filename = filename;
			this.output = output;
		}
	}
}
