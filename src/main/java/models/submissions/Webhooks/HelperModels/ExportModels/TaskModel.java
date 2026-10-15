package models.submissions.Webhooks.HelperModels.ExportModels;

/** One upload task of an export. */
public class TaskModel {

    private String endpoint;

    private boolean isHealthy;

    private int httpStatusCode;

    /** Endpoint address of the export task. */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Misspelled name of {@link #getEndpoint()}.
     * @deprecated Use {@link #getEndpoint()}.
     */
    @Deprecated
    public String getEndpont() {
        return endpoint;
    }

    /** False when the task hit an internal error on the Copyleaks side. */
    public boolean isIsHealthy() {
        return isHealthy;
    }

    /** The status code your server returned; 0 when the task was not healthy. */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
