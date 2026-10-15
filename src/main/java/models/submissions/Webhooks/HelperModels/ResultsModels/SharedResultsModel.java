package models.submissions.Webhooks.HelperModels.ResultsModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class SharedResultsModel {

    private String id;
    
    private String title;
    
    private String introduction;
    
    private int matchedWords;
    
    private String scanId;
    
    private RepositoryMetadataModel metadata;

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

    /** Your scan ID of the result, when it is one of your own submissions in the internal database; else empty. */
    public String getScanId() {
        return scanId;
    }

    /**
     * Result metadata. Declared with the repository subtype so repository results keep {@code submittedBy};
     * null for others.
     */
    public MetadataModel getMetadata() {
        return metadata;
    }

}
