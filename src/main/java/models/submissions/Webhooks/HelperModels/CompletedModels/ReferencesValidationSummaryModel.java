package models.submissions.Webhooks.HelperModels.CompletedModels;

/** Counts of the references found by the Citation Checker. */
public class ReferencesValidationSummaryModel {

    private int total;

    private int academic;

    /** Academic references fully corroborated against the academic citation index. */
    private int academicValidated;

    /** Non-academic references fully corroborated against their cited source. */
    private int nonAcademicValidated;

    public int getTotal() {
        return total;
    }

    public int getAcademic() {
        return academic;
    }

    public int getAcademicValidated() {
        return academicValidated;
    }

    public int getNonAcademicValidated() {
        return nonAcademicValidated;
    }
}
