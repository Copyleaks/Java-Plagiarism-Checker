package models.submissions.aidetection;

public class CopyleaksAIDetectionSubmissionModel {

    private String text;

    private boolean sandbox;

    public CopyleaksAIDetectionSubmissionModel(String text) {
        this(text, false);
    }

    public CopyleaksAIDetectionSubmissionModel(String text, boolean sandbox) {
        this.text = text;
        this.sandbox = sandbox;
    }

    private Boolean explain;

    private Integer sensitivity;

    /** @return true when AI Logic is requested, or null for the server default. */
    public Boolean getExplain() {
        return explain;
    }

    /** AI Logic: explain the patterns behind the result, in {@code explain}. Six languages only. Default: false. */
    public void setExplain(Boolean explain) {
        this.explain = explain;
    }

    /** @return the sensitivity, or null for the server default. */
    public Integer getSensitivity() {
        return sensitivity;
    }

    /**
     * 1 = copied from an AI tool unedited, 2 (default) = minor changes, 3 = heavily modified.
     * @throws IllegalArgumentException if the value is not null and not between 1 and 3.
     */
    public void setSensitivity(Integer sensitivity) {
        if (sensitivity != null && (sensitivity < 1 || sensitivity > 3)) {
            throw new IllegalArgumentException("Sensitivity must be between 1 and 3 (inclusive)");
        }
        this.sensitivity = sensitivity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    /** Return mock results without using credits. */
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }
}
