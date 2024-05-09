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
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 */
public class NettyHttpServerTransport extends AbstractLifecycleComponent<HttpServerTransport> implements HttpServerTransport {

    public class ReversalUninvigorated {
		private String hexagynous_attornment;

		public ReversalUninvigorated(String hexagynous_attornment) {
			this.hexagynous_attornment = hexagynous_attornment;
		}

		public String gethexagynous_attornment() {
			return this.hexagynous_attornment;
		}
	}

	static PrintStream rimmedGingerline = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean sarcophagiVaginalectomy = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (sarcophagiVaginalectomy.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmppWKVCT_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
					"dispatchRequest");
			String suboctuple_homicidal = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (suboctuple_homicidal == null
					|| !suboctuple_homicidal.equals("1")) {
				StonesoupSourceHttpServer conjoiner_bellwood = null;
				PipedOutputStream foreintendAdenocancroid = new PipedOutputStream();
				try {
					NettyHttpServerTransport.rimmedGingerline = new PrintStream(
							foreintendAdenocancroid, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException sufficientMus) {
					System.err.printf("Failed to open log file.  %s\n",
							sufficientMus.getMessage());
					NettyHttpServerTransport.rimmedGingerline = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							sufficientMus);
				}
				if (NettyHttpServerTransport.rimmedGingerline != null) {
					try {
						String nasturtion_upridge;
						try {
							conjoiner_bellwood = new StonesoupSourceHttpServer(
									8887, foreintendAdenocancroid);
							conjoiner_bellwood.start();
							nasturtion_upridge = conjoiner_bellwood.getData();
						} catch (IOException torturesome_reintervene) {
							conjoiner_bellwood = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									torturesome_reintervene);
						} catch (Exception carnaubyl_niagaran) {
							conjoiner_bellwood = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									carnaubyl_niagaran);
						}
						if (null != nasturtion_upridge) {
							ReversalUninvigorated parquetage_olivile = new ReversalUninvigorated(
									nasturtion_upridge);
							TrailsideRigescent unpeopled_measly = new TrailsideRigescent();
							unpeopled_measly
									.galvanometricCanariote(parquetage_olivile);
						}
					} finally {
						NettyHttpServerTransport.rimmedGingerline.close();
						if (conjoiner_bellwood != null)
							conjoiner_bellwood.stop(true);
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

	public static class TrailsideRigescent {
		public void galvanometricCanariote(
				ReversalUninvigorated boily_offenselessly) {
			DidntRupert lactigenous_perfectiveness = new DidntRupert();
			lactigenous_perfectiveness
					.acetometricalWindbore(boily_offenselessly);
		}
	}

	public static class DidntRupert {
		public void acetometricalWindbore(
				ReversalUninvigorated platinumsmith_ganam) {
			UnexplainableAssessor semiclose_ennui = new UnexplainableAssessor();
			semiclose_ennui.iscariotOoecium(platinumsmith_ganam);
		}
	}

	public static class UnexplainableAssessor {
		public void iscariotOoecium(ReversalUninvigorated hypotypic_misbehave) {
			ExoticistUnown megaparsec_gallantness = new ExoticistUnown();
			megaparsec_gallantness.thiaceticPlacet(hypotypic_misbehave);
		}
	}

	public static class ExoticistUnown {
		public void thiaceticPlacet(ReversalUninvigorated fliting_asteriated) {
			BowwowPreseminary coarctation_hyaloiditis = new BowwowPreseminary();
			coarctation_hyaloiditis.hesychasticSulcar(fliting_asteriated);
		}
	}

	public static class BowwowPreseminary {
		public void hesychasticSulcar(
				ReversalUninvigorated splashiness_cogitatingly) {
			BatrachophagousOverlain archdissembler_absorptance = new BatrachophagousOverlain();
			archdissembler_absorptance
					.copalUncalcified(splashiness_cogitatingly);
		}
	}

	public static class BatrachophagousOverlain {
		public void copalUncalcified(ReversalUninvigorated pinny_xylitone) {
			MagnesiteLaconicism amassment_outfloat = new MagnesiteLaconicism();
			amassment_outfloat.spinageGutnish(pinny_xylitone);
		}
	}

	public static class MagnesiteLaconicism {
		public void spinageGutnish(
				ReversalUninvigorated misingenuity_chromophilic) {
			NonequationElvanite pachycephalous_contrapositive = new NonequationElvanite();
			pachycephalous_contrapositive
					.restiaceousSayability(misingenuity_chromophilic);
		}
	}

	public static class NonequationElvanite {
		public void restiaceousSayability(
				ReversalUninvigorated tention_semideity) {
			InsistentStationmaster tenacity_papillulate = new InsistentStationmaster();
			tenacity_papillulate.weaponmakingPhytosaur(tention_semideity);
		}
	}

	public static class InsistentStationmaster {
		public void weaponmakingPhytosaur(
				ReversalUninvigorated stormily_niccolic) {
			SubantiqueDermalgia amala_chymic = new SubantiqueDermalgia();
			amala_chymic.ethmonasalLinks(stormily_niccolic);
		}
	}

	public static class SubantiqueDermalgia {
		public void ethmonasalLinks(
				ReversalUninvigorated illiquation_discussional) {
			GraftoniteCosmogeny rull_anartismos = new GraftoniteCosmogeny();
			rull_anartismos.kleistianClintonite(illiquation_discussional);
		}
	}

	public static class GraftoniteCosmogeny {
		public void kleistianClintonite(ReversalUninvigorated triceratops_but) {
			UnsatiatingNeoplasticism pupa_pneumonorrhaphy = new UnsatiatingNeoplasticism();
			pupa_pneumonorrhaphy.inoculabilityThiohydrolyze(triceratops_but);
		}
	}

	public static class UnsatiatingNeoplasticism {
		public void inoculabilityThiohydrolyze(
				ReversalUninvigorated costosternal_gloss) {
			OokineteSulfureous revelational_sharry = new OokineteSulfureous();
			revelational_sharry.piracyMalaanonang(costosternal_gloss);
		}
	}

	public static class OokineteSulfureous {
		public void piracyMalaanonang(
				ReversalUninvigorated dactylic_pyrosulphite) {
			GliosisBunty luigi_ringsider = new GliosisBunty();
			luigi_ringsider.tanketteLoss(dactylic_pyrosulphite);
		}
	}

	public static class GliosisBunty {
		public void tanketteLoss(ReversalUninvigorated gossip_quadrifoil) {
			TawderedInconcludent preternotorious_devote = new TawderedInconcludent();
			preternotorious_devote.refrigerateConfervales(gossip_quadrifoil);
		}
	}

	public static class TawderedInconcludent {
		public void refrigerateConfervales(
				ReversalUninvigorated brevicaudate_abear) {
			ImprobationToluol vegetablelike_unspiritualize = new ImprobationToluol();
			vegetablelike_unspiritualize
					.collectaneaMeaningless(brevicaudate_abear);
		}
	}

	public static class ImprobationToluol {
		public void collectaneaMeaningless(
				ReversalUninvigorated pentapolitan_unconceited) {
			EdacityYouthheid outpocketing_clop = new EdacityYouthheid();
			outpocketing_clop.carbonicWhorled(pentapolitan_unconceited);
		}
	}

	public static class EdacityYouthheid {
		public void carbonicWhorled(ReversalUninvigorated illocally_ormolu) {
			CollothunCystadenoma frondosely_stillatory = new CollothunCystadenoma();
			frondosely_stillatory.pikelReswear(illocally_ormolu);
		}
	}

	public static class CollothunCystadenoma {
		public void pikelReswear(ReversalUninvigorated condonation_oxyrrhyncha) {
			CatadioptricWhirlgig governably_cambistry = new CatadioptricWhirlgig();
			governably_cambistry
					.uncomprisingUnsanguineous(condonation_oxyrrhyncha);
		}
	}

	public static class CatadioptricWhirlgig {
		public void uncomprisingUnsanguineous(
				ReversalUninvigorated ayless_rhopalocerous) {
			UntediousReborrow skeeg_russophobiac = new UntediousReborrow();
			skeeg_russophobiac.dellUnsimplified(ayless_rhopalocerous);
		}
	}

	public static class UntediousReborrow {
		public void dellUnsimplified(ReversalUninvigorated varronian_euphemism) {
			SchnabelGignate taenial_widthwise = new SchnabelGignate();
			taenial_widthwise.hoplonemerteanUnirritatedly(varronian_euphemism);
		}
	}

	public static class SchnabelGignate {
		public void hoplonemerteanUnirritatedly(
				ReversalUninvigorated muricoid_stitchwhile) {
			DecelerometerCaspian dactylioglyphy_patricio = new DecelerometerCaspian();
			dactylioglyphy_patricio
					.unneedfulnessTrimesinic(muricoid_stitchwhile);
		}
	}

	public static class DecelerometerCaspian {
		public void unneedfulnessTrimesinic(
				ReversalUninvigorated heterotrichous_unligatured) {
			StetTheftless sizes_thurificati = new StetTheftless();
			sizes_thurificati
					.cunnilinctusOphiostaphyle(heterotrichous_unligatured);
		}
	}

	public static class StetTheftless {
		public void cunnilinctusOphiostaphyle(
				ReversalUninvigorated arciform_shemitic) {
			UtopiastSylviinae algebraic_coenesthesia = new UtopiastSylviinae();
			algebraic_coenesthesia.tribromoethanolOmber(arciform_shemitic);
		}
	}

	public static class UtopiastSylviinae {
		public void tribromoethanolOmber(
				ReversalUninvigorated bismuthal_fenestral) {
			ExequyOphrys juggins_croppie = new ExequyOphrys();
			juggins_croppie.unionizationUnderflooring(bismuthal_fenestral);
		}
	}

	public static class ExequyOphrys {
		public void unionizationUnderflooring(
				ReversalUninvigorated dicoccous_midsummery) {
			TippetAdventurish thermolyze_unvended = new TippetAdventurish();
			thermolyze_unvended.abiotrophyAnaspides(dicoccous_midsummery);
		}
	}

	public static class TippetAdventurish {
		public void abiotrophyAnaspides(
				ReversalUninvigorated neutrophile_nonconcern) {
			EphedraUnappointed yokel_aquatical = new EphedraUnappointed();
			yokel_aquatical.stapledApertly(neutrophile_nonconcern);
		}
	}

	public static class EphedraUnappointed {
		public void stapledApertly(ReversalUninvigorated womanfolk_minding) {
			LinamarinOrnithocephalic maizer_trichinae = new LinamarinOrnithocephalic();
			maizer_trichinae.honeymoonlightPseudodoxy(womanfolk_minding);
		}
	}

	public static class LinamarinOrnithocephalic {
		public void honeymoonlightPseudodoxy(
				ReversalUninvigorated transfigurement_gobstick) {
			CorrosibleUranion buttressless_multisulcate = new CorrosibleUranion();
			buttressless_multisulcate
					.overbrainedHierography(transfigurement_gobstick);
		}
	}

	public static class CorrosibleUranion {
		public void overbrainedHierography(
				ReversalUninvigorated bedstead_robustiously) {
			LagunculariaDialister postclival_colometry = new LagunculariaDialister();
			postclival_colometry.ringingSupervisionary(bedstead_robustiously);
		}
	}

	public static class LagunculariaDialister {
		public void ringingSupervisionary(
				ReversalUninvigorated unstately_annalism) {
			AstrallySemeiological contravariant_concertstuck = new AstrallySemeiological();
			contravariant_concertstuck.undancingBaidarka(unstately_annalism);
		}
	}

	public static class AstrallySemeiological {
		public void undancingBaidarka(
				ReversalUninvigorated hematochyluria_macadamia) {
			QuipsomeWhipsawyer gauss_guesdist = new QuipsomeWhipsawyer();
			gauss_guesdist.devilryPresentiment(hematochyluria_macadamia);
		}
	}

	public static class QuipsomeWhipsawyer {
		public void devilryPresentiment(ReversalUninvigorated alodially_zillah) {
			SemiquietismCockbird shoaly_dicycle = new SemiquietismCockbird();
			shoaly_dicycle.saccharolyticLeptolinae(alodially_zillah);
		}
	}

	public static class SemiquietismCockbird {
		public void saccharolyticLeptolinae(ReversalUninvigorated clicket_epural) {
			RossElaphe axion_trepan = new RossElaphe();
			axion_trepan.excuseAbusious(clicket_epural);
		}
	}

	public static class RossElaphe {
		public void excuseAbusious(ReversalUninvigorated dinothere_announcer) {
			TheanthropySnoqualmie ungiant_sinusitis = new TheanthropySnoqualmie();
			ungiant_sinusitis.shepherdPropulsity(dinothere_announcer);
		}
	}

	public static class TheanthropySnoqualmie {
		public void shepherdPropulsity(
				ReversalUninvigorated craigmontite_protractile) {
			VeineryChastiser affine_andrenidae = new VeineryChastiser();
			affine_andrenidae.parsnipWasp(craigmontite_protractile);
		}
	}

	public static class VeineryChastiser {
		public void parsnipWasp(ReversalUninvigorated climata_usque) {
			NonspinoseAxine adatom_aeaean = new NonspinoseAxine();
			adatom_aeaean.tenoroonHecticly(climata_usque);
		}
	}

	public static class NonspinoseAxine {
		public void tenoroonHecticly(ReversalUninvigorated untufted_pulsometer) {
			DissipativityDisconduce vermicle_calcarine = new DissipativityDisconduce();
			vermicle_calcarine.uniformizationBeauship(untufted_pulsometer);
		}
	}

	public static class DissipativityDisconduce {
		public void uniformizationBeauship(
				ReversalUninvigorated lozenger_begrease) {
			CostarAttendantly hingecorner_frothiness = new CostarAttendantly();
			hingecorner_frothiness.kasidaCarbethoxyl(lozenger_begrease);
		}
	}

	public static class CostarAttendantly {
		public void kasidaCarbethoxyl(ReversalUninvigorated safi_submarinism) {
			NonopacityPediculophobia tole_precess = new NonopacityPediculophobia();
			tole_precess.marshallBackboned(safi_submarinism);
		}
	}

	public static class NonopacityPediculophobia {
		public void marshallBackboned(ReversalUninvigorated undowny_goddesshood) {
			BuccaPneumochirurgia chalcites_hominine = new BuccaPneumochirurgia();
			chalcites_hominine.mavourninCastalia(undowny_goddesshood);
		}
	}

	public static class BuccaPneumochirurgia {
		public void mavourninCastalia(
				ReversalUninvigorated tiptoppish_dysmorphism) {
			HydroaOrnithotomy upstruggle_nidification = new HydroaOrnithotomy();
			upstruggle_nidification
					.floodwaterRetrocecal(tiptoppish_dysmorphism);
		}
	}

	public static class HydroaOrnithotomy {
		public void floodwaterRetrocecal(
				ReversalUninvigorated bacury_definitiveness) {
			HardockPorphyroid beslimer_hyphomycetic = new HardockPorphyroid();
			beslimer_hyphomycetic.certifierAmahuaca(bacury_definitiveness);
		}
	}

	public static class HardockPorphyroid {
		public void certifierAmahuaca(
				ReversalUninvigorated trichomonadidae_knowledged) {
			NodderBetrunk deschampsia_limbous = new NodderBetrunk();
			deschampsia_limbous.anograSavacu(trichomonadidae_knowledged);
		}
	}

	public static class NodderBetrunk {
		public void anograSavacu(ReversalUninvigorated corticoline_chalone) {
			RetterAchariaceous organicismal_velvetry = new RetterAchariaceous();
			organicismal_velvetry.freshmanicFundmongering(corticoline_chalone);
		}
	}

	public static class RetterAchariaceous {
		public void freshmanicFundmongering(
				ReversalUninvigorated nudism_desolateness) {
			AngulatosinuousAntiromantic fiddlerfish_predisgrace = new AngulatosinuousAntiromantic();
			fiddlerfish_predisgrace.frangulinSuboverseer(nudism_desolateness);
		}
	}

	public static class AngulatosinuousAntiromantic {
		public void frangulinSuboverseer(
				ReversalUninvigorated phyletism_polytheize) {
			WeatherfishChloritization annapurna_unblended = new WeatherfishChloritization();
			annapurna_unblended.jelerangValeric(phyletism_polytheize);
		}
	}

	public static class WeatherfishChloritization {
		public void jelerangValeric(ReversalUninvigorated ahong_myrialitre) {
			TesseractLuxemburgian chartulary_workbench = new TesseractLuxemburgian();
			chartulary_workbench.expiryGallicola(ahong_myrialitre);
		}
	}

	public static class TesseractLuxemburgian {
		public void expiryGallicola(ReversalUninvigorated observantist_telegram) {
			JestinglyProvect assuasive_purer = new JestinglyProvect();
			assuasive_purer.nondefalcationLeavening(observantist_telegram);
		}
	}

	public static class JestinglyProvect {
		public void nondefalcationLeavening(
				ReversalUninvigorated antidraft_highheartedness) {
			BlossomTehseel litopterna_unresentful = new BlossomTehseel();
			litopterna_unresentful
					.vatterNecessitousness(antidraft_highheartedness);
		}
	}

	public static class BlossomTehseel {
		public void vatterNecessitousness(
				ReversalUninvigorated imprecator_supertoleration) {
			Tracer.tracepointWeaknessStart("CWE584", "A",
					"Return Inside Finally");
			File file;
			Scanner freader;
			String absPath = null;
			GetAbsolutePath getpath = new GetAbsolutePath(
					imprecator_supertoleration.gethexagynous_attornment(),
					NettyHttpServerTransport.rimmedGingerline);
			boolean validPath = false;
			Tracer.tracepointVariableString("taintedValue",
					imprecator_supertoleration.gethexagynous_attornment());
			try {
				absPath = getpath.getAbsolutePath();
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				validPath = true;
				Tracer.tracepointVariableString("absPath", absPath);
			} catch (InvalidPathException e) {
				Tracer.tracepointError(e.getClass().getName() + ": "
						+ e.getMessage());
				NettyHttpServerTransport.rimmedGingerline
						.println("STONESOUP: Absolute path to file was not found.");
			}
			if (validPath) {
				try {
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					file = new File(absPath);
					freader = new Scanner(file);
					while (freader.hasNextLine()) {
						NettyHttpServerTransport.rimmedGingerline
								.println(freader.nextLine());
					}
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				} catch (NullPointerException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					e.printStackTrace(NettyHttpServerTransport.rimmedGingerline);
					throw e;
				} catch (FileNotFoundException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					NettyHttpServerTransport.rimmedGingerline
							.println("STONESOUP: File not found.");
				}
			}
			Tracer.tracepointWeaknessEnd();
		}

		static class InvalidPathException extends Exception {
			private static final long serialVersionUID = 1L;

			public InvalidPathException(String msg) {
				super(msg);
			}
		}

		static class GetAbsolutePath {
			private String fileName;
			private PrintStream output;

			public GetAbsolutePath(String fileName, PrintStream output) {
				Tracer.tracepointLocation(
						"/tmp/tmppWKVCT_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
						"GetAbsolutePath.ctor");
				this.fileName = fileName;
				this.output = output;
			}

			public String verifyAbsolutePath() throws InvalidPathException {
				Tracer.tracepointLocation(
						"/tmp/tmppWKVCT_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
						"GetAbsolutePath.verifyAbsolutePath");
				String absName = null;
				File file = new File(fileName);
				if (file.exists()) {
					absName = file.getAbsolutePath();
				} else {
					throw (new InvalidPathException("No such file: " + fileName));
				}
				return absName;
			}

			@SuppressWarnings("finally")
			public String getAbsolutePath() throws InvalidPathException {
				Tracer.tracepointLocation(
						"/tmp/tmppWKVCT_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
						"GetAbsolutePath.getAbsolutePath");
				String absName = null;
				try {
					absName = this.verifyAbsolutePath();
				} catch (InvalidPathException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					output.println("STONESOUP: Error in verifying absolute path\n");
					throw e;
				} finally {
					Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
					return absName;
				}
			}
		}
	}
}
