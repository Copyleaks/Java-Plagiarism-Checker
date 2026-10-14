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

package models.submissions.properties;

public class SubmissionPdfColors {

    /*
     * The color of the main strip in the header
     */
    private String mainStrip;
    /**
     * The color for titles in copyleaks result report
     */
    private String titles;
    /**
     * The highlight color for identical matches
     */
    private String identical;
    /**
     * The highlight color for minor changes matches
     */
    private String minorChanges;
    /**
     * The highlight color for related meaning matches
     */
    private String relatedMeaning;
    

    /**
     * The highlight color for AI content detection, in HEX format.
     */
    private String aiDetection;

    /**
     * The highlight color for writing feedback (Grammar Checker), in HEX format.
     */
    private String writingFeedback;

    /**
     * @return the AI content detection highlight color, or null.
     */
    public String getAiDetection() {
        return aiDetection;
    }

    /**
     * @param aiDetection the AI content detection highlight color, in HEX format. Default: #FE5758.
     */
    public void setAiDetection(String aiDetection) {
        this.aiDetection = aiDetection;
    }

    /**
     * @return the writing feedback highlight color, or null.
     */
    public String getWritingFeedback() {
        return writingFeedback;
    }

    /**
     * @param writingFeedback the writing feedback highlight color, in HEX format. Default: #FF9A02.
     */
    public void setWritingFeedback(String writingFeedback) {
        this.writingFeedback = writingFeedback;
    }

    public String getMainStrip() {
        return mainStrip;
    }

    public void setMainStrip(String mainStrip) {
        this.mainStrip = mainStrip;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getIdentical() {
        return identical;
    }

    public void setIdentical(String identical) {
        this.identical = identical;
    }

    public String getMinorChanges() {
        return minorChanges;
    }

    public void setMinorChanges(String minorChanges) {
        this.minorChanges = minorChanges;
    }

    public String getRelatedMeaning() {
        return relatedMeaning;
    }

    public void setRelatedMeaning(String relatedMeaning) {
        this.relatedMeaning = relatedMeaning;
    }
    
}
