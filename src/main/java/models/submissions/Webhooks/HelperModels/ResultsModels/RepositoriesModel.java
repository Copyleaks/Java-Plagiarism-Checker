package models.submissions.Webhooks.HelperModels.ResultsModels;

public class RepositoriesModel extends SharedResultsModel {

    private String repositoryId;

    private TagsModel[] tags;

    public String getRepositoryId() {
        return repositoryId;
    }

    public TagsModel[] getTags() {
        return tags;
    }

    @Override
    public RepositoryMetadataModel getMetadata() {

        return (RepositoryMetadataModel) super.getMetadata();
    }
}
