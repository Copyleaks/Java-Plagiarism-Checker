package models.submissions.Webhooks.HelperModels.ResultsModels;

public class ScoreModel {

    private int identicalWords;

    /** Nearly identical words, such as "slow" and "slowly". */
    private int minorChangedWords;
    
    /** Paraphrased words. */
    private int relatedMeaningWords;
    
    /** Share of similar words across all results, without excluded references, quotes, etc. */
    private float aggregatedScore;

    public int getIdenticalWords() {
        return identicalWords;
    }

    public int getMinorChangedWords() {
        return minorChangedWords;
    }

    public int getRelatedMeaningWords() {
        return relatedMeaningWords;
    }

    public float getAggregatedScore() {
        return aggregatedScore;
    }

}
