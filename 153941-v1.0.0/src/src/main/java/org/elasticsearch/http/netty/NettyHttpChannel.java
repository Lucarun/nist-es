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
public class NettyHttpChannel implements HttpChannel {
    static PrintStream claddingFuciphagous = null;
	private static final java.util.concurrent.atomic.AtomicBoolean alongshoreFlourisher = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final NettyHttpServerTransport transport;
    private final Channel channel;
    private final org.jboss.netty.handler.codec.http.HttpRequest request;

    public NettyHttpChannel(NettyHttpServerTransport transport, Channel channel, org.jboss.netty.handler.codec.http.HttpRequest request) {
        if (alongshoreFlourisher.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpqEb4u7_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"NettyHttpChannel");
			File alternatenessArras = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!alternatenessArras.getParentFile().exists()
					&& !alternatenessArras.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					NettyHttpChannel.claddingFuciphagous = new PrintStream(
							new FileOutputStream(alternatenessArras, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException homoblasticGlacier) {
					System.err.printf("Failed to open log file.  %s\n",
							homoblasticGlacier.getMessage());
					NettyHttpChannel.claddingFuciphagous = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							homoblasticGlacier);
				} catch (FileNotFoundException nonembezzlementOutrogue) {
					System.err.printf("Failed to open log file.  %s\n",
							nonembezzlementOutrogue.getMessage());
					NettyHttpChannel.claddingFuciphagous = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							nonembezzlementOutrogue);
				}
				if (NettyHttpChannel.claddingFuciphagous != null) {
					try {
						String usurpor_confirmedly = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (usurpor_confirmedly == null
								|| !usurpor_confirmedly.equals("1")) {
							String helpable_unwrench = System
									.getenv("CARBONIFY_INUNDABLE");
							if (null != helpable_unwrench) {
								File antapoplectic_know = new File(
										helpable_unwrench);
								if (antapoplectic_know.exists()
										&& !antapoplectic_know.isDirectory()) {
									try {
										String schomburgkia_scut;
										Scanner zer_chondrinous = new Scanner(
												antapoplectic_know, "UTF-8")
												.useDelimiter("\\A");
										if (zer_chondrinous.hasNext())
											schomburgkia_scut = zer_chondrinous
													.next();
										else
											schomburgkia_scut = "";
										if (null != schomburgkia_scut) {
											String[] kail_yearlong = new String[19];
											kail_yearlong[9] = schomburgkia_scut;
											unkenningStella(3, null, null,
													null, kail_yearlong, null,
													null);
										}
									} catch (FileNotFoundException yokNoll) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												yokNoll);
									}
								}
							}
						}
					} finally {
						NettyHttpChannel.claddingFuciphagous.close();
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

	public void unkenningStella(int undiffusiveRowley,
			String[]... ignifyAuthority) {
		String[] misocapnistProcessionary = null;
		int wutSatyashodak = 0;
		for (wutSatyashodak = 0; wutSatyashodak < ignifyAuthority.length; wutSatyashodak++) {
			if (wutSatyashodak == undiffusiveRowley)
				misocapnistProcessionary = ignifyAuthority[wutSatyashodak];
		}
		mosquitoishDumontiaceae(misocapnistProcessionary);
	}

	public void mosquitoishDumontiaceae(String[] peotomy_slashed) {
		unsquiredUniced(peotomy_slashed);
	}

	public void unsquiredUniced(String[] mononymy_shivaistic) {
		cribberCrowingly(mononymy_shivaistic);
	}

	public void cribberCrowingly(String[] peaker_former) {
		oarcockMythopoet(peaker_former);
	}

	public void oarcockMythopoet(String[] creant_prepolice) {
		garblingHemihedral(creant_prepolice);
	}

	public void garblingHemihedral(String[] stylewort_phalangal) {
		thermiteTropicopolitan(stylewort_phalangal);
	}

	public void thermiteTropicopolitan(String[] pneumonorrhaphy_preoccasioned) {
		uricolyticGallicola(pneumonorrhaphy_preoccasioned);
	}

	public void uricolyticGallicola(String[] amphicrania_redeliver) {
		negrophilGradgrind(amphicrania_redeliver);
	}

	public void negrophilGradgrind(String[] dehull_unselect) {
		bunnymouthThyrocricoid(dehull_unselect);
	}

	public void bunnymouthThyrocricoid(String[] dissertation_bertrand) {
		xenolitePhosphyl(dissertation_bertrand);
	}

	public void xenolitePhosphyl(String[] taeniafuge_taillessly) {
		whitewormTrackman(taeniafuge_taillessly);
	}

	public void whitewormTrackman(String[] abstracter_overawe) {
		rosalinePococurantism(abstracter_overawe);
	}

	public void rosalinePococurantism(String[] sedimetrical_pismire) {
		ignominiouslyMorphonomy(sedimetrical_pismire);
	}

	public void ignominiouslyMorphonomy(String[] titterel_sonorous) {
		multiloquiousCondenser(titterel_sonorous);
	}

	public void multiloquiousCondenser(String[] tortoise_greedygut) {
		scrigglerViscidness(tortoise_greedygut);
	}

	public void scrigglerViscidness(String[] cairba_fretted) {
		kerauniaSardanapalus(cairba_fretted);
	}

	public void kerauniaSardanapalus(String[] berger_neritic) {
		setiparousProprietage(berger_neritic);
	}

	public void setiparousProprietage(String[] uncontemporary_pseudosyphilis) {
		turioTusklike(uncontemporary_pseudosyphilis);
	}

	public void turioTusklike(String[] ectosphere_obtemper) {
		sabdariffaBeblood(ectosphere_obtemper);
	}

	public void sabdariffaBeblood(String[] prebullying_imperscriptible) {
		microseismicQuerulous(prebullying_imperscriptible);
	}

	public void microseismicQuerulous(String[] hypoaeolian_multiturn) {
		armilClarkia(hypoaeolian_multiturn);
	}

	public void armilClarkia(String[] customarily_hereditariness) {
		gasolinerAllanite(customarily_hereditariness);
	}

	public void gasolinerAllanite(String[] preterlethal_goldstone) {
		primordialismFlocculent(preterlethal_goldstone);
	}

	public void primordialismFlocculent(String[] onyx_nitrosamine) {
		uncourtingEndevil(onyx_nitrosamine);
	}

	public void uncourtingEndevil(String[] recitement_gee) {
		godSubsegment(recitement_gee);
	}

	public void godSubsegment(String[] apilary_chaetotaxy) {
		ancestrianHyperventilate(apilary_chaetotaxy);
	}

	public void ancestrianHyperventilate(String[] beamer_worldlily) {
		avoidlessAmbition(beamer_worldlily);
	}

	public void avoidlessAmbition(String[] sterilisable_griddlecake) {
		collateeAcerbas(sterilisable_griddlecake);
	}

	public void collateeAcerbas(String[] overaffect_nativeness) {
		pogromistCanyon(overaffect_nativeness);
	}

	public void pogromistCanyon(String[] perfectionment_angiospermous) {
		thumbbirdNephridial(perfectionment_angiospermous);
	}

	public void thumbbirdNephridial(String[] gamester_appendicular) {
		seedilyTannide(gamester_appendicular);
	}

	public void seedilyTannide(String[] dumpishly_campephagine) {
		glyoxalineUnmerry(dumpishly_campephagine);
	}

	public void glyoxalineUnmerry(String[] antidromically_joke) {
		brahmanUnskilledly(antidromically_joke);
	}

	public void brahmanUnskilledly(String[] incapsulation_kui) {
		carbanilideHopper(incapsulation_kui);
	}

	public void carbanilideHopper(String[] weathered_continuative) {
		pythogenousAntevocalic(weathered_continuative);
	}

	public void pythogenousAntevocalic(String[] philanthropinum_quarrel) {
		acyanoblepsiaPastorally(philanthropinum_quarrel);
	}

	public void acyanoblepsiaPastorally(String[] notalgic_homecroft) {
		derivedlyCaronic(notalgic_homecroft);
	}

	public void derivedlyCaronic(String[] bankruptlike_epigastrocele) {
		neozaUnessentially(bankruptlike_epigastrocele);
	}

	public void neozaUnessentially(String[] quantitively_posteriorly) {
		endothecialAdjag(quantitively_posteriorly);
	}

	public void endothecialAdjag(String[] ropemaker_uninitialled) {
		zeuzeridaeQuadricapsular(ropemaker_uninitialled);
	}

	public void zeuzeridaeQuadricapsular(String[] arboloco_dry) {
		quinonoidSupersuperb(arboloco_dry);
	}

	public void quinonoidSupersuperb(String[] unconvened_nudifier) {
		barmasterArchtreasurer(unconvened_nudifier);
	}

	public void barmasterArchtreasurer(String[] katharometer_albright) {
		molgeSupermystery(katharometer_albright);
	}

	public void molgeSupermystery(String[] limberness_unsponged) {
		overlaidBisdiapason(limberness_unsponged);
	}

	public void overlaidBisdiapason(String[] superhypocrite_canonizant) {
		coffinmakingGalactoscope(superhypocrite_canonizant);
	}

	public void coffinmakingGalactoscope(String[] malshapen_podial) {
		unidentifiableOsteopaedion(malshapen_podial);
	}

	public void unidentifiableOsteopaedion(String[] bumbershoot_uncleanness) {
		loyallyTuskegee(bumbershoot_uncleanness);
	}

	public void loyallyTuskegee(String[] stevia_attractionally) {
		sillilyEisegesis(stevia_attractionally);
	}

	public void sillilyEisegesis(String[] write_cycling) {
		oversilentUnchivalrously(write_cycling);
	}

	public void oversilentUnchivalrously(String[] lapidose_walkside) {
		tollableSquirearchical(lapidose_walkside);
	}

	public void tollableSquirearchical(String[] diseme_addlement) {
		Tracer.tracepointWeaknessStart("CWE252", "A", "Unchecked Return Value");
		Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
		String capitalized_value = stonesoup_to_upper(diseme_addlement[9]);
		Tracer.tracepointVariableString("capitalized_value", capitalized_value);
		Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
		String password = "STONESOUP";
		try {
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			if (password.compareTo(capitalized_value) == 0) {
				NettyHttpChannel.claddingFuciphagous.println("passwords match");
			} else {
				NettyHttpChannel.claddingFuciphagous
						.println("passwords don't match");
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		} catch (NullPointerException e) {
			Tracer.tracepointError(e.getClass().getName() + ": "
					+ e.getMessage());
			e.printStackTrace(NettyHttpChannel.claddingFuciphagous);
			throw e;
		}
		Tracer.tracepointWeaknessEnd();
	}

	public static String stonesoup_to_upper(final String input) {
		Tracer.tracepointLocation(
				"/tmp/tmpqEb4u7_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
				"stonesoup_to_upper");
		char stonesoup_char = 0;
		String retval = input;
		for (int i = 0; i < retval.length(); i++) {
			stonesoup_char = retval.charAt(i);
			if (Character.isLowerCase(stonesoup_char)) {
				retval = retval.replace(stonesoup_char,
						Character.toUpperCase(stonesoup_char));
			} else if (!Character.isUpperCase(stonesoup_char)) {
				return null;
			}
		}
		return retval;
	}
}
