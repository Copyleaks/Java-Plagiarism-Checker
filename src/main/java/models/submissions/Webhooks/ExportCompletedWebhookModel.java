package models.submissions.Webhooks;

import models.submissions.Webhooks.HelperModels.BaseModels.WebhookModel;
import models.submissions.Webhooks.HelperModels.ExportModels.TaskModel;

public class ExportCompletedWebhookModel extends WebhookModel {

    private boolean completed;
    private TaskModel[] tasks;

    public TaskModel[] getTasks() {
        return tasks;
    }

    public boolean isCompleted() {
        return completed;
    }
}
