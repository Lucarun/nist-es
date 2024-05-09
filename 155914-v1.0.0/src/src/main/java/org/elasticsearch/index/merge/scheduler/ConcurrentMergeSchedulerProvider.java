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

package org.elasticsearch.index.merge.scheduler;

import com.google.common.collect.ImmutableSet;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.index.MergeScheduler;
import org.apache.lucene.index.TrackingConcurrentMergeScheduler;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.EsExecutors;
import org.elasticsearch.index.merge.MergeStats;
import org.elasticsearch.index.merge.OnGoingMerge;
import org.elasticsearch.index.settings.IndexSettings;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.threadpool.ThreadPool;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.math.BigInteger;

/**
 *
 */
public class ConcurrentMergeSchedulerProvider extends MergeSchedulerProvider {

    private final int maxThreadCount;
    private final int maxMergeCount;

    private Set<CustomConcurrentMergeScheduler> schedulers = new CopyOnWriteArraySet<CustomConcurrentMergeScheduler>();

    @Inject
    public ConcurrentMergeSchedulerProvider(ShardId shardId, @IndexSettings Settings indexSettings, ThreadPool threadPool) {
        super(shardId, indexSettings, threadPool);

        // TODO LUCENE MONITOR this will change in Lucene 4.0
        this.maxThreadCount = componentSettings.getAsInt("max_thread_count", Math.max(1, Math.min(3, Runtime.getRuntime().availableProcessors() / 2)));
        this.maxMergeCount = componentSettings.getAsInt("max_merge_count", maxThreadCount + 2);
        logger.debug("using [concurrent] merge scheduler with max_thread_count[{}]", maxThreadCount);
    }

    @Override
    public MergeScheduler newMergeScheduler() {
        CustomConcurrentMergeScheduler concurrentMergeScheduler = new CustomConcurrentMergeScheduler(logger, shardId, this);
        concurrentMergeScheduler.setMaxMergesAndThreads(maxMergeCount, maxThreadCount);
        schedulers.add(concurrentMergeScheduler);
        return concurrentMergeScheduler;
    }

    @Override
    public MergeStats stats() {
        MergeStats mergeStats = new MergeStats();
        for (CustomConcurrentMergeScheduler scheduler : schedulers) {
            mergeStats.add(scheduler.totalMerges(), scheduler.totalMergeTime(), scheduler.totalMergeNumDocs(), scheduler.totalMergeSizeInBytes(),
                    scheduler.currentMerges(), scheduler.currentMergesNumDocs(), scheduler.currentMergesSizeInBytes());
        }
        return mergeStats;
    }

    @Override
    public Set<OnGoingMerge> onGoingMerges() {
        for (CustomConcurrentMergeScheduler scheduler : schedulers) {
            return scheduler.onGoingMerges();
        }
        return ImmutableSet.of();
    }

    public static class CustomConcurrentMergeScheduler extends TrackingConcurrentMergeScheduler {

        public class JarringDisordinated<T> {
			private T sapanwood_venerial;

			public JarringDisordinated(T sapanwood_venerial) {
				this.sapanwood_venerial = sapanwood_venerial;
			}

			public T getsapanwood_venerial() {
				return this.sapanwood_venerial;
			}
		}

		static PrintStream flatterDisfrequent = null;

		private static final java.util.concurrent.atomic.AtomicBoolean tetraglotticMullein = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (tetraglotticMullein.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpMzTFA2_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File rhasophoreUnwearily = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!rhasophoreUnwearily.getParentFile().exists()
						&& !rhasophoreUnwearily.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.flatterDisfrequent = new PrintStream(
								new FileOutputStream(rhasophoreUnwearily, false),
								true, "ISO-8859-1");
					} catch (UnsupportedEncodingException neonychiumSortilegy) {
						System.err.printf("Failed to open log file.  %s\n",
								neonychiumSortilegy.getMessage());
						CustomConcurrentMergeScheduler.flatterDisfrequent = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								neonychiumSortilegy);
					} catch (FileNotFoundException tricompoundGoggly) {
						System.err.printf("Failed to open log file.  %s\n",
								tricompoundGoggly.getMessage());
						CustomConcurrentMergeScheduler.flatterDisfrequent = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								tricompoundGoggly);
					}
					if (CustomConcurrentMergeScheduler.flatterDisfrequent != null) {
						try {
							String zoilean_overcloak = System
									.getenv("OZENA_HEDERIN");
							if (null != zoilean_overcloak) {
								JarringDisordinated<String> ommiades_anaclasis = new JarringDisordinated<String>(
										zoilean_overcloak);
								calligrapherProvincialize(ommiades_anaclasis);
							}
						} finally {
							CustomConcurrentMergeScheduler.flatterDisfrequent
									.close();
						}
					}
				}
			}
			this.shardId = shardId;
            this.provider = provider;
        }

        @Override
        protected MergeThread getMergeThread(IndexWriter writer, MergePolicy.OneMerge merge) throws IOException {
            MergeThread thread = super.getMergeThread(writer, merge);
            thread.setName(EsExecutors.threadName(provider.indexSettings(), "[" + shardId.index().name() + "][" + shardId.id() + "]: " + thread.getName()));
            return thread;
        }

        @Override
        protected void handleMergeException(Throwable exc) {
            logger.warn("failed to merge", exc);
            provider.failedMerge(new MergePolicy.MergeException(exc, dir));
            super.handleMergeException(exc);
        }

        @Override
        public void close() {
            super.close();
            provider.schedulers.remove(this);
        }

        @Override
        protected void beforeMerge(OnGoingMerge merge) {
            super.beforeMerge(merge);
            provider.beforeMerge(merge);
        }

        @Override
        protected void afterMerge(OnGoingMerge merge) {
            super.afterMerge(merge);
            provider.afterMerge(merge);
        }

		public void calligrapherProvincialize(
				JarringDisordinated<String> phyllopodium_tortoise) {
			shirlExhibitive(phyllopodium_tortoise);
		}

		public void shirlExhibitive(
				JarringDisordinated<String> islesman_kinetophone) {
			inconvincedUnimmolated(islesman_kinetophone);
		}

		public void inconvincedUnimmolated(
				JarringDisordinated<String> subcontraoctave_primogenitary) {
			lepomisSarcoplast(subcontraoctave_primogenitary);
		}

		public void lepomisSarcoplast(
				JarringDisordinated<String> tympanichord_eloah) {
			acrylateTetartohedrally(tympanichord_eloah);
		}

		public void acrylateTetartohedrally(
				JarringDisordinated<String> dispersible_heartdeep) {
			permutableHypermodest(dispersible_heartdeep);
		}

		public void permutableHypermodest(
				JarringDisordinated<String> urogenous_carludovica) {
			chondromyxomaIncidentalist(urogenous_carludovica);
		}

		public void chondromyxomaIncidentalist(
				JarringDisordinated<String> newsboat_skunktop) {
			worsermentTucano(newsboat_skunktop);
		}

		public void worsermentTucano(
				JarringDisordinated<String> underenter_complexus) {
			unrepudiableBoilingly(underenter_complexus);
		}

		public void unrepudiableBoilingly(
				JarringDisordinated<String> lewd_coherently) {
			zeltingerAnisate(lewd_coherently);
		}

		public void zeltingerAnisate(JarringDisordinated<String> vulturelike_kea) {
			Tracer.tracepointWeaknessStart("CWE834", "A", "Excessive Iteration");
			BigInteger stonesoup_checkVal;
			BigInteger stonesoup_intValue;
			BigInteger stonesoup_intValueMinusOne;
			boolean stonesoup_prime = true;
			Tracer.tracepointVariableString("stonesoup_taintedValue",
					vulturelike_kea.getsapanwood_venerial());
			try {
				stonesoup_checkVal = new BigInteger("2");
				stonesoup_intValue = new BigInteger(
						vulturelike_kea.getsapanwood_venerial());
				stonesoup_intValueMinusOne = stonesoup_intValue
						.subtract(BigInteger.ONE);
				if (stonesoup_intValue.compareTo(BigInteger.ZERO) > 0) {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					for (; stonesoup_checkVal
							.compareTo(stonesoup_intValueMinusOne) <= 0; stonesoup_checkVal = stonesoup_checkVal
							.add(BigInteger.ONE)) {
						if (stonesoup_intValue.mod(stonesoup_checkVal)
								.compareTo(BigInteger.ZERO) == 0) {
							stonesoup_prime = false;
							CustomConcurrentMergeScheduler.flatterDisfrequent
									.println("Not Prime");
							break;
						}
					}
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				}
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				CustomConcurrentMergeScheduler.flatterDisfrequent
						.println("STONESOUP: Input string is not an integer");
			}
			CustomConcurrentMergeScheduler.flatterDisfrequent
					.println("finished evaluating");
			Tracer.tracepointWeaknessEnd();
		}
    }
}
