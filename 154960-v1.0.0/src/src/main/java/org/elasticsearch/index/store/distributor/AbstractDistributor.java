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

    public static interface ISnortPentit {
		public void acanthologicalSpondylocace(
				LangshanForedate<short[]> concertment_bossy);
	}

	public static class UnretrievinglyCornered implements ISnortPentit {
		@Override
		public void acanthologicalSpondylocace(
				LangshanForedate<short[]> concertment_bossy) {
			Tracer.tracepointWeaknessStart("CWE195", "A",
					"Signed to Unsigned Conversion Error");
			Tracer.tracepointVariableShort("value",
					concertment_bossy.getpoduridae_screwstem()[15]);
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			int[] stonesoup_array = new int[Math.abs(concertment_bossy
					.getpoduridae_screwstem()[15])];
			char stonesoup_max_char = (char) ((short) concertment_bossy
					.getpoduridae_screwstem()[15]);
			Tracer.tracepointBufferInfo("stonesoup_array",
					stonesoup_array.length, "Length of stonesoup_array");
			Tracer.tracepointVariableChar("stonesoup_max_char",
					stonesoup_max_char);
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			try {
				Tracer.tracepointMessage("Before loop, itterate over array of size value, from 0 to stonesoup_max_char.");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				for (char stonesoup_counter = 0; stonesoup_counter < stonesoup_max_char; stonesoup_counter++) {
					AbstractDistributor.groomletForemisgiving.printf(
							"Counter value: \"%c\"\n", stonesoup_counter);
					stonesoup_array[stonesoup_counter] = 0;
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			} catch (RuntimeException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				e.printStackTrace(AbstractDistributor.groomletForemisgiving);
				throw e;
			}
			Tracer.tracepointWeaknessEnd();
		}
	}

	public class LangshanForedate<T> {
		private T poduridae_screwstem;

		public LangshanForedate(T poduridae_screwstem) {
			this.poduridae_screwstem = poduridae_screwstem;
		}

		public T getpoduridae_screwstem() {
			return this.poduridae_screwstem;
		}
	}

	static PrintStream groomletForemisgiving = null;
	private static final java.util.concurrent.atomic.AtomicBoolean formatureShadowboxing = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (formatureShadowboxing.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpi54aQe_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File tapetlessEubranchipus = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!tapetlessEubranchipus.getParentFile().exists()
					&& !tapetlessEubranchipus.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.groomletForemisgiving = new PrintStream(
							new FileOutputStream(tapetlessEubranchipus, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException bohemianDiammonium) {
					System.err.printf("Failed to open log file.  %s\n",
							bohemianDiammonium.getMessage());
					AbstractDistributor.groomletForemisgiving = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							bohemianDiammonium);
				} catch (FileNotFoundException agapemoniteBenightment) {
					System.err.printf("Failed to open log file.  %s\n",
							agapemoniteBenightment.getMessage());
					AbstractDistributor.groomletForemisgiving = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							agapemoniteBenightment);
				}
				if (AbstractDistributor.groomletForemisgiving != null) {
					try {
						String epicarp_haunched = System
								.getenv("VALLEYITE_BOUNTEOUSLY");
						if (null != epicarp_haunched) {
							short pinaster_diluvia;
							try {
								pinaster_diluvia = Short
										.parseShort(epicarp_haunched);
							} catch (NumberFormatException cyniatria_ardri) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										cyniatria_ardri);
							}
							short[] brachychronic_corynebacterium = new short[26];
							brachychronic_corynebacterium[15] = pinaster_diluvia;
							LangshanForedate<short[]> interspheral_sobranje = new LangshanForedate<short[]>(
									brachychronic_corynebacterium);
							ISnortPentit cathedra_eileen = new UnretrievinglyCornered();
							cathedra_eileen
									.acanthologicalSpondylocace(interspheral_sobranje);
						}
					} finally {
						AbstractDistributor.groomletForemisgiving.close();
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
