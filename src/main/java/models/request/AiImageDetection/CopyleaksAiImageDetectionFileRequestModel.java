package models.request.AiImageDetection;

/** AI Image Detection request with the image bytes uploaded as {@code multipart/form-data}, without base64. */
public class CopyleaksAiImageDetectionFileRequestModel extends CopyleaksAiImageDetectionBaseRequestModel {

    private byte[] image;
    private String filename;
    private String model;
    private boolean sandbox;

    /**
     * Sandbox mode off.
     * @param image    the image bytes. 512x512 to 6000x4500 px, under 32 MB.
     * @param filename file name with extension (.png, .jpg, .jpeg, .bmp, .webp, .heic, .heif).
     * @param model    see {@link models.constants.CopyleaksAiImageDetectionModels}.
     */
    public CopyleaksAiImageDetectionFileRequestModel(byte[] image, String filename, String model) {
        this.image = image;
        this.filename = filename;
        this.model = model;
    }

    /**
     * @param image    the image bytes. 512x512 to 6000x4500 px, under 32 MB.
     * @param filename file name with extension (.png, .jpg, .jpeg, .bmp, .webp, .heic, .heif).
     * @param model    see {@link models.constants.CopyleaksAiImageDetectionModels}.
     * @param sandbox  return mock results without using credits.
     */
    public CopyleaksAiImageDetectionFileRequestModel(byte[] image, String filename, String model, boolean sandbox) {
        this(image, filename, model);
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

    /** @return the detection model. */
    public String getModel() {
        return model;
    }

    /** Replaces the {@code model} constructor argument. */
    public void setModel(String model) {
        this.model = model;
    }

    /** @return true when sandbox mode is on. */
    public boolean isSandbox() {
        return sandbox;
    }

    /** Replaces the {@code sandbox} constructor argument. */
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }
}
