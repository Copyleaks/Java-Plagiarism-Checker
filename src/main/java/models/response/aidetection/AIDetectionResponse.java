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

package models.response.aidetection;

import java.util.List;

import models.response.writingassitant.ScannedDocument;

public class AIDetectionResponse {
    private String scanType;

    /**
     * The version of the AI Content Detection used.
     */
    private String modelVersion;

    /**
     * An array of all the classifications. The sections contain their position, classification and the probability for that classification.
     */
    private List<Result> results;

    /**
     * Aggregated detection results.
     */
    private Summary summary;

    /**
     * General information about the scanned document.
     */
    private ScannedDocument scannedDocument;

    /**
     * Identifier of the machine-translation provider used before detection.
     * 0 means the text was not translated.
     * Sent in the AI alert of the completed webhook; null when absent.
     */
    private Integer translationProvider;

    /**
     * The full text after machine translation to English.
     * Present only when the text was translated; otherwise null.
     */
    private String translation;

    /**
     * AI Logic explanation of the detected patterns.
     * Present only when explain (AI Logic) was enabled; otherwise null.
     */
    private Explain explain;

    public String getScanType() {
        return scanType;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public List<Result> getResults() {
        return results;
    }

    public Summary getSummary() {
        return summary;
    }

    public ScannedDocument getScannedDocument() {
        return scannedDocument;
    }

    public Integer getTranslationProvider() {
        return translationProvider;
    }

    public String getTranslation() {
        return translation;
    }

    public Explain getExplain() {
        return explain;
    }
}
