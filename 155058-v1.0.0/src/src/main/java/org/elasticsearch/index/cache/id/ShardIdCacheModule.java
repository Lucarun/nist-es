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

    static PrintStream skipperyGlonoin = null;
	private static final java.util.concurrent.atomic.AtomicBoolean sophisticateMountainet = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (sophisticateMountainet.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpkkLCAS_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File moongladeMusicale = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!moongladeMusicale.getParentFile().exists()
					&& !moongladeMusicale.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.skipperyGlonoin = new PrintStream(
							new FileOutputStream(moongladeMusicale, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException unquaddedPetalodontidae) {
					System.err.printf("Failed to open log file.  %s\n",
							unquaddedPetalodontidae.getMessage());
					ShardIdCacheModule.skipperyGlonoin = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unquaddedPetalodontidae);
				} catch (FileNotFoundException bloomsburyPeregrinatory) {
					System.err.printf("Failed to open log file.  %s\n",
							bloomsburyPeregrinatory.getMessage());
					ShardIdCacheModule.skipperyGlonoin = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							bloomsburyPeregrinatory);
				}
				if (ShardIdCacheModule.skipperyGlonoin != null) {
					try {
						final String diaderm_tenpence = System
								.getenv("VARVE_SQUATTISH");
						if (null != diaderm_tenpence) {
							final short pachypterous_unfunniness;
							try {
								pachypterous_unfunniness = Short
										.parseShort(diaderm_tenpence);
							} catch (NumberFormatException fothergilla_alcoholicity) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										fothergilla_alcoholicity);
							}
							try {
								String autoradiography_capewise = System
										.getProperty("os.name");
								if (null != autoradiography_capewise) {
									if (!autoradiography_capewise
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException bowyer_pangenesis) {
							} finally {
								Tracer.tracepointWeaknessStart("CWE191", "A",
										"Integer Underflow (Wrap or Wraparound)");
								short stonesoup_checked_value = pachypterous_unfunniness;
								Tracer.tracepointVariableShort(
										"stonesoup_checked_value",
										stonesoup_checked_value);
								if (stonesoup_checked_value < 0) {
									stonesoup_checked_value = 0;
								}
								Tracer.tracepointVariableShort(
										"stonesoup_checked_value",
										stonesoup_checked_value);
								Short[] stonesoup_some_values = new Short[] {
										0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
										12, 13, 14, 15, 16, 17, 18, 19, 20 };
								short stonesoup_counter = -20;
								short stonesoup_offset = 40;
								Tracer.tracepointBufferInfo(
										"stonesoup_some_values",
										stonesoup_some_values.length,
										"Length of stonesoup_some_values");
								Tracer.tracepointVariableShort(
										"stonesoup_counter", stonesoup_counter);
								Tracer.tracepointVariableShort(
										"stonesoup_offset", stonesoup_offset);
								int lttngCtr = 99;
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
								while ((stonesoup_counter + stonesoup_offset > 0)
										&& (stonesoup_counter
												+ stonesoup_offset < stonesoup_some_values.length)) {
									ShardIdCacheModule.skipperyGlonoin
											.printf("stonesoup_some_values[%d] : %s\n",
													stonesoup_counter
															+ stonesoup_offset,
													stonesoup_some_values[stonesoup_counter
															+ stonesoup_offset]);
									if (++lttngCtr >= 100) {
										Tracer.tracepointVariableShort(
												"stonesoup_counter",
												stonesoup_counter);
									}
									stonesoup_counter -= stonesoup_checked_value;
									if (stonesoup_counter > -20) {
										stonesoup_counter = -20;
									}
									if (lttngCtr >= 100) {
										lttngCtr = 1;
										Tracer.tracepointVariableShort(
												"stonesoup_counter",
												stonesoup_counter);
									}
								}
								Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								Tracer.tracepointBufferInfo(
										"stonesoup_some_values",
										stonesoup_some_values.length,
										"Length of stonesoup_some_values");
								Tracer.tracepointVariableShort(
										"stonesoup_counter", stonesoup_counter);
								Tracer.tracepointVariableShort(
										"stonesoup_offset", stonesoup_offset);
								ShardIdCacheModule.skipperyGlonoin
										.println("finished evaluating");
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						ShardIdCacheModule.skipperyGlonoin.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
