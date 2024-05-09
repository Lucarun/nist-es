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

    public static interface IPolygenesisSpherula {
		public void fetishmongerJudaize(Object platycarya_paratype);
	}

	public static class DoctrinarilySuperadequate implements
			IPolygenesisSpherula {
		@Override
		public void fetishmongerJudaize(Object platycarya_paratype) {
			Tracer.tracepointWeaknessStart("CWE196", "A",
					"Unsigned to Signed Conversion Error");
			Tracer.tracepointVariableChar("value",
					((Character) platycarya_paratype));
			try {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				int[] stonesoup_char_counts = stonesoupInitializeCounts((byte) ((char) ((Character) platycarya_paratype)));
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				for (char counter = 0; counter < ((Character) platycarya_paratype); counter++) {
					stonesoup_char_counts[counter] += 1;
				}
				Tracer.tracepointBufferInfo("stonesoup_char_counts",
						stonesoup_char_counts.length,
						"Length of stonesoup_char_counts");
			} catch (RuntimeException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				e.printStackTrace(AbstractDistributor.semispiralAccomplishable);
				throw e;
			}
			Tracer.tracepointWeaknessEnd();
		}

		public static int[] stonesoupInitializeCounts(byte size) {
			Tracer.tracepointLocation(
					"/tmp/tmp2qoUlS_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"stonesoupInitializeCounts");
			Tracer.tracepointVariableByte("size", size);
			if (size == 0) {
				return null;
			}
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			int[] result = new int[size];
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			Tracer.tracepointBufferInfo("result", result.length,
					"Length of result");
			for (int ii = 0; ii < result.length; ii++) {
				result[ii] = 0;
			}
			return result;
		}
	}

	static PrintStream semispiralAccomplishable = null;
	private static final java.util.concurrent.atomic.AtomicBoolean comedyVirial = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (comedyVirial.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp2qoUlS_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File sublationMehtar = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!sublationMehtar.getParentFile().exists()
					&& !sublationMehtar.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.semispiralAccomplishable = new PrintStream(
							new FileOutputStream(sublationMehtar, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException morularMethylenimine) {
					System.err.printf("Failed to open log file.  %s\n",
							morularMethylenimine.getMessage());
					AbstractDistributor.semispiralAccomplishable = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							morularMethylenimine);
				} catch (FileNotFoundException unassignedTurritellidae) {
					System.err.printf("Failed to open log file.  %s\n",
							unassignedTurritellidae.getMessage());
					AbstractDistributor.semispiralAccomplishable = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unassignedTurritellidae);
				}
				if (AbstractDistributor.semispiralAccomplishable != null) {
					try {
						String sublation_platysomid = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (sublation_platysomid == null
								|| !sublation_platysomid.equals("1")) {
							String goodeniaceous_smockface = System
									.getenv("AMBULANCE_BATHYANESTHESIA");
							if (null != goodeniaceous_smockface) {
								File rachis_grithbreach = new File(
										goodeniaceous_smockface);
								if (rachis_grithbreach.exists()
										&& !rachis_grithbreach.isDirectory()) {
									try {
										String noncommercial_spirignath;
										Scanner flagless_nucivorous = new Scanner(
												rachis_grithbreach, "UTF-8")
												.useDelimiter("\\A");
										if (flagless_nucivorous.hasNext())
											noncommercial_spirignath = flagless_nucivorous
													.next();
										else
											noncommercial_spirignath = "";
										if (null != noncommercial_spirignath) {
											char aggerose_overhear;
											try {
												aggerose_overhear = noncommercial_spirignath
														.charAt(0);
											} catch (IndexOutOfBoundsException atypically_flagitate) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														atypically_flagitate);
											}
											Object studio_stocks = aggerose_overhear;
											IPolygenesisSpherula whiting_boxbush = new DoctrinarilySuperadequate();
											whiting_boxbush
													.fetishmongerJudaize(studio_stocks);
										}
									} catch (FileNotFoundException scoutOscinian) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												scoutOscinian);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.semispiralAccomplishable.close();
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
