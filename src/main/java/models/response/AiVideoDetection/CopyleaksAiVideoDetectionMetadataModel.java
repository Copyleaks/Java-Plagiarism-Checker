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

package models.response.AiVideoDetection;

/**
 * Metadata found in the video file.
 */
public class CopyleaksAiVideoDetectionMetadataModel {

    /** When the video was created, if available. */
    private String issuedTime;

    /** The AI service or tool that created the video, if available. */
    private String issuedBy;

    /** The application or device used to create the video, if available. */
    private String appOrDeviceUsed;

    /** A summary of how the video was generated, if available. */
    private String contentSummary;

    /** @return when the video was created, if available. */
    public String getIssuedTime() {
        return issuedTime;
    }

    /** @return the AI service or tool that created the video, if available. */
    public String getIssuedBy() {
        return issuedBy;
    }

    /** @return the application or device used to create the video, if available. */
    public String getAppOrDeviceUsed() {
        return appOrDeviceUsed;
    }

    /** @return a summary of how the video was generated, if available. */
    public String getContentSummary() {
        return contentSummary;
    }
}
