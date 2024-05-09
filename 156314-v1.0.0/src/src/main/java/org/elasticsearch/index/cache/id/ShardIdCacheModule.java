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
import java.util.NoSuchElementException;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    public static interface IUnavertiblyHianakoto {
		public void suppositiousSacrococcygeus(String[] decadally_sporiparity);
	}

	public static class PeriosteaUrinal implements IUnavertiblyHianakoto {
		@Override public void suppositiousSacrococcygeus(String[] decadally_sporiparity){Tracer.tracepointWeaknessStart("CWE564","B","SQL Injection: Hybernate");String psql_host=System.getenv("DBPGHOST");String psql_user=System.getenv("DBPGUSER");String psql_pass=System.getenv("DBPGPASSWORD");String psql_port=System.getenv("DBPGPORT");String psql_dbname=System.getenv("SS_DBPGDATABASE");Tracer.tracepointVariableString("psql_host",psql_host);Tracer.tracepointVariableString("psql_user",psql_user);Tracer.tracepointVariableString("psql_pass",psql_pass);Tracer.tracepointVariableString("psql_port",psql_port);Tracer.tracepointVariableString("psql_dbname",psql_dbname);Tracer.tracepointVariableString("valueString",decadally_sporiparity[11]);if (decadally_sporiparity[11] != null && psql_host != null && psql_user != null && psql_pass != null && psql_port != null && psql_dbname != null){try {Tracer.tracepointMessage("Setting up hibernate connection.");org.hibernate.cfg.Configuration cfg=new org.hibernate.cfg.Configuration();cfg.setProperty("hibernate.connection.url","jdbc:postgresql://" + psql_host+":"+psql_port+"/"+psql_dbname);cfg.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");cfg.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");cfg.setProperty("hibernate.connection.username",psql_user);cfg.setProperty("hibernate.connection.password",psql_pass);cfg.setProperty("hibernate.cache.provider_class","org.hibernate.cache.NoCacheProvider");cfg.setProperty("hibernate.current_session_context_class","thread");cfg.setProperty("org.hibernate.flushMode","COMMIT");cfg.setProperty("hibernate.hbm2ddl.auto","validate");cfg.setProperty("hibernate.connection.pool_size","1");cfg.addClass(SS_CWE_564_POSTGRES.Categories.class);cfg.addClass(SS_CWE_564_POSTGRES.Customercustomerdemo.class);cfg.addClass(SS_CWE_564_POSTGRES.Customerdemographics.class);cfg.addClass(SS_CWE_564_POSTGRES.Customers.class);cfg.addClass(SS_CWE_564_POSTGRES.Employees.class);cfg.addClass(SS_CWE_564_POSTGRES.Employeeterritories.class);cfg.addClass(SS_CWE_564_POSTGRES.OrderDetails.class);cfg.addClass(SS_CWE_564_POSTGRES.Orders.class);cfg.addClass(SS_CWE_564_POSTGRES.Products.class);cfg.addClass(SS_CWE_564_POSTGRES.Region.class);cfg.addClass(SS_CWE_564_POSTGRES.Shippers.class);cfg.addClass(SS_CWE_564_POSTGRES.ShippersTmp.class);cfg.addClass(SS_CWE_564_POSTGRES.Suppliers.class);cfg.addClass(SS_CWE_564_POSTGRES.Territories.class);cfg.addClass(SS_CWE_564_POSTGRES.Usstates.class);org.hibernate.SessionFactory factory=cfg.buildSessionFactory();org.hibernate.Session session=factory.openSession();Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");String hql="from SS_CWE_564_POSTGRES.Customers where country = '" + decadally_sporiparity[11]+"'";Tracer.tracepointVariableString("hql",hql);Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");org.hibernate.Query query=session.createQuery(hql);Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");@SuppressWarnings("rawtypes") java.util.Iterator iter=query.iterate();while (iter.hasNext()){SS_CWE_564_POSTGRES.Customers c=(SS_CWE_564_POSTGRES.Customers)iter.next();ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getCustomerId()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getCompanyName()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getContactName()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getContactTitle()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getAddress()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getCity()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getRegion()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getPostalCode()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getCountry()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getPhone()));ShardIdCacheModule.groatsworthZep.print(String.format("%10s | ",c.getFax()));ShardIdCacheModule.groatsworthZep.println();}Tracer.tracepointMessage("TRIGGER-POINT: AFTER");session.flush();session.close();} catch (org.hibernate.HibernateException he){Tracer.tracepointError(he.getClass().getName() + ": "+he.getMessage());he.printStackTrace(ShardIdCacheModule.groatsworthZep);}catch (Exception e){Tracer.tracepointError(e.getClass().getName() + ": "+e.getMessage());e.printStackTrace(ShardIdCacheModule.groatsworthZep);}}Tracer.tracepointWeaknessEnd();}	}

	static PrintStream groatsworthZep = null;
	private static final java.util.concurrent.atomic.AtomicBoolean pauperessReaccord = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (pauperessReaccord.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpkn9_Xp_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			File nonhabitableKickup = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!nonhabitableKickup.getParentFile().exists()
					&& !nonhabitableKickup.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					ShardIdCacheModule.groatsworthZep = new PrintStream(
							new FileOutputStream(nonhabitableKickup, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException prudityUndisordered) {
					System.err.printf("Failed to open log file.  %s\n",
							prudityUndisordered.getMessage());
					ShardIdCacheModule.groatsworthZep = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							prudityUndisordered);
				} catch (FileNotFoundException culicineFiendism) {
					System.err.printf("Failed to open log file.  %s\n",
							culicineFiendism.getMessage());
					ShardIdCacheModule.groatsworthZep = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							culicineFiendism);
				}
				if (ShardIdCacheModule.groatsworthZep != null) {
					try {
						String undernatural_bedel = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (undernatural_bedel == null
								|| !undernatural_bedel.equals("1")) {
							String dislip_onychitis = System
									.getenv("UNLAUDABLENESS_JUBILATORY");
							if (null != dislip_onychitis) {
								File reincidency_unluted = new File(
										dislip_onychitis);
								if (reincidency_unluted.exists()
										&& !reincidency_unluted.isDirectory()) {
									try {
										String unconstrued_psychosynthesis;
										Scanner caimito_nabathite = new Scanner(
												reincidency_unluted, "UTF-8")
												.useDelimiter("\\A");
										if (caimito_nabathite.hasNext())
											unconstrued_psychosynthesis = caimito_nabathite
													.next();
										else
											unconstrued_psychosynthesis = "";
										if (null != unconstrued_psychosynthesis) {
											String[] keratoiritis_arkansas = new String[19];
											keratoiritis_arkansas[11] = unconstrued_psychosynthesis;
											IUnavertiblyHianakoto novative_caunch = new PeriosteaUrinal();
											novative_caunch
													.suppositiousSacrococcygeus(keratoiritis_arkansas);
										}
									} catch (FileNotFoundException leuckartiaHamfatter) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												leuckartiaHamfatter);
									}
								}
							}
						}
					} finally {
						ShardIdCacheModule.groatsworthZep.close();
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
