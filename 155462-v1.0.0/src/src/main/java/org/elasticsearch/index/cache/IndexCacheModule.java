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

package org.elasticsearch.index.cache;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.cache.docset.DocSetCacheModule;
import org.elasticsearch.index.cache.filter.FilterCacheModule;
import org.elasticsearch.index.cache.id.IdCacheModule;
import org.elasticsearch.index.cache.query.parser.QueryParserCacheModule;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    public class InaidableUnligable {
		private int avid_claptrap;

		public InaidableUnligable(int avid_claptrap) {
			this.avid_claptrap = avid_claptrap;
		}

		public int getavid_claptrap() {
			return this.avid_claptrap;
		}
	}

	static PrintStream kinshipDwarfy = null;
	private static final java.util.concurrent.atomic.AtomicBoolean stockannetUnorganizedly = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (stockannetUnorganizedly.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp1hyqOy_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			File valliculaDiamidogen = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!valliculaDiamidogen.getParentFile().exists()
					&& !valliculaDiamidogen.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					IndexCacheModule.kinshipDwarfy = new PrintStream(
							new FileOutputStream(valliculaDiamidogen, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException napoleoniteOpiomaniac) {
					System.err.printf("Failed to open log file.  %s\n",
							napoleoniteOpiomaniac.getMessage());
					IndexCacheModule.kinshipDwarfy = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							napoleoniteOpiomaniac);
				} catch (FileNotFoundException dellenitePhalangologist) {
					System.err.printf("Failed to open log file.  %s\n",
							dellenitePhalangologist.getMessage());
					IndexCacheModule.kinshipDwarfy = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							dellenitePhalangologist);
				}
				if (IndexCacheModule.kinshipDwarfy != null) {
					try {
						String immutilate_clitoris = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (immutilate_clitoris == null
								|| !immutilate_clitoris.equals("1")) {
							String oxydendrum_zoographer = System
									.getenv("CLAMB_CENSURELESS");
							if (null != oxydendrum_zoographer) {
								File albuminate_sesquisulphide = new File(
										oxydendrum_zoographer);
								if (albuminate_sesquisulphide.exists()
										&& !albuminate_sesquisulphide
												.isDirectory()) {
									try {
										String inclinational_suctoria;
										Scanner acetoxyl_driverless = new Scanner(
												albuminate_sesquisulphide,
												"UTF-8").useDelimiter("\\A");
										if (acetoxyl_driverless.hasNext())
											inclinational_suctoria = acetoxyl_driverless
													.next();
										else
											inclinational_suctoria = "";
										if (null != inclinational_suctoria) {
											int moorbird_opisthosomal;
											try {
												moorbird_opisthosomal = Integer
														.parseInt(inclinational_suctoria);
											} catch (NumberFormatException chrysopoeia_consistently) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														chrysopoeia_consistently);
											}
											InaidableUnligable overincrust_replicatory = new InaidableUnligable(
													moorbird_opisthosomal);
											PrerejectionUnfairly tablature_pregust = new PrerejectionUnfairly();
											tablature_pregust
													.unrequiterArchae(overincrust_replicatory);
										}
									} catch (FileNotFoundException mortaryGuha) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												mortaryGuha);
									}
								}
							}
						}
					} finally {
						IndexCacheModule.kinshipDwarfy.close();
					}
				}
			}
		}
		new FilterCacheModule(settings).configure(binder());
        new IdCacheModule(settings).configure(binder());
        new QueryParserCacheModule(settings).configure(binder());
        new DocSetCacheModule(settings).configure(binder());

        bind(IndexCache.class).asEagerSingleton();
    }

	public static class PrerejectionUnfairly {
		public void unrequiterArchae(InaidableUnligable flare_gossamery) {
			HawkeyePrefamiliarly echinacea_adjuratory = new HawkeyePrefamiliarly();
			echinacea_adjuratory.parabaptizationComprest(flare_gossamery);
		}
	}

	public static class HawkeyePrefamiliarly {
		public void parabaptizationComprest(
				InaidableUnligable reactionaryism_metastigmate) {
			Tracer.tracepointWeaknessStart("CWE369", "A", "Divide By Zero");
			Tracer.tracepointVariableInt("value",
					reactionaryism_metastigmate.getavid_claptrap());
			if (reactionaryism_metastigmate.getavid_claptrap() != 0) {
				try {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					int random = (8191 * reactionaryism_metastigmate
							.getavid_claptrap()) % (1 << 15);
					Tracer.tracepointVariableInt("random", random);
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					int factor = (1 << 31) % random;
					Tracer.tracepointVariableInt("factor", factor);
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					IndexCacheModule.kinshipDwarfy.printf(
							"Random Factor: %d\n", factor);
				} catch (java.lang.RuntimeException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					e.printStackTrace(IndexCacheModule.kinshipDwarfy);
					throw e;
				}
			}
			Tracer.tracepointWeaknessEnd();
		}
	}
}
