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

public class SubmissionExclude {
    /**
     * Exclude quoted text from the scan.
     */
    private Boolean quotes = null;

    /**
     * Exclude referenced text from the scan.
     */
    private Boolean references = null;

    /**
     * Exclude table of contents from the scan.
     */
    private Boolean tableOfContents = null;

    /**
     * Exclude titles from the scan.
     */
    private Boolean titles = null;

    /**
     * When the scanned document is an HTML document, exclude irrelevant text that appears across the site like the website footer or header.
     */
    private Boolean htmlTemplate = null;


    private Boolean citations;

    private String[] documentTemplateIds;

    private SubmissionExcludeCode code;

    public SubmissionExcludeCode getCode() {
        return code;
    }

    public void setCode(SubmissionExcludeCode code) {
        this.code = code;
    }

    public String[] getDocumentTemplateIds() {
        return documentTemplateIds;
    }

    public void setDocumentTemplateIds(String[] documentTemplateIds) {
        this.documentTemplateIds = documentTemplateIds;
    }

    public Boolean isCitations() {
        return citations;
    }

    public void setCitations(Boolean citations) {
        this.citations = citations;
    }

    public Boolean isQuotes() {
        return quotes;
    }

    public Boolean isReferences() {
        return references;
    }

    public Boolean isTableOfContents() {
        return tableOfContents;
    }

    public Boolean isTitles() {
        return titles;
    }

    public Boolean isHtmlTemplate() {
        return htmlTemplate;
    }

    public void setQuotes(Boolean quotes) {
        this.quotes = quotes;
    }

    public void setReferences(Boolean references) {
        this.references = references;
    }

    public void setTableOfContents(Boolean tableOfContents) {
        this.tableOfContents = tableOfContents;
    }

    public void setTitles(Boolean titles) {
        this.titles = titles;
    }

    public void setHtmlTemplate(Boolean htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }
}
