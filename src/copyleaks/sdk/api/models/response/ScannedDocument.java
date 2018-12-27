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

package copyleaks.sdk.api.models.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScannedDocument implements Serializable
{
	/**
	 * Total number of words in the document
	 */
    @SerializedName("totalWords")
    @Expose
    private Integer totalWords;
    /**
     * Total number of excluded words
     */
    @SerializedName("totalExcluded")
    @Expose
    private Integer totalExcluded;
    /**
     * The amount of credits that will be consumed by the scan
     */
    @SerializedName("credits")
    @Expose
    private Integer credits;
    /**
     * The amount of credits that will be consumed by the scan
     */
    @SerializedName("creationTime")
    @Expose
    private String creationTime;
    /**
     * A cached version of the content, useful in case the content is changed after the scan has finished, i.e.: when scanning a URL 
     */
    @SerializedName("cachedVersion")
    @Expose
    private String cachedVersion;
    private final static long serialVersionUID = 7231046416537584735L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ScannedDocument() {
    }

	/**
	 * Details about the scanned document
	 * 
	 * @param totalExcluded: Total number of excluded words in the document
	 * @param totalWords: Total number of words in the document
	 * @param credits: The amount of credits that will be consumed by the scan
	 * @param creationTime: The amount of credits that will be consumed by the scan
	 * @param cachedVersion: A cached version of the content, useful in case the
	 *        content is changed after the scan has finished, i.e.: when scanning a
	 *        URL
	 */
    public ScannedDocument(Integer totalWords, Integer totalExcluded, Integer credits, String creationTime, String cachedVersion) {
        super();
        this.totalWords = totalWords;
        this.totalExcluded = totalExcluded;
        this.credits = credits;
        this.creationTime = creationTime;
        this.cachedVersion = cachedVersion;
    }

    public Integer getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(Integer totalWords) {
        this.totalWords = totalWords;
    }

    public Integer getTotalExcluded() {
        return totalExcluded;
    }

    public void setTotalExcluded(Integer totalExcluded) {
        this.totalExcluded = totalExcluded;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCachedVersion() {
        return cachedVersion;
    }

    public void setCachedVersion(String cachedVersion) {
        this.cachedVersion = cachedVersion;
    }

}
