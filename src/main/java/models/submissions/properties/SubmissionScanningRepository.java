package models.submissions.properties;

public class SubmissionScanningRepository extends SubmissionRepository {
    private boolean includeMySubmissions;

    private boolean includeOthersSubmissions;

    public boolean isIncludeOthersSubmissions() {
        return includeOthersSubmissions;
    }

    /** Compare with other users' submissions in the repository. */
    public void setIncludeOthersSubmissions(boolean includeOthersSubmissions) {
        this.includeOthersSubmissions = includeOthersSubmissions;
    }

    public boolean isIncludeMySubmissions() {
        return includeMySubmissions;
    }

    /** Compare with your own submissions in the repository. */
    public void setIncludeMySubmissions(boolean includeMySubmissions) {
        this.includeMySubmissions = includeMySubmissions;
    }
}
