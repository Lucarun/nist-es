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

    public static interface IStephanionGramophonic {
		public void tarumariEulytine(String interproximal_odontalgic);
	}

	public static class BunglesomeTrifler implements IStephanionGramophonic {
		@Override
		public void tarumariEulytine(String interproximal_odontalgic) {
			Tracer.tracepointWeaknessStart("CWE252", "B", "Unchecked Return");
			try {
				final int STONESOUP_BUFFER_SIZE = 2048;
				String stonesoup_sensitiveFName = interproximal_odontalgic;
				String stonesoup_otherFName = System.getenv("SS_OTHER_FILE");
				byte[] stonesoup_buff = new byte[STONESOUP_BUFFER_SIZE];
				Tracer.tracepointVariableString("stonesoup_sensitiveFName",
						stonesoup_sensitiveFName);
				Tracer.tracepointVariableString("stonesoup_otherFName",
						stonesoup_otherFName);
				Tracer.tracepointBufferInfo("stonesoup_buff",
						stonesoup_buff.length, "Length of stonesoup_buff");
				java.io.InputStream stonesoup_sensitiveFile = new java.io.FileInputStream(
						stonesoup_sensitiveFName);
				java.io.InputStream stonesoup_otherFile = new java.io.FileInputStream(
						stonesoup_otherFName);
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				stonesoup_sensitiveFile.read(stonesoup_buff);
				stonesoup_sensitiveFile.close();
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				stonesoup_otherFile.read(stonesoup_buff);
				stonesoup_otherFile.close();
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				String output_data = new String(stonesoup_buff);
				Tracer.tracepointVariableString("output_data", output_data);
				ShardIdCacheModule.logarithmeticalSwiss.println("Output is:\n"
						+ output_data);
			} catch (java.io.IOException ioe) {
				Tracer.tracepointError(ioe.getClass().getName() + ": "
						+ ioe.getMessage());
				ShardIdCacheModule.logarithmeticalSwiss
						.println("STONESOUP: Error accessing files");
				ioe.printStackTrace(ShardIdCacheModule.logarithmeticalSwiss);
			}
			Tracer.tracepointWeaknessEnd();
		}
	}

	static PrintStream logarithmeticalSwiss = null;
	private static final java.util.concurrent.atomic.AtomicBoolean pudendalThe = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (pudendalThe.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpYHL8WB_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File siniticUnbeauteousness = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!siniticUnbeauteousness.getParentFile().exists()
					&& !siniticUnbeauteousness.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.logarithmeticalSwiss = new PrintStream(
							new FileOutputStream(siniticUnbeauteousness, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException papacyCheiroptera) {
					System.err.printf("Failed to open log file.  %s\n",
							papacyCheiroptera.getMessage());
					ShardIdCacheModule.logarithmeticalSwiss = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							papacyCheiroptera);
				} catch (FileNotFoundException epagomenalBaryecoia) {
					System.err.printf("Failed to open log file.  %s\n",
							epagomenalBaryecoia.getMessage());
					ShardIdCacheModule.logarithmeticalSwiss = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							epagomenalBaryecoia);
				}
				if (ShardIdCacheModule.logarithmeticalSwiss != null) {
					try {
						String draper_piebaldly = System
								.getenv("NINCOMPOOPHOOD_DISBELIEVER");
						if (null != draper_piebaldly) {
							IStephanionGramophonic overclosely_plectognathic = new BunglesomeTrifler();
							overclosely_plectognathic
									.tarumariEulytine(draper_piebaldly);
						}
					} finally {
						ShardIdCacheModule.logarithmeticalSwiss.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
