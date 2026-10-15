package models.request;

public class CopyleaksDeleteRequest {

    private ScansRequests[] scans;

    /** Also remove the scans from the Copyleaks internal database, so they stop matching other scans. */
    private boolean purge;

    /** Optional URL called with POST, without a body, once the removal has completed. */
    private String completionWebhook;

    /** Headers added to the completion webhook request, as {@code [["name", "value"], ...]}. */
    private String[][] headers;

    public CopyleaksDeleteRequest(ScansRequests[] scans, boolean purge, String completionWebhook) {
        this.scans = scans;
        this.purge = purge;
        this.completionWebhook = completionWebhook;
    }

    public ScansRequests[] getScans() {
        return scans;
    }

    public boolean isPurge() {
        return purge;
    }

    public String getCompletionWebhook() {
        return completionWebhook;
    }

    public void setCompletionWebhook(String completionWebhook) {
        this.completionWebhook = completionWebhook;
    }

    /** @deprecated Use {@link #getCompletionWebhook()}. */
    @Deprecated
    public String getUri() {
        return completionWebhook;
    }

    public String[][] getHeaders() {
        return headers;
    }

    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
