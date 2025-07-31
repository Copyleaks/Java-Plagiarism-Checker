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
    private SubmissionCourse course = null;
    /**
     * Check inner properties for more details.
     */
    private SubmissionAssignment assignment= null;
    /**
     * Check inner properties for more details.
     */
    private SubmissionInstitution institution= null;
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
     * Specify the maximum life span of a scan in hours on the Copyleaks servers. When expired, the scan will be deleted and will no longer be accessible. 
     * */
    private Integer expiration = null; 

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
    /**
     * Enable automated Writing Assistant . This feature includes grammar checking, spell checking and sentence structure corrections.
     */
    private SubmissionWritingFeedback writingFeedback;
    /**
     * When specified, the PDF report will be generated in the selected language. 
     * Future updates may also apply this setting to the overview and other components
     */
    private String displayLanguage;   
    /**
     * BETA - Enable Gen-AI Overview feature to extract key insights from the scan data.
     * For further information, please check the Overview for a detailed breakdown of its structure and usage.
     */
    private SubmissionOverview overview;
    /**
     * The AI Source Match feature enhances plagiarism detection by identifying online sources that are suspected of containing AI-generated text. 
     * This allows you to find instances of potential plagiarism and understand if the matched source content itself might have been created by an AI.
     */
    private SubmissionAiSourceMatch aiSourceMatch;

        public SubmissionAiSourceMatch getAiSourceMatch() {
        return aiSourceMatch;
    }

    public void setAiSourceMatch(SubmissionAiSourceMatch aiSourceMatch) {
        this.aiSourceMatch = aiSourceMatch;
    }

    public SubmissionOverview getOverview() {
        return overview;
    }

    public void setOverview(SubmissionOverview overview) {
        this.overview = overview;
    }

    public String getDisplayLanguage() {
        return displayLanguage;
    }

    public void setDisplayLanguage(String displayLanguage) {
        this.displayLanguage = displayLanguage;
    }

    public SubmissionAIGeneratedText getAiGeneratedText() {
        return aiGeneratedText;
    }

    public void setAiGeneratedText(SubmissionAIGeneratedText aiGeneratedText) {
        this.aiGeneratedText = aiGeneratedText;
    }

    public SubmissionWritingFeedback getWritingFeedback() {
        return writingFeedback;
    }

    public void setWritingFeedback(SubmissionWritingFeedback writingFeedback) {
        this.writingFeedback = writingFeedback;
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
    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
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
    public SubmissionCourse getCourse() {
        return course;
    }

    public SubmissionAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(SubmissionAssignment assignment) {
        this.assignment = assignment;
    }

    public SubmissionInstitution getInstitution() {
        return institution;
    }

    public void setInstitution(SubmissionInstitution institution) {
        this.institution = institution;
    }
    
    public void setCourse(SubmissionCourse course) {
        this.course = course;
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
