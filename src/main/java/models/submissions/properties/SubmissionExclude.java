package models.submissions.properties;

public class SubmissionExclude {
    private Boolean quotes = null;

    private Boolean references = null;

    private Boolean tableOfContents = null;

    private Boolean titles = null;

    /** For HTML documents, exclude text repeated across the site, such as headers and footers. */
    private Boolean htmlTemplate = null;

    private Boolean citations;

    /** Exclude text found in these documents. */
    private String[] documentTemplateIds;

    /** Exclude parts of source code. */
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
