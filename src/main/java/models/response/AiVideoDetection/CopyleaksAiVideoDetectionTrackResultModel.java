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

package models.response.AiVideoDetection;

/**
 * AI-detected segments of one track (audio or visual). Positions and lengths are in milliseconds.
 */
public class CopyleaksAiVideoDetectionTrackResultModel {

    /** Start positions in milliseconds of the AI-detected segments. */
    private long[] starts;

    /** Durations in milliseconds of the AI-detected segments, aligned with starts. */
    private long[] lengths;

    /** Ranges that were not scored and are left out of the AI ratios. */
    private CopyleaksAiVideoDetectionRangesModel exclude;

    /** @return start positions in milliseconds of the AI-detected segments. */
    public long[] getStarts() {
        return starts;
    }

    /** @return durations in milliseconds of the AI-detected segments, aligned with starts. */
    public long[] getLengths() {
        return lengths;
    }

    /** @return ranges that were not scored and are left out of the AI ratios. */
    public CopyleaksAiVideoDetectionRangesModel getExclude() {
        return exclude;
    }
}
