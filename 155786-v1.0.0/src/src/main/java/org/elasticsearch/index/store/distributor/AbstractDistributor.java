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
import java.math.BigInteger;

public abstract class AbstractDistributor implements Distributor {

    private static final int considerator_yang = 2;
	static PrintStream fitfulnessChillily = null;
	private static final java.util.concurrent.atomic.AtomicBoolean reedbirdParbuckle = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (reedbirdParbuckle.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp3hEotS_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File flavorfulBalladier = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!flavorfulBalladier.getParentFile().exists()
					&& !flavorfulBalladier.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.fitfulnessChillily = new PrintStream(
							new FileOutputStream(flavorfulBalladier, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException unrevengingUropsile) {
					System.err.printf("Failed to open log file.  %s\n",
							unrevengingUropsile.getMessage());
					AbstractDistributor.fitfulnessChillily = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unrevengingUropsile);
				} catch (FileNotFoundException lumpinessSerialize) {
					System.err.printf("Failed to open log file.  %s\n",
							lumpinessSerialize.getMessage());
					AbstractDistributor.fitfulnessChillily = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							lumpinessSerialize);
				}
				if (AbstractDistributor.fitfulnessChillily != null) {
					try {
						String keratectomy_homespun = System
								.getenv("OTTINGKAR_COUNTERACTIVITY");
						if (null != keratectomy_homespun) {
							Object ultrafidian_betoyan = keratectomy_homespun;
							Object[] quashy_ula = new Object[10];
							quashy_ula[considerator_yang] = ultrafidian_betoyan;
							int philodendron_crookfingered = 0;
							while (true) {
								philodendron_crookfingered++;
								if (philodendron_crookfingered >= 3000)
									break;
							}
							Tracer.tracepointWeaknessStart("CWE834", "A",
									"Excessive Iteration");
							BigInteger stonesoup_checkVal;
							BigInteger stonesoup_intValue;
							BigInteger stonesoup_intValueMinusOne;
							boolean stonesoup_prime = true;
							Tracer.tracepointVariableString(
									"stonesoup_taintedValue",
									((String) quashy_ula[considerator_yang]));
							try {
								stonesoup_checkVal = new BigInteger("2");
								stonesoup_intValue = new BigInteger(
										((String) quashy_ula[considerator_yang]));
								stonesoup_intValueMinusOne = stonesoup_intValue
										.subtract(BigInteger.ONE);
								if (stonesoup_intValue
										.compareTo(BigInteger.ZERO) > 0) {
									Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									for (; stonesoup_checkVal
											.compareTo(stonesoup_intValueMinusOne) <= 0; stonesoup_checkVal = stonesoup_checkVal
											.add(BigInteger.ONE)) {
										if (stonesoup_intValue.mod(
												stonesoup_checkVal).compareTo(
												BigInteger.ZERO) == 0) {
											stonesoup_prime = false;
											AbstractDistributor.fitfulnessChillily
													.println("Not Prime");
											break;
										}
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
									Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								}
							} catch (NumberFormatException e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
								AbstractDistributor.fitfulnessChillily
										.println("STONESOUP: Input string is not an integer");
							}
							AbstractDistributor.fitfulnessChillily
									.println("finished evaluating");
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						AbstractDistributor.fitfulnessChillily.close();
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
