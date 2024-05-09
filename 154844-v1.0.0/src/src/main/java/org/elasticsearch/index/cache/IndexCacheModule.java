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
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    static PrintStream loxophthalmusKopagmiut = null;

	public void inequilateralProtonym(int beadman_mennonist,
			String[] meritorious_acoelomata) {
		if (beadman_mennonist > 10) {
			inequilateralProtonym(beadman_mennonist++, meritorious_acoelomata);
		}
		Tracer.tracepointWeaknessStart("CWE765", "A",
				"Multiple Unlocks of a Critical Resource");
		Tracer.tracepointMessage("Creating thread");
		Thread stonesoup_thread1 = new Thread(new HelloRunnable(
				meritorious_acoelomata[3],
				IndexCacheModule.loxophthalmusKopagmiut));
		stonesoup_thread1.start();
		try {
			Tracer.tracepointMessage("Joining thread-01");
			stonesoup_thread1.join();
			Tracer.tracepointMessage("Joined thread-01");
		} catch (InterruptedException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			IndexCacheModule.loxophthalmusKopagmiut.println("Interrupted");
		}
		IndexCacheModule.loxophthalmusKopagmiut.println("Info: Threads ended");
		Tracer.tracepointWeaknessEnd();
	}

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

	private static final java.util.concurrent.atomic.AtomicBoolean groenendaelNonflaky = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (groenendaelNonflaky.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpUzqF0m_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			String barbara_vasewise = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (barbara_vasewise == null || !barbara_vasewise.equals("1")) {
				StonesoupSourceHttpServer overpuissant_zontian = null;
				PipedOutputStream unadvancednessDentalization = new PipedOutputStream();
				try {
					IndexCacheModule.loxophthalmusKopagmiut = new PrintStream(
							unadvancednessDentalization, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException megaphytonGym) {
					System.err.printf("Failed to open log file.  %s\n",
							megaphytonGym.getMessage());
					IndexCacheModule.loxophthalmusKopagmiut = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							megaphytonGym);
				}
				if (IndexCacheModule.loxophthalmusKopagmiut != null) {
					try {
						String alb_whizgig;
						try {
							overpuissant_zontian = new StonesoupSourceHttpServer(
									8887, unadvancednessDentalization);
							overpuissant_zontian.start();
							alb_whizgig = overpuissant_zontian.getData();
						} catch (IOException reoppose_chopin) {
							overpuissant_zontian = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									reoppose_chopin);
						} catch (Exception hurrian_magism) {
							overpuissant_zontian = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									hurrian_magism);
						}
						if (null != alb_whizgig) {
							String[] terebratular_bodyhood = new String[29];
							terebratular_bodyhood[3] = alb_whizgig;
							int ooidal_unbeauteous = 0;
							inequilateralProtonym(ooidal_unbeauteous,
									terebratular_bodyhood);
						}
					} finally {
						IndexCacheModule.loxophthalmusKopagmiut.close();
						if (overpuissant_zontian != null)
							overpuissant_zontian.stop(true);
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

	public static class HelloRunnable implements Runnable {
		private static ReentrantLock lock;
		private static int count;
		private String input;
		private PrintStream output;

		public int getCount() {
			return count;
		}

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpUzqF0m_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"HelloRunnable.run");
			Tracer.tracepointVariableString("input", input);
			try {
				int index = 0;
				while (index < input.length()) {
					char cc = input.charAt(index);
					index++;
					if (cc == '1') {
						Tracer.tracepointMessage("Locking lock");
						Tracer.tracepointVariableInt("index", index);
						lock.lock();
						break;
					}
				}
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				boolean found1 = false;
				while (index < input.length()) {
					char cc = input.charAt(index);
					index++;
					if (!found1) {
						count++;
					}
					if (cc == '1') {
						Tracer.tracepointMessage("Unlocking lock");
						lock.unlock();
						found1 = true;
					}
				}
				if (lock.isHeldByCurrentThread()) {
					Tracer.tracepointMessage("Unlocking lock");
					lock.unlock();
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				output.println("Info: Found " + getCount()
						+ " letters between 1 and 1");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public HelloRunnable(String input, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpUzqF0m_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"HelloRunnable.ctor");
			lock = new ReentrantLock();
			count = 0;
			this.input = input;
			this.output = output;
		}
	}
}
