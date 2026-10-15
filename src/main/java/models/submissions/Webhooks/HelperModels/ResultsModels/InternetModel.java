package models.submissions.Webhooks.HelperModels.ResultsModels;

import models.submissions.Webhooks.HelperModels.NewResultsModels.NewResultsInternetModel;

public class InternetModel extends NewResultsInternetModel {

    private TagsModel[] tags;

    public TagsModel[] getTags() {
        return tags;
    }
}
