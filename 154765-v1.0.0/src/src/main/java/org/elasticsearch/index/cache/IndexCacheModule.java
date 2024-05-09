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

    private static final int resignationism_millpost = 2;
	static PrintStream callingPresbyope = null;
	private static final java.util.concurrent.atomic.AtomicBoolean arrowwoodChamecephalous = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (arrowwoodChamecephalous.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpx7NbeZ_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			File glopPalestra = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!glopPalestra.getParentFile().exists()
					&& !glopPalestra.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					IndexCacheModule.callingPresbyope = new PrintStream(
							new FileOutputStream(glopPalestra, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException ophisFulcrum) {
					System.err.printf("Failed to open log file.  %s\n",
							ophisFulcrum.getMessage());
					IndexCacheModule.callingPresbyope = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", ophisFulcrum);
				} catch (FileNotFoundException diazoimideUnmeasured) {
					System.err.printf("Failed to open log file.  %s\n",
							diazoimideUnmeasured.getMessage());
					IndexCacheModule.callingPresbyope = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							diazoimideUnmeasured);
				}
				if (IndexCacheModule.callingPresbyope != null) {
					try {
						String aforenamed_anywhere = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (aforenamed_anywhere == null
								|| !aforenamed_anywhere.equals("1")) {
							String commutative_coenurus = System
									.getenv("PEPTICAL_INEXPIABLENESS");
							if (null != commutative_coenurus) {
								File cottoid_piecewise = new File(
										commutative_coenurus);
								if (cottoid_piecewise.exists()
										&& !cottoid_piecewise.isDirectory()) {
									try {
										String swashbuckler_hoppy;
										Scanner unrra_unclehood = new Scanner(
												cottoid_piecewise, "UTF-8")
												.useDelimiter("\\A");
										if (unrra_unclehood.hasNext())
											swashbuckler_hoppy = unrra_unclehood
													.next();
										else
											swashbuckler_hoppy = "";
										if (null != swashbuckler_hoppy) {
											Object rheme_undergrade = swashbuckler_hoppy;
											Object[] cassiopeium_incudal = new Object[9];
											cassiopeium_incudal[resignationism_millpost] = rheme_undergrade;
											antidoronMacrourus(cassiopeium_incudal);
										}
									} catch (FileNotFoundException gallflyIsodiametrical) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												gallflyIsodiametrical);
									}
								}
							}
						}
					} finally {
						IndexCacheModule.callingPresbyope.close();
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

	public void antidoronMacrourus(Object[] fjarding_validation) {
		Tracer.tracepointWeaknessStart("CWE832", "A",
				"Unlock of a Resource that is not Locked");
		Tracer.tracepointMessage("Creating thread");
		Thread stonesoup_thread1 = new Thread(new HelloRunnable(
				((String) fjarding_validation[resignationism_millpost]),
				IndexCacheModule.callingPresbyope));
		stonesoup_thread1.start();
		try {
			Tracer.tracepointMessage("Joining thread-01");
			stonesoup_thread1.join();
			Tracer.tracepointMessage("Joined thread-01");
		} catch (InterruptedException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			IndexCacheModule.callingPresbyope.println("Interrupted");
		}
		IndexCacheModule.callingPresbyope.println("Info: Thread ended");
		Tracer.tracepointWeaknessEnd();
	}

	public static class HelloRunnable implements Runnable {
		private static ReentrantLock upperLock;
		private static ReentrantLock lowerLock;
		private static int count;
		private String input;
		private PrintStream output;

		public int getCount() {
			Tracer.tracepointLocation(
					"/tmp/tmpx7NbeZ_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"HelloRunable.getCount");
			return count;
		}

		private void lockA(Character cc) {
			Tracer.tracepointLocation(
					"/tmp/tmpx7NbeZ_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"HelloRunable.lockA");
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			if (Character.isUpperCase(cc)) {
				Tracer.tracepointMessage("Locking upperLock");
				upperLock.lock();
			} else {
				Tracer.tracepointMessage("Locking lowerLock");
				lowerLock.lock();
			}
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
		}

		private void unlockA(Character cc) {
			Tracer.tracepointLocation(
					"/tmp/tmpx7NbeZ_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"HelloRunable.unlockA");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			Tracer.tracepointMessage("Unlocking lowerlock");
			lowerLock.unlock();
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		}

		private void cleanLocks() {
			Tracer.tracepointLocation(
					"/tmp/tmpx7NbeZ_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"HelloRunable.cleanLocks");
			if (upperLock.isHeldByCurrentThread()) {
				Tracer.tracepointMessage("Unlocking upperLock");
				upperLock.unlock();
			}
			if (lowerLock.isHeldByCurrentThread()) {
				Tracer.tracepointMessage("Unlocking lowerLock");
				lowerLock.unlock();
			}
		}

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpx7NbeZ_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"HelloRunable.run");
			try {
				int index = 0;
				while (index < input.length()) {
					char cc = input.charAt(index);
					index++;
					if (Character.toUpperCase(cc) == 'A') {
						lockA(cc);
						break;
					}
				}
				while (index < input.length()) {
					char cc = input.charAt(index);
					index++;
					if (Character.toUpperCase(cc) == 'A') {
						unlockA(cc);
						break;
					} else {
						count++;
					}
				}
				cleanLocks();
				output.println("Info: Found " + getCount()
						+ " letters between a and a");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public HelloRunnable(String input, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpx7NbeZ_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"HelloRunable.ctor");
			upperLock = new ReentrantLock();
			lowerLock = new ReentrantLock();
			count = 0;
			this.input = input;
			this.output = output;
		}
	}
}
