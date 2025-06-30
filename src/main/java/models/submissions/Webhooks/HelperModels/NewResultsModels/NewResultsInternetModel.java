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
package models.submissions.Webhooks.HelperModels.NewResultsModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class NewResultsInternetModel {

    /*Unique result ID to identify the result. */
    private String id;

    /*Document title. Mostly extracted from the document content. */
    private String title;

    /*Document brief introduction. Mostly extracted from the document content. */
    private String introduction;

    /*Total matched words between this result and the scanned document. */
    private int matchedWords;

    /*Metadata object */
    private MetadataModel metadata;

    /*Public URL of the resource. */
    private String url;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getMatchedWords() {
        return matchedWords;
    }

    public MetadataModel getMetadata() {
        return metadata;
    }

    public String getUrl() {
        return url;
    }

}
