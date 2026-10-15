package models.response.AiImageDetection;

/** Metadata found in the image file. Each field is null when not available. */
public class CopyleaksImageMetadataModel {

    private String issuedTime;

    private String issuedBy;

    private String appOrDeviceUsed;

    private String contentSummary;

    public CopyleaksImageMetadataModel() {
    }

    public CopyleaksImageMetadataModel(String issuedTime, String issuedBy, String appOrDeviceUsed) {
        this.issuedTime = issuedTime;
        this.issuedBy = issuedBy;
        this.appOrDeviceUsed = appOrDeviceUsed;
    }

    /** When the image was created. */
    public String getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(String issuedTime) {
        this.issuedTime = issuedTime;
    }

    /** The AI service or tool that created the image. */
    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getAppOrDeviceUsed() {
        return appOrDeviceUsed;
    }

    public void setAppOrDeviceUsed(String appOrDeviceUsed) {
        this.appOrDeviceUsed = appOrDeviceUsed;
    }

    /** How the image was generated. */
    public String getContentSummary() {
        return contentSummary;
    }

    /** @param contentSummary a summary of how the image was generated. */
    public void setContentSummary(String contentSummary) {
        this.contentSummary = contentSummary;
    }
}
