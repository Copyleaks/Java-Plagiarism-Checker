package models.response.textModeration.submodules;

import java.time.OffsetDateTime;

public class TextModerationScannedDocument {

    /**
     * The scan id given by the user.
     */
    private final String scanId;

    /**
     * Total number of words found in the scanned text.
     */
    private final int totalWords;

    /**
     * Total excluded words from the text.
     */
    private final int totalExcluded;

    /**
     * The cost of credits for this scan.
     */
    private final int actualCredits;

    /**
     * The amount of credits that was expected to be spent on the scan.
     */
    private final int expectedCredits;

    /**
     * Creation time of the scan.
     */
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
