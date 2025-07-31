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


public class SubmissionOverview {
    
    /**
     * Enable Gen-AI Overview feature to extract key insights from the scan data.
     * Default: false
     */
    private boolean enable = false;
    
    /**
     * Ignore AI detection when generating the scan's overview. 
     * Only applicable if AI detection was enabled.
     * Default: false
     */
    private boolean ignoreAIDetection = false;
    
    /**
     * Ignore plagiarism detection when generating the scan's overview. 
     * Only applicable if plagiarism detection was enabled.
     * Default: false
     */
    private boolean ignorePlagiarismDetection = false;
    
    /**
     * Ignore writing assistant when generating the scan's overview. 
     * Only applicable if the writing assistant was enabled.
     * Default: false
     */
    private boolean ignoreWritingFeedback = false;
    
    /**
     * Ignore the author's historical data when generating the scan's overview. 
     * Only applicable if author ID added to the request.
     * Default: false
     */
    private boolean ignoreAuthorData = false;
    
    // Getters
    public boolean isEnable() {
        return enable;
    }
    
    public boolean isIgnoreAIDetection() {
        return ignoreAIDetection;
    }
    
    public boolean isIgnorePlagiarismDetection() {
        return ignorePlagiarismDetection;
    }
    
    public boolean isIgnoreWritingFeedback() {
        return ignoreWritingFeedback;
    }
    
    public boolean isIgnoreAuthorData() {
        return ignoreAuthorData;
    }
    
    // Setters
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    public void setIgnoreAIDetection(boolean ignoreAIDetection) {
        this.ignoreAIDetection = ignoreAIDetection;
    }
    
    public void setIgnorePlagiarismDetection(boolean ignorePlagiarismDetection) {
        this.ignorePlagiarismDetection = ignorePlagiarismDetection;
    }
    
    public void setIgnoreWritingFeedback(boolean ignoreWritingFeedback) {
        this.ignoreWritingFeedback = ignoreWritingFeedback;
    }
    
    public void setIgnoreAuthorData(boolean ignoreAuthorData) {
        this.ignoreAuthorData = ignoreAuthorData;
    }
    
}