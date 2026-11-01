package models.submissions.properties;

public class SubmissionScanningExclude {
    private String idPattern = null;

    private String[] backlinksDomains = null;

    private String[] text = null;

    /** @return the ID pattern, or null. */
    public String getIdPattern() {
        return idPattern;
    }

    /**
     * Leave out your submissions whose ID matches, from batch, internal database and repository results.
     * {@code *} matches any characters, {@code .} one non-space.
     */
    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }

    /** @return the backlink domains to exclude, or null. */
    public String[] getBacklinksDomains() {
        return backlinksDomains;
    }

    /** Leave out results that link to these domains. At most 10, each 1 to 256 characters. */
    public void setBacklinksDomains(String[] backlinksDomains) {
        this.backlinksDomains = backlinksDomains;
    }

    /** @return the phrases to exclude, or null. */
    public String[] getText() {
        return text;
    }

    /** Leave out results that contain any of these phrases. At most 5, each 1 to 30 characters. */
    public void setText(String[] text) {
        this.text = text;
    }
}
