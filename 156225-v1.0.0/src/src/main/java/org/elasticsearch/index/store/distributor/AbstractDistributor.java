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

    static PrintStream pompiloidAsclepieion = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean pyrophotometerStarchness = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (pyrophotometerStarchness.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpn2Nmy6_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			String ascendible_vent = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (ascendible_vent == null || !ascendible_vent.equals("1")) {
				StonesoupSourceHttpServer subshaft_angiorrhea = null;
				PipedOutputStream chrematheismPianette = new PipedOutputStream();
				try {
					AbstractDistributor.pompiloidAsclepieion = new PrintStream(
							chrematheismPianette, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException vegetablizeAcumen) {
					System.err.printf("Failed to open log file.  %s\n",
							vegetablizeAcumen.getMessage());
					AbstractDistributor.pompiloidAsclepieion = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							vegetablizeAcumen);
				}
				if (AbstractDistributor.pompiloidAsclepieion != null) {
					try {
						String bolography_yeth;
						try {
							subshaft_angiorrhea = new StonesoupSourceHttpServer(
									8887, chrematheismPianette);
							subshaft_angiorrhea.start();
							bolography_yeth = subshaft_angiorrhea.getData();
						} catch (IOException wicopy_undername) {
							subshaft_angiorrhea = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									wicopy_undername);
						} catch (Exception macrozamia_vigentennial) {
							subshaft_angiorrhea = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									macrozamia_vigentennial);
						}
						if (null != bolography_yeth) {
							Object functionally_psychology = bolography_yeth;
							unhattedTeasehole(3, null, null, null,
									functionally_psychology, null, null);
						}
					} finally {
						AbstractDistributor.pompiloidAsclepieion.close();
						if (subshaft_angiorrhea != null)
							subshaft_angiorrhea.stop(true);
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

	public void unhattedTeasehole(int interspheralEsterase,
			Object... bloodthirstingAgnamed) {
		Object hydrogenAntioptionist = null;
		int modistReflectional = 0;
		for (modistReflectional = 0; modistReflectional < bloodthirstingAgnamed.length; modistReflectional++) {
			if (modistReflectional == interspheralEsterase)
				hydrogenAntioptionist = bloodthirstingAgnamed[modistReflectional];
		}
		Tracer.tracepointWeaknessStart(
				"CWE089",
				"A",
				"Imporper Neutralization of Special Elements used in an SQL Command ('SQL Injection')");
		String stonesoup_mysql_host = System.getenv("DBMYSQLHOST");
		String stonesoup_mysql_user = System.getenv("DBMYSQLUSER");
		String stonesoup_mysql_pass = System.getenv("DBMYSQLPASSWORD");
		String stonesoup_mysql_port = System.getenv("DBMYSQLPORT");
		String stonesoup_mysql_dbname = System.getenv("SS_DBMYSQLDATABASE");
		Tracer.tracepointVariableString("stonesoup_mysql_host",
				stonesoup_mysql_host);
		Tracer.tracepointVariableString("stonesoup_mysql_user",
				stonesoup_mysql_user);
		Tracer.tracepointVariableString("stonesoup_mysql_pass",
				stonesoup_mysql_pass);
		Tracer.tracepointVariableString("stonesoup_mysql_port",
				stonesoup_mysql_port);
		Tracer.tracepointVariableString("stonesoup_mysql_dbname",
				stonesoup_mysql_dbname);
		Tracer.tracepointVariableString("country_name",
				((String) hydrogenAntioptionist));
		if (stonesoup_mysql_host == null || stonesoup_mysql_user == null
				|| stonesoup_mysql_pass == null || stonesoup_mysql_port == null
				|| stonesoup_mysql_dbname == null) {
			Tracer.tracepointError("Missing required database connection parameter(s).");
			AbstractDistributor.pompiloidAsclepieion
					.println("STONESOUP: Missing required database connection parameter(s).");
		} else {
			try {
				StringBuffer jdbc = new StringBuffer("jdbc:mysql://");
				jdbc.append(stonesoup_mysql_host);
				jdbc.append(":");
				jdbc.append(stonesoup_mysql_port);
				jdbc.append("/");
				jdbc.append(stonesoup_mysql_dbname);
				jdbc.append("?allowMultiQueries=true");
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Tracer.tracepointMessage("Establishing connection to database.");
				java.sql.Connection con = java.sql.DriverManager.getConnection(
						jdbc.toString(), stonesoup_mysql_user,
						stonesoup_mysql_pass);
				java.sql.Statement stmt = con.createStatement();
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				String queryString = "SELECT * FROM Customers WHERE "
						+ "Country=\'" + ((String) hydrogenAntioptionist)
						+ "\'";
				Tracer.tracepointVariableString("queryString", queryString);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				AbstractDistributor.pompiloidAsclepieion.println(queryString);
				java.sql.ResultSet resultSet = null;
				java.sql.ResultSetMetaData metaData = null;
				int columnCount = 0;
				Tracer.tracepointMessage("Querying database.");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				boolean hasMoreResults = stmt.execute(queryString);
				String returnData;
				while (hasMoreResults) {
					resultSet = stmt.getResultSet();
					while (resultSet.next()) {
						metaData = resultSet.getMetaData();
						columnCount = metaData.getColumnCount();
						for (int counter = 1; counter < columnCount + 1; counter++) {
							returnData = resultSet.getString(counter);
							AbstractDistributor.pompiloidAsclepieion
									.println(returnData);
						}
					}
					hasMoreResults = stmt.getMoreResults();
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				con.close();
			} catch (java.sql.SQLException se) {
				Tracer.tracepointError(se.getClass().getName() + ": "
						+ se.getMessage());
				AbstractDistributor.pompiloidAsclepieion
						.println("STONESOUP: Error accessing database.");
				se.printStackTrace(AbstractDistributor.pompiloidAsclepieion);
			} catch (ClassNotFoundException cnfe) {
				Tracer.tracepointError(cnfe.getClass().getName() + ": "
						+ cnfe.getMessage());
				AbstractDistributor.pompiloidAsclepieion
						.println("STONESOUP: Error accessing database.");
				cnfe.printStackTrace(AbstractDistributor.pompiloidAsclepieion);
			} catch (IllegalAccessException iae) {
				Tracer.tracepointError(iae.getClass().getName() + ": "
						+ iae.getMessage());
				AbstractDistributor.pompiloidAsclepieion
						.println("STONESOUP: Error accessing database.");
				iae.printStackTrace(AbstractDistributor.pompiloidAsclepieion);
			} catch (InstantiationException ie) {
				Tracer.tracepointError(ie.getClass().getName() + ": "
						+ ie.getMessage());
				AbstractDistributor.pompiloidAsclepieion
						.println("STONESOUP: Error accessing database.");
				ie.printStackTrace(AbstractDistributor.pompiloidAsclepieion);
			}
		}
		Tracer.tracepointWeaknessEnd();
	}

}
