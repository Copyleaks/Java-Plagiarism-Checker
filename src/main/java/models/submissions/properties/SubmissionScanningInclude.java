package models.submissions.properties;

public class SubmissionScanningInclude {
    /** Keep only internal database and repository results whose scan ID matches; wildcards as in exclude. */
    private String idPattern = null;

    public String getIdPattern() {
        return idPattern;
    }

    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }
}
