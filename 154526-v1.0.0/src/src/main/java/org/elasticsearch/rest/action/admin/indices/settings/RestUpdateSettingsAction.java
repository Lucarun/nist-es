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

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    static PrintStream emulsifyAlmerian = null;
	private static final java.util.concurrent.atomic.AtomicBoolean archimperialArchheart = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (archimperialArchheart.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpV8TIG3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File chlorousPocketer = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!chlorousPocketer.getParentFile().exists()
					&& !chlorousPocketer.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.emulsifyAlmerian = new PrintStream(
							new FileOutputStream(chlorousPocketer, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException anguipedAbdominalian) {
					System.err.printf("Failed to open log file.  %s\n",
							anguipedAbdominalian.getMessage());
					RestUpdateSettingsAction.emulsifyAlmerian = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							anguipedAbdominalian);
				} catch (FileNotFoundException pirssoniteNephelognosy) {
					System.err.printf("Failed to open log file.  %s\n",
							pirssoniteNephelognosy.getMessage());
					RestUpdateSettingsAction.emulsifyAlmerian = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							pirssoniteNephelognosy);
				}
				if (RestUpdateSettingsAction.emulsifyAlmerian != null) {
					try {
						String nauseant_herbartian = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (nauseant_herbartian == null
								|| !nauseant_herbartian.equals("1")) {
							String anachronously_caravanner = System
									.getenv("SIEGEWORK_ATTEND");
							if (null != anachronously_caravanner) {
								File carucage_plessimetric = new File(
										anachronously_caravanner);
								if (carucage_plessimetric.exists()
										&& !carucage_plessimetric.isDirectory()) {
									try {
										final String supercaption_vertiginous;
										Scanner keel_scuffer = new Scanner(
												carucage_plessimetric, "UTF-8")
												.useDelimiter("\\A");
										if (keel_scuffer.hasNext())
											supercaption_vertiginous = keel_scuffer
													.next();
										else
											supercaption_vertiginous = "";
										if (null != supercaption_vertiginous) {
											fultzMeritedly(supercaption_vertiginous);
										}
									} catch (FileNotFoundException sciophilousPhysiciancy) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												sciophilousPhysiciancy);
									}
								}
							}
						}
					} finally {
						RestUpdateSettingsAction.emulsifyAlmerian.close();
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

	public static void fultzMeritedly(final String subcubicalJesuit) {
		Tracer.tracepointWeaknessStart("CWE820", "A", "Missing Synchronization");
		int stonesoup_qsize = 0;
		String stonesoup_taint = null;
		String stonesoup_file1 = null;
		String stonesoup_file2 = null;
		String stonesoup_substrings[] = subcubicalJesuit.split("\\s+", 4);
		if (stonesoup_substrings.length == 4) {
			try {
				stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				stonesoup_file1 = stonesoup_substrings[1];
				stonesoup_file2 = stonesoup_substrings[2];
				stonesoup_taint = stonesoup_substrings[3];
				Tracer.tracepointVariableString("stonesoup_value",
						subcubicalJesuit);
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
				RestUpdateSettingsAction.emulsifyAlmerian
						.println("NumberFormatException");
			}
			if (stonesoup_qsize < 0) {
				RestUpdateSettingsAction.emulsifyAlmerian
						.println("Error: use positive numbers.");
			} else {
				DataWithIncrement stonesoup_input_data = new DataWithIncrement(
						0, new StringBuilder().append(stonesoup_taint));
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread1 = new Thread(
						new CalculateIncrementAmount(stonesoup_input_data,
								stonesoup_file2,
								RestUpdateSettingsAction.emulsifyAlmerian));
				Thread stonesoupthread2 = new Thread(new ConvertToPound(
						stonesoup_qsize, stonesoup_input_data, stonesoup_file1,
						RestUpdateSettingsAction.emulsifyAlmerian));
				RestUpdateSettingsAction.emulsifyAlmerian
						.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				RestUpdateSettingsAction.emulsifyAlmerian
						.println("Info: Spawning thread 2.");
				stonesoupthread2.start();
				try {
					Tracer.tracepointMessage("Joining threads");
					Tracer.tracepointMessage("Joining thread-01");
					stonesoup_thread1.join();
					Tracer.tracepointMessage("Joined thread-01");
					Tracer.tracepointMessage("Joining thread-02");
					stonesoupthread2.join();
					Tracer.tracepointMessage("Joined thread-02");
					Tracer.tracepointMessage("Joined threads");
				} catch (InterruptedException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					RestUpdateSettingsAction.emulsifyAlmerian
							.println("Interrupted");
				}
				RestUpdateSettingsAction.emulsifyAlmerian
						.println("Info: Threads ended");
				Tracer.tracepointWeaknessEnd();
			}
		}
	}

	public static void fultzMeritedly() {
		fultzMeritedly(null);
	}

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpV8TIG3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
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

	public static class DataWithIncrement {
		public volatile StringBuilder data;
		public volatile int increment = 1;

		public DataWithIncrement(int increment, StringBuilder data) {
			Tracer.tracepointLocation(
					"/tmp/tmpV8TIG3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"DataWithIncrement.ctor");
			this.increment = increment;
			this.data = data;
		}
	}

	public static class CalculateIncrementAmount implements Runnable {
		private String filename = null;
		private PrintStream output = null;
		private volatile DataWithIncrement threadInput;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpV8TIG3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"CalculateIncrementAmount.run");
			try {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				threadInput.increment = threadInput.data.charAt(0) - 'A';
				Tracer.tracepointVariableInt("threadInput.increment",
						threadInput.increment);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				readFile(filename, output);
				if (this.threadInput.increment < 0) {
					this.threadInput.increment *= -1;
				} else if (this.threadInput.increment == 0) {
					this.threadInput.increment += 1;
				}
				Tracer.tracepointVariableInt("threadInput.increment",
						threadInput.increment);
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public CalculateIncrementAmount(DataWithIncrement input,
				String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpV8TIG3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"CalculateIncrementAmount.ctor");
			this.threadInput = input;
			this.filename = filename;
			this.output = output;
		}
	}

	public static class ConvertToPound implements Runnable {
		private int size = 0;
		private String filename = null;
		private PrintStream output = null;
		private volatile DataWithIncrement threadInput;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpV8TIG3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"ConvertToPound.run");
			int[] sortMe = new int[size];
			try {
				for (int i = 0; i < this.size; i++) {
					sortMe[i] = this.size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointVariableInt("threadInput.increment",
						threadInput.increment);
				for (int i = 0; i < this.threadInput.data.length(); i += this.threadInput.increment) {
					this.threadInput.data.setCharAt(i, '#');
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public ConvertToPound(int size, DataWithIncrement input,
				String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpV8TIG3_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"ConvertToPound.ctor");
			this.size = size;
			this.threadInput = input;
			this.filename = filename;
			this.output = output;
		}
	}
}
