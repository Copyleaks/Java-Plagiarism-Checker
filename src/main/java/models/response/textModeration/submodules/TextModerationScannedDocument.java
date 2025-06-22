package models.response.textModeration.submodules;

import java.time.OffsetDateTime;

public class TextModerationScannedDocument {

    private final String scanId;
    private final int totalWords;
    private final int totalExcluded;
    private final int actualCredits;
    private final int expectedCredits;
    private final OffsetDateTime creationTime;

    public TextModerationScannedDocument(
            String scanId,
            int totalWords,
            int totalExcluded,
            int actualCredits,
            int expectedCredits,
            OffsetDateTime creationTime) {

        this.scanId = scanId;
        this.totalWords = totalWords;
        this.totalExcluded = totalExcluded;
        this.actualCredits = actualCredits;
        this.expectedCredits = expectedCredits;
        this.creationTime = creationTime;
    }

    public String getScanId() {
        return scanId;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getTotalExcluded() {
        return totalExcluded;
    }

    public int getActualCredits() {
        return actualCredits;
    }

    public int getExpectedCredits() {
        return expectedCredits;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }
}
