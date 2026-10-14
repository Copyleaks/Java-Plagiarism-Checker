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

package models.request.AiImageDetection;

import models.request.CopyleaksAsyncWebhooksModel;

/**
 * Request model for asynchronous AI Image Detection of an image URL.
 * The result is sent to {@code webhooks.url} when processing ends.
 */
public class CopyleaksAiImageDetectionAsyncRequestModel {

    /** Publicly reachable URL of the image. */
    private String url;
    /** The image file name including its extension (.jpg, .jpeg, .png, .webp, .tiff, .bmp, .heic, .heif). */
    private String fileName;
    /** The detection model, for example "ai-image-1-ultra". */
    private String model;
    /** "binary" (default) or "heatmap". */
    private String maskType;
    /** Objects to locate and segment, at most 3. */
    private CopyleaksObjectDetectionItem[] objectDetection;
    /** Headers sent when Copyleaks downloads the image, as {@code [["name", "value"], ...]}. */
    private String[][] headers;
    /** Where to send the result. */
    private CopyleaksAsyncWebhooksModel webhooks;
    /** Sandbox mode: free, returns mock results. */
    private boolean sandbox;
    /** A string that is sent back in the webhook. */
    private String developerPayload;

    /**
     * @param url      the publicly reachable image URL.
     * @param fileName the image file name including its extension.
     * @param model    the detection model, for example {@code CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA}.
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

    /** @param url the image URL. */
    public void setUrl(String url) {
        this.url = url;
    }

    /** @return the image file name. */
    public String getFileName() {
        return fileName;
    }

    /** @param fileName the image file name, including its extension. */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /** @return the detection model. */
    public String getModel() {
        return model;
    }

    /** @param model the detection model. */
    public void setModel(String model) {
        this.model = model;
    }

    /** @return the mask type, or null for the server default. */
    public String getMaskType() {
        return maskType;
    }

    /** @param maskType "binary" or "heatmap". See {@link models.constants.CopyleaksAiImageMaskTypes}. */
    public void setMaskType(String maskType) {
        this.maskType = maskType;
    }

    /** @return the objects to detect, or null. */
    public CopyleaksObjectDetectionItem[] getObjectDetection() {
        return objectDetection;
    }

    /** @param objectDetection the objects to detect, at most 3; null turns the feature off. */
    public void setObjectDetection(CopyleaksObjectDetectionItem[] objectDetection) {
        this.objectDetection = objectDetection;
    }

    /** @return the download headers, or null. */
    public String[][] getHeaders() {
        return headers;
    }

    /** @param headers headers sent when Copyleaks downloads the image, as {@code [["name", "value"], ...]}. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
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
