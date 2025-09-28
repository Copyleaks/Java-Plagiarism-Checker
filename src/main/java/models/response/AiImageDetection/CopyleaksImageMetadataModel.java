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
 * Optional metadata extracted from the image.
 */
public class CopyleaksImageMetadataModel {

    /**
     * Timestamp when the image was created (if available).
     */
    @JsonProperty("issuedTime")
    private String issuedTime;

    /**
     * The AI service or tool that created the image (if detected).
     */
    @JsonProperty("issuedBy")
    private String issuedBy;

    /**
     * The application or device used to create the image.
     */
    @JsonProperty("appOrDeviceUsed")
    private String appOrDeviceUsed;

    public CopyleaksImageMetadataModel() {
    }

    public CopyleaksImageMetadataModel(String issuedTime, String issuedBy, String appOrDeviceUsed) {
        this.issuedTime = issuedTime;
        this.issuedBy = issuedBy;
        this.appOrDeviceUsed = appOrDeviceUsed;
    }

    public String getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(String issuedTime) {
        this.issuedTime = issuedTime;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getAppOrDeviceUsed() {
        return appOrDeviceUsed;
    }

    public void setAppOrDeviceUsed(String appOrDeviceUsed) {
        this.appOrDeviceUsed = appOrDeviceUsed;
    }
}
