package models.exports;

public class ExportResults {
    /** Result ID, from the completed webhook. */
    private String id;

    /** URL to upload the data to. */
    private String endpoint;

    /** HTTP method for the upload, for example POST or PUT. */
    private String verb;

    /** Headers for the upload request, such as Authorization, as {@code [["name", "value"], ...]}. */
    private String[][] headers = null;

    public ExportResults(String id, String endpoint, String verb, String[][] headers) {
        this.id = id;
        this.endpoint = endpoint;
        this.verb = verb;
        this.headers = headers;
    }

    public String getId() {
        return id;
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
