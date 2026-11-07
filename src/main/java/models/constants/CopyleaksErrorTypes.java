package models.constants;

/**
 * Values of {@code error.type} in an API error response ({@code CopyleaksApiException.getErrorType()}).
 * See https://docs.copyleaks.com/using-the-apis/api-errors/. Other values may be added later.
 */
public final class CopyleaksErrorTypes {
    /** The request has missing or invalid parameters. */
    public static final String INVALID_REQUEST_ERROR = "invalid_request_error";
    /** The credentials or the access token are missing, invalid or expired. */
    public static final String AUTHENTICATION_ERROR = "authentication_error";
    /** The account has a billing problem, for example not enough credits. */
    public static final String PAYMENT_ERROR = "payment_error";
    /** The server failed to process the request. */
    public static final String API_ERROR = "api_error";

    private CopyleaksErrorTypes() {
    }
}
