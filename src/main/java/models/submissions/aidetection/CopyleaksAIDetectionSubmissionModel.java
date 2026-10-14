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

package models.submissions.aidetection;

public class CopyleaksAIDetectionSubmissionModel {

    /*
     * A text string.
     */
    private String text;

    /*
     * Use sandbox mode to test your integration with the Copyleaks API for free.
     */
    private boolean sandbox;

    public CopyleaksAIDetectionSubmissionModel(String text) {
        this(text, false);
    }

    public CopyleaksAIDetectionSubmissionModel(String text, boolean sandbox) {
        this.text = text;
        this.sandbox = sandbox;
    }

    /*
     * Turn on AI Logic: explanations of the patterns that drove the result.
     */
    private Boolean explain;

    /*
     * Detection sensitivity from 1 to 3. Default: 2.
     */
    private Integer sensitivity;

    /**
     * @return true when AI Logic is requested, or null for the server default (false).
     */
    public Boolean getExplain() {
        return explain;
    }

    /**
     * Turns on AI Logic. The result then includes {@code explain}. Explanations are returned for
     * six languages only; for other languages the result has no explanation.
     * @param explain true to request AI Logic explanations.
     */
    public void setExplain(Boolean explain) {
        this.explain = explain;
    }

    /**
     * @return the sensitivity, or null for the server default (2).
     */
    public Integer getSensitivity() {
        return sensitivity;
    }

    /**
     * Sets how the detection behaves: 1 for text copied from an AI tool without edits,
     * 2 for text with minor changes, 3 for heavily modified text.
     * @param sensitivity 1, 2 or 3, or null for the server default.
     * @throws IllegalArgumentException if the value is not between 1 and 3.
     */
    public void setSensitivity(Integer sensitivity) {
        if (sensitivity != null && (sensitivity < 1 || sensitivity > 3)) {
            throw new IllegalArgumentException("Sensitivity must be between 1 and 3 (inclusive)");
        }
        this.sensitivity = sensitivity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }
}
