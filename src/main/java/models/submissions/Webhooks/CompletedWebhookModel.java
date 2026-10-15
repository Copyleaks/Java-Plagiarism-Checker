package models.submissions.Webhooks;

import models.constants.CopyleaksAlertCodes;
import models.response.aidetection.AIDetectionResponse;
import models.submissions.Webhooks.HelperModels.BaseModels.StatusWebhookModel;
import models.submissions.Webhooks.HelperModels.CompletedModels.NotificationsModel;
import models.submissions.Webhooks.HelperModels.CompletedModels.ReferencesValidationModel;
import models.submissions.Webhooks.HelperModels.CompletedModels.ResultsModel;
import models.submissions.Webhooks.HelperModels.CompletedModels.ScannedDocumentModel;
import models.submissions.Webhooks.HelperModels.NotificationsModels.AlertsModel;

public class CompletedWebhookModel extends StatusWebhookModel {

    private ResultsModel results;
    private NotificationsModel notifications;
    private ScannedDocumentModel scannedDocument;
    private ReferencesValidationModel referencesValidation;

    public ResultsModel getResults() {
        return results;
    }

    public NotificationsModel getNotifications() {
        return notifications;
    }

    public ScannedDocumentModel getScannedDocument() {
        return scannedDocument;
    }

    /** Citation Checker summary; null unless {@code references.validate} was enabled. */
    public ReferencesValidationModel getReferencesValidation() {
        return referencesValidation;
    }

    /**
     * The first {@link CopyleaksAlertCodes#SUSPECTED_AI_TEXT} alert, or null. A computed helper, not a wire field:
     * exclude it when re-serializing with a bean-based serializer such as Jackson (Gson is unaffected).
     */
    public AlertsModel getAIDetectionAlert() {
        if (notifications == null || notifications.getAlerts() == null) {
            return null;
        }
        for (AlertsModel alert : notifications.getAlerts()) {
            if (alert != null && CopyleaksAlertCodes.SUSPECTED_AI_TEXT.equals(alert.getCode())) {
                return alert;
            }
        }
        return null;
    }

    /**
     * The AI Text Detection result decoded from {@link #getAIDetectionAlert()}, or null. A computed helper, like
     * {@link #getAIDetectionAlert()}.
     * @throws com.google.gson.JsonSyntaxException if the alert's {@code additionalData} is not valid JSON.
     */
    public AIDetectionResponse getAIDetectionResult() {
        AlertsModel alert = getAIDetectionAlert();
        return alert == null ? null : alert.getAIDetectionResult();
    }

}
