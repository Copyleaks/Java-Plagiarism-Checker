package models.submissions.properties;

public class SubmissionProperties {
    private SubmissionWebhooks webhooks;
    private Boolean includeHtml = null;
    private String developerPayload = null;
    private Boolean sandbox = null;
    private Integer sensitivityLevel = null;
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
    private Integer expiration = null; 

    private SubmissionScanMethodAlgorithm scanMethodAlgorithm;
    private SubmissionCustomMetadata[] customMetadata;
    private SubmissionAIGeneratedText aiGeneratedText;
    private SubmissionWritingFeedback writingFeedback;
    private String displayLanguage;   
    private SubmissionOverview overview;
    private SubmissionAiSourceMatch aiSourceMatch;

    private String scanTimeZone;

    private SubmissionInternalAiSourceMatch internalAiSourceMatch;

    private SubmissionReferences references;

    /** @return the scan time zone, or null. */
    public String getScanTimeZone() {
        return scanTimeZone;
    }

    /**
     * Case-sensitive IANA time zone for the scan time in the PDF report, such as "America/New_York".
     * Default: the user's country time zone, or UTC.
     */
    public void setScanTimeZone(String scanTimeZone) {
        this.scanTimeZone = scanTimeZone;
    }

    /** @return the Internal AI Source Match settings, or null. */
    public SubmissionInternalAiSourceMatch getInternalAiSourceMatch() {
        return internalAiSourceMatch;
    }

    /** Find sources in the Copyleaks internal AI database that contain AI-generated text. */
    public void setInternalAiSourceMatch(SubmissionInternalAiSourceMatch internalAiSourceMatch) {
        this.internalAiSourceMatch = internalAiSourceMatch;
    }

    /** @return the Citation Checker settings, or null. */
    public SubmissionReferences getReferences() {
        return references;
    }

    /** Citation Checker: detect, parse and validate the references in the submitted text. */
    public void setReferences(SubmissionReferences references) {
        this.references = references;
    }

        public SubmissionAiSourceMatch getAiSourceMatch() {
        return aiSourceMatch;
    }

    /** Find online sources that are suspected to contain AI-generated text. */
    public void setAiSourceMatch(SubmissionAiSourceMatch aiSourceMatch) {
        this.aiSourceMatch = aiSourceMatch;
    }

    public SubmissionOverview getOverview() {
        return overview;
    }

    /** Gen-AI Overview: key insights extracted from the scan data. Beta. */
    public void setOverview(SubmissionOverview overview) {
        this.overview = overview;
    }

    public String getDisplayLanguage() {
        return displayLanguage;
    }

    /** Language of the PDF report. */
    public void setDisplayLanguage(String displayLanguage) {
        this.displayLanguage = displayLanguage;
    }

    public SubmissionAIGeneratedText getAiGeneratedText() {
        return aiGeneratedText;
    }

    /** AI text detection. When found, a "suspected-ai-text" alert is added to the completed webhook. */
    public void setAiGeneratedText(SubmissionAIGeneratedText aiGeneratedText) {
        this.aiGeneratedText = aiGeneratedText;
    }

    public SubmissionWritingFeedback getWritingFeedback() {
        return writingFeedback;
    }

    /** Grammar Checker: grammar, spelling and sentence structure corrections. */
    public void setWritingFeedback(SubmissionWritingFeedback writingFeedback) {
        this.writingFeedback = writingFeedback;
    }

    /** @return the custom properties attached to the document, or null. */
    public SubmissionCustomMetadata[] getCustomMetadata() {
        return customMetadata;
    }

    /** Key and value pairs attached to the document in a Copyleaks repository, at most 20. */
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

    /** Hours the scan is kept on the Copyleaks servers before it is deleted, 1 to 2880. */
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
    /** @param webhooks where Copyleaks sends the scan events. */
    public SubmissionProperties(SubmissionWebhooks webhooks) {
        this.webhooks = webhooks;
    }

    public SubmissionWebhooks getWebhooks() {
        return webhooks;
    }

    /** Replaces the {@code webhooks} constructor argument. */
    public void setWebhooks(SubmissionWebhooks webhooks) {
        this.webhooks = webhooks;
    }

    public Boolean getIncludeHtml() {
        return includeHtml;
    }

    /** Also return the report in HTML format, in addition to text. */
    public void setIncludeHtml(Boolean includeHtml) {
        this.includeHtml = includeHtml;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    /** Custom payload sent back in the webhooks. */
    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }

    public Boolean getSandbox() {
        return sandbox;
    }

    /** Return mock results without using credits. Turn off in production. */
    public void setSandbox(Boolean sandbox) {
        this.sandbox = sandbox;
    }

    /**
     * Misspelled name of {@link #getExpiration()}.
     * @deprecated Use {@link #getExpiration()}.
     */
    @Deprecated
    public Integer getExperation() {
        return expiration;
    }

    /**
     * Misspelled name of {@link #setExpiration(Integer)}.
     * @deprecated Use {@link #setExpiration(Integer)}.
     */
    @Deprecated
    public void setExperation(Integer experation) {
        this.expiration = experation;
    }

    public Integer getSensitivityLevel() {
        return sensitivityLevel;
    }

    /**
     * Plagiarism sensitivity, 1 (fastest, finds the most plagiarism) to 5 (slowest, also finds the smallest
     * matches).
     */
    public void setSensitivityLevel(Integer sensitivityLevel) {
        this.sensitivityLevel = sensitivityLevel;
    }

    public Boolean getCheatDetection() {
        return cheatDetection;
    }

    /** Check the document for cheating. If found, an alert is added to the completed webhook. */
    public void setCheatDetection(Boolean cheatDetection) {
        this.cheatDetection = cheatDetection;
    }

    public SubmissionActions getAction() {
        return action;
    }

    /** Scan now, only check the credits the scan would use, or only index it; see {@link SubmissionActions}. */
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
