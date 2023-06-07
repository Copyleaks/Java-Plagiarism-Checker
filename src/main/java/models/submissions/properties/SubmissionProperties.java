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

public class SubmissionProperties {
    private SubmissionWebhooks webhooks;
    /**
     * By default, Copyleaks will present the report in text format. If set to true, Copyleaks will also include html format.
     */
    private Boolean includeHtml = null;
    /**
     * Add custom developer payload that will then be provided on the webhooks.
     * https://api.copyleaks.com/documentation/v3/webhooks
     */
    private String developerPayload = null;
    /**
     * You can test the integration with the Copyleaks API for free using the sandbox mode.
     * <p>
     * You will be able to submit content for a scan and get back mock results, simulating the way Copyleaks will work to make sure that you successfully integrated with the API.
     * <p>
     * Turn off this feature on production environment.
     */
    private Boolean sandbox = null;
    /**
     * Specify the maximum life span of a scan in hours on the Copyleaks servers.
     * <p>
     * When expired, the scan will be deleted and will no longer be accessible.
     */
    private Integer experation = null;
    /**
     * You can control the level of plagiarism sensitivity that will be identified according to the speed of the scan.
     * If you prefer a faster scan with the results that contains the highest amount of plagiarism choose 1,
     * and if a slower, more comprehensive scan, that will also detect the smallest instances choose 5.
     */
    private Integer sensitivityLevel = null;
    /**
     * When set to true the submitted document will be checked for cheating. If a cheating will be detected, a scan alert will be added to the completed webhook.
     */
    private Boolean cheatDetection = null;
    /**
     * Types of content submission actions.
     * <p>
     * * Possible values:
     * * Scan: Start scan immediately.
     * * Check Credits: Check how many credits will be used for this scan.
     * * Index Only: Only index the file in the Copyleaks internal database. No credits will be used.
     */
    private SubmissionActions action = null;
    /**
     * Check inner properties for more details.
     */
    private SubmissionAuthor author = null;
    /**
     * Check inner properties for more details.
     */
    private SubmissionFilter filters = null;
    /**
     * Check inner properties for more details.
     */
    private SubmissionScanning scanning = null;
    /**
     * Check inner properties for more details.
     */
    private SubmissionIndexing indexing = null;
    /**
     * Check inner properties for more details.
     */
    private SubmissionExclude exclude = null;
    /**
     * Check inner properties for more details.
     */
    private SubmissionPDF pdf = null;
    /**
     * Check inner properties for more details.
     */
    private SubmissionSensitiveData sensitiveDataProtection = null;
    /**
     * Choose the algorithm goal. You can set this value depending on your use-case.
     */
    private SubmissionScanMethodAlgorithm scanMethodAlgorithm;
    /**
     * Add custom properties that will be attached to your document in a Copyleaks repository.
     */
    private SubmissionCustomMetadata customMetadata;
    /**
     * Detects whether the text was written by an AI.
     * 
     * Upon detection a scan alert of type "suspected-ai-text" will be added to the scan completion webhook.
     */
    private SubmissionAIGeneratedText aiGeneratedText;


    public SubmissionAIGeneratedText getAiGeneratedText() {
        return aiGeneratedText;
    }

    public void setAiGeneratedText(SubmissionAIGeneratedText aiGeneratedText) {
        this.aiGeneratedText = aiGeneratedText;
    }

    public SubmissionCustomMetadata getCustomMetadata() {
        return customMetadata;
    }

    public void setCustomMetadata(SubmissionCustomMetadata customMetadata) {
        this.customMetadata = customMetadata;
    }

    public SubmissionScanMethodAlgorithm getScanMethodAlgorithm() {
        return scanMethodAlgorithm;
    }

    public void setScanMethodAlgorithm(SubmissionScanMethodAlgorithm scanMethodAlgorithm) {
        this.scanMethodAlgorithm = scanMethodAlgorithm;
    }

    public SubmissionProperties(SubmissionWebhooks webhooks) {
        this.webhooks = webhooks;
    }

    public SubmissionWebhooks getWebhooks() {
        return webhooks;
    }

    public void setWebhooks(SubmissionWebhooks webhooks) {
        this.webhooks = webhooks;
    }

    public Boolean getIncludeHtml() {
        return includeHtml;
    }

    public void setIncludeHtml(Boolean includeHtml) {
        this.includeHtml = includeHtml;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }

    public Boolean getSandbox() {
        return sandbox;
    }

    public void setSandbox(Boolean sandbox) {
        this.sandbox = sandbox;
    }

    public Integer getExperation() {
        return experation;
    }

    public void setExperation(Integer experation) {
        this.experation = experation;
    }

    public Integer getSensitivityLevel() {
        return sensitivityLevel;
    }

    public void setSensitivityLevel(Integer sensitivityLevel) {
        this.sensitivityLevel = sensitivityLevel;
    }

    public Boolean getCheatDetection() {
        return cheatDetection;
    }

    public void setCheatDetection(Boolean cheatDetection) {
        this.cheatDetection = cheatDetection;
    }

    public SubmissionActions getAction() {
        return action;
    }

    public void setAction(SubmissionActions action) {
        this.action = action;
    }

    public SubmissionAuthor getAuthor() {
        return author;
    }

    public void setAuthor(SubmissionAuthor author) {
        this.author = author;
    }

    public SubmissionFilter getFilters() {
        return filters;
    }

    public void setFilters(SubmissionFilter filters) {
        this.filters = filters;
    }

    public SubmissionScanning getScanning() {
        return scanning;
    }

    public void setScanning(SubmissionScanning scanning) {
        this.scanning = scanning;
    }

    public SubmissionIndexing getIndexing() {
        return indexing;
    }

    public void setIndexing(SubmissionIndexing indexing) {
        this.indexing = indexing;
    }

    public SubmissionExclude getExclude() {
        return exclude;
    }

    public void setExclude(SubmissionExclude exclude) {
        this.exclude = exclude;
    }

    public SubmissionPDF getPdf() {
        return pdf;
    }

    public void setPdf(SubmissionPDF pdf) {
        this.pdf = pdf;
    }

    public SubmissionSensitiveData getSensitiveDataProtection() {
        return sensitiveDataProtection;
    }

    public void setSensitiveDataProtection(SubmissionSensitiveData sensitiveDataProtection) {
        this.sensitiveDataProtection = sensitiveDataProtection;
    }
}
