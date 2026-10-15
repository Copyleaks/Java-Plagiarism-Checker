package models.submissions.properties;

import models.submissions.writingassistant.ScoreWeights;

public class SubmissionWritingFeedback {

    private Boolean enable;

    private ScoreWeights score;

    public Boolean getEnable() {
        return enable;
    }

    /** Turns on the Grammar Checker. */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public ScoreWeights getScore() {
        return score;
    }

    public void setScore(ScoreWeights score) {
        this.score = score;
    }
}
