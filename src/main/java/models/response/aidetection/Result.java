package models.response.aidetection;

import java.util.List;

public class Result {
    private int classification;

    private double probability;

    private List<Match> matches;

    /** Classification: 1 = human-written, 2 = AI-generated. */
    public int getClassification() {
        return classification;
    }

    /**
     * Probability of the classification.
     * @deprecated The API deprecated this field and may stop sending it; then it reads 0.0.
     */
    @Deprecated
    public double getProbability() {
        return probability;
    }

    /** Positions of the sections with this classification. */
    public List<Match> getMatches() {
        return matches;
    }
}
