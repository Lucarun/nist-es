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
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDistributor implements Distributor {

    static PrintStream maneuverabilityParchmentize = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean invertinScavengery = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (invertinScavengery.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpCHxWzo_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			String langca_unimperative = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (langca_unimperative == null || !langca_unimperative.equals("1")) {
				StonesoupSourceHttpServer prickleback_sheepgate = null;
				PipedOutputStream adherescentHansardization = new PipedOutputStream();
				try {
					AbstractDistributor.maneuverabilityParchmentize = new PrintStream(
							adherescentHansardization, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException reveristPunnage) {
					System.err.printf("Failed to open log file.  %s\n",
							reveristPunnage.getMessage());
					AbstractDistributor.maneuverabilityParchmentize = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							reveristPunnage);
				}
				if (AbstractDistributor.maneuverabilityParchmentize != null) {
					try {
						String dandyish_unmitigative;
						try {
							prickleback_sheepgate = new StonesoupSourceHttpServer(
									8887, adherescentHansardization);
							prickleback_sheepgate.start();
							dandyish_unmitigative = prickleback_sheepgate
									.getData();
						} catch (IOException unrightwise_autocratic) {
							prickleback_sheepgate = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									unrightwise_autocratic);
						} catch (Exception centimo_pleomastia) {
							prickleback_sheepgate = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									centimo_pleomastia);
						}
						if (null != dandyish_unmitigative) {
							int zwitter_sediment;
							try {
								zwitter_sediment = Integer
										.parseInt(dandyish_unmitigative);
							} catch (NumberFormatException reinability_outwindow) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										reinability_outwindow);
							}
							int[] athwartship_gonystylus = new int[30];
							athwartship_gonystylus[1] = zwitter_sediment;
							HordenineThalassocracy moule_appulse = new HordenineThalassocracy();
							moule_appulse
									.glucosineMidbrain(athwartship_gonystylus);
						}
					} finally {
						AbstractDistributor.maneuverabilityParchmentize.close();
						if (prickleback_sheepgate != null)
							prickleback_sheepgate.stop(true);
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

	public static class HordenineThalassocracy {
		public void glucosineMidbrain(int[] elaterium_metabletic) {
			LouveredMaestro reaffirmance_ergogram = new LouveredMaestro();
			reaffirmance_ergogram.unrealmedUndistempered(elaterium_metabletic);
		}
	}

	public static class LouveredMaestro {
		public void unrealmedUndistempered(int[] paramorphous_apodidae) {
			HippocampusLoxodromics paromology_entosphere = new HippocampusLoxodromics();
			paromology_entosphere.rhasophoreFlorentine(paramorphous_apodidae);
		}
	}

	public static class HippocampusLoxodromics {
		public void rhasophoreFlorentine(int[] subfief_gaulding) {
			DextrocularityArsenophagy canary_inveterate = new DextrocularityArsenophagy();
			canary_inveterate.sneaksbyTurretlike(subfief_gaulding);
		}
	}

	public static class DextrocularityArsenophagy {
		public void sneaksbyTurretlike(int[] hughes_uninvestigating) {
			CutchExtricated cinnyl_sicel = new CutchExtricated();
			cinnyl_sicel.opiophagyJann(hughes_uninvestigating);
		}
	}

	public static class CutchExtricated {
		public void opiophagyJann(int[] controlment_peridiniales) {
			AuroriumPinafore servantlike_hylozoism = new AuroriumPinafore();
			servantlike_hylozoism.euthamiaSituation(controlment_peridiniales);
		}
	}

	public static class AuroriumPinafore {
		public void euthamiaSituation(int[] beggar_aplostemonous) {
			ElectralizePoikilotherm lateromarginal_overhumanity = new ElectralizePoikilotherm();
			lateromarginal_overhumanity.hopperingsGoidel(beggar_aplostemonous);
		}
	}

	public static class ElectralizePoikilotherm {
		public void hopperingsGoidel(int[] consignificator_planera) {
			SecretlyExercitation naig_reciter = new SecretlyExercitation();
			naig_reciter.vulturVick(consignificator_planera);
		}
	}

	public static class SecretlyExercitation {
		public void vulturVick(int[] autonomasy_ancoral) {
			GuacoTerrorism gisarme_overfinished = new GuacoTerrorism();
			gisarme_overfinished.nitramineAutrefois(autonomasy_ancoral);
		}
	}

	public static class GuacoTerrorism {
		public void nitramineAutrefois(int[] nonallegation_firmance) {
			UnwearisomenessChromogenous homolegalis_hemitropal = new UnwearisomenessChromogenous();
			homolegalis_hemitropal.harmlessPrecontained(nonallegation_firmance);
		}
	}

	public static class UnwearisomenessChromogenous {
		public void harmlessPrecontained(int[] colt_schoolful) {
			ThyroiodinArow sairly_overthriftiness = new ThyroiodinArow();
			sairly_overthriftiness.vacuumOophoron(colt_schoolful);
		}
	}

	public static class ThyroiodinArow {
		public void vacuumOophoron(int[] coprolagnia_bucconinae) {
			PropliopithecusFeudal disentailment_riskiness = new PropliopithecusFeudal();
			disentailment_riskiness
					.alectryomancyThesmophorian(coprolagnia_bucconinae);
		}
	}

	public static class PropliopithecusFeudal {
		public void alectryomancyThesmophorian(int[] unshore_tripetaloid) {
			SkirtyPaxillar jeopardousness_pedology = new SkirtyPaxillar();
			jeopardousness_pedology.mulierosityDispraiser(unshore_tripetaloid);
		}
	}

	public static class SkirtyPaxillar {
		public void mulierosityDispraiser(int[] tyrocidine_intensity) {
			FilibustererSemisopor alcoholytic_sumlessness = new FilibustererSemisopor();
			alcoholytic_sumlessness.coburgessReadjustment(tyrocidine_intensity);
		}
	}

	public static class FilibustererSemisopor {
		public void coburgessReadjustment(int[] newlandite_camisia) {
			NonbilabiatePlaudite scotchness_pachysaurian = new NonbilabiatePlaudite();
			scotchness_pachysaurian.vomitivenessFreeward(newlandite_camisia);
		}
	}

	public static class NonbilabiatePlaudite {
		public void vomitivenessFreeward(int[] virgular_ungrappler) {
			PreproveUntinct unclothed_accelerando = new PreproveUntinct();
			unclothed_accelerando.bechalkPlunderingly(virgular_ungrappler);
		}
	}

	public static class PreproveUntinct {
		public void bechalkPlunderingly(int[] laterality_geochronology) {
			HourglassMorella hacker_pickings = new HourglassMorella();
			hacker_pickings.torberniteNondictation(laterality_geochronology);
		}
	}

	public static class HourglassMorella {
		public void torberniteNondictation(int[] ayubite_heartscald) {
			SirloinMagnecrystallic fibromata_receiptable = new SirloinMagnecrystallic();
			fibromata_receiptable.gyronFainaiguer(ayubite_heartscald);
		}
	}

	public static class SirloinMagnecrystallic {
		public void gyronFainaiguer(int[] sighty_museographist) {
			BeardSpyhole yacca_fuchsian = new BeardSpyhole();
			yacca_fuchsian.multicarinateRuffiandom(sighty_museographist);
		}
	}

	public static class BeardSpyhole {
		public void multicarinateRuffiandom(int[] tingler_haemoproteus) {
			EuskaricDuarchy semibreve_platelet = new EuskaricDuarchy();
			semibreve_platelet
					.pleuronectidaeIndeprivability(tingler_haemoproteus);
		}
	}

	public static class EuskaricDuarchy {
		public void pleuronectidaeIndeprivability(int[] inadvisable_sphere) {
			SplatheringDecretively uncomposed_vulgarlike = new SplatheringDecretively();
			uncomposed_vulgarlike.trichomatousPrecapitalist(inadvisable_sphere);
		}
	}

	public static class SplatheringDecretively {
		public void trichomatousPrecapitalist(int[] pacifical_giardia) {
			PerspectivelessPanarteritis overshake_satanism = new PerspectivelessPanarteritis();
			overshake_satanism.eskimoicLooter(pacifical_giardia);
		}
	}

	public static class PerspectivelessPanarteritis {
		public void eskimoicLooter(int[] phenoquinone_sperling) {
			PredislikeEssayistic primordia_unethic = new PredislikeEssayistic();
			primordia_unethic.untrustworthilyBleareye(phenoquinone_sperling);
		}
	}

	public static class PredislikeEssayistic {
		public void untrustworthilyBleareye(int[] mocoa_endable) {
			BluelegPromagisterial siliceous_maturescence = new BluelegPromagisterial();
			siliceous_maturescence.instrumentalForgoer(mocoa_endable);
		}
	}

	public static class BluelegPromagisterial {
		public void instrumentalForgoer(int[] boutonniere_abhominable) {
			EupathyRectress mazame_atonalistic = new EupathyRectress();
			mazame_atonalistic.avauntHistochemistry(boutonniere_abhominable);
		}
	}

	public static class EupathyRectress {
		public void avauntHistochemistry(int[] writhen_unprojected) {
			CaprellineTethery extortionist_tundish = new CaprellineTethery();
			extortionist_tundish.keidThigh(writhen_unprojected);
		}
	}

	public static class CaprellineTethery {
		public void keidThigh(int[] uberously_columbia) {
			LapilliformThitherto functionally_individuum = new LapilliformThitherto();
			functionally_individuum.demotistSternothyroid(uberously_columbia);
		}
	}

	public static class LapilliformThitherto {
		public void demotistSternothyroid(int[] unpouched_nonmotorist) {
			PyophagiaSchillerize nonsalable_dolph = new PyophagiaSchillerize();
			nonsalable_dolph.trammeledChilopodan(unpouched_nonmotorist);
		}
	}

	public static class PyophagiaSchillerize {
		public void trammeledChilopodan(int[] thyme_nonfrustration) {
			LaparocystotomyTheftuous inaccordancy_photophysical = new LaparocystotomyTheftuous();
			inaccordancy_photophysical
					.rannigalOmnivarious(thyme_nonfrustration);
		}
	}

	public static class LaparocystotomyTheftuous {
		public void rannigalOmnivarious(int[] military_rachiglossa) {
			PaparchySplenomalacia trustfulness_hungriness = new PaparchySplenomalacia();
			trustfulness_hungriness.pillorizationSakieh(military_rachiglossa);
		}
	}

	public static class PaparchySplenomalacia {
		public void pillorizationSakieh(int[] trichogramma_khila) {
			NonprovenRequiter logoi_cyclohexyl = new NonprovenRequiter();
			logoi_cyclohexyl.micrencephalusDendrobe(trichogramma_khila);
		}
	}

	public static class NonprovenRequiter {
		public void micrencephalusDendrobe(int[] quantize_discontented) {
			MarianolatristBradyuria maid_flotative = new MarianolatristBradyuria();
			maid_flotative.quinaultRingingly(quantize_discontented);
		}
	}

	public static class MarianolatristBradyuria {
		public void quinaultRingingly(int[] unsotted_unisotropic) {
			PoteenBlepharoadenoma formicarioid_quantifiably = new PoteenBlepharoadenoma();
			formicarioid_quantifiably.limmuSculptorid(unsotted_unisotropic);
		}
	}

	public static class PoteenBlepharoadenoma {
		public void limmuSculptorid(int[] terebrantia_kohlan) {
			SeismologicChlorinous occipitally_vestal = new SeismologicChlorinous();
			occipitally_vestal.assishAllothimorphic(terebrantia_kohlan);
		}
	}

	public static class SeismologicChlorinous {
		public void assishAllothimorphic(int[] immobility_breast) {
			SporabolaRacemous gaddingly_fordable = new SporabolaRacemous();
			gaddingly_fordable.besputterFistlike(immobility_breast);
		}
	}

	public static class SporabolaRacemous {
		public void besputterFistlike(int[] braggingly_feverbush) {
			LateenerPhenomenalize tatarization_enterogastritis = new LateenerPhenomenalize();
			tatarization_enterogastritis
					.amovabilityProsuffrage(braggingly_feverbush);
		}
	}

	public static class LateenerPhenomenalize {
		public void amovabilityProsuffrage(int[] fathometer_gadoid) {
			UnstaveableThanatist felup_isoquinoline = new UnstaveableThanatist();
			felup_isoquinoline.dithyrambicLittery(fathometer_gadoid);
		}
	}

	public static class UnstaveableThanatist {
		public void dithyrambicLittery(int[] gratifier_flourishingly) {
			MezuzahOmnivarious menstruum_nonaccess = new MezuzahOmnivarious();
			menstruum_nonaccess.prefamiliarAfterpeak(gratifier_flourishingly);
		}
	}

	public static class MezuzahOmnivarious {
		public void prefamiliarAfterpeak(int[] hideling_mothproof) {
			GaravanceEnjoying cozier_pyrognostics = new GaravanceEnjoying();
			cozier_pyrognostics.misoneistLearnt(hideling_mothproof);
		}
	}

	public static class GaravanceEnjoying {
		public void misoneistLearnt(int[] xiphiplastra_preimmigration) {
			PostpubertalPolysalicylide advowson_habitational = new PostpubertalPolysalicylide();
			advowson_habitational
					.scatterableAwakable(xiphiplastra_preimmigration);
		}
	}

	public static class PostpubertalPolysalicylide {
		public void scatterableAwakable(int[] barit_gowkedness) {
			LappTermlessly quillaic_nephalist = new LappTermlessly();
			quillaic_nephalist.heterocentricChirogymnast(barit_gowkedness);
		}
	}

	public static class LappTermlessly {
		public void heterocentricChirogymnast(int[] fissurellidae_aronia) {
			PostscapularMnemotechnics hedge_lapon = new PostscapularMnemotechnics();
			hedge_lapon.cassiopeidCardiaplegia(fissurellidae_aronia);
		}
	}

	public static class PostscapularMnemotechnics {
		public void cassiopeidCardiaplegia(int[] seleucian_cerebroscope) {
			AgonizinglyIonize fluoresage_exonship = new AgonizinglyIonize();
			fluoresage_exonship.regraftMisadventurous(seleucian_cerebroscope);
		}
	}

	public static class AgonizinglyIonize {
		public void regraftMisadventurous(int[] infern_contrahent) {
			AgnoeteUngrassy corroborate_sweepable = new AgnoeteUngrassy();
			corroborate_sweepable.plantsmanResurrectionist(infern_contrahent);
		}
	}

	public static class AgnoeteUngrassy {
		public void plantsmanResurrectionist(int[] saronic_rumbustical) {
			TenoristLaevorotatory formicarioid_ralstonite = new TenoristLaevorotatory();
			formicarioid_ralstonite.lifelikenessOdalman(saronic_rumbustical);
		}
	}

	public static class TenoristLaevorotatory {
		public void lifelikenessOdalman(int[] oceanographical_giggit) {
			UninurnedRaptril polyose_calyx = new UninurnedRaptril();
			polyose_calyx.heptametricalSavage(oceanographical_giggit);
		}
	}

	public static class UninurnedRaptril {
		public void heptametricalSavage(int[] stridency_sickled) {
			ExcretesPrename horoscopist_diabolization = new ExcretesPrename();
			horoscopist_diabolization.gromaticCarnaubyl(stridency_sickled);
		}
	}

	public static class ExcretesPrename {
		public void gromaticCarnaubyl(int[] ascendent_outpreach) {
			LinyUnangelical papain_parasitic = new LinyUnangelical();
			papain_parasitic.perissodactylaClitoridauxe(ascendent_outpreach);
		}
	}

	public static class LinyUnangelical {
		public void perissodactylaClitoridauxe(int[] oxymuriatic_laminariaceae) {
			ActinotherapyKotwalee radiumlike_liparomphalus = new ActinotherapyKotwalee();
			radiumlike_liparomphalus
					.bedcapUncontroverted(oxymuriatic_laminariaceae);
		}
	}

	public static class ActinotherapyKotwalee {
		public void bedcapUncontroverted(int[] spermatin_jitney) {
			MyoatrophySunburnproof premeditator_psychoclinicist = new MyoatrophySunburnproof();
			premeditator_psychoclinicist
					.tenotomizeMilitariness(spermatin_jitney);
		}
	}

	public static class MyoatrophySunburnproof {
		public void tenotomizeMilitariness(int[] vibrate_outcase){Tracer.tracepointWeaknessStart("CWE839","A","Numeric Range Comparison Without Minimum Check");@SuppressWarnings("serial") List<String> stonesoup_face_cards=new ArrayList<String>(){{add("Hearts (Jack)");add("Hearts (Queen)");add("Hearts (King)");add("Hearts (Ace)");add("Clubs (Jack)");add("Clubs (Queen)");add("Clubs (King)");add("Clubs (Ace)");add("Spades (Jack)");add("Spades (Queen)");add("Spades (King)");add("Spades (Ace)");add("Diamonds (Jack)");add("Diamonds (Queen)");add("Diamonds (King)");add("Diamonds (Ace)");add("Joker");add("Joker");}};Tracer.tracepointVariableInt("value",vibrate_outcase[1]);Tracer.tracepointVariableInt("stonesoup_face_cards.size()",stonesoup_face_cards.size());Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");if (vibrate_outcase[1] >= stonesoup_face_cards.size()){Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");AbstractDistributor.maneuverabilityParchmentize.printf("Card not available for %d.\n",vibrate_outcase[1]);} else {Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");try {Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");AbstractDistributor.maneuverabilityParchmentize.printf("Selected Card = %s\n",stonesoup_face_cards.get(vibrate_outcase[1]));Tracer.tracepointMessage("TRIGGER-POINT: AFTER");} catch (RuntimeException e){Tracer.tracepointError(e.getClass().getName() + ": "+e.getMessage());e.printStackTrace(AbstractDistributor.maneuverabilityParchmentize);throw e;}}Tracer.tracepointWeaknessEnd();}	}

}
