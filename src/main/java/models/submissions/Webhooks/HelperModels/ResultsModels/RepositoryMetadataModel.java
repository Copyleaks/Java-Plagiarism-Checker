package models.submissions.Webhooks.HelperModels.ResultsModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class RepositoryMetadataModel extends MetadataModel {
    
    private String submittedBy;

    /** Email of the user who added the document to the repository. */
    public String getSubmittedBy() {
        return submittedBy;
    }
}
