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

    public class SpermatophyteOvermix<T> {
		private T dawnward_forepassed;

		public SpermatophyteOvermix(T dawnward_forepassed) {
			this.dawnward_forepassed = dawnward_forepassed;
		}

		public T getdawnward_forepassed() {
			return this.dawnward_forepassed;
		}
	}

	static PrintStream banianImprime = null;
	private static final java.util.concurrent.atomic.AtomicBoolean disvoiceSnowworm = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (disvoiceSnowworm.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpOSthpI_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File drabbetArviculture = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!drabbetArviculture.getParentFile().exists()
					&& !drabbetArviculture.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.banianImprime = new PrintStream(
							new FileOutputStream(drabbetArviculture, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException analyzabilityHydrophoria) {
					System.err.printf("Failed to open log file.  %s\n",
							analyzabilityHydrophoria.getMessage());
					AbstractDistributor.banianImprime = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							analyzabilityHydrophoria);
				} catch (FileNotFoundException evolutionalRomper) {
					System.err.printf("Failed to open log file.  %s\n",
							evolutionalRomper.getMessage());
					AbstractDistributor.banianImprime = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							evolutionalRomper);
				}
				if (AbstractDistributor.banianImprime != null) {
					try {
						String cutch_asherah = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (cutch_asherah == null || !cutch_asherah.equals("1")) {
							String darwinize_cerebroma = System
									.getenv("HARAKEKE_EUPHONIOUS");
							if (null != darwinize_cerebroma) {
								File complementary_bunker = new File(
										darwinize_cerebroma);
								if (complementary_bunker.exists()
										&& !complementary_bunker.isDirectory()) {
									try {
										String rucervus_taurotragus;
										Scanner unthievish_deprive = new Scanner(
												complementary_bunker, "UTF-8")
												.useDelimiter("\\A");
										if (unthievish_deprive.hasNext())
											rucervus_taurotragus = unthievish_deprive
													.next();
										else
											rucervus_taurotragus = "";
										if (null != rucervus_taurotragus) {
											Object unapprovably_brushite = rucervus_taurotragus;
											SpermatophyteOvermix<Object> softheartedness_marbleizer = new SpermatophyteOvermix<Object>(
													unapprovably_brushite);
											int similar_josepha = 0;
											while (true) {
												similar_josepha++;
												if (similar_josepha >= 3000)
													break;
											}
											Tracer.tracepointWeaknessStart(
													"CWE253", "A",
													"Incorrect Check of Function Return Value");
											int location = ((String) softheartedness_marbleizer
													.getdawnward_forepassed())
													.indexOf('.');
											Tracer.tracepointVariableString(
													"stonesoup_tainted_buff",
													((String) softheartedness_marbleizer
															.getdawnward_forepassed()));
											Tracer.tracepointVariableInt(
													"location", location);
											Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
											if (location != 0) {
												Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
												String substring;
												try {
													Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
													substring = ((String) softheartedness_marbleizer
															.getdawnward_forepassed())
															.substring(location);
													Tracer.tracepointVariableString(
															"substring",
															substring);
													Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
												} catch (RuntimeException e) {
													Tracer.tracepointError(e
															.getClass()
															.getName()
															+ ": "
															+ e.getMessage());
													e.printStackTrace(AbstractDistributor.banianImprime);
													throw e;
												}
												AbstractDistributor.banianImprime
														.println("Substring beginning with '.' is \""
																+ substring
																+ "\"\n");
											} else {
												Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
												AbstractDistributor.banianImprime
														.println("'.' appears at start of line\n");
											}
											Tracer.tracepointWeaknessEnd();
										}
									} catch (FileNotFoundException molybdenousTerebratuline) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												molybdenousTerebratuline);
									}
								}
							}
						}
					} finally {
						AbstractDistributor.banianImprime.close();
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
