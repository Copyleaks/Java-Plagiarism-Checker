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
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package models.exceptions;

import java.time.Duration;

/**
 * The server answered 429 Too Many Requests. Wait before calling again.
 */
public class RateLimitException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String responseBody;
    private final Duration retryAfter;

    /**
     * Creates the exception without response details.
     */
    public RateLimitException() {
        this(429, null, null);
    }

    /**
     * @param statusCode   the HTTP status code of the response.
     * @param responseBody the response body text, or null.
     * @param retryAfter   the wait the server asked for in its {@code Retry-After} header, or null.
     */
    public RateLimitException(int statusCode, String responseBody, Duration retryAfter) {
        super("Too many requests. Please wait before calling again."
                + (retryAfter != null ? " Retry after " + retryAfter.getSeconds() + " seconds." : ""));
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.retryAfter = retryAfter;
    }

    /**
     * @return the HTTP status code of the response (429).
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return the response body the server sent, or null when it is not known.
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * @return the wait the server asked for in its {@code Retry-After} header, or null when it sent none.
     */
    public Duration getRetryAfter() {
        return retryAfter;
    }
}
