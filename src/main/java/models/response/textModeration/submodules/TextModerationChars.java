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

package models.response.textModeration.submodules;

import java.util.Arrays;

public class TextModerationChars {
    /**
     * Predicted label index for the corresponding segment.
     * The index can be resolved to its ID using the supplied legend.
     */
    private final int[] labels;

    /**
     * Start character position of the labelled segment.
     */
    private final int[] starts;

    /**
     * Labelled segment character length.
     */
    private final int[] lengths;

    public TextModerationChars(int[] labels,int[] starts,int[] lengths) {
        
        this.labels = (labels != null) ? Arrays.copyOf(labels, labels.length) : null;
        this.starts = (starts != null) ? Arrays.copyOf(starts, starts.length) : null;
        this.lengths = (lengths != null) ? Arrays.copyOf(lengths, lengths.length) : null;
    }

    public int[] getLabels() {
        
        return (labels != null) ? Arrays.copyOf(labels, labels.length) : null;
    }

    public int[] getStarts() {
        
        return (starts != null) ? Arrays.copyOf(starts, starts.length) : null;
    }

    public int[] getLengths() {
        
        return (lengths != null) ? Arrays.copyOf(lengths, lengths.length) : null;
    }
}
