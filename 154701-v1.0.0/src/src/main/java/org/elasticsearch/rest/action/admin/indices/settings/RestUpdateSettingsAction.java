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

    static PrintStream pedanticalnessAdjuster = null;
	private static final java.util.concurrent.atomic.AtomicBoolean rottenstoneSubperiosteal = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (rottenstoneSubperiosteal.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpG9qZQp_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			File horometryAmyotrophy = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!horometryAmyotrophy.getParentFile().exists()
					&& !horometryAmyotrophy.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					RestUpdateSettingsAction.pedanticalnessAdjuster = new PrintStream(
							new FileOutputStream(horometryAmyotrophy, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException uncelestializedIreful) {
					System.err.printf("Failed to open log file.  %s\n",
							uncelestializedIreful.getMessage());
					RestUpdateSettingsAction.pedanticalnessAdjuster = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							uncelestializedIreful);
				} catch (FileNotFoundException hypophyllContinency) {
					System.err.printf("Failed to open log file.  %s\n",
							hypophyllContinency.getMessage());
					RestUpdateSettingsAction.pedanticalnessAdjuster = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							hypophyllContinency);
				}
				if (RestUpdateSettingsAction.pedanticalnessAdjuster != null) {
					try {
						String tubinarial_unmuscled = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (tubinarial_unmuscled == null
								|| !tubinarial_unmuscled.equals("1")) {
							String denumerable_gripy = System
									.getenv("MISTIC_ZOOMASTIGINA");
							if (null != denumerable_gripy) {
								File cobblerfish_coplanarity = new File(
										denumerable_gripy);
								if (cobblerfish_coplanarity.exists()
										&& !cobblerfish_coplanarity
												.isDirectory()) {
									try {
										String amargoso_eventuality;
										Scanner jersey_stickball = new Scanner(
												cobblerfish_coplanarity,
												"UTF-8").useDelimiter("\\A");
										if (jersey_stickball.hasNext())
											amargoso_eventuality = jersey_stickball
													.next();
										else
											amargoso_eventuality = "";
										if (null != amargoso_eventuality) {
											Object erratical_reheater = amargoso_eventuality;
											sapphistDarst(3, null, null, null,
													erratical_reheater, null,
													null);
										}
									} catch (FileNotFoundException chafferCyclonical) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												chafferCyclonical);
									}
								}
							}
						}
					} finally {
						RestUpdateSettingsAction.pedanticalnessAdjuster.close();
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

	public void sapphistDarst(int tylosteresisUnwatchfully,
			Object... gastrophrenicFeminate) {
		Object bikiniLamel = null;
		int guastallineFarmyardy = 0;
		for (guastallineFarmyardy = 0; guastallineFarmyardy < gastrophrenicFeminate.length; guastallineFarmyardy++) {
			if (guastallineFarmyardy == tylosteresisUnwatchfully)
				bikiniLamel = gastrophrenicFeminate[guastallineFarmyardy];
		}
		boolean odograph_novelette = false;
		capricious_nowise: for (int tawdry_shrubland = 0; tawdry_shrubland < 10; tawdry_shrubland++)
			for (int pneumopyothorax_outlimn = 0; pneumopyothorax_outlimn < 10; pneumopyothorax_outlimn++)
				if (tawdry_shrubland * pneumopyothorax_outlimn == 63) {
					odograph_novelette = true;
					break capricious_nowise;
				}
		Tracer.tracepointWeaknessStart("CWE609", "A", "Double-Checked Locking");
		int stonesoup_qsize = 0;
		String stonesoup_taint = null;
		String stonesoup_file1 = null;
		String stonesoup_file2 = null;
		String stonesoup_substrings[] = ((String) bikiniLamel).split("\\s+", 4);
		if (stonesoup_substrings.length == 4) {
			try {
				stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				stonesoup_file1 = stonesoup_substrings[1];
				stonesoup_file2 = stonesoup_substrings[2];
				stonesoup_taint = stonesoup_substrings[3];
				Tracer.tracepointVariableString("stonesoup_value",
						((String) bikiniLamel));
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
				RestUpdateSettingsAction.pedanticalnessAdjuster
						.println("NumberFormatException");
			}
			if (stonesoup_qsize < 0) {
				RestUpdateSettingsAction.pedanticalnessAdjuster
						.println("Error: use positive numbers.");
			} else {
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread1 = new Thread(new doStuff(
						stonesoup_taint, stonesoup_qsize, stonesoup_file2,
						RestUpdateSettingsAction.pedanticalnessAdjuster));
				Thread stonesoup_thread2 = new Thread(new doStuff2(
						stonesoup_taint, stonesoup_qsize, stonesoup_file1,
						RestUpdateSettingsAction.pedanticalnessAdjuster));
				RestUpdateSettingsAction.pedanticalnessAdjuster
						.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				RestUpdateSettingsAction.pedanticalnessAdjuster
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
					RestUpdateSettingsAction.pedanticalnessAdjuster
							.println("Interrupted");
				}
				RestUpdateSettingsAction.pedanticalnessAdjuster
						.println("Info: Threads ended");
			}
		}
		Tracer.tracepointWeaknessEnd();
	}

	private static ReentrantLock lock = new ReentrantLock();

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpG9qZQp_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
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

	public static class Stonesoup_Str {
		public static StringBuilder data = null;
		public static int size = -1;
	}

	public static void init_Stonesoup_Str(String data, int qsize,
			String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpG9qZQp_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
				"init_Stonesoup_Str");
		output.println(Thread.currentThread().getId()
				+ ": In init_Stonesoup_Str");
		if (Stonesoup_Str.data == null) {
			lock.lock();
			if (Stonesoup_Str.data == null) {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				Stonesoup_Str.data = new StringBuilder();
				Stonesoup_Str.size = data.length();
				output.println(Thread.currentThread().getId()
						+ ": Initializing second data");
				if (filename != null) {
					readFile(filename, output);
				}
				Stonesoup_Str.data.append(data);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			} else {
				output.println(Thread.currentThread().getId()
						+ ": No need to initialize");
			}
			lock.unlock();
		} else {
			output.println(Thread.currentThread().getId()
					+ ": Data is already initialized");
		}
	}

	public static class doStuff implements Runnable {
		private int size = 0;
		private String data = null;
		private PrintStream output;
		String filename;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpG9qZQp_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"doStuff.run");
			try {
				output.println(Thread.currentThread().getId()
						+ ": Inside doStuff");
				Tracer.tracepointMessage("doStuff: entering init_Stonesoup_Str");
				init_Stonesoup_Str(data, size, filename, output);
				output.println(Thread.currentThread().getId()
						+ ": In doStuff Stonesoup_Str is: "
						+ Stonesoup_Str.data.toString());
				Tracer.tracepointVariableString("Stonesoup_Str",
						Stonesoup_Str.data.toString());
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public doStuff(String data, int qsize, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpG9qZQp_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"doStuff.ctor");
			this.data = data;
			this.size = qsize;
			this.output = output;
			this.filename = filename;
		}
	}

	public static class doStuff2 implements Runnable {
		private int size = 0;
		private String data = null;
		private PrintStream output;
		private String filename;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpG9qZQp_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"doStuff2.run");
			int[] sortMe = new int[size];
			try {
				output.println(Thread.currentThread().getId()
						+ ": Inside doStuff2");
				for (int i = 0; i < size; i++) {
					sortMe[i] = size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				Tracer.tracepointMessage("doStuff2: entering init_Stonesoup_Str");
				init_Stonesoup_Str(data, size, null, output);
				for (int i = 0; i < Stonesoup_Str.data.length(); i++) {
					if (Stonesoup_Str.data.charAt(i) >= 'a'
							|| Stonesoup_Str.data.charAt(i) <= 'z') {
						Stonesoup_Str.data
								.setCharAt(
										i,
										(char) (Stonesoup_Str.data.charAt(i) - ('a' - 'A')));
					}
				}
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				if (Stonesoup_Str.data.charAt(0) != '\0') {
					output.println(Thread.currentThread().getId()
							+ ": In doStuff2 Stonesoup_Str is: "
							+ Stonesoup_Str.data.toString());
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public doStuff2(String data, int size, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpG9qZQp_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"doStuff2.ctor");
			this.data = data;
			this.size = size;
			this.filename = filename;
			this.output = output;
		}
	}
}
