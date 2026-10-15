package models.submissions;

import models.submissions.properties.SubmissionProperties;

public class CopyleaksFileOcrSubmissionModel extends CopyleaksFileSubmissionModel {
    private String langCode;

    /**
     * @param langCode   language of the text in the image,
     *                   see {@link classes.Copyleaks#getOCRSupportedLanguagesAsync()}.
     * @param base64     the image file, base64-encoded.
     * @param filename   file name with the right extension, shown in the report.
     * @param properties the scan properties.
     */
    public CopyleaksFileOcrSubmissionModel(String langCode, String base64, String filename, SubmissionProperties properties) {
        super(base64, filename, properties);
        this.langCode = langCode;
    }

    /** @return the language code of the text in the image. */
    public String getLangCode() {
        return langCode;
    }

    /** Replaces the {@code langCode} constructor argument. */
    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }
}
