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

package models.request;

/**
 * Request body for deleting scans.
 */
public class CopyleaksDeleteRequest {

    /** The list of scans to delete. */
    private ScansRequests[] scans;

    /**
     * Delete all trace of the scan from Copyleaks server, including from internal database.
     * A purged process will not be available as a result for previous scans.
     */
    private boolean purge;

    /**
     * A webhook that is called with POST, without a body, once the removal has completed.
     */
    private String completionWebhook;

    /** Headers added to the completion webhook request, as {@code [["name", "value"], ...]}. */
    private String[][] headers;

    /**
     * @param scans             The list of scans to delete
     * @param purge             Delete all trace of the scan from Copyleaks server, including from internal database.
     *                          A purged process will not be available as a result for previous scans.
     * @param completionWebhook Allows you to register to a webhook that will be fired once the removal has been
     *                          completed. Make sure that your endpoint is listening to a POST method (no body
     *                          parameters were supplied). May be null.
     */
    public CopyleaksDeleteRequest(ScansRequests[] scans, boolean purge, String completionWebhook) {
        this.scans = scans;
        this.purge = purge;
        this.completionWebhook = completionWebhook;
    }

    /** @return the scans to delete. */
    public ScansRequests[] getScans() {
        return scans;
    }

    /** @return true when the scans are purged. */
    public boolean isPurge() {
        return purge;
    }

    /** @return the completion webhook URL, or null. */
    public String getCompletionWebhook() {
        return completionWebhook;
    }

    /** @param completionWebhook the completion webhook URL, or null. */
    public void setCompletionWebhook(String completionWebhook) {
        this.completionWebhook = completionWebhook;
    }

    /**
     * @return the completion webhook URL, or null.
     * @deprecated Use {@link #getCompletionWebhook()}.
     */
    @Deprecated
    public String getUri() {
        return completionWebhook;
    }

    /** @return the completion webhook headers, or null. */
    public String[][] getHeaders() {
        return headers;
    }

    /** @param headers headers added to the completion webhook request, as {@code [["name", "value"], ...]}. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
