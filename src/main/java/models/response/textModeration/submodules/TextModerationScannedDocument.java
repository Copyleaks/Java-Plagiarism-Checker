package models.response.textModeration.submodules;

public class TextModerationScannedDocument {

    private final String scanId;

    private final int totalWords;

    private final int totalExcluded;

    private final int actualCredits;

    private final int expectedCredits;

    private final String creationTime;

    public TextModerationScannedDocument(
            String scanId,
            int totalWords,
            int totalExcluded,
            int actualCredits,
            int expectedCredits,
            String creationTime) {

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

    /** Words excluded from the scan. */
    public int getTotalExcluded() {
        return totalExcluded;
    }

    /** Credits the scan used. */
    public int getActualCredits() {
        return actualCredits;
    }

    public int getExpectedCredits() {
        return expectedCredits;
    }

    public String getCreationTime() {
        return creationTime;
    }
}
