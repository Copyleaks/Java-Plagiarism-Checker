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

public class CopyleaksStartRequest {

    private String[] trigger;
    private int errorHandling;

    /**
     * @param trigger       A list of scans that you submitted for a check-credits scan and that you would like to submit for a full scan.
     * @param errorHandling When set to ignore (ignore = 1) the trigger scans will start running even if some of them are in error mode, when set to cancel (cancel = 0) the request will be cancelled if any error was found.
     */
    public CopyleaksStartRequest(String[] trigger, int errorHandling) {
        this.trigger = trigger;
        this.errorHandling = errorHandling;
    }

    public String[] getTrigger() {
        return trigger;
    }

    public int getErrorHandling() {
        return errorHandling;
    }
}
