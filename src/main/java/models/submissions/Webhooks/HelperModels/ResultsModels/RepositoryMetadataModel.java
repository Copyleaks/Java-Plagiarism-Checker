package models.submissions.Webhooks.HelperModels.ResultsModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class RepositoryMetadataModel extends MetadataModel {
    
    /** Email of the user who added the document to the repository. */
    private String submittedBy;

    public String getSubmittedBy() {
        return submittedBy;
    }
}
