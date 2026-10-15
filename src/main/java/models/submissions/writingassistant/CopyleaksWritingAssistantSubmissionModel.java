package models.submissions.writingassistant;

public class CopyleaksWritingAssistantSubmissionModel {
    /** 1 to 25,000 characters. */
    private String text;

    /** Return mock results without using credits. */
    private boolean sandbox = false;

    /** Language code of the text, or null to detect it. */
    private String language;

    private ScoreWeights score;

    public CopyleaksWritingAssistantSubmissionModel(String text) {
        this.text = text;
    }

    public CopyleaksWritingAssistantSubmissionModel(String text, boolean sandbox, String language,
            ScoreWeights scoreWeight) {
        this.text = text;
        this.sandbox = sandbox;
        this.language = language;
        this.score = scoreWeight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ScoreWeights getScore() {
        return score;
    }

    public void setScore(ScoreWeights scoreWeight) {
        this.score = scoreWeight;
    }
}
