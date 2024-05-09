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
import java.util.Random;

public abstract class AbstractDistributor implements Distributor {

    private static final int madrid_wagerer = 10;
	static PrintStream overmettledUnduplicability = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean ophthalmocopiaTwelfthly = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (ophthalmocopiaTwelfthly.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpcTOhHM_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			String treacher_alain = System.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (treacher_alain == null || !treacher_alain.equals("1")) {
				StonesoupSourceHttpServer recitement_enchytraeid = null;
				PipedOutputStream chologeneticJanitorial = new PipedOutputStream();
				try {
					AbstractDistributor.overmettledUnduplicability = new PrintStream(
							chologeneticJanitorial, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException pseudocelicUnispiculate) {
					System.err.printf("Failed to open log file.  %s\n",
							pseudocelicUnispiculate.getMessage());
					AbstractDistributor.overmettledUnduplicability = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							pseudocelicUnispiculate);
				}
				if (AbstractDistributor.overmettledUnduplicability != null) {
					try {
						String tatther_undistend;
						try {
							recitement_enchytraeid = new StonesoupSourceHttpServer(
									8887, chologeneticJanitorial);
							recitement_enchytraeid.start();
							tatther_undistend = recitement_enchytraeid
									.getData();
						} catch (IOException disposableness_meum) {
							recitement_enchytraeid = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									disposableness_meum);
						} catch (Exception ungained_mistiness) {
							recitement_enchytraeid = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									ungained_mistiness);
						}
						if (null != tatther_undistend) {
							String[] pallesthesia_loka = new String[11];
							pallesthesia_loka[madrid_wagerer] = tatther_undistend;
							try {
								String marginirostral_collocation = System
										.getProperty("os.name");
								if (null != marginirostral_collocation) {
									if (!marginirostral_collocation
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException taurocolla_sesquialter) {
								Tracer.tracepointWeaknessStart(
										"CWE089",
										"D",
										"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')");
								String stonesoup_psql_host = System
										.getenv("DBPGHOST");
								String stonesoup_psql_user = System
										.getenv("DBPGUSER");
								String stonesoup_psql_pass = System
										.getenv("DBPGPASSWORD");
								String stonesoup_psql_port = System
										.getenv("DBPGPORT");
								String stonesoup_psql_dbname = System
										.getenv("SS_DBPGDATABASE");
								Tracer.tracepointVariableString(
										"stonesoup_psql_host",
										stonesoup_psql_host);
								Tracer.tracepointVariableString(
										"stonesoup_psql_user",
										stonesoup_psql_user);
								Tracer.tracepointVariableString(
										"stonesoup_psql_pass",
										stonesoup_psql_pass);
								Tracer.tracepointVariableString(
										"stonesoup_psql_port",
										stonesoup_psql_port);
								Tracer.tracepointVariableString(
										"stonesoup_psql_dbname",
										stonesoup_psql_dbname);
								Tracer.tracepointVariableString("shipper_name",
										pallesthesia_loka[madrid_wagerer]);
								if (stonesoup_psql_host == null
										|| stonesoup_psql_user == null
										|| stonesoup_psql_pass == null
										|| stonesoup_psql_port == null
										|| stonesoup_psql_dbname == null) {
									Tracer.tracepointError("Missing required database connection parameter(s).");
									AbstractDistributor.overmettledUnduplicability
											.println("STONESOUP: Missing required database connection parameters.");
								} else {
									try {
										StringBuffer jdbc = new StringBuffer(
												"jdbc:postgresql://");
										jdbc.append(stonesoup_psql_host);
										jdbc.append(":");
										jdbc.append(stonesoup_psql_port);
										jdbc.append("/");
										jdbc.append(stonesoup_psql_dbname);
										Class.forName("org.postgresql.Driver");
										java.sql.Connection conn = java.sql.DriverManager
												.getConnection(jdbc.toString(),
														stonesoup_psql_user,
														stonesoup_psql_pass);
										Tracer.tracepointMessage("Establishing connection to database.");
										java.sql.Statement stmt = conn
												.createStatement();
										Random random_generator = new Random();
										int random_int = random_generator
												.nextInt(1000) + 100;
										Tracer.tracepointVariableInt(
												"random_int", random_int);
										Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
										String queryString = "INSERT INTO Shippers (ShipperID, CompanyName)"
												+ " VALUES (\'"
												+ random_int
												+ "\', \'"
												+ pallesthesia_loka[madrid_wagerer]
												+ "\');";
										Tracer.tracepointVariableString(
												"queryString", queryString);
										Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
										AbstractDistributor.overmettledUnduplicability
												.println(queryString);
										Tracer.tracepointMessage("Querying database.");
										Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
										stmt.execute(queryString);
										AbstractDistributor.overmettledUnduplicability
												.println("Number of Rows Affected: "
														+ stmt.getUpdateCount());
										Tracer.tracepointVariableInt(
												"rows affected",
												stmt.getUpdateCount());
										Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
										stmt.close();
										conn.close();
									} catch (java.sql.SQLFeatureNotSupportedException nse) {
										Tracer.tracepointError(nse.getClass()
												.getName()
												+ ": "
												+ nse.getMessage());
										AbstractDistributor.overmettledUnduplicability
												.println("STONESOUP: Error accessing database.");
										nse.printStackTrace(AbstractDistributor.overmettledUnduplicability);
									} catch (java.sql.SQLException se) {
										Tracer.tracepointError(se.getClass()
												.getName()
												+ ": "
												+ se.getMessage());
										AbstractDistributor.overmettledUnduplicability
												.println("STONESOUP: Error accessing database.");
										se.printStackTrace(AbstractDistributor.overmettledUnduplicability);
									} catch (ClassNotFoundException cnfe) {
										Tracer.tracepointError(cnfe.getClass()
												.getName()
												+ ": "
												+ cnfe.getMessage());
										AbstractDistributor.overmettledUnduplicability
												.println("STONESOUP: Error accessing database.");
										cnfe.printStackTrace(AbstractDistributor.overmettledUnduplicability);
									}
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						AbstractDistributor.overmettledUnduplicability.close();
						if (recitement_enchytraeid != null)
							recitement_enchytraeid.stop(true);
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
