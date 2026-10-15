package models.submissions.Webhooks.HelperModels.CompletedModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class ScannedDocumentModel {

    private String scanId;

    private int totalWords;

    /** Words excluded from the scan. */
    private int totalExcluded;

    /** Credits the scan used. */
    private int credits;

    private String creationTime;

    private MetadataModel metadata;

    public String getScanId() {
        return scanId;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getTotalExcluded() {
        return totalExcluded;
    }

    public int getCredits() {
        return credits;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public MetadataModel getMetadata() {
        return metadata;
    }
}
