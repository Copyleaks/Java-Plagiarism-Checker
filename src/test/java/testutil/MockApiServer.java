package testutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * A local HTTP server that records every request and answers with queued replies.
 */
public final class MockApiServer implements AutoCloseable {

    /** One recorded request. */
    public static final class Recorded {
        public final String method;
        public final String rawPath;
        public final String rawQuery;
        public final Map<String, List<String>> headers;
        public final byte[] body;

        Recorded(String method, String rawPath, String rawQuery, Map<String, List<String>> headers, byte[] body) {
            this.method = method;
            this.rawPath = rawPath;
            this.rawQuery = rawQuery;
            this.headers = headers;
            this.body = body;
        }

        public String header(String name) {
            List<String> v = headers.get(name.toLowerCase(Locale.ROOT));
            return v == null || v.isEmpty() ? null : v.get(0);
        }

        public String bodyText() {
            return new String(body, StandardCharsets.UTF_8);
        }

        public JsonElement json() {
            return JsonParser.parseString(bodyText());
        }
    }

    /** A queued reply. */
    public static final class Reply {
        final int status;
        final String body;
        final Map<String, String> headers = new LinkedHashMap<>();
        long delayMs;

        Reply(int status, String body) {
            this.status = status;
            this.body = body;
        }
    }

    private final HttpServer server;
    private final ExecutorService executor;
    private final List<Recorded> requests = Collections.synchronizedList(new ArrayList<>());
    private final Deque<Reply> replies = new ArrayDeque<>();
    private Reply fallback = new Reply(200, "{}");

    public MockApiServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
        executor = Executors.newCachedThreadPool(r -> {
            Thread t = new Thread(r, "mock-api-server");
            t.setDaemon(true);
            return t;
        });
        server.setExecutor(executor);
        server.createContext("/", this::handle);
        server.start();
    }

    public String url() {
        return "http://127.0.0.1:" + server.getAddress().getPort();
    }

    /** Queues a reply. Header pairs are name, value, name, value... */
    public synchronized MockApiServer reply(int status, String body, String... headerPairs) {
        replies.add(build(status, body, 0, headerPairs));
        return this;
    }

    /** Queues a reply that is sent after a delay. */
    public synchronized MockApiServer replyAfter(long delayMs, int status, String body) {
        replies.add(build(status, body, delayMs));
        return this;
    }

    /** Sets the reply used when the queue is empty. */
    public synchronized MockApiServer always(int status, String body, String... headerPairs) {
        fallback = build(status, body, 0, headerPairs);
        return this;
    }

    private static Reply build(int status, String body, long delayMs, String... headerPairs) {
        Reply r = new Reply(status, body);
        r.delayMs = delayMs;
        for (int i = 0; i + 1 < headerPairs.length; i += 2) {
            r.headers.put(headerPairs[i], headerPairs[i + 1]);
        }
        return r;
    }

    public List<Recorded> requests() {
        synchronized (requests) {
            return new ArrayList<>(requests);
        }
    }

    public Recorded only() {
        List<Recorded> all = requests();
        if (all.size() != 1) {
            throw new AssertionError("expected exactly 1 request, got " + all.size());
        }
        return all.get(0);
    }

    private void handle(HttpExchange exchange) throws IOException {
        byte[] body;
        try (InputStream in = exchange.getRequestBody()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            in.transferTo(bos);
            body = bos.toByteArray();
        }
        Map<String, List<String>> headers = new LinkedHashMap<>();
        exchange.getRequestHeaders().forEach((k, v) -> headers.put(k.toLowerCase(Locale.ROOT), new ArrayList<>(v)));
        requests.add(new Recorded(exchange.getRequestMethod(), exchange.getRequestURI().getRawPath(),
                exchange.getRequestURI().getRawQuery(), headers, body));
        Reply reply;
        synchronized (this) {
            reply = replies.isEmpty() ? fallback : replies.poll();
        }
        if (reply.delayMs > 0) {
            try {
                Thread.sleep(reply.delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        byte[] out = reply.body == null ? new byte[0] : reply.body.getBytes(StandardCharsets.UTF_8);
        reply.headers.forEach((k, v) -> exchange.getResponseHeaders().add(k, v));
        if (!reply.headers.containsKey("Content-Type")) {
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        }
        try {
            exchange.sendResponseHeaders(reply.status, out.length == 0 ? -1 : out.length);
            if (out.length > 0) {
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(out);
                }
            }
        } catch (IOException ignored) {
            // the client gave up (for example after a timeout)
        } finally {
            exchange.close();
        }
    }

    private boolean closed;

    @Override
    public synchronized void close() {
        if (!closed) {
            closed = true;
            server.stop(0);
            executor.shutdownNow();
        }
    }
}
