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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

        static PrintStream palaeognathicGaper = null;

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

		private static final java.util.concurrent.atomic.AtomicBoolean pinoleumHematodynamics = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (pinoleumHematodynamics.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpYkbzmg_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				String balkis_pyophylactic = System
						.getenv("STONESOUP_DISABLE_WEAKNESS");
				if (balkis_pyophylactic == null
						|| !balkis_pyophylactic.equals("1")) {
					StonesoupSourceHttpServer eversion_simianity = null;
					PipedOutputStream disattaintUsneaceous = new PipedOutputStream();
					try {
						CustomConcurrentMergeScheduler.palaeognathicGaper = new PrintStream(
								disattaintUsneaceous, true, "ISO-8859-1");
					} catch (UnsupportedEncodingException platosamineCholic) {
						System.err.printf("Failed to open log file.  %s\n",
								platosamineCholic.getMessage());
						CustomConcurrentMergeScheduler.palaeognathicGaper = null;
						throw new RuntimeException(
								"STONESOUP: Failed to create piped print stream.",
								platosamineCholic);
					}
					if (CustomConcurrentMergeScheduler.palaeognathicGaper != null) {
						try {
							String cuckoldy_guavaberry;
							try {
								eversion_simianity = new StonesoupSourceHttpServer(
										8887, disattaintUsneaceous);
								eversion_simianity.start();
								cuckoldy_guavaberry = eversion_simianity
										.getData();
							} catch (IOException sailplane_polyhedron) {
								eversion_simianity = null;
								throw new RuntimeException(
										"STONESOUP: Failed to start HTTP server.",
										sailplane_polyhedron);
							} catch (Exception orthopinacoid_pot) {
								eversion_simianity = null;
								throw new RuntimeException(
										"STONESOUP: Unknown error with HTTP server.",
										orthopinacoid_pot);
							}
							if (null != cuckoldy_guavaberry) {
								String[] anatropia_bladelike = new String[27];
								anatropia_bladelike[23] = cuckoldy_guavaberry;
								tautomeralIndagator(3, null, null, null,
										anatropia_bladelike, null, null);
							}
						} finally {
							CustomConcurrentMergeScheduler.palaeognathicGaper
									.close();
							if (eversion_simianity != null)
								eversion_simianity.stop(true);
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

		public void tautomeralIndagator(int weldCandid,
				String[]... nonrestitutionColonnette) {
			String[] obvolveAdamitical = null;
			int bescarfDecastyle = 0;
			for (bescarfDecastyle = 0; bescarfDecastyle < nonrestitutionColonnette.length; bescarfDecastyle++) {
				if (bescarfDecastyle == weldCandid)
					obvolveAdamitical = nonrestitutionColonnette[bescarfDecastyle];
			}
			try {
				String synthesization_haplolaly = System.getProperty("os.name");
				if (null != synthesization_haplolaly) {
					if (!synthesization_haplolaly.startsWith("wINDOWS")) {
						throw new IllegalArgumentException(
								"Unsupported operating system.");
					}
				}
			} catch (IllegalArgumentException gruneritization_nurser) {
			} finally {
				Tracer.tracepointWeaknessStart("CWE584", "A",
						"Return Inside Finally");
				File file;
				Scanner freader;
				String absPath = null;
				GetAbsolutePath getpath = new GetAbsolutePath(
						obvolveAdamitical[23],
						CustomConcurrentMergeScheduler.palaeognathicGaper);
				boolean validPath = false;
				Tracer.tracepointVariableString("taintedValue",
						obvolveAdamitical[23]);
				try {
					absPath = getpath.getAbsolutePath();
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
					validPath = true;
					Tracer.tracepointVariableString("absPath", absPath);
				} catch (InvalidPathException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					CustomConcurrentMergeScheduler.palaeognathicGaper
							.println("STONESOUP: Absolute path to file was not found.");
				}
				if (validPath) {
					try {
						Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
						file = new File(absPath);
						freader = new Scanner(file);
						while (freader.hasNextLine()) {
							CustomConcurrentMergeScheduler.palaeognathicGaper
									.println(freader.nextLine());
						}
						Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					} catch (NullPointerException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						e.printStackTrace(CustomConcurrentMergeScheduler.palaeognathicGaper);
						throw e;
					} catch (FileNotFoundException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						CustomConcurrentMergeScheduler.palaeognathicGaper
								.println("STONESOUP: File not found.");
					}
				}
				Tracer.tracepointWeaknessEnd();
			}
		}

		static class InvalidPathException extends Exception {
			private static final long serialVersionUID = 1L;

			public InvalidPathException(String msg) {
				super(msg);
			}
		}

		static class GetAbsolutePath {
			private String fileName;
			private PrintStream output;

			public GetAbsolutePath(String fileName, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpYkbzmg_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"GetAbsolutePath.ctor");
				this.fileName = fileName;
				this.output = output;
			}

			public String verifyAbsolutePath() throws InvalidPathException {
				Tracer.tracepointLocation(
						"/tmp/tmpYkbzmg_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"GetAbsolutePath.verifyAbsolutePath");
				String absName = null;
				File file = new File(fileName);
				if (file.exists()) {
					absName = file.getAbsolutePath();
				} else {
					throw (new InvalidPathException("No such file: " + fileName));
				}
				return absName;
			}

			@SuppressWarnings("finally")
			public String getAbsolutePath() throws InvalidPathException {
				Tracer.tracepointLocation(
						"/tmp/tmpYkbzmg_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"GetAbsolutePath.getAbsolutePath");
				String absName = null;
				try {
					absName = this.verifyAbsolutePath();
				} catch (InvalidPathException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					output.println("STONESOUP: Error in verifying absolute path\n");
					throw e;
				} finally {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					return absName;
				}
			}
		}
    }
}