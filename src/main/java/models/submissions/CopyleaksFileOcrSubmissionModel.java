package models.submissions;

import models.submissions.properties.SubmissionProperties;

public class CopyleaksFileOcrSubmissionModel extends CopyleaksFileSubmissionModel {
    /** Language of the text in the image, see {@link classes.Copyleaks#getOCRSupportedLanguagesAsync()}. */
    private String langCode;

    public CopyleaksFileOcrSubmissionModel(String langCode, String base64, String filename, SubmissionProperties properties) {
        super(base64, filename, properties);
        this.langCode = langCode;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }
}
