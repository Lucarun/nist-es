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
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    private static final int tuchit_reaggregate = 22;
	static PrintStream danubeAsslike = null;
	private static final java.util.concurrent.atomic.AtomicBoolean philadelphySeiche = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (philadelphySeiche.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmplVDAaG_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File samaniLatiniform = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!samaniLatiniform.getParentFile().exists()
					&& !samaniLatiniform.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.danubeAsslike = new PrintStream(
							new FileOutputStream(samaniLatiniform, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException holagogueEtherealism) {
					System.err.printf("Failed to open log file.  %s\n",
							holagogueEtherealism.getMessage());
					ShardIdCacheModule.danubeAsslike = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							holagogueEtherealism);
				} catch (FileNotFoundException tribulationPapillate) {
					System.err.printf("Failed to open log file.  %s\n",
							tribulationPapillate.getMessage());
					ShardIdCacheModule.danubeAsslike = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							tribulationPapillate);
				}
				if (ShardIdCacheModule.danubeAsslike != null) {
					try {
						String rageous_genoese = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (rageous_genoese == null
								|| !rageous_genoese.equals("1")) {
							String liverleaf_tonsurate = System
									.getenv("PRAYERLESSNESS_WINETASTER");
							if (null != liverleaf_tonsurate) {
								File gigliato_gynodioecious = new File(
										liverleaf_tonsurate);
								if (gigliato_gynodioecious.exists()
										&& !gigliato_gynodioecious
												.isDirectory()) {
									try {
										String illuministic_jostle;
										Scanner recompetitor_octahedrical = new Scanner(
												gigliato_gynodioecious, "UTF-8")
												.useDelimiter("\\A");
										if (recompetitor_octahedrical.hasNext())
											illuministic_jostle = recompetitor_octahedrical
													.next();
										else
											illuministic_jostle = "";
										if (null != illuministic_jostle) {
											String[] unwire_oidiomycotic = new String[23];
											unwire_oidiomycotic[tuchit_reaggregate] = illuministic_jostle;
											int unmaniac_syllabicate = 0;
											while (true) {
												unmaniac_syllabicate++;
												if (unmaniac_syllabicate >= 3000)
													break;
											}
											Tracer.tracepointWeaknessStart(
													"CWE833", "A", "Deadlock");
											Tracer.tracepointVariableString(
													"stonesoup_value",
													unwire_oidiomycotic[tuchit_reaggregate]);
											boolean stonesoup_upper = firstIsUpper(unwire_oidiomycotic[tuchit_reaggregate]);
											ReentrantLock stonesoup_lock = null;
											Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
											if (stonesoup_upper) {
												Tracer.tracepointMessage("LOCK: stonesoup_upperLock");
												stonesoup_lock = stonesoup_upperLock;
											} else {
												Tracer.tracepointMessage("LOCK: stonesoup_lowerLock");
												stonesoup_lock = stonesoup_lowerLock;
											}
											Tracer.tracepointMessage("Locking lock");
											stonesoup_lock.lock();
											Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
											try {
												Tracer.tracepointMessage("Creating thread");
												Thread stonesoup_thread1 = new Thread(
														new CountUpper(
																unwire_oidiomycotic[tuchit_reaggregate],
																ShardIdCacheModule.danubeAsslike));
												stonesoup_thread1.start();
												for (int ii = 0; ii < unwire_oidiomycotic[tuchit_reaggregate]
														.length(); ii++) {
													if (stonesoup_upper
															&& Character
																	.isUpperCase(unwire_oidiomycotic[tuchit_reaggregate]
																			.charAt(ii))) {
														stonesoup_upperInt += 1;
													} else if (!stonesoup_upper
															&& !Character
																	.isUpperCase(unwire_oidiomycotic[tuchit_reaggregate]
																			.charAt(ii))) {
														stonesoup_lowerInt += 1;
													}
												}
												try {
													Tracer.tracepointMessage("Joining thread-01");
													stonesoup_thread1.join();
													Tracer.tracepointMessage("Joined thread-01");
												} catch (InterruptedException e) {
													Tracer.tracepointError(e
															.getClass()
															.getName()
															+ ": "
															+ e.getMessage());
													ShardIdCacheModule.danubeAsslike
															.println("Interrupted");
												}
											} finally {
												Tracer.tracepointMessage("Unlocking lock");
												stonesoup_lock.unlock();
											}
											ShardIdCacheModule.danubeAsslike
													.println("finished evaluating");
											ShardIdCacheModule.danubeAsslike
													.println("Threads ended, upperInt "
															+ stonesoup_upperInt
															+ " lowerInt "
															+ stonesoup_lowerInt);
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException predecisiveUnconglutinated) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												predecisiveUnconglutinated);
									}
								}
							}
						}
					} finally {
						ShardIdCacheModule.danubeAsslike.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	private static ReentrantLock stonesoup_lowerLock = new ReentrantLock();
	private static ReentrantLock stonesoup_upperLock = new ReentrantLock();
	private static int stonesoup_lowerInt = 0;
	private static int stonesoup_upperInt = 0;

	public static class CountUpper implements Runnable {
		private String value;
		private PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmplVDAaG_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"CountUpper.run");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			Tracer.tracepointMessage("Locking lock");
			stonesoup_upperLock.lock();
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			try {
				for (int ii = 0; ii < value.length(); ii++) {
					if (Character.isUpperCase(value.charAt(ii))) {
						stonesoup_upperInt += 1;
					}
				}
			} finally {
				Tracer.tracepointMessage("Unlocking lock");
				stonesoup_upperLock.unlock();
			}
			output.println("Info: Thread ending, upperInt "
					+ stonesoup_upperInt);
		}

		public CountUpper(String value, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmplVDAaG_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"CountUpper.ctor");
			this.value = value;
			this.output = output;
		}
	}

	private static boolean firstIsUpper(String value) {
		Tracer.tracepointLocation(
				"/tmp/tmplVDAaG_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
				"firstIsUpper");
		return (Character.isUpperCase(value.charAt(0)));
	}
}
