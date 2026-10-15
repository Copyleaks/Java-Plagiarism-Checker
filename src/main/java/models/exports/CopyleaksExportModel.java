package models.exports;

/** Exports scan artifacts to your server. Only {@code completionWebhook} is required; add artifacts with setters. */
public class CopyleaksExportModel {

    private String completionWebhook;
    private String[][] completionWebhookHeaders = null;
    private ExportResults[] results;
    private ExportCrawledVersion crawledVersion;
    private Integer maxRetries = null;
    private String developerPayload = null;
    private ExportPdfReport pdfReport = null;
    private ExportAiDetection aiDetection = null;
    private ExportWritingFeedback writingFeedback = null;
    private ExportOverview overview = null;

    /** @param completionWebhook URL called once the export is completed. */
    public CopyleaksExportModel(String completionWebhook) {
        this.completionWebhook = completionWebhook;
    }

    /**
     * @param completionWebhook URL called once the export is completed.
     * @param results           see {@link #setResults(ExportResults[])}, or null.
     * @param crawledVersion    see {@link #setCrawledVersion(ExportCrawledVersion)}, or null.
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

    /** Replaces the {@code completionWebhook} constructor argument. */
    public void setCompletionWebhook(String completionWebhook) {
        this.completionWebhook = completionWebhook;
    }

    /** @return the completion webhook headers, or null. */
    public String[][] getCompletionWebhookHeaders() {
        return completionWebhookHeaders;
    }

    /** Headers added to the completion webhook request, as {@code [["name", "value"], ...]}. */
    public void setCompletionWebhookHeaders(String[][] completionWebhookHeaders) {
        this.completionWebhookHeaders = completionWebhookHeaders;
    }

    /** @return the results to export, or null. */
    public ExportResults[] getResults() {
        return results;
    }

    /** Results to export, at most 1000. */
    public void setResults(ExportResults[] results) {
        this.results = results;
    }

    /** @return the crawled version target, or null. */
    public ExportCrawledVersion getCrawledVersion() {
        return crawledVersion;
    }

    /** Where to upload the crawled version of the submitted content. */
    public void setCrawledVersion(ExportCrawledVersion crawledVersion) {
        this.crawledVersion = crawledVersion;
    }

    /** @return the number of retries, or null for the server default. */
    public Integer getMaxRetries() {
        return maxRetries;
    }

    /** Retries before giving up, from 1 to 12. Default: 3. */
    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    /** @return the developer payload, or null. */
    public String getDeveloperPayload() {
        return developerPayload;
    }

    /** Custom payload sent back in the Export-Completed webhook. */
    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }

    /** @return the PDF report target, or null. */
    public ExportPdfReport getPdfReport() {
        return pdfReport;
    }

    /** Where to upload the PDF report. Allowed only when {@code properties.pdf.create} was true on the scan. */
    public void setPdfReport(ExportPdfReport pdfReport) {
        this.pdfReport = pdfReport;
    }

    /** @return the AI Text Detection report target, or null. */
    public ExportAiDetection getAiDetection() {
        return aiDetection;
    }

    /**
     * Where to upload the AI Text Detection report. Allowed only when {@code properties.aiGeneratedText.detect}
     * was true on the scan.
     */
    public void setAiDetection(ExportAiDetection aiDetection) {
        this.aiDetection = aiDetection;
    }

    /** @return the Grammar Checker report target, or null. */
    public ExportWritingFeedback getWritingFeedback() {
        return writingFeedback;
    }

    /**
     * Where to upload the Grammar Checker report. Allowed only when {@code properties.writingFeedback.enable}
     * was true on the scan.
     */
    public void setWritingFeedback(ExportWritingFeedback writingFeedback) {
        this.writingFeedback = writingFeedback;
    }

    /** @return the overview report target, or null. */
    public ExportOverview getOverview() {
        return overview;
    }

    /**
     * Where to upload the overview report. Allowed only when {@code properties.overview.enable}
     * was true on the scan.
     */
    public void setOverview(ExportOverview overview) {
        this.overview = overview;
    }
}
