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
 * Information about an AI Video Detection scan.
 */
public class CopyleaksAiVideoDetectionScannedVideoModel {

    /** The scan ID given in the request. */
    private String scanId;

    /** The credits the scan consumed. */
    private int actualCredits;

    /** The credits the scan was expected to consume. */
    private int expectedCredits;

    /** When the scan was created, in ISO 8601 format. */
    private String creationTime;

    /** @return the scan ID given in the request. */
    public String getScanId() {
        return scanId;
    }

    /** @return the credits the scan consumed. */
    public int getActualCredits() {
        return actualCredits;
    }

    /** @return the credits the scan was expected to consume. */
    public int getExpectedCredits() {
        return expectedCredits;
    }

    /** @return when the scan was created, in ISO 8601 format. */
    public String getCreationTime() {
        return creationTime;
    }
}
