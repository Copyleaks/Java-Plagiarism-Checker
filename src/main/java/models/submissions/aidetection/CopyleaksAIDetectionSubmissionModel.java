package models.submissions.aidetection;

public class CopyleaksAIDetectionSubmissionModel {

    private String text;

    /** Return mock results without using credits. */
    private boolean sandbox;

    public CopyleaksAIDetectionSubmissionModel(String text) {
        this(text, false);
    }

    public CopyleaksAIDetectionSubmissionModel(String text, boolean sandbox) {
        this.text = text;
        this.sandbox = sandbox;
    }

    /** AI Logic: explain the patterns behind the result, in {@code explain}. Six languages only. Default: false. */
    private Boolean explain;

    /** 1 = copied from an AI tool unedited, 2 (default) = minor changes, 3 = heavily modified. */
    private Integer sensitivity;

    public Boolean getExplain() {
        return explain;
    }

    public void setExplain(Boolean explain) {
        this.explain = explain;
    }

    public Integer getSensitivity() {
        return sensitivity;
    }

    /** @throws IllegalArgumentException if the value is not null and not between 1 and 3. */
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

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }
}
