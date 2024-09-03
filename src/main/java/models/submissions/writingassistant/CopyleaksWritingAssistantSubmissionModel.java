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

import com.google.gson.annotations.SerializedName;

public class CopyleaksWritingAssistantSubmissionModel {
    /**
     * Text to produce Writing Assistant report for. 1 >= characters <= 25000
     */
    private String text;

    /**
     * Use sandbox mode to test your integration with the Copyleaks API without consuming any credits.
     */
    private boolean sandbox = false;

    /**
     * The language code of your content. The selected language should be on the Supported Languages list above.
     * If the 'language' field is not supplied , our system will automatically detect the language of the content.
     */
    private String language;

    private ScoreWeights score;

    public CopyleaksWritingAssistantSubmissionModel(String text) {
        this.text = text;
    }

    public CopyleaksWritingAssistantSubmissionModel(String text, boolean sandbox, String language, ScoreWeights scoreWeight) {
        this.text = text;
        this.sandbox = sandbox;
        this.language = language;
        this.score = scoreWeight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ScoreWeights getScore() {
        return score;
    }

    public void setScore(ScoreWeights scoreWeight) {
        this.score = scoreWeight;
    }
}
