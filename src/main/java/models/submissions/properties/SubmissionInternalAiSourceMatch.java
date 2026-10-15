package models.submissions.properties;

/**
 * Find sources with AI-generated text in the Copyleaks internal AI database. Matches arrive in the completed
 * webhook as {@code results.internalAIData}.
 */
public class SubmissionInternalAiSourceMatch {

    /** Default: false. */
    private Boolean enable;

    public SubmissionInternalAiSourceMatch() {
    }

    public SubmissionInternalAiSourceMatch(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
