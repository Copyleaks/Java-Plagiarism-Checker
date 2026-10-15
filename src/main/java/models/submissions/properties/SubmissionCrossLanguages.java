package models.submissions.properties;

public class SubmissionCrossLanguages {

    /** Languages to scan the content against, see {@code Copyleaks.getAllowedCrossLanguages}. */
    private SubmissionLanguage[] languages;

    public SubmissionCrossLanguages(){}

    public SubmissionCrossLanguages(SubmissionLanguage[] languages){
        this.languages = languages;
    }

    public SubmissionLanguage[] getLanguages() {
        return languages;
    }

    public void setLanguages(SubmissionLanguage[] languages) {
        this.languages = languages;
    }

}
