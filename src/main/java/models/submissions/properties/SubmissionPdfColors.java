package models.submissions.properties;

/** PDF report colors, as HEX strings such as "#FE5758". */
public class SubmissionPdfColors {

    /** The strip in the report header. */
    private String mainStrip;
    private String titles;
    private String identical;
    private String minorChanges;
    private String relatedMeaning;

    /** Default: #FE5758. */
    private String aiDetection;

    /** Grammar Checker highlight. Default: #FF9A02. */
    private String writingFeedback;

    public String getAiDetection() {
        return aiDetection;
    }

    public void setAiDetection(String aiDetection) {
        this.aiDetection = aiDetection;
    }

    public String getWritingFeedback() {
        return writingFeedback;
    }

    public void setWritingFeedback(String writingFeedback) {
        this.writingFeedback = writingFeedback;
    }

    public String getMainStrip() {
        return mainStrip;
    }

    public void setMainStrip(String mainStrip) {
        this.mainStrip = mainStrip;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getIdentical() {
        return identical;
    }

    public void setIdentical(String identical) {
        this.identical = identical;
    }

    public String getMinorChanges() {
        return minorChanges;
    }

    public void setMinorChanges(String minorChanges) {
        this.minorChanges = minorChanges;
    }

    public String getRelatedMeaning() {
        return relatedMeaning;
    }

    public void setRelatedMeaning(String relatedMeaning) {
        this.relatedMeaning = relatedMeaning;
    }
    
}
