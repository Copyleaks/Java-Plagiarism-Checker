package models.response;

/** Scan ID, credits and creation time of an image or video scan. */
public abstract class CopyleaksScanInfoModel {

    private String scanId;

    private int actualCredits;

    private int expectedCredits;

    private String creationTime;

    /** Creates an empty model. */
    protected CopyleaksScanInfoModel() {
    }

    /** Creates a model with every field set. */
    protected CopyleaksScanInfoModel(String scanId, int actualCredits, int expectedCredits, String creationTime) {
        this.scanId = scanId;
        this.actualCredits = actualCredits;
        this.expectedCredits = expectedCredits;
        this.creationTime = creationTime;
    }

    /** Scan ID given in the request. */
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

    /** Credits the scan was expected to consume. */
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
