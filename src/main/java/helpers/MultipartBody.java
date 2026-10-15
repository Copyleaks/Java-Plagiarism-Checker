package helpers;

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/** Builds a {@code multipart/form-data} body. File bytes are sent as is, not base64-encoded or copied. */
public final class MultipartBody {

    private final String boundary = "CopyleaksBoundary" + UUID.randomUUID().toString().replace("-", "");
    private final List<byte[]> chunks = new ArrayList<>();

    /** @return this builder. */
    public MultipartBody addField(String name, String value) {
        Objects.requireNonNull(value, name + " is null");
        chunks.add(utf8("--" + boundary + "\r\nContent-Disposition: form-data; name=\"" + escape(name)
                + "\"\r\n\r\n" + value + "\r\n"));
        return this;
    }

    /** @return this builder. */
    public MultipartBody addFile(String name, String filename, String contentType, byte[] content) {
        Objects.requireNonNull(content, name + " is null");
        chunks.add(utf8("--" + boundary + "\r\nContent-Disposition: form-data; name=\"" + escape(name)
                + "\"; filename=\"" + escape(filename) + "\"\r\nContent-Type: " + contentType + "\r\n\r\n"));
        chunks.add(content);
        chunks.add(utf8("\r\n"));
        return this;
    }

    /** @return the Content-Type header value, with the boundary. */
    public String contentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    /** @return a body publisher with a known length, so the request is sent with Content-Length, not chunked. */
    public HttpRequest.BodyPublisher build() {
        List<byte[]> all = new ArrayList<>(chunks);
        all.add(utf8("--" + boundary + "--\r\n"));
        long length = 0;
        for (byte[] chunk : all) {
            length += chunk.length;
        }
        return HttpRequest.BodyPublishers.fromPublisher(HttpRequest.BodyPublishers.ofByteArrays(all), length);
    }

    /** @return the image content type for the file extension, or {@code application/octet-stream}. */
    public static String imageContentType(String filename) {
        String name = filename == null ? "" : filename.toLowerCase(Locale.ROOT);
        int dot = name.lastIndexOf('.');
        switch (dot < 0 ? "" : name.substring(dot + 1)) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            case "ico":
                return "image/x-icon";
            case "heic":
                return "image/heic";
            case "heif":
                return "image/heif";
            case "tif":
            case "tiff":
                return "image/tiff";
            default:
                return "application/octet-stream";
        }
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\"", "%22").replace("\r", "%0D").replace("\n", "%0A");
    }

    private static byte[] utf8(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }
}
