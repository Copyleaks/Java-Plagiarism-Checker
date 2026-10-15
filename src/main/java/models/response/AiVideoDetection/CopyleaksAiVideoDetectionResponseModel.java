package models.response.AiVideoDetection;

import models.submissions.Webhooks.HelperModels.ErrorModels.ErrorModel;

/**
 * AI Video Detection result, sent to the webhook. Parse it with {@code new Gson().fromJson(body, ...)}.
 * Check {@link #getError()} first: when set, the result fields are null.
 */
public class CopyleaksAiVideoDetectionResponseModel {

    /** Version of the detection model used. */
    private String model;

    private CopyleaksAiVideoDetectionTrackResultModel audioResult;

    private CopyleaksAiVideoDetectionTrackResultModel visualResult;

    /** Overall AI ratios, from the scored segments only. */
    private CopyleaksAiVideoDetectionSummaryModel summary;

    /** Whether the video contains AI-generated content. */
    private Boolean isAiDetected;

    private CopyleaksAiVideoDetectionVideoInfoModel videoInfo;

    private CopyleaksAiVideoDetectionScannedVideoModel scannedVideo;

    /** Why the scan failed; null when it succeeded. */
    private ErrorModel error;

    /** From the submit request, or null. */
    private String developerPayload;

    public String getModel() {
        return model;
    }

    public CopyleaksAiVideoDetectionTrackResultModel getAudioResult() {
        return audioResult;
    }

    public CopyleaksAiVideoDetectionTrackResultModel getVisualResult() {
        return visualResult;
    }

    public CopyleaksAiVideoDetectionSummaryModel getSummary() {
        return summary;
    }

    public Boolean getIsAiDetected() {
        return isAiDetected;
    }

    public CopyleaksAiVideoDetectionVideoInfoModel getVideoInfo() {
        return videoInfo;
    }

    public CopyleaksAiVideoDetectionScannedVideoModel getScannedVideo() {
        return scannedVideo;
    }

    public ErrorModel getError() {
        return error;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }
}
