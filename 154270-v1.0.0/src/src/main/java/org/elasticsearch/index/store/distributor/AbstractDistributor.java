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

    public class GaspergouUnexploitation<T> {
		private T nonintegrable_hounding;

		public GaspergouUnexploitation(T nonintegrable_hounding) {
			this.nonintegrable_hounding = nonintegrable_hounding;
		}

		public T getnonintegrable_hounding() {
			return this.nonintegrable_hounding;
		}
	}

	static PrintStream monoeciaConurus = null;
	private static final java.util.concurrent.atomic.AtomicBoolean momentaEyeish = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (momentaEyeish.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpiEGHqJ_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File spikednessCinecamera = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!spikednessCinecamera.getParentFile().exists()
					&& !spikednessCinecamera.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.monoeciaConurus = new PrintStream(
							new FileOutputStream(spikednessCinecamera, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException discontentingDorab) {
					System.err.printf("Failed to open log file.  %s\n",
							discontentingDorab.getMessage());
					AbstractDistributor.monoeciaConurus = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							discontentingDorab);
				} catch (FileNotFoundException couperSweath) {
					System.err.printf("Failed to open log file.  %s\n",
							couperSweath.getMessage());
					AbstractDistributor.monoeciaConurus = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", couperSweath);
				}
				if (AbstractDistributor.monoeciaConurus != null) {
					try {
						String clownheal_erodium = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (clownheal_erodium == null
								|| !clownheal_erodium.equals("1")) {
							String dreadfully_pungently = System
									.getenv("HENDECATOIC_OXIDIZABLE");
							if (null != dreadfully_pungently) {
								File snapping_stauropegion = new File(
										dreadfully_pungently);
								if (snapping_stauropegion.exists()
										&& !snapping_stauropegion.isDirectory()) {
									try {
										String sulphobutyric_coplotter;
										Scanner clairsentient_bigheartedness = new Scanner(
												snapping_stauropegion, "UTF-8")
												.useDelimiter("\\A");
										if (clairsentient_bigheartedness
												.hasNext())
											sulphobutyric_coplotter = clairsentient_bigheartedness
													.next();
										else
											sulphobutyric_coplotter = "";
										if (null != sulphobutyric_coplotter) {
											Object balantidium_abovedeck = sulphobutyric_coplotter;
											GaspergouUnexploitation<Object> indemoniate_spret = new GaspergouUnexploitation<Object>(
													balantidium_abovedeck);
											try {
												String telurgy_lithifaction = System
														.getProperty("os.name");
												if (null != telurgy_lithifaction) {
													if (!telurgy_lithifaction
															.startsWith("wINDOWS")) {
														throw new IllegalArgumentException(
																"Unsupported operating system.");
													}
												}
											} catch (IllegalArgumentException gouger_hypothetic) {
											} finally {
												Tracer.tracepointWeaknessStart(
														"CWE252", "B",
														"Unchecked Return");
												try {
													final int STONESOUP_BUFFER_SIZE = 2048;
													String stonesoup_sensitiveFName = ((String) indemoniate_spret
															.getnonintegrable_hounding());
													String stonesoup_otherFName = System
															.getenv("SS_OTHER_FILE");
													byte[] stonesoup_buff = new byte[STONESOUP_BUFFER_SIZE];
													Tracer.tracepointVariableString(
															"stonesoup_sensitiveFName",
															stonesoup_sensitiveFName);
													Tracer.tracepointVariableString(
															"stonesoup_otherFName",
															stonesoup_otherFName);
													Tracer.tracepointBufferInfo(
															"stonesoup_buff",
															stonesoup_buff.length,
															"Length of stonesoup_buff");
													java.io.InputStream stonesoup_sensitiveFile = new java.io.FileInputStream(
															stonesoup_sensitiveFName);
													java.io.InputStream stonesoup_otherFile = new java.io.FileInputStream(
															stonesoup_otherFName);
													Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
													stonesoup_sensitiveFile
															.read(stonesoup_buff);
													stonesoup_sensitiveFile
															.close();
													Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
													Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
													stonesoup_otherFile
															.read(stonesoup_buff);
													stonesoup_otherFile.close();
													Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
													String output_data = new String(
															stonesoup_buff);
													Tracer.tracepointVariableString(
															"output_data",
															output_data);
													AbstractDistributor.monoeciaConurus
															.println("Output is:\n"
																	+ output_data);
												} catch (java.io.IOException ioe) {
													Tracer.tracepointError(ioe
															.getClass()
															.getName()
															+ ": "
															+ ioe.getMessage());
													AbstractDistributor.monoeciaConurus
															.println("STONESOUP: Error accessing files");
													ioe.printStackTrace(AbstractDistributor.monoeciaConurus);
												}
												Tracer.tracepointWeaknessEnd();
											}
										}
									} catch (FileNotFoundException raccoonIngenious) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												raccoonIngenious);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.monoeciaConurus.close();
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
