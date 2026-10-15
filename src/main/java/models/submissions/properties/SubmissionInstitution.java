package models.submissions.properties;

public class SubmissionInstitution {
    /** Unique institution ID. Use the same ID to get analytics at the institution level. */
    private String id;

    public SubmissionInstitution() {
    }

    public SubmissionInstitution(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
