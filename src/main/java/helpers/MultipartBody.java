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

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * Builds a {@code multipart/form-data} request body. File contents are sent as they are, without
 * base64 encoding and without being copied into one large buffer.
 */
public final class MultipartBody {

    private final String boundary = "CopyleaksBoundary" + UUID.randomUUID().toString().replace("-", "");
    private final List<byte[]> chunks = new ArrayList<>();

    /**
     * Adds a text field.
     * @param name  the field name.
     * @param value the field value.
     * @return this builder.
     */
    public MultipartBody addField(String name, String value) {
        Objects.requireNonNull(value, name + " is null");
        chunks.add(utf8("--" + boundary + "\r\nContent-Disposition: form-data; name=\"" + escape(name)
                + "\"\r\n\r\n" + value + "\r\n"));
        return this;
    }

    /**
     * Adds a file field.
     * @param name        the field name.
     * @param filename    the file name sent in the part header.
     * @param contentType the part content type.
     * @param content     the file bytes.
     * @return this builder.
     */
    public MultipartBody addFile(String name, String filename, String contentType, byte[] content) {
        Objects.requireNonNull(content, name + " is null");
        chunks.add(utf8("--" + boundary + "\r\nContent-Disposition: form-data; name=\"" + escape(name)
                + "\"; filename=\"" + escape(filename) + "\"\r\nContent-Type: " + contentType + "\r\n\r\n"));
        chunks.add(content);
        chunks.add(utf8("\r\n"));
        return this;
    }

    /**
     * @return the Content-Type header value, with the boundary.
     */
    public String contentType() {
        return "multipart/form-data; boundary=" + boundary;
    }

    /**
     * @return a body publisher for the parts added so far.
     */
    public HttpRequest.BodyPublisher build() {
        List<byte[]> all = new ArrayList<>(chunks);
        all.add(utf8("--" + boundary + "--\r\n"));
        return HttpRequest.BodyPublishers.ofByteArrays(all);
    }

    /**
     * Guesses an image content type from a file name extension.
     * @param filename the file name.
     * @return the content type, or {@code application/octet-stream} when the extension is unknown.
     */
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
