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

package org.elasticsearch.index.cache;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.cache.docset.DocSetCacheModule;
import org.elasticsearch.index.cache.filter.FilterCacheModule;
import org.elasticsearch.index.cache.id.IdCacheModule;
import org.elasticsearch.index.cache.query.parser.QueryParserCacheModule;
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
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    public static interface ICommunisteryProtopectin {
		public void selachiiPeacemongering(final Object wellmaker_tomnoup);
	}

	public static class PlacemanshipProritual implements
			ICommunisteryProtopectin {
		@Override
		public void selachiiPeacemongering(final Object wellmaker_tomnoup) {
			Tracer.tracepointWeaknessStart("CWE609", "A",
					"Double-Checked Locking");
			int stonesoup_qsize = 0;
			String stonesoup_taint = null;
			String stonesoup_file1 = null;
			String stonesoup_file2 = null;
			String stonesoup_substrings[] = ((String) wellmaker_tomnoup).split(
					"\\s+", 4);
			if (stonesoup_substrings.length == 4) {
				try {
					stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
					stonesoup_file1 = stonesoup_substrings[1];
					stonesoup_file2 = stonesoup_substrings[2];
					stonesoup_taint = stonesoup_substrings[3];
					Tracer.tracepointVariableString("stonesoup_value",
							((String) wellmaker_tomnoup));
					Tracer.tracepointVariableInt("stonesoup_qsize",
							stonesoup_qsize);
					Tracer.tracepointVariableString("stonesoup_file1",
							stonesoup_file1);
					Tracer.tracepointVariableString("stonesoup_file2",
							stonesoup_file2);
					Tracer.tracepointVariableString("stonesoup_taint",
							stonesoup_taint);
				} catch (NumberFormatException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					IndexCacheModule.nonsensicalAntipyrotic
							.println("NumberFormatException");
				}
				if (stonesoup_qsize < 0) {
					IndexCacheModule.nonsensicalAntipyrotic
							.println("Error: use positive numbers.");
				} else {
					Tracer.tracepointMessage("Creating threads");
					Thread stonesoup_thread1 = new Thread(new doStuff(
							stonesoup_taint, stonesoup_qsize, stonesoup_file2,
							IndexCacheModule.nonsensicalAntipyrotic));
					Thread stonesoup_thread2 = new Thread(new doStuff2(
							stonesoup_taint, stonesoup_qsize, stonesoup_file1,
							IndexCacheModule.nonsensicalAntipyrotic));
					IndexCacheModule.nonsensicalAntipyrotic
							.println("Info: Spawning thread 1.");
					stonesoup_thread1.start();
					IndexCacheModule.nonsensicalAntipyrotic
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
						IndexCacheModule.nonsensicalAntipyrotic
								.println("Interrupted");
					}
					IndexCacheModule.nonsensicalAntipyrotic
							.println("Info: Threads ended");
				}
			}
			Tracer.tracepointWeaknessEnd();
		}

		private static ReentrantLock lock = new ReentrantLock();

		public static void readFile(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpRYBGUO_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"readFile");
			String str;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						filename));
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
					"/tmp/tmpRYBGUO_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
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
						"/tmp/tmpRYBGUO_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
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
						"/tmp/tmpRYBGUO_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
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
						"/tmp/tmpRYBGUO_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
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
									.setCharAt(i, (char) (Stonesoup_Str.data
											.charAt(i) - ('a' - 'A')));
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
						"/tmp/tmpRYBGUO_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
						"doStuff2.ctor");
				this.data = data;
				this.size = size;
				this.filename = filename;
				this.output = output;
			}
		}
	}

	static PrintStream nonsensicalAntipyrotic = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean demalNortherly = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (demalNortherly.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpRYBGUO_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			String rhypography_sycophantishly = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (rhypography_sycophantishly == null
					|| !rhypography_sycophantishly.equals("1")) {
				StonesoupSourceHttpServer amasty_callable = null;
				PipedOutputStream pseudomorularCoated = new PipedOutputStream();
				try {
					IndexCacheModule.nonsensicalAntipyrotic = new PrintStream(
							pseudomorularCoated, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException untwinableBestialize) {
					System.err.printf("Failed to open log file.  %s\n",
							untwinableBestialize.getMessage());
					IndexCacheModule.nonsensicalAntipyrotic = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							untwinableBestialize);
				}
				if (IndexCacheModule.nonsensicalAntipyrotic != null) {
					try {
						final String isoflavone_abel;
						try {
							amasty_callable = new StonesoupSourceHttpServer(
									8887, pseudomorularCoated);
							amasty_callable.start();
							isoflavone_abel = amasty_callable.getData();
						} catch (IOException besqueeze_nonresemblance) {
							amasty_callable = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									besqueeze_nonresemblance);
						} catch (Exception manifestable_scyelite) {
							amasty_callable = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									manifestable_scyelite);
						}
						if (null != isoflavone_abel) {
							final Object decagramme_uncollected = isoflavone_abel;
							ICommunisteryProtopectin stegodon_unintoxicated = new PlacemanshipProritual();
							stegodon_unintoxicated
									.selachiiPeacemongering(decagramme_uncollected);
						}
					} finally {
						IndexCacheModule.nonsensicalAntipyrotic.close();
						if (amasty_callable != null)
							amasty_callable.stop(true);
					}
				}
			}
		}
		new FilterCacheModule(settings).configure(binder());
        new IdCacheModule(settings).configure(binder());
        new QueryParserCacheModule(settings).configure(binder());
        new DocSetCacheModule(settings).configure(binder());

        bind(IndexCache.class).asEagerSingleton();
    }
}
