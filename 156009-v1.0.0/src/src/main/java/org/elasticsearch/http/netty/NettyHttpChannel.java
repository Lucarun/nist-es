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

/**
 *
 */
public class NettyHttpChannel implements HttpChannel {
    private static final int olethreutidae_berycomorphi = 3;
	static PrintStream autodetectorPseudopsia = null;
	private static final java.util.concurrent.atomic.AtomicBoolean stoneableOrleanistic = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final NettyHttpServerTransport transport;
    private final Channel channel;
    private final org.jboss.netty.handler.codec.http.HttpRequest request;

    public NettyHttpChannel(NettyHttpServerTransport transport, Channel channel, org.jboss.netty.handler.codec.http.HttpRequest request) {
        if (stoneableOrleanistic.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpTuODw1_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"NettyHttpChannel");
			File harlequinesquePlaything = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!harlequinesquePlaything.getParentFile().exists()
					&& !harlequinesquePlaything.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					NettyHttpChannel.autodetectorPseudopsia = new PrintStream(
							new FileOutputStream(harlequinesquePlaything, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException tewsomeHelen) {
					System.err.printf("Failed to open log file.  %s\n",
							tewsomeHelen.getMessage());
					NettyHttpChannel.autodetectorPseudopsia = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.", tewsomeHelen);
				} catch (FileNotFoundException novemberHounding) {
					System.err.printf("Failed to open log file.  %s\n",
							novemberHounding.getMessage());
					NettyHttpChannel.autodetectorPseudopsia = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							novemberHounding);
				}
				if (NettyHttpChannel.autodetectorPseudopsia != null) {
					try {
						String excreta_bleck = System
								.getenv("PREHENSIVENESS_MULTIJUGATE");
						if (null != excreta_bleck) {
							Object whaleman_philobrutish = excreta_bleck;
							Object[] landocrat_coinheritance = new Object[23];
							landocrat_coinheritance[olethreutidae_berycomorphi] = whaleman_philobrutish;
							EsophagotomyOverprominent persecutiveness_anhidrosis = new EsophagotomyOverprominent();
							persecutiveness_anhidrosis
									.rivetFinishable(landocrat_coinheritance);
						}
					} finally {
						NettyHttpChannel.autodetectorPseudopsia.close();
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

	public static class EsophagotomyOverprominent {
		public void rivetFinishable(Object[] attendingly_abronia) {
			SkymanSlandering plaiter_tuilyie = new SkymanSlandering();
			plaiter_tuilyie.sophicalAgronomical(attendingly_abronia);
		}
	}

	public static class SkymanSlandering {
		public void sophicalAgronomical(Object[] rodolph_petalodontidae) {
			ThetaAnzanian duopoly_patagon = new ThetaAnzanian();
			duopoly_patagon.uterovesicalDorsiparous(rodolph_petalodontidae);
		}
	}

	public static class ThetaAnzanian {
		public void uterovesicalDorsiparous(Object[] aggrandizable_apexed) {
			SemivitreousRodless haemony_suberiferous = new SemivitreousRodless();
			haemony_suberiferous
					.rationalizationTemiskaming(aggrandizable_apexed);
		}
	}

	public static class SemivitreousRodless {
		public void rationalizationTemiskaming(Object[] xinca_zenaidinae) {
			BrazilianZaptoeca stereotype_sart = new BrazilianZaptoeca();
			stereotype_sart.downgradeAortoclasia(xinca_zenaidinae);
		}
	}

	public static class BrazilianZaptoeca {
		public void downgradeAortoclasia(Object[] pruinose_engreen) {
			ScoundrellyOutromance meretriciously_pumice = new ScoundrellyOutromance();
			meretriciously_pumice.calaveriteBarouchet(pruinose_engreen);
		}
	}

	public static class ScoundrellyOutromance {
		public void calaveriteBarouchet(Object[] demonstratory_organosilicon) {
			AntiaphthicCoequation hemidysergia_isonicotinic = new AntiaphthicCoequation();
			hemidysergia_isonicotinic
					.operantRingsail(demonstratory_organosilicon);
		}
	}

	public static class AntiaphthicCoequation {
		public void operantRingsail(Object[] ineloquent_marie) {
			MonomorphismOverdoor acromiohyoid_fizzle = new MonomorphismOverdoor();
			acromiohyoid_fizzle.ingomarBellote(ineloquent_marie);
		}
	}

	public static class MonomorphismOverdoor {
		public void ingomarBellote(Object[] actualize_tetrabranchia) {
			PhallismChumulu gemarist_egoize = new PhallismChumulu();
			gemarist_egoize.khatriCastellanship(actualize_tetrabranchia);
		}
	}

	public static class PhallismChumulu {
		public void khatriCastellanship(Object[] unethylated_amply) {
			CordaitaceousAortorrhaphy rebounce_caririan = new CordaitaceousAortorrhaphy();
			rebounce_caririan.onzaRadiolitic(unethylated_amply);
		}
	}

	public static class CordaitaceousAortorrhaphy {
		public void onzaRadiolitic(Object[] drolly_betoil) {
			PlauditorRamular masting_downheaded = new PlauditorRamular();
			masting_downheaded.tibiofibularEthoxycaffeine(drolly_betoil);
		}
	}

	public static class PlauditorRamular {
		public void tibiofibularEthoxycaffeine(Object[] camphor_infern) {
			FierasferidaeTrichoptera whirlgig_fulcrum = new FierasferidaeTrichoptera();
			whirlgig_fulcrum.unshawlSchatchen(camphor_infern);
		}
	}

	public static class FierasferidaeTrichoptera {
		public void unshawlSchatchen(Object[] randy_arake) {
			EctosarcousStoma pathetist_frutescence = new EctosarcousStoma();
			pathetist_frutescence.starlightNeuropteroidea(randy_arake);
		}
	}

	public static class EctosarcousStoma {
		public void starlightNeuropteroidea(
				Object[] preabundantly_imparticipable) {
			CotulaTendosynovitis blowpipe_undecayedness = new CotulaTendosynovitis();
			blowpipe_undecayedness
					.pondmanTrilamellar(preabundantly_imparticipable);
		}
	}

	public static class CotulaTendosynovitis {
		public void pondmanTrilamellar(Object[] aswim_cancellous) {
			TitanotheriumHomrai fitchew_ethnologist = new TitanotheriumHomrai();
			fitchew_ethnologist.reswearBackheel(aswim_cancellous);
		}
	}

	public static class TitanotheriumHomrai {
		public void reswearBackheel(Object[] cliftonite_antipolar) {
			FrondageRockably unhoofed_reliberate = new FrondageRockably();
			unhoofed_reliberate.stomapodaSutten(cliftonite_antipolar);
		}
	}

	public static class FrondageRockably {
		public void stomapodaSutten(Object[] piney_unstudied) {
			AcrogenDedicational aneurism_postnaris = new AcrogenDedicational();
			aneurism_postnaris.northwardRemonstration(piney_unstudied);
		}
	}

	public static class AcrogenDedicational {
		public void northwardRemonstration(Object[] ornithodoros_zooperal) {
			ScharfUndisconcerted insteam_nonnational = new ScharfUndisconcerted();
			insteam_nonnational.baccatedGanoidean(ornithodoros_zooperal);
		}
	}

	public static class ScharfUndisconcerted {
		public void baccatedGanoidean(Object[] sendal_archdefender) {
			AlcuinianEpichoric chrysosperm_leeroway = new AlcuinianEpichoric();
			chrysosperm_leeroway.wormweedTumultuousness(sendal_archdefender);
		}
	}

	public static class AlcuinianEpichoric {
		public void wormweedTumultuousness(Object[] irrigationist_heloderma) {
			HemidactylusJocko idiorepulsive_matmaking = new HemidactylusJocko();
			idiorepulsive_matmaking.beerishOnionet(irrigationist_heloderma);
		}
	}

	public static class HemidactylusJocko {
		public void beerishOnionet(Object[] tag_retheness) {
			IodosobenzeneEpispore fayumic_karyogamy = new IodosobenzeneEpispore();
			fayumic_karyogamy.interdictAggressive(tag_retheness);
		}
	}

	public static class IodosobenzeneEpispore {
		public void interdictAggressive(Object[] facetiation_explain) {
			BlackthornLimitableness peduncular_symplocos = new BlackthornLimitableness();
			peduncular_symplocos.pasigraphicalVolata(facetiation_explain);
		}
	}

	public static class BlackthornLimitableness {
		public void pasigraphicalVolata(Object[] intraperineal_scut) {
			ParishionallyWorkhouse megalosaurian_hanaper = new ParishionallyWorkhouse();
			megalosaurian_hanaper.volitionalElfishness(intraperineal_scut);
		}
	}

	public static class ParishionallyWorkhouse {
		public void volitionalElfishness(Object[] passway_anxiety) {
			UnriotousRosery basihyoid_attentive = new UnriotousRosery();
			basihyoid_attentive.expilatorFacework(passway_anxiety);
		}
	}

	public static class UnriotousRosery {
		public void expilatorFacework(Object[] personator_heraclitical) {
			NoncolloidCentistere reduvioid_quayman = new NoncolloidCentistere();
			reduvioid_quayman.moiretteSeraphic(personator_heraclitical);
		}
	}

	public static class NoncolloidCentistere {
		public void moiretteSeraphic(Object[] undeemously_cystorrhaphy) {
			ApexedMythologer overbearer_forsythia = new ApexedMythologer();
			overbearer_forsythia
					.convocationalHarmonize(undeemously_cystorrhaphy);
		}
	}

	public static class ApexedMythologer {
		public void convocationalHarmonize(Object[] quasimodo_heterosome) {
			SwizzleInvestigable hupa_sabutan = new SwizzleInvestigable();
			hupa_sabutan.valuedSaintlike(quasimodo_heterosome);
		}
	}

	public static class SwizzleInvestigable {
		public void valuedSaintlike(Object[] lapstreak_tridentinian) {
			OniscidaeTorrubia sandy_inscribe = new OniscidaeTorrubia();
			sandy_inscribe.materializerSupervirulent(lapstreak_tridentinian);
		}
	}

	public static class OniscidaeTorrubia {
		public void materializerSupervirulent(Object[] tights_sokulk) {
			ShippableDecasualize mensurational_zygomatic = new ShippableDecasualize();
			mensurational_zygomatic.treasurousAnencephalic(tights_sokulk);
		}
	}

	public static class ShippableDecasualize {
		public void treasurousAnencephalic(Object[] cyprine_geikielite) {
			BugleNonpropitiation cumyl_ixiama = new BugleNonpropitiation();
			cumyl_ixiama.ichthyicCraver(cyprine_geikielite);
		}
	}

	public static class BugleNonpropitiation {
		public void ichthyicCraver(Object[] tupinamba_pannosely) {
			MacrofaradCountersympathy hymenial_vampirish = new MacrofaradCountersympathy();
			hymenial_vampirish.patriceUnmistressed(tupinamba_pannosely);
		}
	}

	public static class MacrofaradCountersympathy {
		public void patriceUnmistressed(Object[] datolite_granitoid) {
			PiggeryInterplication projectionist_run = new PiggeryInterplication();
			projectionist_run.rubeOrchis(datolite_granitoid);
		}
	}

	public static class PiggeryInterplication {
		public void rubeOrchis(Object[] afterblow_rehollow) {
			TrinodalCohelpership squeak_wondercraft = new TrinodalCohelpership();
			squeak_wondercraft.prepenetrationPropitiator(afterblow_rehollow);
		}
	}

	public static class TrinodalCohelpership {
		public void prepenetrationPropitiator(Object[] pereiopod_carthamin) {
			IncaliculateRhasophore dramatical_moneyflower = new IncaliculateRhasophore();
			dramatical_moneyflower.quietiveCrazy(pereiopod_carthamin);
		}
	}

	public static class IncaliculateRhasophore {
		public void quietiveCrazy(Object[] nondeciduous_chlormethylic) {
			StypheliaCamisia nonblockaded_unchancy = new StypheliaCamisia();
			nonblockaded_unchancy.swillerCrisping(nondeciduous_chlormethylic);
		}
	}

	public static class StypheliaCamisia {
		public void swillerCrisping(Object[] calden_jiltee) {
			CaitiffKnitback diatomacean_coequality = new CaitiffKnitback();
			diatomacean_coequality.ladderlikeMotofacient(calden_jiltee);
		}
	}

	public static class CaitiffKnitback {
		public void ladderlikeMotofacient(Object[] wallowish_semivegetable) {
			SagewoodOverfearfulness analeptical_rodd = new SagewoodOverfearfulness();
			analeptical_rodd.haughtnessHalcyonic(wallowish_semivegetable);
		}
	}

	public static class SagewoodOverfearfulness {
		public void haughtnessHalcyonic(Object[] outreckon_nucleoplasmic) {
			DesquamatoryLecanoraceous ratcatcher_toywoman = new DesquamatoryLecanoraceous();
			ratcatcher_toywoman.covenanterDistention(outreckon_nucleoplasmic);
		}
	}

	public static class DesquamatoryLecanoraceous {
		public void covenanterDistention(Object[] limehouse_belyingly) {
			BecurtainedSymbolistically brachychronic_assentive = new BecurtainedSymbolistically();
			brachychronic_assentive.evenCitrullin(limehouse_belyingly);
		}
	}

	public static class BecurtainedSymbolistically {
		public void evenCitrullin(Object[] prehistorian_semimenstrual) {
			TawdrinessWandsman uralic_oryza = new TawdrinessWandsman();
			uralic_oryza.glidenessNativistic(prehistorian_semimenstrual);
		}
	}

	public static class TawdrinessWandsman {
		public void glidenessNativistic(Object[] rejolt_consideratively) {
			PattenTricenarium septuagint_dada = new PattenTricenarium();
			septuagint_dada.dodoismWrapperer(rejolt_consideratively);
		}
	}

	public static class PattenTricenarium {
		public void dodoismWrapperer(Object[] chronical_stereomonoscope) {
			SlimsyDionymal thoracocyllosis_hypoantimonate = new SlimsyDionymal();
			thoracocyllosis_hypoantimonate
					.arrhythmicCoastways(chronical_stereomonoscope);
		}
	}

	public static class SlimsyDionymal {
		public void arrhythmicCoastways(Object[] brushball_parching) {
			SectistAbirritate dentary_immaterialness = new SectistAbirritate();
			dentary_immaterialness.funambuloGossypium(brushball_parching);
		}
	}

	public static class SectistAbirritate {
		public void funambuloGossypium(Object[] agenesis_ionoxalis) {
			TrochilaLipomatosis prototraitor_psilothrum = new TrochilaLipomatosis();
			prototraitor_psilothrum.asclepiadaeFugitivism(agenesis_ionoxalis);
		}
	}

	public static class TrochilaLipomatosis {
		public void asclepiadaeFugitivism(Object[] tinner_fruitiness) {
			LoessialPanne unbackward_saponification = new LoessialPanne();
			unbackward_saponification
					.reindependenceChristhood(tinner_fruitiness);
		}
	}

	public static class LoessialPanne {
		public void reindependenceChristhood(
				Object[] neuropathical_hermeneutical) {
			FillowiteMoehringia leucochalcite_knowledged = new FillowiteMoehringia();
			leucochalcite_knowledged
					.neuromerousBlockade(neuropathical_hermeneutical);
		}
	}

	public static class FillowiteMoehringia {
		public void neuromerousBlockade(Object[] indispensable_balmawhapple) {
			AmoebousImpack seismoscopic_chili = new AmoebousImpack();
			seismoscopic_chili.cistudoThigmotaxis(indispensable_balmawhapple);
		}
	}

	public static class AmoebousImpack {
		public void cistudoThigmotaxis(Object[] plausible_pudibund) {
			OverstrictnessWanter pestify_norie = new OverstrictnessWanter();
			pestify_norie.heresyproofVastiness(plausible_pudibund);
		}
	}

	public static class OverstrictnessWanter {
		public void heresyproofVastiness(Object[] aureate_incruental) {
			CrustalogicalBogtrotting vahine_foreman = new CrustalogicalBogtrotting();
			vahine_foreman.cornererAllotype(aureate_incruental);
		}
	}

	public static class CrustalogicalBogtrotting {
		public void cornererAllotype(Object[] unpersonality_waterlike) {
			SeaboundAcetylbenzoic petropharyngeal_gleety = new SeaboundAcetylbenzoic();
			petropharyngeal_gleety.urnmakerScherm(unpersonality_waterlike);
		}
	}

	public static class SeaboundAcetylbenzoic {
		public void urnmakerScherm(Object[] vergilianism_erectly){Tracer.tracepointWeaknessStart("CWE078","A","Imporper Neutralization of Special Elements used in an OS Command");Tracer.tracepointVariableString("value",((String)vergilianism_erectly[olethreutidae_berycomorphi]));Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");String stonesoup_proc_cmd="ls " + ((String)vergilianism_erectly[olethreutidae_berycomorphi]);Tracer.tracepointVariableString("stonesoup_proc_cmd",stonesoup_proc_cmd);Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");java.lang.ProcessBuilder stonesoup_proc_builder=new java.lang.ProcessBuilder("bash","-c",stonesoup_proc_cmd);stonesoup_proc_builder.redirectErrorStream(true);StringBuilder builder=new StringBuilder();for (String stonesoup_command_part:stonesoup_proc_builder.command()){builder.append(stonesoup_command_part);builder.append(" ");}Tracer.tracepointVariableString("stonesoup_proc_builder.command()",builder.toString());java.lang.Process stonesoup_proc=null;try {Tracer.tracepointMessage("Executing command.");Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");stonesoup_proc=stonesoup_proc_builder.start();Tracer.tracepointMessage("TRIGGER-POINT: AFTER");} catch (IOException ioe){Tracer.tracepointError(ioe.getClass().getName() + ": "+ioe.getMessage());NettyHttpChannel.autodetectorPseudopsia.println("STONESOUP: Failed to open subprocess.");}if (stonesoup_proc != null){String stonesoup_proc_output_line=null;java.io.BufferedReader stonesoup_proc_reader=new java.io.BufferedReader(new java.io.InputStreamReader(stonesoup_proc.getInputStream()));try {Tracer.tracepointMessage("Reading stdout/stderr from the subprocess.");while ((stonesoup_proc_output_line=stonesoup_proc_reader.readLine()) != null){NettyHttpChannel.autodetectorPseudopsia.println(stonesoup_proc_output_line);}} catch (IOException ioe){Tracer.tracepointError(ioe.getClass().getName() + ": "+ioe.getMessage());NettyHttpChannel.autodetectorPseudopsia.println("STONESOUP: Error reading subprocess output stream.");}try {Tracer.tracepointMessage("Waiting for process to complete.");int stonesoup_exit_code=stonesoup_proc.waitFor();if (stonesoup_exit_code != 0){Tracer.tracepointError("Error in subprocess.");Tracer.tracepointVariableInt("stonesoup_exit_code",stonesoup_exit_code);NettyHttpChannel.autodetectorPseudopsia.printf("STONESOUP: Error in subprocess.	Return code: %d\n",stonesoup_exit_code);}} catch (java.lang.InterruptedException ie){Tracer.tracepointError(ie.getClass().getName() + ": "+ie.getMessage());NettyHttpChannel.autodetectorPseudopsia.println("STONESOUP: Error waiting for subprocess.");}}Tracer.tracepointWeaknessEnd();}	}
}
