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
 * Where an asynchronous detection (AI Image async, AI Video) sends its result.
 */
public class CopyleaksAsyncWebhooksModel {

    /** The URL that receives the result with a POST request. */
    private String url;

    /** Optional headers added to the webhook request, as {@code [["name", "value"], ...]}. */
    private String[][] headers;

    /**
     * @param url the URL that receives the result.
     */
    public CopyleaksAsyncWebhooksModel(String url) {
        this.url = url;
    }

    /**
     * @param url     the URL that receives the result.
     * @param headers headers added to the webhook request, as {@code [["name", "value"], ...]}.
     */
    public CopyleaksAsyncWebhooksModel(String url, String[][] headers) {
        this.url = url;
        this.headers = headers;
    }

    /** @return the webhook URL. */
    public String getUrl() {
        return url;
    }

    /** @param url the webhook URL. */
    public void setUrl(String url) {
        this.url = url;
    }

    /** @return the webhook headers, or null. */
    public String[][] getHeaders() {
        return headers;
    }

    /** @param headers the webhook headers, as {@code [["name", "value"], ...]}. */
    public void setHeaders(String[][] headers) {
        this.headers = headers;
    }
}
