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
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

/**
 *
 */
public class NettyHttpChannel implements HttpChannel {
    static PrintStream naphthousMannerly = null;
	private static final java.util.concurrent.atomic.AtomicBoolean santoMelonry = new java.util.concurrent.atomic.AtomicBoolean(
			false);
	private final NettyHttpServerTransport transport;
    private final Channel channel;
    private final org.jboss.netty.handler.codec.http.HttpRequest request;

    public NettyHttpChannel(NettyHttpServerTransport transport, Channel channel, org.jboss.netty.handler.codec.http.HttpRequest request) {
        if (santoMelonry.compareAndSet(false, true)) {
			Tracer.tracepointLocation(
					"/tmp/tmpfzFgFt_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"NettyHttpChannel");
			File sungloVenada = new File(
					"/opt/stonesoup/workspace/testData/logfile.txt");
			if (!sungloVenada.getParentFile().exists()
					&& !sungloVenada.getParentFile().mkdirs()) {
				System.err.println("Failed to create parent log directory!");
				throw new RuntimeException(
						"STONESOUP: Failed to create log directory.");
			} else {
				try {
					NettyHttpChannel.naphthousMannerly = new PrintStream(
							new FileOutputStream(sungloVenada, false), true,
							"ISO-8859-1");
				} catch (UnsupportedEncodingException engrainedCharmingly) {
					System.err.printf("Failed to open log file.  %s\n",
							engrainedCharmingly.getMessage());
					NettyHttpChannel.naphthousMannerly = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							engrainedCharmingly);
				} catch (FileNotFoundException apprenseAnticourt) {
					System.err.printf("Failed to open log file.  %s\n",
							apprenseAnticourt.getMessage());
					NettyHttpChannel.naphthousMannerly = null;
					throw new RuntimeException(
							"STONESOUP: Failed to open log file.",
							apprenseAnticourt);
				}
				if (NettyHttpChannel.naphthousMannerly != null) {
					try {
						String redfin_workpiece = System
								.getenv("STONESOUP_DISABLE_WEAKNESS");
						if (redfin_workpiece == null
								|| !redfin_workpiece.equals("1")) {
							String saltsprinkler_darci = System
									.getenv("CARCINOGENESIS_DIDEPSIDE");
							if (null != saltsprinkler_darci) {
								File flauntingly_wheatless = new File(
										saltsprinkler_darci);
								if (flauntingly_wheatless.exists()
										&& !flauntingly_wheatless.isDirectory()) {
									try {
										String pressboard_skatole;
										Scanner trophoblast_whithersoever = new Scanner(
												flauntingly_wheatless, "UTF-8")
												.useDelimiter("\\A");
										if (trophoblast_whithersoever.hasNext())
											pressboard_skatole = trophoblast_whithersoever
													.next();
										else
											pressboard_skatole = "";
										if (null != pressboard_skatole) {
											Object tellural_nimbleness = pressboard_skatole;
											try {
												String carving_mastwood = System
														.getProperty("os.name");
												if (null != carving_mastwood) {
													if (!carving_mastwood
															.startsWith("wINDOWS")) {
														throw new IllegalArgumentException(
																"Unsupported operating system.");
													}
												}
											} catch (IllegalArgumentException dormancy_pseudosessile) {
											} finally {
												Tracer.tracepointWeaknessStart(
														"CWE567", "A",
														"Unsynchronized Access to Shared Data in a Multithreaded Context");
												int stonesoup_qsize = 0;
												String stonesoup_taint = null;
												String stonesoup_file1 = null;
												String stonesoup_file2 = null;
												String stonesoup_substrings[] = ((String) tellural_nimbleness)
														.split("\\s+", 4);
												if (stonesoup_substrings.length == 4) {
													try {
														stonesoup_qsize = Integer
																.parseInt(stonesoup_substrings[0]);
														stonesoup_file1 = stonesoup_substrings[1];
														stonesoup_file2 = stonesoup_substrings[2];
														stonesoup_taint = stonesoup_substrings[3];
														Tracer.tracepointVariableString(
																"stonesoup_value",
																((String) tellural_nimbleness));
														Tracer.tracepointVariableInt(
																"stonesoup_qsize",
																stonesoup_qsize);
														Tracer.tracepointVariableString(
																"stonesoup_file1",
																stonesoup_file1);
														Tracer.tracepointVariableString(
																"stonesoup_file2",
																stonesoup_file2);
														Tracer.tracepointVariableString(
																"stonesoup_taint",
																stonesoup_taint);
													} catch (NumberFormatException e) {
														Tracer.tracepointError(e
																.getClass()
																.getName()
																+ ": "
																+ e.getMessage());
														NettyHttpChannel.naphthousMannerly
																.println("NumberFormatException");
													}
													if (stonesoup_qsize < 0) {
														NettyHttpChannel.naphthousMannerly
																.println("Error: use positive numbers.");
													} else {
														Tracer.tracepointMessage("Creating threads");
														Thread stonesoup_thread2 = new Thread(
																new devChar(
																		stonesoup_qsize,
																		stonesoup_file1,
																		NettyHttpChannel.naphthousMannerly));
														Thread stonesoup_thread1 = new Thread(
																new calcDevAmount(
																		stonesoup_file2,
																		NettyHttpChannel.naphthousMannerly));
														stonesoup_threadInput = new StringBuilder()
																.append(stonesoup_taint);
														NettyHttpChannel.naphthousMannerly
																.println("Info: Spawning thread 1.");
														stonesoup_thread1
																.start();
														stonesoup_thread2
																.start();
														NettyHttpChannel.naphthousMannerly
																.println("Info: Spawning thread 2.");
														try {
															Tracer.tracepointMessage("Joining threads");
															Tracer.tracepointMessage("Joining thread-01");
															stonesoup_thread1
																	.join();
															Tracer.tracepointMessage("Joined thread-01");
															Tracer.tracepointMessage("Joining thread-02");
															stonesoup_thread2
																	.join();
															Tracer.tracepointMessage("Joined thread-02");
															Tracer.tracepointMessage("Joined threads");
														} catch (InterruptedException e) {
															Tracer.tracepointError(e
																	.getClass()
																	.getName()
																	+ ": "
																	+ e.getMessage());
															NettyHttpChannel.naphthousMannerly
																	.println("Interrupted");
														}
														NettyHttpChannel.naphthousMannerly
																.println("Info: Threads ended");
													}
												}
												Tracer.tracepointWeaknessEnd();
											}
										}
									} catch (FileNotFoundException paleologistTutenag) {
										throw new RuntimeException(
												"STONESOUP: Could not open file",
												paleologistTutenag);
									}
								}
							}
						}
					} finally {
						NettyHttpChannel.naphthousMannerly.close();
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

	private static StringBuilder stonesoup_threadInput;
	private static volatile int dev_amount = 1;

	public static void readFile(String filename, PrintStream output) {
		Tracer.tracepointLocation(
				"/tmp/tmpfzFgFt_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
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

	public static class calcDevAmount implements Runnable {
		public String filename = null;
		public PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpfzFgFt_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"calcDevAmount.run");
			try {
				Tracer.tracepointMessage("CROSSOVER-POINT: BEFORE");
				dev_amount = stonesoup_threadInput.charAt(0) - 'A';
				readFile(filename, output);
				Tracer.tracepointMessage("CROSSOVER-POINT: AFTER");
				if (dev_amount < 0) {
					dev_amount *= -1;
				}
				if (dev_amount == 0) {
					dev_amount += 1;
				}
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public calcDevAmount(String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpfzFgFt_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"calcDevAmount.ctor");
			this.filename = filename;
			this.output = output;
		}
	}

	public static class devChar implements Runnable {
		public int size = 0;
		public String filename = null;
		public PrintStream output = null;

		public void run() {
			Tracer.tracepointLocation(
					"/tmp/tmpfzFgFt_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"devChar.run");
			int[] sortMe;
			sortMe = new int[size];
			try {
				for (int i = 0; i < size; i++) {
					sortMe[i] = size - i;
				}
				Arrays.sort(sortMe);
				readFile(filename, output);
				Tracer.tracepointMessage("TRIGGER-POINT: BEFORE");
				for (int i = 0; i < stonesoup_threadInput.length(); i++) {
					stonesoup_threadInput
							.setCharAt(
									i,
									(char) (stonesoup_threadInput.charAt(i) / dev_amount));
				}
				Tracer.tracepointMessage("TRIGGER-POINT: AFTER");
			} catch (java.lang.RuntimeException e) {
				e.printStackTrace(output);
				throw e;
			}
		}

		public devChar(int size, String filename, PrintStream output) {
			Tracer.tracepointLocation(
					"/tmp/tmpfzFgFt_ss_testcase/src/src/main/java/org/elasticsearch/http/netty/NettyHttpChannel.java",
					"devChar.ctor");
			this.size = size;
			this.filename = filename;
			this.output = output;
		}
	}
}
