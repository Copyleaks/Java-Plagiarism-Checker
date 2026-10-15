package models.submissions.writingassistant;

/** Weight of each correction category in the writing score, 0.0 to 1.0. Each defaults to 1.0, as in the API. */
public class ScoreWeights {

    /** Default weight of each category. */
    public static final double DEFAULT_WEIGHT = 1.0;

    private double grammarScoreWeight = DEFAULT_WEIGHT;

    private double mechanicsScoreWeight = DEFAULT_WEIGHT;

    private double sentenceStructureScoreWeight = DEFAULT_WEIGHT;

    private double wordChoiceScoreWeight = DEFAULT_WEIGHT;

    /** Creates weights that are all {@value #DEFAULT_WEIGHT}. */
    public ScoreWeights() {
    }

    /** Creates weights for grammar, mechanics, sentence structure and word choice. */
    public ScoreWeights(double grammarScoreWeight, double mechanicsScoreWeight,
            double sentenceStructureScoreWeight, double wordChoiceScoreWeight) {
        this.grammarScoreWeight = grammarScoreWeight;
        this.mechanicsScoreWeight = mechanicsScoreWeight;
        this.sentenceStructureScoreWeight = sentenceStructureScoreWeight;
        this.wordChoiceScoreWeight = wordChoiceScoreWeight;
    }

    /** @return the grammar weight. */
    public double getGrammarScoreWeight() {
        return grammarScoreWeight;
    }

    /** Weight of the grammar category. */
    public void setGrammarScoreWeight(double grammarScoreWeight) {
        this.grammarScoreWeight = grammarScoreWeight;
    }

    /** @return the mechanics weight. */
    public double getMechanicsScoreWeight() {
        return mechanicsScoreWeight;
    }

    /** Weight of the mechanics category. */
    public void setMechanicsScoreWeight(double mechanicsScoreWeight) {
        this.mechanicsScoreWeight = mechanicsScoreWeight;
    }

    /** @return the sentence structure weight. */
    public double getSentenceStructureScoreWeight() {
        return sentenceStructureScoreWeight;
    }

    /** Weight of the sentence structure category. */
    public void setSentenceStructureScoreWeight(double sentenceStructureScoreWeight) {
        this.sentenceStructureScoreWeight = sentenceStructureScoreWeight;
    }

    /** @return the word choice weight. */
    public double getWordChoiceScoreWeight() {
        return wordChoiceScoreWeight;
    }

    /** Weight of the word choice category. */
    public void setWordChoiceScoreWeight(double wordChoiceScoreWeight) {
        this.wordChoiceScoreWeight = wordChoiceScoreWeight;
    }
}
