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

public class SubmissionScanningCopyleaksDB {
    /**
     * When set to true: Copyleaks will also compare against content which was uploaded by YOU to the Copyleaks internal database.
     * If true, it will also index the scan in the Copyleaks internal database.
     */
    private boolean includeMySubmissions;
    /**
     * When set to true: Copyleaks will also compare against content which was uploaded by OTHERS to the Copyleaks internal database.
     * If true, it will also index the scan in the Copyleaks internal database.
     */
    private boolean includeOthersSubmissions;

    public boolean isIncludeMySubmissions() {
        return includeMySubmissions;
    }
    public void setIncludeMySubmissions(boolean includeMySubmissions) {
        this.includeMySubmissions = includeMySubmissions;
    }
    public boolean isIncludeOthersSubmissions() {
        return includeOthersSubmissions;
    }
    public void setIncludeOthersSubmissions(boolean includeOthersSubmissions) {
        this.includeOthersSubmissions = includeOthersSubmissions;
    }
}
