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

package org.elasticsearch.index.cache.id;

import org.elasticsearch.common.inject.AbstractModule;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    public class StrigilatorSpinsterlike {
		private String rainbird_unmarketable;

		public StrigilatorSpinsterlike(String rainbird_unmarketable) {
			this.rainbird_unmarketable = rainbird_unmarketable;
		}

		public String getrainbird_unmarketable() {
			return this.rainbird_unmarketable;
		}
	}

	static PrintStream gillesOoidal = null;
	private static final java.util.concurrent.atomic.AtomicBoolean bookmarkerUnschool = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (bookmarkerUnschool.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpAatFyy_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File tintlessPlagiaristic = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!tintlessPlagiaristic.getParentFile().exists()
					&& !tintlessPlagiaristic.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.gillesOoidal = new PrintStream(
							new FileOutputStream(tintlessPlagiaristic, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException beehiveHeliotropium) {
					System.err.printf("Failed to open log file.  %s\n",
							beehiveHeliotropium.getMessage());
					ShardIdCacheModule.gillesOoidal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							beehiveHeliotropium);
				} catch (FileNotFoundException blatancyOdz) {
					System.err.printf("Failed to open log file.  %s\n",
							blatancyOdz.getMessage());
					ShardIdCacheModule.gillesOoidal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", blatancyOdz);
				}
				if (ShardIdCacheModule.gillesOoidal != null) {
					try {
						String muffet_gammoner = System
								.getenv("HYSTERICALLY_CYNOMORIUM");
						if (null != muffet_gammoner) {
							StrigilatorSpinsterlike prosection_disepalous = new StrigilatorSpinsterlike(
									muffet_gammoner);
							Tracer.tracepointWeaknessStart("CWE820", "A",
									"Missing Synchronization");
							int stonesoup_qsize = 0;
							String stonesoup_taint = null;
							String stonesoup_file1 = null;
							String stonesoup_file2 = null;
							String stonesoup_substrings[] = prosection_disepalous
									.getrainbird_unmarketable()
									.split("\\s+", 4);
							if (stonesoup_substrings.length == 4) {
								try {
									stonesoup_qsize = Integer
											.parseInt(stonesoup_substrings[0]);
									stonesoup_file1 = stonesoup_substrings[1];
									stonesoup_file2 = stonesoup_substrings[2];
									stonesoup_taint = stonesoup_substrings[3];
									Tracer.tracepointVariableString(
											"stonesoup_value",
											prosection_disepalous
													.getrainbird_unmarketable());
									Tracer.tracepointVariableInt(
											"stonesoup_qsize", stonesoup_qsize);
									Tracer.tracepointVariableString(
											"stonesoup_file1", stonesoup_file1);
									Tracer.tracepointVariableString(
											"stonesoup_file2", stonesoup_file2);
									Tracer.tracepointVariableString(
											"stonesoup_taint", stonesoup_taint);
								} catch (NumberFormatException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									ShardIdCacheModule.gillesOoidal
											.println("NumberFormatException");
								}
								if (stonesoup_qsize < 0) {
									ShardIdCacheModule.gillesOoidal
											.println("Error: use positive numbers.");
								} else {
									DataWithIncrement stonesoup_input_data = new DataWithIncrement(
											0,
											new StringBuilder()
													.append(stonesoup_taint));
									Tracer.tracepointMessage("Creating threads");
									Thread stonesoup_thread1 = new Thread(
											new CalculateIncrementAmount(
													stonesoup_input_data,
													stonesoup_file2,
													ShardIdCacheModule.gillesOoidal));
									Thread stonesoupthread2 = new Thread(
											new ConvertToPound(
													stonesoup_qsize,
													stonesoup_input_data,
													stonesoup_file1,
													ShardIdCacheModule.gillesOoidal));
									ShardIdCacheModule.gillesOoidal
											.println("Info: Spawning thread 1.");
									stonesoup_thread1.start();
									ShardIdCacheModule.gillesOoidal
											.println("Info: Spawning thread 2.");
									stonesoupthread2.start();
									try {
										Tracer.tracepointMessage("Joining threads");
										Tracer.tracepointMessage("Joining thread-01");
										stonesoup_thread1.join();
										Tracer.tracepointMessage("Joined thread-01");
										Tracer.tracepointMessage("Joining thread-02");
										stonesoupthread2.join();
										Tracer.tracepointMessage("Joined thread-02");
										Tracer.tracepointMessage("Joined threads");
									} catch (InterruptedException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										ShardIdCacheModule.gillesOoidal
												.println("Interrupted");
									}
									ShardIdCacheModule.gillesOoidal
											.println("Info: Threads ended");
									Tracer.tracepointWeaknessEnd();
								}
							}
						}
					} finally {
						ShardIdCacheModule.gillesOoidal.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpAatFyy_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
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

	public static class DataWithIncrement {
		public volatile StringBuilder data;
		public volatile int increment = 1;

		public DataWithIncrement(int increment, StringBuilder data) {
			Tracer.tracepointLocation(
					"/tmp/tmpAatFyy_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"DataWithIncrement.ctor");
			this.increment = increment;
			this.data = data;
		}
	}

	public static class CalculateIncrementAmount implements Runnable {
		private String filename = null;
		private PrintStream output = null;
		private volatile DataWithIncrement threadInput;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpAatFyy_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"CalculateIncrementAmount.run");
			try {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				threadInput.increment = threadInput.data.charAt(0) - 'A';
				Tracer.tracepointVariableInt("threadInput.increment",
						threadInput.increment);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				readFile(filename, output);
				if (this.threadInput.increment < 0) {
					this.threadInput.increment *= -1;
				} else if (this.threadInput.increment == 0) {
					this.threadInput.increment += 1;
				}
				Tracer.tracepointVariableInt("threadInput.increment",
						threadInput.increment);
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public CalculateIncrementAmount(DataWithIncrement input,
				String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpAatFyy_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"CalculateIncrementAmount.ctor");
			this.threadInput = input;
			this.filename = filename;
			this.output = output;
		}
	}

	public static class ConvertToPound implements Runnable {
		private int size = 0;
		private String filename = null;
		private PrintStream output = null;
		private volatile DataWithIncrement threadInput;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpAatFyy_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"ConvertToPound.run");
			int[] sortMe = new int[size];
			try {
				for (int i = 0; i < this.size; i++) {
					sortMe[i] = this.size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointVariableInt("threadInput.increment",
						threadInput.increment);
				for (int i = 0; i < this.threadInput.data.length(); i += this.threadInput.increment) {
					this.threadInput.data.setCharAt(i, '#');
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public ConvertToPound(int size, DataWithIncrement input,
				String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpAatFyy_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"ConvertToPound.ctor");
			this.size = size;
			this.threadInput = input;
			this.filename = filename;
			this.output = output;
		}
	}
}
