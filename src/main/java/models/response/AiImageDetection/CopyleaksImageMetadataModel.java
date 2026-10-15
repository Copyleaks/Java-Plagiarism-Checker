package models.response.AiImageDetection;

/** Metadata found in the image file. Each field is null when not available. */
public class CopyleaksImageMetadataModel {

    /** When the image was created. */
    private String issuedTime;

    /** The AI service or tool that created the image. */
    private String issuedBy;

    private String appOrDeviceUsed;

    /** How the image was generated. */
    private String contentSummary;

    public CopyleaksImageMetadataModel() {
    }

    public CopyleaksImageMetadataModel(String issuedTime, String issuedBy, String appOrDeviceUsed) {
        this.issuedTime = issuedTime;
        this.issuedBy = issuedBy;
        this.appOrDeviceUsed = appOrDeviceUsed;
    }

    public String getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(String issuedTime) {
        this.issuedTime = issuedTime;
    }

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

    public String getContentSummary() {
        return contentSummary;
    }

    public void setContentSummary(String contentSummary) {
        this.contentSummary = contentSummary;
    }
}
