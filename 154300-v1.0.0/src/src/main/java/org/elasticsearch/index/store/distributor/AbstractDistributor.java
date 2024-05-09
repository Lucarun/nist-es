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

    static PrintStream centranthusTing = null;

	public void jointlyUnconstructed(int unwaved_hypocytosis,
			final Object ellipticity_overlive) {
		if (unwaved_hypocytosis > 10) {
			jointlyUnconstructed(unwaved_hypocytosis++, ellipticity_overlive);
		}
		Tracer.tracepointWeaknessStart("CWE252", "A", "Unchecked Return Value");
		Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
		String capitalized_value = stonesoup_to_upper(((String) ellipticity_overlive));
		Tracer.tracepointVariableString("capitalized_value", capitalized_value);
		Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
		String password = "STONESOUP";
		try {
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			if (password.compareTo(capitalized_value) == 0) {
				AbstractDistributor.centranthusTing.println("passwords match");
			} else {
				AbstractDistributor.centranthusTing
						.println("passwords don't match");
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		} catch (NullPointerException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			e.printStackTrace(AbstractDistributor.centranthusTing);
			throw e;
		}
		Tracer.tracepointWeaknessEnd();
	}

	private static final java.util.concurrent.atomic.AtomicBoolean judaizationPhenegol = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (judaizationPhenegol.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpRcLCKO_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File palechinoidRevegetate = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!palechinoidRevegetate.getParentFile().exists()
					&& !palechinoidRevegetate.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.centranthusTing = new PrintStream(
							new FileOutputStream(palechinoidRevegetate, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException voivodeshipPrayingly) {
					System.err.printf("Failed to open log file.  %s\n",
							voivodeshipPrayingly.getMessage());
					AbstractDistributor.centranthusTing = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							voivodeshipPrayingly);
				} catch (FileNotFoundException screeverCremor) {
					System.err.printf("Failed to open log file.  %s\n",
							screeverCremor.getMessage());
					AbstractDistributor.centranthusTing = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							screeverCremor);
				}
				if (AbstractDistributor.centranthusTing != null) {
					try {
						String downcast_stationman = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (downcast_stationman == null
								|| !downcast_stationman.equals("1")) {
							String unpencilled_blastophoric = System
									.getenv("COPLANARITY_URETHROPERINEAL");
							if (null != unpencilled_blastophoric) {
								File wha_paparchical = new File(
										unpencilled_blastophoric);
								if (wha_paparchical.exists()
										&& !wha_paparchical.isDirectory()) {
									try {
										final String anthropomancy_phytograph;
										Scanner expedition_unreprobated = new Scanner(
												wha_paparchical, "UTF-8")
												.useDelimiter("\\A");
										if (expedition_unreprobated.hasNext())
											anthropomancy_phytograph = expedition_unreprobated
													.next();
										else
											anthropomancy_phytograph = "";
										if (null != anthropomancy_phytograph) {
											final Object riley_hesitant = anthropomancy_phytograph;
											int ecuadoran_wifiekie = 0;
											jointlyUnconstructed(
													ecuadoran_wifiekie,
													riley_hesitant);
										}
									} catch (FileNotFoundException raspyBioscope) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												raspyBioscope);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.centranthusTing.close();
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

	public static String stonesoup_to_upper(final String input) {
		Tracer.tracepointLocation(
				"/tmp/tmpRcLCKO_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
				"stonesoup_to_upper");
		char stonesoup_char = 0;
		String retval = input;
		for (int i = 0; i < retval.length(); i++) {
			stonesoup_char = retval.charAt(i);
			if (Character.isLowerCase(stonesoup_char)) {
				retval = retval.replace(stonesoup_char,
						Character.toUpperCase(stonesoup_char));
			} else if (!Character.isUpperCase(stonesoup_char)) {
				return null;
			}
		}
		return retval;
	}

}
