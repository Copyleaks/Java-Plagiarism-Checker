package models.submissions.properties;

/**
 * Citation Checker: check the references in the text. The summary arrives in the completed webhook as
 * {@code referencesValidation}.
 */
public class SubmissionReferences {

    /** Detect, parse and validate every reference. Default: false. */
    private Boolean validate;

    public SubmissionReferences() {
    }

    public SubmissionReferences(Boolean validate) {
        this.validate = validate;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }
}
