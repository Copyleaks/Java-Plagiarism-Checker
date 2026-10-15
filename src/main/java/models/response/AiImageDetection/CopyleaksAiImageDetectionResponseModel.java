package models.response.AiImageDetection;

import models.submissions.Webhooks.HelperModels.ErrorModels.ErrorModel;

/**
 * AI Image Detection result. For an async scan, check {@link #getError()} first: when set, the result
 * fields are null.
 */
public class CopyleaksAiImageDetectionResponseModel {

    /** Version of the detection model used. */
    private String model;

    /** RLE mask of the AI-detected regions. */
    private CopyleaksAiImageDetectionResultModel result;

    private CopyleaksAiImageDetectionSummaryModel summary;

    private CopyleaksAiImageDetectionImageInfoModel imageInfo;

    private CopyleaksAiImageDetectionScannedDocumentModel scannedDocument;

    /** Whether the image is AI-generated; null when not reported. */
    private Boolean isAiDetected;

    /** One per object found. Null unless the request set {@code objectDetection}; objects not found are left out. */
    private CopyleaksAiImageDetectedObjectModel[] detectedObjects;

    /** Why the scan failed. Set only in the webhook of a failed async scan. */
    private ErrorModel error;

    /** From the async submit request, or null. */
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

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

    public Boolean getIsAiDetected() {
        return isAiDetected;
    }

    public void setIsAiDetected(Boolean isAiDetected) {
        this.isAiDetected = isAiDetected;
    }

    public CopyleaksAiImageDetectedObjectModel[] getDetectedObjects() {
        return detectedObjects;
    }

    public void setDetectedObjects(CopyleaksAiImageDetectedObjectModel[] detectedObjects) {
        this.detectedObjects = detectedObjects;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }
}
