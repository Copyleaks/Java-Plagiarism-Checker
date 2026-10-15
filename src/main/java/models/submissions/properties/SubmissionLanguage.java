package models.submissions.properties;

public class SubmissionLanguage {

    private String code;

    SubmissionLanguage(){}

    public SubmissionLanguage(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
