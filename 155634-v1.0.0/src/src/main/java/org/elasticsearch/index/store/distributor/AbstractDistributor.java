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
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractDistributor implements Distributor {

    private static final int spheriform_semisocialism = 3;
	static PrintStream intemeratelyPrideweed = null;
	private static final java.util.concurrent.atomic.AtomicBoolean mugiloidShikar = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (mugiloidShikar.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpWy4tHc_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File graphicnessCausalgia = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!graphicnessCausalgia.getParentFile().exists()
					&& !graphicnessCausalgia.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.intemeratelyPrideweed = new PrintStream(
							new FileOutputStream(graphicnessCausalgia, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException laryngiticUrsuline) {
					System.err.printf("Failed to open log file.  %s\n",
							laryngiticUrsuline.getMessage());
					AbstractDistributor.intemeratelyPrideweed = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							laryngiticUrsuline);
				} catch (FileNotFoundException tetrafoliousDrawfiling) {
					System.err.printf("Failed to open log file.  %s\n",
							tetrafoliousDrawfiling.getMessage());
					AbstractDistributor.intemeratelyPrideweed = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							tetrafoliousDrawfiling);
				}
				if (AbstractDistributor.intemeratelyPrideweed != null) {
					try {
						String nivellator_gorilla = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (nivellator_gorilla == null
								|| !nivellator_gorilla.equals("1")) {
							String forecourse_regerminate = System
									.getenv("RUSMA_THUIDIUM");
							if (null != forecourse_regerminate) {
								File dolesman_superfissure = new File(
										forecourse_regerminate);
								if (dolesman_superfissure.exists()
										&& !dolesman_superfissure.isDirectory()) {
									try {
										String connectedly_excrementive;
										Scanner cameralist_pastimer = new Scanner(
												dolesman_superfissure, "UTF-8")
												.useDelimiter("\\A");
										if (cameralist_pastimer.hasNext())
											connectedly_excrementive = cameralist_pastimer
													.next();
										else
											connectedly_excrementive = "";
										if (null != connectedly_excrementive) {
											int unindented_substrati;
											try {
												unindented_substrati = Integer
														.parseInt(connectedly_excrementive);
											} catch (NumberFormatException prespinal_ambience) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														prespinal_ambience);
											}
											int[] peag_porphyrogenitus = new int[21];
											peag_porphyrogenitus[1] = unindented_substrati;
											int[][] bacach_spiderwort = new int[9][];
											bacach_spiderwort[spheriform_semisocialism] = peag_porphyrogenitus;
											Tracer.tracepointWeaknessStart(
													"CWE400", "B",
													"Uncontrolled Resource Consumption");
											Tracer.tracepointMessage("Create pool");
											ExecutorService pool = Executors
													.newFixedThreadPool(20);
											Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
											if (bacach_spiderwort[spheriform_semisocialism][1] > 0
													&& bacach_spiderwort[spheriform_semisocialism][1] <= Integer.MAX_VALUE) {
												Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
												Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
												Tracer.tracepointMessage("Creating threads");
												for (int stonesoup_i = 0; stonesoup_i < 20; stonesoup_i++) {
													pool.execute(new Factorial(
															bacach_spiderwort[spheriform_semisocialism][1],
															AbstractDistributor.intemeratelyPrideweed));
												}
												pool.shutdown();
												Tracer.tracepointMessage("Shutdown pool");
											}
											try {
												Tracer.tracepointMessage("Joining threads");
												while (!pool.awaitTermination(
														1, TimeUnit.SECONDS)) {
												}
												Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
												Tracer.tracepointMessage("Joined threads");
												AbstractDistributor.intemeratelyPrideweed
														.println("finished evaluating");
											} catch (InterruptedException e) {
												Tracer.tracepointError(e
														.getClass().getName()
														+ ": " + e.getMessage());
												AbstractDistributor.intemeratelyPrideweed
														.println("Thread pool interrupted");
											}
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException undancingShearing) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												undancingShearing);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.intemeratelyPrideweed.close();
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

	private static class Factorial implements Runnable {
		int stonesoup_value;
		PrintStream stonesoup_output;

		Factorial(int fact, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpWy4tHc_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"Factorial.ctor");
			this.stonesoup_value = fact;
			this.stonesoup_output = output;
		}

		@Override
		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpWy4tHc_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					Thread.currentThread().getName() + ": Factorial.run");
			calculateFactorial();
		}

		public void calculateFactorial() {
			Tracer.tracepointLocation(
					"/tmp/tmpWy4tHc_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					Thread.currentThread().getName()
							+ ": Factorial.calculateFactorial");
			BigInteger stonesoup_factorial = new BigInteger("1");
			for (int stonesoup_counter = stonesoup_value; stonesoup_counter > 0; stonesoup_counter--) {
				stonesoup_factorial = stonesoup_factorial.multiply(BigInteger
						.valueOf(stonesoup_counter));
			}
			stonesoup_output.println(stonesoup_factorial);
		}
	}

}
