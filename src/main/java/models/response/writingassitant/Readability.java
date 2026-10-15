package models.response.writingassitant;

public class Readability {
    private int score;
    private int readabilityLevel;
    private String readabilityLevelText;
    private String readabilityLevelDescription;

    public int getScore() {
        return score;
    }

    public int getReadabilityLevel() {
        return readabilityLevel;
    }

    public String getReadabilityLevelText() {
        return readabilityLevelText;
    }

    public String getReadabilityLevelDescription() {
        return readabilityLevelDescription;
    }
}
