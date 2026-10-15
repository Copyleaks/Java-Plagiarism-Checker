package models.submissions.properties;

public class SubmissionScanningRepository extends SubmissionRepository {
    /** Compare with your own submissions in the repository. */
    private boolean includeMySubmissions;

    /** Compare with other users' submissions in the repository. */
    private boolean includeOthersSubmissions;

    public boolean isIncludeOthersSubmissions() {
        return includeOthersSubmissions;
    }

    public void setIncludeOthersSubmissions(boolean includeOthersSubmissions) {
        this.includeOthersSubmissions = includeOthersSubmissions;
    }

    public boolean isIncludeMySubmissions() {
        return includeMySubmissions;
    }

    public void setIncludeMySubmissions(boolean includeMySubmissions) {
        this.includeMySubmissions = includeMySubmissions;
    }
}
