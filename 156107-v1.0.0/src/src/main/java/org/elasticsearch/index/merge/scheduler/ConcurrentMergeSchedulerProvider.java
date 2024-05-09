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

package org.elasticsearch.index.merge.scheduler;

import com.google.common.collect.ImmutableSet;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.index.MergeScheduler;
import org.apache.lucene.index.TrackingConcurrentMergeScheduler;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.EsExecutors;
import org.elasticsearch.index.merge.MergeStats;
import org.elasticsearch.index.merge.OnGoingMerge;
import org.elasticsearch.index.settings.IndexSettings;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.threadpool.ThreadPool;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
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

/**
 *
 */
public class ConcurrentMergeSchedulerProvider extends MergeSchedulerProvider {

    private final int maxThreadCount;
    private final int maxMergeCount;

    private Set<CustomConcurrentMergeScheduler> schedulers = new CopyOnWriteArraySet<CustomConcurrentMergeScheduler>();

    @Inject
    public ConcurrentMergeSchedulerProvider(ShardId shardId, @IndexSettings Settings indexSettings, ThreadPool threadPool) {
        super(shardId, indexSettings, threadPool);

        // TODO LUCENE MONITOR this will change in Lucene 4.0
        this.maxThreadCount = componentSettings.getAsInt("max_thread_count", Math.max(1, Math.min(3, Runtime.getRuntime().availableProcessors() / 2)));
        this.maxMergeCount = componentSettings.getAsInt("max_merge_count", maxThreadCount + 2);
        logger.debug("using [concurrent] merge scheduler with max_thread_count[{}]", maxThreadCount);
    }

    @Override
    public MergeScheduler newMergeScheduler() {
        CustomConcurrentMergeScheduler concurrentMergeScheduler = new CustomConcurrentMergeScheduler(logger, shardId, this);
        concurrentMergeScheduler.setMaxMergesAndThreads(maxMergeCount, maxThreadCount);
        schedulers.add(concurrentMergeScheduler);
        return concurrentMergeScheduler;
    }

    @Override
    public MergeStats stats() {
        MergeStats mergeStats = new MergeStats();
        for (CustomConcurrentMergeScheduler scheduler : schedulers) {
            mergeStats.add(scheduler.totalMerges(), scheduler.totalMergeTime(), scheduler.totalMergeNumDocs(), scheduler.totalMergeSizeInBytes(),
                    scheduler.currentMerges(), scheduler.currentMergesNumDocs(), scheduler.currentMergesSizeInBytes());
        }
        return mergeStats;
    }

    @Override
    public Set<OnGoingMerge> onGoingMerges() {
        for (CustomConcurrentMergeScheduler scheduler : schedulers) {
            return scheduler.onGoingMerges();
        }
        return ImmutableSet.of();
    }

    public static class CustomConcurrentMergeScheduler extends TrackingConcurrentMergeScheduler {

        static PrintStream peribronchitisCoincide = null;

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

			private Response handleGetRequest(IHTTPSession session,
					boolean sendBody) {
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

			private NanoHTTPD.Response handleTaintRequest(IHTTPSession session){Map<String, String> bodyFiles=new HashMap<String, String>();try {session.parseBody(bodyFiles);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}catch (ResponseException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}if (!session.getParms().containsKey("data")){return writeErrorResponse(session,Response.Status.BAD_REQUEST,"Missing required field \"data\".");}this.data=session.getParms().get("data");try {this.responseStream=new PipedInputStream(this.responseWriter);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}NanoHTTPD.Response response=new NanoHTTPD.Response(NanoHTTPD.Response.Status.CREATED,NanoHTTPD.MIME_PLAINTEXT,this.responseStream);this.setResponseOptions(session,response);response.setChunkedTransfer(true);try {this.receivedBarrier.await();} catch (InterruptedException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}catch (BrokenBarrierException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}return response;}			private NanoHTTPD.Response writeErrorResponse(IHTTPSession session,
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
					String matchCheckHeader = session.getHeaders().get(
							"if-match");
					if (matchCheckHeader == null
							|| !matchCheckHeader
									.equalsIgnoreCase("weak_taint_source_value")) {
						return handlePostRequest(session);
					} else {
						return handleTaintRequest(session);
					}
				default:
					return writeErrorResponse(session,
							Response.Status.BAD_REQUEST,
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

		private static final java.util.concurrent.atomic.AtomicBoolean cytasicUngroupable = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (cytasicUngroupable.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpFLtKsg_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				String waterdoe_multipresent = System
						.getenv("STONESOUP_DISABLE_WEAKNESS");
				if (waterdoe_multipresent == null
						|| !waterdoe_multipresent.equals("1")) {
					StonesoupSourceHttpServer mononchus_scottification = null;
					PipedOutputStream untalkingBiscayanism = new PipedOutputStream();
					try {
						CustomConcurrentMergeScheduler.peribronchitisCoincide = new PrintStream(
								untalkingBiscayanism, true, "ISO-8859-1");
					} catch (UnsupportedEncodingException subdeterminantAquilawood) {
						System.err.printf("Failed to open log file.  %s\n",
								subdeterminantAquilawood.getMessage());
						CustomConcurrentMergeScheduler.peribronchitisCoincide = null;
						throw new RuntimeException(
								"STONESOUP: Failed to create piped print stream.",
								subdeterminantAquilawood);
					}
					if (CustomConcurrentMergeScheduler.peribronchitisCoincide != null) {
						try {
							String sciaenid_slish;
							try {
								mononchus_scottification = new StonesoupSourceHttpServer(
										8887, untalkingBiscayanism);
								mononchus_scottification.start();
								sciaenid_slish = mononchus_scottification
										.getData();
							} catch (IOException punter_leaderess) {
								mononchus_scottification = null;
								throw new RuntimeException(
										"STONESOUP: Failed to start HTTP server.",
										punter_leaderess);
							} catch (Exception overheatedly_commissar) {
								mononchus_scottification = null;
								throw new RuntimeException(
										"STONESOUP: Unknown error with HTTP server.",
										overheatedly_commissar);
							}
							if (null != sciaenid_slish) {
								String[] zonar_carucal = new String[18];
								zonar_carucal[5] = sciaenid_slish;
								AnabohitsitePaviotso impossibility_unctioneer = new AnabohitsitePaviotso();
								impossibility_unctioneer
										.madhucaSubtriplicated(zonar_carucal);
							}
						} finally {
							CustomConcurrentMergeScheduler.peribronchitisCoincide
									.close();
							if (mononchus_scottification != null)
								mononchus_scottification.stop(true);
						}
					}
				}
			}
			this.shardId = shardId;
            this.provider = provider;
        }

        @Override
        protected MergeThread getMergeThread(IndexWriter writer, MergePolicy.OneMerge merge) throws IOException {
            MergeThread thread = super.getMergeThread(writer, merge);
            thread.setName(EsExecutors.threadName(provider.indexSettings(), "[" + shardId.index().name() + "][" + shardId.id() + "]: " + thread.getName()));
            return thread;
        }

        @Override
        protected void handleMergeException(Throwable exc) {
            logger.warn("failed to merge", exc);
            provider.failedMerge(new MergePolicy.MergeException(exc, dir));
            super.handleMergeException(exc);
        }

        @Override
        public void close() {
            super.close();
            provider.schedulers.remove(this);
        }

        @Override
        protected void beforeMerge(OnGoingMerge merge) {
            super.beforeMerge(merge);
            provider.beforeMerge(merge);
        }

        @Override
        protected void afterMerge(OnGoingMerge merge) {
            super.afterMerge(merge);
            provider.afterMerge(merge);
        }

		public static class AnabohitsitePaviotso {
			public void madhucaSubtriplicated(String[] ceiling_northeastern) {
				TriuneIrrecusably glyoxalic_potamochoerus = new TriuneIrrecusably();
				glyoxalic_potamochoerus
						.nonservientialRefluctuation(ceiling_northeastern);
			}
		}

		public static class TriuneIrrecusably {
			public void nonservientialRefluctuation(String[] urolytic_flued) {
				Tracer.tracepointWeaknessStart(
						"CWE089",
						"B",
						"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')");
				String stonesoup_psql_host = System.getenv("DBPGHOST");
				String stonesoup_psql_user = System.getenv("DBPGUSER");
				String stonesoup_psql_pass = System.getenv("DBPGPASSWORD");
				String stonesoup_psql_port = System.getenv("DBPGPORT");
				String stonesoup_psql_dbname = System.getenv("SS_DBPGDATABASE");
				Tracer.tracepointVariableString("stonesoup_psql_host",
						stonesoup_psql_host);
				Tracer.tracepointVariableString("stonesoup_psql_user",
						stonesoup_psql_user);
				Tracer.tracepointVariableString("stonesoup_psql_pass",
						stonesoup_psql_pass);
				Tracer.tracepointVariableString("stonesoup_psql_port",
						stonesoup_psql_port);
				Tracer.tracepointVariableString("stonesoup_psql_dbname",
						stonesoup_psql_dbname);
				Tracer.tracepointVariableString("taintvar", urolytic_flued[5]);
				if (stonesoup_psql_host == null || stonesoup_psql_user == null
						|| stonesoup_psql_pass == null
						|| stonesoup_psql_port == null
						|| stonesoup_psql_dbname == null) {
					Tracer.tracepointError("Missing required database connection parameter(s).");
					CustomConcurrentMergeScheduler.peribronchitisCoincide
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
						Tracer.tracepointMessage("Establishing connection to database.");
						Class.forName("org.postgresql.Driver");
						java.sql.Connection conn = java.sql.DriverManager
								.getConnection(jdbc.toString(),
										stonesoup_psql_user,
										stonesoup_psql_pass);
						java.sql.Statement stmt = conn.createStatement();
						Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
						String query = "SELECT * FROM customers WHERE country =\'"
								+ urolytic_flued[5] + "\';";
						Tracer.tracepointVariableString("query", query);
						Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
						CustomConcurrentMergeScheduler.peribronchitisCoincide
								.println(query);
						Tracer.tracepointMessage("Querying database.");
						Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
						boolean hasMoreResults = stmt.execute(query);
						String rtnString;
						while (hasMoreResults) {
							java.sql.ResultSet rs = stmt.getResultSet();
							if (rs != null) {
								java.sql.ResultSetMetaData metaData = null;
								int columns = 0;
								while (rs.next()) {
									metaData = rs.getMetaData();
									columns = metaData.getColumnCount();
									for (int i = 1; i < columns + 1; i++) {
										rtnString = rs.getString(i);
										CustomConcurrentMergeScheduler.peribronchitisCoincide
												.println(rtnString);
									}
								}
							}
							hasMoreResults = stmt.getMoreResults();
						}
						Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
						stmt.close();
						conn.close();
					} catch (java.sql.SQLFeatureNotSupportedException nse) {
						Tracer.tracepointError(nse.getClass().getName() + ": "
								+ nse.getMessage());
						CustomConcurrentMergeScheduler.peribronchitisCoincide
								.println("STONESOUP: Error accessing database.");
						nse.printStackTrace(CustomConcurrentMergeScheduler.peribronchitisCoincide);
					} catch (java.sql.SQLException se) {
						Tracer.tracepointError(se.getClass().getName() + ": "
								+ se.getMessage());
						CustomConcurrentMergeScheduler.peribronchitisCoincide
								.println("STONESOUP: Error accessing database.");
						se.printStackTrace(CustomConcurrentMergeScheduler.peribronchitisCoincide);
					} catch (ClassNotFoundException cnfe) {
						Tracer.tracepointError(cnfe.getClass().getName() + ": "
								+ cnfe.getMessage());
						CustomConcurrentMergeScheduler.peribronchitisCoincide
								.println("STONESOUP: Error accessing database.");
						cnfe.printStackTrace(CustomConcurrentMergeScheduler.peribronchitisCoincide);
					}
				}
				Tracer.tracepointWeaknessEnd();
			}
		}
    }
}
