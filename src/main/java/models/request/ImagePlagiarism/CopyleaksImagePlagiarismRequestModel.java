package models.request.ImagePlagiarism;

/** Image Plagiarism Detection request, uploaded as {@code multipart/form-data}. */
public class CopyleaksImagePlagiarismRequestModel {

    private byte[] image;
    private String filename;
    private boolean sandbox;
    private String developerPayload;

    /**
     * Sandbox mode off.
     * @param image    the image bytes, under 20 MB and at most 75 megapixels.
     * @param filename file name with extension (.jpg, .jpeg, .png, .gif, .bmp, .webp, .ico), max 255 chars.
     */
    public CopyleaksImagePlagiarismRequestModel(byte[] image, String filename) {
        this.image = image;
        this.filename = filename;
    }

    /**
     * @param image    the image bytes, under 20 MB and at most 75 megapixels.
     * @param filename file name with extension (.jpg, .jpeg, .png, .gif, .bmp, .webp, .ico), max 255 chars.
     * @param sandbox  return mock results without using credits.
     */
    public CopyleaksImagePlagiarismRequestModel(byte[] image, String filename, boolean sandbox) {
        this(image, filename);
        this.sandbox = sandbox;
    }

    /** @return the image bytes. */
    public byte[] getImage() {
        return image;
    }

    /** Replaces the {@code image} constructor argument. */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /** @return the image file name. */
    public String getFilename() {
        return filename;
    }

    /** Replaces the {@code filename} constructor argument. */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /** @return true when sandbox mode is on. */
    public boolean isSandbox() {
        return sandbox;
    }

    /** Replaces the {@code sandbox} constructor argument. */
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    /** @return the developer payload, or null. */
    public String getDeveloperPayload() {
        return developerPayload;
    }

    /** Custom payload echoed back in the response. */
    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }
}
