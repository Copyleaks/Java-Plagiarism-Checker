package models.exceptions;

import java.time.Duration;

/** The server answered 429 Too Many Requests. Wait before calling again. */
public class RateLimitException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String responseBody;
    private final Duration retryAfter;

    public RateLimitException() {
        this(429, null, null);
    }

    public RateLimitException(int statusCode, String responseBody, Duration retryAfter) {
        super("Too many requests. Please wait before calling again."
                + (retryAfter != null ? " Retry after " + retryAfter.getSeconds() + " seconds." : ""));
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.retryAfter = retryAfter;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /** @return the response body, or null when not known. */
    public String getResponseBody() {
        return responseBody;
    }

    /** @return the wait from the {@code Retry-After} header, or null when there was none. */
    public Duration getRetryAfter() {
        return retryAfter;
    }
}
