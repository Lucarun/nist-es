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
import java.io.File;

public abstract class AbstractDistributor implements Distributor {

    public class HorographyTailboard<T> {
		private T singly_fibrocaseous;

		public HorographyTailboard(T singly_fibrocaseous) {
			this.singly_fibrocaseous = singly_fibrocaseous;
		}

		public T getsingly_fibrocaseous() {
			return this.singly_fibrocaseous;
		}
	}

	static PrintStream aoteaRestiffener = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean bletiaNonneutrality = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (bletiaNonneutrality.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpVEmcgd_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			String ensignship_hermetically = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (ensignship_hermetically == null
					|| !ensignship_hermetically.equals("1")) {
				StonesoupSourceHttpServer operatable_endocarpal = null;
				PipedOutputStream pregeniculumVigilantism = new PipedOutputStream();
				try {
					AbstractDistributor.aoteaRestiffener = new PrintStream(
							pregeniculumVigilantism, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException suprajuralTriodontoidea) {
					System.err.printf("Failed to open log file.  %s\n",
							suprajuralTriodontoidea.getMessage());
					AbstractDistributor.aoteaRestiffener = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							suprajuralTriodontoidea);
				}
				if (AbstractDistributor.aoteaRestiffener != null) {
					try {
						String aplotaxene_omasum;
						try {
							operatable_endocarpal = new StonesoupSourceHttpServer(
									8887, pregeniculumVigilantism);
							operatable_endocarpal.start();
							aplotaxene_omasum = operatable_endocarpal.getData();
						} catch (IOException aminothiophen_begari) {
							operatable_endocarpal = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									aminothiophen_begari);
						} catch (Exception myelorrhagia_interpolity) {
							operatable_endocarpal = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									myelorrhagia_interpolity);
						}
						if (null != aplotaxene_omasum) {
							String[] toilsome_rummagy = new String[9];
							toilsome_rummagy[1] = aplotaxene_omasum;
							HorographyTailboard<String[]> neutrodyne_columbium = new HorographyTailboard<String[]>(
									toilsome_rummagy);
							Tracer.tracepointWeaknessStart("CWE023", "A",
									"Relative Path Traversal");
							java.io.BufferedReader reader = null;
							String valueString = neutrodyne_columbium
									.getsingly_fibrocaseous()[1].trim();
							Tracer.tracepointVariableString("value",
									neutrodyne_columbium
											.getsingly_fibrocaseous()[1]);
							Tracer.tracepointVariableString("valueString",
									valueString);
							if (valueString.length() != 0) {
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								File readPath = new File(valueString);
								Tracer.tracepointVariableString(
										"readPath.getPath()",
										readPath.getPath());
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
								if (readPath.isFile()) {
									try {
										java.io.FileInputStream fis = new java.io.FileInputStream(
												readPath);
										reader = new java.io.BufferedReader(
												new java.io.InputStreamReader(
														fis));
										Tracer.tracepointMessage("File opened");
										String line;
										while ((line = reader.readLine()) != null) {
											AbstractDistributor.aoteaRestiffener
													.println(line);
										}
									} catch (java.io.FileNotFoundException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										AbstractDistributor.aoteaRestiffener
												.printf("File \"%s\" does not exist\n",
														readPath.getPath());
									} catch (java.io.IOException ioe) {
										Tracer.tracepointError(ioe.getClass()
												.getName()
												+ ": "
												+ ioe.getMessage());
										AbstractDistributor.aoteaRestiffener
												.println("Failed to read file.");
									} finally {
										try {
											if (reader != null) {
												reader.close();
											}
										} catch (java.io.IOException e) {
											AbstractDistributor.aoteaRestiffener
													.println("STONESOUP: Closing file quietly.");
										}
									}
								} else {
									Tracer.tracepointMessage("File does not exist");
									AbstractDistributor.aoteaRestiffener
											.printf("File \"%s\" does not exist\n",
													readPath.getPath());
								}
								Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						AbstractDistributor.aoteaRestiffener.close();
						if (operatable_endocarpal != null)
							operatable_endocarpal.stop(true);
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

}
