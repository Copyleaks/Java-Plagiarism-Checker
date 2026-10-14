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

package models.submissions.properties;

import com.google.gson.annotations.JsonAdapter;

import helpers.CopyleaksIntegerEnum;
import helpers.CopyleaksIntegerEnumAdapterFactory;

/**
 * The PDF report version. Sent as an integer: 1, 2 or 3. Deprecated by the API in favor of {@code pdf.reportVersion}.
 */
@JsonAdapter(CopyleaksIntegerEnumAdapterFactory.class)
public enum SubmissionPdfVersion implements CopyleaksIntegerEnum {
    /** PDF report version 1. */
    V1(1),
    /** PDF report version 2. */
    V2(2),
    /** PDF report version 3. */
    V3(3);

    private final int value;

    SubmissionPdfVersion(int value) {
        this.value = value;
    }

    /**
     * @return the integer value sent to the API.
     */
    @Override
    public int getValue() {
        return value;
    }
}
