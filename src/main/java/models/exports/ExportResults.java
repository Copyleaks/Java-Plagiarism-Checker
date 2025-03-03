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

package models.exports;

public class ExportResults {
    /**
     * Result identification to be downloaded. You get these identifications from the completed webhook.
     * https://api.copyleaks.com/documentation/v3/webhooks/completed
     */
    private String id;

    /**
     * The HTTP url to upload the data.
     */
    private String endpoint;

    /**
     * The HTTP verb (also called "HTTP Methods") to upload the data to your specified endpoint.
     */
    private String verb;

    /**
     * List of headers to be submitted with the upload request. You may use this field to provide additional request headers, such as "Authorization" header.
     * <p>
     * Example: [["header-key1", "header-value1"], ["header-key2", "header-value2"]]
     */
    private String[][] headers = null;

    public ExportResults(String id, String endpoint, String verb, String[][] headers) {
        this.id = id;
        this.endpoint = endpoint;
        this.verb = verb;
        this.headers = headers;
    }

    public String getId() {
        return id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getVerb() {
        return verb;
    }

    public String[][] getHeaders() {
        return headers;
    }
}
