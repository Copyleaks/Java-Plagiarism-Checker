package models.submissions.properties;

public class SubmissionAssignment {
    /** Unique assignment ID. Use the same ID to get analytics at the assignment level. */
    private String id;

    public SubmissionAssignment() {
    }

    public SubmissionAssignment(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
