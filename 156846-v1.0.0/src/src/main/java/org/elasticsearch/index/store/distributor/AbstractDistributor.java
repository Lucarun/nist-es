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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDistributor implements Distributor {

    static PrintStream jaglaCrocoisite = null;
	private static final java.util.concurrent.atomic.AtomicBoolean unqualifiableScribacious = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (unqualifiableScribacious.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpcVEWak_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File subdeputyPhototherapist = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!subdeputyPhototherapist.getParentFile().exists()
					&& !subdeputyPhototherapist.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.jaglaCrocoisite = new PrintStream(
							new FileOutputStream(subdeputyPhototherapist, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException ectocarpaceaeOvaloid) {
					System.err.printf("Failed to open log file.  %s\n",
							ectocarpaceaeOvaloid.getMessage());
					AbstractDistributor.jaglaCrocoisite = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							ectocarpaceaeOvaloid);
				} catch (FileNotFoundException motacillidRuby) {
					System.err.printf("Failed to open log file.  %s\n",
							motacillidRuby.getMessage());
					AbstractDistributor.jaglaCrocoisite = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							motacillidRuby);
				}
				if (AbstractDistributor.jaglaCrocoisite != null) {
					try {
						final String lepralian_unaging = System
								.getenv("URETHANE_OVERPOPULOUS");
						if (null != lepralian_unaging) {
							final Object campephagine_friar = lepralian_unaging;
							try {
								String bettering_bygone = System
										.getProperty("os.name");
								if (null != bettering_bygone) {
									if (!bettering_bygone.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException nonportable_raphidiidae) {
								Tracer.tracepointWeaknessStart("CWE606", "A",
										"Unchecked Input for Loop Condition");
								String valueString = ((String) campephagine_friar)
										.trim();
								Pattern stonesoup_rel_path_pattern = Pattern
										.compile("(^|/)\\.\\.?/");
								Matcher rel_path_match = stonesoup_rel_path_pattern
										.matcher(valueString);
								Tracer.tracepointVariableString("value",
										((String) campephagine_friar));
								Tracer.tracepointVariableString("valueString",
										valueString);
								if (valueString.length() == 0
										|| valueString.startsWith("/")
										|| rel_path_match.find()) {
									AbstractDistributor.jaglaCrocoisite
											.println("Path traversal identified, discarding request.");
								} else {
									Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
									java.io.File checkedPath = new java.io.File(
											valueString);
									Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									while (!checkedPath.isFile()) {
										try {
											AbstractDistributor.jaglaCrocoisite
													.printf("File \"%s\" does not exist, sleeping...\n",
															valueString);
											Thread.sleep(500);
										} catch (InterruptedException e) {
											Tracer.tracepointError(e.getClass()
													.getName()
													+ ": "
													+ e.getMessage());
											AbstractDistributor.jaglaCrocoisite
													.println("Thread interrupted.");
											e.printStackTrace(AbstractDistributor.jaglaCrocoisite);
										}
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
									AbstractDistributor.jaglaCrocoisite
											.println("Found file.");
									AbstractDistributor.jaglaCrocoisite.printf(
											"Reading \"%s\".\n",
											checkedPath.getPath());
									java.io.BufferedReader reader = null;
									try {
										java.io.FileInputStream fis = new java.io.FileInputStream(
												checkedPath);
										reader = new java.io.BufferedReader(
												new java.io.InputStreamReader(
														fis));
										String line;
										while ((line = reader.readLine()) != null) {
											AbstractDistributor.jaglaCrocoisite
													.println(line);
										}
									} catch (java.io.FileNotFoundException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										AbstractDistributor.jaglaCrocoisite
												.printf("File \"%s\" does not exist\n",
														checkedPath.getPath());
									} catch (java.io.IOException ioe) {
										Tracer.tracepointError(ioe.getClass()
												.getName()
												+ ": "
												+ ioe.getMessage());
										AbstractDistributor.jaglaCrocoisite
												.println("Failed to read file.");
									} finally {
										try {
											if (reader != null) {
												reader.close();
											}
										} catch (java.io.IOException e) {
											AbstractDistributor.jaglaCrocoisite
													.println("STONESOUP: Closing file quietly.");
										}
									}
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						AbstractDistributor.jaglaCrocoisite.close();
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
