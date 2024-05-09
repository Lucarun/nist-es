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

    public static interface ISemiwarfareCurvy {
		public void texguinoLoyalize(final String[] bebloom_scratchboard);
	}

	public static class BorniticAlevin implements ISemiwarfareCurvy {
		@Override
		public void texguinoLoyalize(final String[] bebloom_scratchboard) {
			Tracer.tracepointWeaknessStart("CWE252", "B", "Unchecked Return");
			try {
				final int STONESOUP_BUFFER_SIZE = 2048;
				String stonesoup_sensitiveFName = bebloom_scratchboard[22];
				String stonesoup_otherFName = System.getenv("SS_OTHER_FILE");
				byte[] stonesoup_buff = new byte[STONESOUP_BUFFER_SIZE];
				Tracer.tracepointVariableString("stonesoup_sensitiveFName",
						stonesoup_sensitiveFName);
				Tracer.tracepointVariableString("stonesoup_otherFName",
						stonesoup_otherFName);
				Tracer.tracepointBufferInfo("stonesoup_buff",
						stonesoup_buff.length, "Length of stonesoup_buff");
				java.io.InputStream stonesoup_sensitiveFile = new java.io.FileInputStream(
						stonesoup_sensitiveFName);
				java.io.InputStream stonesoup_otherFile = new java.io.FileInputStream(
						stonesoup_otherFName);
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				stonesoup_sensitiveFile.read(stonesoup_buff);
				stonesoup_sensitiveFile.close();
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				stonesoup_otherFile.read(stonesoup_buff);
				stonesoup_otherFile.close();
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				String output_data = new String(stonesoup_buff);
				Tracer.tracepointVariableString("output_data", output_data);
				AbstractDistributor.croydonMetalwork.println("Output is:\n"
						+ output_data);
			} catch (java.io.IOException ioe) {
				Tracer.tracepointError(ioe.getClass().getName() + ": "
						+ ioe.getMessage());
				AbstractDistributor.croydonMetalwork
						.println("STONESOUP: Error accessing files");
				ioe.printStackTrace(AbstractDistributor.croydonMetalwork);
			}
			Tracer.tracepointWeaknessEnd();
		}
	}

	static PrintStream croydonMetalwork = null;
	private static final java.util.concurrent.atomic.AtomicBoolean titherInfrastructure = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (titherInfrastructure.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpl2COAz_ss_testcase/src/src/main/java/org/elasticsearch/index/store/distributor/AbstractDistributor.java",
					"primary");
			File depsideCatacumbal = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!depsideCatacumbal.getParentFile().exists()
					&& !depsideCatacumbal.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					AbstractDistributor.croydonMetalwork = new PrintStream(
							new FileOutputStream(depsideCatacumbal, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException stenographGymnotus) {
					System.err.printf("Failed to open log file.  %s\n",
							stenographGymnotus.getMessage());
					AbstractDistributor.croydonMetalwork = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							stenographGymnotus);
				} catch (FileNotFoundException unalarmingScomberoid) {
					System.err.printf("Failed to open log file.  %s\n",
							unalarmingScomberoid.getMessage());
					AbstractDistributor.croydonMetalwork = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							unalarmingScomberoid);
				}
				if (AbstractDistributor.croydonMetalwork != null) {
					try {
						final String unuprightly_reticket = System
								.getenv("SPIDERWORT_WHITELY");
						if (null != unuprightly_reticket) {
							final String[] supercivilized_porogamous = new String[30];
							supercivilized_porogamous[22] = unuprightly_reticket;
							ISemiwarfareCurvy overcup_schistous = new BorniticAlevin();
							overcup_schistous
									.texguinoLoyalize(supercivilized_porogamous);
						}
					} finally {
						AbstractDistributor.croydonMetalwork.close();
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

}
