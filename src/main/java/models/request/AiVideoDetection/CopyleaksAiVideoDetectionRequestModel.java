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

package models.request.AiVideoDetection;

import models.request.CopyleaksAsyncWebhooksModel;

/**
 * Request model for AI Video Detection of a video URL.
 * The result is sent to {@code webhooks.url} when processing ends.
 */
public class CopyleaksAiVideoDetectionRequestModel {

    /** Publicly reachable URL of the video. */
    private String url;
    /** Headers sent when Copyleaks downloads the video, as {@code [["name", "value"], ...]}. */
    private String[][] headers;
    /** The HTTP method used to download the video: GET, POST or PUT. */
    private String verb;
    /** The video file name including its extension. */
    private String filename;
    /** The detection model, for example "ai-video-1-pro". */
    private String model;
    /** Where to send the result. */
    private CopyleaksAsyncWebhooksModel webhooks;
    /** Sandbox mode: free, returns mock results. */
    private boolean sandbox;
    /** A string that is sent back in the webhook. */
    private String developerPayload;

    /**
     * @param url      the publicly reachable video URL.
     * @param filename the video file name including its extension (.mp4, .avi, .mov, .mkv, .webm, .flv,
     *                 .wmv, .mpg, .m4v, .3gp, .mxf), at most 255 characters.
     * @param model    the detection model, for example {@code CopyleaksAiVideoDetectionModels.AI_VIDEO_1_PRO}.
     * @param webhooks where to send the result.
     */
    public CopyleaksAiVideoDetectionRequestModel(String url, String filename, String model,
            CopyleaksAsyncWebhooksModel webhooks) {
        this.url = url;
        this.filename = filename;
        this.model = model;
        this.webhooks = webhooks;
    }

    /** @return the video URL. */
    public String getUrl() {
        return url;
    }

    /** @param url the video URL. */
    public void setUrl(String url) {
        this.url = url;
    }

    /** @return the download headers, or null. */
    public String[][] getHeaders() {
        return headers;
    }

    /** @param headers headers sent when Copyleaks downloads the video, as {@code [["name", "value"], ...]}. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }

    /** @return the download HTTP method, or null for GET. */
    public String getVerb() {
        return verb;
    }

    /** @param verb the HTTP method used to download the video: GET, POST or PUT. */
    public void setVerb(String verb) {
        this.verb = verb;
    }

    /** @return the video file name. */
    public String getFilename() {
        return filename;
    }

    /** @param filename the video file name, including its extension. */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /** @return the detection model. */
    public String getModel() {
        return model;
    }

    /** @param model the detection model. */
    public void setModel(String model) {
        this.model = model;
    }

    /** @return the webhook settings. */
    public CopyleaksAsyncWebhooksModel getWebhooks() {
        return webhooks;
    }

    /** @param webhooks where to send the result. */
    public void setWebhooks(CopyleaksAsyncWebhooksModel webhooks) {
        this.webhooks = webhooks;
    }

    /** @return true when sandbox mode is on. */
    public boolean isSandbox() {
        return sandbox;
    }

    /** @param sandbox true to use sandbox mode. */
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    /** @return the developer payload, or null. */
    public String getDeveloperPayload() {
        return developerPayload;
    }

    /** @param developerPayload a string that is sent back in the webhook. */
    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }
}
