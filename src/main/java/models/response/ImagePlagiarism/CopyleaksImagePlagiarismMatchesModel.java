package models.response.ImagePlagiarism;

/** Matches found on the web. An empty {@code internet} array with zero scores means no copies were found. */
public class CopyleaksImagePlagiarismMatchesModel {

    private CopyleaksImagePlagiarismInternetMatchModel[] internet;

    private CopyleaksImagePlagiarismScoreModel score;

    /** Matching images found on the web, full matches first. */
    public CopyleaksImagePlagiarismInternetMatchModel[] getInternet() {
        return internet;
    }

    /** Number of matches by type. */
    public CopyleaksImagePlagiarismScoreModel getScore() {
        return score;
    }
}
