/*
 The MIT License(MIT)
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package models.submissions.writingassistant;

/**
 * The weight of each correction category in the overall writing score.
 * Every weight is between 0.0 and 1.0 and defaults to 1.0, the API default,
 * so setting one weight leaves the others at 1.0.
 */
public class ScoreWeights {

    /** The default weight of each category. */
    public static final double DEFAULT_WEIGHT = 1.0;

    /**
     * Grammar correction category weight in the overall score. 0.0 &lt;= weight &lt;= 1.0
     */
    private double grammarScoreWeight = DEFAULT_WEIGHT;

    /**
     * Mechanics correction category weight in the overall score. 0.0 &lt;= weight &lt;= 1.0
     */
    private double mechanicsScoreWeight = DEFAULT_WEIGHT;

    /**
     * Sentence structure correction category weight in the overall score. 0.0 &lt;= weight &lt;= 1.0
     */
    private double sentenceStructureScoreWeight = DEFAULT_WEIGHT;

    /**
     * Word choice correction category weight in the overall score. 0.0 &lt;= weight &lt;= 1.0
     */
    private double wordChoiceScoreWeight = DEFAULT_WEIGHT;

    /**
     * Creates weights that are all 1.0.
     */
    public ScoreWeights() {
    }

    /**
     * @param grammarScoreWeight           grammar weight, 0.0 to 1.0.
     * @param mechanicsScoreWeight         mechanics weight, 0.0 to 1.0.
     * @param sentenceStructureScoreWeight sentence structure weight, 0.0 to 1.0.
     * @param wordChoiceScoreWeight        word choice weight, 0.0 to 1.0.
     */
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

    /** @param grammarScoreWeight the grammar weight, 0.0 to 1.0. */
    public void setGrammarScoreWeight(double grammarScoreWeight) {
        this.grammarScoreWeight = grammarScoreWeight;
    }

    /** @return the mechanics weight. */
    public double getMechanicsScoreWeight() {
        return mechanicsScoreWeight;
    }

    /** @param mechanicsScoreWeight the mechanics weight, 0.0 to 1.0. */
    public void setMechanicsScoreWeight(double mechanicsScoreWeight) {
        this.mechanicsScoreWeight = mechanicsScoreWeight;
    }

    /** @return the sentence structure weight. */
    public double getSentenceStructureScoreWeight() {
        return sentenceStructureScoreWeight;
    }

    /** @param sentenceStructureScoreWeight the sentence structure weight, 0.0 to 1.0. */
    public void setSentenceStructureScoreWeight(double sentenceStructureScoreWeight) {
        this.sentenceStructureScoreWeight = sentenceStructureScoreWeight;
    }

    /** @return the word choice weight. */
    public double getWordChoiceScoreWeight() {
        return wordChoiceScoreWeight;
    }

    /** @param wordChoiceScoreWeight the word choice weight, 0.0 to 1.0. */
    public void setWordChoiceScoreWeight(double wordChoiceScoreWeight) {
        this.wordChoiceScoreWeight = wordChoiceScoreWeight;
    }
}
