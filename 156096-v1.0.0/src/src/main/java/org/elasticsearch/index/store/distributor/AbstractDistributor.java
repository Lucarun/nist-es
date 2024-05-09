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
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.util.Random;

public abstract class AbstractDistributor implements Distributor {

    public class AnnunciableTemplize {
		private Object befreckle_sleepyhead;

		public AnnunciableTemplize(Object befreckle_sleepyhead) {
			this.befreckle_sleepyhead = befreckle_sleepyhead;
		}

		public Object getbefreckle_sleepyhead() {
			return this.befreckle_sleepyhead;
		}
	}

	static PrintStream abovedeckInelaborated = null;
	private static final java.util.concurrent.atomic.AtomicBoolean protectionizeHanch = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (protectionizeHanch.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpdblOcC_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File analepsyOrderliness = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!analepsyOrderliness.getParentFile().exists()
					&& !analepsyOrderliness.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.abovedeckInelaborated = new PrintStream(
							new FileOutputStream(analepsyOrderliness, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException verminproofOutmerchant) {
					System.err.printf("Failed to open log file.  %s\n",
							verminproofOutmerchant.getMessage());
					AbstractDistributor.abovedeckInelaborated = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							verminproofOutmerchant);
				} catch (FileNotFoundException tchickUnbowed) {
					System.err.printf("Failed to open log file.  %s\n",
							tchickUnbowed.getMessage());
					AbstractDistributor.abovedeckInelaborated = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							tchickUnbowed);
				}
				if (AbstractDistributor.abovedeckInelaborated != null) {
					try {
						String lactamide_castigate = System
								.getenv("MANO_ACTUALIZE");
						if (null != lactamide_castigate) {
							Object nasolabial_schoolmasterish = lactamide_castigate;
							AnnunciableTemplize livian_phlebolith = new AnnunciableTemplize(
									nasolabial_schoolmasterish);
							try {
								String metameral_businesswoman = System
										.getProperty("os.name");
								if (null != metameral_businesswoman) {
									if (!metameral_businesswoman
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException keerogue_educative) {
							} finally {
								Tracer.tracepointWeaknessStart(
										"CWE089",
										"C",
										"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')");
								String stonesoup_mysql_host = System
										.getenv("DBMYSQLHOST");
								String stonesoup_mysql_user = System
										.getenv("DBMYSQLUSER");
								String stonesoup_mysql_pass = System
										.getenv("DBMYSQLPASSWORD");
								String stonesoup_mysql_port = System
										.getenv("DBMYSQLPORT");
								String stonesoup_mysql_dbname = System
										.getenv("SS_DBMYSQLDATABASE");
								Tracer.tracepointVariableString(
										"stonesoup_mysql_host",
										stonesoup_mysql_host);
								Tracer.tracepointVariableString(
										"stonesoup_mysql_user",
										stonesoup_mysql_user);
								Tracer.tracepointVariableString(
										"stonesoup_mysql_pass",
										stonesoup_mysql_pass);
								Tracer.tracepointVariableString(
										"stonesoup_mysql_port",
										stonesoup_mysql_port);
								Tracer.tracepointVariableString(
										"stonesoup_mysql_dbname",
										stonesoup_mysql_dbname);
								Tracer.tracepointVariableString("shipper_name",
										((String) livian_phlebolith
												.getbefreckle_sleepyhead()));
								if (stonesoup_mysql_host == null
										|| stonesoup_mysql_user == null
										|| stonesoup_mysql_pass == null
										|| stonesoup_mysql_port == null
										|| stonesoup_mysql_dbname == null) {
									Tracer.tracepointError("Missing required database connection parameter(s).");
									AbstractDistributor.abovedeckInelaborated
											.println("STONESOUP: Missing required database connection parameters.");
								} else {
									try {
										StringBuffer jdbc = new StringBuffer(
												"jdbc:mysql://");
										jdbc.append(stonesoup_mysql_host);
										jdbc.append(":");
										jdbc.append(stonesoup_mysql_port);
										jdbc.append("/");
										jdbc.append(stonesoup_mysql_dbname);
										jdbc.append("?allowMultiQueries=true");
										Class.forName("com.mysql.jdbc.Driver")
												.newInstance();
										Tracer.tracepointMessage("Establishing connection to database.");
										java.sql.Connection con = java.sql.DriverManager
												.getConnection(jdbc.toString(),
														stonesoup_mysql_user,
														stonesoup_mysql_pass);
										java.sql.Statement stmt = con
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
												+ ((String) livian_phlebolith
														.getbefreckle_sleepyhead())
												+ "\');";
										Tracer.tracepointVariableString(
												"queryString", queryString);
										Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
										AbstractDistributor.abovedeckInelaborated
												.println(queryString);
										Tracer.tracepointMessage("Querying database.");
										Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
										stmt.execute(queryString);
										AbstractDistributor.abovedeckInelaborated
												.println("Number of Rows Affected: "
														+ stmt.getUpdateCount());
										Tracer.tracepointVariableInt(
												"rows affected",
												stmt.getUpdateCount());
										Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
										con.close();
									} catch (java.sql.SQLException se) {
										Tracer.tracepointError(se.getClass()
												.getName()
												+ ": "
												+ se.getMessage());
										Tracer.tracepointError("Error accessing database.");
										AbstractDistributor.abovedeckInelaborated
												.println("STONESOUP: Error accessing database.");
										se.printStackTrace(AbstractDistributor.abovedeckInelaborated);
									} catch (ClassNotFoundException cnfe) {
										Tracer.tracepointError(cnfe.getClass()
												.getName()
												+ ": "
												+ cnfe.getMessage());
										AbstractDistributor.abovedeckInelaborated
												.println("STONESOUP: Error accessing database.");
										cnfe.printStackTrace(AbstractDistributor.abovedeckInelaborated);
									} catch (IllegalAccessException iae) {
										Tracer.tracepointError(iae.getClass()
												.getName()
												+ ": "
												+ iae.getMessage());
										AbstractDistributor.abovedeckInelaborated
												.println("STONESOUP: Error accessing database.");
										iae.printStackTrace(AbstractDistributor.abovedeckInelaborated);
									} catch (InstantiationException ie) {
										Tracer.tracepointError(ie.getClass()
												.getName()
												+ ": "
												+ ie.getMessage());
										AbstractDistributor.abovedeckInelaborated
												.println("STONESOUP: Error accessing database.");
										ie.printStackTrace(AbstractDistributor.abovedeckInelaborated);
									}
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						AbstractDistributor.abovedeckInelaborated.close();
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
