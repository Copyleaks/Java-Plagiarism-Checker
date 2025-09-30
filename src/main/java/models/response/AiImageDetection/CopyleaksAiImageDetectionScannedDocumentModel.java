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
 * Metadata about the AI image detection scan operation.
 */
public class CopyleaksAiImageDetectionScannedDocumentModel {

    /**
     * The unique identifier for this scan.
     */
    @JsonProperty("scanId")
    private String scanId;

    /**
     * The actual number of credits consumed by this scan.
     */
    @JsonProperty("actualCredits")
    private int actualCredits;

    /**
     * The expected number of credits for this scan.
     */
    @JsonProperty("expectedCredits")
    private int expectedCredits;

    /**
     * ISO 8601 timestamp of when the scan was created.
     */
    @JsonProperty("creationTime")
    private String creationTime;

    public CopyleaksAiImageDetectionScannedDocumentModel() {
    }

    public CopyleaksAiImageDetectionScannedDocumentModel(String scanId, int actualCredits, int expectedCredits, String creationTime) {
        this.scanId = scanId;
        this.actualCredits = actualCredits;
        this.expectedCredits = expectedCredits;
        this.creationTime = creationTime;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public int getActualCredits() {
        return actualCredits;
    }

    public void setActualCredits(int actualCredits) {
        this.actualCredits = actualCredits;
    }

    public int getExpectedCredits() {
        return expectedCredits;
    }

    public void setExpectedCredits(int expectedCredits) {
        this.expectedCredits = expectedCredits;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
