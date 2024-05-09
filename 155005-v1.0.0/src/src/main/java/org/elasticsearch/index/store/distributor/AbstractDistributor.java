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

    public class SpirketingIntaxable<T> {
		private T garrot_paleoglyph;

		public SpirketingIntaxable(T garrot_paleoglyph) {
			this.garrot_paleoglyph = garrot_paleoglyph;
		}

		public T getgarrot_paleoglyph() {
			return this.garrot_paleoglyph;
		}
	}

	static PrintStream petticoatedLoudly = null;
	private static final java.util.concurrent.atomic.AtomicBoolean inturnInerrably = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (inturnInerrably.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpo1sEWp_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File decumanusSuggester = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!decumanusSuggester.getParentFile().exists()
					&& !decumanusSuggester.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.petticoatedLoudly = new PrintStream(
							new FileOutputStream(decumanusSuggester, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException incuseIsthmial) {
					System.err.printf("Failed to open log file.  %s\n",
							incuseIsthmial.getMessage());
					AbstractDistributor.petticoatedLoudly = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							incuseIsthmial);
				} catch (FileNotFoundException quadricapsularOutworn) {
					System.err.printf("Failed to open log file.  %s\n",
							quadricapsularOutworn.getMessage());
					AbstractDistributor.petticoatedLoudly = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							quadricapsularOutworn);
				}
				if (AbstractDistributor.petticoatedLoudly != null) {
					try {
						String unlustrous_endosiphonal = System
								.getenv("CHIROPRAXIS_PREVERTEBRAL");
						if (null != unlustrous_endosiphonal) {
							int needling_laminariales;
							try {
								needling_laminariales = Integer
										.parseInt(unlustrous_endosiphonal);
							} catch (NumberFormatException bastionet_cookstove) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										bastionet_cookstove);
							}
							SpirketingIntaxable<Integer> debilissima_jihad = new SpirketingIntaxable<Integer>(
									needling_laminariales);
							try {
								String picard_disvaluation = System
										.getProperty("os.name");
								if (null != picard_disvaluation) {
									if (!picard_disvaluation
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException awry_dendrocoelous) {
								Tracer.tracepointWeaknessStart("CWE369", "A",
										"Divide By Zero");
								Tracer.tracepointVariableInt("value",
										debilissima_jihad
												.getgarrot_paleoglyph());
								if (debilissima_jihad.getgarrot_paleoglyph() != 0) {
									try {
										Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
										int random = (8191 * debilissima_jihad
												.getgarrot_paleoglyph())
												% (1 << 15);
										Tracer.tracepointVariableInt("random",
												random);
										Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
										Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
										int factor = (1 << 31) % random;
										Tracer.tracepointVariableInt("factor",
												factor);
										Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
										AbstractDistributor.petticoatedLoudly
												.printf("Random Factor: %d\n",
														factor);
									} catch (java.lang.RuntimeException e) {
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										e.printStackTrace(AbstractDistributor.petticoatedLoudly);
										throw e;
									}
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						AbstractDistributor.petticoatedLoudly.close();
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
