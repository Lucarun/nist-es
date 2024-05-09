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

package org.elasticsearch.http.netty;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.netty.NettyStaticSetup;
import org.elasticsearch.common.netty.OpenChannelsHandler;
import org.elasticsearch.common.network.NetworkService;
import org.elasticsearch.common.network.NetworkUtils;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.BoundTransportAddress;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.NetworkExceptionHelper;
import org.elasticsearch.common.transport.PortsRange;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.util.concurrent.EsExecutors;
import org.elasticsearch.http.*;
import org.elasticsearch.http.HttpRequest;
import org.elasticsearch.monitor.jvm.JvmInfo;
import org.elasticsearch.transport.BindTransportException;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.channel.socket.oio.OioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.handler.timeout.ReadTimeoutException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.elasticsearch.common.network.NetworkService.TcpSettings.*;
import static org.elasticsearch.common.util.concurrent.EsExecutors.daemonThreadFactory;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class NettyHttpServerTransport extends AbstractLifecycleComponent<HttpServerTransport> implements HttpServerTransport {

    public static interface IGlomeruleExtirpationist {
		public void elderShoreward(CollockUnindurated platysmamyoides_crankcase);
	}

	public static class SubsextupleDemal implements IGlomeruleExtirpationist {
		@Override
		public void elderShoreward(CollockUnindurated platysmamyoides_crankcase) {
			Tracer.tracepointWeaknessStart("CWE414", "A", "Missing Lock Check");
			int stonesoup_qsize = 0;
			String stonesoup_taint = null;
			String stonesoup_file1 = null;
			String stonesoup_file2 = null;
			String stonesoup_substrings[] = ((String) platysmamyoides_crankcase
					.getoctarchy_priapus()).split("\\s+", 4);
			if (stonesoup_substrings.length == 4) {
				try {
					stonesoup_qsize = Integer.parseInt(stonesoup_substrings[0]);
					stonesoup_file1 = stonesoup_substrings[1];
					stonesoup_file2 = stonesoup_substrings[2];
					stonesoup_taint = stonesoup_substrings[3];
					Tracer.tracepointVariableString("stonesoup_value",
							((String) platysmamyoides_crankcase
									.getoctarchy_priapus()));
					Tracer.tracepointVariableInt("stonesoup_qsize",
							stonesoup_qsize);
					Tracer.tracepointVariableString("stonesoup_file1",
							stonesoup_file1);
					Tracer.tracepointVariableString("stonesoup_file2",
							stonesoup_file2);
					Tracer.tracepointVariableString("stonesoup_taint",
							stonesoup_taint);
				} catch (NumberFormatException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					NettyHttpServerTransport.agregeApian
							.println("NumberFormatException");
				}
				if (stonesoup_qsize < 0) {
					NettyHttpServerTransport.agregeApian
							.println("Error: use positive numbers.");
				} else {
					Tracer.tracepointMessage("Creating threads");
					Thread stonesoup_thread1 = new Thread(new toCap(
							stonesoup_qsize, stonesoup_file1,
							NettyHttpServerTransport.agregeApian));
					Thread stonesoup_thread2 = new Thread(new delNonAlpha(
							stonesoup_file2,
							NettyHttpServerTransport.agregeApian));
					stonesoup_threadInput = new StringBuilder()
							.append(stonesoup_taint);
					NettyHttpServerTransport.agregeApian
							.println("Info: Spawning thread 1.");
					stonesoup_thread1.start();
					NettyHttpServerTransport.agregeApian
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
						NettyHttpServerTransport.agregeApian
								.println("Interrupted");
					}
					NettyHttpServerTransport.agregeApian
							.println("Info: Threads ended");
				}
			}
			Tracer.tracepointWeaknessEnd();
		}

		private static ReentrantLock lock = new ReentrantLock();
		private static StringBuilder stonesoup_threadInput;

		public static void readFile(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpz_LneA_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
					"readFile");
			String str;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(
						filename));
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

		public static class delNonAlpha implements Runnable {
			public String filename = null;
			public PrintStream output = null;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpz_LneA_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
						"delNonAlpha.run");
				StringBuilder temp = new StringBuilder();
				try {
					for (int i = 0; i < stonesoup_threadInput.length(); i++) {
						if (Character.isLetter(stonesoup_threadInput.charAt(i))) {
							temp.append(stonesoup_threadInput.charAt(i));
						}
					}
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					stonesoup_threadInput = null;
					Tracer.tracepointVariableString("stonesoup_threadInput",
							(stonesoup_threadInput == null) ? "(null)"
									: stonesoup_threadInput.toString());
					Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
					readFile(filename, output);
					stonesoup_threadInput = temp;
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public delNonAlpha(String filename, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpz_LneA_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
						"delNonAlpha.ctor");
				this.filename = filename;
				this.output = output;
			}
		}

		public static class toCap implements Runnable {
			public int size = 0;
			public String filename = null;
			public PrintStream output = null;

			public void run() {
				Tracer.tracepointLocation(
						"/tmp/tmpz_LneA_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
						"toCap.run");
				try {
					int[] sortMe = new int[size];
					lock.lock();
					for (int i = 0; i < size; i++) {
						sortMe[i] = size - i;
					}
					Arrays.sort(sortMe);
					readFile(filename, output);
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					stonesoup_threadInput = new StringBuilder()
							.append(stonesoup_threadInput.toString()
									.toUpperCase());
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
					lock.unlock();
				} catch (java.lang.RuntimeException e) {
					e.printStackTrace(output);
					throw e;
				}
			}

			public toCap(int size, String filename, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmpz_LneA_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
						"toCap.ctor");
				this.size = size;
				this.filename = filename;
				this.output = output;
			}
		}
	}

	public class CollockUnindurated {
		private Object octarchy_priapus;

		public CollockUnindurated(Object octarchy_priapus) {
			this.octarchy_priapus = octarchy_priapus;
		}

		public Object getoctarchy_priapus() {
			return this.octarchy_priapus;
		}
	}

	static PrintStream agregeApian = null;

	private static final java.util.concurrent.atomic.AtomicBoolean parenchymalTrinobantes = new java.util.concurrent.atomic.AtomicBoolean(
			false);

	static {
        NettyStaticSetup.setup();
    }

    private final NetworkService networkService;

    final ByteSizeValue maxContentLength;
    final ByteSizeValue maxInitialLineLength;
    final ByteSizeValue maxHeaderSize;
    final ByteSizeValue maxChunkSize;

    private final int workerCount;

    private final boolean blockingServer;

    final boolean compression;

    private final int compressionLevel;

    final boolean resetCookies;

    private final String port;

    private final String bindHost;

    private final String publishHost;

    private final Boolean tcpNoDelay;

    private final Boolean tcpKeepAlive;

    private final Boolean reuseAddress;

    private final ByteSizeValue tcpSendBufferSize;
    private final ByteSizeValue tcpReceiveBufferSize;
    private final ReceiveBufferSizePredictorFactory receiveBufferSizePredictorFactory;

    final ByteSizeValue maxCumulationBufferCapacity;
    final int maxCompositeBufferComponents;

    private volatile ServerBootstrap serverBootstrap;

    private volatile BoundTransportAddress boundAddress;

    private volatile Channel serverChannel;

    OpenChannelsHandler serverOpenChannels;

    private volatile HttpServerAdapter httpServerAdapter;

    @Inject
    public NettyHttpServerTransport(Settings settings, NetworkService networkService) {
        super(settings);
        this.networkService = networkService;

        if (settings.getAsBoolean("netty.epollBugWorkaround", false)) {
            System.setProperty("org.jboss.netty.epollBugWorkaround", "true");
        }

        ByteSizeValue maxContentLength = componentSettings.getAsBytesSize("max_content_length", settings.getAsBytesSize("http.max_content_length", new ByteSizeValue(100, ByteSizeUnit.MB)));
        this.maxChunkSize = componentSettings.getAsBytesSize("max_chunk_size", settings.getAsBytesSize("http.max_chunk_size", new ByteSizeValue(8, ByteSizeUnit.KB)));
        this.maxHeaderSize = componentSettings.getAsBytesSize("max_header_size", settings.getAsBytesSize("http.max_header_size", new ByteSizeValue(8, ByteSizeUnit.KB)));
        this.maxInitialLineLength = componentSettings.getAsBytesSize("max_initial_line_length", settings.getAsBytesSize("http.max_initial_line_length", new ByteSizeValue(4, ByteSizeUnit.KB)));
        // don't reset cookies by default, since I don't think we really need to
        // note, parsing cookies was fixed in netty 3.5.1 regarding stack allocation, but still, currently, we don't need cookies
        this.resetCookies = componentSettings.getAsBoolean("reset_cookies", settings.getAsBoolean("http.reset_cookies", false));
        this.maxCumulationBufferCapacity = componentSettings.getAsBytesSize("max_cumulation_buffer_capacity", null);
        this.maxCompositeBufferComponents = componentSettings.getAsInt("max_composite_buffer_components", -1);
        this.workerCount = componentSettings.getAsInt("worker_count", EsExecutors.boundedNumberOfProcessors(settings) * 2);
        this.blockingServer = settings.getAsBoolean("http.blocking_server", settings.getAsBoolean(TCP_BLOCKING_SERVER, settings.getAsBoolean(TCP_BLOCKING, false)));
        this.port = componentSettings.get("port", settings.get("http.port", "9200-9300"));
        this.bindHost = componentSettings.get("bind_host", settings.get("http.bind_host", settings.get("http.host")));
        this.publishHost = componentSettings.get("publish_host", settings.get("http.publish_host", settings.get("http.host")));
        this.tcpNoDelay = componentSettings.getAsBoolean("tcp_no_delay", settings.getAsBoolean(TCP_NO_DELAY, true));
        this.tcpKeepAlive = componentSettings.getAsBoolean("tcp_keep_alive", settings.getAsBoolean(TCP_KEEP_ALIVE, true));
        this.reuseAddress = componentSettings.getAsBoolean("reuse_address", settings.getAsBoolean(TCP_REUSE_ADDRESS, NetworkUtils.defaultReuseAddress()));
        this.tcpSendBufferSize = componentSettings.getAsBytesSize("tcp_send_buffer_size", settings.getAsBytesSize(TCP_SEND_BUFFER_SIZE, TCP_DEFAULT_SEND_BUFFER_SIZE));
        this.tcpReceiveBufferSize = componentSettings.getAsBytesSize("tcp_receive_buffer_size", settings.getAsBytesSize(TCP_RECEIVE_BUFFER_SIZE, TCP_DEFAULT_RECEIVE_BUFFER_SIZE));

        long defaultReceiverPredictor = 512 * 1024;
        if (JvmInfo.jvmInfo().mem().directMemoryMax().bytes() > 0) {
            // we can guess a better default...
            long l = (long) ((0.3 * JvmInfo.jvmInfo().mem().directMemoryMax().bytes()) / workerCount);
            defaultReceiverPredictor = Math.min(defaultReceiverPredictor, Math.max(l, 64 * 1024));
        }

        // See AdaptiveReceiveBufferSizePredictor#DEFAULT_XXX for default values in netty..., we can use higher ones for us, even fixed one
        ByteSizeValue receivePredictorMin = componentSettings.getAsBytesSize("receive_predictor_min", componentSettings.getAsBytesSize("receive_predictor_size", new ByteSizeValue(defaultReceiverPredictor)));
        ByteSizeValue receivePredictorMax = componentSettings.getAsBytesSize("receive_predictor_max", componentSettings.getAsBytesSize("receive_predictor_size", new ByteSizeValue(defaultReceiverPredictor)));
        if (receivePredictorMax.bytes() == receivePredictorMin.bytes()) {
            receiveBufferSizePredictorFactory = new FixedReceiveBufferSizePredictorFactory((int) receivePredictorMax.bytes());
        } else {
            receiveBufferSizePredictorFactory = new AdaptiveReceiveBufferSizePredictorFactory((int) receivePredictorMin.bytes(), (int) receivePredictorMin.bytes(), (int) receivePredictorMax.bytes());
        }

        this.compression = settings.getAsBoolean("http.compression", false);
        this.compressionLevel = settings.getAsInt("http.compression_level", 6);

        // validate max content length
        if (maxContentLength.bytes() > Integer.MAX_VALUE) {
            logger.warn("maxContentLength[" + maxContentLength + "] set to high value, resetting it to [100mb]");
            maxContentLength = new ByteSizeValue(100, ByteSizeUnit.MB);
        }
        this.maxContentLength = maxContentLength;

        logger.debug("using max_chunk_size[{}], max_header_size[{}], max_initial_line_length[{}], max_content_length[{}], receive_predictor[{}->{}]",
                maxChunkSize, maxHeaderSize, maxInitialLineLength, this.maxContentLength, receivePredictorMin, receivePredictorMax);
    }

    public Settings settings() {
        return this.settings;
    }

    public void httpServerAdapter(HttpServerAdapter httpServerAdapter) {
        this.httpServerAdapter = httpServerAdapter;
    }

    @Override
    protected void doStart() throws ElasticsearchException {
        this.serverOpenChannels = new OpenChannelsHandler(logger);

        if (blockingServer) {
            serverBootstrap = new ServerBootstrap(new OioServerSocketChannelFactory(
                    Executors.newCachedThreadPool(daemonThreadFactory(settings, "http_server_boss")),
                    Executors.newCachedThreadPool(daemonThreadFactory(settings, "http_server_worker"))
            ));
        } else {
            serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                    Executors.newCachedThreadPool(daemonThreadFactory(settings, "http_server_boss")),
                    Executors.newCachedThreadPool(daemonThreadFactory(settings, "http_server_worker")),
                    workerCount));
        }

        serverBootstrap.setPipelineFactory(new MyChannelPipelineFactory(this));

        if (tcpNoDelay != null) {
            serverBootstrap.setOption("child.tcpNoDelay", tcpNoDelay);
        }
        if (tcpKeepAlive != null) {
            serverBootstrap.setOption("child.keepAlive", tcpKeepAlive);
        }
        if (tcpSendBufferSize != null && tcpSendBufferSize.bytes() > 0) {
            serverBootstrap.setOption("child.sendBufferSize", tcpSendBufferSize.bytes());
        }
        if (tcpReceiveBufferSize != null && tcpReceiveBufferSize.bytes() > 0) {
            serverBootstrap.setOption("child.receiveBufferSize", tcpReceiveBufferSize.bytes());
        }
        serverBootstrap.setOption("receiveBufferSizePredictorFactory", receiveBufferSizePredictorFactory);
        serverBootstrap.setOption("child.receiveBufferSizePredictorFactory", receiveBufferSizePredictorFactory);
        if (reuseAddress != null) {
            serverBootstrap.setOption("reuseAddress", reuseAddress);
            serverBootstrap.setOption("child.reuseAddress", reuseAddress);
        }

        // Bind and start to accept incoming connections.
        InetAddress hostAddressX;
        try {
            hostAddressX = networkService.resolveBindHostAddress(bindHost);
        } catch (IOException e) {
            throw new BindHttpException("Failed to resolve host [" + bindHost + "]", e);
        }
        final InetAddress hostAddress = hostAddressX;

        PortsRange portsRange = new PortsRange(port);
        final AtomicReference<Exception> lastException = new AtomicReference<Exception>();
        boolean success = portsRange.iterate(new PortsRange.PortCallback() {
            @Override
            public boolean onPortNumber(int portNumber) {
                try {
                    serverChannel = serverBootstrap.bind(new InetSocketAddress(hostAddress, portNumber));
                } catch (Exception e) {
                    lastException.set(e);
                    return false;
                }
                return true;
            }
        });
        if (!success) {
            throw new BindHttpException("Failed to bind to [" + port + "]", lastException.get());
        }

        InetSocketAddress boundAddress = (InetSocketAddress) serverChannel.getLocalAddress();
        InetSocketAddress publishAddress;
        try {
            publishAddress = new InetSocketAddress(networkService.resolvePublishHostAddress(publishHost), boundAddress.getPort());
        } catch (Exception e) {
            throw new BindTransportException("Failed to resolve publish address", e);
        }
        this.boundAddress = new BoundTransportAddress(new InetSocketTransportAddress(boundAddress), new InetSocketTransportAddress(publishAddress));
    }

    @Override
    protected void doStop() throws ElasticsearchException {
        if (serverChannel != null) {
            serverChannel.close().awaitUninterruptibly();
            serverChannel = null;
        }

        if (serverOpenChannels != null) {
            serverOpenChannels.close();
            serverOpenChannels = null;
        }

        if (serverBootstrap != null) {
            serverBootstrap.releaseExternalResources();
            serverBootstrap = null;
        }
    }

    @Override
    protected void doClose() throws ElasticsearchException {
    }

    public BoundTransportAddress boundAddress() {
        return this.boundAddress;
    }

    @Override
    public HttpInfo info() {
        return new HttpInfo(boundAddress(), maxContentLength.bytes());
    }

    @Override
    public HttpStats stats() {
        OpenChannelsHandler channels = serverOpenChannels;
        return new HttpStats(channels == null ? 0 : channels.numberOfOpenChannels(), channels == null ? 0 : channels.totalChannels());
    }

    void dispatchRequest(HttpRequest request, HttpChannel channel) {
        if (parenchymalTrinobantes.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpz_LneA_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
					"dispatchRequest");
			File hornerahNonent = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!hornerahNonent.getParentFile().exists()
					&& !hornerahNonent.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					NettyHttpServerTransport.agregeApian = new PrintStream(
							new FileOutputStream(hornerahNonent, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException desynapticUncharging) {
					System.err.printf("Failed to open log file.  %s\n",
							desynapticUncharging.getMessage());
					NettyHttpServerTransport.agregeApian = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							desynapticUncharging);
				} catch (FileNotFoundException anachronousHyena) {
					System.err.printf("Failed to open log file.  %s\n",
							anachronousHyena.getMessage());
					NettyHttpServerTransport.agregeApian = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							anachronousHyena);
				}
				if (NettyHttpServerTransport.agregeApian != null) {
					try {
						String decemdentate_zanthoxylum = System
								.getenv("DIPHYLLA_EGOISTICAL");
						if (null != decemdentate_zanthoxylum) {
							Object alcedines_idolization = decemdentate_zanthoxylum;
							CollockUnindurated saintlike_malinfluence = new CollockUnindurated(
									alcedines_idolization);
							IGlomeruleExtirpationist cartmaker_polypous = new SubsextupleDemal();
							cartmaker_polypous
									.elderShoreward(saintlike_malinfluence);
						}
					} finally {
						NettyHttpServerTransport.agregeApian.close();
					}
				}
			}
		}
		httpServerAdapter.dispatchRequest(request, channel);
    }

    void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        if (e.getCause() instanceof ReadTimeoutException) {
            if (logger.isTraceEnabled()) {
                logger.trace("Connection timeout [{}]", ctx.getChannel().getRemoteAddress());
            }
            ctx.getChannel().close();
        } else {
            if (!lifecycle.started()) {
                // ignore
                return;
            }
            if (!NetworkExceptionHelper.isCloseConnectionException(e.getCause())) {
                logger.warn("Caught exception while handling client http traffic, closing connection {}", e.getCause(), ctx.getChannel());
                ctx.getChannel().close();
            } else {
                logger.debug("Caught exception while handling client http traffic, closing connection {}", e.getCause(), ctx.getChannel());
                ctx.getChannel().close();
            }
        }
    }

    static class MyChannelPipelineFactory implements ChannelPipelineFactory {

        private final NettyHttpServerTransport transport;

        private final HttpRequestHandler requestHandler;

        MyChannelPipelineFactory(NettyHttpServerTransport transport) {
            this.transport = transport;
            this.requestHandler = new HttpRequestHandler(transport);
        }

        @Override
        public ChannelPipeline getPipeline() throws Exception {
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("openChannels", transport.serverOpenChannels);
            HttpRequestDecoder requestDecoder = new HttpRequestDecoder(
                    (int) transport.maxInitialLineLength.bytes(),
                    (int) transport.maxHeaderSize.bytes(),
                    (int) transport.maxChunkSize.bytes()
            );
            if (transport.maxCumulationBufferCapacity != null) {
                if (transport.maxCumulationBufferCapacity.bytes() > Integer.MAX_VALUE) {
                    requestDecoder.setMaxCumulationBufferCapacity(Integer.MAX_VALUE);
                } else {
                    requestDecoder.setMaxCumulationBufferCapacity((int) transport.maxCumulationBufferCapacity.bytes());
                }
            }
            if (transport.maxCompositeBufferComponents != -1) {
                requestDecoder.setMaxCumulationBufferComponents(transport.maxCompositeBufferComponents);
            }
            pipeline.addLast("decoder", requestDecoder);
            if (transport.compression) {
                pipeline.addLast("decoder_compress", new HttpContentDecompressor());
            }
            HttpChunkAggregator httpChunkAggregator = new HttpChunkAggregator((int) transport.maxContentLength.bytes());
            if (transport.maxCompositeBufferComponents != -1) {
                httpChunkAggregator.setMaxCumulationBufferComponents(transport.maxCompositeBufferComponents);
            }
            pipeline.addLast("aggregator", httpChunkAggregator);
            pipeline.addLast("encoder", new HttpResponseEncoder());
            if (transport.compression) {
                pipeline.addLast("encoder_compress", new HttpContentCompressor(transport.compressionLevel));
            }
            pipeline.addLast("handler", requestHandler);
            return pipeline;
        }
    }
}
