package models.response.ImagePlagiarism;

/** Number of matches by type. */
public class CopyleaksImagePlagiarismScoreModel {

    private int totalMatches;

    private int fullMatches;

    private int partialMatches;

    /** Total number of matching images. */
    public int getTotalMatches() {
        return totalMatches;
    }

    /** Number of full matches (matchType 0). */
    public int getFullMatches() {
        return fullMatches;
    }

    /** Number of partial matches (matchType 1). */
    public int getPartialMatches() {
        return partialMatches;
    }
}
