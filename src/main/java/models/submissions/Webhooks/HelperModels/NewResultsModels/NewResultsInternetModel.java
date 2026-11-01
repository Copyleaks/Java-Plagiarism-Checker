package models.submissions.Webhooks.HelperModels.NewResultsModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class NewResultsInternetModel {

    private String id;

    private String title;

    private String introduction;

    private int matchedWords;

    private MetadataModel metadata;

    private String url;

    public String getId() {
        return id;
    }

    /** Result title, mostly extracted from the document content. */
    public String getTitle() {
        return title;
    }

    /** Brief introduction, mostly extracted from the document content. */
    public String getIntroduction() {
        return introduction;
    }

    /** Words matched between this result and the scanned document. */
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
