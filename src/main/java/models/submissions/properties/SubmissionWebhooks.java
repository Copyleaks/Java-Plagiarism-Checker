package models.submissions.properties;

public class SubmissionWebhooks {

    private String status;
    private String newResult = null;

    /** @param status see {@link #setStatus(String)}. */
    public SubmissionWebhooks(String status) {
        this.status = status;
    }

    private String[][] statusHeaders = null;

    private String[][] newResultHeaders = null;

    /**
     * Called when the scan status changes. {STATUS} in the URL is replaced with completed, error,
     * creditschecked or indexed. https://docs.copyleaks.com/reference/data-types/authenticity/webhooks/overview
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return the status webhook headers, or null. */
    public String[][] getStatusHeaders() {
        return statusHeaders;
    }

    /** Headers added to the status webhook request, as {@code [["name", "value"], ...]}. */
    public void setStatusHeaders(String[][] statusHeaders) {
        this.statusHeaders = statusHeaders;
    }

    /** @return the new result webhook headers, or null. */
    public String[][] getNewResultHeaders() {
        return newResultHeaders;
    }

    /** Headers added to the new result webhook request, as {@code [["name", "value"], ...]}. */
    public void setNewResultHeaders(String[][] newResultHeaders) {
        this.newResultHeaders = newResultHeaders;
    }

    /** @return the status webhook URL. */
    public String getStatus() {
        return status;
    }

    public String getNewResult() {
        return newResult;
    }

    /** Called for each new result while the scan runs, so a live report can load gradually. */
    public void setNewResult(String newResult) {
        this.newResult = newResult;
    }
}
