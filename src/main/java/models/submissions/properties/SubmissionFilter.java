package models.submissions.properties;

public class SubmissionFilter {
    private Boolean identicalEnabled = null;

    private Boolean minorChangesEnabled = null;

    private Boolean relatedMeaningEnabled = null;

    private Integer minCopiedWords = null;

    private Boolean safeSearch = null;

    private String[] domains = null;

    private SubmissionFilterDomainsMode domainsMode = null;

    private Boolean allowSameDomain = false;

    public Boolean isIdenticalEnabled() {
        return identicalEnabled;
    }

    public Boolean isMinorChangesEnabled() {
        return minorChangesEnabled;
    }

    public Boolean isRelatedMeaningEnabled() {
        return relatedMeaningEnabled;
    }

    public Integer getMinCopiedWords() {
        return minCopiedWords;
    }

    /** Keep only results with at least this many copied words. */
    public void setMinCopiedWords(Integer minCopiedWords) {
        this.minCopiedWords = minCopiedWords;
    }

    public Boolean isSafeSearch() {
        return safeSearch;
    }

    public String[] getDomains() {
        return domains;
    }

    /** Domains to include or exclude, see {@code domainsMode}. */
    public void setDomains(String[] domains) {
        this.domains = domains;
    }

    public SubmissionFilterDomainsMode getDomainsMode() {
        return domainsMode;
    }

    public void setDomainsMode(SubmissionFilterDomainsMode domainsMode) {
        this.domainsMode = domainsMode;
    }

    /** Match identical words. */
    public void setIdenticalEnabled(Boolean identicalEnabled) {
        this.identicalEnabled = identicalEnabled;
    }

    /** Match nearly identical words, such as "slow" and "slowly". */
    public void setMinorChangesEnabled(Boolean minorChangesEnabled) {
        this.minorChangesEnabled = minorChangesEnabled;
    }

    /** Match paraphrased content. */
    public void setRelatedMeaningEnabled(Boolean relatedMeaningEnabled) {
        this.relatedMeaningEnabled = relatedMeaningEnabled;
    }

    /** Block explicit adult content from the results. Not 100% effective. */
    public void setSafeSearch(Boolean safeSearch) {
        this.safeSearch = safeSearch;
    }

    public Boolean getAllowSameDomain() {
        return allowSameDomain;
    }

    /** Allow results from the same domain as the submitted URL. */
    public void setAllowSameDomain(Boolean allowSameDomain) {
        this.allowSameDomain = allowSameDomain;
    }
}
