package models.response.ImagePlagiarism;

/** An empty {@code internet} array with zero scores means no copies were found. */
public class CopyleaksImagePlagiarismMatchesModel {

    /** Full matches first. */
    private CopyleaksImagePlagiarismInternetMatchModel[] internet;

    private CopyleaksImagePlagiarismScoreModel score;

    public CopyleaksImagePlagiarismInternetMatchModel[] getInternet() {
        return internet;
    }

    public CopyleaksImagePlagiarismScoreModel getScore() {
        return score;
    }
}
