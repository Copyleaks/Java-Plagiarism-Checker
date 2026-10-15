package models.submissions.properties;

public class SubmissionAuthor {
    /** Unique author ID. Use the same ID for the same author so Copyleaks can learn their writing patterns. */
    private String id;

    public SubmissionAuthor() {
    }

    public SubmissionAuthor(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
