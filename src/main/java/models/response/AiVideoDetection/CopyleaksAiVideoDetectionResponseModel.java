package models.response.AiVideoDetection;

import models.submissions.Webhooks.HelperModels.ErrorModels.ErrorModel;

/**
 * AI Video Detection result, sent to the webhook. Parse it with {@code new Gson().fromJson(body, ...)}.
 * Check {@link #getError()} first: when set, the result fields are null.
 */
public class CopyleaksAiVideoDetectionResponseModel {

    private String model;

    private CopyleaksAiVideoDetectionTrackResultModel audioResult;

    private CopyleaksAiVideoDetectionTrackResultModel visualResult;

    private CopyleaksAiVideoDetectionSummaryModel summary;

    private Boolean isAiDetected;

    private CopyleaksAiVideoDetectionVideoInfoModel videoInfo;

    private CopyleaksAiVideoDetectionScannedVideoModel scannedVideo;

    private ErrorModel error;

    private String developerPayload;

    /** Version of the detection model used. */
    public String getModel() {
        return model;
    }

    /** Time-based results for the audio track, in milliseconds. */
    public CopyleaksAiVideoDetectionTrackResultModel getAudioResult() {
        return audioResult;
    }

    /** Time-based results for the visual track, in milliseconds. */
    public CopyleaksAiVideoDetectionTrackResultModel getVisualResult() {
        return visualResult;
    }

    /** Overall AI ratios, from the scored segments only. */
    public CopyleaksAiVideoDetectionSummaryModel getSummary() {
        return summary;
    }

    /** Whether the video contains AI-generated content. */
    public Boolean getIsAiDetected() {
        return isAiDetected;
    }

    /** Information about the analyzed video. */
    public CopyleaksAiVideoDetectionVideoInfoModel getVideoInfo() {
        return videoInfo;
    }

    /** Information about the scan. */
    public CopyleaksAiVideoDetectionScannedVideoModel getScannedVideo() {
        return scannedVideo;
    }

    /** Why the scan failed; null when it succeeded. */
    public ErrorModel getError() {
        return error;
    }

    /** Developer payload from the submit request, or null. */
    public String getDeveloperPayload() {
        return developerPayload;
    }
}
