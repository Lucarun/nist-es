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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractDistributor implements Distributor {

    static PrintStream glutealUnthievish = null;

	public void veldmanCarlings(int undine_underpopulation,
			String stuntednessWoorali) {
		undine_underpopulation--;
		if (undine_underpopulation > 0) {
			megalopsiaJacalteca(undine_underpopulation, stuntednessWoorali);
		}
	}

	public void megalopsiaJacalteca(int mariposan_anamnia,
			String stuntednessWoorali) {
		veldmanCarlings(mariposan_anamnia, stuntednessWoorali);
		Tracer.tracepointWeaknessStart("CWE663", "A",
				"Use of a Non-reentrant Function in a Concurrent Context");
		String stonesoup_substrings[] = stuntednessWoorali.split("\\s", 2);
		int stonesoup_qsize = 0;
		if (stonesoup_substrings.length == 2) {
			try {
				stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				AbstractDistributor.glutealUnthievish
						.println("NumberFormatException");
			}
			Tracer.tracepointVariableString("stonesoup_value",
					stuntednessWoorali);
			Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
			Tracer.tracepointVariableString("stonesoup_threadInput",
					stonesoup_substrings[1]);
			if (stonesoup_qsize < 0) {
				stonesoup_qsize = 0;
				AbstractDistributor.glutealUnthievish
						.println("Qsize should be >=0, setting it to 0.");
			}
			Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
			Tracer.tracepointMessage("Creating threads");
			Thread stonesoup_thread1 = new Thread(new replaceSymbols(
					stonesoup_qsize, AbstractDistributor.glutealUnthievish));
			Thread stonesoup_thread2 = new Thread(new toCaps(stonesoup_qsize,
					AbstractDistributor.glutealUnthievish));
			stonesoup_threadInput = new StringBuilder()
					.append(stonesoup_substrings[1]);
			Tracer.tracepointMessage("Spawning threads.");
			AbstractDistributor.glutealUnthievish
					.println("Info: Spawning thread 1.");
			stonesoup_thread1.start();
			AbstractDistributor.glutealUnthievish
					.println("Info: Spawning thread 2.");
			stonesoup_thread2.start();
			try {
				Tracer.tracepointMessage("Joining threads");
				Tracer.tracepointMessage("Joining thread-01");
				stonesoup_thread1.join();
				Tracer.tracepointMessage("Joined thread-01");
				Tracer.tracepointMessage("Joining thread-02");
				stonesoup_thread2.join();
				Tracer.tracepointMessage("Joined thread-02");
				Tracer.tracepointMessage("Joined threads");
			} catch (InterruptedException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				AbstractDistributor.glutealUnthievish.println("Interrupted");
			}
			AbstractDistributor.glutealUnthievish
					.println("Info: Threads ended");
		}
		Tracer.tracepointWeaknessEnd();
	}

	private static final java.util.concurrent.atomic.AtomicBoolean spirivalveDynamometer = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (spirivalveDynamometer.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpq8FUVn_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File surgerizeResupposition = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!surgerizeResupposition.getParentFile().exists()
					&& !surgerizeResupposition.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.glutealUnthievish = new PrintStream(
							new FileOutputStream(surgerizeResupposition, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException proneurAwedness) {
					System.err.printf("Failed to open log file.  %s\n",
							proneurAwedness.getMessage());
					AbstractDistributor.glutealUnthievish = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							proneurAwedness);
				} catch (FileNotFoundException cassiaPseudomalachite) {
					System.err.printf("Failed to open log file.  %s\n",
							cassiaPseudomalachite.getMessage());
					AbstractDistributor.glutealUnthievish = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							cassiaPseudomalachite);
				}
				if (AbstractDistributor.glutealUnthievish != null) {
					try {
						String zirconate_ewry = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (zirconate_ewry == null
								|| !zirconate_ewry.equals("1")) {
							String romancy_nonoccupation = System
									.getenv("HYPOCHROMIA_PALATALIZATION");
							if (null != romancy_nonoccupation) {
								File hegelizer_hulver = new File(
										romancy_nonoccupation);
								if (hegelizer_hulver.exists()
										&& !hegelizer_hulver.isDirectory()) {
									try {
										String monogamousness_sapient;
										Scanner sleighing_bedravel = new Scanner(
												hegelizer_hulver, "UTF-8")
												.useDelimiter("\\A");
										if (sleighing_bedravel.hasNext())
											monogamousness_sapient = sleighing_bedravel
													.next();
										else
											monogamousness_sapient = "";
										if (null != monogamousness_sapient) {
											ethnopsychicTetraedrum(3, null,
													null, null,
													monogamousness_sapient,
													null, null);
										}
									} catch (FileNotFoundException flumerinBartram) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												flumerinBartram);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.glutealUnthievish.close();
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

	public void ethnopsychicTetraedrum(int nutcrackerySignaletic,
			String... poaceousTrawl) {
		String stuntednessWoorali = null;
		int outromanceThrowing = 0;
		for (outromanceThrowing = 0; outromanceThrowing < poaceousTrawl.length; outromanceThrowing++) {
			if (outromanceThrowing == nutcrackerySignaletic)
				stuntednessWoorali = poaceousTrawl[outromanceThrowing];
		}
		int untimbered_lampwick = 2;
		veldmanCarlings(untimbered_lampwick, stuntednessWoorali);
	}

	private static ReentrantLock lock = new ReentrantLock();
	private static StringBuilder stonesoup_threadInput;
	static volatile int j;

	public static void arrFunc(int size, String tempfile, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpq8FUVn_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
				"arrFunc");
		int[] sortMe = new int[size];
		j = 0;
		Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
		for (int i = 0; i < stonesoup_threadInput.length(); i++, j++) {
			stonesoup_threadInput.setCharAt(j, '\0');
			output.format("TID: %d I: %d J: %d\n", Thread.currentThread()
					.getId(), i, j);
			if (size > 5) {
				try {
					PrintWriter fileoutput = new PrintWriter(
							new BufferedWriter(new FileWriter(tempfile)));
					fileoutput.println("Iteration: " + i);
					fileoutput.close();
				} catch (IOException e) {
					Tracer.tracepointError("IOException");
				}
				for (int k = 0; k < size; k++) {
					sortMe[k] = size - k;
				}
				Arrays.sort(sortMe);
			}
		}
		Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
	}

	public static class replaceSymbols implements Runnable {
		private int size = 0;
		private int threadTiming = 500000;
		PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpq8FUVn_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"replaceSymbols.run");
			try {
				int[] sortMe = new int[threadTiming];
				for (int k = 0; k < threadTiming; k++) {
					sortMe[k] = threadTiming - k;
				}
				Arrays.sort(sortMe);
				Tracer.tracepointMessage("replaceSymbols: after qsort");
				lock.lock();
				char val;
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					val = stonesoup_threadInput.charAt(i);
					if (((val >= '!' && val <= '/')
							|| (val >= ':' && val <= '@')
							|| (val >= '[' && val <= '`') || (val >= '{' && val <= '~'))
							&& (val != '@' && val != '.')) {
						stonesoup_threadInput.setCharAt(i, '_');
					}
				}
				lock.unlock();
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE (1)");
				arrFunc(size, "/opt/stonesoup/workspace/testData/replace.txt",
						output);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER (1)");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public replaceSymbols(int size, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpq8FUVn_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"replaceSymbols.ctor");
			this.size = size;
			this.output = output;
		}
	}

	public static class toCaps implements Runnable {
		public int size = 0;
		PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpq8FUVn_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"toCaps.run");
			try {
				lock.lock();
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					if (stonesoup_threadInput.charAt(i) >= 'a'
							|| stonesoup_threadInput.charAt(i) <= 'z') {
						stonesoup_threadInput
								.setCharAt(
										i,
										(char) (stonesoup_threadInput.charAt(i) - ('a' - 'A')));
					}
				}
				lock.unlock();
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE (2)");
				arrFunc(size, "/opt/stonesoup/workspace/testData/toCaps.txt",
						output);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER (2)");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public toCaps(int size, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpq8FUVn_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"toCaps.ctor");
			this.size = size;
			this.output = output;
		}
	}

}
