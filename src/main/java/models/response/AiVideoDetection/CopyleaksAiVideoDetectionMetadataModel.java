package models.response.AiVideoDetection;

/** Metadata found in the video file. Each field is null when not available. */
public class CopyleaksAiVideoDetectionMetadataModel {

    /** When the video was created. */
    private String issuedTime;

    /** The AI service or tool that created the video. */
    private String issuedBy;

    private String appOrDeviceUsed;

    /** How the video was generated. */
    private String contentSummary;

    public String getIssuedTime() {
        return issuedTime;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public String getAppOrDeviceUsed() {
        return appOrDeviceUsed;
    }

    public String getContentSummary() {
        return contentSummary;
    }
}
