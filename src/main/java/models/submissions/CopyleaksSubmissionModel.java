package models.submissions;

import models.submissions.properties.SubmissionProperties;

public class CopyleaksSubmissionModel {
    private SubmissionProperties properties;

    /** @param properties the scan properties. */
    public CopyleaksSubmissionModel(SubmissionProperties properties) {
        this.properties = properties;
    }

    /** @return the scan properties. */
    public SubmissionProperties getProperties() {
        return properties;
    }

    /** Replaces the {@code properties} constructor argument. */
    public void setProperties(SubmissionProperties properties) {
        this.properties = properties;
    }

}
