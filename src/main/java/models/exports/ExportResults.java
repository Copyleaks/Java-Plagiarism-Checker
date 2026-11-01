package models.exports;

public class ExportResults {
    private String id;

    private String endpoint;

    private String verb;

    private String[][] headers = null;

    /**
     * @param id       result ID, from the completed webhook.
     * @param endpoint URL to upload the data to.
     * @param verb     HTTP method for the upload, for example POST or PUT.
     * @param headers  headers for the upload request, such as Authorization, as {@code [["name", "value"], ...]},
     *                 or null.
     */
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
