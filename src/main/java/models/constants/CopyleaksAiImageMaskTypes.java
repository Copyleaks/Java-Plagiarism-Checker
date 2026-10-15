package models.constants;

/** Values for the AI Image Detection {@code maskType} option. */
public final class CopyleaksAiImageMaskTypes {
    /** Default. Run-Length Encoded mask of the AI regions, in {@code result.starts} and {@code result.lengths}. */
    public static final String BINARY = "binary";
    /** Like binary, plus {@code result.labels}: one confidence band from 0 to 4 per run. */
    public static final String HEATMAP = "heatmap";
    /** No mask. The synchronous response has no {@code result} object. */
    public static final String NONE = "none";

    private CopyleaksAiImageMaskTypes() {
    }
}
