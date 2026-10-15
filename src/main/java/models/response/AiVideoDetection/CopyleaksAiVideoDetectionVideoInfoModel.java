package models.response.AiVideoDetection;

/** Information about the analyzed video. */
public class CopyleaksAiVideoDetectionVideoInfoModel {

    private double duration;

    private CopyleaksAiVideoDetectionMetadataModel metadata;

    /** Video duration in seconds. */
    public double getDuration() {
        return duration;
    }

    /** Metadata found in the video file, such as C2PA provenance data, or null. */
    public CopyleaksAiVideoDetectionMetadataModel getMetadata() {
        return metadata;
    }
}
