package models.request.AiImageDetection;

/** AI Image Detection request with the image bytes uploaded as {@code multipart/form-data}, without base64. */
public class CopyleaksAiImageDetectionFileRequestModel {

    private byte[] image;
    private String filename;
    private String model;
    /** Return mock results without using credits. */
    private boolean sandbox;
    /** "binary" (default), "heatmap" or "none", see {@link models.constants.CopyleaksAiImageMaskTypes}. */
    private String maskType;
    /** Objects to locate and segment, at most 3. Null turns the feature off. */
    private CopyleaksObjectDetectionItem[] objectDetection;

    /**
     * @param image    the image bytes. 512x512 to 6000x4500 px, under 32 MB.
     * @param filename file name with extension (.png, .jpg, .jpeg, .bmp, .webp, .heic, .heif).
     * @param model    see {@link models.constants.CopyleaksAiImageDetectionModels}.
     */
    public CopyleaksAiImageDetectionFileRequestModel(byte[] image, String filename, String model) {
        this.image = image;
        this.filename = filename;
        this.model = model;
    }

    public CopyleaksAiImageDetectionFileRequestModel(byte[] image, String filename, String model, boolean sandbox) {
        this(image, filename, model);
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getMaskType() {
        return maskType;
    }

    public void setMaskType(String maskType) {
        this.maskType = maskType;
    }

    public CopyleaksObjectDetectionItem[] getObjectDetection() {
        return objectDetection;
    }

    public void setObjectDetection(CopyleaksObjectDetectionItem[] objectDetection) {
        this.objectDetection = objectDetection;
    }
}
