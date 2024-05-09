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
import java.util.Scanner;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    public class PensacolaBally<T> {
		private T theyll_cardiopathy;

		public PensacolaBally(T theyll_cardiopathy) {
			this.theyll_cardiopathy = theyll_cardiopathy;
		}

		public T gettheyll_cardiopathy() {
			return this.theyll_cardiopathy;
		}
	}

	static PrintStream houseAntibubonic = null;
	private static final java.util.concurrent.atomic.AtomicBoolean forbearingSanguinary = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (forbearingSanguinary.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpFoTbSd_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File blastoporicButoxyl = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!blastoporicButoxyl.getParentFile().exists()
					&& !blastoporicButoxyl.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.houseAntibubonic = new PrintStream(
							new FileOutputStream(blastoporicButoxyl, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException stingyUnsubduedness) {
					System.err.printf("Failed to open log file.  %s\n",
							stingyUnsubduedness.getMessage());
					ShardIdCacheModule.houseAntibubonic = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							stingyUnsubduedness);
				} catch (FileNotFoundException rudyReobtainment) {
					System.err.printf("Failed to open log file.  %s\n",
							rudyReobtainment.getMessage());
					ShardIdCacheModule.houseAntibubonic = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							rudyReobtainment);
				}
				if (ShardIdCacheModule.houseAntibubonic != null) {
					try {
						String subcriminal_hiro = System
								.getenv("THORO_CLITORIA");
						if (null != subcriminal_hiro) {
							String[] mimsey_gudgeon = new String[31];
							mimsey_gudgeon[4] = subcriminal_hiro;
							PensacolaBally<String[]> unwifelike_opisthosomal = new PensacolaBally<String[]>(
									mimsey_gudgeon);
							boolean bradyseism_nonachievement = false;
							itamalic_gressoria: for (int deionize_tittie = 0; deionize_tittie < 10; deionize_tittie++)
								for (int tubman_aegyrite = 0; tubman_aegyrite < 10; tubman_aegyrite++)
									if (deionize_tittie * tubman_aegyrite == 63) {
										bradyseism_nonachievement = true;
										break itamalic_gressoria;
									}
							Tracer.tracepointWeaknessStart("CWE584", "A",
									"Return Inside Finally");
							File file;
							Scanner freader;
							String absPath = null;
							GetAbsolutePath getpath = new GetAbsolutePath(
									unwifelike_opisthosomal
											.gettheyll_cardiopathy()[4],
									ShardIdCacheModule.houseAntibubonic);
							boolean validPath = false;
							Tracer.tracepointVariableString("taintedValue",
									unwifelike_opisthosomal
											.gettheyll_cardiopathy()[4]);
							try {
								absPath = getpath.getAbsolutePath();
								Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
								validPath = true;
								Tracer.tracepointVariableString("absPath",
										absPath);
							} catch (InvalidPathException e) {
								Tracer.tracepointError(e.getClass().getName()
										+ ": " + e.getMessage());
								ShardIdCacheModule.houseAntibubonic
										.println("STONESOUP: Absolute path to file was not found.");
							}
							if (validPath) {
								try {
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									file = new File(absPath);
									freader = new Scanner(file);
									while (freader.hasNextLine()) {
										ShardIdCacheModule.houseAntibubonic
												.println(freader.nextLine());
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
								} catch (NullPointerException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									e.printStackTrace(ShardIdCacheModule.houseAntibubonic);
									throw e;
								} catch (FileNotFoundException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									ShardIdCacheModule.houseAntibubonic
											.println("STONESOUP: File not found.");
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						ShardIdCacheModule.houseAntibubonic.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }

	static class InvalidPathException extends Exception {
		private static final long serialVersionUID = 1L;

		public InvalidPathException(String msg) {
			super(msg);
		}
	}

	static class GetAbsolutePath {
		private String fileName;
		private PrintStream output;

		public GetAbsolutePath(String fileName, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpFoTbSd_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"GetAbsolutePath.ctor");
			this.fileName = fileName;
			this.output = output;
		}

		public String verifyAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmpFoTbSd_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"GetAbsolutePath.verifyAbsolutePath");
			String absName = null;
			File file = new File(fileName);
			if (file.exists()) {
				absName = file.getAbsolutePath();
			} else {
				throw (new InvalidPathException("No such file: " + fileName));
			}
			return absName;
		}

		@SuppressWarnings("finally")
		public String getAbsolutePath() throws InvalidPathException {
			Tracer.tracepointLocation(
					"/tmp/tmpFoTbSd_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"GetAbsolutePath.getAbsolutePath");
			String absName = null;
			try {
				absName = this.verifyAbsolutePath();
			} catch (InvalidPathException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				output.println("STONESOUP: Error in verifying absolute path\n");
				throw e;
			} finally {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				return absName;
			}
		}
	}
}
