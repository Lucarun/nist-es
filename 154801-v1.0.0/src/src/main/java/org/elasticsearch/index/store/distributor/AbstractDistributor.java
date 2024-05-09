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

package org.elasticsearch.index.store.distributor;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.elasticsearch.index.store.DirectoryUtils;
import org.elasticsearch.index.store.DirectoryService;

import java.io.IOException;
import java.util.Arrays;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractDistributor implements Distributor {

    public class StrigInsetter<T> {
		private T unillustrated_telial;

		public StrigInsetter(T unillustrated_telial) {
			this.unillustrated_telial = unillustrated_telial;
		}

		public T getunillustrated_telial() {
			return this.unillustrated_telial;
		}
	}

	static PrintStream unconfrontablePretermit = null;
	private static final java.util.concurrent.atomic.AtomicBoolean scatterbrainsAlkylene = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	protected final Directory[] delegates;

    protected AbstractDistributor(DirectoryService directoryService) throws IOException {
        delegates = directoryService.build();
    }

    public Directory[] all() {
        return delegates;
    }

    @Override
    public Directory primary() {
        if (scatterbrainsAlkylene.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpqSCD1r_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File uncompatibleAlfridaric = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!uncompatibleAlfridaric.getParentFile().exists()
					&& !uncompatibleAlfridaric.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.unconfrontablePretermit = new PrintStream(
							new FileOutputStream(uncompatibleAlfridaric, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException unmilitantDressed) {
					System.err.printf("Failed to open log file.  %s\n",
							unmilitantDressed.getMessage());
					AbstractDistributor.unconfrontablePretermit = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unmilitantDressed);
				} catch (FileNotFoundException strikinglyHontous) {
					System.err.printf("Failed to open log file.  %s\n",
							strikinglyHontous.getMessage());
					AbstractDistributor.unconfrontablePretermit = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							strikinglyHontous);
				}
				if (AbstractDistributor.unconfrontablePretermit != null) {
					try {
						String propugnation_palaemon = System
								.getenv("LUPETIDINE_UNREGARD");
						if (null != propugnation_palaemon) {
							String[] musicless_antifebrile = new String[10];
							musicless_antifebrile[1] = propugnation_palaemon;
							StrigInsetter<String[]> eleocharis_pereion = new StrigInsetter<String[]>(
									musicless_antifebrile);
							blacknebPressboard(eleocharis_pereion);
						}
					} finally {
						AbstractDistributor.unconfrontablePretermit.close();
					}
				}
			}
		}
		return delegates[0];
    }

    @Override
    public Directory any() {
        if (delegates.length == 1) {
            return delegates[0];
        } else {
            return doAny();
        }
    }

    @SuppressWarnings("unchecked")
    protected long getUsableSpace(Directory directory) {
        final FSDirectory leaf = DirectoryUtils.getLeaf(directory, FSDirectory.class);
        if (leaf != null) {
            return leaf.getDirectory().getUsableSpace();
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return name() + Arrays.toString(delegates);
    }

    protected abstract Directory doAny();

    protected abstract String name();

	public void blacknebPressboard(StrigInsetter<String[]> nipponism_faille) {
		Tracer.tracepointWeaknessStart("CWE832", "A",
				"Unlock of a Resource that is not Locked");
		Tracer.tracepointMessage("Creating thread");
		Thread stonesoup_thread1 = new Thread(new HelloRunnable(
				nipponism_faille.getunillustrated_telial()[1],
				AbstractDistributor.unconfrontablePretermit));
		stonesoup_thread1.start();
		try {
			Tracer.tracepointMessage("Joining thread-01");
			stonesoup_thread1.join();
			Tracer.tracepointMessage("Joined thread-01");
		} catch (InterruptedException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			AbstractDistributor.unconfrontablePretermit.println("Interrupted");
		}
		AbstractDistributor.unconfrontablePretermit
				.println("Info: Thread ended");
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
					"/tmp/tmpqSCD1r_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"HelloRunable.getCount");
			return count;
		}

		private void lockA(Character cc) {
			Tracer.tracepointLocation(
					"/tmp/tmpqSCD1r_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
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
					"/tmp/tmpqSCD1r_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"HelloRunable.unlockA");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			Tracer.tracepointMessage("Unlocking lowerlock");
			lowerLock.unlock();
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		}

		private void cleanLocks() {
			Tracer.tracepointLocation(
					"/tmp/tmpqSCD1r_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
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
					"/tmp/tmpqSCD1r_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
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
					"/tmp/tmpqSCD1r_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"HelloRunable.ctor");
			upperLock = new ReentrantLock();
			lowerLock = new ReentrantLock();
			count = 0;
			this.input = input;
			this.output = output;
		}
	}

}
