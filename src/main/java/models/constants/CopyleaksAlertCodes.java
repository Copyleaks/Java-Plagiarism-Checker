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

package models.constants;

/**
 * Scan alert codes reported in {@code notifications.alerts[].code} of the completed webhook.
 * <p>
 * All codes listed here belong to alert category 2 (AI content detection).
 * Alert severity ranges from 0 (lowest) to 4 (highest); the {@link #SUSPECTED_AI_TEXT} alert has severity 4.
 */
public final class CopyleaksAlertCodes {

    /**
     * AI-generated text was detected in the scanned document.
     * The alert's {@code additionalData} holds the AI text detection result as a JSON string.
     */
    public static final String SUSPECTED_AI_TEXT = "suspected-ai-text";

    /**
     * AI text detection failed for the scanned document.
     */
    public static final String AI_DETECTION_FAILED = "ai-detection-failed";

    /**
     * AI text detection was not run because the document language is not supported.
     */
    public static final String AI_DETECTION_LANG_NOT_SUPPORTED = "ai-detection-lang-not-supported";

    /**
     * AI text detection was not run because the text is too short.
     */
    public static final String AI_DETECTION_TEXT_TOO_SHORT = "ai-detection-text-too-short";

    /**
     * AI text detection was not run because the file type is not supported.
     */
    public static final String FILE_TYPE_NOT_SUPPORTED = "file-type-not-supported";
}
