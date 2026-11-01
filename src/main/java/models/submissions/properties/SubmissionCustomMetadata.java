package models.submissions.properties;

public class SubmissionCustomMetadata {
    
    private String key;

    private String value;

    /** A custom metadata entry. */
    public SubmissionCustomMetadata(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
