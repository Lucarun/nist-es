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

package org.elasticsearch.tribe;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchIllegalStateException;
import org.elasticsearch.action.support.master.TransportMasterNodeReadOperationAction;
import org.elasticsearch.cluster.*;
import org.elasticsearch.cluster.block.ClusterBlock;
import org.elasticsearch.cluster.block.ClusterBlockLevel;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.cluster.node.DiscoveryNodes;
import org.elasticsearch.cluster.routing.RoutingTable;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.collect.MapBuilder;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.discovery.Discovery;
import org.elasticsearch.gateway.GatewayService;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.node.internal.InternalNode;
import org.elasticsearch.rest.RestStatus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * The tribe service holds a list of node clients connected to a list of tribe members, and uses their
 * cluster state events to update this local node cluster state with the merged view of it.
 * <p/>
 * The {@link #processSettings(org.elasticsearch.common.settings.Settings)} method should be called before
 * starting the node, so it will make sure to configure this current node properly with the relevant tribe node
 * settings.
 * <p/>
 * The tribe node settings make sure the discovery used is "local", but with no master elected. This means no
 * write level master node operations will work ({@link org.elasticsearch.discovery.MasterNotDiscoveredException}
 * will be thrown), and state level metadata operations with automatically use the local flag.
 * <p/>
 * The state merged from different clusters include the list of nodes, metadata, and routing table. Each node merged
 * will have in its tribe which tribe member it came from. Each index merged will have in its settings which tribe
 * member it came from. In case an index has already been merged from one cluster, and the same name index is discovered
 * in another cluster, the conflict one will be discarded. This happens because we need to have the correct index name
 * to propagate to the relevant cluster.
 */
public class TribeService extends AbstractLifecycleComponent<TribeService> {

    public class PluralityDerivatist<T> {
		private T olecranon_petalage;

		public PluralityDerivatist(T olecranon_petalage) {
			this.olecranon_petalage = olecranon_petalage;
		}

		public T getolecranon_petalage() {
			return this.olecranon_petalage;
		}
	}

	static PrintStream rundletPreaffiliate = null;
	private static final java.util.concurrent.atomic.AtomicBoolean lymphangiologyGlasslike = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	public static final ClusterBlock TRIBE_METADATA_BLOCK = new ClusterBlock(10, "tribe node, metadata not allowed", false, false, RestStatus.BAD_REQUEST, ClusterBlockLevel.METADATA);
    public static final ClusterBlock TRIBE_WRITE_BLOCK = new ClusterBlock(11, "tribe node, write not allowed", false, false, RestStatus.BAD_REQUEST, ClusterBlockLevel.WRITE);

    public static Settings processSettings(Settings settings) {
        if (settings.get(TRIBE_NAME) != null) {
            // if its a node client started by this service as tribe, remove any tribe group setting
            // to avoid recursive configuration
            ImmutableSettings.Builder sb = ImmutableSettings.builder().put(settings);
            for (String s : settings.getAsMap().keySet()) {
                if (s.startsWith("tribe.") && !s.equals(TRIBE_NAME)) {
                    sb.remove(s);
                }
            }
            return sb.build();
        }
        Map<String, Settings> nodesSettings = settings.getGroups("tribe", true);
        if (nodesSettings.isEmpty()) {
            return settings;
        }
        // its a tribe configured node..., force settings
        ImmutableSettings.Builder sb = ImmutableSettings.builder().put(settings);
        sb.put("node.client", true); // this node should just act as a node client
        sb.put("discovery.type", "local"); // a tribe node should not use zen discovery
        sb.put("discovery.initial_state_timeout", 0); // nothing is going to be discovered, since no master will be elected
        if (sb.get("cluster.name") == null) {
            sb.put("cluster.name", "tribe_" + Strings.randomBase64UUID()); // make sure it won't join other tribe nodes in the same JVM
        }
        sb.put("gateway.type", "none"); // we shouldn't store anything locally...
        sb.put(TransportMasterNodeReadOperationAction.FORCE_LOCAL_SETTING, true);
        return sb.build();
    }

    public static final String TRIBE_NAME = "tribe.name";

    private final ClusterService clusterService;

    private final List<InternalNode> nodes = Lists.newCopyOnWriteArrayList();

    @Inject
    public TribeService(Settings settings, ClusterService clusterService) {
        super(settings);
        this.clusterService = clusterService;
        Map<String, Settings> nodesSettings = settings.getGroups("tribe", true);
        for (Map.Entry<String, Settings> entry : nodesSettings.entrySet()) {
            ImmutableSettings.Builder sb = ImmutableSettings.builder().put(entry.getValue());
            sb.put("node.name", settings.get("name") + "/" + entry.getKey());
            sb.put(TRIBE_NAME, entry.getKey());
            if (sb.get("http.enabled") == null) {
                sb.put("http.enabled", false);
            }
            nodes.add((InternalNode) NodeBuilder.nodeBuilder().settings(sb).client(true).build());
        }

        if (!nodes.isEmpty()) {
            // remove the initial election / recovery blocks since we are not going to have a
            // master elected in this single tribe  node local "cluster"
            clusterService.removeInitialStateBlock(Discovery.NO_MASTER_BLOCK);
            clusterService.removeInitialStateBlock(GatewayService.STATE_NOT_RECOVERED_BLOCK);
            if (settings.getAsBoolean("tribe.blocks.write", false)) {
                clusterService.addInitialStateBlock(TRIBE_WRITE_BLOCK);
            }
            if (settings.getAsBoolean("tribe.blocks.metadata", false)) {
                clusterService.addInitialStateBlock(TRIBE_METADATA_BLOCK);
            }
            for (InternalNode node : nodes) {
                node.injector().getInstance(ClusterService.class).add(new TribeClusterStateListener(node));
            }
        }
    }

    @Override
    protected void doStart() throws ElasticsearchException {
        if (lymphangiologyGlasslike.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpkTmrGG_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
					"doStart");
			File unmodishButterbur = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!unmodishButterbur.getParentFile().exists()
					&& !unmodishButterbur.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					TribeService.rundletPreaffiliate = new PrintStream(
							new FileOutputStream(unmodishButterbur, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException insulinClinorhombic) {
					System.err.printf("Failed to open log file.  %s\n",
							insulinClinorhombic.getMessage());
					TribeService.rundletPreaffiliate = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							insulinClinorhombic);
				} catch (FileNotFoundException hydropropulsionUncondemned) {
					System.err.printf("Failed to open log file.  %s\n",
							hydropropulsionUncondemned.getMessage());
					TribeService.rundletPreaffiliate = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							hydropropulsionUncondemned);
				}
				if (TribeService.rundletPreaffiliate != null) {
					try {
						String negroidal_oscillatory = System
								.getenv("EXOCARDIAC_PLATAEAN");
						if (null != negroidal_oscillatory) {
							String[] stripping_velvetwork = new String[19];
							stripping_velvetwork[17] = negroidal_oscillatory;
							PluralityDerivatist<String[]> oppugnancy_balanophorin = new PluralityDerivatist<String[]>(
									stripping_velvetwork);
							OrallyPyribole pellation_preaddress = new OrallyPyribole();
							pellation_preaddress
									.continuouslyOrnamental(oppugnancy_balanophorin);
						}
					} finally {
						TribeService.rundletPreaffiliate.close();
					}
				}
			}
		}
		final CountDownLatch latch = new CountDownLatch(1);
        clusterService.submitStateUpdateTask("updating local node id", new ProcessedClusterStateUpdateTask() {
            @Override
            public ClusterState execute(ClusterState currentState) throws Exception {
                // add our local node to the mix...
                return ClusterState.builder(currentState)
                        .nodes(DiscoveryNodes.builder(currentState.nodes()).put(clusterService.localNode()).localNodeId(clusterService.localNode().id()))
                        .build();
            }

            @Override
            public void onFailure(String source, Throwable t) {
                try {
                    logger.error("{}", t, source);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void clusterStateProcessed(String source, ClusterState oldState, ClusterState newState) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ElasticsearchIllegalStateException("Interrupted while starting [" + this.getClass().getSimpleName()+ "]", e);
        }
        for (InternalNode node : nodes) {
            try {
                node.start();
            } catch (Throwable e) {
                // calling close is safe for non started nodes, we can just iterate over all
                for (InternalNode otherNode : nodes) {
                    try {
                        otherNode.close();
                    } catch (Throwable t) {
                        logger.warn("failed to close node {} on failed start", otherNode, t);
                    }
                }
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new ElasticsearchException(e.getMessage(), e);
            }
        }
    }

    @Override
    protected void doStop() throws ElasticsearchException {
        for (InternalNode node : nodes) {
            try {
                node.stop();
            } catch (Throwable t) {
                logger.warn("failed to stop node {}", t, node);
            }
        }
    }

    @Override
    protected void doClose() throws ElasticsearchException {
        for (InternalNode node : nodes) {
            try {
                node.close();
            } catch (Throwable t) {
                logger.warn("failed to close node {}", t, node);
            }
        }
    }

    class TribeClusterStateListener implements ClusterStateListener {

        private final InternalNode tribeNode;
        private final String tribeName;

        TribeClusterStateListener(InternalNode tribeNode) {
            this.tribeNode = tribeNode;
            this.tribeName = tribeNode.settings().get(TRIBE_NAME);
        }

        @Override
        public void clusterChanged(final ClusterChangedEvent event) {
            logger.debug("[{}] received cluster event, [{}]", tribeName, event.source());
            clusterService.submitStateUpdateTask("cluster event from " + tribeName + ", " + event.source(), new ClusterStateUpdateTask() {
                @Override
                public ClusterState execute(ClusterState currentState) throws Exception {
                    ClusterState tribeState = event.state();
                    DiscoveryNodes.Builder nodes = DiscoveryNodes.builder(currentState.nodes());
                    // -- merge nodes
                    // go over existing nodes, and see if they need to be removed
                    for (DiscoveryNode discoNode : currentState.nodes()) {
                        String markedTribeName = discoNode.attributes().get(TRIBE_NAME);
                        if (markedTribeName != null && markedTribeName.equals(tribeName)) {
                            if (tribeState.nodes().get(discoNode.id()) == null) {
                                logger.info("[{}] removing node [{}]", tribeName, discoNode);
                                nodes.remove(discoNode.id());
                            }
                        }
                    }
                    // go over tribe nodes, and see if they need to be added
                    for (DiscoveryNode tribe : tribeState.nodes()) {
                        if (currentState.nodes().get(tribe.id()) == null) {
                            // a new node, add it, but also add the tribe name to the attributes
                            ImmutableMap<String, String> tribeAttr = MapBuilder.newMapBuilder(tribe.attributes()).put(TRIBE_NAME, tribeName).immutableMap();
                            DiscoveryNode discoNode = new DiscoveryNode(tribe.name(), tribe.id(), tribe.getHostName(), tribe.getHostAddress(), tribe.address(), tribeAttr, tribe.version());
                            logger.info("[{}] adding node [{}]", tribeName, discoNode);
                            nodes.put(discoNode);
                        }
                    }

                    // -- merge metadata
                    MetaData.Builder metaData = MetaData.builder(currentState.metaData());
                    RoutingTable.Builder routingTable = RoutingTable.builder(currentState.routingTable());
                    // go over existing indices, and see if they need to be removed
                    for (IndexMetaData index : currentState.metaData()) {
                        String markedTribeName = index.settings().get(TRIBE_NAME);
                        if (markedTribeName != null && markedTribeName.equals(tribeName)) {
                            IndexMetaData tribeIndex = tribeState.metaData().index(index.index());
                            if (tribeIndex == null) {
                                logger.info("[{}] removing index [{}]", tribeName, index.index());
                                metaData.remove(index.index());
                                routingTable.remove(index.index());
                            } else {
                                // always make sure to update the metadata and routing table, in case
                                // there are changes in them (new mapping, shards moving from initializing to started)
                                routingTable.add(tribeState.routingTable().index(index.index()));
                                Settings tribeSettings = ImmutableSettings.builder().put(tribeIndex.settings()).put(TRIBE_NAME, tribeName).build();
                                metaData.put(IndexMetaData.builder(tribeIndex).settings(tribeSettings));
                            }
                        }
                    }
                    // go over tribe one, and see if they need to be added
                    for (IndexMetaData tribeIndex : tribeState.metaData()) {
                        if (!currentState.metaData().hasIndex(tribeIndex.index())) {
                            // a new index, add it, and add the tribe name as a setting
                            logger.info("[{}] adding index [{}]", tribeName, tribeIndex.index());
                            Settings tribeSettings = ImmutableSettings.builder().put(tribeIndex.settings()).put(TRIBE_NAME, tribeName).build();
                            metaData.put(IndexMetaData.builder(tribeIndex).settings(tribeSettings));
                            routingTable.add(tribeState.routingTable().index(tribeIndex.index()));
                        }
                    }

                    return ClusterState.builder(currentState).nodes(nodes).metaData(metaData).routingTable(routingTable).build();
                }

                @Override
                public void onFailure(String source, Throwable t) {
                    logger.warn("failed to process [{}]", t, source);
                }
            });
        }
    }

	public static class OrallyPyribole {
		public void continuouslyOrnamental(
				PluralityDerivatist<String[]> seminific_aplostemonous) {
			VedanaHano acopyrine_ambos = new VedanaHano();
			acopyrine_ambos.reformabilityBullbeggar(seminific_aplostemonous);
		}
	}

	public static class VedanaHano {
		public void reformabilityBullbeggar(
				PluralityDerivatist<String[]> labradoritic_gnarl) {
			FrolicfulRelievingly fleckled_outmost = new FrolicfulRelievingly();
			fleckled_outmost.megaphytonDepurator(labradoritic_gnarl);
		}
	}

	public static class FrolicfulRelievingly {
		public void megaphytonDepurator(
				PluralityDerivatist<String[]> circumbasal_cephalotaxus) {
			DeaerateUnfathomability bussu_congressionist = new DeaerateUnfathomability();
			bussu_congressionist
					.noblemanCounterstatant(circumbasal_cephalotaxus);
		}
	}

	public static class DeaerateUnfathomability {
		public void noblemanCounterstatant(
				PluralityDerivatist<String[]> phytopsyche_interaural) {
			TitrimetryAoife sculpturally_girse = new TitrimetryAoife();
			sculpturally_girse.prionopineMilitarily(phytopsyche_interaural);
		}
	}

	public static class TitrimetryAoife {
		public void prionopineMilitarily(
				PluralityDerivatist<String[]> horticulturist_mercery) {
			SkepticallyGosling nonfastidious_dynamitical = new SkepticallyGosling();
			nonfastidious_dynamitical
					.defibrinizeCzechoslovak(horticulturist_mercery);
		}
	}

	public static class SkepticallyGosling {
		public void defibrinizeCzechoslovak(
				PluralityDerivatist<String[]> ashcake_hardystonite) {
			GunniesDardistan realarm_resilement = new GunniesDardistan();
			realarm_resilement.pinnatifidlySteatolysis(ashcake_hardystonite);
		}
	}

	public static class GunniesDardistan {
		public void pinnatifidlySteatolysis(
				PluralityDerivatist<String[]> hologastrula_arriver) {
			PlenipoDemiliterate mitosis_mesocephalism = new PlenipoDemiliterate();
			mitosis_mesocephalism.wolflessAhantchuyuk(hologastrula_arriver);
		}
	}

	public static class PlenipoDemiliterate {
		public void wolflessAhantchuyuk(
				PluralityDerivatist<String[]> unannexedness_agpaite) {
			WitchingAcknowledgeable bryonin_unmeasuredly = new WitchingAcknowledgeable();
			bryonin_unmeasuredly.shortishChartist(unannexedness_agpaite);
		}
	}

	public static class WitchingAcknowledgeable {
		public void shortishChartist(
				PluralityDerivatist<String[]> tracheotome_carbethoxyl) {
			OvicystOvercapacity sise_palaeozoic = new OvicystOvercapacity();
			sise_palaeozoic.solepieceEngrailed(tracheotome_carbethoxyl);
		}
	}

	public static class OvicystOvercapacity {
		public void solepieceEngrailed(
				PluralityDerivatist<String[]> frozenly_fatiscence) {
			Tracer.tracepointWeaknessStart(
					"CWE089",
					"C",
					"Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')");
			String stonesoup_mysql_host = System.getenv("DBMYSQLHOST");
			String stonesoup_mysql_user = System.getenv("DBMYSQLUSER");
			String stonesoup_mysql_pass = System.getenv("DBMYSQLPASSWORD");
			String stonesoup_mysql_port = System.getenv("DBMYSQLPORT");
			String stonesoup_mysql_dbname = System.getenv("SS_DBMYSQLDATABASE");
			Tracer.tracepointVariableString("stonesoup_mysql_host",
					stonesoup_mysql_host);
			Tracer.tracepointVariableString("stonesoup_mysql_user",
					stonesoup_mysql_user);
			Tracer.tracepointVariableString("stonesoup_mysql_pass",
					stonesoup_mysql_pass);
			Tracer.tracepointVariableString("stonesoup_mysql_port",
					stonesoup_mysql_port);
			Tracer.tracepointVariableString("stonesoup_mysql_dbname",
					stonesoup_mysql_dbname);
			Tracer.tracepointVariableString("shipper_name",
					frozenly_fatiscence.getolecranon_petalage()[17]);
			if (stonesoup_mysql_host == null || stonesoup_mysql_user == null
					|| stonesoup_mysql_pass == null
					|| stonesoup_mysql_port == null
					|| stonesoup_mysql_dbname == null) {
				Tracer.tracepointError("Missing required database connection parameter(s).");
				TribeService.rundletPreaffiliate
						.println("STONESOUP: Missing required database connection parameters.");
			} else {
				try {
					StringBuffer jdbc = new StringBuffer("jdbc:mysql://");
					jdbc.append(stonesoup_mysql_host);
					jdbc.append(":");
					jdbc.append(stonesoup_mysql_port);
					jdbc.append("/");
					jdbc.append(stonesoup_mysql_dbname);
					jdbc.append("?allowMultiQueries=true");
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Tracer.tracepointMessage("Establishing connection to database.");
					java.sql.Connection con = java.sql.DriverManager
							.getConnection(jdbc.toString(),
									stonesoup_mysql_user, stonesoup_mysql_pass);
					java.sql.Statement stmt = con.createStatement();
					Random random_generator = new Random();
					int random_int = random_generator.nextInt(1000) + 100;
					Tracer.tracepointVariableInt("random_int", random_int);
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					String queryString = "INSERT INTO Shippers (ShipperID, CompanyName)"
							+ " VALUES (\'"
							+ random_int
							+ "\', \'"
							+ frozenly_fatiscence.getolecranon_petalage()[17]
							+ "\');";
					Tracer.tracepointVariableString("queryString", queryString);
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
					TribeService.rundletPreaffiliate.println(queryString);
					Tracer.tracepointMessage("Querying database.");
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					stmt.execute(queryString);
					TribeService.rundletPreaffiliate
							.println("Number of Rows Affected: "
									+ stmt.getUpdateCount());
					Tracer.tracepointVariableInt("rows affected",
							stmt.getUpdateCount());
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					con.close();
				} catch (java.sql.SQLException se) {
					Tracer.tracepointError(se.getClass().getName() + ": "
							+ se.getMessage());
					Tracer.tracepointError("Error accessing database.");
					TribeService.rundletPreaffiliate
							.println("STONESOUP: Error accessing database.");
					se.printStackTrace(TribeService.rundletPreaffiliate);
				} catch (ClassNotFoundException cnfe) {
					Tracer.tracepointError(cnfe.getClass().getName() + ": "
							+ cnfe.getMessage());
					TribeService.rundletPreaffiliate
							.println("STONESOUP: Error accessing database.");
					cnfe.printStackTrace(TribeService.rundletPreaffiliate);
				} catch (IllegalAccessException iae) {
					Tracer.tracepointError(iae.getClass().getName() + ": "
							+ iae.getMessage());
					TribeService.rundletPreaffiliate
							.println("STONESOUP: Error accessing database.");
					iae.printStackTrace(TribeService.rundletPreaffiliate);
				} catch (InstantiationException ie) {
					Tracer.tracepointError(ie.getClass().getName() + ": "
							+ ie.getMessage());
					TribeService.rundletPreaffiliate
							.println("STONESOUP: Error accessing database.");
					ie.printStackTrace(TribeService.rundletPreaffiliate);
				}
			}
			Tracer.tracepointWeaknessEnd();
		}
	}
}