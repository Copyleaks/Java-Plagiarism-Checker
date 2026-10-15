package models.submissions.Webhooks.HelperModels.ErrorModels;

/** Why a scan failed. */
public class ErrorModel {

    /** For example "invalid_request_error" or "payment_error"; may be null. */
    private String type;

    /** Machine-readable ID, for example "insufficient_credits"; may be null. */
    private String id;

    private int code;

    private String message;

    /** Documentation page of this error; may be null. */
    private String url;

    /** The parameters that caused the error; may be null. */
    private ErrorDetailsModel[] details;

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public ErrorDetailsModel[] getDetails() {
        return details;
    }
}
