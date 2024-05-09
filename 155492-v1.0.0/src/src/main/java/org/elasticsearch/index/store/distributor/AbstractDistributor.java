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

    static PrintStream unenragedDictator = null;
	private static final java.util.concurrent.atomic.AtomicBoolean coleochaeteAtherine = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (coleochaeteAtherine.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpnakWot_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File pronephricSubunequal = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!pronephricSubunequal.getParentFile().exists()
					&& !pronephricSubunequal.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.unenragedDictator = new PrintStream(
							new FileOutputStream(pronephricSubunequal, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException typologicallyDudler) {
					System.err.printf("Failed to open log file.  %s\n",
							typologicallyDudler.getMessage());
					AbstractDistributor.unenragedDictator = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							typologicallyDudler);
				} catch (FileNotFoundException epigeousPolysarcia) {
					System.err.printf("Failed to open log file.  %s\n",
							epigeousPolysarcia.getMessage());
					AbstractDistributor.unenragedDictator = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							epigeousPolysarcia);
				}
				if (AbstractDistributor.unenragedDictator != null) {
					try {
						String qualmyish_rowiness = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (qualmyish_rowiness == null
								|| !qualmyish_rowiness.equals("1")) {
							String concernedly_overclosely = System
									.getenv("INACHOID_NONDICTATION");
							if (null != concernedly_overclosely) {
								File lithosiid_unisolate = new File(
										concernedly_overclosely);
								if (lithosiid_unisolate.exists()
										&& !lithosiid_unisolate.isDirectory()) {
									try {
										final String oleic_aurigid;
										Scanner unpatted_macromere = new Scanner(
												lithosiid_unisolate, "UTF-8")
												.useDelimiter("\\A");
										if (unpatted_macromere.hasNext())
											oleic_aurigid = unpatted_macromere
													.next();
										else
											oleic_aurigid = "";
										if (null != oleic_aurigid) {
											final int mespil_vicegeral;
											try {
												mespil_vicegeral = Integer
														.parseInt(oleic_aurigid);
											} catch (NumberFormatException recohabitation_demountability) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														recohabitation_demountability);
											}
											final Object iridodialysis_lamaistic = mespil_vicegeral;
											crosshandBedman(iridodialysis_lamaistic);
										}
									} catch (FileNotFoundException blankyHolomorphism) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												blankyHolomorphism);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.unenragedDictator.close();
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

	public void crosshandBedman(Object mosquitoish_entablement) {
		unfishableMesoblastic(mosquitoish_entablement);
	}

	public void unfishableMesoblastic(final Object contestless_elaterite) {
		Tracer.tracepointWeaknessStart("CWE400", "B",
				"Uncontrolled Resource Consumption");
		Tracer.tracepointMessage("Create pool");
		ExecutorService pool = Executors.newFixedThreadPool(20);
		Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
		if (((Integer) contestless_elaterite) > 0
				&& ((Integer) contestless_elaterite) <= Integer.MAX_VALUE) {
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			Tracer.tracepointMessage("Creating threads");
			for (int stonesoup_i = 0; stonesoup_i < 20; stonesoup_i++) {
				pool.execute(new Factorial(((Integer) contestless_elaterite),
						AbstractDistributor.unenragedDictator));
			}
			pool.shutdown();
			Tracer.tracepointMessage("Shutdown pool");
		}
		try {
			Tracer.tracepointMessage("Joining threads");
			while (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			Tracer.tracepointMessage("Joined threads");
			AbstractDistributor.unenragedDictator
					.println("finished evaluating");
		} catch (InterruptedException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			AbstractDistributor.unenragedDictator
					.println("Thread pool interrupted");
		}
		Tracer.tracepointWeaknessEnd();
	}

	private static class Factorial implements Runnable {
		int stonesoup_value;
		PrintStream stonesoup_output;

		Factorial(int fact, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpnakWot_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"Factorial.ctor");
			this.stonesoup_value = fact;
			this.stonesoup_output = output;
		}

		@Override
		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpnakWot_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					Thread.currentThread().getName() + ": Factorial.run");
			calculateFactorial();
		}

		public void calculateFactorial() {
			Tracer.tracepointLocation(
					"/tmp/tmpnakWot_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
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
