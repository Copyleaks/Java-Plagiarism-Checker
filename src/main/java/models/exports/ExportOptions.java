package models.exports;

public class ExportOptions {
    private String endpoint;

    private String verb;

    private String[][] headers = null;

    public ExportOptions() {}

    public ExportOptions(String endpoint, String verb, String[][] headers) {
        this.endpoint = endpoint;
        this.verb = verb;
        this.headers = headers;
    }

    public String getEndpoint() {
        return endpoint;
    }

    /** URL to upload the data to. */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getVerb() {
        return verb;
    }

    /** HTTP method for the upload, for example POST or PUT. */
    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String[][] getHeaders() {
        return headers;
    }

    /** Headers for the upload request, such as Authorization, as {@code [["name", "value"], ...]}. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
