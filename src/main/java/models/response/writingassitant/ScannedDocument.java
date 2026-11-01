package models.response.writingassitant;

import java.util.Date;

public class ScannedDocument {
    private String scanId;
    private int totalWords;
    private int totalExcluded;
    private int actualCredits;
    private int expectedCredits;
    private Date creationTime;

    public String getScanId() {
        return scanId;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getTotalExcluded() {
        return totalExcluded;
    }

    public int getActualCredits() {
        return actualCredits;
    }

    public int getExpectedCredits() {
        return expectedCredits;
    }

    public Date getCreationTime() {
        return creationTime;
    }
}
