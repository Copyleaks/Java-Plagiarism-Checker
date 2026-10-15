package models.exceptions;

/** The login token has expired, or expires within 5 minutes. Log in again. */
public class AuthExpiredException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthExpiredException() {
        super("Authentication Expired. Need to login again.");
    }
}
