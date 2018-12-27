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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InternetResult extends BasicResult {

	@SerializedName("url")
	@Expose
	private String url;

	private final static long serialVersionUID = 813046936617362195L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public InternetResult() {
	}

	/**
	 * A result from the Internet
	 * 
	 * @param id: Unique result id
	 * @param matchedWords: Number of matched words
	 * @param title: Result title
	 * @param comparison: downloadable comparison report
	 * @param introduction: The results introduction
	 * @param url: The result url
	 */
	public InternetResult(String url, String id, String title, String introduction, Integer matchedWords, String comparison) {
		super(id, title, introduction, matchedWords, comparison);
		this.url = url;

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
