package models.request.AiVideoDetection;

import models.request.CopyleaksAsyncWebhooksModel;

/** AI Video Detection of a video URL. The result is sent to {@code webhooks.url}. */
public class CopyleaksAiVideoDetectionRequestModel {

    private String url;
    private String[][] headers;
    private String verb;
    private String filename;
    private String model;
    private CopyleaksAsyncWebhooksModel webhooks;
    private boolean sandbox;
    private String developerPayload;

    /**
     * @param url      publicly reachable URL of the video.
     * @param filename file name with extension (.mp4, .avi, .mov, .mkv, .webm, .flv, .wmv, .mpg, .m4v, .3gp, .mxf),
     *                 max 255 chars.
     * @param model    see {@link models.constants.CopyleaksAiVideoDetectionModels}.
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

    /** Replaces the {@code url} constructor argument. */
    public void setUrl(String url) {
        this.url = url;
    }

    /** @return the download headers, or null. */
    public String[][] getHeaders() {
        return headers;
    }

    /** Headers sent when Copyleaks downloads the video, as {@code [["name", "value"], ...]}. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }

    /** @return the download HTTP method, or null for GET. */
    public String getVerb() {
        return verb;
    }

    /** HTTP method used to download the video: GET (default), POST or PUT. */
    public void setVerb(String verb) {
        this.verb = verb;
    }

    /** @return the video file name. */
    public String getFilename() {
        return filename;
    }

    /** Replaces the {@code filename} constructor argument. */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /** @return the detection model. */
    public String getModel() {
        return model;
    }

    /** Replaces the {@code model} constructor argument. */
    public void setModel(String model) {
        this.model = model;
    }

    /** @return the webhook settings. */
    public CopyleaksAsyncWebhooksModel getWebhooks() {
        return webhooks;
    }

    /** Replaces the {@code webhooks} constructor argument. */
    public void setWebhooks(CopyleaksAsyncWebhooksModel webhooks) {
        this.webhooks = webhooks;
    }

    /** @return true when sandbox mode is on. */
    public boolean isSandbox() {
        return sandbox;
    }

    /** Return mock results without using credits. */
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    /** @return the developer payload, or null. */
    public String getDeveloperPayload() {
        return developerPayload;
    }

    /** Custom payload sent back in the webhook. */
    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }
}
