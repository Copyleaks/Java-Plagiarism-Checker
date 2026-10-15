package models.submissions;

import models.submissions.properties.SubmissionProperties;

public class CopyleaksFileSubmissionModel extends CopyleaksSubmissionModel {
    /** The file, base64-encoded. To scan plain text, base64-encode it. */
    private String base64;
    /** File name with the right extension, shown in the report. */
    private String filename;

    public CopyleaksFileSubmissionModel(String base64, String filename, SubmissionProperties properties) {
        super(properties);
        this.base64 = base64;
        this.filename = filename;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
