package models.request.AiImageDetection;

import models.request.CopyleaksAsyncWebhooksModel;

/** Asynchronous AI Image Detection of an image URL. The result is sent to {@code webhooks.url}. */
public class CopyleaksAiImageDetectionAsyncRequestModel {

    /** Publicly reachable URL of the image. */
    private String url;
    /** File name with extension (.jpg, .jpeg, .png, .webp, .tiff, .bmp, .heic, .heif). */
    private String fileName;
    /** See {@link models.constants.CopyleaksAiImageDetectionModels}. */
    private String model;
    /** "binary" (default) or "heatmap", see {@link models.constants.CopyleaksAiImageMaskTypes}. */
    private String maskType;
    /** Objects to locate and segment, at most 3. Null turns the feature off. */
    private CopyleaksObjectDetectionItem[] objectDetection;
    /** Headers sent when Copyleaks downloads the image, as {@code [["name", "value"], ...]}. */
    private String[][] headers;
    private CopyleaksAsyncWebhooksModel webhooks;
    /** Return mock results without using credits. */
    private boolean sandbox;
    /** Sent back in the webhook. */
    private String developerPayload;

    public CopyleaksAiImageDetectionAsyncRequestModel(String url, String fileName, String model,
            CopyleaksAsyncWebhooksModel webhooks) {
        this.url = url;
        this.fileName = fileName;
        this.model = model;
        this.webhooks = webhooks;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMaskType() {
        return maskType;
    }

    public void setMaskType(String maskType) {
        this.maskType = maskType;
    }

    public CopyleaksObjectDetectionItem[] getObjectDetection() {
        return objectDetection;
    }

    public void setObjectDetection(CopyleaksObjectDetectionItem[] objectDetection) {
        this.objectDetection = objectDetection;
    }

    public String[][] getHeaders() {
        return headers;
    }

    public void setHeaders(String[][] headers) {
        this.headers = headers;
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
