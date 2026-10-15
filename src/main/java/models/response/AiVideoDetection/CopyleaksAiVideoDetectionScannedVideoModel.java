package models.response.AiVideoDetection;

public class CopyleaksAiVideoDetectionScannedVideoModel {

    private String scanId;

    /** Credits the scan used. */
    private int actualCredits;

    private int expectedCredits;

    /** ISO 8601. */
    private String creationTime;

    public String getScanId() {
        return scanId;
    }

    public int getActualCredits() {
        return actualCredits;
    }

    public int getExpectedCredits() {
        return expectedCredits;
    }

    public String getCreationTime() {
        return creationTime;
    }
}
