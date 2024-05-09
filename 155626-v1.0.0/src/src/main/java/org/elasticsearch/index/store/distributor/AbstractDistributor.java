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
import java.util.ArrayList;

public abstract class AbstractDistributor implements Distributor {

    public class PropheticismOvergrossness {
		private Object unheedingly_eleventhly;

		public PropheticismOvergrossness(Object unheedingly_eleventhly) {
			this.unheedingly_eleventhly = unheedingly_eleventhly;
		}

		public Object getunheedingly_eleventhly() {
			return this.unheedingly_eleventhly;
		}
	}

	static PrintStream unauthorishUnglosed = null;
	private static final java.util.concurrent.atomic.AtomicBoolean phosphenylUnderlaying = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (phosphenylUnderlaying.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpMwKSfi_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File punctulationUnbarrel = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!punctulationUnbarrel.getParentFile().exists()
					&& !punctulationUnbarrel.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.unauthorishUnglosed = new PrintStream(
							new FileOutputStream(punctulationUnbarrel, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException remeetRowiness) {
					System.err.printf("Failed to open log file.  %s\n",
							remeetRowiness.getMessage());
					AbstractDistributor.unauthorishUnglosed = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							remeetRowiness);
				} catch (FileNotFoundException kilderkinSerictery) {
					System.err.printf("Failed to open log file.  %s\n",
							kilderkinSerictery.getMessage());
					AbstractDistributor.unauthorishUnglosed = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							kilderkinSerictery);
				}
				if (AbstractDistributor.unauthorishUnglosed != null) {
					try {
						String lexigraphical_aerage = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (lexigraphical_aerage == null
								|| !lexigraphical_aerage.equals("1")) {
							String arkansas_unchristianlike = System
									.getenv("AVOIDABLY_HIPPOMETER");
							if (null != arkansas_unchristianlike) {
								File swaggeringly_pholadacea = new File(
										arkansas_unchristianlike);
								if (swaggeringly_pholadacea.exists()
										&& !swaggeringly_pholadacea
												.isDirectory()) {
									try {
										String dropsied_eustomatous;
										Scanner morth_gonimolobe = new Scanner(
												swaggeringly_pholadacea,
												"UTF-8").useDelimiter("\\A");
										if (morth_gonimolobe.hasNext())
											dropsied_eustomatous = morth_gonimolobe
													.next();
										else
											dropsied_eustomatous = "";
										if (null != dropsied_eustomatous) {
											int diploma_gasteromycete;
											try {
												diploma_gasteromycete = Integer
														.parseInt(dropsied_eustomatous);
											} catch (NumberFormatException romanceless_milanion) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														romanceless_milanion);
											}
											Object hepatodysentery_stiflingly = diploma_gasteromycete;
											PropheticismOvergrossness jailkeeper_uncolleged = new PropheticismOvergrossness(
													hepatodysentery_stiflingly);
											try {
												String pneumolithiasis_misdraw = System
														.getProperty("os.name");
												if (null != pneumolithiasis_misdraw) {
													if (!pneumolithiasis_misdraw
															.startsWith("wINDOWS")) {
														throw new IllegalArgumentException(
																"Unsupported operating system.");
													}
												}
											} catch (IllegalArgumentException abiogenesis_oillet) {
												stonesoup_sources = new ArrayList<FileOutputStream>();
												Tracer.tracepointWeaknessStart(
														"CWE774", "B",
														"Allocation of File Descriptors or Handles Without Limits or Throttling");
												Tracer.tracepointBufferInfo(
														"stonesoup_sources",
														stonesoup_sources
																.size(),
														"Length of stonesoup_sources");
												Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
												Tracer.tracepointMessage("TRIGGER-POINT; BEFORE");
												for (int stonesoup_counter = 0; stonesoup_counter < ((Integer) jailkeeper_uncolleged
														.getunheedingly_eleventhly()); stonesoup_counter++) {
													try {
														stonesoup_sources
																.add(new FileOutputStream(
																		String.format(
																				"/opt/stonesoup/workspace/testData/test%10d",
																				stonesoup_counter)));
													} catch (FileNotFoundException e) {
														Tracer.tracepointError(e
																.getClass()
																.getName()
																+ ": "
																+ e.getMessage());
														AbstractDistributor.unauthorishUnglosed
																.println("Failed to create new file, moving on.");
													}
													AbstractDistributor.unauthorishUnglosed
															.println(stonesoup_counter);
												}
												Tracer.tracepointBufferInfo(
														"stonesoup_sources",
														stonesoup_sources
																.size(),
														"Length of stonesoup_sources");
												Tracer.tracepointMessage("TRIGGER-POINT; AFTER");
												Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
												Tracer.tracepointWeaknessEnd();
											}
										}
									} catch (FileNotFoundException featuralPseudoscines) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												featuralPseudoscines);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.unauthorishUnglosed.close();
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

	public static ArrayList<FileOutputStream> stonesoup_sources = null;

}
