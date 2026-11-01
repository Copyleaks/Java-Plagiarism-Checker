package models.response.writingassitant;

public class Score {
    private CorrectionsDetails corrections;
    private Readability readability;
    private Statistics statistics;

    public CorrectionsDetails getCorrections() {
        return corrections;
    }

    public Readability getReadability() {
        return readability;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
