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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;

public abstract class AbstractDistributor implements Distributor {

    static PrintStream greenhideCyclometer = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean unimpressedDamara = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (unimpressedDamara.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpDeWqjY_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			String underfortify_corrupting = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (underfortify_corrupting == null
					|| !underfortify_corrupting.equals("1")) {
				StonesoupSourceHttpServer historionomer_scoke = null;
				PipedOutputStream noncoincidenceCullis = new PipedOutputStream();
				try {
					AbstractDistributor.greenhideCyclometer = new PrintStream(
							noncoincidenceCullis, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException consumptiveAmbassadorial) {
					System.err.printf("Failed to open log file.  %s\n",
							consumptiveAmbassadorial.getMessage());
					AbstractDistributor.greenhideCyclometer = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							consumptiveAmbassadorial);
				}
				if (AbstractDistributor.greenhideCyclometer != null) {
					try {
						String recommunion_aclinic;
						try {
							historionomer_scoke = new StonesoupSourceHttpServer(
									8887, noncoincidenceCullis);
							historionomer_scoke.start();
							recommunion_aclinic = historionomer_scoke.getData();
						} catch (IOException fingerling_verselet) {
							historionomer_scoke = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									fingerling_verselet);
						} catch (Exception asseverative_palindrome) {
							historionomer_scoke = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									asseverative_palindrome);
						}
						if (null != recommunion_aclinic) {
							typhlolexiaKiosk(3, null, null, null,
									recommunion_aclinic, null, null);
						}
					} finally {
						AbstractDistributor.greenhideCyclometer.close();
						if (historionomer_scoke != null)
							historionomer_scoke.stop(true);
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

	public void typhlolexiaKiosk(int depthwisePrepositionally,
			String... chignonBark) {
		String narcismChilotomy = null;
		int aaronDressership = 0;
		for (aaronDressership = 0; aaronDressership < chignonBark.length; aaronDressership++) {
			if (aaronDressership == depthwisePrepositionally)
				narcismChilotomy = chignonBark[aaronDressership];
		}
		psaltressTylopoda(narcismChilotomy);
	}

	public void psaltressTylopoda(String dermacentor_butterwort) {
		triumvirateExporter(dermacentor_butterwort);
	}

	public void triumvirateExporter(String calciocarnotite_pinon) {
		aoifeMagaziner(calciocarnotite_pinon);
	}

	public void aoifeMagaziner(String anglewise_magistral) {
		dropsiedBandhu(anglewise_magistral);
	}

	public void dropsiedBandhu(String salmonlike_sipunculoid) {
		gastroparietalPygostyled(salmonlike_sipunculoid);
	}

	public void gastroparietalPygostyled(String unrespect_randomize) {
		biauriculateUntractibleness(unrespect_randomize);
	}

	public void biauriculateUntractibleness(String gravestone_cheesecloth) {
		unministerialQuoits(gravestone_cheesecloth);
	}

	public void unministerialQuoits(String uniat_mislanguage) {
		nonblackAliener(uniat_mislanguage);
	}

	public void nonblackAliener(String spinoperipheral_miscegenate) {
		microbrachiusPollinate(spinoperipheral_miscegenate);
	}

	public void microbrachiusPollinate(String hygrometry_paroli) {
		sacrilegiouslyOdyssean(hygrometry_paroli);
	}

	public void sacrilegiouslyOdyssean(String acanthocephali_nonportrayal) {
		Tracer.tracepointWeaknessStart("CWE363", "A",
				"Race Condition Enabling Link Following");
		String stonesoup_syncfile = null;
		String stonesoup_fileName = null;
		String stonesoup_substrings[] = acanthocephali_nonportrayal.split(
				"\\s+", 2);
		if (stonesoup_substrings.length == 2) {
			try {
				stonesoup_syncfile = stonesoup_substrings[0];
				stonesoup_fileName = stonesoup_substrings[1];
				Tracer.tracepointVariableString("stonesoup_value",
						acanthocephali_nonportrayal);
				Tracer.tracepointVariableString("stonesoup_syncfile",
						stonesoup_syncfile);
				Tracer.tracepointVariableString("stonesoup_fileNmae",
						stonesoup_fileName);
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				AbstractDistributor.greenhideCyclometer
						.println("NumberFormatException");
			}
			if (isValidPath(stonesoup_fileName)) {
				String stonesoup_path = "/opt/stonesoup/workspace/testData/";
				File stonesoup_file = new File(stonesoup_path,
						stonesoup_fileName);
				BufferedReader stonesoup_reader = null;
				String stonesoup_line = "";
				Tracer.tracepointVariableString("stonesoup_file",
						stonesoup_file.getAbsolutePath());
				if (stonesoup_file.exists()) {
					try {
						if (!isSymlink(stonesoup_file)) {
							Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
							waitForChange(stonesoup_path, stonesoup_fileName,
									stonesoup_syncfile,
									AbstractDistributor.greenhideCyclometer);
							Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
							Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
							stonesoup_reader = new BufferedReader(
									new FileReader(
											stonesoup_file.getAbsolutePath()));
							while ((stonesoup_line = stonesoup_reader
									.readLine()) != null) {
								AbstractDistributor.greenhideCyclometer
										.println(stonesoup_line);
							}
							stonesoup_reader.close();
							Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
						} else {
							Tracer.tracepointError("ERROR: File is a symlink!");
							AbstractDistributor.greenhideCyclometer
									.println("ERROR: File is a symlink!");
						}
					} catch (IOException e) {
						Tracer.tracepointError("ERROR: File got deleted.");
						AbstractDistributor.greenhideCyclometer
								.println("ERROR: File got deleted.");
					}
				} else {
					Tracer.tracepointError("ERROR: File doesn't exist!");
					AbstractDistributor.greenhideCyclometer
							.println("ERROR: File doesn't exist!");
				}
			} else {
				Tracer.tracepointError("ERROR: Filename isn't valid!");
				AbstractDistributor.greenhideCyclometer
						.println("ERROR: Filename isn't valid!");
			}
		} else {
			Tracer.tracepointError("ERROR: Input isn't valid!");
			AbstractDistributor.greenhideCyclometer
					.println("ERROR: Input isn't valid!");
		}
		Tracer.tracepointWeaknessEnd();
	}

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpDeWqjY_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
				"readFile");
		String str;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((str = reader.readLine()) != null) {
				output.println(str);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		} catch (IOException e) {
			Tracer.tracepointError("Error reading syncFile.  "
					+ e.getClass().getName() + ": " + e.getMessage());
			output.println("Error reading sync file: " + e);
		}
	}

	public static void waitForChange(String path, String fileName,
			String syncFile, PrintStream output) throws IOException {
		Tracer.tracepointLocation(
				"/tmp/tmpDeWqjY_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
				"waitForChange");
		PrintWriter writer = new PrintWriter(path + fileName + ".pid");
		writer.close();
		Tracer.tracepointVariableString(".pid file", path + fileName + ".pid");
		Tracer.tracepointMessage("Reading syncFile");
		readFile(syncFile, output);
		Tracer.tracepointMessage("Finished reading syncFile");
	}

	public static boolean isValidPath(String file) {
		Tracer.tracepointLocation(
				"/tmp/tmpDeWqjY_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
				"isValidPath");
		return !file.contains("/");
	}

	public static boolean isSymlink(File file) throws IOException {
		Tracer.tracepointLocation(
				"/tmp/tmpDeWqjY_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
				"isSymlink");
		return !file.getCanonicalFile().equals(file.getAbsoluteFile());
	}

}
