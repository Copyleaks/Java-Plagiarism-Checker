package models.submissions.Webhooks.HelperModels.ResultsModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class SharedResultsModel {

    private String id;
    
    /** Mostly extracted from the document content. */
    private String title;
    
    /** Brief introduction, mostly extracted from the document content. */
    private String introduction;
    
    /** Words matched between this result and the scanned document. */
    private int matchedWords;
    
    /** Your scan ID of the result, when it is one of your own submissions in the internal database; else empty. */
    private String scanId;
    
    /** Declared with the repository subtype so repository results keep {@code submittedBy}; null for others. */
    private RepositoryMetadataModel metadata;

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

    public String getScanId() {
        return scanId;
    }

    public MetadataModel getMetadata() {
        return metadata;
    }

}
