package models.submissions.properties;

/** PDF report colors, as HEX strings such as "#FE5758". */
public class SubmissionPdfColors {

    private String mainStrip;
    private String titles;
    private String identical;
    private String minorChanges;
    private String relatedMeaning;

    private String aiDetection;

    private String writingFeedback;

    /** @return the AI content detection highlight color, or null. */
    public String getAiDetection() {
        return aiDetection;
    }

    /** Highlight color for AI content detection. Default: #FE5758. */
    public void setAiDetection(String aiDetection) {
        this.aiDetection = aiDetection;
    }

    /** @return the writing feedback highlight color, or null. */
    public String getWritingFeedback() {
        return writingFeedback;
    }

    /** Highlight color for writing feedback (Grammar Checker). Default: #FF9A02. */
    public void setWritingFeedback(String writingFeedback) {
        this.writingFeedback = writingFeedback;
    }

    public String getMainStrip() {
        return mainStrip;
    }

    /** Color of the strip in the report header. */
    public void setMainStrip(String mainStrip) {
        this.mainStrip = mainStrip;
    }

    public String getTitles() {
        return titles;
    }

    /** Color of the titles in the report. */
    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getIdentical() {
        return identical;
    }

    /** Highlight color for identical matches. */
    public void setIdentical(String identical) {
        this.identical = identical;
    }

    public String getMinorChanges() {
        return minorChanges;
    }

    /** Highlight color for minor-changes matches. */
    public void setMinorChanges(String minorChanges) {
        this.minorChanges = minorChanges;
    }

    public String getRelatedMeaning() {
        return relatedMeaning;
    }

    /** Highlight color for related-meaning matches. */
    public void setRelatedMeaning(String relatedMeaning) {
        this.relatedMeaning = relatedMeaning;
    }
    
}
