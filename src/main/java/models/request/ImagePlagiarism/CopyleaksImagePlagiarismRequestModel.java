package models.request.ImagePlagiarism;

/** Image Plagiarism Detection request, uploaded as {@code multipart/form-data}. */
public class CopyleaksImagePlagiarismRequestModel {

    private byte[] image;
    private String filename;
    private boolean sandbox;
    /** Echoed back in the response. */
    private String developerPayload;

    /**
     * @param image    the image file bytes, under 20 MB and at most 75 megapixels.
     * @param filename file name with extension (.jpg, .jpeg, .png, .gif, .bmp, .webp, .ico), max 255 chars.
     */
    public CopyleaksImagePlagiarismRequestModel(byte[] image, String filename) {
        this.image = image;
        this.filename = filename;
    }

    /** @param sandbox return mock results without using credits. */
    public CopyleaksImagePlagiarismRequestModel(byte[] image, String filename, boolean sandbox) {
        this(image, filename);
        this.sandbox = sandbox;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }
}
