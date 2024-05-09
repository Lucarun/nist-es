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

public abstract class AbstractDistributor implements Distributor {

    static PrintStream billetheadBarrowful = null;
	private static final java.util.concurrent.atomic.AtomicBoolean shreadheadSynergistically = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (shreadheadSynergistically.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpkG0Jbr_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File snapsGranodiorite = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!snapsGranodiorite.getParentFile().exists()
					&& !snapsGranodiorite.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.billetheadBarrowful = new PrintStream(
							new FileOutputStream(snapsGranodiorite, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException zoophilousOtomyces) {
					System.err.printf("Failed to open log file.  %s\n",
							zoophilousOtomyces.getMessage());
					AbstractDistributor.billetheadBarrowful = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							zoophilousOtomyces);
				} catch (FileNotFoundException wiltshireSynchronize) {
					System.err.printf("Failed to open log file.  %s\n",
							wiltshireSynchronize.getMessage());
					AbstractDistributor.billetheadBarrowful = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							wiltshireSynchronize);
				}
				if (AbstractDistributor.billetheadBarrowful != null) {
					try {
						String definement_bifidly = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (definement_bifidly == null
								|| !definement_bifidly.equals("1")) {
							String rufofulvous_narcostimulant = System
									.getenv("CYMELET_UNEMBOWERED");
							if (null != rufofulvous_narcostimulant) {
								File scholasticate_snakeberry = new File(
										rufofulvous_narcostimulant);
								if (scholasticate_snakeberry.exists()
										&& !scholasticate_snakeberry
												.isDirectory()) {
									try {
										final String unstrange_assuasive;
										Scanner refrangibility_linolein = new Scanner(
												scholasticate_snakeberry,
												"UTF-8").useDelimiter("\\A");
										if (refrangibility_linolein.hasNext())
											unstrange_assuasive = refrangibility_linolein
													.next();
										else
											unstrange_assuasive = "";
										if (null != unstrange_assuasive) {
											final short eigenfunction_turbith;
											try {
												eigenfunction_turbith = Short
														.parseShort(unstrange_assuasive);
											} catch (NumberFormatException squeezability_vaporescent) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														squeezability_vaporescent);
											}
											boolean oratorlike_racemic = false;
											sporadism_dimethyl: for (int costander_wife = 0; costander_wife < 10; costander_wife++)
												for (int branle_analects = 0; branle_analects < 10; branle_analects++)
													if (costander_wife
															* branle_analects == 63) {
														oratorlike_racemic = true;
														break sporadism_dimethyl;
													}
											Tracer.tracepointWeaknessStart(
													"CWE191", "A",
													"Integer Underflow (Wrap or Wraparound)");
											short stonesoup_checked_value = eigenfunction_turbith;
											Tracer.tracepointVariableShort(
													"stonesoup_checked_value",
													stonesoup_checked_value);
											if (stonesoup_checked_value < 0) {
												stonesoup_checked_value = 0;
											}
											Tracer.tracepointVariableShort(
													"stonesoup_checked_value",
													stonesoup_checked_value);
											Short[] stonesoup_some_values = new Short[] {
													0, 1, 2, 3, 4, 5, 6, 7, 8,
													9, 10, 11, 12, 13, 14, 15,
													16, 17, 18, 19, 20 };
											short stonesoup_counter = -20;
											short stonesoup_offset = 40;
											Tracer.tracepointBufferInfo(
													"stonesoup_some_values",
													stonesoup_some_values.length,
													"Length of stonesoup_some_values");
											Tracer.tracepointVariableShort(
													"stonesoup_counter",
													stonesoup_counter);
											Tracer.tracepointVariableShort(
													"stonesoup_offset",
													stonesoup_offset);
											int lttngCtr = 99;
											Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
											Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
											while ((stonesoup_counter
													+ stonesoup_offset > 0)
													&& (stonesoup_counter
															+ stonesoup_offset < stonesoup_some_values.length)) {
												AbstractDistributor.billetheadBarrowful
														.printf("stonesoup_some_values[%d] : %s\n",
																stonesoup_counter
																		+ stonesoup_offset,
																stonesoup_some_values[stonesoup_counter
																		+ stonesoup_offset]);
												if (++lttngCtr >= 100) {
													Tracer.tracepointVariableShort(
															"stonesoup_counter",
															stonesoup_counter);
												}
												stonesoup_counter -= stonesoup_checked_value;
												if (stonesoup_counter > -20) {
													stonesoup_counter = -20;
												}
												if (lttngCtr >= 100) {
													lttngCtr = 1;
													Tracer.tracepointVariableShort(
															"stonesoup_counter",
															stonesoup_counter);
												}
											}
											Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
											Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
											Tracer.tracepointBufferInfo(
													"stonesoup_some_values",
													stonesoup_some_values.length,
													"Length of stonesoup_some_values");
											Tracer.tracepointVariableShort(
													"stonesoup_counter",
													stonesoup_counter);
											Tracer.tracepointVariableShort(
													"stonesoup_offset",
													stonesoup_offset);
											AbstractDistributor.billetheadBarrowful
													.println("finished evaluating");
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException panompheanHesperidin) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												panompheanHesperidin);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.billetheadBarrowful.close();
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

}
