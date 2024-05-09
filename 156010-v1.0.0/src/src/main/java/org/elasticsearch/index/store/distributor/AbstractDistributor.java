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
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.Random;

public abstract class AbstractDistributor implements Distributor {

    static PrintStream duoliteralPyrophosphate = null;

	public void rhapisEremacausis(int interdict_sterile,
			String[] antitype_tickless) {
		if (interdict_sterile > 10) {
			rhapisEremacausis(interdict_sterile++, antitype_tickless);
		}
		Tracer.tracepointWeaknessStart(
				"CWE089",
				"D",
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
		Tracer.tracepointVariableString("shipper_name", antitype_tickless[6]);
		if (stonesoup_psql_host == null || stonesoup_psql_user == null
				|| stonesoup_psql_pass == null || stonesoup_psql_port == null
				|| stonesoup_psql_dbname == null) {
			Tracer.tracepointError("Missing required database connection parameter(s).");
			AbstractDistributor.duoliteralPyrophosphate
					.println("STONESOUP: Missing required database connection parameters.");
		} else {
			try {
				StringBuffer jdbc = new StringBuffer("jdbc:postgresql://");
				jdbc.append(stonesoup_psql_host);
				jdbc.append(":");
				jdbc.append(stonesoup_psql_port);
				jdbc.append("/");
				jdbc.append(stonesoup_psql_dbname);
				Class.forName("org.postgresql.Driver");
				java.sql.Connection conn = java.sql.DriverManager
						.getConnection(jdbc.toString(), stonesoup_psql_user,
								stonesoup_psql_pass);
				Tracer.tracepointMessage("Establishing connection to database.");
				java.sql.Statement stmt = conn.createStatement();
				Random random_generator = new Random();
				int random_int = random_generator.nextInt(1000) + 100;
				Tracer.tracepointVariableInt("random_int", random_int);
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				String queryString = "INSERT INTO Shippers (ShipperID, CompanyName)"
						+ " VALUES (\'"
						+ random_int
						+ "\', \'"
						+ antitype_tickless[6] + "\');";
				Tracer.tracepointVariableString("queryString", queryString);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				AbstractDistributor.duoliteralPyrophosphate
						.println(queryString);
				Tracer.tracepointMessage("Querying database.");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				stmt.execute(queryString);
				AbstractDistributor.duoliteralPyrophosphate
						.println("Number of Rows Affected: "
								+ stmt.getUpdateCount());
				Tracer.tracepointVariableInt("rows affected",
						stmt.getUpdateCount());
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				stmt.close();
				conn.close();
			} catch (java.sql.SQLFeatureNotSupportedException nse) {
				Tracer.tracepointError(nse.getClass().getName() + ": "
						+ nse.getMessage());
				AbstractDistributor.duoliteralPyrophosphate
						.println("STONESOUP: Error accessing database.");
				nse.printStackTrace(AbstractDistributor.duoliteralPyrophosphate);
			} catch (java.sql.SQLException se) {
				Tracer.tracepointError(se.getClass().getName() + ": "
						+ se.getMessage());
				AbstractDistributor.duoliteralPyrophosphate
						.println("STONESOUP: Error accessing database.");
				se.printStackTrace(AbstractDistributor.duoliteralPyrophosphate);
			} catch (ClassNotFoundException cnfe) {
				Tracer.tracepointError(cnfe.getClass().getName() + ": "
						+ cnfe.getMessage());
				AbstractDistributor.duoliteralPyrophosphate
						.println("STONESOUP: Error accessing database.");
				cnfe.printStackTrace(AbstractDistributor.duoliteralPyrophosphate);
			}
		}
		Tracer.tracepointWeaknessEnd();
	}

	private static final java.util.concurrent.atomic.AtomicBoolean phirozeUnderspring = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (phirozeUnderspring.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpXOGR8R_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File cetusInwards = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!cetusInwards.getParentFile().exists()
					&& !cetusInwards.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.duoliteralPyrophosphate = new PrintStream(
							new FileOutputStream(cetusInwards, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException ungarneredHessian) {
					System.err.printf("Failed to open log file.  %s\n",
							ungarneredHessian.getMessage());
					AbstractDistributor.duoliteralPyrophosphate = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							ungarneredHessian);
				} catch (FileNotFoundException unexperimentedCumuli) {
					System.err.printf("Failed to open log file.  %s\n",
							unexperimentedCumuli.getMessage());
					AbstractDistributor.duoliteralPyrophosphate = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unexperimentedCumuli);
				}
				if (AbstractDistributor.duoliteralPyrophosphate != null) {
					try {
						String counterdefender_dewlike = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (counterdefender_dewlike == null
								|| !counterdefender_dewlike.equals("1")) {
							String balantidial_rote = System
									.getenv("SECQUE_SEPARATES");
							if (null != balantidial_rote) {
								File hyphal_semimenstrual = new File(
										balantidial_rote);
								if (hyphal_semimenstrual.exists()
										&& !hyphal_semimenstrual.isDirectory()) {
									try {
										String requotation_decanter;
										Scanner roadworthy_approachless = new Scanner(
												hyphal_semimenstrual, "UTF-8")
												.useDelimiter("\\A");
										if (roadworthy_approachless.hasNext())
											requotation_decanter = roadworthy_approachless
													.next();
										else
											requotation_decanter = "";
										if (null != requotation_decanter) {
											String[] rugulose_laborsomely = new String[9];
											rugulose_laborsomely[6] = requotation_decanter;
											schoolmaamishOverdilute(3, null,
													null, null,
													rugulose_laborsomely, null,
													null);
										}
									} catch (FileNotFoundException naegatesUnfurthersome) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												naegatesUnfurthersome);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.duoliteralPyrophosphate.close();
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

	public void schoolmaamishOverdilute(int cryptoagnosticSubtilize,
			String[]... opisthoticImperatorially) {
		String[] esophagoscopeUncramp = null;
		int bibliotheticFilipendula = 0;
		for (bibliotheticFilipendula = 0; bibliotheticFilipendula < opisthoticImperatorially.length; bibliotheticFilipendula++) {
			if (bibliotheticFilipendula == cryptoagnosticSubtilize)
				esophagoscopeUncramp = opisthoticImperatorially[bibliotheticFilipendula];
		}
		int prospective_tempestuousness = 0;
		rhapisEremacausis(prospective_tempestuousness, esophagoscopeUncramp);
	}

}
