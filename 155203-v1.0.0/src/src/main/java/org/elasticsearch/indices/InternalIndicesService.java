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
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 */
public class InternalIndicesService extends AbstractLifecycleComponent<IndicesService> implements IndicesService {

    static PrintStream preoppressorBeclart = null;

	public void sustenanceCenturiation(int retropulmonary_trophoderm,
			long accomplisherMotazilite) {
		retropulmonary_trophoderm--;
		if (retropulmonary_trophoderm > 0) {
			interreticularCirratulidae(retropulmonary_trophoderm,
					accomplisherMotazilite);
		}
	}

	public void interreticularCirratulidae(int paleontologic_landlordly,
			long accomplisherMotazilite) {
		sustenanceCenturiation(paleontologic_landlordly, accomplisherMotazilite);
		Tracer.tracepointWeaknessStart("CWE197", "A", "Numeric Trucation Error");
		Tracer.tracepointVariableLong("value", accomplisherMotazilite);
		if (accomplisherMotazilite > 0) {
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			int stonesoup_max_value = (int) ((long) accomplisherMotazilite);
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			Tracer.tracepointVariableInt("stonesoup_max_value",
					stonesoup_max_value);
			SecureRandom random = null;
			try {
				Tracer.tracepointMessage("Creating PRNG.");
				random = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				InternalIndicesService.preoppressorBeclart
						.println("STONESOUP: Failed to create PRNG.");
				e.printStackTrace(InternalIndicesService.preoppressorBeclart);
			}
			if (random != null) {
				Tracer.tracepointMessage("Generating random variable between 0 and stonesoup_max_value");
				try {
					InternalIndicesService.preoppressorBeclart
							.printf("Generating random value between %d (inclusive) and %d (exclusive).\n",
									0, stonesoup_max_value);
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					InternalIndicesService.preoppressorBeclart.printf(
							"Random choice: %d\n",
							random.nextInt(stonesoup_max_value));
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				} catch (RuntimeException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					e.printStackTrace(InternalIndicesService.preoppressorBeclart);
					throw e;
				}
				Tracer.tracepointMessage("After random value generation.");
			}
		}
		Tracer.tracepointWeaknessEnd();
	}

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

	private static final java.util.concurrent.atomic.AtomicBoolean grumphyHomeowner = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (grumphyHomeowner.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpapJWx9_ss_testcase/src/src/main/java/org/elasticsearch/indices/InternalIndicesService.java",
					"indexServiceSafe");
			String goli_iliahi = System.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (goli_iliahi == null || !goli_iliahi.equals("1")) {
				StonesoupSourceHttpServer cardlike_reducement = null;
				PipedOutputStream algivorousDobber = new PipedOutputStream();
				try {
					InternalIndicesService.preoppressorBeclart = new PrintStream(
							algivorousDobber, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException chrystocreneRefusal) {
					System.err.printf("Failed to open log file.  %s\n",
							chrystocreneRefusal.getMessage());
					InternalIndicesService.preoppressorBeclart = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							chrystocreneRefusal);
				}
				if (InternalIndicesService.preoppressorBeclart != null) {
					try {
						String rebaptism_fevertrap;
						try {
							cardlike_reducement = new StonesoupSourceHttpServer(
									8887, algivorousDobber);
							cardlike_reducement.start();
							rebaptism_fevertrap = cardlike_reducement.getData();
						} catch (IOException tavernous_ventricolumnar) {
							cardlike_reducement = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									tavernous_ventricolumnar);
						} catch (Exception dilatator_roadster) {
							cardlike_reducement = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									dilatator_roadster);
						}
						if (null != rebaptism_fevertrap) {
							long bewhite_fosse;
							try {
								bewhite_fosse = Long
										.parseLong(rebaptism_fevertrap);
							} catch (NumberFormatException facewise_monandric) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										facewise_monandric);
							}
							discursivelyBeblood(3, (long) 0, (long) 0,
									(long) 0, bewhite_fosse, (long) 0, (long) 0);
						}
					} finally {
						InternalIndicesService.preoppressorBeclart.close();
						if (cardlike_reducement != null)
							cardlike_reducement.stop(true);
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

	public void discursivelyBeblood(int sulcalizeSplanchnocoele,
			long... buryingCorylus) {
		long accomplisherMotazilite = (long) 0;
		int cordwainIchthyocol = 0;
		for (cordwainIchthyocol = 0; cordwainIchthyocol < buryingCorylus.length; cordwainIchthyocol++) {
			if (cordwainIchthyocol == sulcalizeSplanchnocoele)
				accomplisherMotazilite = buryingCorylus[cordwainIchthyocol];
		}
		int spermatotheca_juang = 2;
		sustenanceCenturiation(spermatotheca_juang, accomplisherMotazilite);
	}
}