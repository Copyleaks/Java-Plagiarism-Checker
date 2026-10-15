package models.submissions.properties;


public class SubmissionOverview {
    
    /** Gen-AI Overview: key insights from the scan data. */
    private boolean enable = false;
    
    /** Leave AI detection out of the overview, when it was enabled. */
    private boolean ignoreAIDetection = false;
    
    /** Leave plagiarism detection out of the overview, when it was enabled. */
    private boolean ignorePlagiarismDetection = false;
    
    /** Leave the Grammar Checker out of the overview, when it was enabled. */
    private boolean ignoreWritingFeedback = false;
    
    /** Leave the author's history out of the overview, when an author ID was sent. */
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
    
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    public void setIgnoreAIDetection(boolean ignoreAIDetection) {
        this.ignoreAIDetection = ignoreAIDetection;
    }
    
    public void setIgnorePlagiarismDetection(boolean ignorePlagiarismDetection) {
        this.ignorePlagiarismDetection = ignorePlagiarismDetection;
    }
    
    public void setIgnoreWritingFeedback(boolean ignoreWritingFeedback) {
        this.ignoreWritingFeedback = ignoreWritingFeedback;
    }
    
    public void setIgnoreAuthorData(boolean ignoreAuthorData) {
        this.ignoreAuthorData = ignoreAuthorData;
    }
    
}