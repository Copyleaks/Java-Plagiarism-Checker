package models.response.AiImageDetection;

/** RLE mask of the AI-detected regions. */
public class CopyleaksAiImageDetectionResultModel {

    private int[] starts;

    private int[] lengths;

    private int[] labels;

    public CopyleaksAiImageDetectionResultModel() {
    }

    public CopyleaksAiImageDetectionResultModel(int[] starts, int[] lengths) {
        this.starts = starts;
        this.lengths = lengths;
    }

    /** Start of each AI segment in the flattened image array. */
    public int[] getStarts() {
        return starts;
    }

    public void setStarts(int[] starts) {
        this.starts = starts;
    }

    /** Length of each AI segment, aligned with {@code starts}. */
    public int[] getLengths() {
        return lengths;
    }

    public void setLengths(int[] lengths) {
        this.lengths = lengths;
    }

    /** Confidence band per segment, 0 (lowest) to 4. Only when {@code maskType} is "heatmap", otherwise null. */
    public int[] getLabels() {
        return labels;
    }

    /** @param labels the confidence band of each segment. */
    public void setLabels(int[] labels) {
        this.labels = labels;
    }
}
