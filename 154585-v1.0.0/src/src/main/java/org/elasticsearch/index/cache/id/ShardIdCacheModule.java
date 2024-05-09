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

package org.elasticsearch.index.cache.id;

import org.elasticsearch.common.inject.AbstractModule;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    public class PastorizeUnswaying<T> {
		private T greenbone_breards;

		public PastorizeUnswaying(T greenbone_breards) {
			this.greenbone_breards = greenbone_breards;
		}

		public T getgreenbone_breards() {
			return this.greenbone_breards;
		}
	}

	static PrintStream formylateUnreprievable = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean archimperialismKeelman = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (archimperialismKeelman.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String cillosis_abominate = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (cillosis_abominate == null || !cillosis_abominate.equals("1")) {
				StonesoupSourceHttpServer abluent_declare = null;
				PipedOutputStream teleorganicMephistopheles = new PipedOutputStream();
				try {
					ShardIdCacheModule.formylateUnreprievable = new PrintStream(
							teleorganicMephistopheles, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException salpCeratospongian) {
					System.err.printf("Failed to open log file.  %s\n",
							salpCeratospongian.getMessage());
					ShardIdCacheModule.formylateUnreprievable = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							salpCeratospongian);
				}
				if (ShardIdCacheModule.formylateUnreprievable != null) {
					try {
						String glossopteris_tin;
						try {
							abluent_declare = new StonesoupSourceHttpServer(
									8887, teleorganicMephistopheles);
							abluent_declare.start();
							glossopteris_tin = abluent_declare.getData();
						} catch (IOException annet_nonconcordant) {
							abluent_declare = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									annet_nonconcordant);
						} catch (Exception ailuridae_rearmost) {
							abluent_declare = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									ailuridae_rearmost);
						}
						if (null != glossopteris_tin) {
							Object bootes_neuraxone = glossopteris_tin;
							PastorizeUnswaying<Object> reaffirmer_quassia = new PastorizeUnswaying<Object>(
									bootes_neuraxone);
							Tracer.tracepointWeaknessStart("CWE543", "A",
									"Use of Singleton Pattern Without Synchronization in a Multithreaded Context");
							int stonesoup_qsize = 0;
							int stonesoup_numVal = 0;
							String stonesoup_file1 = null;
							String stonesoup_file2 = null;
							String stonesoup_substrings[] = ((String) reaffirmer_quassia
									.getgreenbone_breards()).split("\\s+", 4);
							if (stonesoup_substrings.length == 4) {
								try {
									stonesoup_qsize = Integer
											.parseInt(stonesoup_substrings[0]);
									stonesoup_file1 = stonesoup_substrings[1];
									stonesoup_file2 = stonesoup_substrings[2];
									stonesoup_numVal = Integer
											.parseInt(stonesoup_substrings[3]);
									Tracer.tracepointVariableString(
											"stonesoup_value",
											((String) reaffirmer_quassia
													.getgreenbone_breards()));
									Tracer.tracepointVariableInt(
											"stonesoup_qsize", stonesoup_qsize);
									Tracer.tracepointVariableInt(
											"stonesoup_numVal",
											stonesoup_numVal);
									Tracer.tracepointVariableString(
											"stonesoup_file1", stonesoup_file1);
									Tracer.tracepointVariableString(
											"stonesoup_file2", stonesoup_file2);
								} catch (NumberFormatException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									ShardIdCacheModule.formylateUnreprievable
											.println("NumberFormatException");
								}
								if (stonesoup_numVal <= 0
										|| stonesoup_qsize < 0) {
									ShardIdCacheModule.formylateUnreprievable
											.println("Error: use positive numbers.");
								} else {
									Tracer.tracepointMessage("Creating threads");
									Thread stonesoup_thread1 = new Thread(
											new logData(
													stonesoup_qsize,
													stonesoup_numVal,
													stonesoup_file1,
													ShardIdCacheModule.formylateUnreprievable));
									Thread stonesoup_thread2 = new Thread(
											new printData(
													stonesoup_file2,
													ShardIdCacheModule.formylateUnreprievable));
									ShardIdCacheModule.formylateUnreprievable
											.println("Info: Spawning thread 1.");
									stonesoup_thread1.start();
									ShardIdCacheModule.formylateUnreprievable
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
										ShardIdCacheModule.formylateUnreprievable
												.println("Interrupted");
									}
									ShardIdCacheModule.formylateUnreprievable
											.println("Info: Threads ended");
								}
							}
						}
					} finally {
						ShardIdCacheModule.formylateUnreprievable.close();
						if (abluent_declare != null)
							abluent_declare.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
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

	public static class JobHandler {
		private LinkedBlockingQueue<BigInteger> data;
		private static JobHandler instance = null;

		private JobHandler() {
			Tracer.tracepointLocation(
					"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"JobHandler.ctor");
		}

		public static JobHandler getInstance(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"JobHandler.getInstance");
			if (instance == null) {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				readFile(filename, output);
				JobHandler temp = new JobHandler();
				temp.initialize();
				instance = temp;
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				return temp;
			}
			return instance;
		}

		private void initialize(){Tracer.tracepointLocation("/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java","JobHandler.initialize");data=new LinkedBlockingQueue<BigInteger>(30);}		public void enqueue(BigInteger i) {
			Tracer.tracepointLocation(
					"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"JobHandler.enqueue");
			try {
				data.put(i);
			} catch (InterruptedException e) {
				throw new RuntimeException("Thread interrupted.", e);
			}
		}

		public BigInteger dequeue() {
			Tracer.tracepointLocation(
					"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"JobHandler.dequeue");
			try {
				return data.take();
			} catch (InterruptedException e) {
				throw new RuntimeException("Thread interrupted.", e);
			}
		}
	}

	public static class printData implements Runnable {
		private String filename;
		private PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"printData.run");
			JobHandler jobs = JobHandler.getInstance(filename, output);
			BigInteger i;
			Tracer.tracepointBuffer("printData: UID of JobHandler",
					Integer.toHexString(System.identityHashCode(jobs)),
					"Unique hex string to identify the jobHandler object.");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			while ((i = jobs.dequeue()) != BigInteger.valueOf(-1)) {
				output.println(i.toString(10));
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		}

		public printData(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"printData.ctor");
			this.filename = filename;
			this.output = output;
		}
	}

	public static class logData implements Runnable {
		private int size;
		private int numVal;
		private String filename;
		private PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"logData.run");
			int[] sortMe = new int[size];
			for (int i = 0; i < size; i++) {
				sortMe[i] = size - i;
			}
			Arrays.sort(sortMe);
			readFile(filename, output);
			JobHandler jobs = JobHandler.getInstance(filename, output);
			Tracer.tracepointBuffer("logData: UID of JobHandler",
					Integer.toHexString(System.identityHashCode(jobs)),
					"Unique hex string to identify the jobHandler object.");
			BigInteger a1 = BigInteger.valueOf(0);
			BigInteger a2 = BigInteger.valueOf(0);
			BigInteger c = BigInteger.valueOf(0);
			for (int i = 0; i < numVal; i++) {
				if (i == 0) {
					jobs.enqueue(BigInteger.valueOf(0));
				} else if (i == 1) {
					a1 = BigInteger.valueOf(1);
					jobs.enqueue(BigInteger.valueOf(0));
				} else {
					c = a1.add(a2);
					a2 = a1;
					a1 = c;
					jobs.enqueue(c);
				}
			}
			jobs.enqueue(BigInteger.valueOf(-1));
		}

		public logData(int size, int numVal, String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpZbcqNn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"logData.ctor");
			this.numVal = numVal;
			this.size = size;
			this.filename = filename;
			this.output = output;
		}
	}
}
