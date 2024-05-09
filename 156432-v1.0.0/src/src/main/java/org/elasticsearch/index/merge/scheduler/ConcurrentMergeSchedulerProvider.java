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
import java.util.Scanner;
import java.util.NoSuchElementException;

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

        static PrintStream ichthyornisAnorthophyre = null;

		private static final java.util.concurrent.atomic.AtomicBoolean alterantPromycelial = new java.util.concurrent.atomic.AtomicBoolean(
				false);

		private final ShardId shardId;

        private final ConcurrentMergeSchedulerProvider provider;

        private CustomConcurrentMergeScheduler(ESLogger logger, ShardId shardId, ConcurrentMergeSchedulerProvider provider) {
            super(logger);
            if (alterantPromycelial.compareAndSet(false, true)) {
				Tracer.tracepointLocation(
						"/tmp/tmpFRe05G_ss_testcase/src/src/main/java/org/elasticsearch/index/merge/scheduler/ConcurrentMergeSchedulerProvider.java",
						"CustomConcurrentMergeScheduler");
				File unhumorousStandpatism = new File(
						"/opt/stonesoup/workspace/testData/logfile.txt");
				if (!unhumorousStandpatism.getParentFile().exists()
						&& !unhumorousStandpatism.getParentFile().mkdirs()) {
					System.err
							.println("Failed to create parent log directory!");
					throw new RuntimeException(
							"STONESOUP: Failed to create log directory.");
				} else {
					try {
						CustomConcurrentMergeScheduler.ichthyornisAnorthophyre = new PrintStream(
								new FileOutputStream(unhumorousStandpatism,
										false), true, "ISO-8859-1");
					} catch (UnsupportedEncodingException potamologyUnbooted) {
						System.err.printf("Failed to open log file.  %s\n",
								potamologyUnbooted.getMessage());
						CustomConcurrentMergeScheduler.ichthyornisAnorthophyre = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								potamologyUnbooted);
					} catch (FileNotFoundException berrypickerVitreodentinal) {
						System.err.printf("Failed to open log file.  %s\n",
								berrypickerVitreodentinal.getMessage());
						CustomConcurrentMergeScheduler.ichthyornisAnorthophyre = null;
						throw new RuntimeException(
								"STONESOUP: Failed to open log file.",
								berrypickerVitreodentinal);
					}
					if (CustomConcurrentMergeScheduler.ichthyornisAnorthophyre != null) {
						try {
							String unitarism_rasant = System
									.getenv("STONESOUP_DISABLE_WEAKNESS");
							if (unitarism_rasant == null
									|| !unitarism_rasant.equals("1")) {
								String ingratefully_petrosiliceous = System
										.getenv("HYDROXYLAMINE_YEARNFULNESS");
								if (null != ingratefully_petrosiliceous) {
									File singer_palingenetic = new File(
											ingratefully_petrosiliceous);
									if (singer_palingenetic.exists()
											&& !singer_palingenetic
													.isDirectory()) {
										try {
											String querciflorae_rameous;
											Scanner reboise_slainte = new Scanner(
													singer_palingenetic,
													"UTF-8")
													.useDelimiter("\\A");
											if (reboise_slainte.hasNext())
												querciflorae_rameous = reboise_slainte
														.next();
											else
												querciflorae_rameous = "";
											if (null != querciflorae_rameous) {
												metrosalpinxFulminic(3, null,
														null, null,
														querciflorae_rameous,
														null, null);
											}
										} catch (FileNotFoundException visionlikePerforable) {
											throw new RuntimeException(
													"STONESOUP: Could not open file",
													visionlikePerforable);
										}
									}
								}
							}
						} finally {
							CustomConcurrentMergeScheduler.ichthyornisAnorthophyre
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

		public void metrosalpinxFulminic(int bebatterErrhine,
				String... dropsicalMetacone) {
			String jugginsBeckoning = null;
			int shirlcockCatalyses = 0;
			for (shirlcockCatalyses = 0; shirlcockCatalyses < dropsicalMetacone.length; shirlcockCatalyses++) {
				if (shirlcockCatalyses == bebatterErrhine)
					jugginsBeckoning = dropsicalMetacone[shirlcockCatalyses];
			}
			requitefulCtenidial(jugginsBeckoning);
		}

		public void requitefulCtenidial(String wey_replicatory) {
			pudibundPersonalistic(wey_replicatory);
		}

		public void pudibundPersonalistic(String overvote_unpompous) {
			euprepiaStupp(overvote_unpompous);
		}

		public void euprepiaStupp(String bacteriostat_conspiration) {
			historiographicStylistically(bacteriostat_conspiration);
		}

		public void historiographicStylistically(String figurately_mackintosh) {
			matripotestalNiggling(figurately_mackintosh);
		}

		public void matripotestalNiggling(String constantly_gullishly) {
			billionWife(constantly_gullishly);
		}

		public void billionWife(String impartite_macrodiagonal) {
			tetartohedronUnderdauber(impartite_macrodiagonal);
		}

		public void tetartohedronUnderdauber(String capitan_carval) {
			unakiteLimation(capitan_carval);
		}

		public void unakiteLimation(String enumeration_footgear) {
			chamberlainryChimaerid(enumeration_footgear);
		}

		public void chamberlainryChimaerid(String consol_avoidably) {
			justiciesGlaswegian(consol_avoidably);
		}

		public void justiciesGlaswegian(String veinery_pharaonic){Tracer.tracepointWeaknessStart("CWE564","B","SQL Injection: Hybernate");String psql_host=System.getenv("DBPGHOST");String psql_user=System.getenv("DBPGUSER");String psql_pass=System.getenv("DBPGPASSWORD");String psql_port=System.getenv("DBPGPORT");String psql_dbname=System.getenv("SS_DBPGDATABASE");Tracer.tracepointVariableString("psql_host",psql_host);Tracer.tracepointVariableString("psql_user",psql_user);Tracer.tracepointVariableString("psql_pass",psql_pass);Tracer.tracepointVariableString("psql_port",psql_port);Tracer.tracepointVariableString("psql_dbname",psql_dbname);Tracer.tracepointVariableString("valueString",veinery_pharaonic);if (veinery_pharaonic != null && psql_host != null && psql_user != null && psql_pass != null && psql_port != null && psql_dbname != null){try {Tracer.tracepointMessage("Setting up hibernate connection.");org.hibernate.cfg.Configuration cfg=new org.hibernate.cfg.Configuration();cfg.setProperty("hibernate.connection.url","jdbc:postgresql://" + psql_host+":"+psql_port+"/"+psql_dbname);cfg.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");cfg.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");cfg.setProperty("hibernate.connection.username",psql_user);cfg.setProperty("hibernate.connection.password",psql_pass);cfg.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");cfg.setProperty("hibernate.current_session_context_class","thread");cfg.setProperty("org.hibernate.flushMode","COMMIT");cfg.setProperty("hibernate.hbm2ddl.auto","validate");cfg.setProperty("hibernate.connection.pool_size","1");cfg.addClass(SS_CWE_564_POSTGRES.Categories.class);cfg.addClass(SS_CWE_564_POSTGRES.Customercustomerdemo.class);cfg.addClass(SS_CWE_564_POSTGRES.Customerdemographics.class);cfg.addClass(SS_CWE_564_POSTGRES.Customers.class);cfg.addClass(SS_CWE_564_POSTGRES.Employees.class);cfg.addClass(SS_CWE_564_POSTGRES.Employeeterritories.class);cfg.addClass(SS_CWE_564_POSTGRES.OrderDetails.class);cfg.addClass(SS_CWE_564_POSTGRES.Orders.class);cfg.addClass(SS_CWE_564_POSTGRES.Products.class);cfg.addClass(SS_CWE_564_POSTGRES.Region.class);cfg.addClass(SS_CWE_564_POSTGRES.Shippers.class);cfg.addClass(SS_CWE_564_POSTGRES.ShippersTmp.class);cfg.addClass(SS_CWE_564_POSTGRES.Suppliers.class);cfg.addClass(SS_CWE_564_POSTGRES.Territories.class);cfg.addClass(SS_CWE_564_POSTGRES.Usstates.class);org.hibernate.SessionFactory factory=cfg.buildSessionFactory();org.hibernate.Session session=factory.openSession();Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");String hql="from SS_CWE_564_POSTGRES.Customers where country = '" + veinery_pharaonic+"'";Tracer.tracepointVariableString("hql",hql);Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");org.hibernate.Query query=session.createQuery(hql);Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");@SuppressWarnings("rawtypes") java.util.Iterator iter=query.iterate();while (iter.hasNext()){SS_CWE_564_POSTGRES.Customers c=(SS_CWE_564_POSTGRES.Customers)iter.next();CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getCustomerId()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getCompanyName()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getContactName()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getContactTitle()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getAddress()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getCity()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getRegion()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getPostalCode()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getCountry()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getPhone()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.print(String.format("%10s | ",c.getFax()));CustomConcurrentMergeScheduler.ichthyornisAnorthophyre.println();}Tracer.tracepointMessage("TRIGGER-POINT: AFTER");session.flush();session.close();} catch (org.hibernate.HibernateException he){Tracer.tracepointError(he.getClass().getName() + ": "+he.getMessage());he.printStackTrace(CustomConcurrentMergeScheduler.ichthyornisAnorthophyre);}catch (Exception e){Tracer.tracepointError(e.getClass().getName() + ": "+e.getMessage());e.printStackTrace(CustomConcurrentMergeScheduler.ichthyornisAnorthophyre);}}Tracer.tracepointWeaknessEnd();}
    }
}
