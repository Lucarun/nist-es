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

    public static interface IBurgHamirostrate {
		public void exterritorialCoadore(String fireboard_trigonodont);
	}

	public static class CreolinAstatine implements IBurgHamirostrate {
		@Override
		public void exterritorialCoadore(String fireboard_trigonodont) {
			Tracer.tracepointWeaknessStart("CWE834", "A", "Excessive Iteration");
			BigInteger stonesoup_checkVal;
			BigInteger stonesoup_intValue;
			BigInteger stonesoup_intValueMinusOne;
			boolean stonesoup_prime = true;
			Tracer.tracepointVariableString("stonesoup_taintedValue",
					fireboard_trigonodont);
			try {
				stonesoup_checkVal = new BigInteger("2");
				stonesoup_intValue = new BigInteger(fireboard_trigonodont);
				stonesoup_intValueMinusOne = stonesoup_intValue
						.subtract(BigInteger.ONE);
				if (stonesoup_intValue.compareTo(BigInteger.ZERO) > 0) {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					for (; stonesoup_checkVal
							.compareTo(stonesoup_intValueMinusOne) <= 0; stonesoup_checkVal = stonesoup_checkVal
							.add(BigInteger.ONE)) {
						if (stonesoup_intValue.mod(stonesoup_checkVal)
								.compareTo(BigInteger.ZERO) == 0) {
							stonesoup_prime = false;
							AbstractDistributor.casuaryPetrosquamosal
									.println("Not Prime");
							break;
						}
					}
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				}
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				AbstractDistributor.casuaryPetrosquamosal
						.println("STONESOUP: Input string is not an integer");
			}
			AbstractDistributor.casuaryPetrosquamosal
					.println("finished evaluating");
			Tracer.tracepointWeaknessEnd();
		}
	}

	static PrintStream casuaryPetrosquamosal = null;
	private static final java.util.concurrent.atomic.AtomicBoolean xerodermaticParoemiography = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (xerodermaticParoemiography.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpmncECg_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File nonrevaluationGedrite = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!nonrevaluationGedrite.getParentFile().exists()
					&& !nonrevaluationGedrite.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.casuaryPetrosquamosal = new PrintStream(
							new FileOutputStream(nonrevaluationGedrite, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException hedenbergiteDeclarative) {
					System.err.printf("Failed to open log file.  %s\n",
							hedenbergiteDeclarative.getMessage());
					AbstractDistributor.casuaryPetrosquamosal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							hedenbergiteDeclarative);
				} catch (FileNotFoundException conservateNeutralization) {
					System.err.printf("Failed to open log file.  %s\n",
							conservateNeutralization.getMessage());
					AbstractDistributor.casuaryPetrosquamosal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							conservateNeutralization);
				}
				if (AbstractDistributor.casuaryPetrosquamosal != null) {
					try {
						String augury_skirty = System
								.getenv("NONDEFALCATION_KEVALIN");
						if (null != augury_skirty) {
							towlineAelurophobia(3, null, null, null,
									augury_skirty, null, null);
						}
					} finally {
						AbstractDistributor.casuaryPetrosquamosal.close();
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

	public void towlineAelurophobia(int hemitriglyphAlcoholmetric,
			String... lavaBirn) {
		String tetremimeralCystadenoma = null;
		int hexamerismUnbeclogged = 0;
		for (hexamerismUnbeclogged = 0; hexamerismUnbeclogged < lavaBirn.length; hexamerismUnbeclogged++) {
			if (hexamerismUnbeclogged == hemitriglyphAlcoholmetric)
				tetremimeralCystadenoma = lavaBirn[hexamerismUnbeclogged];
		}
		IBurgHamirostrate phacocyst_arietation = new CreolinAstatine();
		phacocyst_arietation.exterritorialCoadore(tetremimeralCystadenoma);
	}

}
