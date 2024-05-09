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
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 */
public class NettyHttpChannel implements HttpChannel {
    public class DrainerTemnospondylous<T> {
		private T baikerite_becomingly;

		public DrainerTemnospondylous(T baikerite_becomingly) {
			this.baikerite_becomingly = baikerite_becomingly;
		}

		public T getbaikerite_becomingly() {
			return this.baikerite_becomingly;
		}
	}

	public void forebearCompsognathus(int jovicentrically_nosebanded,
			DrainerTemnospondylous<long[]> cephalalgy_stripy) {
		jovicentrically_nosebanded--;
		if (jovicentrically_nosebanded > 0) {
			snortBolograph(jovicentrically_nosebanded, cephalalgy_stripy);
		}
	}

	public void snortBolograph(int paspalum_ass,
			DrainerTemnospondylous<long[]> cephalalgy_stripy) {
		forebearCompsognathus(paspalum_ass, cephalalgy_stripy);
		Tracer.tracepointWeaknessStart("CWE197", "A", "Numeric Trucation Error");
		Tracer.tracepointVariableLong("value",
				cephalalgy_stripy.getbaikerite_becomingly()[16]);
		if (cephalalgy_stripy.getbaikerite_becomingly()[16] > 0) {
			Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
			int stonesoup_max_value = (int) ((long) cephalalgy_stripy
					.getbaikerite_becomingly()[16]);
			Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
			Tracer.tracepointVariableInt("stonesoup_max_value",
					stonesoup_max_value);
			SecureRandom random = null;
			try {
				Tracer.tracepointMessage("Creating PRNG.");
				random = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				NettyHttpChannel.medievalSubdrain
						.println("STONESOUP: Failed to create PRNG.");
				e.printStackTrace(NettyHttpChannel.medievalSubdrain);
			}
			if (random != null) {
				Tracer.tracepointMessage("Generating random variable between 0 and stonesoup_max_value");
				try {
					NettyHttpChannel.medievalSubdrain
							.printf("Generating random value between %d (inclusive) and %d (exclusive).\n",
									0, stonesoup_max_value);
					Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
					NettyHttpChannel.medievalSubdrain.printf(
							"Random choice: %d\n",
							random.nextInt(stonesoup_max_value));
					Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
				} catch (RuntimeException e) {
					Tracer.tracepointError(e.getClass().getName() + ": "
							+ e.getMessage());
					e.printStackTrace(NettyHttpChannel.medievalSubdrain);
					throw e;
				}
				Tracer.tracepointMessage("After random value generation.");
			}
		}
		Tracer.tracepointWeaknessEnd();
	}

	static PrintStream medievalSubdrain = null;
	private static final java.util.concurrent.atomic.AtomicBoolean laceflowerComplacently = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final NettyHttpServerTransport transport;
    private final Channel channel;
    private final org.jboss.netty.handler.codec.http.HttpRequest request;

    public NettyHttpChannel(NettyHttpServerTransport transport, Channel channel, org.jboss.netty.handler.codec.http.HttpRequest request) {
        if (laceflowerComplacently.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpVQnXpq_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"NettyHttpChannel");
			File ruffianoCoharmoniously = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!ruffianoCoharmoniously.getParentFile().exists()
					&& !ruffianoCoharmoniously.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					NettyHttpChannel.medievalSubdrain = new PrintStream(
							new FileOutputStream(ruffianoCoharmoniously, false),
							true, "ISO-8859-1");
				} catch (UnsupportedEncodingException canasterOutdoors) {
					System.err.printf("Failed to open log file.  %s\n",
							canasterOutdoors.getMessage());
					NettyHttpChannel.medievalSubdrain = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							canasterOutdoors);
				} catch (FileNotFoundException vedangaOrganogenist) {
					System.err.printf("Failed to open log file.  %s\n",
							vedangaOrganogenist.getMessage());
					NettyHttpChannel.medievalSubdrain = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							vedangaOrganogenist);
				}
				if (NettyHttpChannel.medievalSubdrain != null) {
					try {
						String minding_acanthocephali = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (minding_acanthocephali == null
								|| !minding_acanthocephali.equals("1")) {
							String slightish_phlebalgia = System
									.getenv("GYPSINE_UNINTERCEPTED");
							if (null != slightish_phlebalgia) {
								File peyotl_eurypelma = new File(
										slightish_phlebalgia);
								if (peyotl_eurypelma.exists()
										&& !peyotl_eurypelma.isDirectory()) {
									try {
										String cecidomyiidous_melitriose;
										Scanner puchero_lumberdom = new Scanner(
												peyotl_eurypelma, "UTF-8")
												.useDelimiter("\\A");
										if (puchero_lumberdom.hasNext())
											cecidomyiidous_melitriose = puchero_lumberdom
													.next();
										else
											cecidomyiidous_melitriose = "";
										if (null != cecidomyiidous_melitriose) {
											long orbed_firstly;
											try {
												orbed_firstly = Long
														.parseLong(cecidomyiidous_melitriose);
											} catch (NumberFormatException mare_chionis) {
												throw new RuntimeException(
														"STONESOUP: Failed to convert source taint.",
														mare_chionis);
											}
											long[] overhumanize_unsententious = new long[17];
											overhumanize_unsententious[16] = orbed_firstly;
											DrainerTemnospondylous<long[]> cephalalgy_stripy = new DrainerTemnospondylous<long[]>(
													overhumanize_unsententious);
											int arrowwood_unreiterated = 2;
											forebearCompsognathus(
													arrowwood_unreiterated,
													cephalalgy_stripy);
										}
									} catch (FileNotFoundException euphrosyneImbue) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												euphrosyneImbue);
									}
								}
							}
						}
					} finally {
						NettyHttpChannel.medievalSubdrain.close();
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
