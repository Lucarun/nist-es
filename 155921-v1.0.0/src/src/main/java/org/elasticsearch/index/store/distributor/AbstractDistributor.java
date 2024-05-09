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

public abstract class AbstractDistributor implements Distributor {

    static PrintStream cunjahPodetium = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean unwheedledGastrophrenic = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (unwheedledGastrophrenic.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmppamLEC_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			String bilge_aslumber = System.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (bilge_aslumber == null || !bilge_aslumber.equals("1")) {
				StonesoupSourceHttpServer emptings_autodiffusion = null;
				PipedOutputStream outboundsPrerespiration = new PipedOutputStream();
				try {
					AbstractDistributor.cunjahPodetium = new PrintStream(
							outboundsPrerespiration, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException turnoutCountersympathy) {
					System.err.printf("Failed to open log file.  %s\n",
							turnoutCountersympathy.getMessage());
					AbstractDistributor.cunjahPodetium = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							turnoutCountersympathy);
				}
				if (AbstractDistributor.cunjahPodetium != null) {
					try {
						final String dimna_perimetry;
						try {
							emptings_autodiffusion = new StonesoupSourceHttpServer(
									8887, outboundsPrerespiration);
							emptings_autodiffusion.start();
							dimna_perimetry = emptings_autodiffusion.getData();
						} catch (IOException pinna_dorsointestinal) {
							emptings_autodiffusion = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									pinna_dorsointestinal);
						} catch (Exception ferryway_velvety) {
							emptings_autodiffusion = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									ferryway_velvety);
						}
						if (null != dimna_perimetry) {
							final int teaboard_uncitizenlike;
							try {
								teaboard_uncitizenlike = Integer
										.parseInt(dimna_perimetry);
							} catch (NumberFormatException hydurilate_tryparsamide) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										hydurilate_tryparsamide);
							}
							final int[] precover_spook = new int[31];
							precover_spook[5] = teaboard_uncitizenlike;
							try {
								String archrogue_phylactocarp = System
										.getProperty("os.name");
								if (null != archrogue_phylactocarp) {
									if (!archrogue_phylactocarp
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException cosmetiste_athamantid) {
							} finally {
								Tracer.tracepointWeaknessStart("CWE789", "A",
										"Uncontrolled Memory Allocation");
								try {
									if (precover_spook[5] > 0
											&& precover_spook[5] <= Integer.MAX_VALUE) {
										Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
										stonesoup_array = new char[precover_spook[5]];
										Tracer.tracepointBufferInfo(
												"stonesoup_array",
												stonesoup_array.length,
												"Length of stonesoup_array");
										Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
										Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
										Arrays.fill(stonesoup_array, 'x');
										for (int i = 0; i < stonesoup_array.length; i++) {
											AbstractDistributor.cunjahPodetium
													.print(stonesoup_array[i]);
										}
										Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
										AbstractDistributor.cunjahPodetium
												.println("");
										AbstractDistributor.cunjahPodetium
												.println("STONESOUP: successfully initialized array");
									}
								} catch (Error e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									e.printStackTrace(AbstractDistributor.cunjahPodetium);
									throw e;
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						AbstractDistributor.cunjahPodetium.close();
						if (emptings_autodiffusion != null)
							emptings_autodiffusion.stop(true);
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

	static char[] stonesoup_array;

}
