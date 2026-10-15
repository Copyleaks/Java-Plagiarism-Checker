package models.submissions.Webhooks.HelperModels.ErrorModels;

/** A parameter that caused an error. */
public class ErrorDetailsModel {

    private String param;

    private String message;

    /** Parameter name. */
    public String getParam() {
        return param;
    }

    /** What is wrong with the parameter. */
    public String getMessage() {
        return message;
    }
}
