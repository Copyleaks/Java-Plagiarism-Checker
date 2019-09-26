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

public class SuspectedVersion implements Serializable {

	private static final long serialVersionUID = 1416307247887098871L;

	@SerializedName("value")
	@Expose
	private String value;
	@SerializedName("pages")
	@Expose
	private Pages pages;
	@SerializedName("comparison")
	@Expose
	private Comparison comparison;

	public SuspectedVersion() {
	}

	public SuspectedVersion(String value, Pages pages, Comparison comparison) {
		super();
		this.value = value;
		this.pages = pages;
		this.comparison = comparison;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Pages getPages() {
		return pages;
	}

	public void setPages(Pages pages) {
		this.pages = pages;
	}

	public Comparison getComparison() {
		return comparison;
	}

	public void setComparison(Comparison comparison) {
		this.comparison = comparison;
	}

	@Override
	public String toString() {
		String ellipsis = value.length() > 20 ? value.substring(0,20) + "..." : value;
		return "SuspectedVersion [value=" + ellipsis + ", pages=" + pages + ", comparison=" + comparison + "]";
	}

}
