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
package models.response.AiImageDetection;

/**
 * Response model for Copyleaks AI image detection analysis.
 * Contains the AI detection results, image information, and scan metadata.
 */
public class CopyleaksAiImageDetectionResponseModel {

    /**
     * The version of the AI detection model used for analysis.
     */
    private String model;

    /**
     * RLE-encoded mask data containing arrays of start positions and lengths for AI-detected regions.
     */
    private CopyleaksAiImageDetectionResultModel result;

    /**
     * Summary statistics of the AI detection analysis.
     */
    private CopyleaksAiImageDetectionSummaryModel summary;

    /**
     * Information about the analyzed image.
     */
    private CopyleaksAiImageDetectionImageInfoModel imageInfo;

    /**
     * Metadata about the scan operation.
     */
    private CopyleaksAiImageDetectionScannedDocumentModel scannedDocument;

    /**
     * Whether the image was determined to be AI-generated.
     */
    private Boolean isAiDetected;

    /**
     * The objects found in the image, one entry per object. Present only when the request
     * included objectDetection; objects that were not found are left out.
     */
    private CopyleaksAiImageDetectedObjectModel[] detectedObjects;

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

    /**
     * @return true when the image was determined to be AI-generated, or null when the response did not say.
     */
    public Boolean getIsAiDetected() {
        return isAiDetected;
    }

    /**
     * @param isAiDetected whether the image was determined to be AI-generated.
     */
    public void setIsAiDetected(Boolean isAiDetected) {
        this.isAiDetected = isAiDetected;
    }

    /**
     * @return the objects found in the image, or null when object detection was not requested.
     */
    public CopyleaksAiImageDetectedObjectModel[] getDetectedObjects() {
        return detectedObjects;
    }

    /**
     * @param detectedObjects the objects found in the image.
     */
    public void setDetectedObjects(CopyleaksAiImageDetectedObjectModel[] detectedObjects) {
        this.detectedObjects = detectedObjects;
    }
}
