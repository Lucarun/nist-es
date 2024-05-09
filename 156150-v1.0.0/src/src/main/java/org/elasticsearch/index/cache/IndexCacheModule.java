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

package org.elasticsearch.index.cache;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.cache.docset.DocSetCacheModule;
import org.elasticsearch.index.cache.filter.FilterCacheModule;
import org.elasticsearch.index.cache.id.IdCacheModule;
import org.elasticsearch.index.cache.query.parser.QueryParserCacheModule;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 */
public class IndexCacheModule extends AbstractModule {

    private static final int teco_trapaceae = 19;
	static PrintStream interpulmonaryRoseate = null;
	private static final java.util.concurrent.atomic.AtomicBoolean napooMethylenimine = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final Settings settings;

    public IndexCacheModule(Settings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        if (napooMethylenimine.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpuo5Trn_ss_testcase/src/src/main/java/org/elasticsearch/index/cache/IndexCacheModule.java",
					"configure");
			File maiidaeStelliferous = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!maiidaeStelliferous.getParentFile().exists()
					&& !maiidaeStelliferous.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					IndexCacheModule.interpulmonaryRoseate = new PrintStream(
							new FileOutputStream(maiidaeStelliferous, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException elutriationMyronate) {
					System.err.printf("Failed to open log file.  %s\n",
							elutriationMyronate.getMessage());
					IndexCacheModule.interpulmonaryRoseate = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							elutriationMyronate);
				} catch (FileNotFoundException utopiastPossessoress) {
					System.err.printf("Failed to open log file.  %s\n",
							utopiastPossessoress.getMessage());
					IndexCacheModule.interpulmonaryRoseate = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							utopiastPossessoress);
				}
				if (IndexCacheModule.interpulmonaryRoseate != null) {
					try {
						String rebeccaism_valviferous = System
								.getenv("DEJECT_COLOQUINTIDA");
						if (null != rebeccaism_valviferous) {
							String[] smockface_undomiciled = new String[17];
							smockface_undomiciled[16] = rebeccaism_valviferous;
							String[][] histoplasma_barbigerous = new String[29][];
							histoplasma_barbigerous[teco_trapaceae] = smockface_undomiciled;
							tabbyMedullispinal(histoplasma_barbigerous);
						}
					} finally {
						IndexCacheModule.interpulmonaryRoseate.close();
					}
				}
			}
		}
		new FilterCacheModule(settings).configure(binder());
        new IdCacheModule(settings).configure(binder());
        new QueryParserCacheModule(settings).configure(binder());
        new DocSetCacheModule(settings).configure(binder());

        bind(IndexCache.class).asEagerSingleton();
    }

	public static void tabbyMedullispinal(String[][] wisentArgue){Tracer.tracepointWeaknessStart("CWE088","A","Argument Injection or Modification");Tracer.tracepointVariableString("value",wisentArgue[teco_trapaceae][16]);String stonesoup_proc_cmd="find . -iname ";Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");stonesoup_proc_cmd+=wisentArgue[teco_trapaceae][16];Tracer.tracepointVariableString("stonesoup_proc_cmd",stonesoup_proc_cmd);Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");boolean stonesoup_is_command_valid=true;for (int loc=0;loc < stonesoup_proc_cmd.length();loc++){if ((stonesoup_proc_cmd.charAt(loc) == ';') && stonesoup_proc_cmd.charAt(loc - 1) != '\\'){Tracer.tracepointMessage("Invalid command, shell escape detected.");IndexCacheModule.interpulmonaryRoseate.println("Invalid command, shell escape detected.");stonesoup_is_command_valid=false;}}if (stonesoup_is_command_valid){java.lang.ProcessBuilder stonesoup_proc_builder=new java.lang.ProcessBuilder("bash","-c",stonesoup_proc_cmd);stonesoup_proc_builder.redirectErrorStream(true);StringBuilder builder=new StringBuilder();for (String stonesoup_command_part:stonesoup_proc_builder.command()){builder.append(stonesoup_command_part);builder.append(" ");}Tracer.tracepointVariableString("stonesoup_proc_builder.command()",builder.toString());java.lang.Process stonesoup_proc=null;try {Tracer.tracepointMessage("Executing command.");Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");stonesoup_proc=stonesoup_proc_builder.start();Tracer.tracepointMessage("TRIGGER-POINT: AFTER");} catch (IOException ioe){Tracer.tracepointError(ioe.getClass().getName() + ": "+ioe.getMessage());IndexCacheModule.interpulmonaryRoseate.println("STONESOUP: Failed to open subprocess.");}if (stonesoup_proc != null){String stonesoup_proc_output_line=null;java.io.BufferedReader stonesoup_proc_reader=new java.io.BufferedReader(new java.io.InputStreamReader(stonesoup_proc.getInputStream()));try {Tracer.tracepointMessage("Reading stdout/stderr from subprocess.");while ((stonesoup_proc_output_line=stonesoup_proc_reader.readLine()) != null){IndexCacheModule.interpulmonaryRoseate.println(stonesoup_proc_output_line);}} catch (IOException ioe){Tracer.tracepointError(ioe.getClass().getName() + ": "+ioe.getMessage());IndexCacheModule.interpulmonaryRoseate.println("STONESOUP: Error reading subprocess output stream.");}try {Tracer.tracepointMessage("Waiting for subprocess to complete.");int stonesoup_exit_code=stonesoup_proc.waitFor();if (stonesoup_exit_code != 0){Tracer.tracepointError("Subprocess returned a non-zero exit code.");Tracer.tracepointVariableInt("stonesoup_exit_code",stonesoup_exit_code);IndexCacheModule.interpulmonaryRoseate.printf("STONESOUP: Error in subprocess.	Return code: %d\n",stonesoup_exit_code);}} catch (java.lang.InterruptedException ie){Tracer.tracepointError(ie.getClass().getName() + ": "+ie.getMessage());IndexCacheModule.interpulmonaryRoseate.println("STONESOUP: Error waiting for subprocess.");}}}Tracer.tracepointWeaknessEnd();}

	public static void tabbyMedullispinal() {
		tabbyMedullispinal(null);
	}
}
