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
package models.submissions.Webhooks.HelperModels.ErrorModels;

/**
 * The error of a scan that ended with an error.
 */
public class ErrorModel {

    /*The error type, for example "invalid_request_error" or "payment_error". */
    private String type;

    /*The machine-readable error identifier, for example "insufficient_credits". */
    private String id;

    /*Error code that represents the reason for failure. See below the full error table. */
    private int code;

    /*Error message that represents the reason for failure. */
    private String message;

    /*A URL to the documentation page of this error. */
    private String url;

    /*Details about the parameters that caused the error. */
    private ErrorDetailsModel[] details;

    /** @return the error type, for example "invalid_request_error", or null. */
    public String getType() {
        return type;
    }

    /** @return the machine-readable error identifier, for example "insufficient_credits", or null. */
    public String getId() {
        return id;
    }

    /** @return the error code. */
    public int getCode() {
        return code;
    }

    /** @return the human-readable error message. */
    public String getMessage() {
        return message;
    }

    /** @return a URL to the documentation page of this error, or null. */
    public String getUrl() {
        return url;
    }

    /** @return details about the parameters that caused the error, or null. */
    public ErrorDetailsModel[] getDetails() {
        return details;
    }
}
