package models.submissions.properties;

public class SubmissionScanningCopyleaksDB {
    /** Compare with your own submissions in the internal database. Also indexes this scan there. */
    private boolean includeMySubmissions;
    /** Compare with other users' submissions in the internal database. Also indexes this scan there. */
    private boolean includeOthersSubmissions;

    public boolean isIncludeMySubmissions() {
        return includeMySubmissions;
    }
    public void setIncludeMySubmissions(boolean includeMySubmissions) {
        this.includeMySubmissions = includeMySubmissions;
    }
    public boolean isIncludeOthersSubmissions() {
        return includeOthersSubmissions;
    }
    public void setIncludeOthersSubmissions(boolean includeOthersSubmissions) {
        this.includeOthersSubmissions = includeOthersSubmissions;
    }
}
