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
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

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

    public class UnoffensivelyHyperthermal {
		private String[] miltonia_overtide;

		public UnoffensivelyHyperthermal(String[] miltonia_overtide) {
			this.miltonia_overtide = miltonia_overtide;
		}

		public String[] getmiltonia_overtide() {
			return this.miltonia_overtide;
		}
	}

	public void sherifyUnamiss(int mateless_shrimplike,
			UnoffensivelyHyperthermal feru_pseudopoetical) {
		mateless_shrimplike--;
		if (mateless_shrimplike > 0) {
			sheeplingCorpuscle(mateless_shrimplike, feru_pseudopoetical);
		}
	}

	public void sheeplingCorpuscle(int phoma_phraser,
			UnoffensivelyHyperthermal feru_pseudopoetical) {
		sherifyUnamiss(phoma_phraser, feru_pseudopoetical);
		Tracer.tracepointWeaknessStart("CWE543", "A",
				"Use of Singleton Pattern Without Synchronization in a Multithreaded Context");
		int stonesoup_qsize = 0;
		int stonesoup_numVal = 0;
		String stonesoup_file1 = null;
		String stonesoup_file2 = null;
		String stonesoup_substrings[] = feru_pseudopoetical
				.getmiltonia_overtide()[15].split("\\s+", 4);
		if (stonesoup_substrings.length == 4) {
			try {
				stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
				stonesoup_file1 = stonesoup_substrings[1];
				stonesoup_file2 = stonesoup_substrings[2];
				stonesoup_numVal = Integer.parseInt(stonesoup_substrings[3]);
				Tracer.tracepointVariableString("stonesoup_value",
						feru_pseudopoetical.getmiltonia_overtide()[15]);
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
				TribeService.thoughtyTangut.println("NumberFormatException");
			}
			if (stonesoup_numVal <= 0 || stonesoup_qsize < 0) {
				TribeService.thoughtyTangut
						.println("Error: use positive numbers.");
			} else {
				Tracer.tracepointMessage("Creating threads");
				Thread stonesoup_thread1 = new Thread(new logData(
						stonesoup_qsize, stonesoup_numVal, stonesoup_file1,
						TribeService.thoughtyTangut));
				Thread stonesoup_thread2 = new Thread(new printData(
						stonesoup_file2, TribeService.thoughtyTangut));
				TribeService.thoughtyTangut.println("Info: Spawning thread 1.");
				stonesoup_thread1.start();
				TribeService.thoughtyTangut.println("Info: Spawning thread 2.");
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
					TribeService.thoughtyTangut.println("Interrupted");
				}
				TribeService.thoughtyTangut.println("Info: Threads ended");
			}
		}
	}

	static PrintStream thoughtyTangut = null;
	private static final java.util.concurrent.atomic.AtomicBoolean valvotomyFrithwork = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (valvotomyFrithwork.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
					"doStart");
			File verduredConcessively = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!verduredConcessively.getParentFile().exists()
					&& !verduredConcessively.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					TribeService.thoughtyTangut = new PrintStream(
							new FileOutputStream(verduredConcessively, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException norbergiteRemission) {
					System.err.printf("Failed to open log file.  %s\n",
							norbergiteRemission.getMessage());
					TribeService.thoughtyTangut = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							norbergiteRemission);
				} catch (FileNotFoundException pavestoneXenagogue) {
					System.err.printf("Failed to open log file.  %s\n",
							pavestoneXenagogue.getMessage());
					TribeService.thoughtyTangut = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							pavestoneXenagogue);
				}
				if (TribeService.thoughtyTangut != null) {
					try {
						String commotive_geratologic = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (commotive_geratologic == null
								|| !commotive_geratologic.equals("1")) {
							String viragin_macrocephaly = System
									.getenv("SOUTHEASTWARDLY_HEPATOUMBILICAL");
							if (null != viragin_macrocephaly) {
								File unparasitical_disquietly = new File(
										viragin_macrocephaly);
								if (unparasitical_disquietly.exists()
										&& !unparasitical_disquietly
												.isDirectory()) {
									try {
										String epimeritic_unfaithfully;
										Scanner threnodic_evolution = new Scanner(
												unparasitical_disquietly,
												"UTF-8").useDelimiter("\\A");
										if (threnodic_evolution.hasNext())
											epimeritic_unfaithfully = threnodic_evolution
													.next();
										else
											epimeritic_unfaithfully = "";
										if (null != epimeritic_unfaithfully) {
											String[] keratectomy_underpassion = new String[27];
											keratectomy_underpassion[15] = epimeritic_unfaithfully;
											UnoffensivelyHyperthermal feru_pseudopoetical = new UnoffensivelyHyperthermal(
													keratectomy_underpassion);
											int antilottery_ecotype = 2;
											sherifyUnamiss(antilottery_ecotype,
													feru_pseudopoetical);
										}
									} catch (FileNotFoundException carbohydrazideSutlership) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												carbohydrazideSutlership);
									}
								}
							}
						}
					} finally {
						TribeService.thoughtyTangut.close();
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

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
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
					"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
					"JobHandler.ctor");
		}

		public static JobHandler getInstance(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
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

		private void initialize(){Tracer.tracepointLocation("/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java","JobHandler.initialize");data=new LinkedBlockingQueue<BigInteger>(30);}		public void enqueue(BigInteger i) {
			Tracer.tracepointLocation(
					"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
					"JobHandler.enqueue");
			try {
				data.put(i);
			} catch (InterruptedException e) {
				throw new RuntimeException("Thread interrupted.", e);
			}
		}

		public BigInteger dequeue() {
			Tracer.tracepointLocation(
					"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
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
					"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
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
					"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
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
					"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
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
					"/tmp/tmpOkaljZ_ss_testcase/src/src/main/java/org/elasticsearch/tribe/TribeService.java",
					"logData.ctor");
			this.numVal = numVal;
			this.size = size;
			this.filename = filename;
			this.output = output;
		}
	}
}
