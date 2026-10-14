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

/**
 * Citation Checker settings: check the references and citations in the submitted text.
 * The summary arrives in the completed webhook as {@code referencesValidation}.
 */
public class SubmissionReferences {

    /** Detect, parse and validate every reference in the submitted text. Default: false. */
    private Boolean validate;

    /**
     * Creates the settings with the server default.
     */
    public SubmissionReferences() {
    }

    /**
     * @param validate true to detect, parse and validate every reference.
     */
    public SubmissionReferences(Boolean validate) {
        this.validate = validate;
    }

    /** @return true when references are validated, or null for the server default. */
    public Boolean getValidate() {
        return validate;
    }

    /** @param validate true to detect, parse and validate every reference. */
    public void setValidate(Boolean validate) {
        this.validate = validate;
    }
}
