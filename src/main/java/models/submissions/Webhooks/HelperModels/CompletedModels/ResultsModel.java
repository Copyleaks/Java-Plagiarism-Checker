package models.submissions.Webhooks.HelperModels.CompletedModels;

import models.submissions.Webhooks.HelperModels.ResultsModels.BatchModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.DatabaseModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.InternalAIDataModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.InternetModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.RepositoriesModel;
import models.submissions.Webhooks.HelperModels.ResultsModels.ScoreModel;

public class ResultsModel {

    private DatabaseModel[] database;
    private BatchModel[] batch;
    private RepositoriesModel[] repositories;
    private ScoreModel score;
    private InternetModel[] internet;
    private InternalAIDataModel[] internalAIData;

    public DatabaseModel[] getDatabase() {
        return database;
    }

    public BatchModel[] getBatch() {
        return batch;
    }

    public RepositoriesModel[] getRepositories() {
        return repositories;
    }

    public ScoreModel getScore() {
        return score;
    }

    public InternetModel[] getInternet() {
        return internet;
    }

    /** Internal AI Source Match results; null unless the feature was enabled. */
    public InternalAIDataModel[] getInternalAIData() {
        return internalAIData;
    }

}