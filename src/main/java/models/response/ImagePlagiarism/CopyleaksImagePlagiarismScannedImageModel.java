package models.response.ImagePlagiarism;

/** Information about the submitted image and the scan. */
public class CopyleaksImagePlagiarismScannedImageModel {

    private String scanId;

    private int expectedCredits;

    private int actualCredits;

    private String creationTime;

    private int width;

    private int height;

    private String filename;

    /** Scan ID given in the request path. */
    public String getScanId() {
        return scanId;
    }

    /** Credits the scan was expected to consume. */
    public int getExpectedCredits() {
        return expectedCredits;
    }

    /** Credits the scan used. */
    public int getActualCredits() {
        return actualCredits;
    }

    /** When the scan was created, ISO 8601 in UTC. */
    public String getCreationTime() {
        return creationTime;
    }

    /** Image width in pixels. */
    public int getWidth() {
        return width;
    }

    /** Image height in pixels. */
    public int getHeight() {
        return height;
    }

    /** File name given in the request. */
    public String getFilename() {
        return filename;
    }
}
