package models.submissions.Webhooks.HelperModels.CompletedModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class ScannedDocumentModel {

    private String scanId;

    private int totalWords;

    private int totalExcluded;

    private int credits;

    private String creationTime;

    private MetadataModel metadata;

    public String getScanId() {
        return scanId;
    }

    public int getTotalWords() {
        return totalWords;
    }

    /** Words excluded from the scan. */
    public int getTotalExcluded() {
        return totalExcluded;
    }

    /** Credits the scan used. */
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
