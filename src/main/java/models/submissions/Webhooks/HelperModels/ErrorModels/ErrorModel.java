package models.submissions.Webhooks.HelperModels.ErrorModels;

/** Why a scan failed. */
public class ErrorModel {

    private String type;

    private String id;

    private int code;

    private String message;

    private String url;

    private ErrorDetailsModel[] details;

    /** Error type, for example "invalid_request_error" or "payment_error"; may be null. */
    public String getType() {
        return type;
    }

    /** Machine-readable ID, for example "insufficient_credits"; may be null. */
    public String getId() {
        return id;
    }

    /** Error code. */
    public int getCode() {
        return code;
    }

    /** Human-readable error message. */
    public String getMessage() {
        return message;
    }

    /** Documentation page of this error; may be null. */
    public String getUrl() {
        return url;
    }

    /** The parameters that caused the error; may be null. */
    public ErrorDetailsModel[] getDetails() {
        return details;
    }
}
