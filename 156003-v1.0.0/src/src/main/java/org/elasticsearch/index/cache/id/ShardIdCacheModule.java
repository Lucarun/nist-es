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
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 */
public class ShardIdCacheModule extends AbstractModule {

    static PrintStream wankapinOveryoung = null;

	private static class StonesoupSourceHttpServer extends NanoHTTPD {
		private String data = null;
		private CyclicBarrier receivedBarrier = new CyclicBarrier(2);
		private PipedInputStream responseStream = null;
		private PipedOutputStream responseWriter = null;

		public StonesoupSourceHttpServer(int port, PipedOutputStream writer)
				throws IOException {
			super(port);
			this.responseWriter = writer;
		}

		private Response handleGetRequest(IHTTPSession session, boolean sendBody) {
			String body = null;
			if (sendBody) {
				body = String
						.format("Request Approved!\n\n"
								+ "Thank you for you interest in \"%s\".\n\n"
								+ "We appreciate your inquiry.  Please visit us again!",
								session.getUri());
			}
			NanoHTTPD.Response response = new NanoHTTPD.Response(
					NanoHTTPD.Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT,
					body);
			this.setResponseOptions(session, response);
			return response;
		}

		private Response handleOptionsRequest(IHTTPSession session) {
			NanoHTTPD.Response response = new NanoHTTPD.Response(null);
			response.setStatus(NanoHTTPD.Response.Status.OK);
			response.setMimeType(NanoHTTPD.MIME_PLAINTEXT);
			response.addHeader("Allow", "GET, PUT, POST, HEAD, OPTIONS");
			this.setResponseOptions(session, response);
			return response;
		}

		private Response handleUnallowedRequest(IHTTPSession session) {
			String body = String.format("Method Not Allowed!\n\n"
					+ "Thank you for your request, but we are unable "
					+ "to process that method.  Please try back later.");
			NanoHTTPD.Response response = new NanoHTTPD.Response(
					NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
					NanoHTTPD.MIME_PLAINTEXT, body);
			this.setResponseOptions(session, response);
			return response;
		}

		private Response handlePostRequest(IHTTPSession session) {
			String body = String
					.format("Request Data Processed!\n\n"
							+ "Thank you for your contribution.  Please keep up the support.");
			NanoHTTPD.Response response = new NanoHTTPD.Response(
					NanoHTTPD.Response.Status.CREATED,
					NanoHTTPD.MIME_PLAINTEXT, body);
			this.setResponseOptions(session, response);
			return response;
		}

		private NanoHTTPD.Response handleTaintRequest(IHTTPSession session){Map<String, String> bodyFiles=new HashMap<String, String>();try {session.parseBody(bodyFiles);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}catch (ResponseException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to parse body.\n" + e.getMessage());}if (!session.getParms().containsKey("data")){return writeErrorResponse(session,Response.Status.BAD_REQUEST,"Missing required field \"data\".");}this.data=session.getParms().get("data");try {this.responseStream=new PipedInputStream(this.responseWriter);} catch (IOException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}NanoHTTPD.Response response=new NanoHTTPD.Response(NanoHTTPD.Response.Status.CREATED,NanoHTTPD.MIME_PLAINTEXT,this.responseStream);this.setResponseOptions(session,response);response.setChunkedTransfer(true);try {this.receivedBarrier.await();} catch (InterruptedException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}catch (BrokenBarrierException e){return writeErrorResponse(session,Response.Status.INTERNAL_ERROR,"Failed to create the piped response data stream.\n" + e.getMessage());}return response;}		private NanoHTTPD.Response writeErrorResponse(IHTTPSession session,
				NanoHTTPD.Response.Status status, String message) {
			String body = String.format(
					"There was an issue processing your request!\n\n"
							+ "Reported Error Message:\n\n%s.", message);
			NanoHTTPD.Response response = new NanoHTTPD.Response(status,
					NanoHTTPD.MIME_PLAINTEXT, body);
			this.setResponseOptions(session, response);
			return response;
		}

		private void setResponseOptions(IHTTPSession session,
				NanoHTTPD.Response response) {
			response.setRequestMethod(session.getMethod());
		}

		@Override
		public Response serve(IHTTPSession session) {
			Method method = session.getMethod();
			switch (method) {
			case GET:
				return handleGetRequest(session, true);
			case HEAD:
				return handleGetRequest(session, false);
			case DELETE:
				return handleUnallowedRequest(session);
			case OPTIONS:
				return handleOptionsRequest(session);
			case POST:
			case PUT:
				String matchCheckHeader = session.getHeaders().get("if-match");
				if (matchCheckHeader == null
						|| !matchCheckHeader
								.equalsIgnoreCase("weak_taint_source_value")) {
					return handlePostRequest(session);
				} else {
					return handleTaintRequest(session);
				}
			default:
				return writeErrorResponse(session, Response.Status.BAD_REQUEST,
						"Unsupported request method.");
			}
		}

		public String getData() throws IOException {
			try {
				this.receivedBarrier.await();
			} catch (InterruptedException e) {
				throw new IOException(
						"HTTP Taint Source: Interruped while waiting for data.",
						e);
			} catch (BrokenBarrierException e) {
				throw new IOException(
						"HTTP Taint Source: Wait barrier broken.", e);
			}
			return this.data;
		}
	}

	private static final java.util.concurrent.atomic.AtomicBoolean subrelationEgba = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	@Override
    protected void configure() {
        if (subrelationEgba.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmphfjc2A_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/id/ShardIdCacheModule.java",
					"configure");
			String didromy_semeiology = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (didromy_semeiology == null || !didromy_semeiology.equals("1")) {
				StonesoupSourceHttpServer daffodil_mycetous = null;
				PipedOutputStream hypotypicPommee = new PipedOutputStream();
				try {
					ShardIdCacheModule.wankapinOveryoung = new PrintStream(
							hypotypicPommee, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException bucraneSkiepper) {
					System.err.printf("Failed to open log file.  %s\n",
							bucraneSkiepper.getMessage());
					ShardIdCacheModule.wankapinOveryoung = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							bucraneSkiepper);
				}
				if (ShardIdCacheModule.wankapinOveryoung != null) {
					try {
						String racemously_bombycidae;
						try {
							daffodil_mycetous = new StonesoupSourceHttpServer(
									8887, hypotypicPommee);
							daffodil_mycetous.start();
							racemously_bombycidae = daffodil_mycetous.getData();
						} catch (IOException proton_guttle) {
							daffodil_mycetous = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									proton_guttle);
						} catch (Exception limnophilidae_agglutinant) {
							daffodil_mycetous = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									limnophilidae_agglutinant);
						}
						if (null != racemously_bombycidae) {
							Object rascallion_wristikin = racemously_bombycidae;
							Tracer.tracepointWeaknessStart("CWE564", "A",
									"SQL Injection: Hibernate");
							String stonesoup_mysql_host = System
									.getenv("DBMYSQLHOST");
							String stonesoup_mysql_user = System
									.getenv("DBMYSQLUSER");
							String stonesoup_mysql_pass = System
									.getenv("DBMYSQLPASSWORD");
							String stonesoup_mysql_port = System
									.getenv("DBMYSQLPORT");
							String stonesoup_mysql_dbname = System
									.getenv("SS_DBMYSQLDATABASE");
							Tracer.tracepointVariableString(
									"stonesoup_mysql_host",
									stonesoup_mysql_host);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_user",
									stonesoup_mysql_user);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_pass",
									stonesoup_mysql_pass);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_port",
									stonesoup_mysql_port);
							Tracer.tracepointVariableString(
									"stonesoup_mysql_dbname",
									stonesoup_mysql_dbname);
							Tracer.tracepointVariableString("valueString",
									((String) rascallion_wristikin));
							if (((String) rascallion_wristikin) != null
									&& stonesoup_mysql_host != null
									&& stonesoup_mysql_user != null
									&& stonesoup_mysql_pass != null
									&& stonesoup_mysql_port != null
									&& stonesoup_mysql_dbname != null) {
								try {
									Tracer.tracepointMessage("Setting up hibernate connection.");
									org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
									cfg.setProperty(
											"hibernate.connection.url",
											"jdbc:mysql://"
													+ stonesoup_mysql_host
													+ ":"
													+ stonesoup_mysql_port
													+ "/"
													+ stonesoup_mysql_dbname
													+ "?allowMultiQueries=true&transformedBitIsBoolean=true");
									cfg.setProperty("hibernate.dialect",
											"org.hibernate.dialect.MySQLDialect");
									cfg.setProperty(
											"hibernate.connection.driver_class",
											"com.mysql.jdbc.Driver");
									cfg.setProperty(
											"hibernate.connection.username",
											stonesoup_mysql_user);
									cfg.setProperty(
											"hibernate.connection.password",
											stonesoup_mysql_pass);
									cfg.setProperty(
											"hibernate.cache.provider_class",
											"org.hibernate.cache.NoCacheProvider");
									cfg.setProperty(
											"hibernate.current_session_context_class",
											"thread");
									cfg.setProperty(
											"hibernate.default_catalog",
											stonesoup_mysql_dbname);
									cfg.setProperty("org.hibernate.flushMode",
											"MANUAL");
									cfg.setProperty("hibernate.hbm2ddl.auto",
											"validate");
									cfg.setProperty(
											"hibernate.connection.pool_size",
											"1");
									cfg.addClass(SS_CWE_564_MYSQL.CustomerAndSuppliersByCity.class);
									cfg.addClass(SS_CWE_564_MYSQL.Invoices.class);
									cfg.addClass(SS_CWE_564_MYSQL.OrderDetailsExtended.class);
									cfg.addClass(SS_CWE_564_MYSQL.AlphabeticalListOfProducts.class);
									cfg.addClass(SS_CWE_564_MYSQL.OrdersQry.class);
									cfg.addClass(SS_CWE_564_MYSQL.CustomerDemographics.class);
									cfg.addClass(SS_CWE_564_MYSQL.Suppliers.class);
									cfg.addClass(SS_CWE_564_MYSQL.SalesByCategory.class);
									cfg.addClass(SS_CWE_564_MYSQL.ProductsByCategory.class);
									cfg.addClass(SS_CWE_564_MYSQL.SummaryOfSalesByQuarter.class);
									cfg.addClass(SS_CWE_564_MYSQL.SummaryOfSalesByYear.class);
									cfg.addClass(SS_CWE_564_MYSQL.Categories.class);
									cfg.addClass(SS_CWE_564_MYSQL.Shippers.class);
									cfg.addClass(SS_CWE_564_MYSQL.Employees.class);
									cfg.addClass(SS_CWE_564_MYSQL.Products.class);
									cfg.addClass(SS_CWE_564_MYSQL.CategorySalesFor1997.class);
									cfg.addClass(SS_CWE_564_MYSQL.OrderDetails.class);
									cfg.addClass(SS_CWE_564_MYSQL.Region.class);
									cfg.addClass(SS_CWE_564_MYSQL.QuarterlyOrders.class);
									cfg.addClass(SS_CWE_564_MYSQL.OrderSubtotals.class);
									cfg.addClass(SS_CWE_564_MYSQL.ProductsAboveAveragePrice.class);
									cfg.addClass(SS_CWE_564_MYSQL.Territories.class);
									cfg.addClass(SS_CWE_564_MYSQL.Customers.class);
									cfg.addClass(SS_CWE_564_MYSQL.Orders.class);
									cfg.addClass(SS_CWE_564_MYSQL.CurrentProductList.class);
									cfg.addClass(SS_CWE_564_MYSQL.SalesTotalsByAmount.class);
									cfg.addClass(SS_CWE_564_MYSQL.ProductSalesFor1997.class);
									ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
											.applySettings(cfg.getProperties())
											.buildServiceRegistry();
									org.hibernate.SessionFactory factory = cfg
											.buildSessionFactory(serviceRegistry);
									org.hibernate.Session session = factory
											.openSession();
									Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
									String hql = "from SS_CWE_564_MYSQL.Customers where country = '"
											+ ((String) rascallion_wristikin)
											+ "'";
									Tracer.tracepointVariableString("hql", hql);
									Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
									org.hibernate.Query query = session
											.createQuery(hql);
									Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
									@SuppressWarnings("rawtypes")
									java.util.Iterator iter = query.iterate();
									while (iter.hasNext()) {
										SS_CWE_564_MYSQL.Customers c = (SS_CWE_564_MYSQL.Customers) iter
												.next();
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getCustomerId()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getCompanyName()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getContactName()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getContactTitle()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getAddress()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getCity()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getRegion()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getPostalCode()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getCountry()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getPhone()));
										ShardIdCacheModule.wankapinOveryoung
												.print(String.format("%10s | ",
														c.getFax()));
										ShardIdCacheModule.wankapinOveryoung
												.println();
									}
									Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
									session.flush();
									session.close();
								} catch (org.hibernate.HibernateException he) {
									Tracer.tracepointError(he.getClass()
											.getName() + ": " + he.getMessage());
									ShardIdCacheModule.wankapinOveryoung
											.println("STONESOUP: Error accessing database.");
									he.printStackTrace(ShardIdCacheModule.wankapinOveryoung);
								}
							}
							Tracer.tracepointWeaknessEnd();
						}
					} finally {
						ShardIdCacheModule.wankapinOveryoung.close();
						if (daffodil_mycetous != null)
							daffodil_mycetous.stop(true);
					}
				}
			}
		}
		bind(ShardIdCache.class).asEagerSingleton();
    }
}
