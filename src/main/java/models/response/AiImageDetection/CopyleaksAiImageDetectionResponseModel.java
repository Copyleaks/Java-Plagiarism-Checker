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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response model for Copyleaks AI image detection analysis.
 * Contains the AI detection results, image information, and scan metadata.
 */
public class CopyleaksAiImageDetectionResponseModel {

    /**
     * The version of the AI detection model used for analysis.
     */
    @JsonProperty("model")
    private String model;

    /**
     * RLE-encoded mask data containing arrays of start positions and lengths for AI-detected regions.
     */
    @JsonProperty("result")
    private CopyleaksAiImageDetectionResultModel result;

    /**
     * Summary statistics of the AI detection analysis.
     */
    @JsonProperty("summary")
    private CopyleaksAiImageDetectionSummaryModel summary;

    /**
     * Information about the analyzed image.
     */
    @JsonProperty("imageInfo")
    private CopyleaksAiImageDetectionImageInfoModel imageInfo;

    /**
     * Metadata about the scan operation.
     */
    @JsonProperty("scannedDocument")
    private CopyleaksAiImageDetectionScannedDocumentModel scannedDocument;

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
}
