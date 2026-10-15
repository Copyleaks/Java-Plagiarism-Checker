package models.response.ImagePlagiarism;

/** A matching image found on the web. */
public class CopyleaksImagePlagiarismInternetMatchModel {

    /** An exact or near-exact copy. */
    public static final int MATCH_TYPE_FULL = 0;

    /** A cropped, resized, recolored or otherwise modified copy. */
    public static final int MATCH_TYPE_PARTIAL = 1;

    private String url;

    private int matchType;

    private CopyleaksImagePlagiarismWebPageModel[] webPages;

    /** URL of the matching image. */
    public String getUrl() {
        return url;
    }

    /** {@link #MATCH_TYPE_FULL} (0) or {@link #MATCH_TYPE_PARTIAL} (1). */
    public int getMatchType() {
        return matchType;
    }

    /** Pages where the image was found, or null. */
    public CopyleaksImagePlagiarismWebPageModel[] getWebPages() {
        return webPages;
    }
}
