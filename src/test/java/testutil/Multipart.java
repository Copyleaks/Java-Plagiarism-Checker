package testutil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Parses a multipart/form-data body for assertions.
 */
public final class Multipart {

    /** One part of the body. */
    public static final class Part {
        public final Map<String, String> headers;
        public final byte[] content;

        Part(Map<String, String> headers, byte[] content) {
            this.headers = headers;
            this.content = content;
        }

        public String text() {
            return new String(content, StandardCharsets.UTF_8);
        }

        public String disposition() {
            return headers.get("content-disposition");
        }

        public String contentType() {
            return headers.get("content-type");
        }
    }

    private Multipart() {
    }

    /**
     * @return the parts by field name, in body order.
     */
    public static Map<String, Part> parse(String contentType, byte[] body) {
        String marker = "boundary=";
        int at = contentType.indexOf(marker);
        if (!contentType.startsWith("multipart/form-data") || at < 0) {
            throw new AssertionError("not multipart/form-data: " + contentType);
        }
        byte[] delimiter = ("--" + contentType.substring(at + marker.length())).getBytes(StandardCharsets.ISO_8859_1);
        Map<String, Part> parts = new LinkedHashMap<>();
        int pos = indexOf(body, delimiter, 0);
        if (pos != 0) {
            throw new AssertionError("body does not start with the boundary");
        }
        while (true) {
            int afterDelim = pos + delimiter.length;
            if (body[afterDelim] == '-' && body[afterDelim + 1] == '-') {
                String tail = new String(body, afterDelim + 2, body.length - afterDelim - 2, StandardCharsets.ISO_8859_1);
                if (!tail.equals("\r\n")) {
                    throw new AssertionError("unexpected content after the closing boundary: " + tail);
                }
                return parts;
            }
            int headersStart = afterDelim + 2; // skip CRLF
            int headersEnd = indexOf(body, "\r\n\r\n".getBytes(StandardCharsets.ISO_8859_1), headersStart);
            String headerText = new String(body, headersStart, headersEnd - headersStart, StandardCharsets.UTF_8);
            Map<String, String> headers = new LinkedHashMap<>();
            for (String line : headerText.split("\r\n")) {
                int colon = line.indexOf(':');
                headers.put(line.substring(0, colon).trim().toLowerCase(Locale.ROOT), line.substring(colon + 1).trim());
            }
            int contentStart = headersEnd + 4;
            int next = indexOf(body, delimiter, contentStart);
            byte[] content = Arrays.copyOfRange(body, contentStart, next - 2); // strip CRLF before the boundary
            String disposition = headers.get("content-disposition");
            int nameAt = disposition.indexOf("name=\"");
            String name = disposition.substring(nameAt + 6, disposition.indexOf('"', nameAt + 6));
            if (parts.put(name, new Part(headers, content)) != null) {
                throw new AssertionError("duplicate part " + name);
            }
            pos = next;
        }
    }

    private static int indexOf(byte[] data, byte[] pattern, int from) {
        outer:
        for (int i = from; i <= data.length - pattern.length; i++) {
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    continue outer;
                }
            }
            return i;
        }
        throw new AssertionError("pattern not found");
    }
}
