package models.response.ImagePlagiarism;

/** Number of matches by type. */
public class CopyleaksImagePlagiarismScoreModel {

    private int totalMatches;

    private int fullMatches;

    private int partialMatches;

    public int getTotalMatches() {
        return totalMatches;
    }

    public int getFullMatches() {
        return fullMatches;
    }

    public int getPartialMatches() {
        return partialMatches;
    }
}
