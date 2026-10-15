package models.response.AiImageDetection;

import models.response.CopyleaksScanInfoModel;

/** Metadata about the AI image detection scan operation. */
public class CopyleaksAiImageDetectionScannedDocumentModel extends CopyleaksScanInfoModel {

    public CopyleaksAiImageDetectionScannedDocumentModel() {
    }

    public CopyleaksAiImageDetectionScannedDocumentModel(String scanId, int actualCredits, int expectedCredits, String creationTime) {
        super(scanId, actualCredits, expectedCredits, creationTime);
    }
}
