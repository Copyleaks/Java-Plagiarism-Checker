package models.response.AiVideoDetection;

/** Metadata found in the video file. Each field is null when not available. */
public class CopyleaksAiVideoDetectionMetadataModel {

    private String issuedTime;

    private String issuedBy;

    private String appOrDeviceUsed;

    private String contentSummary;

    /** When the video was created. */
    public String getIssuedTime() {
        return issuedTime;
    }

    /** The AI service or tool that created the video. */
    public String getIssuedBy() {
        return issuedBy;
    }

    /** Application or device used to create the video, if available. */
    public String getAppOrDeviceUsed() {
        return appOrDeviceUsed;
    }

    /** How the video was generated. */
    public String getContentSummary() {
        return contentSummary;
    }
}
