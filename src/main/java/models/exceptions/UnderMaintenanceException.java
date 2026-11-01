package models.exceptions;

/** The server answered 503 Service Unavailable, usually during maintenance. */
public class UnderMaintenanceException extends CopyleaksApiException {

    private static final long serialVersionUID = 1L;

    /** Creates the exception without response details. */
    public UnderMaintenanceException() {
        this(503, null);
    }

    /**
     * @param statusCode   the HTTP status code of the response.
     * @param responseBody the response body text, or null.
     */
    public UnderMaintenanceException(int statusCode, String responseBody) {
        super("Copyleaks is Under Maintenance, please visit https://status.copyleaks.com", statusCode, responseBody);
    }
}
