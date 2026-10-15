package models.submissions.properties;

/**
 * Citation Checker: check the references in the text. The summary arrives in the completed webhook as
 * {@code referencesValidation}.
 */
public class SubmissionReferences {

    private Boolean validate;

    /** Creates the settings with the server default. */
    public SubmissionReferences() {
    }

    /** @param validate see {@link #setValidate(Boolean)}. */
    public SubmissionReferences(Boolean validate) {
        this.validate = validate;
    }

    /** @return true when references are validated, or null for the server default. */
    public Boolean getValidate() {
        return validate;
    }

    /** Detect, parse and validate every reference. Default: false. */
    public void setValidate(Boolean validate) {
        this.validate = validate;
    }
}
