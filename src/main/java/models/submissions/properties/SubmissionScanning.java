package models.submissions.properties;

public class SubmissionScanning {
    private Boolean internet = null;

    private SubmissionScanningExclude exclude = null;
    
    private SubmissionScanningInclude include = null;
    private SubmissionScanningRepository[] repositories = null;

    private SubmissionScanningCopyleaksDB copyleaksDb = null;

    private SubmissionCrossLanguages crossLanguages;

    public SubmissionCrossLanguages getCrossLanguages() {
        return crossLanguages;
    }

    public void setCrossLanguages(SubmissionCrossLanguages crossLanguages) {
        this.crossLanguages = crossLanguages;
    }

    public Boolean isInternet() {
        return internet;
    }

    public SubmissionScanningExclude getExclude() {
        return exclude;
    }

    public void setExclude(SubmissionScanningExclude exclude) {
        this.exclude = exclude;
    }

    public SubmissionScanningInclude getInclude() {
        return include;
    }

    public void setInclude(SubmissionScanningInclude include) {
        this.include = include;
    }
    
    public SubmissionScanningRepository[] getRepositories() {
        return repositories;
    }

    public void setRepositories(SubmissionScanningRepository[] repositories) {
        this.repositories = repositories;
    }

    public SubmissionScanningCopyleaksDB getCopyleaksDb() {
        return copyleaksDb;
    }

    public void setCopyleaksDb(SubmissionScanningCopyleaksDB copyleaksDb) {
        this.copyleaksDb = copyleaksDb;
    }

    /** Compare with online sources. */
    public void setInternet(Boolean internet) {
        this.internet = internet;
    }
}
