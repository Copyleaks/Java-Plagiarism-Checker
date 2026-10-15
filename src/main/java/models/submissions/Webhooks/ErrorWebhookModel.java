package models.submissions.Webhooks;

import models.submissions.Webhooks.HelperModels.BaseModels.StatusWebhookModel;
import models.submissions.Webhooks.HelperModels.ErrorModels.ErrorModel;

public class ErrorWebhookModel extends StatusWebhookModel {
    private ErrorModel error;

    public ErrorModel getError() {
        return error;
    }
}
