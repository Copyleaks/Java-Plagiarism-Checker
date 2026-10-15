package models.submissions.properties;

public class SubmissionCourse {
    /** Unique course ID. Use the same ID to get analytics at the course level. */
    private String id;

    public SubmissionCourse() {
    }

    public SubmissionCourse(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
