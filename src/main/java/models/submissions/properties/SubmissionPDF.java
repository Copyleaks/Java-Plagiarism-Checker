package models.submissions.properties;

public class SubmissionPDF {
    private boolean create;
    private String title;
    private String largeLogo;
    private boolean rtl;
    private SubmissionPdfVersion version;
    private SubmissionPdfColors colors;
    private String reportVersion;

    public boolean isCreate() {
        return create;
    }
    /** Generate a PDF report of the scan. */
    public void setCreate(boolean create) {
        this.create = create;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLargeLogo() {
        return largeLogo;
    }
    public void setLargeLogo(String largeLogo) {
        this.largeLogo = largeLogo;
    }
    public boolean isRtl() {
        return rtl;
    }
    /** Align the report text from right to left. */
    public void setRtl(boolean rtl) {
        this.rtl = rtl;
    }
    public SubmissionPdfVersion getVersion() {
        return version;
    }
    /** Report version. Default: 1. Overridden by {@code reportVersion}. */
    public void setVersion(SubmissionPdfVersion version) {
        this.version = version;
    }
    public SubmissionPdfColors getColors() {
        return colors;
    }
    public void setColors(SubmissionPdfColors colors) {
        this.colors = colors;
    }
    public String getReportVersion() {
        return reportVersion;
    }
    
    /** The PDF report version to generate. Overrides {@code version}. */
    public void setReportVersion(String reportVersion) {
        this.reportVersion = reportVersion;
    }
  
}
