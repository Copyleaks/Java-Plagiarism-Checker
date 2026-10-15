package models.response.ImagePlagiarism;

public class CopyleaksImagePlagiarismScannedImageModel {

    private String scanId;

    private int expectedCredits;

    /** Credits the scan used. */
    private int actualCredits;

    /** ISO 8601, UTC. */
    private String creationTime;

    /** In pixels. */
    private int width;

    /** In pixels. */
    private int height;

    private String filename;

    public String getScanId() {
        return scanId;
    }

    public int getExpectedCredits() {
        return expectedCredits;
    }

    public int getActualCredits() {
        return actualCredits;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFilename() {
        return filename;
    }
}
