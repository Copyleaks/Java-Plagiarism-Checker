package models.response.writingassitant;

public class Statistics {
    private int sentenceCount;
    private double averageSentenceLength;
    private double averageWordLength;
    private double readingTimeSeconds;
    private double speakingTimeSeconds;

    public int getSentenceCount() {
        return sentenceCount;
    }

    public double getAverageSentenceLength() {
        return averageSentenceLength;
    }

    public double getAverageWordLength() {
        return averageWordLength;
    }

    public double getReadingTimeSeconds() {
        return readingTimeSeconds;
    }

    public double getSpeakingTimeSeconds() {
        return speakingTimeSeconds;
    }
}
