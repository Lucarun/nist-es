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

package org.elasticsearch.index.cache.id;

import org.elasticsearch.common.inject.AbstractModule;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    public class ShaktiUnshamable {
		private Object pioxe_thereanent;

		public ShaktiUnshamable(Object pioxe_thereanent) {
			this.pioxe_thereanent = pioxe_thereanent;
		}

		public Object getpioxe_thereanent() {
			return this.pioxe_thereanent;
		}
	}

	static PrintStream nonappraisalUnapprenticed = null;
	private static final java.util.concurrent.atomic.AtomicBoolean bradynosusMagellanian = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (bradynosusMagellanian.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpIHb8Eq_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File epipharyngealBeguileful = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!epipharyngealBeguileful.getParentFile().exists()
					&& !epipharyngealBeguileful.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.nonappraisalUnapprenticed = new PrintStream(
							new FileOutputStream(epipharyngealBeguileful, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException recaptionStog) {
					System.err.printf("Failed to open log file.  %s\n",
							recaptionStog.getMessage());
					ShardIdCacheModule.nonappraisalUnapprenticed = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							recaptionStog);
				} catch (FileNotFoundException maladaptationHornety) {
					System.err.printf("Failed to open log file.  %s\n",
							maladaptationHornety.getMessage());
					ShardIdCacheModule.nonappraisalUnapprenticed = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							maladaptationHornety);
				}
				if (ShardIdCacheModule.nonappraisalUnapprenticed != null) {
					try {
						String kulak_criticizingly = System
								.getenv("CHILDLIKENESS_SPORADOSIDERITE");
						if (null != kulak_criticizingly) {
							int cotorture_rechristen;
							try {
								cotorture_rechristen = Integer
										.parseInt(kulak_criticizingly);
							} catch (NumberFormatException honk_iodinophilous) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										honk_iodinophilous);
							}
							Object labiograph_nonunionist = cotorture_rechristen;
							ShaktiUnshamable laver_gypsyweed = new ShaktiUnshamable(
									labiograph_nonunionist);
							boolean seidlitz_navicert = false;
							caressively_undecayedness: for (int antipatriotic_tetraspermous = 0; antipatriotic_tetraspermous < 10; antipatriotic_tetraspermous++)
								for (int globosity_remodelment = 0; globosity_remodelment < 10; globosity_remodelment++)
									if (antipatriotic_tetraspermous
											* globosity_remodelment == 63) {
										seidlitz_navicert = true;
										break caressively_undecayedness;
									}
							Tracer.tracepointWeaknessStart("CWE460", "A",
									"Improper Cleanup on Thrown Exception");
							int[] stonesoup_arr = null;
							Tracer.tracepointVariableInt("size",
									((Integer) laver_gypsyweed
											.getpioxe_thereanent()));
							Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
							try {
								ShardIdCacheModule.nonappraisalUnapprenticed
										.printf("Allocating array of size %d\n",
												((Integer) laver_gypsyweed
														.getpioxe_thereanent()));
								stonesoup_arr = new int[((Integer) laver_gypsyweed
										.getpioxe_thereanent())];
							} catch (java.lang.OutOfMemoryError e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
								stonesoup_arr = new int[100];
							}
							Tracer.tracepointBufferInfo("stonesoup_arr",
									stonesoup_arr.length,
									"Length of stonesoup_arr");
							Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
							try {
								Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
								int i = ((Integer) laver_gypsyweed
										.getpioxe_thereanent()) - 1;
								do {
									stonesoup_arr[i--] = i;
								} while (i > 0);
								Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
							} catch (RuntimeException e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
								e.printStackTrace(ShardIdCacheModule.nonappraisalUnapprenticed);
								throw e;
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						ShardIdCacheModule.nonappraisalUnapprenticed.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
