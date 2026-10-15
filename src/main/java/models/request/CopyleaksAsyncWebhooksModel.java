package models.request;

/** Where an asynchronous detection (AI Image async, AI Video) sends its result. */
public class CopyleaksAsyncWebhooksModel {

    /** The URL that receives the result with a POST request. */
    private String url;

    /** Optional headers added to the webhook request, as {@code [["name", "value"], ...]}. */
    private String[][] headers;

    public CopyleaksAsyncWebhooksModel(String url) {
        this.url = url;
    }

    public CopyleaksAsyncWebhooksModel(String url, String[][] headers) {
        this.url = url;
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[][] getHeaders() {
        return headers;
    }

    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
