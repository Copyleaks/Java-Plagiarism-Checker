package models.response.AiImageDetection;

import models.submissions.Webhooks.HelperModels.ErrorModels.ErrorModel;

/**
 * AI Image Detection result. For an async scan, check {@link #getError()} first: when set, the result
 * fields are null.
 */
public class CopyleaksAiImageDetectionResponseModel {

    private String model;

    private CopyleaksAiImageDetectionResultModel result;

    private CopyleaksAiImageDetectionSummaryModel summary;

    private CopyleaksAiImageDetectionImageInfoModel imageInfo;

    private CopyleaksAiImageDetectionScannedDocumentModel scannedDocument;

    private Boolean isAiDetected;

    private CopyleaksAiImageDetectedObjectModel[] detectedObjects;

    private ErrorModel error;

    private String developerPayload;

    public CopyleaksAiImageDetectionResponseModel() {
    }

    public CopyleaksAiImageDetectionResponseModel(String model, CopyleaksAiImageDetectionResultModel result, CopyleaksAiImageDetectionSummaryModel summary, CopyleaksAiImageDetectionImageInfoModel imageInfo, CopyleaksAiImageDetectionScannedDocumentModel scannedDocument) {
        this.model = model;
        this.result = result;
        this.summary = summary;
        this.imageInfo = imageInfo;
        this.scannedDocument = scannedDocument;
    }

    /** Version of the detection model used. */
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /** RLE mask of the AI-detected regions. */
    public CopyleaksAiImageDetectionResultModel getResult() {
        return result;
    }

    public void setResult(CopyleaksAiImageDetectionResultModel result) {
        this.result = result;
    }

    public CopyleaksAiImageDetectionSummaryModel getSummary() {
        return summary;
    }

    public void setSummary(CopyleaksAiImageDetectionSummaryModel summary) {
        this.summary = summary;
    }

    public CopyleaksAiImageDetectionImageInfoModel getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(CopyleaksAiImageDetectionImageInfoModel imageInfo) {
        this.imageInfo = imageInfo;
    }

    public CopyleaksAiImageDetectionScannedDocumentModel getScannedDocument() {
        return scannedDocument;
    }

    public void setScannedDocument(CopyleaksAiImageDetectionScannedDocumentModel scannedDocument) {
        this.scannedDocument = scannedDocument;
    }

    /** Whether the image is AI-generated; null when not reported. */
    public Boolean getIsAiDetected() {
        return isAiDetected;
    }

    /** @param isAiDetected whether the image was determined to be AI-generated. */
    public void setIsAiDetected(Boolean isAiDetected) {
        this.isAiDetected = isAiDetected;
    }

    /** One per object found. Null unless the request set {@code objectDetection}; objects not found are left out. */
    public CopyleaksAiImageDetectedObjectModel[] getDetectedObjects() {
        return detectedObjects;
    }

    /** @param detectedObjects the objects found in the image. */
    public void setDetectedObjects(CopyleaksAiImageDetectedObjectModel[] detectedObjects) {
        this.detectedObjects = detectedObjects;
    }

    /** Why the scan failed. Set only in the webhook of a failed async scan. */
    public ErrorModel getError() {
        return error;
    }

    /** @param error why the async scan failed. */
    public void setError(ErrorModel error) {
        this.error = error;
    }

    /** Developer payload from the async submit request, or null. */
    public String getDeveloperPayload() {
        return developerPayload;
    }

    /** @param developerPayload the developer payload sent with the async submit request. */
    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }
}
