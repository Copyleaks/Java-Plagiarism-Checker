package models.submissions.properties;

public class SubmissionScanningExclude {
    /** Leave out your submissions whose ID matches; {@code *} matches any characters, {@code .} one non-space. */
    private String idPattern = null;

    /** Leave out results that link to these domains. At most 10, each 1 to 256 characters. */
    private String[] backlinksDomains = null;

    /** Leave out results that contain any of these phrases. At most 5, each 1 to 30 characters. */
    private String[] text = null;

    public String getIdPattern() {
        return idPattern;
    }

    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }

    public String[] getBacklinksDomains() {
        return backlinksDomains;
    }

    public void setBacklinksDomains(String[] backlinksDomains) {
        this.backlinksDomains = backlinksDomains;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}
