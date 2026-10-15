package models.response.AiVideoDetection;

/** AI-detected segments of one track (audio or visual), in milliseconds. */
public class CopyleaksAiVideoDetectionTrackResultModel {

    private long[] starts;

    private long[] lengths;

    private CopyleaksAiVideoDetectionRangesModel exclude;

    /** Start of each AI-detected segment. */
    public long[] getStarts() {
        return starts;
    }

    /** Length of each AI-detected segment, aligned with {@code starts}. */
    public long[] getLengths() {
        return lengths;
    }

    /** Ranges that were not scored and are left out of the AI ratios. */
    public CopyleaksAiVideoDetectionRangesModel getExclude() {
        return exclude;
    }
}
