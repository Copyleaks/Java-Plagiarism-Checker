package models.submissions.properties;

public class SubmissionScanningCopyleaksDB {
    private boolean includeMySubmissions;
    private boolean includeOthersSubmissions;

    public boolean isIncludeMySubmissions() {
        return includeMySubmissions;
    }
    /** Compare with your own submissions in the internal database. Also indexes this scan there. */
    public void setIncludeMySubmissions(boolean includeMySubmissions) {
        this.includeMySubmissions = includeMySubmissions;
    }
    public boolean isIncludeOthersSubmissions() {
        return includeOthersSubmissions;
    }
    /** Compare with other users' submissions in the internal database. Also indexes this scan there. */
    public void setIncludeOthersSubmissions(boolean includeOthersSubmissions) {
        this.includeOthersSubmissions = includeOthersSubmissions;
    }
}
