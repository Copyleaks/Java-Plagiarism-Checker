package models.request.AiImageDetection;

import models.request.CopyleaksAsyncWebhooksModel;

/** Asynchronous AI Image Detection of an image URL. The result is sent to {@code webhooks.url}. */
public class CopyleaksAiImageDetectionAsyncRequestModel {

    private String url;
    private String fileName;
    private String model;
    private String maskType;
    private CopyleaksObjectDetectionItem[] objectDetection;
    private String[][] headers;
    private CopyleaksAsyncWebhooksModel webhooks;
    private boolean sandbox;
    private String developerPayload;

    /**
     * @param url      publicly reachable URL of the image.
     * @param fileName file name with extension (.jpg, .jpeg, .png, .webp, .tiff, .bmp, .heic, .heif).
     * @param model    see {@link models.constants.CopyleaksAiImageDetectionModels}.
     * @param webhooks where to send the result.
     */
    public CopyleaksAiImageDetectionAsyncRequestModel(String url, String fileName, String model,
            CopyleaksAsyncWebhooksModel webhooks) {
        this.url = url;
        this.fileName = fileName;
        this.model = model;
        this.webhooks = webhooks;
    }

    /** @return the image URL. */
    public String getUrl() {
        return url;
    }

    /** Replaces the {@code url} constructor argument. */
    public void setUrl(String url) {
        this.url = url;
    }

    /** @return the image file name. */
    public String getFileName() {
        return fileName;
    }

    /** Replaces the {@code fileName} constructor argument. */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /** @return the detection model. */
    public String getModel() {
        return model;
    }

    /** Replaces the {@code model} constructor argument. */
    public void setModel(String model) {
        this.model = model;
    }

    /** @return the mask type, or null for the server default. */
    public String getMaskType() {
        return maskType;
    }

    /** "binary" (default) or "heatmap", see {@link models.constants.CopyleaksAiImageMaskTypes}. */
    public void setMaskType(String maskType) {
        this.maskType = maskType;
    }

    /** @return the objects to detect, or null. */
    public CopyleaksObjectDetectionItem[] getObjectDetection() {
        return objectDetection;
    }

    /** Objects to locate and segment, at most 3. Null turns the feature off. */
    public void setObjectDetection(CopyleaksObjectDetectionItem[] objectDetection) {
        this.objectDetection = objectDetection;
    }

    /** @return the download headers, or null. */
    public String[][] getHeaders() {
        return headers;
    }

    /** Headers sent when Copyleaks downloads the image, as {@code [["name", "value"], ...]}. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
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
