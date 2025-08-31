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

package models.request.TextModeration;

public class CopyleaksTextModerationRequest {
    /**
     * Text to produce Text Moderation report for.
     */
    private final String text;

    /**
     * Use sandbox mode to test your integration with the Copyleaks API
     * without consuming any credits. Default value is false.
     */
    private final boolean sandbox;

    /**
     * The language code of your content. The selected language should be on the Supported Languages list.
     * If not provided, the system will automatically detect the language of the content.
     */
    private final String language;

    /**
     * A list of label configurations to be used for the moderation process.
     * The array must have at least 1 element and at most 32 elements.
     */
    private final Object[] labels;

    public CopyleaksTextModerationRequest(
            String text,
            Boolean sandbox,
            String language,
            CopyleaksTextModerationLabel[] labels) {

        if (text == null) {
            throw new IllegalArgumentException("Text field is required!");
        }
        this.text = text;
        this.sandbox = (sandbox != null) ? sandbox : false;
        this.language = language;

        // Validation for labels
        if (labels == null) {
            throw new IllegalArgumentException("Labels array must have at least 1 element");
        }
        
        if (labels.length > 32) {
        throw new IllegalArgumentException("Labels array must not contain more than 32 elements");
        }

        this.labels = labels;
    }

    public String getText() {
        return text;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public String getLanguage() {
        return language;
    }

    public Object[] getLabels() {
        return labels;
    }
}