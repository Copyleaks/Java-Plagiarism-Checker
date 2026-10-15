package models.response.AiImageDetection;

/** Information about the analyzed image. */
public class CopyleaksAiImageDetectionImageInfoModel {

    private CopyleaksImageShapeModel shape;

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

    /** Metadata found in the image file, or null. */
    public CopyleaksImageMetadataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(CopyleaksImageMetadataModel metadata) {
        this.metadata = metadata;
    }
}
