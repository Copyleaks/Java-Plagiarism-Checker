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

package models.response.aidetection;

import java.util.List;

/**
 * Per-pattern statistics of an AI Logic explanation. All lists match the patterns by index.
 */
public class PatternStatistics {
    /**
     * How often each pattern appears in AI-generated text.
     */
    private List<Double> aiCount;

    /**
     * How often each pattern appears in human-written text.
     */
    private List<Double> humanCount;

    /**
     * Ratio between the AI and human frequencies of each pattern.
     */
    private List<Double> proportion;

    /**
     * Source of each pattern: 1 = AI, 2 = humanizer.
     */
    private List<Integer> source;

    public List<Double> getAiCount() {
        return aiCount;
    }

    public List<Double> getHumanCount() {
        return humanCount;
    }

    public List<Double> getProportion() {
        return proportion;
    }

    public List<Integer> getSource() {
        return source;
    }
}
