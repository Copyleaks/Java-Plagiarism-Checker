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
 * RLE-encoded mask data for AI-detected regions.
 */
public class CopyleaksAiImageDetectionResultModel {

    /**
     * Start positions of AI-detected segments in the flattened image array.
     */
    @JsonProperty("starts")
    private int[] starts;

    /**
     * Lengths of AI-detected segments corresponding to each start position.
     */
    @JsonProperty("lengths")
    private int[] lengths;

    public CopyleaksAiImageDetectionResultModel() {
    }

    public CopyleaksAiImageDetectionResultModel(int[] starts, int[] lengths) {
        this.starts = starts;
        this.lengths = lengths;
    }

    public int[] getStarts() {
        return starts;
    }

    public void setStarts(int[] starts) {
        this.starts = starts;
    }

    public int[] getLengths() {
        return lengths;
    }

    public void setLengths(int[] lengths) {
        this.lengths = lengths;
    }
}
