package models.submissions.writingassistant;

public class CopyleaksWritingAssistantSubmissionModel {
    private String text;

    private boolean sandbox = false;

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

    /** Text to check, 1 to 25000 characters. */
    public void setText(String text) {
        this.text = text;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    /** Return mock results without using credits. */
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getLanguage() {
        return language;
    }

    /** Language code of the text, or null to detect it. */
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
