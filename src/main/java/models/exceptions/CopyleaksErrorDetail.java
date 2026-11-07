package models.exceptions;

import java.io.Serializable;

/** A request parameter that caused an API error: one item of {@code error.details} in an error response. */
public final class CopyleaksErrorDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String param;
    private final String message;

    /**
     * @param param   the parameter name, or null.
     * @param message what is wrong with the parameter, or null.
     */
    public CopyleaksErrorDetail(String param, String message) {
        this.param = param;
        this.message = message;
    }

    /** @return the parameter name, or null when not sent. */
    public String getParam() {
        return param;
    }

    /** @return what is wrong with the parameter, or null when not sent. */
    public String getMessage() {
        return message;
    }
}
