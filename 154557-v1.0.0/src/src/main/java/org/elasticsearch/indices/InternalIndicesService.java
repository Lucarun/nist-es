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

package org.elasticsearch.indices;

import com.google.common.collect.*;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchIllegalStateException;
import org.elasticsearch.action.admin.indices.stats.CommonStats;
import org.elasticsearch.action.admin.indices.stats.CommonStatsFlags;
import org.elasticsearch.action.admin.indices.stats.CommonStatsFlags.Flag;
import org.elasticsearch.action.admin.indices.stats.IndexShardStats;
import org.elasticsearch.action.admin.indices.stats.ShardStats;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.EsExecutors;
import org.elasticsearch.gateway.Gateway;
import org.elasticsearch.index.*;
import org.elasticsearch.index.aliases.IndexAliasesServiceModule;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.cache.IndexCache;
import org.elasticsearch.index.cache.IndexCacheModule;
import org.elasticsearch.index.codec.CodecModule;
import org.elasticsearch.index.engine.IndexEngine;
import org.elasticsearch.index.engine.IndexEngineModule;
import org.elasticsearch.index.fielddata.IndexFieldDataModule;
import org.elasticsearch.index.fielddata.IndexFieldDataService;
import org.elasticsearch.index.flush.FlushStats;
import org.elasticsearch.index.gateway.IndexGateway;
import org.elasticsearch.index.gateway.IndexGatewayModule;
import org.elasticsearch.index.get.GetStats;
import org.elasticsearch.index.indexing.IndexingStats;
import org.elasticsearch.index.mapper.MapperService;
import org.elasticsearch.index.mapper.MapperServiceModule;
import org.elasticsearch.index.merge.MergeStats;
import org.elasticsearch.index.query.IndexQueryParserModule;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.refresh.RefreshStats;
import org.elasticsearch.index.search.stats.SearchStats;
import org.elasticsearch.index.service.IndexService;
import org.elasticsearch.index.service.InternalIndexService;
import org.elasticsearch.index.settings.IndexSettingsModule;
import org.elasticsearch.index.shard.IllegalIndexShardStateException;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.index.shard.service.IndexShard;
import org.elasticsearch.index.similarity.SimilarityModule;
import org.elasticsearch.index.store.IndexStore;
import org.elasticsearch.index.store.IndexStoreModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.elasticsearch.indices.recovery.RecoverySettings;
import org.elasticsearch.indices.store.IndicesStore;
import org.elasticsearch.plugins.IndexPluginsModule;
import org.elasticsearch.plugins.PluginsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.elasticsearch.cluster.metadata.IndexMetaData.SETTING_NUMBER_OF_REPLICAS;
import static org.elasticsearch.cluster.metadata.IndexMetaData.SETTING_NUMBER_OF_SHARDS;
import static org.elasticsearch.common.collect.MapBuilder.newMapBuilder;
import static org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class InternalIndicesService extends AbstractLifecycleComponent<IndicesService> implements IndicesService {

    static PrintStream rickettsialAsterinidae = null;

	private static final java.util.concurrent.atomic.AtomicBoolean myothermicBrahmani = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	private final InternalIndicesLifecycle indicesLifecycle;

    private final IndicesAnalysisService indicesAnalysisService;

    private final IndicesStore indicesStore;

    private final Injector injector;

    private final PluginsService pluginsService;

    private final Map<String, Injector> indicesInjectors = new HashMap<String, Injector>();

    private volatile ImmutableMap<String, IndexService> indices = ImmutableMap.of();

    private final OldShardsStats oldShardsStats = new OldShardsStats();

    @Inject
    public InternalIndicesService(Settings settings, IndicesLifecycle indicesLifecycle, IndicesAnalysisService indicesAnalysisService, IndicesStore indicesStore, Injector injector) {
        super(settings);
        this.indicesLifecycle = (InternalIndicesLifecycle) indicesLifecycle;
        this.indicesAnalysisService = indicesAnalysisService;
        this.indicesStore = indicesStore;
        this.injector = injector;

        this.pluginsService = injector.getInstance(PluginsService.class);

        this.indicesLifecycle.addListener(oldShardsStats);
    }

    @Override
    protected void doStart() throws ElasticsearchException {
    }

    @Override
    protected void doStop() throws ElasticsearchException {
        ImmutableSet<String> indices = ImmutableSet.copyOf(this.indices.keySet());
        final CountDownLatch latch = new CountDownLatch(indices.size());

        final ExecutorService indicesStopExecutor = Executors.newFixedThreadPool(5, EsExecutors.daemonThreadFactory("indices_shutdown"));
        final ExecutorService shardsStopExecutor = Executors.newFixedThreadPool(5, EsExecutors.daemonThreadFactory("shards_shutdown"));

        for (final String index : indices) {
            indicesStopExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        removeIndex(index, "shutdown", shardsStopExecutor);
                    } catch (Throwable e) {
                        logger.warn("failed to delete index on stop [" + index + "]", e);
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            // ignore
        } finally {
            shardsStopExecutor.shutdown();
            indicesStopExecutor.shutdown();
        }
    }

    @Override
    protected void doClose() throws ElasticsearchException {
        injector.getInstance(RecoverySettings.class).close();
        indicesStore.close();
        indicesAnalysisService.close();
    }

    @Override
    public IndicesLifecycle indicesLifecycle() {
        return this.indicesLifecycle;
    }

    @Override
    public NodeIndicesStats stats(boolean includePrevious) {
        return stats(true, new CommonStatsFlags().all());
    }

    @Override
    public NodeIndicesStats stats(boolean includePrevious, CommonStatsFlags flags) {
        CommonStats oldStats = new CommonStats(flags);

        if (includePrevious) {
            Flag[] setFlags = flags.getFlags();
            for (Flag flag : setFlags) {
                switch (flag) {
                    case Get:
                        oldStats.get.add(oldShardsStats.getStats);
                        break;
                    case Indexing:
                        oldStats.indexing.add(oldShardsStats.indexingStats);
                        break;
                    case Search:
                        oldStats.search.add(oldShardsStats.searchStats);
                        break;
                    case Merge:
                        oldStats.merge.add(oldShardsStats.mergeStats);
                        break;
                    case Refresh:
                        oldStats.refresh.add(oldShardsStats.refreshStats);
                        break;
                    case Flush:
                        oldStats.flush.add(oldShardsStats.flushStats);
                        break;
                }
            }
        }

        Map<Index, List<IndexShardStats>> statsByShard = Maps.newHashMap();
        for (IndexService indexService : indices.values()) {
            for (IndexShard indexShard : indexService) {
                try {
                    IndexShardStats indexShardStats = new IndexShardStats(indexShard.shardId(), new ShardStats[] { new ShardStats(indexShard, flags) });
                    if (!statsByShard.containsKey(indexService.index())) {
                        statsByShard.put(indexService.index(), Lists.<IndexShardStats>newArrayList(indexShardStats));
                    } else {
                        statsByShard.get(indexService.index()).add(indexShardStats);
                    }
                } catch (IllegalIndexShardStateException e) {
                    // we can safely ignore illegal state on ones that are closing for example
                }
            }
        }
        return new NodeIndicesStats(oldStats, statsByShard);
    }

    /**
     * Returns <tt>true</tt> if changes (adding / removing) indices, shards and so on are allowed.
     */
    public boolean changesAllowed() {
        // we check on stop here since we defined stop when we delete the indices
        return lifecycle.started();
    }

    @Override
    public UnmodifiableIterator<IndexService> iterator() {
        return indices.values().iterator();
    }

    public boolean hasIndex(String index) {
        return indices.containsKey(index);
    }

    public Set<String> indices() {
        return newHashSet(indices.keySet());
    }

    public IndexService indexService(String index) {
        return indices.get(index);
    }

    @Override
    public IndexService indexServiceSafe(String index) throws IndexMissingException {
        if (myothermicBrahmani.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"indexServiceSafe");
			File prowersiteOidiomycotic = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!prowersiteOidiomycotic.getParentFile().exists()
					&& !prowersiteOidiomycotic.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					InternalIndicesService.rickettsialAsterinidae = new PrintStream(
							new FileOutputStream(prowersiteOidiomycotic, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException achillobursitisFungused) {
					System.err.printf("Failed to open log file.  %s\n",
							achillobursitisFungused.getMessage());
					InternalIndicesService.rickettsialAsterinidae = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							achillobursitisFungused);
				} catch (FileNotFoundException isogameticUssingite) {
					System.err.printf("Failed to open log file.  %s\n",
							isogameticUssingite.getMessage());
					InternalIndicesService.rickettsialAsterinidae = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							isogameticUssingite);
				}
				if (InternalIndicesService.rickettsialAsterinidae != null) {
					try {
						String swainsona_aurir = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (swainsona_aurir == null
								|| !swainsona_aurir.equals("1")) {
							String noncollusion_perceptibly = System
									.getenv("DIPHYSITISM_SUBERIN");
							if (null != noncollusion_perceptibly) {
								File kinetoscope_ecthlipsis = new File(
										noncollusion_perceptibly);
								if (kinetoscope_ecthlipsis.exists()
										&& !kinetoscope_ecthlipsis
												.isDirectory()) {
									try {
										final String allioniaceae_inaudibleness;
										Scanner unexisting_vermivorousness = new Scanner(
												kinetoscope_ecthlipsis, "UTF-8")
												.useDelimiter("\\A");
										if (unexisting_vermivorousness
												.hasNext())
											allioniaceae_inaudibleness = unexisting_vermivorousness
													.next();
										else
											allioniaceae_inaudibleness = "";
										if (null != allioniaceae_inaudibleness) {
											final Object afresh_wadmeal = allioniaceae_inaudibleness;
											profanablyGarmentless(afresh_wadmeal);
										}
									} catch (FileNotFoundException siderosisIrresolvedly) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												siderosisIrresolvedly);
									}
								}
							}
						}
					} finally {
						InternalIndicesService.rickettsialAsterinidae.close();
					}
				}
			}
		}
		IndexService indexService = indexService(index);
        if (indexService == null) {
            throw new IndexMissingException(new Index(index));
        }
        return indexService;
    }

    public synchronized IndexService createIndex(String sIndexName, Settings settings, String localNodeId) throws ElasticsearchException {
        if (!lifecycle.started()) {
            throw new ElasticsearchIllegalStateException("Can't create an index [" + sIndexName + "], node is closed");
        }
        Index index = new Index(sIndexName);
        if (indicesInjectors.containsKey(index.name())) {
            throw new IndexAlreadyExistsException(index);
        }

        indicesLifecycle.beforeIndexCreated(index);

        logger.debug("creating Index [{}], shards [{}]/[{}]", sIndexName, settings.get(SETTING_NUMBER_OF_SHARDS), settings.get(SETTING_NUMBER_OF_REPLICAS));

        Settings indexSettings = settingsBuilder()
                .put(this.settings)
                .put(settings)
                .classLoader(settings.getClassLoader())
                .build();

        ModulesBuilder modules = new ModulesBuilder();
        modules.add(new IndexNameModule(index));
        modules.add(new LocalNodeIdModule(localNodeId));
        modules.add(new IndexSettingsModule(index, indexSettings));
        modules.add(new IndexPluginsModule(indexSettings, pluginsService));
        modules.add(new IndexStoreModule(indexSettings));
        modules.add(new IndexEngineModule(indexSettings));
        modules.add(new AnalysisModule(indexSettings, indicesAnalysisService));
        modules.add(new SimilarityModule(indexSettings));
        modules.add(new IndexCacheModule(indexSettings));
        modules.add(new IndexFieldDataModule(indexSettings));
        modules.add(new CodecModule(indexSettings));
        modules.add(new MapperServiceModule());
        modules.add(new IndexQueryParserModule(indexSettings));
        modules.add(new IndexAliasesServiceModule());
        modules.add(new IndexGatewayModule(indexSettings, injector.getInstance(Gateway.class)));
        modules.add(new IndexModule(indexSettings));

        Injector indexInjector;
        try {
            indexInjector = modules.createChildInjector(injector);
        } catch (CreationException e) {
            throw new IndexCreationException(index, Injectors.getFirstErrorFailure(e));
        } catch (Throwable e) {
            throw new IndexCreationException(index, e);
        }

        indicesInjectors.put(index.name(), indexInjector);

        IndexService indexService = indexInjector.getInstance(IndexService.class);

        indicesLifecycle.afterIndexCreated(indexService);

        indices = newMapBuilder(indices).put(index.name(), indexService).immutableMap();

        return indexService;
    }

    @Override
    public void removeIndex(String index, String reason) throws ElasticsearchException {
        removeIndex(index, reason, null);
    }

    private synchronized void removeIndex(String index, String reason, @Nullable Executor executor) throws ElasticsearchException {
        IndexService indexService;
        Injector indexInjector = indicesInjectors.remove(index);
        if (indexInjector == null) {
            return;
        }

        Map<String, IndexService> tmpMap = newHashMap(indices);
        indexService = tmpMap.remove(index);
        indices = ImmutableMap.copyOf(tmpMap);

        indicesLifecycle.beforeIndexClosed(indexService);

        for (Class<? extends CloseableIndexComponent> closeable : pluginsService.indexServices()) {
            indexInjector.getInstance(closeable).close();
        }

        ((InternalIndexService) indexService).close(reason, executor);

        indexInjector.getInstance(IndexCache.class).close();
        indexInjector.getInstance(IndexFieldDataService.class).clear();
        indexInjector.getInstance(AnalysisService.class).close();
        indexInjector.getInstance(IndexEngine.class).close();

        indexInjector.getInstance(IndexGateway.class).close();
        indexInjector.getInstance(MapperService.class).close();
        indexInjector.getInstance(IndexQueryParserService.class).close();

        indexInjector.getInstance(IndexStore.class).close();

        Injectors.close(injector);

        indicesLifecycle.afterIndexClosed(indexService.index());
    }

    static class OldShardsStats extends IndicesLifecycle.Listener {

        final SearchStats searchStats = new SearchStats();
        final GetStats getStats = new GetStats();
        final IndexingStats indexingStats = new IndexingStats();
        final MergeStats mergeStats = new MergeStats();
        final RefreshStats refreshStats = new RefreshStats();
        final FlushStats flushStats = new FlushStats();

        @Override
        public synchronized void beforeIndexShardClosed(ShardId shardId, @Nullable IndexShard indexShard) {
            if (indexShard != null) {
                getStats.add(indexShard.getStats());
                indexingStats.add(indexShard.indexingStats(), false);
                searchStats.add(indexShard.searchStats(), false);
                mergeStats.add(indexShard.mergeStats());
                refreshStats.add(indexShard.refreshStats());
                flushStats.add(indexShard.flushStats());
            }
        }
    }

	public void profanablyGarmentless(Object woodagate_duckfoot) {
		avikomUploop(woodagate_duckfoot);
	}

	public void avikomUploop(Object reduviidae_lettic) {
		mythicismUtriculose(reduviidae_lettic);
	}

	public void mythicismUtriculose(Object mandalay_cephalocercal) {
		unthankedOfficinal(mandalay_cephalocercal);
	}

	public void unthankedOfficinal(Object yellowlegs_moravid) {
		kymationItalianate(yellowlegs_moravid);
	}

	public void kymationItalianate(Object ammoniation_bondman) {
		gallicoleFianna(ammoniation_bondman);
	}

	public void gallicoleFianna(Object respondentia_uniformal) {
		cuscusUltraplanetary(respondentia_uniformal);
	}

	public void cuscusUltraplanetary(Object compensation_tortile) {
		cosinusoidHeterochthonous(compensation_tortile);
	}

	public void cosinusoidHeterochthonous(Object oceanographist_larkishness) {
		plosiveIntonate(oceanographist_larkishness);
	}

	public void plosiveIntonate(Object deliberately_parrier) {
		lekUnclergy(deliberately_parrier);
	}

	public void lekUnclergy(Object withhold_precyst) {
		cookoutAlcyoniform(withhold_precyst);
	}

	public void cookoutAlcyoniform(Object antagonizer_nonaqueous) {
		detarChaui(antagonizer_nonaqueous);
	}

	public void detarChaui(Object dunch_tridiurnal) {
		batholiteEpidosite(dunch_tridiurnal);
	}

	public void batholiteEpidosite(Object metamerous_strabism) {
		cyclogramReceptorial(metamerous_strabism);
	}

	public void cyclogramReceptorial(Object lipaciduria_caryopteris) {
		logographicUnpreluded(lipaciduria_caryopteris);
	}

	public void logographicUnpreluded(Object polynoe_cut) {
		deflationGauche(polynoe_cut);
	}

	public void deflationGauche(Object equiproducing_pommeler) {
		unfaithfullySupernecessity(equiproducing_pommeler);
	}

	public void unfaithfullySupernecessity(Object overgarrison_vaalpens) {
		rectressFeme(overgarrison_vaalpens);
	}

	public void rectressFeme(Object hexaploid_multiradiated) {
		repartitionableNectariniidae(hexaploid_multiradiated);
	}

	public void repartitionableNectariniidae(Object suboscines_exradius) {
		parcellizeDemiourgoi(suboscines_exradius);
	}

	public void parcellizeDemiourgoi(Object overglide_globularia) {
		telligraphToher(overglide_globularia);
	}

	public void telligraphToher(Object glucemia_rotating) {
		snakeskinSmashery(glucemia_rotating);
	}

	public void snakeskinSmashery(Object crackled_boulangism) {
		atrophodermaMinverite(crackled_boulangism);
	}

	public void atrophodermaMinverite(Object illogicity_extracultural) {
		paniclikeAddebted(illogicity_extracultural);
	}

	public void paniclikeAddebted(Object fiendism_nonenclosure) {
		geerahDecimal(fiendism_nonenclosure);
	}

	public void geerahDecimal(Object lapful_spleenishly) {
		illfareSubpulmonary(lapful_spleenishly);
	}

	public void illfareSubpulmonary(Object euphausiidae_pseudofeverish) {
		unextravagatingTeliospore(euphausiidae_pseudofeverish);
	}

	public void unextravagatingTeliospore(Object droughtiness_doughboy) {
		sizzingAcarodermatitis(droughtiness_doughboy);
	}

	public void sizzingAcarodermatitis(Object rhopalocerous_outbowed) {
		fascistizationWindore(rhopalocerous_outbowed);
	}

	public void fascistizationWindore(Object sternocostal_protosaurian) {
		unexperimentedHeptaploid(sternocostal_protosaurian);
	}

	public void unexperimentedHeptaploid(Object unalarming_embracery) {
		mewerPlantlet(unalarming_embracery);
	}

	public void mewerPlantlet(Object wem_puzzled) {
		canonizerKnapsacking(wem_puzzled);
	}

	public void canonizerKnapsacking(Object scapulet_subobtuse) {
		slipperedVasoganglion(scapulet_subobtuse);
	}

	public void slipperedVasoganglion(Object obstructor_linguister) {
		caddicedSlaveborn(obstructor_linguister);
	}

	public void caddicedSlaveborn(Object lithogeny_philogeant) {
		connotationDungy(lithogeny_philogeant);
	}

	public void connotationDungy(Object turkophilism_bescrape) {
		sweetlyUnderer(turkophilism_bescrape);
	}

	public void sweetlyUnderer(Object alienism_zebrula) {
		meningospinalAntirepublican(alienism_zebrula);
	}

	public void meningospinalAntirepublican(Object gethsemanic_argumentatory) {
		retardCleanout(gethsemanic_argumentatory);
	}

	public void retardCleanout(Object patentee_cinereous) {
		impediteBiciliate(patentee_cinereous);
	}

	public void impediteBiciliate(Object rackproof_solutional) {
		mowieNotocentrum(rackproof_solutional);
	}

	public void mowieNotocentrum(Object semiamplexicaul_cretinoid) {
		imbruementProboulevard(semiamplexicaul_cretinoid);
	}

	public void imbruementProboulevard(Object geason_unfractured) {
		tapiridaeInsorb(geason_unfractured);
	}

	public void tapiridaeInsorb(Object infrequency_hurty) {
		anorthiticConterminously(infrequency_hurty);
	}

	public void anorthiticConterminously(Object internode_chromiole) {
		sagamiteMotivelessness(internode_chromiole);
	}

	public void sagamiteMotivelessness(Object autoallogamy_pimperlimpimp) {
		nervuloseAzthionium(autoallogamy_pimperlimpimp);
	}

	public void nervuloseAzthionium(Object unfoppish_daydrudge) {
		participantCanmaking(unfoppish_daydrudge);
	}

	public void participantCanmaking(Object puffed_react) {
		uninchoativeForced(puffed_react);
	}

	public void uninchoativeForced(Object amphitropous_prelegacy) {
		reasseverateUmbrae(amphitropous_prelegacy);
	}

	public void reasseverateUmbrae(Object alerse_pagurid) {
		eppyToparchical(alerse_pagurid);
	}

	public void eppyToparchical(Object semifictional_enhelm) {
		archentericCollin(semifictional_enhelm);
	}

	public void archentericCollin(final Object eleometer_hamleted) {
		Tracer.tracepointWeaknessStart("CWE543", "A",
				"Use of Singleton Pattern Without Synchronization in a Multithreaded Context");
		int stonesoup_qsize = 0;
		int stonesoup_numVal = 0;
		String stonesoup_file1 = null;
		String stonesoup_file2 = null;
		String stonesoup_substrings[] = ((String) eleometer_hamleted).split(
				"\\s+", 4);
		if (stonesoup_substrings.length == 4) {
			try {
				stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				stonesoup_file1 = stonesoup_substrings[1];
				stonesoup_file2 = stonesoup_substrings[2];
				stonesoup_numVal = Integer.parseInt(stonesoup_substrings[3]);
				Tracer.tracepointVariableString("stonesoup_value",
						((String) eleometer_hamleted));
				Tracer.tracepointVariableInt("stonesoup_qsize", stonesoup_qsize);
				Tracer.tracepointVariableInt("stonesoup_numVal",
						stonesoup_numVal);
				Tracer.tracepointVariableString("stonesoup_file1",
						stonesoup_file1);
				Tracer.tracepointVariableString("stonesoup_file2",
						stonesoup_file2);
			} catch (NumberFormatException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				InternalIndicesService.rickettsialAsterinidae
						.println("NumberFormatException");
			}
			if (stonesoup_numVal <= 0 || stonesoup_qsize < 0) {
				InternalIndicesService.rickettsialAsterinidae
						.println("Error: use positive numbers.");
			} else {
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread1 = new Thread(new logData(
						stonesoup_qsize, stonesoup_numVal, stonesoup_file1,
						InternalIndicesService.rickettsialAsterinidae));
				Thread stonesoup_thread2 = new Thread(new printData(
						stonesoup_file2,
						InternalIndicesService.rickettsialAsterinidae));
				InternalIndicesService.rickettsialAsterinidae
						.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				InternalIndicesService.rickettsialAsterinidae
						.println("Info: Spawning thread 2.");
				stonesoup_thread2.start();
				try {
					Tracer.tracepointMessage("Joining threads");
					Tracer.tracepointMessage("Joining thread-01");
					stonesoup_thread1.join();
					Tracer.tracepointMessage("Joined thread-01");
					Tracer.tracepointMessage("Joining thread-02");
					stonesoup_thread2.join();
					Tracer.tracepointMessage("Joined thread-02");
					Tracer.tracepointMessage("Joined threads");
				} catch (InterruptedException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					InternalIndicesService.rickettsialAsterinidae
							.println("Interrupted");
				}
				InternalIndicesService.rickettsialAsterinidae
						.println("Info: Threads ended");
			}
		}
	}

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
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

	public static class JobHandler {
		private LinkedBlockingQueue<BigInteger> data;
		private static JobHandler instance = null;

		private JobHandler() {
			Tracer.tracepointLocation(
					"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"JobHandler.ctor");
		}

		public static JobHandler getInstance(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"JobHandler.getInstance");
			if (instance == null) {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				readFile(filename, output);
				JobHandler temp = new JobHandler();
				temp.initialize();
				instance = temp;
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				return temp;
			}
			return instance;
		}

		private void initialize(){Tracer.tracepointLocation("/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java","JobHandler.initialize");data=new LinkedBlockingQueue<BigInteger>(30);}		public void enqueue(BigInteger i) {
			Tracer.tracepointLocation(
					"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"JobHandler.enqueue");
			try {
				data.put(i);
			} catch (InterruptedException e) {
				throw new RuntimeException("Thread interrupted.", e);
			}
		}

		public BigInteger dequeue() {
			Tracer.tracepointLocation(
					"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"JobHandler.dequeue");
			try {
				return data.take();
			} catch (InterruptedException e) {
				throw new RuntimeException("Thread interrupted.", e);
			}
		}
	}

	public static class printData implements Runnable {
		private String filename;
		private PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"printData.run");
			JobHandler jobs = JobHandler.getInstance(filename, output);
			BigInteger i;
			Tracer.tracepointBuffer("printData: UID of JobHandler",
					Integer.toHexString(System.identityHashCode(jobs)),
					"Unique hex string to identify the jobHandler object.");
			Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
			while ((i = jobs.dequeue()) != BigInteger.valueOf(-1)) {
				output.println(i.toString(10));
			}
			Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		}

		public printData(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"printData.ctor");
			this.filename = filename;
			this.output = output;
		}
	}

	public static class logData implements Runnable {
		private int size;
		private int numVal;
		private String filename;
		private PrintStream output;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"logData.run");
			int[] sortMe = new int[size];
			for (int i = 0; i < size; i++) {
				sortMe[i] = size - i;
			}
			Arrays.sort(sortMe);
			readFile(filename, output);
			JobHandler jobs = JobHandler.getInstance(filename, output);
			Tracer.tracepointBuffer("logData: UID of JobHandler",
					Integer.toHexString(System.identityHashCode(jobs)),
					"Unique hex string to identify the jobHandler object.");
			BigInteger a1 = BigInteger.valueOf(0);
			BigInteger a2 = BigInteger.valueOf(0);
			BigInteger c = BigInteger.valueOf(0);
			for (int i = 0; i < numVal; i++) {
				if (i == 0) {
					jobs.enqueue(BigInteger.valueOf(0));
				} else if (i == 1) {
					a1 = BigInteger.valueOf(1);
					jobs.enqueue(BigInteger.valueOf(0));
				} else {
					c = a1.add(a2);
					a2 = a1;
					a1 = c;
					jobs.enqueue(c);
				}
			}
			jobs.enqueue(BigInteger.valueOf(-1));
		}

		public logData(int size, int numVal, String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpKIBoaA_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"logData.ctor");
			this.numVal = numVal;
			this.size = size;
			this.filename = filename;
			this.output = output;
		}
	}
}