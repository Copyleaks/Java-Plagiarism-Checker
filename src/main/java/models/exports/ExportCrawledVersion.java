package models.exports;

public class ExportCrawledVersion {
    /** Headers for the upload request, such as Authorization, as {@code [["name", "value"], ...]}. */
    public String[][] headers;
    private String endpoint;
    private String verb;

    /**
     * @param endpoint URL to upload the data to.
     * @param verb     HTTP method for the upload, for example POST or PUT.
     * @param headers  see {@link #headers}, or null.
     */
    public ExportCrawledVersion(String endpoint, String verb, String[][] headers) {
        this.endpoint = endpoint;
        this.verb = verb;
        this.headers = headers;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getVerb() {
        return verb;
    }

    public String[][] getHeaders() {
        return headers;
    }

}
