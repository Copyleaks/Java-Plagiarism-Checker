package models.exports;

public class ExportCrawledVersion {
    /** Headers for the upload request, such as Authorization, as {@code [["name", "value"], ...]}. */
    public String[][] headers;
    /** URL to upload the data to. */
    private String endpoint;
    /** HTTP method for the upload, for example POST or PUT. */
    private String verb;

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
