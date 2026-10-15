package models.submissions.properties;

public class SubmissionIndexing {
    /** Private Cloud Hub repositories to index the document in. */
    private SubmissionIndexingRepository[] repositories = null;
    private Boolean copyleaksDb=false;
    
    public Boolean getCopyleaksDb() {
        return copyleaksDb;
    }
    
    public void setCopyleaksDb(Boolean copyleaksDb) {
        this.copyleaksDb = copyleaksDb;
    }
    
    public SubmissionIndexingRepository[] getRepositories() {
        return repositories;
    }

    public void setRepositories(SubmissionIndexingRepository[] repositories) {
        this.repositories = repositories;
    }
}
