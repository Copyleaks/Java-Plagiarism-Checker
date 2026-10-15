package models.response.AiImageDetection;

public class CopyleaksAiImageDetectionImageInfoModel {

    private CopyleaksImageShapeModel shape;

    /** Metadata found in the image file, or null. */
    private CopyleaksImageMetadataModel metadata;

    public CopyleaksAiImageDetectionImageInfoModel() {
    }

    public CopyleaksAiImageDetectionImageInfoModel(CopyleaksImageShapeModel shape, CopyleaksImageMetadataModel metadata) {
        this.shape = shape;
        this.metadata = metadata;
    }

    public CopyleaksImageShapeModel getShape() {
        return shape;
    }

    public void setShape(CopyleaksImageShapeModel shape) {
        this.shape = shape;
    }

    public CopyleaksImageMetadataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(CopyleaksImageMetadataModel metadata) {
        this.metadata = metadata;
    }
}
