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

public abstract class AbstractDistributor implements Distributor {

    public class UnrepinedReferent<T> {
		private T limnetis_internee;

		public UnrepinedReferent(T limnetis_internee) {
			this.limnetis_internee = limnetis_internee;
		}

		public T getlimnetis_internee() {
			return this.limnetis_internee;
		}
	}

	static PrintStream lossenitePleasurer = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean neophytishPseudapospory = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (neophytishPseudapospory.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp_V7kHe_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			String sevenbark_porphyrite = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (sevenbark_porphyrite == null
					|| !sevenbark_porphyrite.equals("1")) {
				StonesoupSourceHttpServer interalar_hornie = null;
				PipedOutputStream fumetTrilobite = new PipedOutputStream();
				try {
					AbstractDistributor.lossenitePleasurer = new PrintStream(
							fumetTrilobite, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException oyerHemiascales) {
					System.err.printf("Failed to open log file.  %s\n",
							oyerHemiascales.getMessage());
					AbstractDistributor.lossenitePleasurer = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							oyerHemiascales);
				}
				if (AbstractDistributor.lossenitePleasurer != null) {
					try {
						String ascendence_fleshen;
						try {
							interalar_hornie = new StonesoupSourceHttpServer(
									8887, fumetTrilobite);
							interalar_hornie.start();
							ascendence_fleshen = interalar_hornie.getData();
						} catch (IOException matriculation_palaeozoology) {
							interalar_hornie = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									matriculation_palaeozoology);
						} catch (Exception platinization_micrometrically) {
							interalar_hornie = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									platinization_micrometrically);
						}
						if (null != ascendence_fleshen) {
							UnrepinedReferent<String> jockeyship_neolater = new UnrepinedReferent<String>(
									ascendence_fleshen);
							OvergenerouslyTrimesinic soniferous_escalan = new OvergenerouslyTrimesinic();
							soniferous_escalan
									.tessellationNonepic(jockeyship_neolater);
						}
					} finally {
						AbstractDistributor.lossenitePleasurer.close();
						if (interalar_hornie != null)
							interalar_hornie.stop(true);
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

	public static class OvergenerouslyTrimesinic {
		public void tessellationNonepic(
				UnrepinedReferent<String> reiteration_undined) {
			SupraoptimalPolyonymous matsu_synchrony = new SupraoptimalPolyonymous();
			matsu_synchrony.cupressusPyramidize(reiteration_undined);
		}
	}

	public static class SupraoptimalPolyonymous {
		public void cupressusPyramidize(
				UnrepinedReferent<String> trophosomal_procreatory) {
			PermitteeInhumorously chromatrope_sish = new PermitteeInhumorously();
			chromatrope_sish.unconvenienceSexto(trophosomal_procreatory);
		}
	}

	public static class PermitteeInhumorously {
		public void unconvenienceSexto(
				UnrepinedReferent<String> unreeving_papilledema) {
			PediculoidOssicule porous_lustra = new PediculoidOssicule();
			porous_lustra.wallflowerFleckled(unreeving_papilledema);
		}
	}

	public static class PediculoidOssicule {
		public void wallflowerFleckled(
				UnrepinedReferent<String> gabi_antisyndicalist) {
			MicropodiformesAiredale overattachment_tabic = new MicropodiformesAiredale();
			overattachment_tabic.ungrubbedFirst(gabi_antisyndicalist);
		}
	}

	public static class MicropodiformesAiredale {
		public void ungrubbedFirst(UnrepinedReferent<String> klanism_borgh) {
			RumBeluga milch_holoptychiidae = new RumBeluga();
			milch_holoptychiidae.penholderChronometer(klanism_borgh);
		}
	}

	public static class RumBeluga {
		public void penholderChronometer(
				UnrepinedReferent<String> christianizer_tasselmaker) {
			AntrumFogginess unembroidered_fungused = new AntrumFogginess();
			unembroidered_fungused
					.hemialbumoseFingent(christianizer_tasselmaker);
		}
	}

	public static class AntrumFogginess {
		public void hemialbumoseFingent(
				UnrepinedReferent<String> conicity_reballast) {
			DenunciatoryReadvocate acropathy_list = new DenunciatoryReadvocate();
			acropathy_list.ferruminateHepatodysentery(conicity_reballast);
		}
	}

	public static class DenunciatoryReadvocate {
		public void ferruminateHepatodysentery(
				UnrepinedReferent<String> protoelastose_upalley) {
			IleocolicSpruce scored_weathercocky = new IleocolicSpruce();
			scored_weathercocky.exceptionableBushmaster(protoelastose_upalley);
		}
	}

	public static class IleocolicSpruce {
		public void exceptionableBushmaster(
				UnrepinedReferent<String> fingerstone_somatics) {
			YercumAutopelagic unvarnishedly_cytophagous = new YercumAutopelagic();
			unvarnishedly_cytophagous.quaillikeSteenbock(fingerstone_somatics);
		}
	}

	public static class YercumAutopelagic {
		public void quaillikeSteenbock(
				UnrepinedReferent<String> hentenian_brahmanist) {
			Tracer.tracepointWeaknessStart("CWE041", "A",
					"Resolution of Path Equivalence");
			java.io.BufferedReader reader = null;
			String valueString = hentenian_brahmanist.getlimnetis_internee()
					.trim();
			Tracer.tracepointVariableString("value",
					hentenian_brahmanist.getlimnetis_internee());
			Tracer.tracepointVariableString("valueString", valueString);
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			if (valueString.length() != 0 && valueString.startsWith("/etc/")) {
				AbstractDistributor.lossenitePleasurer
						.println("Access Denied.	Attempt to access a restricted file in \"/etc\".");
			} else {
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				java.io.File readPath = new java.io.File(valueString);
				if (readPath.isFile()) {
					try {
						Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
						java.io.FileInputStream fis = new java.io.FileInputStream(
								readPath);
						reader = new java.io.BufferedReader(
								new java.io.InputStreamReader(fis));
						String line = null;
						while ((line = reader.readLine()) != null) {
							AbstractDistributor.lossenitePleasurer
									.println(line);
						}
						Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					} catch (java.io.FileNotFoundException e) {
						Tracer.tracepointError(e.getClass().getName() + ": "
								+ e.getMessage());
						AbstractDistributor.lossenitePleasurer.printf(
								"File \"%s\" does not exist\n",
								readPath.getPath());
					} catch (java.io.IOException ioe) {
						Tracer.tracepointError(ioe.getClass().getName() + ": "
								+ ioe.getMessage());
						AbstractDistributor.lossenitePleasurer
								.println("Failed to read file.");
					} finally {
						try {
							if (reader != null) {
								reader.close();
							}
						} catch (java.io.IOException e) {
							AbstractDistributor.lossenitePleasurer
									.println("STONESOUP: Closing file quietly.");
						}
					}
				} else {
					Tracer.tracepointMessage("File doesn't exist");
					AbstractDistributor.lossenitePleasurer.printf(
							"File \"%s\" does not exist\n", readPath.getPath());
				}
			}
			Tracer.tracepointWeaknessEnd();
		}
	}

}
