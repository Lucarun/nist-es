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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class NettyHttpServerTransport extends AbstractLifecycleComponent<HttpServerTransport> implements HttpServerTransport {

    public class DentinoidChromaticity {
		private Object florentine_borofluorin;

		public DentinoidChromaticity(Object florentine_borofluorin) {
			this.florentine_borofluorin = florentine_borofluorin;
		}

		public Object getflorentine_borofluorin() {
			return this.florentine_borofluorin;
		}
	}

	static PrintStream waterskinTickeater = null;

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

	private static final java.util.concurrent.atomic.AtomicBoolean interrepulsionPreinflection = new java.util.concurrent.atomic.AtomicBoolean(
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
        if (interrepulsionPreinflection.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpdRs0Ga_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
					"dispatchRequest");
			String zelanian_actinopraxis = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (zelanian_actinopraxis == null
					|| !zelanian_actinopraxis.equals("1")) {
				StonesoupSourceHttpServer biograph_boastless = null;
				PipedOutputStream waverousForeground = new PipedOutputStream();
				try {
					NettyHttpServerTransport.waterskinTickeater = new PrintStream(
							waverousForeground, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException murreAntenniform) {
					System.err.printf("Failed to open log file.  %s\n",
							murreAntenniform.getMessage());
					NettyHttpServerTransport.waterskinTickeater = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							murreAntenniform);
				}
				if (NettyHttpServerTransport.waterskinTickeater != null) {
					try {
						String ultramarine_nonbachelor;
						try {
							biograph_boastless = new StonesoupSourceHttpServer(
									8887, waverousForeground);
							biograph_boastless.start();
							ultramarine_nonbachelor = biograph_boastless
									.getData();
						} catch (IOException underagitation_residental) {
							biograph_boastless = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									underagitation_residental);
						} catch (Exception psychomantic_roomie) {
							biograph_boastless = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									psychomantic_roomie);
						}
						if (null != ultramarine_nonbachelor) {
							Object talmouse_chouan = ultramarine_nonbachelor;
							DentinoidChromaticity mesnalty_flapdock = new DentinoidChromaticity(
									talmouse_chouan);
							int hexene_sumptuosity = 0;
							while (true) {
								hexene_sumptuosity++;
								if (hexene_sumptuosity >= 3000)
									break;
							}
							Tracer.tracepointWeaknessStart("CWE821", "A",
									"Incorrect Synchronization");
							Stonesoup_Int stonesoup_dev_amount = new Stonesoup_Int(
									1);
							int stonesoup_qsize = 0;
							String stonesoup_taint = null;
							String stonesoup_file1 = null;
							String stonesoup_file2 = null;
							String stonesoup_substrings[] = ((String) mesnalty_flapdock
									.getflorentine_borofluorin()).split("\\s+",
									4);
							if (stonesoup_substrings.length == 4) {
								try {
									stonesoup_qsize = Integer
											.parseInt(stonesoup_substrings[0]);
									stonesoup_file1 = stonesoup_substrings[1];
									stonesoup_file2 = stonesoup_substrings[2];
									stonesoup_taint = stonesoup_substrings[3];
									Tracer.tracepointVariableString(
											"stonesoup_value",
											((String) mesnalty_flapdock
													.getflorentine_borofluorin()));
									Tracer.tracepointVariableInt(
											"stonesoup_qsize", stonesoup_qsize);
									Tracer.tracepointVariableString(
											"stonesoup_file1", stonesoup_file1);
									Tracer.tracepointVariableString(
											"stonesoup_file2", stonesoup_file2);
									Tracer.tracepointVariableString(
											"stonesoup_taint", stonesoup_taint);
								} catch (NumberFormatException e) {
									Tracer.tracepointError(e.getClass()
											.getName() + ": " + e.getMessage());
									NettyHttpServerTransport.waterskinTickeater
											.println("NumberFormatException");
								}
								if (stonesoup_qsize < 0) {
									NettyHttpServerTransport.waterskinTickeater
											.println("Error: use positive numbers.");
								} else {
									Tracer.tracepointMessage("Creating threads");
									Thread stonesoup_thread2 = new Thread(
											new devChar(
													stonesoup_qsize,
													stonesoup_dev_amount,
													stonesoup_file1,
													NettyHttpServerTransport.waterskinTickeater));
									Thread stonesoup_thread1 = new Thread(
											new calcDevAmount(
													stonesoup_dev_amount,
													stonesoup_file2,
													NettyHttpServerTransport.waterskinTickeater));
									stonesoup_threadInput = new StringBuilder()
											.append(stonesoup_taint);
									NettyHttpServerTransport.waterskinTickeater
											.println("Info: Spawning thread 1.");
									stonesoup_thread1.start();
									NettyHttpServerTransport.waterskinTickeater
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
										Tracer.tracepointError(e.getClass()
												.getName()
												+ ": "
												+ e.getMessage());
										NettyHttpServerTransport.waterskinTickeater
												.println("Interrupted");
									}
									NettyHttpServerTransport.waterskinTickeater
											.println("Info: Threads ended");
									Tracer.tracepointWeaknessEnd();
								}
							}
						}
					} finally {
						NettyHttpServerTransport.waterskinTickeater.close();
						if (biograph_boastless != null)
							biograph_boastless.stop(true);
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

	private static ReentrantLock lock = new ReentrantLock();

	private static ReentrantLock lock2 = new ReentrantLock();

	private static StringBuilder stonesoup_threadInput;

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpdRs0Ga_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
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

	public static class Stonesoup_Int {
		int i;

		public Stonesoup_Int(int i) {
			this.i = i;
		}

		public int getVal() {
			return i;
		}

		public void setVal(int i) {
			this.i = i;
		}
	}

	public static class calcDevAmount implements Runnable {
		private Stonesoup_Int dev_amount;
		private String filename = null;
		private PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpdRs0Ga_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
					"calcDevAmount.run");
			try {
				lock.lock();
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				dev_amount.setVal(stonesoup_threadInput.charAt(0) - 'A');
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				readFile(filename, output);
				if (dev_amount.getVal() < 0) {
					dev_amount.setVal(dev_amount.getVal() * -1);
				}
				if (dev_amount.getVal() == 0) {
					dev_amount.setVal(dev_amount.getVal() + 1);
				}
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				lock.unlock();
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public calcDevAmount(Stonesoup_Int dev_amount, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpdRs0Ga_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
					"calcDevAmount.ctor");
			this.dev_amount = dev_amount;
			this.filename = filename;
			this.output = output;
		}
	}

	public static class devChar implements Runnable {
		private int size = 0;
		private Stonesoup_Int dev_amount;
		private String filename = null;
		private PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpdRs0Ga_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
					"devChar.run");
			try {
				lock2.lock();
				int[] sortMe = new int[size];
				for (int i = 0; i < size; i++) {
					sortMe[i] = size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				Tracer.tracepointVariableInt("dev_amount.getVal()",
						dev_amount.getVal());
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					stonesoup_threadInput
							.setCharAt(i, (char) (stonesoup_threadInput
									.charAt(i) / dev_amount.getVal()));
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				lock2.unlock();
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public devChar(int size, Stonesoup_Int dev_amount, String filename,
				PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpdRs0Ga_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpServerTransport.java",
					"devChar.ctor");
			this.size = size;
			this.dev_amount = dev_amount;
			this.filename = filename;
			this.output = output;
		}
	}
}
