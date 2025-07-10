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

package models.response.textModeration.submodules;

public class TextModerationScannedDocument {

    /**
     * The scan id given by the user.
     */
    private final String scanId;

    /**
     * Total number of words found in the scanned text.
     */
    private final int totalWords;

    /**
     * Total excluded words from the text.
     */
    private final int totalExcluded;

    /**
     * The cost of credits for this scan.
     */
    private final int actualCredits;

    /**
     * The amount of credits that was expected to be spent on the scan.
     */
    private final int expectedCredits;

    /**
     * Creation time of the scan.
     */
    private final String creationTime;

    public TextModerationScannedDocument(
            String scanId,
            int totalWords,
            int totalExcluded,
            int actualCredits,
            int expectedCredits,
            String creationTime) {

        this.scanId = scanId;
        this.totalWords = totalWords;
        this.totalExcluded = totalExcluded;
        this.actualCredits = actualCredits;
        this.expectedCredits = expectedCredits;
        this.creationTime = creationTime;
    }

    public String getScanId() {
        return scanId;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getTotalExcluded() {
        return totalExcluded;
    }

    public int getActualCredits() {
        return actualCredits;
    }

    public int getExpectedCredits() {
        return expectedCredits;
    }

    public String getCreationTime() {
        return creationTime;
    }
}
