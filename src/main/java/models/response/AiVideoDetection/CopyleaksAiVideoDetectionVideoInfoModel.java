package models.response.AiVideoDetection;

import models.response.AiImageDetection.CopyleaksImageMetadataModel;

/** Information about the analyzed video. */
public class CopyleaksAiVideoDetectionVideoInfoModel {

    private double duration;

    private CopyleaksImageMetadataModel metadata;

    /** Video duration in seconds. */
    public double getDuration() {
        return duration;
    }

    /** Metadata found in the video file, such as C2PA provenance data, or null. */
    public CopyleaksImageMetadataModel getMetadata() {
        return metadata;
    }
}
