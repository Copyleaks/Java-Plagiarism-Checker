package models.submissions.writingassistant;

/** Weight of each correction category in the writing score, 0.0 to 1.0. Each defaults to 1.0, as in the API. */
public class ScoreWeights {

    public static final double DEFAULT_WEIGHT = 1.0;

    private double grammarScoreWeight = DEFAULT_WEIGHT;

    private double mechanicsScoreWeight = DEFAULT_WEIGHT;

    private double sentenceStructureScoreWeight = DEFAULT_WEIGHT;

    private double wordChoiceScoreWeight = DEFAULT_WEIGHT;

    public ScoreWeights() {
    }

    public ScoreWeights(double grammarScoreWeight, double mechanicsScoreWeight,
            double sentenceStructureScoreWeight, double wordChoiceScoreWeight) {
        this.grammarScoreWeight = grammarScoreWeight;
        this.mechanicsScoreWeight = mechanicsScoreWeight;
        this.sentenceStructureScoreWeight = sentenceStructureScoreWeight;
        this.wordChoiceScoreWeight = wordChoiceScoreWeight;
    }

    public double getGrammarScoreWeight() {
        return grammarScoreWeight;
    }

    public void setGrammarScoreWeight(double grammarScoreWeight) {
        this.grammarScoreWeight = grammarScoreWeight;
    }

    public double getMechanicsScoreWeight() {
        return mechanicsScoreWeight;
    }

    public void setMechanicsScoreWeight(double mechanicsScoreWeight) {
        this.mechanicsScoreWeight = mechanicsScoreWeight;
    }

    public double getSentenceStructureScoreWeight() {
        return sentenceStructureScoreWeight;
    }

    public void setSentenceStructureScoreWeight(double sentenceStructureScoreWeight) {
        this.sentenceStructureScoreWeight = sentenceStructureScoreWeight;
    }

    public double getWordChoiceScoreWeight() {
        return wordChoiceScoreWeight;
    }

    public void setWordChoiceScoreWeight(double wordChoiceScoreWeight) {
        this.wordChoiceScoreWeight = wordChoiceScoreWeight;
    }
}
