package models.submissions.properties;

/**
 * Find sources with AI-generated text in the Copyleaks internal AI database. Matches arrive in the completed
 * webhook as {@code results.internalAIData}.
 */
public class SubmissionInternalAiSourceMatch {

    private Boolean enable;

    /** Creates the settings with the server default. */
    public SubmissionInternalAiSourceMatch() {
    }

    /** @param enable see {@link #setEnable(Boolean)}. */
    public SubmissionInternalAiSourceMatch(Boolean enable) {
        this.enable = enable;
    }

    /** @return true when the feature is on, or null for the server default. */
    public Boolean getEnable() {
        return enable;
    }

    /** Turns the feature on. Default: false. */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
