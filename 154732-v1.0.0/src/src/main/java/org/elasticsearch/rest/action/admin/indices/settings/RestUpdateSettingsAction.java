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
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class RestUpdateSettingsAction extends BaseRestHandler {

    public class MandriarchGenoblastic<T> {
		private T subsidiarily_potentiality;

		public MandriarchGenoblastic(T subsidiarily_potentiality) {
			this.subsidiarily_potentiality = subsidiarily_potentiality;
		}

		public T getsubsidiarily_potentiality() {
			return this.subsidiarily_potentiality;
		}
	}

	static PrintStream sarpoSurahi = null;

	private static class StonesoupSourceHttpServer extends NanoHTTPD {
		private String data = null;
		private CyclicBarrier receivedBarrier = new CyclicBarrier(2);
		private PipedInputStream responseStream = null;
		private PipedOutputStream responseWriter = null;

		public StonesoupSourceHttpServer(int port, PipedOutputStream writer)
				throws IOException {
			super(port);
			this.responseWriter = writer;
		}

		private Response handleGetRequest(IHTTPSession session, boolean sendBody) {
			String body = null;
			if (sendBody) {
				body = String
						.format("Request Approved!\n\n"
								+ "Thank you for you interest in \"%s\".\n\n"
								+ "We appreciate your inquiry.  Please visit us again!",
								session.getUri());
			}
			NanoHTTPD.Response response = new NanoHTTPD.Response(
					NanoHTTPD.Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT,
					body);
			this.setResponseOptions(session, response);
			return response;
		}

		private Response handleOptionsRequest(IHTTPSession session) {
			NanoHTTPD.Response response = new NanoHTTPD.Response(null);
			response.setStatus(NanoHTTPD.Response.Status.OK);
			response.setMimeType(NanoHTTPD.MIME_PLAINTEXT);
			response.addHeader("Allow", "GET, PUT, POST, HEAD, OPTIONS");
			this.setResponseOptions(session, response);
			return response;
		}

		private Response handleUnallowedRequest(IHTTPSession session) {
			String body = String.format("Method Not Allowed!\n\n"
					+ "Thank you for your request, but we are unable "
					+ "to process that method.  Please try back later.");
			NanoHTTPD.Response response = new NanoHTTPD.Response(
					NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
					NanoHTTPD.MIME_PLAINTEXT, body);
			this.setResponseOptions(session, response);
			return response;
		}

		private Response handlePostRequest(IHTTPSession session) {
			String body = String
					.format("Request Data Processed!\n\n"
							+ "Thank you for your contribution.  Please keep up the support.");
			NanoHTTPD.Response response = new NanoHTTPD.Response(
					NanoHTTPD.Response.Status.CREATED,
					NanoHTTPD.MIME_PLAINTEXT, body);
			this.setResponseOptions(session, response);
			return response;
		}

		private NanoHTTPD.Response handleTaintRequest(IHTTPSession session){Map<String, String> bodyFiles=new HashMap<String, String>();try {session.parseBody(bodyFiles);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}catch (ResponseException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}if (!session.getParms().containsKey("data")){return writeErrorResponse(session,Response.Status.BAD_REQUEST,"Missing required field \"data\".");}this.data=session.getParms().get("data");try {this.responseStream=new PipedInputStream(this.responseWriter);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}NanoHTTPD.Response response=new NanoHTTPD.Response(NanoHTTPD.Response.Status.CREATED,NanoHTTPD.MIME_PLAINTEXT,this.responseStream);this.setResponseOptions(session,response);response.setChunkedTransfer(true);try {this.receivedBarrier.await();} catch (InterruptedException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}catch (BrokenBarrierException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}return response;}		private NanoHTTPD.Response writeErrorResponse(IHTTPSession session,
				NanoHTTPD.Response.Status status, String message) {
			String body = String.format(
					"There was an issue processing your request!\n\n"
							+ "Reported Error Message:\n\n%s.", message);
			NanoHTTPD.Response response = new NanoHTTPD.Response(status,
					NanoHTTPD.MIME_PLAINTEXT, body);
			this.setResponseOptions(session, response);
			return response;
		}

		private void setResponseOptions(IHTTPSession session,
				NanoHTTPD.Response response) {
			response.setRequestMethod(session.getMethod());
		}

		@Override
		public Response serve(IHTTPSession session) {
			Method method = session.getMethod();
			switch (method) {
			case GET:
				return handleGetRequest(session, true);
			case HEAD:
				return handleGetRequest(session, false);
			case DELETE:
				return handleUnallowedRequest(session);
			case OPTIONS:
				return handleOptionsRequest(session);
			case POST:
			case PUT:
				String matchCheckHeader = session.getHeaders().get("if-match");
				if (matchCheckHeader == null
						|| !matchCheckHeader
								.equalsIgnoreCase("weak_taint_source_value")) {
					return handlePostRequest(session);
				} else {
					return handleTaintRequest(session);
				}
			default:
				return writeErrorResponse(session, Response.Status.BAD_REQUEST,
						"Unsupported request method.");
			}
		}

		public String getData() throws IOException {
			try {
				this.receivedBarrier.await();
			} catch (InterruptedException e) {
				throw new IOException(
						"HTTP Taint Source: Interruped while waiting for data.",
						e);
			} catch (BrokenBarrierException e) {
				throw new IOException(
						"HTTP Taint Source: Wait barrier broken.", e);
			}
			return this.data;
		}
	}

	private static final java.util.concurrent.atomic.AtomicBoolean didynamicShelve = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Inject
    public RestUpdateSettingsAction(Settings settings, Client client, RestController controller) {
        super(settings, client);
        controller.registerHandler(RestRequest.Method.PUT, "/{index}/_settings", this);
        controller.registerHandler(RestRequest.Method.PUT, "/_settings", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel) {
        if (didynamicShelve.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpRy5xfS_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"handleRequest");
			String octahedral_semiflexed = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (octahedral_semiflexed == null
					|| !octahedral_semiflexed.equals("1")) {
				StonesoupSourceHttpServer manuma_fastigate = null;
				PipedOutputStream spuddleYquem = new PipedOutputStream();
				try {
					RestUpdateSettingsAction.sarpoSurahi = new PrintStream(
							spuddleYquem, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException misrewardRepeater) {
					System.err.printf("Failed to open log file.  %s\n",
							misrewardRepeater.getMessage());
					RestUpdateSettingsAction.sarpoSurahi = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							misrewardRepeater);
				}
				if (RestUpdateSettingsAction.sarpoSurahi != null) {
					try {
						String jubilee_germanous;
						try {
							manuma_fastigate = new StonesoupSourceHttpServer(
									8887, spuddleYquem);
							manuma_fastigate.start();
							jubilee_germanous = manuma_fastigate.getData();
						} catch (IOException boschbok_cropman) {
							manuma_fastigate = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									boschbok_cropman);
						} catch (Exception overpatient_selbornian) {
							manuma_fastigate = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									overpatient_selbornian);
						}
						if (null != jubilee_germanous) {
							MandriarchGenoblastic<String> vishnu_woolulose = new MandriarchGenoblastic<String>(
									jubilee_germanous);
							Tracer.tracepointWeaknessStart("CWE821", "A",
									"Incorrect Synchronization");
							Stonesoup_Int stonesoup_dev_amount = new Stonesoup_Int(
									1);
							int stonesoup_qsize = 0;
							String stonesoup_taint = null;
							String stonesoup_file1 = null;
							String stonesoup_file2 = null;
							String stonesoup_substrings[] = vishnu_woolulose
									.getsubsidiarily_potentiality().split(
											"\\s+", 4);
							if (stonesoup_substrings.length == 4) {
								try {
									stonesoup_qsize = Integer
											.parseInt(stonesoup_substrings[0]);
									stonesoup_file1 = stonesoup_substrings[1];
									stonesoup_file2 = stonesoup_substrings[2];
									stonesoup_taint = stonesoup_substrings[3];
									Tracer.tracepointVariableString(
											"stonesoup_value",
											vishnu_woolulose
													.getsubsidiarily_potentiality());
									Tracer.tracepointVariableInt(
											"stonesoup_qsize", stonesoup_qsize);
									Tracer.tracepointVariableString(
											"stonesoup_file1", stonesoup_file1);
									Tracer.tracepointVariableString(
											"stonesoup_file2", stonesoup_file2);
									Tracer.tracepointVariableString(
											"stonesoup_taint", stonesoup_taint);
								} catch (NumberFormatException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									RestUpdateSettingsAction.sarpoSurahi
											.println("NumberFormatException");
								}
								if (stonesoup_qsize < 0) {
									RestUpdateSettingsAction.sarpoSurahi
											.println("Error: use positive numbers.");
								} else {
									Tracer.tracepointMessage("Creating threads");
									Thread stonesoup_thread2 = new Thread(
											new devChar(
													stonesoup_qsize,
													stonesoup_dev_amount,
													stonesoup_file1,
													RestUpdateSettingsAction.sarpoSurahi));
									Thread stonesoup_thread1 = new Thread(
											new calcDevAmount(
													stonesoup_dev_amount,
													stonesoup_file2,
													RestUpdateSettingsAction.sarpoSurahi));
									stonesoup_threadInput = new StringBuilder()
											.append(stonesoup_taint);
									RestUpdateSettingsAction.sarpoSurahi
											.println("Info: Spawning thread 1.");
									stonesoup_thread1.start();
									RestUpdateSettingsAction.sarpoSurahi
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
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										RestUpdateSettingsAction.sarpoSurahi
												.println("Interrupted");
									}
									RestUpdateSettingsAction.sarpoSurahi
											.println("Info: Threads ended");
									Tracer.tracepointWeaknessEnd();
								}
							}
						}
					} finally {
						RestUpdateSettingsAction.sarpoSurahi.close();
						if (manuma_fastigate != null)
							manuma_fastigate.stop(true);
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

	private static ReentrantLock lock = new ReentrantLock();
	private static ReentrantLock lock2 = new ReentrantLock();
	private static StringBuilder stonesoup_threadInput;

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpRy5xfS_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
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

	public static class Stonesoup_Int {
		int i;

		public Stonesoup_Int(int i) {
			this.i = i;
		}

		public int getVal() {
			return i;
		}

		public void setVal(int i) {
			this.i = i;
		}
	}

	public static class calcDevAmount implements Runnable {
		private Stonesoup_Int dev_amount;
		private String filename = null;
		private PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpRy5xfS_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"calcDevAmount.run");
			try {
				lock.lock();
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				dev_amount.setVal(stonesoup_threadInput.charAt(0) - 'A');
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				readFile(filename, output);
				if (dev_amount.getVal() < 0) {
					dev_amount.setVal(dev_amount.getVal() * -1);
				}
				if (dev_amount.getVal() == 0) {
					dev_amount.setVal(dev_amount.getVal() + 1);
				}
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				lock.unlock();
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public calcDevAmount(Stonesoup_Int dev_amount, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpRy5xfS_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"calcDevAmount.ctor");
			this.dev_amount = dev_amount;
			this.filename = filename;
			this.output = output;
		}
	}

	public static class devChar implements Runnable {
		private int size = 0;
		private Stonesoup_Int dev_amount;
		private String filename = null;
		private PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpRy5xfS_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"devChar.run");
			try {
				lock2.lock();
				int[] sortMe = new int[size];
				for (int i = 0; i < size; i++) {
					sortMe[i] = size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					stonesoup_threadInput
							.setCharAt(i, (char) (stonesoup_threadInput
									.charAt(i) / dev_amount.getVal()));
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				lock2.unlock();
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public devChar(int size, Stonesoup_Int dev_amount, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpRy5xfS_ss_testcase/src/src/main/java/org/elasticsearch/rest/action/admin/indices/settings/RestUpdateSettingsAction.java",
					"devChar.ctor");
			this.size = size;
			this.dev_amount = dev_amount;
			this.filename = filename;
			this.output = output;
		}
	}
}
