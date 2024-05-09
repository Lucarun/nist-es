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
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractDistributor implements Distributor {

    static PrintStream underdoseUnconsumable = null;
	private static final java.util.concurrent.atomic.AtomicBoolean chimerUnpacified = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (chimerUnpacified.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpW8OfaO_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File encyclopedialLoessoid = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!encyclopedialLoessoid.getParentFile().exists()
					&& !encyclopedialLoessoid.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.underdoseUnconsumable = new PrintStream(
							new FileOutputStream(encyclopedialLoessoid, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException pompelmousArduously) {
					System.err.printf("Failed to open log file.  %s\n",
							pompelmousArduously.getMessage());
					AbstractDistributor.underdoseUnconsumable = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							pompelmousArduously);
				} catch (FileNotFoundException lawfulnessPedalian) {
					System.err.printf("Failed to open log file.  %s\n",
							lawfulnessPedalian.getMessage());
					AbstractDistributor.underdoseUnconsumable = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							lawfulnessPedalian);
				}
				if (AbstractDistributor.underdoseUnconsumable != null) {
					try {
						String supergratify_orthologer = System
								.getenv("REOBTAIN_RECREATIVENESS");
						if (null != supergratify_orthologer) {
							Object strany_xerography = supergratify_orthologer;
							steamerloadClarkeite(3, null, null, null,
									strany_xerography, null, null);
						}
					} finally {
						AbstractDistributor.underdoseUnconsumable.close();
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

	public void steamerloadClarkeite(int bepewedApophysary,
			Object... rearlingPaneulogism) {
		Object eruciformHaab = null;
		int piroplasmosisSubsensuous = 0;
		for (piroplasmosisSubsensuous = 0; piroplasmosisSubsensuous < rearlingPaneulogism.length; piroplasmosisSubsensuous++) {
			if (piroplasmosisSubsensuous == bepewedApophysary)
				eruciformHaab = rearlingPaneulogism[piroplasmosisSubsensuous];
		}
		unreconstructedMisogynistical(eruciformHaab);
	}

	public static void unreconstructedMisogynistical(
			Object bismuthousConvenership) {
		Tracer.tracepointWeaknessStart("CWE821", "A",
				"Incorrect Synchronization");
		Stonesoup_Int stonesoup_dev_amount = new Stonesoup_Int(1);
		int stonesoup_qsize = 0;
		String stonesoup_taint = null;
		String stonesoup_file1 = null;
		String stonesoup_file2 = null;
		String stonesoup_substrings[] = ((String) bismuthousConvenership)
				.split("\\s+", 4);
		if (stonesoup_substrings.length == 4) {
			try {
				stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				stonesoup_file1 = stonesoup_substrings[1];
				stonesoup_file2 = stonesoup_substrings[2];
				stonesoup_taint = stonesoup_substrings[3];
				Tracer.tracepointVariableString("stonesoup_value",
						((String) bismuthousConvenership));
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointVariableString("stonesoup_file1",
						stonesoup_file1);
				Tracer.tracepointVariableString("stonesoup_file2",
						stonesoup_file2);
				Tracer.tracepointVariableString("stonesoup_taint",
						stonesoup_taint);
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				AbstractDistributor.underdoseUnconsumable
						.println("NumberFormatException");
			}
			if (stonesoup_qsize < 0) {
				AbstractDistributor.underdoseUnconsumable
						.println("Error: use positive numbers.");
			} else {
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread2 = new Thread(new devChar(
						stonesoup_qsize, stonesoup_dev_amount, stonesoup_file1,
						AbstractDistributor.underdoseUnconsumable));
				Thread stonesoup_thread1 = new Thread(new calcDevAmount(
						stonesoup_dev_amount, stonesoup_file2,
						AbstractDistributor.underdoseUnconsumable));
				stonesoup_threadInput = new StringBuilder()
						.append(stonesoup_taint);
				AbstractDistributor.underdoseUnconsumable
						.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				AbstractDistributor.underdoseUnconsumable
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
					AbstractDistributor.underdoseUnconsumable
							.println("Interrupted");
				}
				AbstractDistributor.underdoseUnconsumable
						.println("Info: Threads ended");
				Tracer.tracepointWeaknessEnd();
			}
		}
	}

	public static void unreconstructedMisogynistical() {
		unreconstructedMisogynistical(null);
	}

	private static ReentrantLock lock = new ReentrantLock();
	private static ReentrantLock lock2 = new ReentrantLock();
	private static StringBuilder stonesoup_threadInput;

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpW8OfaO_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
				"readFile");
		String str;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((str = reader.readLine()) != null) {
				output.println(str);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		} catch (IOException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		}
	}

	public static class Stonesoup_Int {
		int i;

		public Stonesoup_Int(int i) {
			this.i = i;
		}

		public int getVal() {
			return i;
		}

		public void setVal(int i) {
			this.i = i;
		}
	}

	public static class calcDevAmount implements Runnable {
		private Stonesoup_Int dev_amount;
		private String filename = null;
		private PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpW8OfaO_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"calcDevAmount.run");
			try {
				lock.lock();
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				dev_amount.setVal(stonesoup_threadInput.charAt(0) - 'A');
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				readFile(filename, output);
				if (dev_amount.getVal() < 0) {
					dev_amount.setVal(dev_amount.getVal() * -1);
				}
				if (dev_amount.getVal() == 0) {
					dev_amount.setVal(dev_amount.getVal() + 1);
				}
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				lock.unlock();
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public calcDevAmount(Stonesoup_Int dev_amount, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpW8OfaO_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"calcDevAmount.ctor");
			this.dev_amount = dev_amount;
			this.filename = filename;
			this.output = output;
		}
	}

	public static class devChar implements Runnable {
		private int size = 0;
		private Stonesoup_Int dev_amount;
		private String filename = null;
		private PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpW8OfaO_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"devChar.run");
			try {
				lock2.lock();
				int[] sortMe = new int[size];
				for (int i = 0; i < size; i++) {
					sortMe[i] = size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					stonesoup_threadInput
							.setCharAt(i, (char) (stonesoup_threadInput
									.charAt(i) / dev_amount.getVal()));
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				lock2.unlock();
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public devChar(int size, Stonesoup_Int dev_amount, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpW8OfaO_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"devChar.ctor");
			this.size = size;
			this.dev_amount = dev_amount;
			this.filename = filename;
			this.output = output;
		}
	}

}