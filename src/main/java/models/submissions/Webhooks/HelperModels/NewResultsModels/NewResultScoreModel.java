package models.submissions.Webhooks.HelperModels.NewResultsModels;

public class NewResultScoreModel {

    private double aggregatedScore;

    /** Share of similar words across all results, without excluded references, quotes, etc. */
    public double getAggregatedScore() {
        return aggregatedScore;
    }

}
