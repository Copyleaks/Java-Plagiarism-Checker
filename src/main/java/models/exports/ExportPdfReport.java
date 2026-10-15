package models.exports;

public class ExportPdfReport {
    /** URL to upload the data to. */
    private String endpoint;

    /** HTTP method for the upload, for example POST or PUT. */
    private String verb;

    /** Headers for the upload request, such as Authorization, as {@code [["name", "value"], ...]}. */
    private String[][] headers = null;

    public ExportPdfReport() {}

    public ExportPdfReport(String endpoint, String verb, String[][] headers) {
        this.endpoint = endpoint;
        this.verb = verb;
        this.headers = headers;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String[][] getHeaders() {
        return headers;
    }

    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
