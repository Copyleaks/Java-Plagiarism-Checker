package helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpTimeoutException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import testutil.MockApiServer;
import testutil.TestTokens;

class CopyleaksRequestExecutorTest {

    private MockApiServer server;
    private CopyleaksRequestExecutor executor;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockApiServer();
        executor = new CopyleaksRequestExecutor();
    }

    @AfterEach
    void tearDown() {
        server.close();
    }

    private String get(String path) throws Exception {
        return executor.send("GET", server.url() + path, null, null, null);
    }

    // ---- timeouts and the concurrency limit --------------------------------------------------

    @Test
    void defaults() {
        assertEquals(Duration.ofSeconds(30), executor.getConnectTimeout());
        assertEquals(Duration.ofSeconds(120), executor.getRequestTimeout());
        assertEquals(20, executor.getMaxConcurrentRequests());
        assertEquals(0, executor.getMaxRetries());
        assertEquals(Optional.of(Duration.ofSeconds(30)), executor.getHttpClient().connectTimeout());
    }

    @Test
    void requestTimeoutEndsAHungCall() throws Exception {
        executor.setRequestTimeout(Duration.ofMillis(300));
        server.replyAfter(5000, 200, "{}");
        long start = System.nanoTime();
        ExecutionException e = assertThrows(ExecutionException.class, () -> get("/hang"));
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        assertInstanceOf(HttpTimeoutException.class, e.getCause());
        assertTrue(elapsedMs < 3000, "call took " + elapsedMs + " ms");
    }

    @Test
    void timedOutCallReleasesItsConcurrencySlot() throws Exception {
        executor.setMaxConcurrentRequests(1);
        executor.setRequestTimeout(Duration.ofMillis(300));
        server.replyAfter(5000, 200, "{}");
        assertThrows(ExecutionException.class, () -> get("/hang"));
        // the only slot must be free again
        executor.setRequestTimeout(Duration.ofSeconds(5));
        server.reply(200, "{\"ok\":true}");
        assertEquals("{\"ok\":true}", get("/fast"));
    }

    @Test
    void waitingForASlotIsBoundedByTheRequestTimeout() {
        CopyleaksRequestExecutor noSlots = new CopyleaksRequestExecutor(HttpClient.newHttpClient(), new Semaphore(0));
        noSlots.setRequestTimeout(Duration.ofMillis(200));
        long start = System.nanoTime();
        ExecutionException e = assertThrows(ExecutionException.class,
                () -> noSlots.send("GET", server.url() + "/x", null, null, null));
        assertInstanceOf(HttpTimeoutException.class, e.getCause());
        assertTrue(e.getCause().getMessage().contains("No free request slot"));
        assertTrue((System.nanoTime() - start) / 1_000_000 < 2000);
        assertTrue(server.requests().isEmpty(), "no request may be sent without a slot");
    }

    @Test
    void concurrentCallsShareTheLimitAndAllFinish() throws Exception {
        executor.setMaxConcurrentRequests(2);
        server.always(200, "{}");
        List<CompletableFuture<String>> calls = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            calls.add(CompletableFuture.supplyAsync(() -> {
                try {
                    return get("/c");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        for (Future<String> f : calls) {
            assertEquals("{}", f.get(10, TimeUnit.SECONDS));
        }
        assertEquals(8, server.requests().size());
    }

    @Test
    void connectTimeoutIsConfigurable() {
        executor.setConnectTimeout(Duration.ofSeconds(5));
        assertEquals(Duration.ofSeconds(5), executor.getConnectTimeout());
        assertEquals(Optional.of(Duration.ofSeconds(5)), executor.getHttpClient().connectTimeout());
    }

    @Test
    void injectedHttpClientIsUsedAndKept() throws Exception {
        HttpClient mine = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();
        executor.setHttpClient(mine);
        assertSame(mine, executor.getHttpClient());
        executor.setConnectTimeout(Duration.ofSeconds(9));
        assertSame(mine, executor.getHttpClient(), "a caller's client is not replaced");
        server.reply(200, "{}");
        assertEquals("{}", get("/x"));
        executor.setHttpClient(null);
        assertFalse(executor.getHttpClient() == mine);
        assertEquals(Optional.of(Duration.ofSeconds(9)), executor.getHttpClient().connectTimeout());
    }

    @Test
    void invalidSettingsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> executor.setRequestTimeout(Duration.ZERO));
        assertThrows(IllegalArgumentException.class, () -> executor.setConnectTimeout(Duration.ofSeconds(-1)));
        assertThrows(NullPointerException.class, () -> executor.setRequestTimeout(null));
        assertThrows(IllegalArgumentException.class, () -> executor.setMaxConcurrentRequests(0));
        assertThrows(IllegalArgumentException.class, () -> executor.setMaxRetries(-1));
        assertThrows(IllegalArgumentException.class, () -> executor.setMaxRetries(11));
    }

    // ---- error mapping ---------------------------------------------------------------------------

    @Test
    void non2xxThrowsCommandExceptionWithStatusAndBody() {
        String body = "{\"error\":{\"type\":\"invalid_request_error\",\"id\":\"missing_parameter\",\"code\":1,\"message\":\"Bad request.\"}}";
        server.reply(400, body);
        CommandException e = assertThrows(CommandException.class, () -> get("/bad"));
        assertEquals(400, e.getStatusCode());
        assertEquals(body, e.getResponseBody());
        assertTrue(e.getMessage().startsWith("command failed with status Code: 400"));
        assertTrue(e.getMessage().contains("missing_parameter"), e.getMessage());
        assertFalse(e.getMessage().contains("CompletableFuture"));
    }

    @Test
    void longBodiesAreShortenedInTheMessageOnly() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6000; i++) {
            sb.append('x');
        }
        server.reply(500, sb.toString());
        CommandException e = assertThrows(CommandException.class, () -> get("/big"));
        assertEquals(6000, e.getResponseBody().length());
        assertTrue(e.getMessage().length() < 4100);
    }

    @Test
    void status429ThrowsRateLimitExceptionWithRetryAfter() {
        server.reply(429, "{\"error\":\"Rate limit exceeded.\"}", "Retry-After", "7");
        RateLimitException e = assertThrows(RateLimitException.class, () -> get("/limited"));
        assertEquals(429, e.getStatusCode());
        assertEquals("{\"error\":\"Rate limit exceeded.\"}", e.getResponseBody());
        assertEquals(Duration.ofSeconds(7), e.getRetryAfter());
        assertTrue(e.getMessage().contains("Retry after 7 seconds"));
    }

    @Test
    void status503ThrowsUnderMaintenanceExceptionWithBody() {
        server.reply(503, "{\"message\":\"maintenance\"}");
        UnderMaintenanceException e = assertThrows(UnderMaintenanceException.class, () -> get("/down"));
        assertEquals(503, e.getStatusCode());
        assertEquals("{\"message\":\"maintenance\"}", e.getResponseBody());
    }

    @Test
    void connectionRefusedIsAnExecutionExceptionWithTheIoCause() throws Exception {
        String url = server.url();
        server.close();
        ExecutionException e = assertThrows(ExecutionException.class,
                () -> executor.send("GET", url + "/gone", null, null, null));
        assertInstanceOf(java.io.IOException.class, e.getCause());
    }

    // ---- retries -----------------------------------------------------------------------------------

    @Test
    void noRetriesByDefault() {
        server.reply(429, "{}", "Retry-After", "0").reply(200, "{}");
        assertThrows(RateLimitException.class, () -> get("/x"));
        assertEquals(1, server.requests().size());
    }

    @Test
    void retries429OnAnyMethodAndHonorsRetryAfter() throws Exception {
        executor.setMaxRetries(2);
        server.reply(429, "{}", "Retry-After", "0").reply(503, "{}", "Retry-After", "0").reply(200, "{\"done\":1}");
        String body = executor.send("POST", server.url() + "/p",
                HttpRequest.BodyPublishers.ofString("{\"a\":1}"), "application/json", null);
        assertEquals("{\"done\":1}", body);
        assertEquals(3, server.requests().size());
        for (MockApiServer.Recorded r : server.requests()) {
            assertEquals("{\"a\":1}", r.bodyText(), "the body is sent again on each attempt");
        }
    }

    @Test
    void retriesStopAtTheLimit() {
        executor.setMaxRetries(1);
        server.always(429, "{}", "Retry-After", "0");
        assertThrows(RateLimitException.class, () -> get("/x"));
        assertEquals(2, server.requests().size());
    }

    @Test
    void getIsRetriedOn502ButPostIsNot() throws Exception {
        executor.setMaxRetries(1);
        server.reply(502, "{}").reply(200, "{}");
        assertEquals("{}", get("/g"));
        assertEquals(2, server.requests().size());

        try (MockApiServer other = new MockApiServer()) {
            other.reply(502, "{}").reply(200, "{}");
            CommandException e = assertThrows(CommandException.class, () -> executor.send("POST", other.url() + "/p",
                    HttpRequest.BodyPublishers.ofString("{}"), "application/json", null));
            assertEquals(502, e.getStatusCode());
            assertEquals(1, other.requests().size());
        }
    }

    @Test
    void backoffGrowsAndIsCapped() {
        for (int attempt = 0; attempt < 12; attempt++) {
            long cap = Math.min(30_000, 500L << Math.min(attempt, 10));
            long ms = CopyleaksRequestExecutor.backoff(attempt, null).toMillis();
            assertTrue(ms >= cap / 2 && ms <= cap, "attempt " + attempt + ": " + ms);
        }
        assertEquals(Duration.ofSeconds(4), CopyleaksRequestExecutor.backoff(0, Duration.ofSeconds(4)));
        assertEquals(Duration.ofSeconds(30), CopyleaksRequestExecutor.backoff(0, Duration.ofSeconds(600)));
    }

    @Test
    void retryAfterParsesSecondsAndDates() {
        assertEquals(Duration.ofSeconds(12), CopyleaksRequestExecutor.retryAfter(headers("Retry-After", "12")));
        assertNull(CopyleaksRequestExecutor.retryAfter(headers("Retry-After", "soon")));
        assertNull(CopyleaksRequestExecutor.retryAfter(headers("X-Other", "1")));
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now().plusSeconds(20));
        Duration d = CopyleaksRequestExecutor.retryAfter(headers("Retry-After", date));
        assertTrue(d.getSeconds() >= 15 && d.getSeconds() <= 21, d.toString());
    }

    private static HttpHeaders headers(String name, String value) {
        return HttpHeaders.of(Map.of(name, List.of(value)), (a, b) -> true);
    }

    // ---- request building --------------------------------------------------------------------------

    @Test
    void sendsUserAgentAuthorizationAndContentType() throws Exception {
        server.reply(200, "{}");
        executor.send("PUT", server.url() + "/v3/x", HttpRequest.BodyPublishers.ofString("{}"), "application/json",
                TestTokens.valid());
        MockApiServer.Recorded r = server.only();
        assertEquals("PUT", r.method);
        assertEquals("java-sdk/6.0.0", r.header("User-Agent"));
        assertEquals("Bearer " + TestTokens.ACCESS_TOKEN, r.header("Authorization"));
        assertEquals("application/json", r.header("Content-Type"));
    }

    @Test
    void publicCallsSendNoAuthorization() throws Exception {
        server.reply(200, "[]");
        get("/v3/miscellaneous/ocr-languages-list");
        assertNull(server.only().header("Authorization"));
        assertNull(server.only().header("Content-Type"));
    }

    @Test
    void pathSegmentEncodesReservedCharacters() {
        assertEquals("my-scan_1.2~x", CopyleaksRequestExecutor.pathSegment("my-scan_1.2~x"));
        assertEquals("a%20b%2Fc%3Fd%23e%25f", CopyleaksRequestExecutor.pathSegment("a b/c?d#e%f"));
        assertEquals("!$&'()*+,;=:@", CopyleaksRequestExecutor.pathSegment("!$&'()*+,;=:@"));
        assertEquals("%7B%7D%3C%3E%22%7C%60%5E", CopyleaksRequestExecutor.pathSegment("{}<>\"|`^"));
        assertEquals("%C3%A9", CopyleaksRequestExecutor.pathSegment("é"));
    }

    @Test
    void queryValueEncodes() {
        assertEquals("01-02-2026", CopyleaksRequestExecutor.queryValue("01-02-2026"));
        assertEquals("a+b%26c", CopyleaksRequestExecutor.queryValue("a b&c"));
    }

    @Test
    void jsonBodyWritesUtf8Json() throws Exception {
        Map<String, String> model = Map.of("text", "café ✓");
        HttpRequest.BodyPublisher publisher = CopyleaksRequestExecutor.jsonBody(new Gson(), model);
        byte[] bytes = drain(publisher);
        assertEquals("{\"text\":\"café ✓\"}", new String(bytes, StandardCharsets.UTF_8));
        assertEquals(bytes.length, publisher.contentLength());
    }

    private static byte[] drain(HttpRequest.BodyPublisher publisher) throws Exception {
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        CompletableFuture<Void> done = new CompletableFuture<>();
        publisher.subscribe(new Flow.Subscriber<ByteBuffer>() {
            @Override
            public void onSubscribe(Flow.Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(ByteBuffer item) {
                byte[] b = new byte[item.remaining()];
                item.get(b);
                out.write(b, 0, b.length);
            }

            @Override
            public void onError(Throwable t) {
                done.completeExceptionally(t);
            }

            @Override
            public void onComplete() {
                done.complete(null);
            }
        });
        done.get(5, TimeUnit.SECONDS);
        return out.toByteArray();
    }

    @Test
    void multipartBodyEscapesQuotesInNames() throws Exception {
        MultipartBody form = new MultipartBody().addField("a", "1").addFile("f", "we\"ird\r\n.png", "image/png", new byte[] { 1, 2 });
        String text = new String(drain(form.build()), StandardCharsets.ISO_8859_1);
        assertTrue(text.contains("filename=\"we%22ird%0D%0A.png\""), text);
        assertTrue(form.contentType().startsWith("multipart/form-data; boundary=CopyleaksBoundary"));
        assertEquals("image/png", MultipartBody.imageContentType("x.PNG"));
        assertEquals("image/jpeg", MultipartBody.imageContentType("x.jpeg"));
        assertEquals("image/x-icon", MultipartBody.imageContentType("x.ico"));
        assertEquals("application/octet-stream", MultipartBody.imageContentType("noext"));
    }
}
