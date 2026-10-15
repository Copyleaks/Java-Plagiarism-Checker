package models.submissions.properties;

public class SubmissionCourse {
    private String id;

    /** Creates an empty course object. Set the ID with {@link #setId(String)}. */
    public SubmissionCourse() {
    }

    /** @param id see {@link #setId(String)}. */
    public SubmissionCourse(String id) {
        this.id = id;
    }

    /** Unique course ID. Use the same ID to get analytics at the course level. */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the course ID. */
    public String getId() {
        return id;
    }
}
