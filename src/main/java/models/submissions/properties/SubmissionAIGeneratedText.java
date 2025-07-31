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

public class SubmissionAIGeneratedText {
    private boolean detect;
    private SubmissionExplain explain;
    private int sensitivity;
    
    // Default constructor with default values
    public SubmissionAIGeneratedText() {
        this.detect = false;
        this.explain = new SubmissionExplain();
        this.sensitivity = 2; // Default value
    }
    
    // Full constructor
    public SubmissionAIGeneratedText(boolean detect, SubmissionExplain explain, int sensitivity) {
        this.detect = detect;
        this.explain = explain;
        setSensitivity(sensitivity); 
    }
    
    // Getters and Setters
    public boolean getDetect() {
        return detect;
    }
    
    public void setDetect(boolean detect) {
        this.detect = detect;
    }
    
    public SubmissionExplain getExplain() {
        return explain;
    }
    
    public void setExplain(SubmissionExplain explain) {
        this.explain = explain;
    }
    
    public int getSensitivity() {
        return sensitivity;
    }
    
    public void setSensitivity(int sensitivity) {
        if (sensitivity < 1 || sensitivity > 3) {
            throw new IllegalArgumentException("Sensitivity must be between 1 and 3 (inclusive)");
        }
        this.sensitivity = sensitivity;
    }
}

