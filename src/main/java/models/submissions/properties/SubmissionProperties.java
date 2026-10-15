package models.submissions.properties;

public class SubmissionProperties {
    private SubmissionWebhooks webhooks;
    /** Also return the report in HTML format, in addition to text. */
    private Boolean includeHtml = null;
    /** Sent back in the webhooks. */
    private String developerPayload = null;
    /** Return mock results without using credits. Turn off in production. */
    private Boolean sandbox = null;
    /** 1 (fastest, finds the most plagiarism) to 5 (slowest, also finds the smallest matches). */
    private Integer sensitivityLevel = null;
    /** Check the document for cheating. If found, an alert is added to the completed webhook. */
    private Boolean cheatDetection = null;
    private SubmissionActions action = null;
    private SubmissionAuthor author = null;
    private SubmissionCourse course = null;
    private SubmissionAssignment assignment= null;
    private SubmissionInstitution institution= null;
    private SubmissionFilter filters = null;
    private SubmissionScanning scanning = null;
    private SubmissionIndexing indexing = null;
    private SubmissionExclude exclude = null;
    private SubmissionPDF pdf = null;
    private SubmissionSensitiveData sensitiveDataProtection = null;
    /** Hours the scan is kept on the Copyleaks servers before it is deleted, 1 to 2880. */
    private Integer expiration = null; 

    private SubmissionScanMethodAlgorithm scanMethodAlgorithm;
    /** Key and value pairs attached to the document in a Copyleaks repository, at most 20. */
    private SubmissionCustomMetadata[] customMetadata;
    /** AI text detection. When found, a "suspected-ai-text" alert is added to the completed webhook. */
    private SubmissionAIGeneratedText aiGeneratedText;
    /** Grammar Checker: grammar, spelling and sentence structure corrections. */
    private SubmissionWritingFeedback writingFeedback;
    /** Language of the PDF report. */
    private String displayLanguage;   
    /** Beta. */
    private SubmissionOverview overview;
    /** Find online sources that are suspected to contain AI-generated text. */
    private SubmissionAiSourceMatch aiSourceMatch;

    /**
     * Case-sensitive IANA time zone for the scan time in the PDF report, such as "America/New_York".
     * Default: the user's country time zone, or UTC.
     */
    private String scanTimeZone;

    private SubmissionInternalAiSourceMatch internalAiSourceMatch;

    private SubmissionReferences references;

    public String getScanTimeZone() {
        return scanTimeZone;
    }

    public void setScanTimeZone(String scanTimeZone) {
        this.scanTimeZone = scanTimeZone;
    }

    public SubmissionInternalAiSourceMatch getInternalAiSourceMatch() {
        return internalAiSourceMatch;
    }

    public void setInternalAiSourceMatch(SubmissionInternalAiSourceMatch internalAiSourceMatch) {
        this.internalAiSourceMatch = internalAiSourceMatch;
    }

    public SubmissionReferences getReferences() {
        return references;
    }

    public void setReferences(SubmissionReferences references) {
        this.references = references;
    }

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

    public SubmissionCustomMetadata[] getCustomMetadata() {
        return customMetadata;
    }

    public void setCustomMetadata(SubmissionCustomMetadata[] customMetadata) {
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

    /** @deprecated Misspelled. Use {@link #getExpiration()}. */
    @Deprecated
    public Integer getExperation() {
        return expiration;
    }

    /** @deprecated Misspelled. Use {@link #setExpiration(Integer)}. */
    @Deprecated
    public void setExperation(Integer experation) {
        this.expiration = experation;
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
