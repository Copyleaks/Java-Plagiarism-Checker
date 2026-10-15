package models.submissions;

import models.submissions.properties.SubmissionProperties;

public class CopyleaksSubmissionModel {
    private SubmissionProperties properties;

    public CopyleaksSubmissionModel(SubmissionProperties properties) {
        this.properties = properties;
    }

    public SubmissionProperties getProperties() {
        return properties;
    }

    public void setProperties(SubmissionProperties properties) {
        this.properties = properties;
    }

}
