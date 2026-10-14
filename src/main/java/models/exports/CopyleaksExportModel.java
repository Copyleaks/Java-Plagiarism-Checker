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

/**
 * Request body for exporting scan artifacts to your server.
 * Only {@code completionWebhook} is required. Add the artifacts you want: results, the crawled version,
 * the PDF report, the AI Text Detection report, the Grammar Checker report or the overview report.
 */
public class CopyleaksExportModel {

    /** This webhook event is triggered once the export is completed. */
    private String completionWebhook;
    /** Headers added to the completion webhook request, as {@code [["name", "value"], ...]}. */
    private String[][] completionWebhookHeaders = null;
    /** An array of results to be exported. The equivalent of downloading results manually. */
    private ExportResults[] results;
    /** Download the crawled version of the submitted text. */
    private ExportCrawledVersion crawledVersion;
    /** How many retries to send before giving up, from 1 to 12. Default: 3. */
    private Integer maxRetries = null;
    /** A custom payload that is sent back in the Export-Completed webhook. */
    private String developerPayload = null;
    /** Download the PDF report. Allowed only when {@code properties.pdf.create} was true on the scan. */
    private ExportPdfReport pdfReport = null;
    /** Export the AI Text Detection report. Allowed only when {@code properties.aiGeneratedText.detect} was true. */
    private ExportAiDetection aiDetection = null;
    /** Export the Grammar Checker report. Allowed only when {@code properties.writingFeedback.enable} was true. */
    private ExportWritingFeedback writingFeedback = null;
    /** Export the overview report. Allowed only when {@code properties.overview.enable} was true. */
    private ExportOverview overview = null;

    /**
     * Creates an export request with only the completion webhook. Add artifacts with the setters.
     * @param completionWebhook the URL called once the export is completed.
     */
    public CopyleaksExportModel(String completionWebhook) {
        this.completionWebhook = completionWebhook;
    }

    /**
     * @param completionWebhook This webhook event is triggered once the export is completed.
     * @param results An array of results to be exported, or null. The equivalent of downloading results manually.
     * @param crawledVersion Download the crawled version of the submitted text, or null.
     */
    public CopyleaksExportModel(String completionWebhook, ExportResults[] results, ExportCrawledVersion crawledVersion) {
        this.completionWebhook = completionWebhook;
        this.results = results;
        this.crawledVersion = crawledVersion;
    }

    /** @return the completion webhook URL. */
    public String getCompletionWebhook() {
        return completionWebhook;
    }

    /** @param completionWebhook the URL called once the export is completed. */
    public void setCompletionWebhook(String completionWebhook) {
        this.completionWebhook = completionWebhook;
    }

    /** @return the completion webhook headers, or null. */
    public String[][] getCompletionWebhookHeaders() {
        return completionWebhookHeaders;
    }

    /** @param completionWebhookHeaders headers added to the completion webhook request, as {@code [["name", "value"], ...]}. */
    public void setCompletionWebhookHeaders(String[][] completionWebhookHeaders) {
        this.completionWebhookHeaders = completionWebhookHeaders;
    }

    /** @return the results to export, or null. */
    public ExportResults[] getResults() {
        return results;
    }

    /** @param results the results to export, at most 1000. */
    public void setResults(ExportResults[] results) {
        this.results = results;
    }

    /** @return the crawled version target, or null. */
    public ExportCrawledVersion getCrawledVersion() {
        return crawledVersion;
    }

    /** @param crawledVersion where to upload the crawled version. */
    public void setCrawledVersion(ExportCrawledVersion crawledVersion) {
        this.crawledVersion = crawledVersion;
    }

    /** @return the number of retries, or null for the server default. */
    public Integer getMaxRetries() {
        return maxRetries;
    }

    /** @param maxRetries how many retries to send before giving up, from 1 to 12. */
    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    /** @return the developer payload, or null. */
    public String getDeveloperPayload() {
        return developerPayload;
    }

    /** @param developerPayload a custom payload sent back in the Export-Completed webhook. */
    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }

    /** @return the PDF report target, or null. */
    public ExportPdfReport getPdfReport() {
        return pdfReport;
    }

    /** @param pdfReport where to upload the PDF report. */
    public void setPdfReport(ExportPdfReport pdfReport) {
        this.pdfReport = pdfReport;
    }

    /** @return the AI Text Detection report target, or null. */
    public ExportAiDetection getAiDetection() {
        return aiDetection;
    }

    /** @param aiDetection where to upload the AI Text Detection report. */
    public void setAiDetection(ExportAiDetection aiDetection) {
        this.aiDetection = aiDetection;
    }

    /** @return the Grammar Checker report target, or null. */
    public ExportWritingFeedback getWritingFeedback() {
        return writingFeedback;
    }

    /** @param writingFeedback where to upload the Grammar Checker report. */
    public void setWritingFeedback(ExportWritingFeedback writingFeedback) {
        this.writingFeedback = writingFeedback;
    }

    /** @return the overview report target, or null. */
    public ExportOverview getOverview() {
        return overview;
    }

    /** @param overview where to upload the overview report. */
    public void setOverview(ExportOverview overview) {
        this.overview = overview;
    }
}
