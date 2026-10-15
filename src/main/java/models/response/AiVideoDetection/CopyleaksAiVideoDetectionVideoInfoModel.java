package models.response.AiVideoDetection;

public class CopyleaksAiVideoDetectionVideoInfoModel {

    /** In seconds. */
    private double duration;

    /** Metadata found in the video file, such as C2PA provenance data, or null. */
    private CopyleaksAiVideoDetectionMetadataModel metadata;

    public double getDuration() {
        return duration;
    }

    public CopyleaksAiVideoDetectionMetadataModel getMetadata() {
        return metadata;
    }
}
