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
package models.response.AiImageDetection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Summary statistics of the AI detection analysis.
 */
public class CopyleaksAiImageDetectionSummaryModel {

    /**
     * Percentage of pixels suspected to be human-created.
     * Range: 0.0-1.0
     */
    @JsonProperty("human")
    private float human;

    /**
     * Percentage of pixels suspected to be AI-generated.
     * Range: 0.0-1.0
     */
    @JsonProperty("ai")
    private float ai;

    public CopyleaksAiImageDetectionSummaryModel() {
    }

    public CopyleaksAiImageDetectionSummaryModel(float human, float ai) {
        this.human = human;
        this.ai = ai;
    }

    public float getHuman() {
        return human;
    }

    public void setHuman(float human) {
        this.human = human;
    }

    public float getAi() {
        return ai;
    }

    public void setAi(float ai) {
        this.ai = ai;
    }
}
