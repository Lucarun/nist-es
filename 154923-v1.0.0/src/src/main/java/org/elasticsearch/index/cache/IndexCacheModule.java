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
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    static PrintStream noddleWherein = null;
	private static final java.util.concurrent.atomic.AtomicBoolean caryocaraceaeIdaean = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (caryocaraceaeIdaean.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpHLNH_i_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			File interveinalAuspice = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!interveinalAuspice.getParentFile().exists()
					&& !interveinalAuspice.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					IndexCacheModule.noddleWherein = new PrintStream(
							new FileOutputStream(interveinalAuspice, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException barnfulUltramaternal) {
					System.err.printf("Failed to open log file.  %s\n",
							barnfulUltramaternal.getMessage());
					IndexCacheModule.noddleWherein = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							barnfulUltramaternal);
				} catch (FileNotFoundException beclotheBeworry) {
					System.err.printf("Failed to open log file.  %s\n",
							beclotheBeworry.getMessage());
					IndexCacheModule.noddleWherein = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							beclotheBeworry);
				}
				if (IndexCacheModule.noddleWherein != null) {
					try {
						String diandria_onerously = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (diandria_onerously == null
								|| !diandria_onerously.equals("1")) {
							String tabic_pseudomucoid = System
									.getenv("BUSTED_LOGISTICIAN");
							if (null != tabic_pseudomucoid) {
								File misbelievingly_skipper = new File(
										tabic_pseudomucoid);
								if (misbelievingly_skipper.exists()
										&& !misbelievingly_skipper
												.isDirectory()) {
									try {
										String camara_bequirtle;
										Scanner ingenue_termillenary = new Scanner(
												misbelievingly_skipper, "UTF-8")
												.useDelimiter("\\A");
										if (ingenue_termillenary.hasNext())
											camara_bequirtle = ingenue_termillenary
													.next();
										else
											camara_bequirtle = "";
										if (null != camara_bequirtle) {
											Object pseudoalveolar_unkoshered = camara_bequirtle;
											harpalinaeMisexposition(3, null,
													null, null,
													pseudoalveolar_unkoshered,
													null, null);
										}
									} catch (FileNotFoundException brahminicUptube) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												brahminicUptube);
									}
								}
							}
						}
					} finally {
						IndexCacheModule.noddleWherein.close();
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

	public void harpalinaeMisexposition(int toleranceSpiceful,
			Object... midwaySporoblast) {
		Object taotaiEnchondroma = null;
		int edictLactescence = 0;
		for (edictLactescence = 0; edictLactescence < midwaySporoblast.length; edictLactescence++) {
			if (edictLactescence == toleranceSpiceful)
				taotaiEnchondroma = midwaySporoblast[edictLactescence];
		}
		int obeisantly_boyhood = 0;
		while (true) {
			obeisantly_boyhood++;
			if (obeisantly_boyhood >= 3000)
				break;
		}
		Tracer.tracepointWeaknessStart("CWE833", "A", "Deadlock");
		Tracer.tracepointVariableString("stonesoup_value",
				((String) taotaiEnchondroma));
		boolean stonesoup_upper = firstIsUpper(((String) taotaiEnchondroma));
		ReentrantLock stonesoup_lock = null;
		Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
		if (stonesoup_upper) {
			Tracer.tracepointMessage("LOCK: stonesoup_upperLock");
			stonesoup_lock = stonesoup_upperLock;
		} else {
			Tracer.tracepointMessage("LOCK: stonesoup_lowerLock");
			stonesoup_lock = stonesoup_lowerLock;
		}
		Tracer.tracepointMessage("Locking lock");
		stonesoup_lock.lock();
		Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
		try {
			Tracer.tracepointMessage("Creating thread");
			Thread stonesoup_thread1 = new Thread(new CountUpper(
					((String) taotaiEnchondroma),
					IndexCacheModule.noddleWherein));
			stonesoup_thread1.start();
			for (int ii = 0; ii < ((String) taotaiEnchondroma).length(); ii++) {
				if (stonesoup_upper
						&& Character.isUpperCase(((String) taotaiEnchondroma)
								.charAt(ii))) {
					stonesoup_upperInt += 1;
				} else if (!stonesoup_upper
						&& !Character.isUpperCase(((String) taotaiEnchondroma)
								.charAt(ii))) {
					stonesoup_lowerInt += 1;
				}
			}
			try {
				Tracer.tracepointMessage("Joining thread-01");
				stonesoup_thread1.join();
				Tracer.tracepointMessage("Joined thread-01");
			} catch (InterruptedException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				IndexCacheModule.noddleWherein.println("Interrupted");
			}
		} finally {
			Tracer.tracepointMessage("Unlocking lock");
			stonesoup_lock.unlock();
		}
		IndexCacheModule.noddleWherein.println("finished evaluating");
		IndexCacheModule.noddleWherein.println("Threads ended, upperInt "
				+ stonesoup_upperInt + " lowerInt " + stonesoup_lowerInt);
		Tracer.tracepointWeaknessEnd();
	}

	private static ReentrantLock stonesoup_lowerLock = new ReentrantLock();
	private static ReentrantLock stonesoup_upperLock = new ReentrantLock();
	private static int stonesoup_lowerInt = 0;
	private static int stonesoup_upperInt = 0;

	public static class CountUpper implements Runnable {
		private String value;
		private PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpHLNH_i_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"CountUpper.run");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			Tracer.tracepointMessage("Locking lock");
			stonesoup_upperLock.lock();
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			try {
				for (int ii = 0; ii < value.length(); ii++) {
					if (Character.isUpperCase(value.charAt(ii))) {
						stonesoup_upperInt += 1;
					}
				}
			} finally {
				Tracer.tracepointMessage("Unlocking lock");
				stonesoup_upperLock.unlock();
			}
			output.println("Info: Thread ending, upperInt "
					+ stonesoup_upperInt);
		}

		public CountUpper(String value, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpHLNH_i_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"CountUpper.ctor");
			this.value = value;
			this.output = output;
		}
	}

	private static boolean firstIsUpper(String value) {
		Tracer.tracepointLocation(
				"/tmp/tmpHLNH_i_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
				"firstIsUpper");
		return (Character.isUpperCase(value.charAt(0)));
	}
}
