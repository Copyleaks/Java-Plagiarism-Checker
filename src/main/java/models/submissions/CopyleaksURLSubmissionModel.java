package models.submissions;

import models.submissions.properties.SubmissionProperties;

public class CopyleaksURLSubmissionModel extends CopyleaksSubmissionModel {
    private String url;

    private String verb;

    private String[][] headers;

    /**
     * @param url        the URL to scan.
     * @param properties the scan properties.
     */
    public CopyleaksURLSubmissionModel(String url, SubmissionProperties properties) {
        super(properties);
        this.url = url;
    }

    /** @return the URL to scan. */
    public String getUrl() {
        return url;
    }

    /** Replaces the {@code url} constructor argument. */
    public void setUrl(String url) {
        this.url = url;
    }

    /** @return the HTTP method used to fetch the URL, or null for GET. */
    public String getVerb() {
        return verb;
    }

    /** HTTP method used to fetch the URL: GET (default), POST or PUT. */
    public void setVerb(String verb) {
        this.verb = verb;
    }

    /** @return the headers sent when fetching the URL, or null. */
    public String[][] getHeaders() {
        return headers;
    }

    /** Headers for fetching the URL, as {@code [["name", "value"], ...]}. When set, no default headers are sent. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
