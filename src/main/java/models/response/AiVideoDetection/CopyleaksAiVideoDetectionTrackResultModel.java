package models.response.AiVideoDetection;

/** AI-detected segments of one track (audio or visual), in milliseconds. */
public class CopyleaksAiVideoDetectionTrackResultModel {

    private long[] starts;

    /** Aligned with {@code starts}. */
    private long[] lengths;

    /** Ranges that were not scored and are left out of the AI ratios. */
    private CopyleaksAiVideoDetectionRangesModel exclude;

    public long[] getStarts() {
        return starts;
    }

    public long[] getLengths() {
        return lengths;
    }

    public CopyleaksAiVideoDetectionRangesModel getExclude() {
        return exclude;
    }
}
