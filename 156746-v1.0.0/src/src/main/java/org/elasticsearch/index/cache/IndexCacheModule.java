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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    static PrintStream diacriticalSemiterete = null;

	public void anchatMolehilly(int glummy_repurification,
			final String lorriker_winterhain) {
		if (glummy_repurification > 10) {
			anchatMolehilly(glummy_repurification++, lorriker_winterhain);
		}
		Tracer.tracepointWeaknessStart("CWE606", "A",
				"Unchecked Input for Loop Condition");
		String valueString = lorriker_winterhain.trim();
		Pattern stonesoup_rel_path_pattern = Pattern.compile("(^|/)\\.\\.?/");
		Matcher rel_path_match = stonesoup_rel_path_pattern
				.matcher(valueString);
		Tracer.tracepointVariableString("value", lorriker_winterhain);
		Tracer.tracepointVariableString("valueString", valueString);
		if (valueString.length() == 0 || valueString.startsWith("/")
				|| rel_path_match.find()) {
			IndexCacheModule.diacriticalSemiterete
					.println("Path traversal identified, discarding request.");
		} else {
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			java.io.File checkedPath = new java.io.File(valueString);
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			while (!checkedPath.isFile()) {
				try {
					IndexCacheModule.diacriticalSemiterete.printf(
							"File \"%s\" does not exist, sleeping...\n",
							valueString);
					Thread.sleep(500);
				} catch (InterruptedException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					IndexCacheModule.diacriticalSemiterete
							.println("Thread interrupted.");
					e.printStackTrace(IndexCacheModule.diacriticalSemiterete);
				}
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			IndexCacheModule.diacriticalSemiterete.println("Found file.");
			IndexCacheModule.diacriticalSemiterete.printf("Reading \"%s\".\n",
					checkedPath.getPath());
			java.io.BufferedReader reader = null;
			try {
				java.io.FileInputStream fis = new java.io.FileInputStream(
						checkedPath);
				reader = new java.io.BufferedReader(
						new java.io.InputStreamReader(fis));
				String line;
				while ((line = reader.readLine()) != null) {
					IndexCacheModule.diacriticalSemiterete.println(line);
				}
			} catch (java.io.FileNotFoundException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				IndexCacheModule.diacriticalSemiterete.printf(
						"File \"%s\" does not exist\n", checkedPath.getPath());
			} catch (java.io.IOException ioe) {
				Tracer.tracepointError(ioe.getClass().getName() + ": "
						+ ioe.getMessage());
				IndexCacheModule.diacriticalSemiterete
						.println("Failed to read file.");
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (java.io.IOException e) {
					IndexCacheModule.diacriticalSemiterete
							.println("STONESOUP: Closing file quietly.");
				}
			}
		}
		Tracer.tracepointWeaknessEnd();
	}

	private static final java.util.concurrent.atomic.AtomicBoolean mesentodermCephalomenia = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (mesentodermCephalomenia.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp6MBDin_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			File exasperatedMisteacher = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!exasperatedMisteacher.getParentFile().exists()
					&& !exasperatedMisteacher.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					IndexCacheModule.diacriticalSemiterete = new PrintStream(
							new FileOutputStream(exasperatedMisteacher, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException ptilopaedicGleamless) {
					System.err.printf("Failed to open log file.  %s\n",
							ptilopaedicGleamless.getMessage());
					IndexCacheModule.diacriticalSemiterete = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							ptilopaedicGleamless);
				} catch (FileNotFoundException shadowistNoneclipsing) {
					System.err.printf("Failed to open log file.  %s\n",
							shadowistNoneclipsing.getMessage());
					IndexCacheModule.diacriticalSemiterete = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							shadowistNoneclipsing);
				}
				if (IndexCacheModule.diacriticalSemiterete != null) {
					try {
						String summage_provincialist = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (summage_provincialist == null
								|| !summage_provincialist.equals("1")) {
							String carter_haruspices = System
									.getenv("OBLITERATIVE_RUSTICITY");
							if (null != carter_haruspices) {
								File cellulomonas_lepidine = new File(
										carter_haruspices);
								if (cellulomonas_lepidine.exists()
										&& !cellulomonas_lepidine.isDirectory()) {
									try {
										final String prereject_rooflet;
										Scanner otalgic_noded = new Scanner(
												cellulomonas_lepidine, "UTF-8")
												.useDelimiter("\\A");
										if (otalgic_noded.hasNext())
											prereject_rooflet = otalgic_noded
													.next();
										else
											prereject_rooflet = "";
										if (null != prereject_rooflet) {
											int inconcluding_polymethylene = 0;
											anchatMolehilly(
													inconcluding_polymethylene,
													prereject_rooflet);
										}
									} catch (FileNotFoundException selenographicalDasya) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												selenographicalDasya);
									}
								}
							}
						}
					} finally {
						IndexCacheModule.diacriticalSemiterete.close();
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
