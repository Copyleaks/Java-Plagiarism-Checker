package models.response.AiVideoDetection;

/** Time ranges in milliseconds. */
public class CopyleaksAiVideoDetectionRangesModel {

    private long[] starts;

    private long[] lengths;

    /** Start of each range. */
    public long[] getStarts() {
        return starts;
    }

    /** Length of each range, aligned with {@code starts}. */
    public long[] getLengths() {
        return lengths;
    }
}
