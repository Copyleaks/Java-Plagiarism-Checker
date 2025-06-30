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
package models.submissions.Webhooks.HelperModels.ResultsModels;

import models.submissions.Webhooks.HelperModels.BaseModels.MetadataModel;

public class SharedResultsModel {

    /*Unique result ID to identify this result.*/
    private String id;
    
    /*Document title. Mostly extracted from the document content. */
    private String title;
    
    /*Document brief introduction. Mostly extracted from the document content. */
    private String introduction;
    
    /*Total matched words between this result and the scanned document. */
    private int matchedWords;
    
    /*In case a result was found in the Copyleaks internal database, and was submitted by you, this will show the scan id of the specific result. 
    Otherwise, this field will remain empty. */
    private String scanId;
    
     /*Metdata object */
    private MetadataModel metadata;

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

    public String getScanId() {
        return scanId;
    }

    public MetadataModel getMetadata() {
        return metadata;
    }

}
