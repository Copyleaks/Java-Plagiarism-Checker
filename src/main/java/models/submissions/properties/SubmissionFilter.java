package models.submissions.properties;

public class SubmissionFilter {
    /** Match identical words. */
    private Boolean identicalEnabled = null;

    /** Match nearly identical words, such as "slow" and "slowly". */
    private Boolean minorChangesEnabled = null;

    /** Match paraphrased content. */
    private Boolean relatedMeaningEnabled = null;

    /** Keep only results with at least this many copied words. */
    private Integer minCopiedWords = null;

    /** Block explicit adult content from the results. Not 100% effective. */
    private Boolean safeSearch = null;

    /** Domains to include or exclude, see {@code domainsMode}. */
    private String[] domains = null;

    private SubmissionFilterDomainsMode domainsMode = null;

    /** Allow results from the same domain as the submitted URL. */
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

    public void setMinCopiedWords(Integer minCopiedWords) {
        this.minCopiedWords = minCopiedWords;
    }

    public Boolean isSafeSearch() {
        return safeSearch;
    }

    public String[] getDomains() {
        return domains;
    }

    public void setDomains(String[] domains) {
        this.domains = domains;
    }

    public SubmissionFilterDomainsMode getDomainsMode() {
        return domainsMode;
    }

    public void setDomainsMode(SubmissionFilterDomainsMode domainsMode) {
        this.domainsMode = domainsMode;
    }

    public void setIdenticalEnabled(Boolean identicalEnabled) {
        this.identicalEnabled = identicalEnabled;
    }

    public void setMinorChangesEnabled(Boolean minorChangesEnabled) {
        this.minorChangesEnabled = minorChangesEnabled;
    }

    public void setRelatedMeaningEnabled(Boolean relatedMeaningEnabled) {
        this.relatedMeaningEnabled = relatedMeaningEnabled;
    }

    public void setSafeSearch(Boolean safeSearch) {
        this.safeSearch = safeSearch;
    }

    public Boolean getAllowSameDomain() {
        return allowSameDomain;
    }

    public void setAllowSameDomain(Boolean allowSameDomain) {
        this.allowSameDomain = allowSameDomain;
    }
}
