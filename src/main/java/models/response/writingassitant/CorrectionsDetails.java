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
