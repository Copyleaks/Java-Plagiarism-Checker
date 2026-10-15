package models.request.AiImageDetection;

/**
 * One object to locate in an image, for example {@code new CopyleaksObjectDetectionItem("face")}.
 * Sent as {@code {"object": "face"}}.
 */
public class CopyleaksObjectDetectionItem {

    private String object;

    /** @param object the object to find, for example "face"; 1 to 32 characters. */
    public CopyleaksObjectDetectionItem(String object) {
        this.object = object;
    }

    /** @return the object to find. */
    public String getObject() {
        return object;
    }

    /** Replaces the {@code object} constructor argument. */
    public void setObject(String object) {
        this.object = object;
    }
}
