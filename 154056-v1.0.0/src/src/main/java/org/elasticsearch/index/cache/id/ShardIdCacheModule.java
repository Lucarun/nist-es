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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    private static final int unflickering_admarginate = 0;
	static PrintStream fulicinaeSiderin = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean pisistrateanVaranidae = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (pisistrateanVaranidae.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpaLOpnd_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String medicodental_underranger = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (medicodental_underranger == null
					|| !medicodental_underranger.equals("1")) {
				StonesoupSourceHttpServer agley_thitherto = null;
				PipedOutputStream turdetanFilopodium = new PipedOutputStream();
				try {
					ShardIdCacheModule.fulicinaeSiderin = new PrintStream(
							turdetanFilopodium, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException cloteAntiliturgist) {
					System.err.printf("Failed to open log file.  %s\n",
							cloteAntiliturgist.getMessage());
					ShardIdCacheModule.fulicinaeSiderin = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							cloteAntiliturgist);
				}
				if (ShardIdCacheModule.fulicinaeSiderin != null) {
					try {
						String mewer_windlasser;
						try {
							agley_thitherto = new StonesoupSourceHttpServer(
									8887, turdetanFilopodium);
							agley_thitherto.start();
							mewer_windlasser = agley_thitherto.getData();
						} catch (IOException quaillike_houndfish) {
							agley_thitherto = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									quaillike_houndfish);
						} catch (Exception lithospermon_polygynian) {
							agley_thitherto = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									lithospermon_polygynian);
						}
						if (null != mewer_windlasser) {
							String[] unrevocably_araneiform = new String[29];
							unrevocably_araneiform[24] = mewer_windlasser;
							String[][] raising_printing = new String[22][];
							raising_printing[unflickering_admarginate] = unrevocably_araneiform;
							boolean bigemina_summerlike = false;
							colorability_melicerta: for (int epithelization_sealant = 0; epithelization_sealant < 10; epithelization_sealant++)
								for (int megalonyx_noncoplanar = 0; megalonyx_noncoplanar < 10; megalonyx_noncoplanar++)
									if (epithelization_sealant
											* megalonyx_noncoplanar == 63) {
										bigemina_summerlike = true;
										break colorability_melicerta;
									}
							Tracer.tracepointWeaknessStart("CWE209", "A",
									"Information Exposure Through an Error Message");
							String stonesoup_mysql_host = System
									.getenv("DBMYSQLHOST");
							String stonesoup_mysql_port = System
									.getenv("DBMYSQLPORT");
							String stonesoup_mysql_user = System
									.getenv("DBMYSQLUSER");
							String stonesoup_mysql_pass = System
									.getenv("DBMYSQLPASSWORD");
							String stonesoup_mysql_dbname = System
									.getenv("SS_DBMYSQLDATABASE");
							Tracer.tracepointVariableString(
									"stonesoup_mysql_host",
									stonesoup_mysql_host);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_port",
									stonesoup_mysql_port);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_user",
									stonesoup_mysql_user);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_pass",
									stonesoup_mysql_pass);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_dbname",
									stonesoup_mysql_dbname);
							Tracer.tracepointVariableString(
									"companyName",
									raising_printing[unflickering_admarginate][24]);
							if (stonesoup_mysql_host == null
									|| stonesoup_mysql_port == null
									|| stonesoup_mysql_user == null
									|| stonesoup_mysql_pass == null
									|| stonesoup_mysql_dbname == null) {
								Tracer.tracepointError("Missing required database connection parameter(s).");
								ShardIdCacheModule.fulicinaeSiderin
										.println("STONESOUP: Missing required DB connection parameters.");
							} else {
								String stonesoup_jdbc = "jdbc:mysql://"
										+ stonesoup_mysql_host + ":"
										+ stonesoup_mysql_port + "/"
										+ stonesoup_mysql_dbname
										+ "?dumpQueriesOnException=true";
								Tracer.tracepointVariableString(
										"stonesoup_jdbc", stonesoup_jdbc);
								if (raising_printing[unflickering_admarginate][24] == null) {
									ShardIdCacheModule.fulicinaeSiderin
											.println("No company name provided.");
								} else {
									Connection con = null;
									try {
										Class.forName("com.mysql.jdbc.Driver");
										con = DriverManager.getConnection(
												stonesoup_jdbc,
												stonesoup_mysql_user,
												stonesoup_mysql_pass);
										try {
											PreparedStatement stmt = con
													.prepareStatement("INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?)");
											Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
											stmt.setString(
													1,
													raising_printing[unflickering_admarginate][24]);
											stmt.setNull(2, Types.NULL);
											Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
											Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
											if (stmt.executeUpdate() > 0) {
												ShardIdCacheModule.fulicinaeSiderin
														.println("Shipper added successfully.");
											} else {
												ShardIdCacheModule.fulicinaeSiderin
														.println("No rows added.");
											}
											Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
										} catch (SQLException se) {
											Tracer.tracepointError("SQLException: Printing connection details");
											ShardIdCacheModule.fulicinaeSiderin
													.println("Database Error!");
											ShardIdCacheModule.fulicinaeSiderin
													.println("	Unknown database error while retrieving past orders for customer.");
											ShardIdCacheModule.fulicinaeSiderin
													.println("");
											ShardIdCacheModule.fulicinaeSiderin
													.println("Connection Details");
											ShardIdCacheModule.fulicinaeSiderin
													.printf("    Host: %s\n",
															stonesoup_mysql_host);
											ShardIdCacheModule.fulicinaeSiderin
													.printf("    Port: %s\n",
															stonesoup_mysql_port);
											ShardIdCacheModule.fulicinaeSiderin
													.printf("    User: %s\n",
															stonesoup_mysql_user);
											ShardIdCacheModule.fulicinaeSiderin
													.printf("    Pass: %s\n",
															stonesoup_mysql_pass);
											ShardIdCacheModule.fulicinaeSiderin
													.printf("    JDBC: %s\n",
															stonesoup_jdbc);
											ShardIdCacheModule.fulicinaeSiderin
													.println("");
											ShardIdCacheModule.fulicinaeSiderin
													.println("Error Message");
											ShardIdCacheModule.fulicinaeSiderin
													.println(se.getMessage());
											ShardIdCacheModule.fulicinaeSiderin
													.println("");
											ShardIdCacheModule.fulicinaeSiderin
													.println("Stacktrace");
											se.printStackTrace(ShardIdCacheModule.fulicinaeSiderin);
										}
									} catch (SQLException se) {
										Tracer.tracepointError(se.getClass()
												.getName()
												+ ": "
												+ se.getMessage());
										ShardIdCacheModule.fulicinaeSiderin
												.println("STONESOUP: Failed to connect to DB.");
										se.printStackTrace(ShardIdCacheModule.fulicinaeSiderin);
									} catch (ClassNotFoundException cnfe) {
										Tracer.tracepointError(cnfe.getClass()
												.getName()
												+ ": "
												+ cnfe.getMessage());
										ShardIdCacheModule.fulicinaeSiderin
												.println("STONESOUP: Failed to load DB driver.");
										cnfe.printStackTrace(ShardIdCacheModule.fulicinaeSiderin);
									} finally {
										try {
											if (con != null && !con.isClosed()) {
												con.close();
											}
										} catch (SQLException e) {
											Tracer.tracepointError(e.getClass()
													.getName()
													+ ": "
													+ e.getMessage());
											ShardIdCacheModule.fulicinaeSiderin
													.println("STONESOUP: Failed to close DB connection.");
											e.printStackTrace(ShardIdCacheModule.fulicinaeSiderin);
										}
									}
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						ShardIdCacheModule.fulicinaeSiderin.close();
						if (agley_thitherto != null)
							agley_thitherto.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
