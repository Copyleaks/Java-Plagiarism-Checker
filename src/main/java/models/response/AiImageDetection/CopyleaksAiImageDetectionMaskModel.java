package models.response.AiImageDetection;

/** A Run-Length Encoded (RLE) mask over the flattened image array. */
public class CopyleaksAiImageDetectionMaskModel {

    /** Start of each segment in the flattened image array. */
    private int[] starts;

    /** Aligned with {@code starts}. */
    private int[] lengths;

    public int[] getStarts() {
        return starts;
    }

    public int[] getLengths() {
        return lengths;
    }
}
