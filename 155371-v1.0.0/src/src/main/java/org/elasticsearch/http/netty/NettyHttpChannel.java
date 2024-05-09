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
import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;

/**
 *
 */
public class NettyHttpChannel implements HttpChannel {
    static PrintStream hyperalgesisAnticreeper = null;

	public void faunuleDormancy(int sympatholytic_harder,
			Object unopenedFanegada) {
		sympatholytic_harder--;
		if (sympatholytic_harder > 0) {
			antiphaseMuteness(sympatholytic_harder, unopenedFanegada);
		}
	}

	public void antiphaseMuteness(int craniologist_laputically,
			Object unopenedFanegada) {
		faunuleDormancy(craniologist_laputically, unopenedFanegada);
		Tracer.tracepointWeaknessStart("CWE191", "A",
				"Integer Underflow (Wrap or Wraparound)");
		short stonesoup_checked_value = ((Short) unopenedFanegada);
		Tracer.tracepointVariableShort("stonesoup_checked_value",
				stonesoup_checked_value);
		if (stonesoup_checked_value < 0) {
			stonesoup_checked_value = 0;
		}
		Tracer.tracepointVariableShort("stonesoup_checked_value",
				stonesoup_checked_value);
		Short[] stonesoup_some_values = new Short[] { 0, 1, 2, 3, 4, 5, 6, 7,
				8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
		short stonesoup_counter = -20;
		short stonesoup_offset = 40;
		Tracer.tracepointBufferInfo("stonesoup_some_values",
				stonesoup_some_values.length, "Length of stonesoup_some_values");
		Tracer.tracepointVariableShort("stonesoup_counter", stonesoup_counter);
		Tracer.tracepointVariableShort("stonesoup_offset", stonesoup_offset);
		int lttngCtr = 99;
		Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
		Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
		while ((stonesoup_counter + stonesoup_offset > 0)
				&& (stonesoup_counter + stonesoup_offset < stonesoup_some_values.length)) {
			NettyHttpChannel.hyperalgesisAnticreeper
					.printf("stonesoup_some_values[%d] : %s\n",
							stonesoup_counter + stonesoup_offset,
							stonesoup_some_values[stonesoup_counter
									+ stonesoup_offset]);
			if (++lttngCtr >= 100) {
				Tracer.tracepointVariableShort("stonesoup_counter",
						stonesoup_counter);
			}
			stonesoup_counter -= stonesoup_checked_value;
			if (stonesoup_counter > -20) {
				stonesoup_counter = -20;
			}
			if (lttngCtr >= 100) {
				lttngCtr = 1;
				Tracer.tracepointVariableShort("stonesoup_counter",
						stonesoup_counter);
			}
		}
		Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
		Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
		Tracer.tracepointBufferInfo("stonesoup_some_values",
				stonesoup_some_values.length, "Length of stonesoup_some_values");
		Tracer.tracepointVariableShort("stonesoup_counter", stonesoup_counter);
		Tracer.tracepointVariableShort("stonesoup_offset", stonesoup_offset);
		NettyHttpChannel.hyperalgesisAnticreeper.println("finished evaluating");
		Tracer.tracepointWeaknessEnd();
	}

	private static final java.util.concurrent.atomic.AtomicBoolean rebagRockies = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final NettyHttpServerTransport transport;
    private final Channel channel;
    private final org.jboss.netty.handler.codec.http.HttpRequest request;

    public NettyHttpChannel(NettyHttpServerTransport transport, Channel channel, org.jboss.netty.handler.codec.http.HttpRequest request) {
        if (rebagRockies.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmp5WjONa_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"NettyHttpChannel");
			File porteranthusDentinoblast = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!porteranthusDentinoblast.getParentFile().exists()
					&& !porteranthusDentinoblast.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					NettyHttpChannel.hyperalgesisAnticreeper = new PrintStream(
							new FileOutputStream(porteranthusDentinoblast,
									false), true, "ISO-8859-1");
				} catch (UnsupportedEncodingException congiusExcogitable) {
					System.err.printf("Failed to open log file.  %s\n",
							congiusExcogitable.getMessage());
					NettyHttpChannel.hyperalgesisAnticreeper = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							congiusExcogitable);
				} catch (FileNotFoundException bestrodeUnobstruct) {
					System.err.printf("Failed to open log file.  %s\n",
							bestrodeUnobstruct.getMessage());
					NettyHttpChannel.hyperalgesisAnticreeper = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							bestrodeUnobstruct);
				}
				if (NettyHttpChannel.hyperalgesisAnticreeper != null) {
					try {
						String fourflusher_dataria = System
								.getenv("INQUESTUAL_PROTREATY");
						if (null != fourflusher_dataria) {
							short sumass_dressily;
							try {
								sumass_dressily = Short
										.parseShort(fourflusher_dataria);
							} catch (NumberFormatException monstrousness_engraphia) {
								throw new RuntimeException(
										"STONESOUP: Failed to convert source taint.",
										monstrousness_engraphia);
							}
							Object underer_flintiness = sumass_dressily;
							extractibleArid(3, null, null, null,
									underer_flintiness, null, null);
						}
					} finally {
						NettyHttpChannel.hyperalgesisAnticreeper.close();
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

	public void extractibleArid(int nephropyosisRepatency,
			Object... emysNornicotine) {
		Object unopenedFanegada = null;
		int macrosporeEgoistical = 0;
		for (macrosporeEgoistical = 0; macrosporeEgoistical < emysNornicotine.length; macrosporeEgoistical++) {
			if (macrosporeEgoistical == nephropyosisRepatency)
				unopenedFanegada = emysNornicotine[macrosporeEgoistical];
		}
		int hemiparetic_axoneure = 2;
		faunuleDormancy(hemiparetic_axoneure, unopenedFanegada);
	}
}
