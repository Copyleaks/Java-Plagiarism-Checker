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
 * How documents indexed in a Private Cloud Hub repository are shown to other users. Sent as an integer: 0, 1 or 2.
 */
@JsonAdapter(CopyleaksIntegerEnumAdapterFactory.class)
public enum SubmissionMaskingPolicy implements CopyleaksIntegerEnum {
    /**
     * Users see the entire document.
     */
    NoMask(0),
    /**
     * Mask documents that other users added. Text that does not match identically is replaced with hashtags.
     */
    MaskOtherUsersFiles(1),
    /**
     * Mask all documents. Text that does not match identically is replaced with hashtags.
     */
    MaskAllFiles(2);

    private final int value;

    SubmissionMaskingPolicy(int value) {
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
