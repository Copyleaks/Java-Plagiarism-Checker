package models.response.writingassitant;

public class CorrectionsDetails {
    private int grammarCorrectionsCount;
    private int grammarCorrectionsScore;
    private double grammarScoreWeight;
    private int mechanicsCorrectionsCount;
    private int mechanicsCorrectionsScore;
    private double mechanicsScoreWeight;
    private int sentenceStructureCorrectionsCount;
    private int sentenceStructureCorrectionsScore;
    private double sentenceStructureScoreWeight;
    private int wordChoiceCorrectionsCount;
    private int wordChoiceCorrectionsScore;
    private double wordChoiceScoreWeight;
    private int overallScore;

    public int getGrammarCorrectionsCount() {
        return grammarCorrectionsCount;
    }

    public int getGrammarCorrectionsScore() {
        return grammarCorrectionsScore;
    }

    public double getGrammarScoreWeight() {
        return grammarScoreWeight;
    }

    public int getMechanicsCorrectionsCount() {
        return mechanicsCorrectionsCount;
    }

    public int getMechanicsCorrectionsScore() {
        return mechanicsCorrectionsScore;
    }

    public double getMechanicsScoreWeight() {
        return mechanicsScoreWeight;
    }

    public int getSentenceStructureCorrectionsCount() {
        return sentenceStructureCorrectionsCount;
    }

    public int getSentenceStructureCorrectionsScore() {
        return sentenceStructureCorrectionsScore;
    }

    public double getSentenceStructureScoreWeight() {
        return sentenceStructureScoreWeight;
    }

    public int getWordChoiceCorrectionsCount() {
        return wordChoiceCorrectionsCount;
    }

    public int getWordChoiceCorrectionsScore() {
        return wordChoiceCorrectionsScore;
    }

    public double getWordChoiceScoreWeight() {
        return wordChoiceScoreWeight;
    }

    public int getOverallScore() {
        return overallScore;
    }
}
