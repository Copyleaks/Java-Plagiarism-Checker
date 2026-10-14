/*
 The MIT License(MIT)
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;

import classes.Consts;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.response.CopyleaksAuthToken;

/**
 * Sends the SDK's HTTP requests. One instance is shared by {@link classes.Copyleaks} and all its clients.
 * <p>
 * Applications configure it through the static setters on {@link classes.Copyleaks}
 * ({@code setConnectTimeout}, {@code setRequestTimeout}, {@code setMaxConcurrentRequests},
 * {@code setMaxRetries} and {@code setHttpClient}). Creating your own instance is only needed when
 * you build the clients yourself.
 * <p>
 * Every request is bounded by the request timeout. That covers the wait for a free concurrency slot,
 * sending the request and reading the whole response, so a call that hangs cannot block the caller
 * or hold a concurrency slot forever.
 */
public final class CopyleaksRequestExecutor {

    /** Default time allowed to open a connection: 30 seconds. */
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(30);
    /** Default time allowed for a whole request, including reading the response: 120 seconds. */
    public static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofSeconds(120);
    /** Default number of requests that may run at the same time: 20. */
    public static final int DEFAULT_MAX_CONCURRENT_REQUESTS = 20;

    static final Duration MAX_RETRY_DELAY = Duration.ofSeconds(30);
    static final Duration BASE_RETRY_DELAY = Duration.ofMillis(500);
    private static final int MAX_MESSAGE_BODY_CHARS = 4000;

    private volatile HttpClient httpClient;
    private volatile boolean clientProvidedByCaller;
    private volatile Duration connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private volatile Duration requestTimeout = DEFAULT_REQUEST_TIMEOUT;
    private volatile Semaphore permits;
    private volatile int maxConcurrentRequests;
    private volatile int maxRetries = 0;

    /**
     * Creates an executor with its own {@link HttpClient} and the default timeouts and concurrency limit.
     */
    public CopyleaksRequestExecutor() {
        this.httpClient = buildClient(connectTimeout);
        this.clientProvidedByCaller = false;
        this.maxConcurrentRequests = DEFAULT_MAX_CONCURRENT_REQUESTS;
        this.permits = new Semaphore(DEFAULT_MAX_CONCURRENT_REQUESTS, true);
    }

    /**
     * Creates an executor that uses the given client and concurrency semaphore.
     * The default request timeout applies.
     * @param httpClient the HTTP client to send requests with.
     * @param semaphore  limits how many requests run at the same time.
     */
    public CopyleaksRequestExecutor(HttpClient httpClient, Semaphore semaphore) {
        this.httpClient = Objects.requireNonNull(httpClient, "httpClient is null");
        this.clientProvidedByCaller = true;
        this.permits = Objects.requireNonNull(semaphore, "semaphore is null");
        this.maxConcurrentRequests = semaphore.availablePermits();
    }

    private static HttpClient buildClient(Duration connectTimeout) {
        return HttpClient.newBuilder().connectTimeout(connectTimeout).build();
    }

    // ---- configuration ---------------------------------------------------------------------------

    /**
     * @return the HTTP client used to send requests.
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Uses the given HTTP client for all following requests. Its own connect timeout, proxy and executor apply.
     * Pass null to go back to a client created by the SDK.
     * @param client the client to use, or null.
     */
    public synchronized void setHttpClient(HttpClient client) {
        if (client == null) {
            this.httpClient = buildClient(connectTimeout);
            this.clientProvidedByCaller = false;
        } else {
            this.httpClient = client;
            this.clientProvidedByCaller = true;
        }
    }

    /**
     * @return the time allowed to open a connection.
     */
    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets the time allowed to open a connection. It applies to the client the SDK creates.
     * A client passed to {@link #setHttpClient(HttpClient)} keeps its own setting.
     * @param timeout a positive duration.
     */
    public synchronized void setConnectTimeout(Duration timeout) {
        this.connectTimeout = requirePositive(timeout, "connect timeout");
        if (!clientProvidedByCaller) {
            this.httpClient = buildClient(this.connectTimeout);
        }
    }

    /**
     * @return the time allowed for a whole request.
     */
    public Duration getRequestTimeout() {
        return requestTimeout;
    }

    /**
     * Sets the time allowed for a whole request: waiting for a free concurrency slot, sending the request
     * and reading the whole response. When it runs out, the call throws an {@link ExecutionException}
     * caused by an {@link HttpTimeoutException}.
     * @param timeout a positive duration.
     */
    public void setRequestTimeout(Duration timeout) {
        this.requestTimeout = requirePositive(timeout, "request timeout");
    }

    /**
     * @return how many requests may run at the same time.
     */
    public int getMaxConcurrentRequests() {
        return maxConcurrentRequests;
    }

    /**
     * Sets how many requests may run at the same time. Requests already running are not affected.
     * @param max a number of requests, at least 1.
     */
    public synchronized void setMaxConcurrentRequests(int max) {
        if (max < 1) {
            throw new IllegalArgumentException("max concurrent requests must be at least 1");
        }
        this.maxConcurrentRequests = max;
        this.permits = new Semaphore(max, true);
    }

    /**
     * @return how many times a failed request is retried.
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * Sets how many times a failed request is retried. The default is 0 (no retries).
     * <p>
     * Every request is retried on 429 and 503, which mean that the server did not process it.
     * GET requests are also retried on 502, 504 and network errors.
     * The wait between attempts grows exponentially with random jitter, and a {@code Retry-After}
     * header is honored up to 30 seconds.
     * @param retries a number of retries, from 0 to 10.
     */
    public void setMaxRetries(int retries) {
        if (retries < 0 || retries > 10) {
            throw new IllegalArgumentException("max retries must be between 0 and 10");
        }
        this.maxRetries = retries;
    }

    private static Duration requirePositive(Duration d, String name) {
        Objects.requireNonNull(d, name + " is null");
        if (d.isNegative() || d.isZero()) {
            throw new IllegalArgumentException(name + " must be positive");
        }
        return d;
    }

    // ---- request building ------------------------------------------------------------------------

    /**
     * Serializes an object to a JSON request body without building an intermediate string.
     * @param gson  the Gson instance to serialize with.
     * @param model the object to serialize.
     * @return a body publisher for the JSON bytes.
     */
    public static HttpRequest.BodyPublisher jsonBody(Gson gson, Object model) {
        ExposedByteArrayOutputStream out = new ExposedByteArrayOutputStream();
        try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            gson.toJson(model, writer);
        } catch (IOException e) {
            throw new IllegalStateException("Could not serialize the request body", e);
        }
        return HttpRequest.BodyPublishers.ofByteArray(out.buffer(), 0, out.size());
    }

    private static final class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        ExposedByteArrayOutputStream() {
            super(8192);
        }

        byte[] buffer() {
            return buf;
        }
    }

    /**
     * Percent-encodes a value for use as one URL path segment.
     * @param value the raw value, for example a scan ID.
     * @return the encoded segment.
     */
    public static String pathSegment(String value) {
        Objects.requireNonNull(value, "path value is null");
        StringBuilder sb = new StringBuilder(value.length() + 8);
        for (byte b : value.getBytes(StandardCharsets.UTF_8)) {
            int c = b & 0xff;
            boolean keep = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')
                    || "-._~!$&'()*+,;=:@".indexOf(c) >= 0;
            if (keep) {
                sb.append((char) c);
            } else {
                sb.append('%').append(Character.toUpperCase(Character.forDigit(c >> 4, 16)))
                        .append(Character.toUpperCase(Character.forDigit(c & 0xf, 16)));
            }
        }
        return sb.toString();
    }

    /**
     * Encodes a value for use in a URL query string.
     * @param value the raw value.
     * @return the encoded value.
     */
    public static String queryValue(String value) {
        return URLEncoder.encode(Objects.requireNonNull(value, "query value is null"), StandardCharsets.UTF_8);
    }

    /**
     * Sends a request and returns the response body of a 2xx response.
     * @param method      the HTTP method.
     * @param url         the full request URL.
     * @param body        the request body, or null for none.
     * @param contentType the Content-Type header, or null for none.
     * @param authToken   the login token for the Authorization header, or null for public endpoints.
     * @return the response body text.
     * @throws ExecutionException        if the request could not be sent or timed out. The cause is the I/O error,
     *                                   for example an {@link HttpTimeoutException}.
     * @throws InterruptedException      if the calling thread is interrupted.
     * @throws UnderMaintenanceException if the server answered 503.
     * @throws RateLimitException        if the server answered 429.
     * @throws CommandException          if the server answered with any other non-2xx status.
     */
    public String send(String method, String url, HttpRequest.BodyPublisher body, String contentType,
            CopyleaksAuthToken authToken)
            throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException,
            CommandException {
        Duration timeout = requestTimeout;
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(timeout)
                .method(method, body == null ? HttpRequest.BodyPublishers.noBody() : body)
                .setHeader("User-Agent", Consts.USER_AGENT);
        if (contentType != null) {
            builder.setHeader("Content-Type", contentType);
        }
        if (authToken != null) {
            builder.setHeader("Authorization", "Bearer " + authToken.getAccessToken());
        }
        HttpResponse<String> response = sendWithRetries(builder.build(), timeout);
        int status = response.statusCode();
        String text = response.body();
        if (status >= 200 && status <= 299) {
            return text;
        }
        if (status == 429) {
            throw new RateLimitException(status, text, retryAfter(response.headers()));
        }
        if (status == 503) {
            throw new UnderMaintenanceException(status, text);
        }
        throw new CommandException(commandMessage(status, text), status, text);
    }

    private static String commandMessage(int status, String body) {
        String message = "command failed with status Code: " + status;
        if (body != null && !body.isEmpty()) {
            message += "\n" + (body.length() > MAX_MESSAGE_BODY_CHARS
                    ? body.substring(0, MAX_MESSAGE_BODY_CHARS) + "..." : body);
        }
        return message;
    }

    private HttpResponse<String> sendWithRetries(HttpRequest request, Duration timeout)
            throws ExecutionException, InterruptedException {
        boolean isGet = "GET".equals(request.method());
        int retries = maxRetries;
        for (int attempt = 0; ; attempt++) {
            HttpResponse<String> response;
            try {
                response = sendOnce(request, timeout);
            } catch (ExecutionException e) {
                if (isGet && attempt < retries && e.getCause() instanceof IOException) {
                    sleep(backoff(attempt, null));
                    continue;
                }
                throw e;
            }
            int status = response.statusCode();
            boolean retryable = status == 429 || status == 503 || (isGet && (status == 502 || status == 504));
            if (!retryable || attempt >= retries) {
                return response;
            }
            sleep(backoff(attempt, retryAfter(response.headers())));
        }
    }

    private HttpResponse<String> sendOnce(HttpRequest request, Duration timeout)
            throws ExecutionException, InterruptedException {
        long deadline = System.nanoTime() + timeout.toNanos();
        Semaphore semaphore = permits;
        if (!semaphore.tryAcquire(timeout.toNanos(), TimeUnit.NANOSECONDS)) {
            throw new ExecutionException(new HttpTimeoutException(
                    "No free request slot within " + timeout.toMillis() + " ms (max concurrent requests: "
                            + maxConcurrentRequests + ")"));
        }
        try {
            CompletableFuture<HttpResponse<String>> future =
                    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            long remaining = deadline - System.nanoTime();
            try {
                return future.get(Math.max(remaining, 1L), TimeUnit.NANOSECONDS);
            } catch (TimeoutException e) {
                future.cancel(true);
                throw new ExecutionException(new HttpTimeoutException(
                        "Request timed out after " + timeout.toMillis() + " ms"));
            } catch (InterruptedException e) {
                future.cancel(true);
                throw e;
            }
        } finally {
            semaphore.release();
        }
    }

    static Duration backoff(int attempt, Duration retryAfter) {
        if (retryAfter != null) {
            return retryAfter.compareTo(MAX_RETRY_DELAY) > 0 ? MAX_RETRY_DELAY : retryAfter;
        }
        long cap = Math.min(MAX_RETRY_DELAY.toMillis(), BASE_RETRY_DELAY.toMillis() << Math.min(attempt, 10));
        long half = cap / 2;
        return Duration.ofMillis(half + ThreadLocalRandom.current().nextLong(half + 1));
    }

    private static void sleep(Duration d) throws InterruptedException {
        if (!d.isZero() && !d.isNegative()) {
            Thread.sleep(d.toMillis());
        }
    }

    /**
     * Reads a {@code Retry-After} header given in seconds or as an HTTP date.
     * @param headers the response headers.
     * @return the delay, or null when the header is missing or invalid.
     */
    static Duration retryAfter(HttpHeaders headers) {
        Optional<String> value = headers.firstValue("Retry-After");
        if (!value.isPresent()) {
            return null;
        }
        String v = value.get().trim();
        try {
            long seconds = Long.parseLong(v);
            return seconds < 0 ? null : Duration.ofSeconds(seconds);
        } catch (NumberFormatException ignored) {
            // not a number of seconds, try an HTTP date
        }
        try {
            Duration d = Duration.between(ZonedDateTime.now(),
                    ZonedDateTime.parse(v, DateTimeFormatter.RFC_1123_DATE_TIME));
            return d.isNegative() ? Duration.ZERO : d;
        } catch (RuntimeException ignored) {
            return null;
        }
    }
}
