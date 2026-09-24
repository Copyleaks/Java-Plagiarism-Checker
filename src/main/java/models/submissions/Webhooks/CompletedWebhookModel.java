/*
 The MIT License(MIT)
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/
package models.submissions.Webhooks;

import models.constants.CopyleaksAlertCodes;
import models.response.aidetection.AIDetectionResponse;
import models.submissions.Webhooks.HelperModels.BaseModels.StatusWebhookModel;
import models.submissions.Webhooks.HelperModels.CompletedModels.NotificationsModel;
import models.submissions.Webhooks.HelperModels.CompletedModels.ResultsModel;
import models.submissions.Webhooks.HelperModels.CompletedModels.ScannedDocumentModel;
import models.submissions.Webhooks.HelperModels.NotificationsModels.AlertsModel;

public class CompletedWebhookModel extends StatusWebhookModel {

    private ResultsModel results;
    private NotificationsModel notifications;
    private ScannedDocumentModel scannedDocument;

    public ResultsModel getResults() {
        return results;
    }

    public NotificationsModel getNotifications() {
        return notifications;
    }

    public ScannedDocumentModel getScannedDocument() {
        return scannedDocument;
    }

    /**
     * Finds the AI text detection alert of this scan.
     * <p>
     * A null result means the scan produced no AI alert. It does not by itself prove
     * that AI detection ran: check that the submission set {@code aiGeneratedText.detect},
     * and look for the category 2 failure codes in {@link CopyleaksAlertCodes}
     * (for example {@link CopyleaksAlertCodes#AI_DETECTION_FAILED}).
     *
     * @return the first alert whose code is {@link CopyleaksAlertCodes#SUSPECTED_AI_TEXT},
     *         or null when there is none or the webhook has no notifications or alerts.
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
     * Decodes the AI text detection result from the AI alert of this scan.
     * <p>
     * A null result means the scan produced no AI alert, or the alert carried no data.
     * It does not by itself prove that AI detection ran: check that the submission set
     * {@code aiGeneratedText.detect}, and look for the category 2 failure codes in
     * {@link CopyleaksAlertCodes}.
     *
     * @return the result decoded by {@link AlertsModel#getAIDetectionResult()} for the alert
     *         returned by {@link #getAIDetectionAlert()}, or null.
     * @throws com.google.gson.JsonSyntaxException if the alert's {@code additionalData} is not valid JSON.
     */
    public AIDetectionResponse getAIDetectionResult() {
        AlertsModel alert = getAIDetectionAlert();
        return alert == null ? null : alert.getAIDetectionResult();
    }

}
