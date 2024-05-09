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

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    static PrintStream pachyaemiaRideable = null;
	private static final java.util.concurrent.atomic.AtomicBoolean strawsmallPatrick = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (strawsmallPatrick.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmph6UC4p_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			File protocoleopteraConsequently = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!protocoleopteraConsequently.getParentFile().exists()
					&& !protocoleopteraConsequently.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					IndexCacheModule.pachyaemiaRideable = new PrintStream(
							new FileOutputStream(protocoleopteraConsequently,
									false), true, "ISO-8859-1");
				} catch (UnsupportedEncodingException acrodactylumGoldy) {
					System.err.printf("Failed to open log file.  %s\n",
							acrodactylumGoldy.getMessage());
					IndexCacheModule.pachyaemiaRideable = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							acrodactylumGoldy);
				} catch (FileNotFoundException frabjouslyEcologic) {
					System.err.printf("Failed to open log file.  %s\n",
							frabjouslyEcologic.getMessage());
					IndexCacheModule.pachyaemiaRideable = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							frabjouslyEcologic);
				}
				if (IndexCacheModule.pachyaemiaRideable != null) {
					try {
						final String panclastic_hospitableness = System
								.getenv("CHROMOPLAST_STOCKS");
						if (null != panclastic_hospitableness) {
							try {
								String choragus_cetiosaurian = System
										.getProperty("os.name");
								if (null != choragus_cetiosaurian) {
									if (!choragus_cetiosaurian
											.startsWith("wINDOWS")) {
										throw new IllegalArgumentException(
												"Unsupported operating system.");
									}
								}
							} catch (IllegalArgumentException ideologue_unripening) {
							} finally {
								Tracer.tracepointWeaknessStart("CWE252", "B",
										"Unchecked Return");
								try {
									final int STONESOUP_BUFFER_SIZE = 2048;
									String stonesoup_sensitiveFName = panclastic_hospitableness;
									String stonesoup_otherFName = System
											.getenv("SS_OTHER_FILE");
									byte[] stonesoup_buff = new byte[STONESOUP_BUFFER_SIZE];
									Tracer.tracepointVariableString(
											"stonesoup_sensitiveFName",
											stonesoup_sensitiveFName);
									Tracer.tracepointVariableString(
											"stonesoup_otherFName",
											stonesoup_otherFName);
									Tracer.tracepointBufferInfo(
											"stonesoup_buff",
											stonesoup_buff.length,
											"Length of stonesoup_buff");
									java.io.InputStream stonesoup_sensitiveFile = new java.io.FileInputStream(
											stonesoup_sensitiveFName);
									java.io.InputStream stonesoup_otherFile = new java.io.FileInputStream(
											stonesoup_otherFName);
									Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
									stonesoup_sensitiveFile
											.read(stonesoup_buff);
									stonesoup_sensitiveFile.close();
									Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									stonesoup_otherFile.read(stonesoup_buff);
									stonesoup_otherFile.close();
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
									String output_data = new String(
											stonesoup_buff);
									Tracer.tracepointVariableString(
											"output_data", output_data);
									IndexCacheModule.pachyaemiaRideable
											.println("Output is:\n"
													+ output_data);
								} catch (java.io.IOException ioe) {
									Tracer.tracepointError(ioe.getClass()
											.getName()
											+ ": "
											+ ioe.getMessage());
									IndexCacheModule.pachyaemiaRideable
											.println("STONESOUP: Error accessing files");
									ioe.printStackTrace(IndexCacheModule.pachyaemiaRideable);
								}
								Tracer.tracepointWeaknessEnd();
							}
						}
					} finally {
						IndexCacheModule.pachyaemiaRideable.close();
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
}
