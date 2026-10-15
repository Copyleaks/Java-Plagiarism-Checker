package models.submissions.Webhooks.HelperModels.NewResultsModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class NewResultsInternetModel {

    private String id;

    /** Mostly extracted from the document content. */
    private String title;

    /** Brief introduction, mostly extracted from the document content. */
    private String introduction;

    /** Words matched between this result and the scanned document. */
    private int matchedWords;

    private MetadataModel metadata;

    private String url;

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

    public MetadataModel getMetadata() {
        return metadata;
    }

    public String getUrl() {
        return url;
    }

}
