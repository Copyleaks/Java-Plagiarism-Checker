package models.submissions.properties;

public class SubmissionPDF {
    /** Generate a PDF report of the scan. */
    private boolean create;
    private String title;
    private String largeLogo;
    /** Align the report text from right to left. */
    private boolean rtl;
    /** Default: 1. Overridden by {@code reportVersion}. */
    private SubmissionPdfVersion version;
    private SubmissionPdfColors colors;
    /** The PDF report version to generate. Overrides {@code version}. */
    private String reportVersion;

    public boolean isCreate() {
        return create;
    }
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
    public void setRtl(boolean rtl) {
        this.rtl = rtl;
    }
    public SubmissionPdfVersion getVersion() {
        return version;
    }
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
    
    public void setReportVersion(String reportVersion) {
        this.reportVersion = reportVersion;
    }
  
}
