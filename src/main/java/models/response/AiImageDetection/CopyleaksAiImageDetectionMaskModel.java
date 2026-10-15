package models.response.AiImageDetection;

/** A Run-Length Encoded (RLE) mask over the flattened image array. */
public class CopyleaksAiImageDetectionMaskModel {

    private int[] starts;

    private int[] lengths;

    /** Start of each segment in the flattened image array. */
    public int[] getStarts() {
        return starts;
    }

    /** Length of each segment, aligned with {@code starts}. */
    public int[] getLengths() {
        return lengths;
    }
}
