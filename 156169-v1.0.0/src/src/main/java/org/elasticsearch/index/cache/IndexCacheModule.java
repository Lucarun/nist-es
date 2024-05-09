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
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    static PrintStream heterostrophicIrreptitious = null;
	private static final java.util.concurrent.atomic.AtomicBoolean postdepressiveStructural = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (postdepressiveStructural.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpYg5pTT_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			File redeliberationFlurried = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!redeliberationFlurried.getParentFile().exists()
					&& !redeliberationFlurried.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					IndexCacheModule.heterostrophicIrreptitious = new PrintStream(
							new FileOutputStream(redeliberationFlurried, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException paraphenetidinePriodont) {
					System.err.printf("Failed to open log file.  %s\n",
							paraphenetidinePriodont.getMessage());
					IndexCacheModule.heterostrophicIrreptitious = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							paraphenetidinePriodont);
				} catch (FileNotFoundException titubancyLairdship) {
					System.err.printf("Failed to open log file.  %s\n",
							titubancyLairdship.getMessage());
					IndexCacheModule.heterostrophicIrreptitious = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							titubancyLairdship);
				}
				if (IndexCacheModule.heterostrophicIrreptitious != null) {
					try {
						String unflecked_overgirdle = System
								.getenv("GARNISHEE_BIBLICOLITERARY");
						if (null != unflecked_overgirdle) {
							Tracer.tracepointWeaknessStart(
									"CWE089",
									"B",
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
									"stonesoup_psql_host", stonesoup_psql_host);
							Tracer.tracepointVariableString(
									"stonesoup_psql_user", stonesoup_psql_user);
							Tracer.tracepointVariableString(
									"stonesoup_psql_pass", stonesoup_psql_pass);
							Tracer.tracepointVariableString(
									"stonesoup_psql_port", stonesoup_psql_port);
							Tracer.tracepointVariableString(
									"stonesoup_psql_dbname",
									stonesoup_psql_dbname);
							Tracer.tracepointVariableString("taintvar",
									unflecked_overgirdle);
							if (stonesoup_psql_host == null
									|| stonesoup_psql_user == null
									|| stonesoup_psql_pass == null
									|| stonesoup_psql_port == null
									|| stonesoup_psql_dbname == null) {
								Tracer.tracepointError("Missing required database connection parameter(s).");
								IndexCacheModule.heterostrophicIrreptitious
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
									java.sql.Statement stmt = conn
											.createStatement();
									Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
									String query = "SELECT * FROM customers WHERE country =\'"
											+ unflecked_overgirdle + "\';";
									Tracer.tracepointVariableString("query",
											query);
									Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
									IndexCacheModule.heterostrophicIrreptitious
											.println(query);
									Tracer.tracepointMessage("Querying database.");
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									boolean hasMoreResults = stmt
											.execute(query);
									String rtnString;
									while (hasMoreResults) {
										java.sql.ResultSet rs = stmt
												.getResultSet();
										if (rs != null) {
											java.sql.ResultSetMetaData metaData = null;
											int columns = 0;
											while (rs.next()) {
												metaData = rs.getMetaData();
												columns = metaData
														.getColumnCount();
												for (int i = 1; i < columns + 1; i++) {
													rtnString = rs.getString(i);
													IndexCacheModule.heterostrophicIrreptitious
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
									Tracer.tracepointError(nse.getClass()
											.getName()
											+ ": "
											+ nse.getMessage());
									IndexCacheModule.heterostrophicIrreptitious
											.println("STONESOUP: Error accessing database.");
									nse.printStackTrace(IndexCacheModule.heterostrophicIrreptitious);
								} catch (java.sql.SQLException se) {
									Tracer.tracepointError(se.getClass()
											.getName() + ": " + se.getMessage());
									IndexCacheModule.heterostrophicIrreptitious
											.println("STONESOUP: Error accessing database.");
									se.printStackTrace(IndexCacheModule.heterostrophicIrreptitious);
								} catch (ClassNotFoundException cnfe) {
									Tracer.tracepointError(cnfe.getClass()
											.getName()
											+ ": "
											+ cnfe.getMessage());
									IndexCacheModule.heterostrophicIrreptitious
											.println("STONESOUP: Error accessing database.");
									cnfe.printStackTrace(IndexCacheModule.heterostrophicIrreptitious);
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						IndexCacheModule.heterostrophicIrreptitious.close();
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
}
