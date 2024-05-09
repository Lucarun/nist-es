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
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    public class YesterPreaseptic {
		private String[] nursekin_eumycetes;

		public YesterPreaseptic(String[] nursekin_eumycetes) {
			this.nursekin_eumycetes = nursekin_eumycetes;
		}

		public String[] getnursekin_eumycetes() {
			return this.nursekin_eumycetes;
		}
	}

	static PrintStream conductometricLynette = null;
	private static final java.util.concurrent.atomic.AtomicBoolean artelTucum = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (artelTucum.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp9SWT08_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			File sparklikeMoieter = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!sparklikeMoieter.getParentFile().exists()
					&& !sparklikeMoieter.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					IndexCacheModule.conductometricLynette = new PrintStream(
							new FileOutputStream(sparklikeMoieter, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException castanospermumGirr) {
					System.err.printf("Failed to open log file.  %s\n",
							castanospermumGirr.getMessage());
					IndexCacheModule.conductometricLynette = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							castanospermumGirr);
				} catch (FileNotFoundException ravelerShemitic) {
					System.err.printf("Failed to open log file.  %s\n",
							ravelerShemitic.getMessage());
					IndexCacheModule.conductometricLynette = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							ravelerShemitic);
				}
				if (IndexCacheModule.conductometricLynette != null) {
					try {
						String preanticipate_peritoneoclysis = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (preanticipate_peritoneoclysis == null
								|| !preanticipate_peritoneoclysis.equals("1")) {
							String semibarren_propugner = System
									.getenv("NONINTERSECTING_HANSGRAVE");
							if (null != semibarren_propugner) {
								File townsite_amboceptoid = new File(
										semibarren_propugner);
								if (townsite_amboceptoid.exists()
										&& !townsite_amboceptoid.isDirectory()) {
									try {
										String orthotropy_importer;
										Scanner septivalent_daygoing = new Scanner(
												townsite_amboceptoid, "UTF-8")
												.useDelimiter("\\A");
										if (septivalent_daygoing.hasNext())
											orthotropy_importer = septivalent_daygoing
													.next();
										else
											orthotropy_importer = "";
										if (null != orthotropy_importer) {
											String[] semilucent_patchwork = new String[29];
											semilucent_patchwork[5] = orthotropy_importer;
											YesterPreaseptic icicle_alsbachite = new YesterPreaseptic(
													semilucent_patchwork);
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
											Tracer.tracepointVariableString(
													"shipper_name",
													icicle_alsbachite
															.getnursekin_eumycetes()[5]);
											if (stonesoup_psql_host == null
													|| stonesoup_psql_user == null
													|| stonesoup_psql_pass == null
													|| stonesoup_psql_port == null
													|| stonesoup_psql_dbname == null) {
												Tracer.tracepointError("Missing required database connection parameter(s).");
												IndexCacheModule.conductometricLynette
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
															.getConnection(
																	jdbc.toString(),
																	stonesoup_psql_user,
																	stonesoup_psql_pass);
													Tracer.tracepointMessage("Establishing connection to database.");
													java.sql.Statement stmt = conn
															.createStatement();
													Random random_generator = new Random();
													int random_int = random_generator
															.nextInt(1000) + 100;
													Tracer.tracepointVariableInt(
															"random_int",
															random_int);
													Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
													String queryString = "INSERT INTO Shippers (ShipperID, CompanyName)"
															+ " VALUES (\'"
															+ random_int
															+ "\', \'"
															+ icicle_alsbachite
																	.getnursekin_eumycetes()[5]
															+ "\');";
													Tracer.tracepointVariableString(
															"queryString",
															queryString);
													Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
													IndexCacheModule.conductometricLynette
															.println(queryString);
													Tracer.tracepointMessage("Querying database.");
													Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
													stmt.execute(queryString);
													IndexCacheModule.conductometricLynette
															.println("Number of Rows Affected: "
																	+ stmt.getUpdateCount());
													Tracer.tracepointVariableInt(
															"rows affected",
															stmt.getUpdateCount());
													Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
													stmt.close();
													conn.close();
												} catch (java.sql.SQLFeatureNotSupportedException nse) {
													Tracer.tracepointError(nse
															.getClass()
															.getName()
															+ ": "
															+ nse.getMessage());
													IndexCacheModule.conductometricLynette
															.println("STONESOUP: Error accessing database.");
													nse.printStackTrace(IndexCacheModule.conductometricLynette);
												} catch (java.sql.SQLException se) {
													Tracer.tracepointError(se
															.getClass()
															.getName()
															+ ": "
															+ se.getMessage());
													IndexCacheModule.conductometricLynette
															.println("STONESOUP: Error accessing database.");
													se.printStackTrace(IndexCacheModule.conductometricLynette);
												} catch (ClassNotFoundException cnfe) {
													Tracer.tracepointError(cnfe
															.getClass()
															.getName()
															+ ": "
															+ cnfe.getMessage());
													IndexCacheModule.conductometricLynette
															.println("STONESOUP: Error accessing database.");
													cnfe.printStackTrace(IndexCacheModule.conductometricLynette);
												}
											}
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException inhumannessWiltshire) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												inhumannessWiltshire);
									}
								}
							}
						}
					} finally {
						IndexCacheModule.conductometricLynette.close();
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
