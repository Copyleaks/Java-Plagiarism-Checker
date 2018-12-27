/********************************************************************************
 The MIT License(MIT)
 
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
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
********************************************************************************/

package copyleaks.sdk.api.models.request;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exclude implements Serializable
{

    @SerializedName("references")
    @Expose
    private Boolean references;
    @SerializedName("quotes")
    @Expose
    private Boolean quotes;
    @SerializedName("titles")
    @Expose
    private Boolean titles;
    @SerializedName("htmlTemplate")
    @Expose
    private Boolean htmlTemplate;
    private final static long serialVersionUID = 2375465712578814001L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Exclude() {
    }

    /**
     * Exclude properties from scan 
     * @param references:Exclude references from the scan.
     * @param quotes: Exclude quotes from the scan.
     * @param titles: Exclude titles from the scan.
     * @param htmlTemplate: When scanning an HTML document, exclude the HTML tags and attribute metadata from the scan. 
     */
    public Exclude(Boolean references, Boolean quotes, Boolean titles, Boolean htmlTemplate) {
        super();
        this.references = references;
        this.quotes = quotes;
        this.titles = titles;
        this.htmlTemplate = htmlTemplate;
    }

    public Boolean getReferences() {
        return references;
    }

    public void setReferences(Boolean references) {
        this.references = references;
    }

    public Boolean getQuotes() {
        return quotes;
    }

    public void setQuotes(Boolean quotes) {
        this.quotes = quotes;
    }

    public Boolean getTitles() {
        return titles;
    }

    public void setTitles(Boolean titles) {
        this.titles = titles;
    }

    public Boolean getHtmlTemplate() {
        return htmlTemplate;
    }

    public void setHtmlTemplate(Boolean htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

}
