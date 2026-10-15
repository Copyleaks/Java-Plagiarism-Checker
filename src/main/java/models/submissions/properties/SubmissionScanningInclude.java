package models.submissions.properties;

public class SubmissionScanningInclude {
    private String idPattern = null;

    /** @return the ID pattern, or null. */
    public String getIdPattern() {
        return idPattern;
    }

    /** Keep only internal database and repository results whose scan ID matches; wildcards as in exclude. */
    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }
}
