package models.submissions.properties;

public class SubmissionInstitution {
    private String id;

    /** Creates an empty institution object. Set the ID with {@link #setId(String)}. */
    public SubmissionInstitution() {
    }

    /** @param id see {@link #setId(String)}. */
    public SubmissionInstitution(String id) {
        this.id = id;
    }

    /** Unique institution ID. Use the same ID to get analytics at the institution level. */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the institution ID. */
    public String getId() {
        return id;
    }
}
