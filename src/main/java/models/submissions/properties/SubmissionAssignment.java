package models.submissions.properties;

public class SubmissionAssignment {
    private String id;

    /** Creates an empty assignment object. Set the ID with {@link #setId(String)}. */
    public SubmissionAssignment() {
    }

    /** @param id see {@link #setId(String)}. */
    public SubmissionAssignment(String id) {
        this.id = id;
    }

    /** Unique assignment ID. Use the same ID to get analytics at the assignment level. */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the assignment ID. */
    public String getId() {
        return id;
    }
}
