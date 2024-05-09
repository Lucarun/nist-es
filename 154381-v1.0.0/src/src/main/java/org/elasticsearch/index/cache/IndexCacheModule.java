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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    static PrintStream ministrableCincinnati = null;
	private static final java.util.concurrent.atomic.AtomicBoolean tartaricUnprecedentedly = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (tartaricUnprecedentedly.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp20bXYq_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			File depreciatorSquattish = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!depreciatorSquattish.getParentFile().exists()
					&& !depreciatorSquattish.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					IndexCacheModule.ministrableCincinnati = new PrintStream(
							new FileOutputStream(depreciatorSquattish, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException drawlingOvangangela) {
					System.err.printf("Failed to open log file.  %s\n",
							drawlingOvangangela.getMessage());
					IndexCacheModule.ministrableCincinnati = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							drawlingOvangangela);
				} catch (FileNotFoundException unreasonedImpunity) {
					System.err.printf("Failed to open log file.  %s\n",
							unreasonedImpunity.getMessage());
					IndexCacheModule.ministrableCincinnati = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unreasonedImpunity);
				}
				if (IndexCacheModule.ministrableCincinnati != null) {
					try {
						final String inigo_podarginae = System
								.getenv("SCOTT_NONROYAL");
						if (null != inigo_podarginae) {
							boolean etamine_peroxidizement = false;
							preobject_phytophysiology: for (int taenidia_multiped = 0; taenidia_multiped < 10; taenidia_multiped++)
								for (int hymenean_asomatophyte = 0; hymenean_asomatophyte < 10; hymenean_asomatophyte++)
									if (taenidia_multiped
											* hymenean_asomatophyte == 63) {
										etamine_peroxidizement = true;
										break preobject_phytophysiology;
									}
							Tracer.tracepointWeaknessStart("CWE663", "A",
									"Use of a Non-reentrant Function in a Concurrent Context");
							String stonesoup_substrings[] = inigo_podarginae
									.split("\\s", 2);
							int stonesoup_qsize = 0;
							if (stonesoup_substrings.length == 2) {
								try {
									stonesoup_qsize = Integer
											.parseInt(stonesoup_substrings[0]);
								} catch (NumberFormatException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									IndexCacheModule.ministrableCincinnati
											.println("NumberFormatException");
								}
								Tracer.tracepointVariableString(
										"stonesoup_value", inigo_podarginae);
								Tracer.tracepointVariableInt("stonesoup_qsize",
										stonesoup_qsize);
								Tracer.tracepointVariableString(
										"stonesoup_threadInput",
										stonesoup_substrings[1]);
								if (stonesoup_qsize < 0) {
									stonesoup_qsize = 0;
									IndexCacheModule.ministrableCincinnati
											.println("Qsize should be >=0, setting it to 0.");
								}
								Tracer.tracepointVariableInt("stonesoup_qsize",
										stonesoup_qsize);
								Tracer.tracepointMessage("Creating threads");
								Thread stonesoup_thread1 = new Thread(
										new replaceSymbols(
												stonesoup_qsize,
												IndexCacheModule.ministrableCincinnati));
								Thread stonesoup_thread2 = new Thread(
										new toCaps(
												stonesoup_qsize,
												IndexCacheModule.ministrableCincinnati));
								stonesoup_threadInput = new StringBuilder()
										.append(stonesoup_substrings[1]);
								Tracer.tracepointMessage("Spawning threads.");
								IndexCacheModule.ministrableCincinnati
										.println("Info: Spawning thread 1.");
								stonesoup_thread1.start();
								IndexCacheModule.ministrableCincinnati
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
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									IndexCacheModule.ministrableCincinnati
											.println("Interrupted");
								}
								IndexCacheModule.ministrableCincinnati
										.println("Info: Threads ended");
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						IndexCacheModule.ministrableCincinnati.close();
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

	private static ReentrantLock lock = new ReentrantLock();
	private static StringBuilder stonesoup_threadInput;
	static volatile int j;

	public static void arrFunc(int size, String tempfile, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmp20bXYq_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
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
					"/tmp/tmp20bXYq_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
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
					"/tmp/tmp20bXYq_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
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
					"/tmp/tmp20bXYq_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
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
					"/tmp/tmp20bXYq_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"toCaps.ctor");
			this.size = size;
			this.output = output;
		}
	}
}
