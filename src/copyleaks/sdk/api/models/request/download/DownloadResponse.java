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

package copyleaks.sdk.api.models.request.download;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadResponse implements Serializable {

	private static final long serialVersionUID = 8287787528047896690L;

	@SerializedName("statistics")
	@Expose
	private Statistics statistics;
	@SerializedName("text")
	@Expose
	private SuspectedVersion text;
	@SerializedName("html")
	@Expose
	private SuspectedVersion html;

	public DownloadResponse() {
	}

	public DownloadResponse(Statistics statistics, SuspectedVersion text, SuspectedVersion html) {
		super();
		this.statistics = statistics;
		this.text = text;
		this.html = html;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public SuspectedVersion getText() {
		return text;
	}

	public void setText(SuspectedVersion text) {
		this.text = text;
	}

	public SuspectedVersion getHtml() {
		return html;
	}

	public void setHtml(SuspectedVersion html) {
		this.html = html;
	}

	@Override
	public String toString() {
		return "DownloadResponse [statistics=" + statistics + ", text=" + text + ", html=" + html + "]";
	}
	
	

}
