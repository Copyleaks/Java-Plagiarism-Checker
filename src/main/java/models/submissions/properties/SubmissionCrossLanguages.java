package models.submissions.properties;

public class SubmissionCrossLanguages {

    private SubmissionLanguage[] languages;

    public SubmissionCrossLanguages(){}

    public SubmissionCrossLanguages(SubmissionLanguage[] languages){
        this.languages = languages;
    }

    public SubmissionLanguage[] getLanguages() {
        return languages;
    }

    /** Languages to scan the content against, see {@code Copyleaks.getAllowedCrossLanguages}. */
    public void setLanguages(SubmissionLanguage[] languages) {
        this.languages = languages;
    }

}
