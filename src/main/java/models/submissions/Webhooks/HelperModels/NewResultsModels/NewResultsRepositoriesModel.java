package models.submissions.Webhooks.HelperModels.NewResultsModels;

import models.submissions.Webhooks.HelperModels.ResultsModels.RepositoryMetadataModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.SharedResultsModel;

public class NewResultsRepositoriesModel extends SharedResultsModel {
    
    private String repositoryId;

    public String getRepositoryId() {
        return repositoryId;
    }

    @Override
    public RepositoryMetadataModel getMetadata() {

        return (RepositoryMetadataModel) super.getMetadata();
    }
}
