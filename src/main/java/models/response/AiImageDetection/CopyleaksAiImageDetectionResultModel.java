package models.response.AiImageDetection;

/** RLE mask of the AI-detected regions. */
public class CopyleaksAiImageDetectionResultModel {

    /** Start of each AI segment in the flattened image array. */
    private int[] starts;

    /** Aligned with {@code starts}. */
    private int[] lengths;

    /** Confidence band per segment, 0 (lowest) to 4. Only when {@code maskType} is "heatmap", otherwise null. */
    private int[] labels;

    public CopyleaksAiImageDetectionResultModel() {
    }

    public CopyleaksAiImageDetectionResultModel(int[] starts, int[] lengths) {
        this.starts = starts;
        this.lengths = lengths;
    }

    public int[] getStarts() {
        return starts;
    }

    public void setStarts(int[] starts) {
        this.starts = starts;
    }

    public int[] getLengths() {
        return lengths;
    }

    public void setLengths(int[] lengths) {
        this.lengths = lengths;
    }

    public int[] getLabels() {
        return labels;
    }

    public void setLabels(int[] labels) {
        this.labels = labels;
    }
}
