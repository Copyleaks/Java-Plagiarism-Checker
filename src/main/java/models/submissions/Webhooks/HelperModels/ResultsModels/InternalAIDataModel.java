package models.submissions.Webhooks.HelperModels.ResultsModels;

/** A match found by Internal AI Source Match in the Copyleaks internal AI database. */
public class InternalAIDataModel {

    private String id;

    /** Title of the matched database entry. */
    private String title;

    /** A sample of the matched content. */
    private String introduction;

    private int matchedWords;

    private int identicalWords;

    /** Similar but not identical words. */
    private int similarWords;

    private int paraphrasedWords;

    /** Words in the matched database entry. */
    private int totalWords;

    private TagsModel[] tags;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getMatchedWords() {
        return matchedWords;
    }

    public int getIdenticalWords() {
        return identicalWords;
    }

    public int getSimilarWords() {
        return similarWords;
    }

    public int getParaphrasedWords() {
        return paraphrasedWords;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public TagsModel[] getTags() {
        return tags;
    }
}
