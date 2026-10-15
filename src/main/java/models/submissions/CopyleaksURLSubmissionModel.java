package models.submissions;

import models.submissions.properties.SubmissionProperties;

public class CopyleaksURLSubmissionModel extends CopyleaksSubmissionModel {
    private String url;

    /** HTTP method used to fetch the URL: GET (default), POST or PUT. */
    private String verb;

    /** Headers for fetching the URL, as {@code [["name", "value"], ...]}. When set, no default headers are sent. */
    private String[][] headers;

    public CopyleaksURLSubmissionModel(String url, SubmissionProperties properties) {
        super(properties);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
