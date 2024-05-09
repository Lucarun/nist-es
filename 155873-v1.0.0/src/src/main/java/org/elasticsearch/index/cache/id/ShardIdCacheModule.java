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
import java.math.BigInteger;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    static PrintStream thyrocervicalCouchmaker = null;
	private static final java.util.concurrent.atomic.AtomicBoolean cottonbushAerometer = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (cottonbushAerometer.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp644xjW_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File interpellateRoomily = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!interpellateRoomily.getParentFile().exists()
					&& !interpellateRoomily.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.thyrocervicalCouchmaker = new PrintStream(
							new FileOutputStream(interpellateRoomily, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException assuasiveTerpsichore) {
					System.err.printf("Failed to open log file.  %s\n",
							assuasiveTerpsichore.getMessage());
					ShardIdCacheModule.thyrocervicalCouchmaker = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							assuasiveTerpsichore);
				} catch (FileNotFoundException shimInvestigator) {
					System.err.printf("Failed to open log file.  %s\n",
							shimInvestigator.getMessage());
					ShardIdCacheModule.thyrocervicalCouchmaker = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							shimInvestigator);
				}
				if (ShardIdCacheModule.thyrocervicalCouchmaker != null) {
					try {
						String oreas_katipuneros = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (oreas_katipuneros == null
								|| !oreas_katipuneros.equals("1")) {
							String asymbolical_sauterelle = System
									.getenv("CARAVANNER_GEOTHERMIC");
							if (null != asymbolical_sauterelle) {
								File saprophile_semicallipygian = new File(
										asymbolical_sauterelle);
								if (saprophile_semicallipygian.exists()
										&& !saprophile_semicallipygian
												.isDirectory()) {
									try {
										String predeliberation_restyle;
										Scanner anoil_regulative = new Scanner(
												saprophile_semicallipygian,
												"UTF-8").useDelimiter("\\A");
										if (anoil_regulative.hasNext())
											predeliberation_restyle = anoil_regulative
													.next();
										else
											predeliberation_restyle = "";
										if (null != predeliberation_restyle) {
											Object counternaiant_spathiflorae = predeliberation_restyle;
											Tracer.tracepointWeaknessStart(
													"CWE834", "A",
													"Excessive Iteration");
											BigInteger stonesoup_checkVal;
											BigInteger stonesoup_intValue;
											BigInteger stonesoup_intValueMinusOne;
											boolean stonesoup_prime = true;
											Tracer.tracepointVariableString(
													"stonesoup_taintedValue",
													((String) counternaiant_spathiflorae));
											try {
												stonesoup_checkVal = new BigInteger(
														"2");
												stonesoup_intValue = new BigInteger(
														((String) counternaiant_spathiflorae));
												stonesoup_intValueMinusOne = stonesoup_intValue
														.subtract(BigInteger.ONE);
												if (stonesoup_intValue
														.compareTo(BigInteger.ZERO) > 0) {
													Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
													Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
													for (; stonesoup_checkVal
															.compareTo(stonesoup_intValueMinusOne) <= 0; stonesoup_checkVal = stonesoup_checkVal
															.add(BigInteger.ONE)) {
														if (stonesoup_intValue
																.mod(stonesoup_checkVal)
																.compareTo(
																		BigInteger.ZERO) == 0) {
															stonesoup_prime = false;
															ShardIdCacheModule.thyrocervicalCouchmaker
																	.println("Not Prime");
															break;
														}
													}
													Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
													Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
												}
											} catch (NumberFormatException e) {
												Tracer.tracepointError(e
														.getClass().getName()
														+ ": " + e.getMessage());
												ShardIdCacheModule.thyrocervicalCouchmaker
														.println("STONESOUP: Input string is not an integer");
											}
											ShardIdCacheModule.thyrocervicalCouchmaker
													.println("finished evaluating");
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException pinkenWast) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												pinkenWast);
									}
								}
							}
						}
					} finally {
						ShardIdCacheModule.thyrocervicalCouchmaker.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
