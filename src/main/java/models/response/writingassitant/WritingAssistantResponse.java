package models.response.writingassitant;

public class WritingAssistantResponse {
    private Score score;

    /** Spelling, grammar and sentence structure corrections. */
    private Corrections corrections;

    private ScannedDocument scannedDocument;

    public Score getScore() {
        return score;
    }

    public Corrections getCorrections() {
        return corrections;
    }

    public ScannedDocument getScannedDocument() {
        return scannedDocument;
    }
}
