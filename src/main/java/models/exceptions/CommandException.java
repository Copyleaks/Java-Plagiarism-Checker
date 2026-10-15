package models.exceptions;

/** The server answered with a non-2xx status. The response body usually holds an {@code error} object. */
public class CommandException extends Exception {

    private static final long serialVersionUID = -2201560758494317755L;

    private final int statusCode;
    private final String responseBody;

    public CommandException(String errorMessage) {
        this(errorMessage, 0, null);
    }

    public CommandException(String errorMessage, int statusCode, String responseBody) {
        super(errorMessage);
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
