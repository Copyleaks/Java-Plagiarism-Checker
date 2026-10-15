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
import models.exceptions.AuthExpiredException;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.response.CopyleaksAuthToken;

/**
 * Sends the SDK's HTTP requests. {@link classes.Copyleaks} and its clients share one instance, configured
 * through the static setters on {@link classes.Copyleaks}, which document each setting. Create your own
 * instance only when you build the clients yourself.
 */
public final class CopyleaksRequestExecutor {

    /** Default time allowed to open a connection: 30 seconds. */
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(30);
    /** Default time allowed for each attempt of a request, including reading the response: 120 seconds. */
    public static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofSeconds(120);
    /** Default number of requests that may run at the same time: 20. */
    public static final int DEFAULT_MAX_CONCURRENT_REQUESTS = 20;

    static final Duration MAX_RETRY_DELAY = Duration.ofSeconds(30);
    static final Duration BASE_RETRY_DELAY = Duration.ofMillis(500);
    private static final int MAX_MESSAGE_BODY_CHARS = 4000;
    private static final String JSON = "application/json";
    private static final Gson GSON = new Gson();

    private volatile HttpClient httpClient;
    private volatile boolean clientProvidedByCaller;
    private volatile Duration connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private volatile Duration requestTimeout = DEFAULT_REQUEST_TIMEOUT;
    private volatile Semaphore permits;
    private volatile int maxConcurrentRequests;
    private volatile int maxRetries = 0;

    /** Creates an executor with its own {@link HttpClient} and the default settings. */
    public CopyleaksRequestExecutor() {
        this.httpClient = buildClient(connectTimeout);
        this.clientProvidedByCaller = false;
        this.maxConcurrentRequests = DEFAULT_MAX_CONCURRENT_REQUESTS;
        this.permits = new Semaphore(DEFAULT_MAX_CONCURRENT_REQUESTS, true);
    }

    /**
     * Uses the given client and concurrency semaphore, with the default request timeout.
     * @param semaphore limits how many requests run at the same time.
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

    /** @return the HTTP client used to send requests. */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /** Client for later requests, or null for the SDK's own. See {@link classes.Copyleaks#setHttpClient}. */
    public synchronized void setHttpClient(HttpClient client) {
        if (client == null) {
            this.httpClient = buildClient(connectTimeout);
            this.clientProvidedByCaller = false;
        } else {
            this.httpClient = client;
            this.clientProvidedByCaller = true;
        }
    }

    /** @return the time allowed to open a connection. */
    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    /** Time allowed to open a connection, a positive duration. See {@link classes.Copyleaks#setConnectTimeout}. */
    public synchronized void setConnectTimeout(Duration timeout) {
        this.connectTimeout = requirePositive(timeout, "connect timeout");
        if (!clientProvidedByCaller) {
            this.httpClient = buildClient(this.connectTimeout);
        }
    }

    /** @return the time allowed for each attempt of a request. */
    public Duration getRequestTimeout() {
        return requestTimeout;
    }

    /** Time allowed per attempt, a positive duration. See {@link classes.Copyleaks#setRequestTimeout}. */
    public void setRequestTimeout(Duration timeout) {
        this.requestTimeout = requirePositive(timeout, "request timeout");
    }

    /** @return how many requests may run at the same time. */
    public int getMaxConcurrentRequests() {
        return maxConcurrentRequests;
    }

    /** Requests that may run at the same time, at least 1. Requests already running are not affected. */
    public synchronized void setMaxConcurrentRequests(int max) {
        if (max < 1) {
            throw new IllegalArgumentException("max concurrent requests must be at least 1");
        }
        this.maxConcurrentRequests = max;
        this.permits = new Semaphore(max, true);
    }

    /** @return how many times a failed request is retried. */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * Retries for a failed request, from 0 to 10. See {@link classes.Copyleaks#setMaxRetries}.
     * 429 and 503 are retried for every method because the server did not process the request.
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

    /** Serializes an object to a JSON request body without building an intermediate string. */
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

    /** Percent-encodes a value, for example a scan ID, as one URL path segment. */
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

    /** Encodes a value for a URL query string. */
    public static String queryValue(String value) {
        return URLEncoder.encode(Objects.requireNonNull(value, "query value is null"), StandardCharsets.UTF_8);
    }

    /**
     * Sends a request and returns the body of a 2xx response. Errors are mapped as described on
     * {@link classes.Copyleaks}.
     * @param body        the request body, or null.
     * @param contentType the Content-Type header, or null.
     * @param authToken   the token for the Authorization header, or null for public endpoints.
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

    /**
     * Sends a request to an endpoint that needs a login token and parses the response. GET requests have no body;
     * other requests send {@code model} as JSON, or an empty body when it is null, with Content-Type application/json.
     * @param authToken    checked before anything is sent.
     * @param responseType the class to parse the response into; String.class returns the raw text, null ignores it.
     * @throws AuthExpiredException if the token has expired.
     */
    public <T> T sendJson(String method, String url, CopyleaksAuthToken authToken, Object model, Class<T> responseType)
            throws AuthExpiredException, ExecutionException, InterruptedException, UnderMaintenanceException,
            RateLimitException, CommandException {
        requireValidToken(authToken);
        return exchange(method, url, model, authToken, responseType);
    }

    /** Like {@link #sendJson}, for public endpoints that need no login token. */
    public <T> T sendJsonPublic(String method, String url, Object model, Class<T> responseType)
            throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException,
            CommandException {
        return exchange(method, url, model, null, responseType);
    }

    private <T> T exchange(String method, String url, Object model, CopyleaksAuthToken authToken,
            Class<T> responseType)
            throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException,
            CommandException {
        if ("GET".equals(method)) {
            return parse(send(method, url, null, null, authToken), responseType);
        }
        HttpRequest.BodyPublisher body = model == null ? HttpRequest.BodyPublishers.noBody() : jsonBody(GSON, model);
        return parse(send(method, url, body, JSON, authToken), responseType);
    }

    /**
     * POSTs a {@code multipart/form-data} body to an endpoint that needs a login token and parses the response.
     * @param authToken    checked before anything is sent.
     * @param responseType the class to parse the response into, or null to ignore it.
     * @throws AuthExpiredException if the token has expired.
     */
    public <T> T sendMultipart(String url, CopyleaksAuthToken authToken, MultipartBody form, Class<T> responseType)
            throws AuthExpiredException, ExecutionException, InterruptedException, UnderMaintenanceException,
            RateLimitException, CommandException {
        requireValidToken(authToken);
        return parse(send("POST", url, form.build(), form.contentType(), authToken), responseType);
    }

    private static void requireValidToken(CopyleaksAuthToken authToken) throws AuthExpiredException {
        Objects.requireNonNull(authToken, "token is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);
    }

    private static <T> T parse(String body, Class<T> responseType) {
        if (responseType == null) {
            return null;
        }
        if (responseType == String.class) {
            return responseType.cast(body);
        }
        return GSON.fromJson(body, responseType);
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

    /** Reads {@code Retry-After} in seconds or as an HTTP date; null when missing or invalid. */
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
