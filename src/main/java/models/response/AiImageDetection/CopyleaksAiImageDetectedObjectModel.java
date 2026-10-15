package models.response.AiImageDetection;

/** An object found by object detection. */
public class CopyleaksAiImageDetectedObjectModel {

    /** The object name from the request. */
    private String object;

    /** Run-Length Encoded mask of the object, in the coordinate space of {@code imageInfo.shape}. */
    private CopyleaksAiImageDetectionMaskModel mask;

    public String getObject() {
        return object;
    }

    public CopyleaksAiImageDetectionMaskModel getMask() {
        return mask;
    }
}
