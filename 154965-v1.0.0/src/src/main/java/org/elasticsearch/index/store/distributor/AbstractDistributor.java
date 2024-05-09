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

public abstract class AbstractDistributor implements Distributor {

    static PrintStream shotmakerMechanality = null;
	private static final java.util.concurrent.atomic.AtomicBoolean gypsyishZoic = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (gypsyishZoic.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpX5rTM9_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File romantistNivicolous = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!romantistNivicolous.getParentFile().exists()
					&& !romantistNivicolous.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.shotmakerMechanality = new PrintStream(
							new FileOutputStream(romantistNivicolous, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException dioptrometerUltraeligible) {
					System.err.printf("Failed to open log file.  %s\n",
							dioptrometerUltraeligible.getMessage());
					AbstractDistributor.shotmakerMechanality = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							dioptrometerUltraeligible);
				} catch (FileNotFoundException dissentiouslyPneumograph) {
					System.err.printf("Failed to open log file.  %s\n",
							dissentiouslyPneumograph.getMessage());
					AbstractDistributor.shotmakerMechanality = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							dissentiouslyPneumograph);
				}
				if (AbstractDistributor.shotmakerMechanality != null) {
					try {
						String unfarming_sulkiness = System
								.getenv("CHOLESTERIC_STUPEFIEDNESS");
						if (null != unfarming_sulkiness) {
							char elaeomargaric_prerupt;
							try {
								elaeomargaric_prerupt = unfarming_sulkiness
										.charAt(0);
							} catch (IndexOutOfBoundsException untallowed_bemajesty) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										untallowed_bemajesty);
							}
							Object autodrainage_controller = elaeomargaric_prerupt;
							unctiousQuartation(3, null, null, null,
									autodrainage_controller, null, null);
						}
					} finally {
						AbstractDistributor.shotmakerMechanality.close();
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

	public void unctiousQuartation(int bisharinSoured,
			Object... bluebillObedientially) {
		Object chytridiumPate = null;
		int depressedAmbrology = 0;
		for (depressedAmbrology = 0; depressedAmbrology < bluebillObedientially.length; depressedAmbrology++) {
			if (depressedAmbrology == bisharinSoured)
				chytridiumPate = bluebillObedientially[depressedAmbrology];
		}
		boolean goldstone_intraglandular = false;
		pokable_sprightliness: for (int bilharziasis_acidulent = 0; bilharziasis_acidulent < 10; bilharziasis_acidulent++)
			for (int amoebicide_reintroduction = 0; amoebicide_reintroduction < 10; amoebicide_reintroduction++)
				if (bilharziasis_acidulent * amoebicide_reintroduction == 63) {
					goldstone_intraglandular = true;
					break pokable_sprightliness;
				}
		Tracer.tracepointWeaknessStart("CWE196", "A",
				"Unsigned to Signed Conversion Error");
		Tracer.tracepointVariableChar("value", ((Character) chytridiumPate));
		try {
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			int[] stonesoup_char_counts = stonesoupInitializeCounts((byte) ((char) ((Character) chytridiumPate)));
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			for (char counter = 0; counter < ((Character) chytridiumPate); counter++) {
				stonesoup_char_counts[counter] += 1;
			}
			Tracer.tracepointBufferInfo("stonesoup_char_counts",
					stonesoup_char_counts.length,
					"Length of stonesoup_char_counts");
		} catch (RuntimeException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			e.printStackTrace(AbstractDistributor.shotmakerMechanality);
			throw e;
		}
		Tracer.tracepointWeaknessEnd();
	}

	public static int[] stonesoupInitializeCounts(byte size) {
		Tracer.tracepointLocation(
				"/tmp/tmpX5rTM9_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
				"stonesoupInitializeCounts");
		Tracer.tracepointVariableByte("size", size);
		if (size == 0) {
			return null;
		}
		Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
		int[] result = new int[size];
		Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		Tracer.tracepointBufferInfo("result", result.length, "Length of result");
		for (int ii = 0; ii < result.length; ii++) {
			result[ii] = 0;
		}
		return result;
	}

}
