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
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package models.exceptions;

/**
 * The server answered 503 Service Unavailable, usually because Copyleaks is under maintenance.
 */
public class UnderMaintenanceException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String responseBody;

    /**
     * Creates the exception without response details.
     */
    public UnderMaintenanceException() {
        this(503, null);
    }

    /**
     * @param statusCode   the HTTP status code of the response.
     * @param responseBody the response body text, or null.
     */
    public UnderMaintenanceException(int statusCode, String responseBody) {
        super("Copyleaks is Under Maintenance, please visit https://status.copyleaks.com");
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    /**
     * @return the HTTP status code of the response (503).
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return the response body the server sent, or null when it is not known.
     */
    public String getResponseBody() {
        return responseBody;
    }
}
