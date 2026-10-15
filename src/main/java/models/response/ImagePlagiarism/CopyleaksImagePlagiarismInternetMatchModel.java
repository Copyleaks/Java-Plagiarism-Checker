package models.response.ImagePlagiarism;

/** A matching image found on the web. */
public class CopyleaksImagePlagiarismInternetMatchModel {

    /** An exact or near-exact copy. */
    public static final int MATCH_TYPE_FULL = 0;

    /** A cropped, resized, recolored or otherwise modified copy. */
    public static final int MATCH_TYPE_PARTIAL = 1;

    private String url;

    /** {@link #MATCH_TYPE_FULL} or {@link #MATCH_TYPE_PARTIAL}. */
    private int matchType;

    /** Pages where the image was found, or null. */
    private CopyleaksImagePlagiarismWebPageModel[] webPages;

    public String getUrl() {
        return url;
    }

    public int getMatchType() {
        return matchType;
    }

    public CopyleaksImagePlagiarismWebPageModel[] getWebPages() {
        return webPages;
    }
}
