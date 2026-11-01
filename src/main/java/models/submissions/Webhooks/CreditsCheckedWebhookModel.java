package models.submissions.Webhooks;

import models.submissions.Webhooks.HelperModels.BaseModels.StatusWebhookModel;
import models.submissions.Webhooks.HelperModels.CompletedModels.ScannedDocumentModel;

/** Sent for a scan submitted with the {@code CheckCredits} action. */
public class CreditsCheckedWebhookModel extends StatusWebhookModel {
    private int credits;
    private ScannedDocumentModel scannedDocument;

    /** The price of the scan in credits. */
    public int getCredits() {
        return credits;
    }

    /** General information about the scanned document, or null. */
    public ScannedDocumentModel getScannedDocument() {
        return scannedDocument;
    }
}
