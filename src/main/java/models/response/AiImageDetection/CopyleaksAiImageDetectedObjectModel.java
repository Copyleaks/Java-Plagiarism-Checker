package models.response.AiImageDetection;

/** An object found by object detection. */
public class CopyleaksAiImageDetectedObjectModel {

    private String object;

    private CopyleaksAiImageDetectionMaskModel mask;

    /** The object name from the request. */
    public String getObject() {
        return object;
    }

    /** Run-Length Encoded mask of the object, in the coordinate space of {@code imageInfo.shape}. */
    public CopyleaksAiImageDetectionMaskModel getMask() {
        return mask;
    }
}
