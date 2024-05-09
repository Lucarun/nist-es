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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class AbstractDistributor implements Distributor {

    static PrintStream mermithidaeNosologically = null;
	private static final java.util.concurrent.atomic.AtomicBoolean ecchondrosisEmotion = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (ecchondrosisEmotion.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpuoK7L1_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File cuissenString = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!cuissenString.getParentFile().exists()
					&& !cuissenString.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.mermithidaeNosologically = new PrintStream(
							new FileOutputStream(cuissenString, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException didascaliaeAmply) {
					System.err.printf("Failed to open log file.  %s\n",
							didascaliaeAmply.getMessage());
					AbstractDistributor.mermithidaeNosologically = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							didascaliaeAmply);
				} catch (FileNotFoundException deciusFlair) {
					System.err.printf("Failed to open log file.  %s\n",
							deciusFlair.getMessage());
					AbstractDistributor.mermithidaeNosologically = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", deciusFlair);
				}
				if (AbstractDistributor.mermithidaeNosologically != null) {
					try {
						String tidbit_kingu = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (tidbit_kingu == null || !tidbit_kingu.equals("1")) {
							String extrorse_priestfish = System
									.getenv("SULPHARSENIOUS_UNPREDISPOSING");
							if (null != extrorse_priestfish) {
								File antehall_pauciarticulate = new File(
										extrorse_priestfish);
								if (antehall_pauciarticulate.exists()
										&& !antehall_pauciarticulate
												.isDirectory()) {
									try {
										String unsystematic_unshorn;
										Scanner misdelivery_platykurtic = new Scanner(
												antehall_pauciarticulate,
												"UTF-8").useDelimiter("\\A");
										if (misdelivery_platykurtic.hasNext())
											unsystematic_unshorn = misdelivery_platykurtic
													.next();
										else
											unsystematic_unshorn = "";
										if (null != unsystematic_unshorn) {
											int unhallooed_voidable;
											try {
												unhallooed_voidable = Integer
														.parseInt(unsystematic_unshorn);
											} catch (NumberFormatException berycomorphi_landgravate) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														berycomorphi_landgravate);
											}
											Tracer.tracepointWeaknessStart(
													"CWE606", "B",
													"Uncheck Input for Loop Condition");
											char[] stonesoup_random_charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
													.toCharArray();
											SecureRandom random = null;
											try {
												random = SecureRandom
														.getInstance("SHA1PRNG");
											} catch (NoSuchAlgorithmException e) {
												Tracer.tracepointError(e
														.getClass().getName()
														+ ": " + e.getMessage());
												AbstractDistributor.mermithidaeNosologically
														.println("STONESOUP: Random generator algorithm does not exist.");
											}
											Tracer.tracepointVariableInt(
													"value",
													unhallooed_voidable);
											if (random != null) {
												StringBuilder stonesoup_filename = new StringBuilder();
												AbstractDistributor.mermithidaeNosologically
														.println("Generating file name");
												Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
												for (int stonesoup_counter = 0; stonesoup_counter < unhallooed_voidable; stonesoup_counter++) {
													stonesoup_filename
															.append(stonesoup_random_charset[random
																	.nextInt(stonesoup_random_charset.length)]);
												}
												Tracer.tracepointVariableString(
														"stonesoup_filename",
														stonesoup_filename
																.toString());
												Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
												if (stonesoup_filename.length() > 0) {
													File writePath = new File(
															stonesoup_filename
																	.toString());
													try {
														Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
														writePath
																.createNewFile();
														Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
													} catch (IOException e) {
														Tracer.tracepointError(e
																.getClass()
																.getName()
																+ ": "
																+ e.getMessage());
														AbstractDistributor.mermithidaeNosologically
																.println("Failed to create file.");
														AbstractDistributor.mermithidaeNosologically
																.println("Error:");
														e.printStackTrace(AbstractDistributor.mermithidaeNosologically);
														throw new RuntimeException(
																"Unknown error in filename.",
																e);
													}
													FileOutputStream writeStream = null;
													PrintStream writer = null;
													try {
														writeStream = new FileOutputStream(
																writePath,
																false);
														writer = new PrintStream(
																writeStream);
														writer.println("/* This is my file */");
													} catch (FileNotFoundException e) {
														Tracer.tracepointError(e
																.getClass()
																.getName()
																+ ": "
																+ e.getMessage());
														AbstractDistributor.mermithidaeNosologically
																.println("Failed to create file.");
														e.printStackTrace(AbstractDistributor.mermithidaeNosologically);
													} finally {
														if (writer != null) {
															writer.close();
														}
													}
												}
											}
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException festucaAstrantia) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												festucaAstrantia);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.mermithidaeNosologically.close();
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
