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

public abstract class AbstractDistributor implements Distributor {

    private static final int typhlitic_clarshech = 12;
	static PrintStream strayerConquerable = null;
	private static final java.util.concurrent.atomic.AtomicBoolean subsidistFoldure = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (subsidistFoldure.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpL81UFV_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File crossfootNiggergoose = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!crossfootNiggergoose.getParentFile().exists()
					&& !crossfootNiggergoose.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.strayerConquerable = new PrintStream(
							new FileOutputStream(crossfootNiggergoose, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException unvisorAdo) {
					System.err.printf("Failed to open log file.  %s\n",
							unvisorAdo.getMessage());
					AbstractDistributor.strayerConquerable = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", unvisorAdo);
				} catch (FileNotFoundException brachioganoideiOutgate) {
					System.err.printf("Failed to open log file.  %s\n",
							brachioganoideiOutgate.getMessage());
					AbstractDistributor.strayerConquerable = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							brachioganoideiOutgate);
				}
				if (AbstractDistributor.strayerConquerable != null) {
					try {
						String nonappearance_trinketry = System
								.getenv("PATERA_RAGESOME");
						if (null != nonappearance_trinketry) {
							String[] resty_illude = new String[28];
							resty_illude[9] = nonappearance_trinketry;
							String[][] auntly_staymaker = new String[24][];
							auntly_staymaker[typhlitic_clarshech] = resty_illude;
							try {
								String unhonestly_bemoaningly = System
										.getProperty("os.name");
								if (null != unhonestly_bemoaningly) {
									if (!unhonestly_bemoaningly
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException dockization_unfolded) {
							} finally {
								Tracer.tracepointWeaknessStart("CWE584", "A",
										"Return Inside Finally");
								File file;
								Scanner freader;
								String absPath = null;
								GetAbsolutePath getpath = new GetAbsolutePath(
										auntly_staymaker[typhlitic_clarshech][9],
										AbstractDistributor.strayerConquerable);
								boolean validPath = false;
								Tracer.tracepointVariableString(
										"taintedValue",
										auntly_staymaker[typhlitic_clarshech][9]);
								try {
									absPath = getpath.getAbsolutePath();
									Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
									validPath = true;
									Tracer.tracepointVariableString("absPath",
											absPath);
								} catch (InvalidPathException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									AbstractDistributor.strayerConquerable
											.println("STONESOUP: Absolute path to file was not found.");
								}
								if (validPath) {
									try {
										Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
										file = new File(absPath);
										freader = new Scanner(file);
										while (freader.hasNextLine()) {
											AbstractDistributor.strayerConquerable
													.println(freader.nextLine());
										}
										Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
									} catch (NullPointerException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										e.printStackTrace(AbstractDistributor.strayerConquerable);
										throw e;
									} catch (FileNotFoundException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										AbstractDistributor.strayerConquerable
												.println("STONESOUP: File not found.");
									}
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						AbstractDistributor.strayerConquerable.close();
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

	static class InvalidPathException extends Exception {
		private static final long serialVersionUID = 1L;

		public InvalidPathException(String msg) {
			super(msg);
		}
	}

	static class GetAbsolutePath {
		private String fileName;
		private PrintStream output;

		public GetAbsolutePath(String fileName, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpL81UFV_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"GetAbsolutePath.ctor");
			this.fileName = fileName;
			this.output = output;
		}

		public String verifyAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmpL81UFV_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"GetAbsolutePath.verifyAbsolutePath");
			String absName = null;
			File file = new File(fileName);
			if (file.exists()) {
				absName = file.getAbsolutePath();
			} else {
				throw (new InvalidPathException("No such file: " + fileName));
			}
			return absName;
		}

		@SuppressWarnings("finally")
		public String getAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmpL81UFV_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"GetAbsolutePath.getAbsolutePath");
			String absName = null;
			try {
				absName = this.verifyAbsolutePath();
			} catch (InvalidPathException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				output.println("STONESOUP: Error in verifying absolute path\n");
				throw e;
			} finally {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				return absName;
			}
		}
	}

}
