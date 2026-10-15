package models.response.AiImageDetection;

/** Metadata about the AI image detection scan operation. */
public class CopyleaksAiImageDetectionScannedDocumentModel {

    private String scanId;

    private int actualCredits;

    private int expectedCredits;

    private String creationTime;

    public CopyleaksAiImageDetectionScannedDocumentModel() {
    }

    public CopyleaksAiImageDetectionScannedDocumentModel(String scanId, int actualCredits, int expectedCredits, String creationTime) {
        this.scanId = scanId;
        this.actualCredits = actualCredits;
        this.expectedCredits = expectedCredits;
        this.creationTime = creationTime;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    /** Credits the scan used. */
    public int getActualCredits() {
        return actualCredits;
    }

    public void setActualCredits(int actualCredits) {
        this.actualCredits = actualCredits;
    }

    public int getExpectedCredits() {
        return expectedCredits;
    }

    public void setExpectedCredits(int expectedCredits) {
        this.expectedCredits = expectedCredits;
    }

    /** When the scan was created, ISO 8601. */
    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
