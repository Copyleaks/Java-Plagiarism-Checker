package models.submissions.aidetection;

public class CopyleaksNaturalLanguageSubmissionModel extends CopyleaksAIDetectionSubmissionModel {

    private String language;

    public CopyleaksNaturalLanguageSubmissionModel(String text) {
        this(text, false, null);
    }

    public CopyleaksNaturalLanguageSubmissionModel(String text, boolean sandbox) {
        this(text, sandbox, null);
    }

    public CopyleaksNaturalLanguageSubmissionModel(String text, boolean sandbox, String language) {
        super(text, sandbox);
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    /** Language code of the text, or null to detect it. */
    public void setLanguage(String language) {
        this.language = language;
    }
}
