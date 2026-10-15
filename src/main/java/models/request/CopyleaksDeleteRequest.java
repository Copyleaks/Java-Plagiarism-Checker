package models.request;

/** Request body for deleting scans. */
public class CopyleaksDeleteRequest {

    private ScansRequests[] scans;

    private boolean purge;

    private String completionWebhook;

    private String[][] headers;

    /**
     * @param scans             the scans to delete.
     * @param purge             also remove the scans from the Copyleaks internal database, so they stop matching
     *                          other scans and are no longer available as results of earlier scans.
     * @param completionWebhook URL called with POST, without a body, once the removal has completed; may be null.
     */
    public CopyleaksDeleteRequest(ScansRequests[] scans, boolean purge, String completionWebhook) {
        this.scans = scans;
        this.purge = purge;
        this.completionWebhook = completionWebhook;
    }

    /** @return the scans to delete. */
    public ScansRequests[] getScans() {
        return scans;
    }

    /** @return true when the scans are purged. */
    public boolean isPurge() {
        return purge;
    }

    /** @return the completion webhook URL, or null. */
    public String getCompletionWebhook() {
        return completionWebhook;
    }

    /** Replaces the {@code completionWebhook} constructor argument. */
    public void setCompletionWebhook(String completionWebhook) {
        this.completionWebhook = completionWebhook;
    }

    /**
     * Old name of {@link #getCompletionWebhook()}.
     * @deprecated Use {@link #getCompletionWebhook()}.
     */
    @Deprecated
    public String getUri() {
        return completionWebhook;
    }

    /** @return the completion webhook headers, or null. */
    public String[][] getHeaders() {
        return headers;
    }

    /** Headers added to the completion webhook request, as {@code [["name", "value"], ...]}. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
