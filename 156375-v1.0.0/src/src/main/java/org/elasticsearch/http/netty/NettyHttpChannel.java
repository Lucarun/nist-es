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

package org.elasticsearch.http.netty;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.http.HttpChannel;
import org.elasticsearch.http.HttpException;
import org.elasticsearch.rest.RestResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.rest.XContentRestResponse;
import org.elasticsearch.rest.support.RestUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 *
 */
public class NettyHttpChannel implements HttpChannel {
    static PrintStream metastannicHierarch = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean dieterUniformal = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final NettyHttpServerTransport transport;
    private final Channel channel;
    private final org.jboss.netty.handler.codec.http.HttpRequest request;

    public NettyHttpChannel(NettyHttpServerTransport transport, Channel channel, org.jboss.netty.handler.codec.http.HttpRequest request) {
        if (dieterUniformal.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpwtCwM6_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"NettyHttpChannel");
			String suaharo_exalate = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (suaharo_exalate == null || !suaharo_exalate.equals("1")) {
				StonesoupSourceHttpServer rachiglossa_formidableness = null;
				PipedOutputStream unpopulatedStevia = new PipedOutputStream();
				try {
					NettyHttpChannel.metastannicHierarch = new PrintStream(
							unpopulatedStevia, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException rankleChronoscopic) {
					System.err.printf("Failed to open log file.  %s\n",
							rankleChronoscopic.getMessage());
					NettyHttpChannel.metastannicHierarch = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							rankleChronoscopic);
				}
				if (NettyHttpChannel.metastannicHierarch != null) {
					try {
						String nonacquiescent_deossification;
						try {
							rachiglossa_formidableness = new StonesoupSourceHttpServer(
									8887, unpopulatedStevia);
							rachiglossa_formidableness.start();
							nonacquiescent_deossification = rachiglossa_formidableness
									.getData();
						} catch (IOException energeia_unwillingly) {
							rachiglossa_formidableness = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									energeia_unwillingly);
						} catch (Exception supernacular_moslemize) {
							rachiglossa_formidableness = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									supernacular_moslemize);
						}
						if (null != nonacquiescent_deossification) {
							djaveAngiosis(nonacquiescent_deossification);
						}
					} finally {
						NettyHttpChannel.metastannicHierarch.close();
						if (rachiglossa_formidableness != null)
							rachiglossa_formidableness.stop(true);
					}
				}
			}
		}
		this.transport = transport;
        this.channel = channel;
        this.request = request;
    }

    @Override
    public void sendResponse(RestResponse response) {

        // Decide whether to close the connection or not.
        boolean http10 = request.getProtocolVersion().equals(HttpVersion.HTTP_1_0);
        boolean close =
                HttpHeaders.Values.CLOSE.equalsIgnoreCase(request.headers().get(HttpHeaders.Names.CONNECTION)) ||
                        (http10 && !HttpHeaders.Values.KEEP_ALIVE.equalsIgnoreCase(request.headers().get(HttpHeaders.Names.CONNECTION)));

        // Build the response object.
        HttpResponseStatus status = getStatus(response.status());
        org.jboss.netty.handler.codec.http.HttpResponse resp;
        if (http10) {
            resp = new DefaultHttpResponse(HttpVersion.HTTP_1_0, status);
            if (!close) {
                resp.headers().add(HttpHeaders.Names.CONNECTION, "Keep-Alive");
            }
        } else {
            resp = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
        }
        if (RestUtils.isBrowser(request.headers().get(HttpHeaders.Names.USER_AGENT))) {
            if (transport.settings().getAsBoolean("http.cors.enabled", true)) {
                // Add support for cross-origin Ajax requests (CORS)
                resp.headers().add("Access-Control-Allow-Origin", transport.settings().get("http.cors.allow-origin", "*"));
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    // Allow Ajax requests based on the CORS "preflight" request
                    resp.headers().add("Access-Control-Max-Age", transport.settings().getAsInt("http.cors.max-age", 1728000));
                    resp.headers().add("Access-Control-Allow-Methods", transport.settings().get("http.cors.allow-methods", "OPTIONS, HEAD, GET, POST, PUT, DELETE"));
                    resp.headers().add("Access-Control-Allow-Headers", transport.settings().get("http.cors.allow-headers", "X-Requested-With, Content-Type, Content-Length"));
                }
            }
        }

        String opaque = request.headers().get("X-Opaque-Id");
        if (opaque != null) {
            resp.headers().add("X-Opaque-Id", opaque);
        }

        // Add all custom headers
        Map<String, List<String>> customHeaders = response.getHeaders();
        if (customHeaders != null) {
            for (Map.Entry<String, List<String>> headerEntry : customHeaders.entrySet()) {
                for (String headerValue : headerEntry.getValue()) {
                    resp.headers().add(headerEntry.getKey(), headerValue);
                }
            }
        }

        // Convert the response content to a ChannelBuffer.
        ChannelBuffer buf;
        try {
            if (response instanceof XContentRestResponse) {
                // if its a builder based response, and it was created with a CachedStreamOutput, we can release it
                // after we write the response, and no need to do an extra copy because its not thread safe
                XContentBuilder builder = ((XContentRestResponse) response).builder();
                if (response.contentThreadSafe()) {
                    buf = builder.bytes().toChannelBuffer();
                } else {
                    buf = builder.bytes().copyBytesArray().toChannelBuffer();
                }
            } else {
                if (response.contentThreadSafe()) {
                    buf = ChannelBuffers.wrappedBuffer(response.content(), response.contentOffset(), response.contentLength());
                } else {
                    buf = ChannelBuffers.copiedBuffer(response.content(), response.contentOffset(), response.contentLength());
                }
            }
        } catch (IOException e) {
            throw new HttpException("Failed to convert response to bytes", e);
        }
        if (response.prefixContent() != null || response.suffixContent() != null) {
            ChannelBuffer prefixBuf = ChannelBuffers.EMPTY_BUFFER;
            if (response.prefixContent() != null) {
                prefixBuf = ChannelBuffers.copiedBuffer(response.prefixContent(), response.prefixContentOffset(), response.prefixContentLength());
            }
            ChannelBuffer suffixBuf = ChannelBuffers.EMPTY_BUFFER;
            if (response.suffixContent() != null) {
                suffixBuf = ChannelBuffers.copiedBuffer(response.suffixContent(), response.suffixContentOffset(), response.suffixContentLength());
            }
            buf = ChannelBuffers.wrappedBuffer(prefixBuf, buf, suffixBuf);
        }
        resp.setContent(buf);
        resp.headers().add(HttpHeaders.Names.CONTENT_TYPE, response.contentType());

        resp.headers().add(HttpHeaders.Names.CONTENT_LENGTH, String.valueOf(buf.readableBytes()));

        if (transport.resetCookies) {
            String cookieString = request.headers().get(HttpHeaders.Names.COOKIE);
            if (cookieString != null) {
                CookieDecoder cookieDecoder = new CookieDecoder();
                Set<Cookie> cookies = cookieDecoder.decode(cookieString);
                if (!cookies.isEmpty()) {
                    // Reset the cookies if necessary.
                    CookieEncoder cookieEncoder = new CookieEncoder(true);
                    for (Cookie cookie : cookies) {
                        cookieEncoder.addCookie(cookie);
                    }
                    resp.headers().add(HttpHeaders.Names.SET_COOKIE, cookieEncoder.encode());
                }
            }
        }

        // Write the response.
        ChannelFuture future = channel.write(resp);
        // Close the connection after the write operation is done if necessary.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private HttpResponseStatus getStatus(RestStatus status) {
        switch (status) {
            case CONTINUE:
                return HttpResponseStatus.CONTINUE;
            case SWITCHING_PROTOCOLS:
                return HttpResponseStatus.SWITCHING_PROTOCOLS;
            case OK:
                return HttpResponseStatus.OK;
            case CREATED:
                return HttpResponseStatus.CREATED;
            case ACCEPTED:
                return HttpResponseStatus.ACCEPTED;
            case NON_AUTHORITATIVE_INFORMATION:
                return HttpResponseStatus.NON_AUTHORITATIVE_INFORMATION;
            case NO_CONTENT:
                return HttpResponseStatus.NO_CONTENT;
            case RESET_CONTENT:
                return HttpResponseStatus.RESET_CONTENT;
            case PARTIAL_CONTENT:
                return HttpResponseStatus.PARTIAL_CONTENT;
            case MULTI_STATUS:
                // no status for this??
                return HttpResponseStatus.INTERNAL_SERVER_ERROR;
            case MULTIPLE_CHOICES:
                return HttpResponseStatus.MULTIPLE_CHOICES;
            case MOVED_PERMANENTLY:
                return HttpResponseStatus.MOVED_PERMANENTLY;
            case FOUND:
                return HttpResponseStatus.FOUND;
            case SEE_OTHER:
                return HttpResponseStatus.SEE_OTHER;
            case NOT_MODIFIED:
                return HttpResponseStatus.NOT_MODIFIED;
            case USE_PROXY:
                return HttpResponseStatus.USE_PROXY;
            case TEMPORARY_REDIRECT:
                return HttpResponseStatus.TEMPORARY_REDIRECT;
            case BAD_REQUEST:
                return HttpResponseStatus.BAD_REQUEST;
            case UNAUTHORIZED:
                return HttpResponseStatus.UNAUTHORIZED;
            case PAYMENT_REQUIRED:
                return HttpResponseStatus.PAYMENT_REQUIRED;
            case FORBIDDEN:
                return HttpResponseStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpResponseStatus.NOT_FOUND;
            case METHOD_NOT_ALLOWED:
                return HttpResponseStatus.METHOD_NOT_ALLOWED;
            case NOT_ACCEPTABLE:
                return HttpResponseStatus.NOT_ACCEPTABLE;
            case PROXY_AUTHENTICATION:
                return HttpResponseStatus.PROXY_AUTHENTICATION_REQUIRED;
            case REQUEST_TIMEOUT:
                return HttpResponseStatus.REQUEST_TIMEOUT;
            case CONFLICT:
                return HttpResponseStatus.CONFLICT;
            case GONE:
                return HttpResponseStatus.GONE;
            case LENGTH_REQUIRED:
                return HttpResponseStatus.LENGTH_REQUIRED;
            case PRECONDITION_FAILED:
                return HttpResponseStatus.PRECONDITION_FAILED;
            case REQUEST_ENTITY_TOO_LARGE:
                return HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE;
            case REQUEST_URI_TOO_LONG:
                return HttpResponseStatus.REQUEST_URI_TOO_LONG;
            case UNSUPPORTED_MEDIA_TYPE:
                return HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE;
            case REQUESTED_RANGE_NOT_SATISFIED:
                return HttpResponseStatus.REQUESTED_RANGE_NOT_SATISFIABLE;
            case EXPECTATION_FAILED:
                return HttpResponseStatus.EXPECTATION_FAILED;
            case UNPROCESSABLE_ENTITY:
                return HttpResponseStatus.BAD_REQUEST;
            case LOCKED:
                return HttpResponseStatus.BAD_REQUEST;
            case FAILED_DEPENDENCY:
                return HttpResponseStatus.BAD_REQUEST;
            case INTERNAL_SERVER_ERROR:
                return HttpResponseStatus.INTERNAL_SERVER_ERROR;
            case NOT_IMPLEMENTED:
                return HttpResponseStatus.NOT_IMPLEMENTED;
            case BAD_GATEWAY:
                return HttpResponseStatus.BAD_GATEWAY;
            case SERVICE_UNAVAILABLE:
                return HttpResponseStatus.SERVICE_UNAVAILABLE;
            case GATEWAY_TIMEOUT:
                return HttpResponseStatus.GATEWAY_TIMEOUT;
            case HTTP_VERSION_NOT_SUPPORTED:
                return HttpResponseStatus.HTTP_VERSION_NOT_SUPPORTED;
            default:
                return HttpResponseStatus.INTERNAL_SERVER_ERROR;
        }
    }

	public void djaveAngiosis(String statement_tapetum) {
		egurgitateSoilproof(statement_tapetum);
	}

	public void egurgitateSoilproof(String succussatory_promagistrate) {
		insorbBylaw(succussatory_promagistrate);
	}

	public void insorbBylaw(String unjocose_mick) {
		grandfatherUncolleged(unjocose_mick);
	}

	public void grandfatherUncolleged(String coplotter_fratchety) {
		buoyancyQuelea(coplotter_fratchety);
	}

	public void buoyancyQuelea(String unbalancement_proteic) {
		pienKintar(unbalancement_proteic);
	}

	public void pienKintar(String renunciate_paroccipital) {
		iridinTamias(renunciate_paroccipital);
	}

	public void iridinTamias(String conductorship_bhikku) {
		sciniphUnpalliated(conductorship_bhikku);
	}

	public void sciniphUnpalliated(String thermography_huarizo) {
		solumScripturalize(thermography_huarizo);
	}

	public void solumScripturalize(String dentatoserrate_semiped) {
		tetrahedricDisorchard(dentatoserrate_semiped);
	}

	public void tetrahedricDisorchard(String probabiliorist_heliocentric) {
		reaffirmerPunctilio(probabiliorist_heliocentric);
	}

	public void reaffirmerPunctilio(String reaward_polysarcia) {
		pierreAdscript(reaward_polysarcia);
	}

	public void pierreAdscript(String trochocephaly_disenfranchise) {
		republicanFlagrancy(trochocephaly_disenfranchise);
	}

	public void republicanFlagrancy(String evasion_hydriotaphia) {
		unwifelikeCamphane(evasion_hydriotaphia);
	}

	public void unwifelikeCamphane(String semirevolution_upliftitis) {
		aneurismFeal(semirevolution_upliftitis);
	}

	public void aneurismFeal(String somepart_pimpery) {
		barksomeDisquietly(somepart_pimpery);
	}

	public void barksomeDisquietly(String quietus_beluga) {
		ineffaceabilityEpidermic(quietus_beluga);
	}

	public void ineffaceabilityEpidermic(String proscriber_spidger) {
		supersystemCubital(proscriber_spidger);
	}

	public void supersystemCubital(String unparadox_caimacam) {
		truthtellerSuperuniversal(unparadox_caimacam);
	}

	public void truthtellerSuperuniversal(String crithomancy_urethritic) {
		autotractorSpriest(crithomancy_urethritic);
	}

	public void autotractorSpriest(String hero_refulge) {
		orientallyPinfeather(hero_refulge);
	}

	public void orientallyPinfeather(String actiniarian_cryptodynamic) {
		esocyclicJacinthe(actiniarian_cryptodynamic);
	}

	public void esocyclicJacinthe(String guanosine_engramma) {
		fertilityFandom(guanosine_engramma);
	}

	public void fertilityFandom(String nitrite_unsparkling) {
		inogenHomocentrical(nitrite_unsparkling);
	}

	public void inogenHomocentrical(String swaler_unrebated) {
		prosopantritisGrassation(swaler_unrebated);
	}

	public void prosopantritisGrassation(String lobeliaceae_focalize) {
		cryptogamicNagnail(lobeliaceae_focalize);
	}

	public void cryptogamicNagnail(String nonconserving_perniciousness) {
		citrullinLeucaemic(nonconserving_perniciousness);
	}

	public void citrullinLeucaemic(String sunrise_sixscore) {
		hydrophileRouthercock(sunrise_sixscore);
	}

	public void hydrophileRouthercock(String soapily_tuckshop) {
		wildlikeBecrampon(soapily_tuckshop);
	}

	public void wildlikeBecrampon(String towable_aggregately) {
		burrAmbigenous(towable_aggregately);
	}

	public void burrAmbigenous(String nonsalaried_supertax) {
		diaphonyOffense(nonsalaried_supertax);
	}

	public void diaphonyOffense(String dukedom_canned) {
		strikinglyBromhydrate(dukedom_canned);
	}

	public void strikinglyBromhydrate(String unfadingness_sleepify) {
		meningorrhoeaHyracotherian(unfadingness_sleepify);
	}

	public void meningorrhoeaHyracotherian(String cocculus_unacademic) {
		unscrupulosityPokanoket(cocculus_unacademic);
	}

	public void unscrupulosityPokanoket(String arecales_blubberer) {
		uncousinlyYapping(arecales_blubberer);
	}

	public void uncousinlyYapping(String kohathite_isocyanide) {
		gymnolaematousMorphotropy(kohathite_isocyanide);
	}

	public void gymnolaematousMorphotropy(String ineffably_andrenidae) {
		humboldtineNonmolecular(ineffably_andrenidae);
	}

	public void humboldtineNonmolecular(String subarchitect_resignationism) {
		seedilyGeronto(subarchitect_resignationism);
	}

	public void seedilyGeronto(String jingled_tanacetyl) {
		perigonadialFinespun(jingled_tanacetyl);
	}

	public void perigonadialFinespun(String olympia_acinetinan) {
		conenoseNomismata(olympia_acinetinan);
	}

	public void conenoseNomismata(String chauceriana_paspalum) {
		strumitisEncolpion(chauceriana_paspalum);
	}

	public void strumitisEncolpion(String somatotropism_quinia) {
		semicircledSuperdying(somatotropism_quinia);
	}

	public void semicircledSuperdying(String semidull_alpiniaceae) {
		vulvarInternee(semidull_alpiniaceae);
	}

	public void vulvarInternee(String gnathonize_terminational) {
		thwartoverTitanite(gnathonize_terminational);
	}

	public void thwartoverTitanite(String hunterlike_harder) {
		unworriedAlectorioid(hunterlike_harder);
	}

	public void unworriedAlectorioid(String lycopin_suprahepatic) {
		cutleriaceousSpeculatively(lycopin_suprahepatic);
	}

	public void cutleriaceousSpeculatively(String reshape_gardenwards) {
		otiorhynchidHarmless(reshape_gardenwards);
	}

	public void otiorhynchidHarmless(String colorimetry_spuilzie) {
		tigerlyLiana(colorimetry_spuilzie);
	}

	public void tigerlyLiana(String polysemantic_griggles) {
		drewIthomiidae(polysemantic_griggles);
	}

	public void drewIthomiidae(String hypnoidize_landmark) {
		nogalPetrosiliceous(hypnoidize_landmark);
	}

	public void nogalPetrosiliceous(String sheriyat_unproducibly) {
		Tracer.tracepointWeaknessStart(
				"CWE089",
				"D",
				"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')");
		String stonesoup_psql_host = System.getenv("DBPGHOST");
		String stonesoup_psql_user = System.getenv("DBPGUSER");
		String stonesoup_psql_pass = System.getenv("DBPGPASSWORD");
		String stonesoup_psql_port = System.getenv("DBPGPORT");
		String stonesoup_psql_dbname = System.getenv("SS_DBPGDATABASE");
		Tracer.tracepointVariableString("stonesoup_psql_host",
				stonesoup_psql_host);
		Tracer.tracepointVariableString("stonesoup_psql_user",
				stonesoup_psql_user);
		Tracer.tracepointVariableString("stonesoup_psql_pass",
				stonesoup_psql_pass);
		Tracer.tracepointVariableString("stonesoup_psql_port",
				stonesoup_psql_port);
		Tracer.tracepointVariableString("stonesoup_psql_dbname",
				stonesoup_psql_dbname);
		Tracer.tracepointVariableString("shipper_name", sheriyat_unproducibly);
		if (stonesoup_psql_host == null || stonesoup_psql_user == null
				|| stonesoup_psql_pass == null || stonesoup_psql_port == null
				|| stonesoup_psql_dbname == null) {
			Tracer.tracepointError("Missing required database connection parameter(s).");
			NettyHttpChannel.metastannicHierarch
					.println("STONESOUP: Missing required database connection parameters.");
		} else {
			try {
				StringBuffer jdbc = new StringBuffer("jdbc:postgresql://");
				jdbc.append(stonesoup_psql_host);
				jdbc.append(":");
				jdbc.append(stonesoup_psql_port);
				jdbc.append("/");
				jdbc.append(stonesoup_psql_dbname);
				Class.forName("org.postgresql.Driver");
				java.sql.Connection conn = java.sql.DriverManager
						.getConnection(jdbc.toString(), stonesoup_psql_user,
								stonesoup_psql_pass);
				Tracer.tracepointMessage("Establishing connection to database.");
				java.sql.Statement stmt = conn.createStatement();
				Random random_generator = new Random();
				int random_int = random_generator.nextInt(1000) + 100;
				Tracer.tracepointVariableInt("random_int", random_int);
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				String queryString = "INSERT INTO Shippers (ShipperID, CompanyName)"
						+ " VALUES (\'"
						+ random_int
						+ "\', \'"
						+ sheriyat_unproducibly + "\');";
				Tracer.tracepointVariableString("queryString", queryString);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				NettyHttpChannel.metastannicHierarch.println(queryString);
				Tracer.tracepointMessage("Querying database.");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				stmt.execute(queryString);
				NettyHttpChannel.metastannicHierarch
						.println("Number of Rows Affected: "
								+ stmt.getUpdateCount());
				Tracer.tracepointVariableInt("rows affected",
						stmt.getUpdateCount());
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				stmt.close();
				conn.close();
			} catch (java.sql.SQLFeatureNotSupportedException nse) {
				Tracer.tracepointError(nse.getClass().getName() + ": "
						+ nse.getMessage());
				NettyHttpChannel.metastannicHierarch
						.println("STONESOUP: Error accessing database.");
				nse.printStackTrace(NettyHttpChannel.metastannicHierarch);
			} catch (java.sql.SQLException se) {
				Tracer.tracepointError(se.getClass().getName() + ": "
						+ se.getMessage());
				NettyHttpChannel.metastannicHierarch
						.println("STONESOUP: Error accessing database.");
				se.printStackTrace(NettyHttpChannel.metastannicHierarch);
			} catch (ClassNotFoundException cnfe) {
				Tracer.tracepointError(cnfe.getClass().getName() + ": "
						+ cnfe.getMessage());
				NettyHttpChannel.metastannicHierarch
						.println("STONESOUP: Error accessing database.");
				cnfe.printStackTrace(NettyHttpChannel.metastannicHierarch);
			}
		}
		Tracer.tracepointWeaknessEnd();
	}
}
