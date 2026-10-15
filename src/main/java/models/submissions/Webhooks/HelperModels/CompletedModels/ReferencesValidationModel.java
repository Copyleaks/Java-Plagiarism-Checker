package models.submissions.Webhooks.HelperModels.CompletedModels;

/** Citation Checker summary. The per-reference results are in the crawled version. */
public class ReferencesValidationModel {

    private ReferencesValidationSummaryModel summary;

    /** Count of references that were detected and validated. */
    public ReferencesValidationSummaryModel getSummary() {
        return summary;
    }
}
