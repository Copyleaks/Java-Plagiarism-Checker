package models.submissions.Webhooks.HelperModels.CompletedModels;

/** Counts of the references found by the Citation Checker. */
public class ReferencesValidationSummaryModel {

    private int total;

    private int academic;

    private int academicValidated;

    private int nonAcademicValidated;

    /** Total number of references detected. */
    public int getTotal() {
        return total;
    }

    /** Number of references identified as academic. */
    public int getAcademic() {
        return academic;
    }

    /** Academic references fully corroborated against the academic citation index. */
    public int getAcademicValidated() {
        return academicValidated;
    }

    /** Non-academic references fully corroborated against their cited source. */
    public int getNonAcademicValidated() {
        return nonAcademicValidated;
    }
}
