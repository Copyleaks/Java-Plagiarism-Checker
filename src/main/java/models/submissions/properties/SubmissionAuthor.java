package models.submissions.properties;

public class SubmissionAuthor {
    private String id;

    /** Creates an empty author object. Set the ID with {@link #setId(String)}. */
    public SubmissionAuthor() {
    }

    /** @param id see {@link #setId(String)}. */
    public SubmissionAuthor(String id) {
        this.id = id;
    }

    /** Unique author ID. Use the same ID for the same author so Copyleaks can learn their writing patterns. */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the author ID. */
    public String getId() {
        return id;
    }
}
