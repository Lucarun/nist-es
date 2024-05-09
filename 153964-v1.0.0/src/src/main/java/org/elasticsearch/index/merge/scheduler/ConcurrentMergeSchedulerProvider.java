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
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

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

        static PrintStream verisimilarlyIntuitionistic = null;

		private static final java.util.concurrent.atomic.AtomicBoolean medusoidMycetes = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (medusoidMycetes.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpmdZEuG_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File viridescenceGlauconite = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!viridescenceGlauconite.getParentFile().exists()
						&& !viridescenceGlauconite.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic = new PrintStream(
								new FileOutputStream(viridescenceGlauconite,
										false), true, "ISO-8859-1");
					} catch (UnsupportedEncodingException wettishChevage) {
						System.err.printf("Failed to open log file.  %s\n",
								wettishChevage.getMessage());
						CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								wettishChevage);
					} catch (FileNotFoundException harrowDetect) {
						System.err.printf("Failed to open log file.  %s\n",
								harrowDetect.getMessage());
						CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								harrowDetect);
					}
					if (CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic != null) {
						try {
							String thunderbearer_seaquake = System
									.getenv("POOPED_DEMIASSIGNATION");
							if (null != thunderbearer_seaquake) {
								saurornithesSomersault(3, null, null, null,
										thunderbearer_seaquake, null, null);
							}
						} finally {
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.close();
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

		public void saurornithesSomersault(int brookieUnreturned,
				String... buchoniteNoggen) {
			String fendyAsshead = null;
			int coplotterBruit = 0;
			for (coplotterBruit = 0; coplotterBruit < buchoniteNoggen.length; coplotterBruit++) {
				if (coplotterBruit == brookieUnreturned)
					fendyAsshead = buchoniteNoggen[coplotterBruit];
			}
			boolean enfeature_symptomatics = false;
			phaneroscope_japanee: for (int goli_yarkand = 0; goli_yarkand < 10; goli_yarkand++)
				for (int nightshade_paladin = 0; nightshade_paladin < 10; nightshade_paladin++)
					if (goli_yarkand * nightshade_paladin == 63) {
						enfeature_symptomatics = true;
						break phaneroscope_japanee;
					}
			Tracer.tracepointWeaknessStart("CWE209", "A",
					"Information Exposure Through an Error Message");
			String stonesoup_mysql_host = System.getenv("DBMYSQLHOST");
			String stonesoup_mysql_port = System.getenv("DBMYSQLPORT");
			String stonesoup_mysql_user = System.getenv("DBMYSQLUSER");
			String stonesoup_mysql_pass = System.getenv("DBMYSQLPASSWORD");
			String stonesoup_mysql_dbname = System.getenv("SS_DBMYSQLDATABASE");
			Tracer.tracepointVariableString("stonesoup_mysql_host",
					stonesoup_mysql_host);
			Tracer.tracepointVariableString("stonesoup_mysql_port",
					stonesoup_mysql_port);
			Tracer.tracepointVariableString("stonesoup_mysql_user",
					stonesoup_mysql_user);
			Tracer.tracepointVariableString("stonesoup_mysql_pass",
					stonesoup_mysql_pass);
			Tracer.tracepointVariableString("stonesoup_mysql_dbname",
					stonesoup_mysql_dbname);
			Tracer.tracepointVariableString("companyName", fendyAsshead);
			if (stonesoup_mysql_host == null || stonesoup_mysql_port == null
					|| stonesoup_mysql_user == null
					|| stonesoup_mysql_pass == null
					|| stonesoup_mysql_dbname == null) {
				Tracer.tracepointError("Missing required database connection parameter(s).");
				CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
						.println("STONESOUP: Missing required DB connection parameters.");
			} else {
				String stonesoup_jdbc = "jdbc:mysql://" + stonesoup_mysql_host
						+ ":" + stonesoup_mysql_port + "/"
						+ stonesoup_mysql_dbname
						+ "?dumpQueriesOnException=true";
				Tracer.tracepointVariableString("stonesoup_jdbc",
						stonesoup_jdbc);
				if (fendyAsshead == null) {
					CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
							.println("No company name provided.");
				} else {
					Connection con = null;
					try {
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection(stonesoup_jdbc,
								stonesoup_mysql_user, stonesoup_mysql_pass);
						try {
							PreparedStatement stmt = con
									.prepareStatement("INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?)");
							Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
							stmt.setString(1, fendyAsshead);
							stmt.setNull(2, Types.NULL);
							Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
							Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
							if (stmt.executeUpdate() > 0) {
								CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
										.println("Shipper added successfully.");
							} else {
								CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
										.println("No rows added.");
							}
							Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
						} catch (SQLException se) {
							Tracer.tracepointError("SQLException: Printing connection details");
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println("Database Error!");
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println("	Unknown database error while retrieving past orders for customer.");
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println("");
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println("Connection Details");
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.printf("    Host: %s\n",
											stonesoup_mysql_host);
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.printf("    Port: %s\n",
											stonesoup_mysql_port);
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.printf("    User: %s\n",
											stonesoup_mysql_user);
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.printf("    Pass: %s\n",
											stonesoup_mysql_pass);
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.printf("    JDBC: %s\n", stonesoup_jdbc);
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println("");
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println("Error Message");
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println(se.getMessage());
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println("");
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println("Stacktrace");
							se.printStackTrace(CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic);
						}
					} catch (SQLException se) {
						Tracer.tracepointError(se.getClass().getName() + ": "
								+ se.getMessage());
						CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
								.println("STONESOUP: Failed to connect to DB.");
						se.printStackTrace(CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic);
					} catch (ClassNotFoundException cnfe) {
						Tracer.tracepointError(cnfe.getClass().getName() + ": "
								+ cnfe.getMessage());
						CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
								.println("STONESOUP: Failed to load DB driver.");
						cnfe.printStackTrace(CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic);
					} finally {
						try {
							if (con != null && !con.isClosed()) {
								con.close();
							}
						} catch (SQLException e) {
							Tracer.tracepointError(e.getClass().getName()
									+ ": " + e.getMessage());
							CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic
									.println("STONESOUP: Failed to close DB connection.");
							e.printStackTrace(CustomConcurrentMergeScheduler.verisimilarlyIntuitionistic);
						}
					}
				}
			}
			Tracer.tracepointWeaknessEnd();
		}
    }
}
