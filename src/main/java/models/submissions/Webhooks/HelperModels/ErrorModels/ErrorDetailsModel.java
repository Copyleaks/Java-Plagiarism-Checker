package models.submissions.Webhooks.HelperModels.ErrorModels;

/** A parameter that caused an error. */
public class ErrorDetailsModel {

    private String param;

    /** What is wrong with the parameter. */
    private String message;

    public String getParam() {
        return param;
    }

    public String getMessage() {
        return message;
    }
}
