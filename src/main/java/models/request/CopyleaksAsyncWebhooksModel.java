package models.request;

/** Where an asynchronous detection (AI Image async, AI Video) sends its result. */
public class CopyleaksAsyncWebhooksModel {

    private String url;

    private String[][] headers;

    /** @param url URL that receives the result with a POST request. */
    public CopyleaksAsyncWebhooksModel(String url) {
        this.url = url;
    }

    /**
     * @param url     URL that receives the result with a POST request.
     * @param headers optional headers added to the webhook request, as {@code [["name", "value"], ...]}.
     */
    public CopyleaksAsyncWebhooksModel(String url, String[][] headers) {
        this.url = url;
        this.headers = headers;
    }

    /** @return the webhook URL. */
    public String getUrl() {
        return url;
    }

    /** Replaces the {@code url} constructor argument. */
    public void setUrl(String url) {
        this.url = url;
    }

    /** @return the webhook headers, or null. */
    public String[][] getHeaders() {
        return headers;
    }

    /** Replaces the {@code headers} constructor argument. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
