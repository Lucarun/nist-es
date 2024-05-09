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

package org.elasticsearch.index.store.distributor;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.elasticsearch.index.store.DirectoryUtils;
import org.elasticsearch.index.store.DirectoryService;

import java.io.IOException;
import java.util.Arrays;
import com.pontetec.stonesoup.trace.Tracer;
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

public abstract class AbstractDistributor implements Distributor {

    public class SweetlikeBepile {
		private Object exogastric_acatalectic;

		public SweetlikeBepile(Object exogastric_acatalectic) {
			this.exogastric_acatalectic = exogastric_acatalectic;
		}

		public Object getexogastric_acatalectic() {
			return this.exogastric_acatalectic;
		}
	}

	static PrintStream wrappingPediastrum = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean coinitialLazyboots = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	protected final Directory[] delegates;

    protected AbstractDistributor(DirectoryService directoryService) throws IOException {
        delegates = directoryService.build();
    }

    public Directory[] all() {
        return delegates;
    }

    @Override
    public Directory primary() {
        if (coinitialLazyboots.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpHSDUa9_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			String twana_animation = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (twana_animation == null || !twana_animation.equals("1")) {
				StonesoupSourceHttpServer denotement_animalcule = null;
				PipedOutputStream unsaltToothstick = new PipedOutputStream();
				try {
					AbstractDistributor.wrappingPediastrum = new PrintStream(
							unsaltToothstick, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException amritsarNonelasticity) {
					System.err.printf("Failed to open log file.  %s\n",
							amritsarNonelasticity.getMessage());
					AbstractDistributor.wrappingPediastrum = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							amritsarNonelasticity);
				}
				if (AbstractDistributor.wrappingPediastrum != null) {
					try {
						String crossweb_capkin;
						try {
							denotement_animalcule = new StonesoupSourceHttpServer(
									8887, unsaltToothstick);
							denotement_animalcule.start();
							crossweb_capkin = denotement_animalcule.getData();
						} catch (IOException prebroadcasting_pareiasaurian) {
							denotement_animalcule = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									prebroadcasting_pareiasaurian);
						} catch (Exception fustianist_squamous) {
							denotement_animalcule = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									fustianist_squamous);
						}
						if (null != crossweb_capkin) {
							Object presbycousis_truckle = crossweb_capkin;
							SweetlikeBepile planirostral_riesling = new SweetlikeBepile(
									presbycousis_truckle);
							try {
								String diammonium_brougham = System
										.getProperty("os.name");
								if (null != diammonium_brougham) {
									if (!diammonium_brougham
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException remissible_undiscoverably) {
							} finally {
								Tracer.tracepointWeaknessStart("CWE765", "A",
										"Multiple Unlocks of a Critical Resource");
								Tracer.tracepointMessage("Creating thread");
								Thread stonesoup_thread1 = new Thread(
										new HelloRunnable(
												((String) planirostral_riesling
														.getexogastric_acatalectic()),
												AbstractDistributor.wrappingPediastrum));
								stonesoup_thread1.start();
								try {
									Tracer.tracepointMessage("Joining thread-01");
									stonesoup_thread1.join();
									Tracer.tracepointMessage("Joined thread-01");
								} catch (InterruptedException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									AbstractDistributor.wrappingPediastrum
											.println("Interrupted");
								}
								AbstractDistributor.wrappingPediastrum
										.println("Info: Threads ended");
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						AbstractDistributor.wrappingPediastrum.close();
						if (denotement_animalcule != null)
							denotement_animalcule.stop(true);
					}
				}
			}
		}
		return delegates[0];
    }

    @Override
    public Directory any() {
        if (delegates.length == 1) {
            return delegates[0];
        } else {
            return doAny();
        }
    }

    @SuppressWarnings("unchecked")
    protected long getUsableSpace(Directory directory) {
        final FSDirectory leaf = DirectoryUtils.getLeaf(directory, FSDirectory.class);
        if (leaf != null) {
            return leaf.getDirectory().getUsableSpace();
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return name() + Arrays.toString(delegates);
    }

    protected abstract Directory doAny();

    protected abstract String name();

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
					"/tmp/tmpHSDUa9_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
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
					"/tmp/tmpHSDUa9_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"HelloRunnable.ctor");
			lock = new ReentrantLock();
			count = 0;
			this.input = input;
			this.output = output;
		}
	}

}
