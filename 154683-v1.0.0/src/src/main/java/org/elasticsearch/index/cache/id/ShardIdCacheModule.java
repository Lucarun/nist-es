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
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    public static interface ILaurentianAntebrachium {
		public void cupmakingHarmless(String recentralize_vetoism);
	}

	public static class SupracorallineResiner implements
			ILaurentianAntebrachium {
		@Override
		public void cupmakingHarmless(String recentralize_vetoism) {
			Tracer.tracepointWeaknessStart("CWE543", "A",
					"Use of Singleton Pattern Without Synchronization in a Multithreaded Context");
			int stonesoup_qsize = 0;
			int stonesoup_numVal = 0;
			String stonesoup_file1 = null;
			String stonesoup_file2 = null;
			String stonesoup_substrings[] = recentralize_vetoism.split("\\s+",
					4);
			if (stonesoup_substrings.length == 4) {
				try {
					stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
					stonesoup_file1 = stonesoup_substrings[1];
					stonesoup_file2 = stonesoup_substrings[2];
					stonesoup_numVal = Integer
							.parseInt(stonesoup_substrings[3]);
					Tracer.tracepointVariableString("stonesoup_value",
							recentralize_vetoism);
					Tracer.tracepointVariableInt("stonesoup_qsize",
							stonesoup_qsize);
					Tracer.tracepointVariableInt("stonesoup_numVal",
							stonesoup_numVal);
					Tracer.tracepointVariableString("stonesoup_file1",
							stonesoup_file1);
					Tracer.tracepointVariableString("stonesoup_file2",
							stonesoup_file2);
				} catch (NumberFormatException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					ShardIdCacheModule.delousePrototheria
							.println("NumberFormatException");
				}
				if (stonesoup_numVal <= 0 || stonesoup_qsize < 0) {
					ShardIdCacheModule.delousePrototheria
							.println("Error: use positive numbers.");
				} else {
					Tracer.tracepointMessage("Creating threads");
					Thread stonesoup_thread1 = new Thread(new logData(
							stonesoup_qsize, stonesoup_numVal, stonesoup_file1,
							ShardIdCacheModule.delousePrototheria));
					Thread stonesoup_thread2 = new Thread(new printData(
							stonesoup_file2,
							ShardIdCacheModule.delousePrototheria));
					ShardIdCacheModule.delousePrototheria
							.println("Info: Spawning thread 1.");
					stonesoup_thread1.start();
					ShardIdCacheModule.delousePrototheria
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
						ShardIdCacheModule.delousePrototheria
								.println("Interrupted");
					}
					ShardIdCacheModule.delousePrototheria
							.println("Info: Threads ended");
				}
			}
		}

		public static void readFile(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"readFile");
			String str;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						filename));
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

		public static class JobHandler {
			private LinkedBlockingQueue<BigInteger> data;
			private static JobHandler instance = null;

			private JobHandler() {
				Tracer.tracepointLocation(
						"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						"JobHandler.ctor");
			}

			public static JobHandler getInstance(String filename,
					PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						"JobHandler.getInstance");
				if (instance == null) {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					readFile(filename, output);
					JobHandler temp = new JobHandler();
					temp.initialize();
					instance = temp;
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
					return temp;
				}
				return instance;
			}

			private void initialize(){Tracer.tracepointLocation("/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java","JobHandler.initialize");data=new LinkedBlockingQueue<BigInteger>(30);}			public void enqueue(BigInteger i) {
				Tracer.tracepointLocation(
						"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						"JobHandler.enqueue");
				try {
					data.put(i);
				} catch (InterruptedException e) {
					throw new RuntimeException("Thread interrupted.", e);
				}
			}

			public BigInteger dequeue() {
				Tracer.tracepointLocation(
						"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						"JobHandler.dequeue");
				try {
					return data.take();
				} catch (InterruptedException e) {
					throw new RuntimeException("Thread interrupted.", e);
				}
			}
		}

		public static class printData implements Runnable {
			private String filename;
			private PrintStream output;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						"printData.run");
				JobHandler jobs = JobHandler.getInstance(filename, output);
				BigInteger i;
				Tracer.tracepointBuffer("printData: UID of JobHandler",
						Integer.toHexString(System.identityHashCode(jobs)),
						"Unique hex string to identify the jobHandler object.");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				while ((i = jobs.dequeue()) != BigInteger.valueOf(-1)) {
					output.println(i.toString(10));
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			}

			public printData(String filename, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						"printData.ctor");
				this.filename = filename;
				this.output = output;
			}
		}

		public static class logData implements Runnable {
			private int size;
			private int numVal;
			private String filename;
			private PrintStream output;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						"logData.run");
				int[] sortMe = new int[size];
				for (int i = 0; i < size; i++) {
					sortMe[i] = size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				JobHandler jobs = JobHandler.getInstance(filename, output);
				Tracer.tracepointBuffer("logData: UID of JobHandler",
						Integer.toHexString(System.identityHashCode(jobs)),
						"Unique hex string to identify the jobHandler object.");
				BigInteger a1 = BigInteger.valueOf(0);
				BigInteger a2 = BigInteger.valueOf(0);
				BigInteger c = BigInteger.valueOf(0);
				for (int i = 0; i < numVal; i++) {
					if (i == 0) {
						jobs.enqueue(BigInteger.valueOf(0));
					} else if (i == 1) {
						a1 = BigInteger.valueOf(1);
						jobs.enqueue(BigInteger.valueOf(0));
					} else {
						c = a1.add(a2);
						a2 = a1;
						a1 = c;
						jobs.enqueue(c);
					}
				}
				jobs.enqueue(BigInteger.valueOf(-1));
			}

			public logData(int size, int numVal, String filename,
					PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
						"logData.ctor");
				this.numVal = numVal;
				this.size = size;
				this.filename = filename;
				this.output = output;
			}
		}
	}

	static PrintStream delousePrototheria = null;
	private static final java.util.concurrent.atomic.AtomicBoolean metacentricityBittium = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (metacentricityBittium.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpjeVkao_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File flakilyThoke = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!flakilyThoke.getParentFile().exists()
					&& !flakilyThoke.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.delousePrototheria = new PrintStream(
							new FileOutputStream(flakilyThoke, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException blackballSporadicity) {
					System.err.printf("Failed to open log file.  %s\n",
							blackballSporadicity.getMessage());
					ShardIdCacheModule.delousePrototheria = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							blackballSporadicity);
				} catch (FileNotFoundException monogenicZemstroist) {
					System.err.printf("Failed to open log file.  %s\n",
							monogenicZemstroist.getMessage());
					ShardIdCacheModule.delousePrototheria = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							monogenicZemstroist);
				}
				if (ShardIdCacheModule.delousePrototheria != null) {
					try {
						String insagacity_precative = System
								.getenv("OVALIFORM_MANIFOLDWISE");
						if (null != insagacity_precative) {
							imperscriptibleInfantine(3, null, null, null,
									insagacity_precative, null, null);
						}
					} finally {
						ShardIdCacheModule.delousePrototheria.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	public void imperscriptibleInfantine(int mustelinousEndoplastule,
			String... winnowingSlummocky) {
		String nonuniversityEmotionalism = null;
		int understeerAlkargen = 0;
		for (understeerAlkargen = 0; understeerAlkargen < winnowingSlummocky.length; understeerAlkargen++) {
			if (understeerAlkargen == mustelinousEndoplastule)
				nonuniversityEmotionalism = winnowingSlummocky[understeerAlkargen];
		}
		ILaurentianAntebrachium imprudent_poleman = new SupracorallineResiner();
		imprudent_poleman.cupmakingHarmless(nonuniversityEmotionalism);
	}
}
