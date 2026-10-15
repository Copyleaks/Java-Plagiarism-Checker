package models.submissions.properties;

public class SubmissionIndexing {
    private SubmissionIndexingRepository[] repositories = null;
    private Boolean copyleaksDb=false;
    
    public Boolean getCopyleaksDb() {
        return copyleaksDb;
    }
    
    public void setCopyleaksDb(Boolean copyleaksDb) {
        this.copyleaksDb = copyleaksDb;
    }
    
    /** @return the repositories to index the document in, or null. */
    public SubmissionIndexingRepository[] getRepositories() {
        return repositories;
    }

    /** Private Cloud Hub repositories to index the document in. */
    public void setRepositories(SubmissionIndexingRepository[] repositories) {
        this.repositories = repositories;
    }
}
