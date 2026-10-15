package models.response.AiImageDetection;

/** Image size in pixels. */
public class CopyleaksImageShapeModel {

    private int height;

    private int width;

    public CopyleaksImageShapeModel() {
    }

    public CopyleaksImageShapeModel(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
