package models.response.aidetection;

import java.util.List;

public class Result {
    /** 1 = human-written, 2 = AI-generated. */
    private int classification;

    private double probability;

    /** Positions of the sections with this classification. */
    private List<Match> matches;

    public int getClassification() {
        return classification;
    }

    /** @deprecated The API deprecated this field and may stop sending it; then it reads 0.0. */
    @Deprecated
    public double getProbability() {
        return probability;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
