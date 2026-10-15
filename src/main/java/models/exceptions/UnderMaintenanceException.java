package models.exceptions;

/** The server answered 503 Service Unavailable, usually during maintenance. */
public class UnderMaintenanceException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String responseBody;

    public UnderMaintenanceException() {
        this(503, null);
    }

    public UnderMaintenanceException(int statusCode, String responseBody) {
        super("Copyleaks is Under Maintenance, please visit https://status.copyleaks.com");
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /** @return the response body, or null when not known. */
    public String getResponseBody() {
        return responseBody;
    }
}
