package models.request.AiImageDetection;

/**
 * One object to locate in an image, for example {@code new CopyleaksObjectDetectionItem("face")}.
 * Sent as {@code {"object": "face"}}.
 */
public class CopyleaksObjectDetectionItem {

    /** The object to find, 1 to 32 characters. */
    private String object;

    public CopyleaksObjectDetectionItem(String object) {
        this.object = object;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
