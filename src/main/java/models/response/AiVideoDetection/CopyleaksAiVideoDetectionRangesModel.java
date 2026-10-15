package models.response.AiVideoDetection;

/** Time ranges in milliseconds. */
public class CopyleaksAiVideoDetectionRangesModel {

    private long[] starts;

    /** Aligned with {@code starts}. */
    private long[] lengths;

    public long[] getStarts() {
        return starts;
    }

    public long[] getLengths() {
        return lengths;
    }
}
