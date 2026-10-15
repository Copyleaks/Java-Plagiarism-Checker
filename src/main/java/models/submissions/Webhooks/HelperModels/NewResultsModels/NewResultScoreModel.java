package models.submissions.Webhooks.HelperModels.NewResultsModels;

public class NewResultScoreModel {

    /** Share of similar words across all results, without excluded references, quotes, etc. */
    private double aggregatedScore;

    public double getAggregatedScore() {
        return aggregatedScore;
    }

}
