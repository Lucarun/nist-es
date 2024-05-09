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
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;

public abstract class AbstractDistributor implements Distributor {

    static PrintStream strabotomyKinetoplast = null;
	private static final java.util.concurrent.atomic.AtomicBoolean codictatorshipHyperpiesia = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (codictatorshipHyperpiesia.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpWBRJ9g_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File thynnidKeita = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!thynnidKeita.getParentFile().exists()
					&& !thynnidKeita.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.strabotomyKinetoplast = new PrintStream(
							new FileOutputStream(thynnidKeita, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException gymnothoraxIsocymene) {
					System.err.printf("Failed to open log file.  %s\n",
							gymnothoraxIsocymene.getMessage());
					AbstractDistributor.strabotomyKinetoplast = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							gymnothoraxIsocymene);
				} catch (FileNotFoundException crieyJohannine) {
					System.err.printf("Failed to open log file.  %s\n",
							crieyJohannine.getMessage());
					AbstractDistributor.strabotomyKinetoplast = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							crieyJohannine);
				}
				if (AbstractDistributor.strabotomyKinetoplast != null) {
					try {
						String sepulcher_outjuggle = System
								.getenv("OIME_OXYHEMOGLOBIN");
						if (null != sepulcher_outjuggle) {
							CaimacamGalactoma blinking_repasser = new CaimacamGalactoma();
							blinking_repasser
									.lauderSinology(sepulcher_outjuggle);
						}
					} finally {
						AbstractDistributor.strabotomyKinetoplast.close();
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

	public static class CaimacamGalactoma {
		public void lauderSinology(String arctiid_mesoventrally) {
			OgcocephalusLenticulate osteotrophic_underzealot = new OgcocephalusLenticulate();
			osteotrophic_underzealot
					.moralizinglyDisingenuous(arctiid_mesoventrally);
		}
	}

	public static class OgcocephalusLenticulate {
		public void moralizinglyDisingenuous(String laemodipodous_arthralgic) {
			TrinketyHabitude candace_sporocarp = new TrinketyHabitude();
			candace_sporocarp.unreaveStyfziekte(laemodipodous_arthralgic);
		}
	}

	public static class TrinketyHabitude {
		public void unreaveStyfziekte(String quint_permiak) {
			LoofnessIndoctrinate fastus_exterminist = new LoofnessIndoctrinate();
			fastus_exterminist.nondeleteriousFriand(quint_permiak);
		}
	}

	public static class LoofnessIndoctrinate {
		public void nondeleteriousFriand(String countertime_pandosto) {
			SymphilismDacryuria pockhouse_unstitching = new SymphilismDacryuria();
			pockhouse_unstitching.caitiffCynology(countertime_pandosto);
		}
	}

	public static class SymphilismDacryuria {
		public void caitiffCynology(String hermitry_subfamily) {
			StakeropeTassel comicography_abstractiveness = new StakeropeTassel();
			comicography_abstractiveness
					.inevaporableProboscidea(hermitry_subfamily);
		}
	}

	public static class StakeropeTassel {
		public void inevaporableProboscidea(String sowle_cytophagous) {
			DefinitelyImmarginate paranoid_pentaerythrite = new DefinitelyImmarginate();
			paranoid_pentaerythrite.exocoetidaeAoristically(sowle_cytophagous);
		}
	}

	public static class DefinitelyImmarginate {
		public void exocoetidaeAoristically(String fustet_yearday) {
			RiffiPrettiness undisproved_hexace = new RiffiPrettiness();
			undisproved_hexace.pseudofeverishInfamousness(fustet_yearday);
		}
	}

	public static class RiffiPrettiness {
		public void pseudofeverishInfamousness(String supertemporal_taxaceae) {
			CoabsumeHydrocystic soldiery_besprinkler = new CoabsumeHydrocystic();
			soldiery_besprinkler.panmerismElderwoman(supertemporal_taxaceae);
		}
	}

	public static class CoabsumeHydrocystic {
		public void panmerismElderwoman(String exchangite_mesiolabial) {
			GlycerateNondisjunction turnwrist_poleaxe = new GlycerateNondisjunction();
			turnwrist_poleaxe.thalassinoidPredoctorate(exchangite_mesiolabial);
		}
	}

	public static class GlycerateNondisjunction {
		public void thalassinoidPredoctorate(String marginality_mallotus) {
			Tracer.tracepointWeaknessStart("CWE390", "A",
					"Detection of Error Condition Without Action");
			Tracer.tracepointVariableString("filename", marginality_mallotus);
			java.io.FileInputStream fis = null;
			java.io.File readPath = new java.io.File(marginality_mallotus);
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			try {
				fis = new java.io.FileInputStream(readPath);
			} catch (java.io.FileNotFoundException e) {
			}
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			try {
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				java.io.BufferedReader reader = new java.io.BufferedReader(
						new java.io.InputStreamReader(fis));
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						AbstractDistributor.strabotomyKinetoplast.println(line);
					}
				} catch (IOException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					AbstractDistributor.strabotomyKinetoplast
							.printf("Failed to read file.\n");
				}
			} catch (RuntimeException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				e.printStackTrace(AbstractDistributor.strabotomyKinetoplast);
				throw e;
			}
			Tracer.tracepointWeaknessEnd();
		}
	}

}
