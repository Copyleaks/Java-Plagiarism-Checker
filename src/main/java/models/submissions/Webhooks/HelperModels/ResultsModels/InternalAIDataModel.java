package models.submissions.Webhooks.HelperModels.ResultsModels;

/** A match found by Internal AI Source Match in the Copyleaks internal AI database. */
public class InternalAIDataModel {

    private String id;

    private String title;

    private String introduction;

    private int matchedWords;

    private int identicalWords;

    private int similarWords;

    private int paraphrasedWords;

    private int totalWords;

    private TagsModel[] tags;

    /** Short identifier of the match. */
    public String getId() {
        return id;
    }

    /** Title of the matched database entry. */
    public String getTitle() {
        return title;
    }

    /** A sample of the matched content. */
    public String getIntroduction() {
        return introduction;
    }

    /** Total number of matched words. */
    public int getMatchedWords() {
        return matchedWords;
    }

    /** Number of identical words. */
    public int getIdenticalWords() {
        return identicalWords;
    }

    /** Similar but not identical words. */
    public int getSimilarWords() {
        return similarWords;
    }

    /** Number of paraphrased words. */
    public int getParaphrasedWords() {
        return paraphrasedWords;
    }

    /** Words in the matched database entry. */
    public int getTotalWords() {
        return totalWords;
    }

    /** Result tags, or null. */
    public TagsModel[] getTags() {
        return tags;
    }
}
