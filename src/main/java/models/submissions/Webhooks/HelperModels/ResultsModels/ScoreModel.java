package models.submissions.Webhooks.HelperModels.ResultsModels;

public class ScoreModel {

    private int identicalWords;

    private int minorChangedWords;
    
    private int relatedMeaningWords;
    
    private float aggregatedScore;

    public int getIdenticalWords() {
        return identicalWords;
    }

    /** Nearly identical words, such as "slow" and "slowly". */
    public int getMinorChangedWords() {
        return minorChangedWords;
    }

    /** Paraphrased words. */
    public int getRelatedMeaningWords() {
        return relatedMeaningWords;
    }

    /** Share of similar words across all results, without excluded references, quotes, etc. */
    public float getAggregatedScore() {
        return aggregatedScore;
    }

}
