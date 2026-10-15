package models.exports;

/** Exports scan artifacts to your server. Only {@code completionWebhook} is required; add artifacts with setters. */
public class CopyleaksExportModel {

    /** URL called once the export is completed. */
    private String completionWebhook;
    /** Headers added to the completion webhook request, as {@code [["name", "value"], ...]}. */
    private String[][] completionWebhookHeaders = null;
    /** Results to export, at most 1000. */
    private ExportResults[] results;
    private ExportCrawledVersion crawledVersion;
    /** Retries before giving up, from 1 to 12. Default: 3. */
    private Integer maxRetries = null;
    /** Sent back in the Export-Completed webhook. */
    private String developerPayload = null;
    /** Allowed only when {@code properties.pdf.create} was true on the scan. */
    private ExportPdfReport pdfReport = null;
    /** Allowed only when {@code properties.aiGeneratedText.detect} was true on the scan. */
    private ExportAiDetection aiDetection = null;
    /** Grammar Checker report. Allowed only when {@code properties.writingFeedback.enable} was true on the scan. */
    private ExportWritingFeedback writingFeedback = null;
    /** Allowed only when {@code properties.overview.enable} was true on the scan. */
    private ExportOverview overview = null;

    public CopyleaksExportModel(String completionWebhook) {
        this.completionWebhook = completionWebhook;
    }

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

    public String[][] getCompletionWebhookHeaders() {
        return completionWebhookHeaders;
    }

    public void setCompletionWebhookHeaders(String[][] completionWebhookHeaders) {
        this.completionWebhookHeaders = completionWebhookHeaders;
    }

    public ExportResults[] getResults() {
        return results;
    }

    public void setResults(ExportResults[] results) {
        this.results = results;
    }

    public ExportCrawledVersion getCrawledVersion() {
        return crawledVersion;
    }

    public void setCrawledVersion(ExportCrawledVersion crawledVersion) {
        this.crawledVersion = crawledVersion;
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

    public ExportAiDetection getAiDetection() {
        return aiDetection;
    }

    public void setAiDetection(ExportAiDetection aiDetection) {
        this.aiDetection = aiDetection;
    }

    public ExportWritingFeedback getWritingFeedback() {
        return writingFeedback;
    }

    public void setWritingFeedback(ExportWritingFeedback writingFeedback) {
        this.writingFeedback = writingFeedback;
    }

    public ExportOverview getOverview() {
        return overview;
    }

    public void setOverview(ExportOverview overview) {
        this.overview = overview;
    }
}
