package models.request.AiImageDetection;

/** Options shared by the AI Image Detection requests. */
public abstract class CopyleaksAiImageDetectionBaseRequestModel {

    private String maskType;

    private CopyleaksObjectDetectionItem[] objectDetection;

    /** @return the mask type, or null for the server default. */
    public String getMaskType() {
        return maskType;
    }

    /**
     * "binary" (default), "heatmap" or "none", see {@link models.constants.CopyleaksAiImageMaskTypes}.
     * The async submit accepts only "binary" and "heatmap".
     */
    public void setMaskType(String maskType) {
        this.maskType = maskType;
    }

    /** @return the objects to detect, or null. */
    public CopyleaksObjectDetectionItem[] getObjectDetection() {
        return objectDetection;
    }

    /** Objects to locate and segment, at most 3. Null turns the feature off. */
    public void setObjectDetection(CopyleaksObjectDetectionItem[] objectDetection) {
        this.objectDetection = objectDetection;
    }
}
