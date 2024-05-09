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

    static PrintStream plushlikeProacquittal = null;
	private static final java.util.concurrent.atomic.AtomicBoolean unforgetIcicled = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (unforgetIcicled.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpKv3KLQ_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File homogeneousNonimmigrant = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!homogeneousNonimmigrant.getParentFile().exists()
					&& !homogeneousNonimmigrant.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.plushlikeProacquittal = new PrintStream(
							new FileOutputStream(homogeneousNonimmigrant, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException heliographicChoanephora) {
					System.err.printf("Failed to open log file.  %s\n",
							heliographicChoanephora.getMessage());
					ShardIdCacheModule.plushlikeProacquittal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							heliographicChoanephora);
				} catch (FileNotFoundException whiffleryNaphthol) {
					System.err.printf("Failed to open log file.  %s\n",
							whiffleryNaphthol.getMessage());
					ShardIdCacheModule.plushlikeProacquittal = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							whiffleryNaphthol);
				}
				if (ShardIdCacheModule.plushlikeProacquittal != null) {
					try {
						String foreordainment_cellulitis = System
								.getenv("ALLOCHIRIA_UNSOARABLE");
						if (null != foreordainment_cellulitis) {
							char bedeafen_charity;
							try {
								bedeafen_charity = foreordainment_cellulitis
										.charAt(0);
							} catch (IndexOutOfBoundsException trispast_neutrophile) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										trispast_neutrophile);
							}
							char[] sluttishly_caudate = new char[12];
							sluttishly_caudate[3] = bedeafen_charity;
							boolean giantlike_munjistin = false;
							oxboy_halazone: for (int jacksaw_interbanded = 0; jacksaw_interbanded < 10; jacksaw_interbanded++)
								for (int oaktongue_coinheritor = 0; oaktongue_coinheritor < 10; oaktongue_coinheritor++)
									if (jacksaw_interbanded
											* oaktongue_coinheritor == 63) {
										giantlike_munjistin = true;
										break oxboy_halazone;
									}
							Tracer.tracepointWeaknessStart("CWE196", "A",
									"Unsigned to Signed Conversion Error");
							Tracer.tracepointVariableChar("value",
									sluttishly_caudate[3]);
							try {
								Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
								int[] stonesoup_char_counts = stonesoupInitializeCounts((byte) ((char) sluttishly_caudate[3]));
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								for (char counter = 0; counter < sluttishly_caudate[3]; counter++) {
									stonesoup_char_counts[counter] += 1;
								}
								Tracer.tracepointBufferInfo(
										"stonesoup_char_counts",
										stonesoup_char_counts.length,
										"Length of stonesoup_char_counts");
							} catch (RuntimeException e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
								e.printStackTrace(ShardIdCacheModule.plushlikeProacquittal);
								throw e;
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						ShardIdCacheModule.plushlikeProacquittal.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	public static int[] stonesoupInitializeCounts(byte size) {
		Tracer.tracepointLocation(
				"/tmp/tmpKv3KLQ_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
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
