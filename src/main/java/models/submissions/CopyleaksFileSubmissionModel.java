package models.submissions;

import models.submissions.properties.SubmissionProperties;

public class CopyleaksFileSubmissionModel extends CopyleaksSubmissionModel {
    private String base64;
    private String filename;

    /**
     * @param base64     the file, base64-encoded. To scan plain text, base64-encode it.
     * @param filename   file name with the right extension, shown in the report.
     * @param properties the scan properties.
     */
    public CopyleaksFileSubmissionModel(String base64, String filename, SubmissionProperties properties) {
        super(properties);
        this.base64 = base64;
        this.filename = filename;
    }

    public String getBase64() {
        return base64;
    }

    /** Replaces the {@code base64} constructor argument. */
    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getFilename() {
        return filename;
    }

    /** Replaces the {@code filename} constructor argument. */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
