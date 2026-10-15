package models.submissions.Webhooks.HelperModels.ExportModels;

/** One upload task of an export. */
public class TaskModel {

    private String endpoint;

    /** False when the task hit an internal error on the Copyleaks side. */
    private boolean isHealthy;

    /** The status code your server returned; 0 when the task was not healthy. */
    private int httpStatusCode;

    public String getEndpoint() {
        return endpoint;
    }

    /** @deprecated Misspelled. Use {@link #getEndpoint()}. */
    @Deprecated
    public String getEndpont() {
        return endpoint;
    }

    public boolean isIsHealthy() {
        return isHealthy;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
