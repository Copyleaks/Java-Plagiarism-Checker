package models.submissions.properties;

public class SubmissionWebhooks {

    /**
     * Called when the scan status changes. {STATUS} in the URL is replaced with completed, error,
     * creditschecked or indexed. https://docs.copyleaks.com/reference/data-types/authenticity/webhooks/overview
     */
    private String status;
    /** Called for each new result while the scan runs, so a live report can load gradually. */
    private String newResult = null;

    public SubmissionWebhooks(String status) {
        this.status = status;
    }

    /** As {@code [["name", "value"], ...]}. */
    private String[][] statusHeaders = null;

    /** As {@code [["name", "value"], ...]}. */
    private String[][] newResultHeaders = null;

    public void setStatus(String status) {
        this.status = status;
    }

    public String[][] getStatusHeaders() {
        return statusHeaders;
    }

    public void setStatusHeaders(String[][] statusHeaders) {
        this.statusHeaders = statusHeaders;
    }

    public String[][] getNewResultHeaders() {
        return newResultHeaders;
    }

    public void setNewResultHeaders(String[][] newResultHeaders) {
        this.newResultHeaders = newResultHeaders;
    }

    public String getStatus() {
        return status;
    }

    public String getNewResult() {
        return newResult;
    }

    public void setNewResult(String newResult) {
        this.newResult = newResult;
    }
}
