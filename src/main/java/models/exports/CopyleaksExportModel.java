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

package models.exports;

public class CopyleaksExportModel {
    /**
     * @param completionWebhook This webhook event is triggered once the export is completed.
     * @param results An array of results to be exported. The equivalent of downloading results manually.
     * @param crawledVersion Download the crawled version of the submitted text. The equivalent of downloading crawled version manually.
     * @param maxRetries How many retries to send before giving up. Using high value (12) may lead to a longer time until the completionWebhook being executed. A low value (1) may lead to errors while your service is temporary having problems.
     * @param developerPayload Add a custom developer payload that will then be provided on the Export-Completed webhook. https://api.copyleaks.com/documentation/v3/webhooks/export-completed
     * @param pdfReport Download the PDF report. Allowed only when `properties.pdf.create` was set to true on the scan submittion.
     */
    private String completionWebhook;
    private ExportResults[] results;
    private ExportCrawledVersion crawledVersion;
    private Integer maxRetries = null;
    private String developerPayload = null;
    private ExportPdfReport pdfReport = null;

    public CopyleaksExportModel(String completionWebhook, ExportResults[] results, ExportCrawledVersion crawledVersion) {
        this.completionWebhook = completionWebhook;
        this.results = results;
        this.crawledVersion = crawledVersion;
    }

    public String getCompletionWebhook() {
        return completionWebhook;
    }

    public void setCompletionWebhook(String completionWebhook) {
        this.completionWebhook = completionWebhook;
    }

    public ExportResults[] getResults() {
        return results;
    }

    public ExportCrawledVersion getCrawledVersion() {
        return crawledVersion;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }

    public ExportPdfReport getPdfReport() {
        return pdfReport;
    }

    public void setPdfReport(ExportPdfReport pdfReport) {
        this.pdfReport = pdfReport;
    }
}
