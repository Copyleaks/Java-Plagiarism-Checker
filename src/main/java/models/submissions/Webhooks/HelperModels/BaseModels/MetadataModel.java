package models.submissions.Webhooks.HelperModels.BaseModels;

public class MetadataModel {

    /** The submitted URL after all redirects. */
    private String finalUrl;

    private String canonicalUrl;

    private String publishDate;

    private String creationDate;

    private String lastModificationDate;

    private String author;

    private String organization;

    private String filename;

    public String getFinalUrl() {
        return finalUrl;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getLastModificationDate() {
        return lastModificationDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getOrganization() {
        return organization;
    }

    public String getFilename() {
        return filename;
    }
}
