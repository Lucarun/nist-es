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

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.http.HttpChannel;
import org.elasticsearch.http.HttpException;
import org.elasticsearch.rest.RestResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.rest.XContentRestResponse;
import org.elasticsearch.rest.support.RestUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.pontetec.stonesoup.trace.Tracer;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import fi.iki.elonen.NanoHTTPD;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 */
public class NettyHttpChannel implements HttpChannel {
    static PrintStream hypokoristikonEnchantment = null;

	public void nonsocietySubsegment(int acinetinan_ribaudequin,
			final Object ascidicolous_pasquin) {
		acinetinan_ribaudequin--;
		if (acinetinan_ribaudequin > 0) {
			chauiJeddock(acinetinan_ribaudequin, ascidicolous_pasquin);
		}
	}

	public void chauiJeddock(int mobocratic_inaccessibly,final Object ascidicolous_pasquin){nonsocietySubsegment(mobocratic_inaccessibly,ascidicolous_pasquin);Tracer.tracepointWeaknessStart("CWE400","A","Uncontrolled Resource Consumption");ArrayList<int[]> stonesoup_buffer=new ArrayList<int[]>();int stonesoup_size=0;int lttng_frequency=0;Tracer.tracepointVariableInt("stonesoup_intValue",((Integer)ascidicolous_pasquin));if (((Integer)ascidicolous_pasquin) > 0 && ((Integer)ascidicolous_pasquin) <= Integer.MAX_VALUE){stonesoup_size=10000;Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");for (int i=0;i < ((Integer)ascidicolous_pasquin);){try {stonesoup_buffer.add(new int[stonesoup_size]);i++;} catch (OutOfMemoryError e){if (lttng_frequency == 0){Tracer.tracepointError(e.getClass().getName() + ": "+e.getMessage());Tracer.tracepointBufferInfo("stonesoup_buffer",stonesoup_buffer.size(),"Size of stonesoup_buffer");}lttng_frequency=(lttng_frequency == 199)?0:lttng_frequency + 1;}}Tracer.tracepointBufferInfo("stonesoup_buffer",stonesoup_buffer.size(),"Size of stonesoup_buffer");Tracer.tracepointMessage("TRIGGER-POINT: AFTER");Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");NettyHttpChannel.hypokoristikonEnchantment.println("Allocated all the memory requested");}Tracer.tracepointWeaknessEnd();}

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

	private static final java.util.concurrent.atomic.AtomicBoolean silphaMemorially = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final NettyHttpServerTransport transport;
    private final Channel channel;
    private final org.jboss.netty.handler.codec.http.HttpRequest request;

    public NettyHttpChannel(NettyHttpServerTransport transport, Channel channel, org.jboss.netty.handler.codec.http.HttpRequest request) {
        if (silphaMemorially.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpRyO_fY_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"NettyHttpChannel");
			String beastliness_untumultuous = System
					.getenv("STONESOUP_DISABLE_WEAKNESS");
			if (beastliness_untumultuous == null
					|| !beastliness_untumultuous.equals("1")) {
				StonesoupSourceHttpServer lutetia_gunshop = null;
				PipedOutputStream leeangleChiliahedron = new PipedOutputStream();
				try {
					NettyHttpChannel.hypokoristikonEnchantment = new PrintStream(
							leeangleChiliahedron, true, "ISO-8859-1");
				} catch (UnsupportedEncodingException zekeProministry) {
					System.err.printf("Failed to open log file.  %s\n",
							zekeProministry.getMessage());
					NettyHttpChannel.hypokoristikonEnchantment = null;
					throw new RuntimeException(
							"STONESOUP: Failed to create piped print stream.",
							zekeProministry);
				}
				if (NettyHttpChannel.hypokoristikonEnchantment != null) {
					try {
						final String promiseless_pancratism;
						try {
							lutetia_gunshop = new StonesoupSourceHttpServer(
									8887, leeangleChiliahedron);
							lutetia_gunshop.start();
							promiseless_pancratism = lutetia_gunshop.getData();
						} catch (IOException arnut_champacol) {
							lutetia_gunshop = null;
							throw new RuntimeException(
									"STONESOUP: Failed to start HTTP server.",
									arnut_champacol);
						} catch (Exception dyehouse_geotherm) {
							lutetia_gunshop = null;
							throw new RuntimeException(
									"STONESOUP: Unknown error with HTTP server.",
									dyehouse_geotherm);
						}
						if (null != promiseless_pancratism) {
							final int evolution_holoquinoidal;
							try {
								evolution_holoquinoidal = Integer
										.parseInt(promiseless_pancratism);
							} catch (NumberFormatException bawdily_wellness) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										bawdily_wellness);
							}
							final Object ascidicolous_pasquin = evolution_holoquinoidal;
							int cartomancy_seduce = 2;
							nonsocietySubsegment(cartomancy_seduce,
									ascidicolous_pasquin);
						}
					} finally {
						NettyHttpChannel.hypokoristikonEnchantment.close();
						if (lutetia_gunshop != null)
							lutetia_gunshop.stop(true);
					}
				}
			}
		}
		this.transport = transport;
        this.channel = channel;
        this.request = request;
    }

    @Override
    public void sendResponse(RestResponse response) {

        // Decide whether to close the connection or not.
        boolean http10 = request.getProtocolVersion().equals(HttpVersion.HTTP_1_0);
        boolean close =
                HttpHeaders.Values.CLOSE.equalsIgnoreCase(request.headers().get(HttpHeaders.Names.CONNECTION)) ||
                        (http10 && !HttpHeaders.Values.KEEP_ALIVE.equalsIgnoreCase(request.headers().get(HttpHeaders.Names.CONNECTION)));

        // Build the response object.
        HttpResponseStatus status = getStatus(response.status());
        org.jboss.netty.handler.codec.http.HttpResponse resp;
        if (http10) {
            resp = new DefaultHttpResponse(HttpVersion.HTTP_1_0, status);
            if (!close) {
                resp.headers().add(HttpHeaders.Names.CONNECTION, "Keep-Alive");
            }
        } else {
            resp = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
        }
        if (RestUtils.isBrowser(request.headers().get(HttpHeaders.Names.USER_AGENT))) {
            if (transport.settings().getAsBoolean("http.cors.enabled", true)) {
                // Add support for cross-origin Ajax requests (CORS)
                resp.headers().add("Access-Control-Allow-Origin", transport.settings().get("http.cors.allow-origin", "*"));
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    // Allow Ajax requests based on the CORS "preflight" request
                    resp.headers().add("Access-Control-Max-Age", transport.settings().getAsInt("http.cors.max-age", 1728000));
                    resp.headers().add("Access-Control-Allow-Methods", transport.settings().get("http.cors.allow-methods", "OPTIONS, HEAD, GET, POST, PUT, DELETE"));
                    resp.headers().add("Access-Control-Allow-Headers", transport.settings().get("http.cors.allow-headers", "X-Requested-With, Content-Type, Content-Length"));
                }
            }
        }

        String opaque = request.headers().get("X-Opaque-Id");
        if (opaque != null) {
            resp.headers().add("X-Opaque-Id", opaque);
        }

        // Add all custom headers
        Map<String, List<String>> customHeaders = response.getHeaders();
        if (customHeaders != null) {
            for (Map.Entry<String, List<String>> headerEntry : customHeaders.entrySet()) {
                for (String headerValue : headerEntry.getValue()) {
                    resp.headers().add(headerEntry.getKey(), headerValue);
                }
            }
        }

        // Convert the response content to a ChannelBuffer.
        ChannelBuffer buf;
        try {
            if (response instanceof XContentRestResponse) {
                // if its a builder based response, and it was created with a CachedStreamOutput, we can release it
                // after we write the response, and no need to do an extra copy because its not thread safe
                XContentBuilder builder = ((XContentRestResponse) response).builder();
                if (response.contentThreadSafe()) {
                    buf = builder.bytes().toChannelBuffer();
                } else {
                    buf = builder.bytes().copyBytesArray().toChannelBuffer();
                }
            } else {
                if (response.contentThreadSafe()) {
                    buf = ChannelBuffers.wrappedBuffer(response.content(), response.contentOffset(), response.contentLength());
                } else {
                    buf = ChannelBuffers.copiedBuffer(response.content(), response.contentOffset(), response.contentLength());
                }
            }
        } catch (IOException e) {
            throw new HttpException("Failed to convert response to bytes", e);
        }
        if (response.prefixContent() != null || response.suffixContent() != null) {
            ChannelBuffer prefixBuf = ChannelBuffers.EMPTY_BUFFER;
            if (response.prefixContent() != null) {
                prefixBuf = ChannelBuffers.copiedBuffer(response.prefixContent(), response.prefixContentOffset(), response.prefixContentLength());
            }
            ChannelBuffer suffixBuf = ChannelBuffers.EMPTY_BUFFER;
            if (response.suffixContent() != null) {
                suffixBuf = ChannelBuffers.copiedBuffer(response.suffixContent(), response.suffixContentOffset(), response.suffixContentLength());
            }
            buf = ChannelBuffers.wrappedBuffer(prefixBuf, buf, suffixBuf);
        }
        resp.setContent(buf);
        resp.headers().add(HttpHeaders.Names.CONTENT_TYPE, response.contentType());

        resp.headers().add(HttpHeaders.Names.CONTENT_LENGTH, String.valueOf(buf.readableBytes()));

        if (transport.resetCookies) {
            String cookieString = request.headers().get(HttpHeaders.Names.COOKIE);
            if (cookieString != null) {
                CookieDecoder cookieDecoder = new CookieDecoder();
                Set<Cookie> cookies = cookieDecoder.decode(cookieString);
                if (!cookies.isEmpty()) {
                    // Reset the cookies if necessary.
                    CookieEncoder cookieEncoder = new CookieEncoder(true);
                    for (Cookie cookie : cookies) {
                        cookieEncoder.addCookie(cookie);
                    }
                    resp.headers().add(HttpHeaders.Names.SET_COOKIE, cookieEncoder.encode());
                }
            }
        }

        // Write the response.
        ChannelFuture future = channel.write(resp);
        // Close the connection after the write operation is done if necessary.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private HttpResponseStatus getStatus(RestStatus status) {
        switch (status) {
            case CONTINUE:
                return HttpResponseStatus.CONTINUE;
            case SWITCHING_PROTOCOLS:
                return HttpResponseStatus.SWITCHING_PROTOCOLS;
            case OK:
                return HttpResponseStatus.OK;
            case CREATED:
                return HttpResponseStatus.CREATED;
            case ACCEPTED:
                return HttpResponseStatus.ACCEPTED;
            case NON_AUTHORITATIVE_INFORMATION:
                return HttpResponseStatus.NON_AUTHORITATIVE_INFORMATION;
            case NO_CONTENT:
                return HttpResponseStatus.NO_CONTENT;
            case RESET_CONTENT:
                return HttpResponseStatus.RESET_CONTENT;
            case PARTIAL_CONTENT:
                return HttpResponseStatus.PARTIAL_CONTENT;
            case MULTI_STATUS:
                // no status for this??
                return HttpResponseStatus.INTERNAL_SERVER_ERROR;
            case MULTIPLE_CHOICES:
                return HttpResponseStatus.MULTIPLE_CHOICES;
            case MOVED_PERMANENTLY:
                return HttpResponseStatus.MOVED_PERMANENTLY;
            case FOUND:
                return HttpResponseStatus.FOUND;
            case SEE_OTHER:
                return HttpResponseStatus.SEE_OTHER;
            case NOT_MODIFIED:
                return HttpResponseStatus.NOT_MODIFIED;
            case USE_PROXY:
                return HttpResponseStatus.USE_PROXY;
            case TEMPORARY_REDIRECT:
                return HttpResponseStatus.TEMPORARY_REDIRECT;
            case BAD_REQUEST:
                return HttpResponseStatus.BAD_REQUEST;
            case UNAUTHORIZED:
                return HttpResponseStatus.UNAUTHORIZED;
            case PAYMENT_REQUIRED:
                return HttpResponseStatus.PAYMENT_REQUIRED;
            case FORBIDDEN:
                return HttpResponseStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpResponseStatus.NOT_FOUND;
            case METHOD_NOT_ALLOWED:
                return HttpResponseStatus.METHOD_NOT_ALLOWED;
            case NOT_ACCEPTABLE:
                return HttpResponseStatus.NOT_ACCEPTABLE;
            case PROXY_AUTHENTICATION:
                return HttpResponseStatus.PROXY_AUTHENTICATION_REQUIRED;
            case REQUEST_TIMEOUT:
                return HttpResponseStatus.REQUEST_TIMEOUT;
            case CONFLICT:
                return HttpResponseStatus.CONFLICT;
            case GONE:
                return HttpResponseStatus.GONE;
            case LENGTH_REQUIRED:
                return HttpResponseStatus.LENGTH_REQUIRED;
            case PRECONDITION_FAILED:
                return HttpResponseStatus.PRECONDITION_FAILED;
            case REQUEST_ENTITY_TOO_LARGE:
                return HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE;
            case REQUEST_URI_TOO_LONG:
                return HttpResponseStatus.REQUEST_URI_TOO_LONG;
            case UNSUPPORTED_MEDIA_TYPE:
                return HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE;
            case REQUESTED_RANGE_NOT_SATISFIED:
                return HttpResponseStatus.REQUESTED_RANGE_NOT_SATISFIABLE;
            case EXPECTATION_FAILED:
                return HttpResponseStatus.EXPECTATION_FAILED;
            case UNPROCESSABLE_ENTITY:
                return HttpResponseStatus.BAD_REQUEST;
            case LOCKED:
                return HttpResponseStatus.BAD_REQUEST;
            case FAILED_DEPENDENCY:
                return HttpResponseStatus.BAD_REQUEST;
            case INTERNAL_SERVER_ERROR:
                return HttpResponseStatus.INTERNAL_SERVER_ERROR;
            case NOT_IMPLEMENTED:
                return HttpResponseStatus.NOT_IMPLEMENTED;
            case BAD_GATEWAY:
                return HttpResponseStatus.BAD_GATEWAY;
            case SERVICE_UNAVAILABLE:
                return HttpResponseStatus.SERVICE_UNAVAILABLE;
            case GATEWAY_TIMEOUT:
                return HttpResponseStatus.GATEWAY_TIMEOUT;
            case HTTP_VERSION_NOT_SUPPORTED:
                return HttpResponseStatus.HTTP_VERSION_NOT_SUPPORTED;
            default:
                return HttpResponseStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
