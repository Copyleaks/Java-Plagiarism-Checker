package models.response.AiVideoDetection;

/** Information about an AI Video Detection scan. */
public class CopyleaksAiVideoDetectionScannedVideoModel {

    private String scanId;

    private int actualCredits;

    private int expectedCredits;

    private String creationTime;

    /** Scan ID given in the request. */
    public String getScanId() {
        return scanId;
    }

    /** Credits the scan used. */
    public int getActualCredits() {
        return actualCredits;
    }

    /** Credits the scan was expected to consume. */
    public int getExpectedCredits() {
        return expectedCredits;
    }

    /** When the scan was created, ISO 8601. */
    public String getCreationTime() {
        return creationTime;
    }
}
