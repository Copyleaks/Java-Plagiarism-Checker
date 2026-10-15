package models.submissions.properties;


/** Gen-AI Overview settings. Every flag defaults to false. */
public class SubmissionOverview {
    
    private boolean enable = false;
    
    private boolean ignoreAIDetection = false;
    
    private boolean ignorePlagiarismDetection = false;
    
    private boolean ignoreWritingFeedback = false;
    
    private boolean ignoreAuthorData = false;
    
    public boolean isEnable() {
        return enable;
    }
    
    public boolean isIgnoreAIDetection() {
        return ignoreAIDetection;
    }
    
    public boolean isIgnorePlagiarismDetection() {
        return ignorePlagiarismDetection;
    }
    
    public boolean isIgnoreWritingFeedback() {
        return ignoreWritingFeedback;
    }
    
    public boolean isIgnoreAuthorData() {
        return ignoreAuthorData;
    }
    
    /** Gen-AI Overview: key insights from the scan data. */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    /** Leave AI detection out of the overview, when it was enabled. */
    public void setIgnoreAIDetection(boolean ignoreAIDetection) {
        this.ignoreAIDetection = ignoreAIDetection;
    }
    
    /** Leave plagiarism detection out of the overview, when it was enabled. */
    public void setIgnorePlagiarismDetection(boolean ignorePlagiarismDetection) {
        this.ignorePlagiarismDetection = ignorePlagiarismDetection;
    }
    
    /** Leave the Grammar Checker out of the overview, when it was enabled. */
    public void setIgnoreWritingFeedback(boolean ignoreWritingFeedback) {
        this.ignoreWritingFeedback = ignoreWritingFeedback;
    }
    
    /** Leave the author's history out of the overview, when an author ID was sent. */
    public void setIgnoreAuthorData(boolean ignoreAuthorData) {
        this.ignoreAuthorData = ignoreAuthorData;
    }
    
}