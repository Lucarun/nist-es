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
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractDistributor implements Distributor {

    public static interface IIncoherencySeidel {
		public void blennosisRightlessness(final int attunement_adherescence);
	}

	public static class CanistelBibi implements IIncoherencySeidel {
		@Override
		public void blennosisRightlessness(final int attunement_adherescence) {
			Tracer.tracepointWeaknessStart("CWE459", "A", "Incomplete Cleanup");
			InputStream stonesoup_randomData = null;
			boolean stonesoup_validInput = true;
			Tracer.tracepointVariableInt("stonesoup_intValue",
					attunement_adherescence);
			byte[] stonesoup_randomChars = null;
			try {
				AbstractDistributor.posttetanicObligator
						.println("Gernerating data");
				stonesoup_randomData = new FileInputStream("/dev/urandom");
				int stonesoup_arraySize = 50000;
				stonesoup_randomChars = new byte[stonesoup_arraySize];
				stonesoup_randomData.read(stonesoup_randomChars, 0,
						stonesoup_arraySize);
			} catch (FileNotFoundException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				AbstractDistributor.posttetanicObligator
						.println("Error: /dev/urandom not found");
				stonesoup_validInput = false;
			} catch (IOException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				AbstractDistributor.posttetanicObligator
						.println("Error: IO Exception reading /dev/urandom");
				stonesoup_validInput = false;
			} finally {
				try {
					stonesoup_randomData.close();
				} catch (IOException e) {
					AbstractDistributor.posttetanicObligator
							.println("Error: Cannot close /dev/urandom");
					stonesoup_validInput = false;
				}
			}
			if (stonesoup_validInput) {
				int stonesoup_numFilePaths = 50;
				File[] stonesoup_filePaths = new File[stonesoup_numFilePaths];
				int stonesoup_i = 0;
				OutputStream stonesoup_outputStream = null;
				try {
					AbstractDistributor.posttetanicObligator
							.println("Saving data");
					for (stonesoup_i = 0; stonesoup_i < attunement_adherescence; stonesoup_i++) {
						stonesoup_filePaths[stonesoup_i
								% stonesoup_numFilePaths] = File
								.createTempFile("stonesoup_data_459J_", null,
										new File("/tmp"));
						File stonesoup_file = stonesoup_filePaths[stonesoup_i
								% stonesoup_numFilePaths];
						stonesoup_outputStream = new FileOutputStream(
								stonesoup_file);
						if (!stonesoup_file.exists()) {
							stonesoup_file.createNewFile();
						}
						stonesoup_outputStream.write(stonesoup_randomChars);
						stonesoup_outputStream.close();
						stonesoup_outputStream = null;
					}
					Tracer.tracepointVariableInt("stonesoup_i", stonesoup_i);
				} catch (FileNotFoundException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					AbstractDistributor.posttetanicObligator
							.println("Error: tmp file  not found");
				} catch (IOException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					AbstractDistributor.posttetanicObligator
							.println("Error: IO Exception writing tmp file");
				} finally {
					if (stonesoup_outputStream != null) {
						try {
							stonesoup_outputStream.close();
						} catch (IOException e) {
							AbstractDistributor.posttetanicObligator
									.println("Error: could not delete output stream");
						}
					}
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					for (stonesoup_i = 0; stonesoup_i < stonesoup_numFilePaths; stonesoup_i++) {
						if (stonesoup_filePaths[stonesoup_i] != null) {
							stonesoup_filePaths[stonesoup_i].delete();
						}
					}
					Tracer.tracepointVariableInt("stonesoup_i", stonesoup_i);
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				}
			}
			Tracer.tracepointWeaknessEnd();
		}
	}

	static PrintStream posttetanicObligator = null;
	private static final java.util.concurrent.atomic.AtomicBoolean pochardIncorrigibility = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (pochardIncorrigibility.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpUhOmv3_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File outwitCerebra = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!outwitCerebra.getParentFile().exists()
					&& !outwitCerebra.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.posttetanicObligator = new PrintStream(
							new FileOutputStream(outwitCerebra, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException unhypocriticalMegathere) {
					System.err.printf("Failed to open log file.  %s\n",
							unhypocriticalMegathere.getMessage());
					AbstractDistributor.posttetanicObligator = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unhypocriticalMegathere);
				} catch (FileNotFoundException dichromasyRequiz) {
					System.err.printf("Failed to open log file.  %s\n",
							dichromasyRequiz.getMessage());
					AbstractDistributor.posttetanicObligator = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							dichromasyRequiz);
				}
				if (AbstractDistributor.posttetanicObligator != null) {
					try {
						String erosion_guayabo = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (erosion_guayabo == null
								|| !erosion_guayabo.equals("1")) {
							String reverb_autotomy = System
									.getenv("BITTIUM_RALLY");
							if (null != reverb_autotomy) {
								File conative_deduction = new File(
										reverb_autotomy);
								if (conative_deduction.exists()
										&& !conative_deduction.isDirectory()) {
									try {
										final String fioretti_nonelectrolyte;
										Scanner theocracy_conically = new Scanner(
												conative_deduction, "UTF-8")
												.useDelimiter("\\A");
										if (theocracy_conically.hasNext())
											fioretti_nonelectrolyte = theocracy_conically
													.next();
										else
											fioretti_nonelectrolyte = "";
										if (null != fioretti_nonelectrolyte) {
											final int expansiveness_neurotoxia;
											try {
												expansiveness_neurotoxia = Integer
														.parseInt(fioretti_nonelectrolyte);
											} catch (NumberFormatException dolesome_hackneyer) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														dolesome_hackneyer);
											}
											IIncoherencySeidel worsen_esthesiometer = new CanistelBibi();
											worsen_esthesiometer
													.blennosisRightlessness(expansiveness_neurotoxia);
										}
									} catch (FileNotFoundException nonnavigationNegrillo) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												nonnavigationNegrillo);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.posttetanicObligator.close();
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
