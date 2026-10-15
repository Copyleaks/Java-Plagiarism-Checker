package models.submissions.Webhooks;

import models.submissions.Webhooks.HelperModels.BaseModels.WebhookModel;
import models.submissions.Webhooks.HelperModels.NewResultsModels.NewResultScoreModel;
import models.submissions.Webhooks.HelperModels.NewResultsModels.NewResultsInternetModel;
import models.submissions.Webhooks.HelperModels.NewResultsModels.NewResultsRepositoriesModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.SharedResultsModel;

public class NewResultWebhookModel extends WebhookModel {

    private NewResultScoreModel score;
    private NewResultsInternetModel[] internet;
    private SharedResultsModel[] database;
    private SharedResultsModel[] batch;
    private NewResultsRepositoriesModel[] repositories;

    public NewResultsInternetModel[] getInternet() {
        return internet;
    }

    public SharedResultsModel[] getDatabase() {
        return database;
    }

    public SharedResultsModel[] getBatch() {
        return batch;
    }

    public NewResultsRepositoriesModel[] getRepositories() {
        return repositories;
    }

    public NewResultScoreModel getScore() {
        return score;
    }

}
