package models.request.AiVideoDetection;

import models.request.CopyleaksAsyncWebhooksModel;

/** AI Video Detection of a video URL. The result is sent to {@code webhooks.url}. */
public class CopyleaksAiVideoDetectionRequestModel {

    /** Publicly reachable URL of the video. */
    private String url;
    /** Headers sent when Copyleaks downloads the video, as {@code [["name", "value"], ...]}. */
    private String[][] headers;
    /** HTTP method used to download the video: GET (default), POST or PUT. */
    private String verb;
    /** File name with extension (.mp4, .avi, .mov, .mkv, .webm, .flv, .wmv, .mpg, .m4v, .3gp, .mxf), max 255 chars. */
    private String filename;
    /** See {@link models.constants.CopyleaksAiVideoDetectionModels}. */
    private String model;
    private CopyleaksAsyncWebhooksModel webhooks;
    /** Return mock results without using credits. */
    private boolean sandbox;
    /** Sent back in the webhook. */
    private String developerPayload;

    public CopyleaksAiVideoDetectionRequestModel(String url, String filename, String model,
            CopyleaksAsyncWebhooksModel webhooks) {
        this.url = url;
        this.filename = filename;
        this.model = model;
        this.webhooks = webhooks;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[][] getHeaders() {
        return headers;
    }

    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CopyleaksAsyncWebhooksModel getWebhooks() {
        return webhooks;
    }

    public void setWebhooks(CopyleaksAsyncWebhooksModel webhooks) {
        this.webhooks = webhooks;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }
}
