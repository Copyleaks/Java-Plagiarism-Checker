package models.exceptions;

/**
 * An error response from the Copyleaks API. Catch it to handle {@link CommandException},
 * {@link RateLimitException} and {@link UnderMaintenanceException} together.
 */
public abstract class CopyleaksApiException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String responseBody;

    /**
     * @param message      the exception message.
     * @param statusCode   the HTTP status code of the response, or 0 when not known.
     * @param responseBody the response body text, or null.
     */
    protected CopyleaksApiException(String message, int statusCode, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    /** @return the HTTP status code, or 0 when not known. */
    public int getStatusCode() {
        return statusCode;
    }

    /** @return the response body, or null when not known. */
    public String getResponseBody() {
        return responseBody;
    }
}
