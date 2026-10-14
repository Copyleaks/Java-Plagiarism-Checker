/*
 The MIT License(MIT)
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package models.response.AiVideoDetection;

/**
 * The AI Video Detection result, delivered to the webhook given in the submit request.
 * Parse the webhook body with Gson: {@code new Gson().fromJson(body, CopyleaksAiVideoDetectionResponseModel.class)}.
 */
public class CopyleaksAiVideoDetectionResponseModel {

    /** The version of the AI detection model used. */
    private String model;

    /** Time-based results for the audio track, in milliseconds. */
    private CopyleaksAiVideoDetectionTrackResultModel audioResult;

    /** Time-based results for the visual track, in milliseconds. */
    private CopyleaksAiVideoDetectionTrackResultModel visualResult;

    /** The overall AI ratios, calculated from the scored segments only. */
    private CopyleaksAiVideoDetectionSummaryModel summary;

    /** Whether the video was determined to contain AI-generated content. */
    private Boolean isAiDetected;

    /** Information about the analyzed video. */
    private CopyleaksAiVideoDetectionVideoInfoModel videoInfo;

    /** Information about the scan. */
    private CopyleaksAiVideoDetectionScannedVideoModel scannedVideo;

    /** @return the version of the AI detection model used. */
    public String getModel() {
        return model;
    }

    /** @return time-based results for the audio track, in milliseconds. */
    public CopyleaksAiVideoDetectionTrackResultModel getAudioResult() {
        return audioResult;
    }

    /** @return time-based results for the visual track, in milliseconds. */
    public CopyleaksAiVideoDetectionTrackResultModel getVisualResult() {
        return visualResult;
    }

    /** @return the overall AI ratios, calculated from the scored segments only. */
    public CopyleaksAiVideoDetectionSummaryModel getSummary() {
        return summary;
    }

    /** @return whether the video was determined to contain AI-generated content. */
    public Boolean getIsAiDetected() {
        return isAiDetected;
    }

    /** @return information about the analyzed video. */
    public CopyleaksAiVideoDetectionVideoInfoModel getVideoInfo() {
        return videoInfo;
    }

    /** @return information about the scan. */
    public CopyleaksAiVideoDetectionScannedVideoModel getScannedVideo() {
        return scannedVideo;
    }
}
