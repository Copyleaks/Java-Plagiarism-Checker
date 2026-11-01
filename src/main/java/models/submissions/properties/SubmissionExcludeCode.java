package models.submissions.properties;

public class SubmissionExcludeCode {

    private Boolean comments;

    public SubmissionExcludeCode(){}

    public SubmissionExcludeCode(Boolean comments){
        this.comments = comments;
    }

    public Boolean getComments() {
        return comments;
    }

    public void setComments(Boolean comments) {
        this.comments = comments;
    }
    
}
