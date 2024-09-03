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

public class ScoreWeights {
    /**
     * Grammar correction category weight in the overall score. 0.0 >= weight <=1.0
     */
    private double grammarScoreWeight;

    /**
     * Mechanics correction category weight in the overall score. 0.0 >= weight <=1.0
     */
    private double mechanicsScoreWeight;

    /**
     * Sentence structure correction category weight in the overall score. 0.0 >= weight <=1.0
     */
    private double sentenceStructureScoreWeight;

    /**
     * Word choice correction category weight in the overall score. 0.0 >= weight <=1.0
     */
    private double wordChoiceScoreWeight;

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
