package models.response.writingassitant;

public class WritingAssistantResponse {
    private Score score;

    private Corrections corrections;

    private ScannedDocument scannedDocument;

    public Score getScore() {
        return score;
    }

    /** Spelling, grammar and sentence structure corrections. */
    public Corrections getCorrections() {
        return corrections;
    }

    public ScannedDocument getScannedDocument() {
        return scannedDocument;
    }
}
