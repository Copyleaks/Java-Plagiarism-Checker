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

public class Comparison implements Serializable {

	private static final long serialVersionUID = 6003155956882984171L;

	@SerializedName("identical")
	@Expose
	private SuspectedComparisonResult identical;
	@SerializedName("minorChanges")
	@Expose
	private SuspectedComparisonResult minorChanges;
	@SerializedName("relatedMeaning")
	@Expose
	private SuspectedComparisonResult relatedMeaning;

	public Comparison() {
	}

	public Comparison(SuspectedComparisonResult identical, SuspectedComparisonResult minorChanges,
			SuspectedComparisonResult relatedMeaning) {
		super();
		this.identical = identical;
		this.minorChanges = minorChanges;
		this.relatedMeaning = relatedMeaning;
	}

	public SuspectedComparisonResult getIdentical() {
		return identical;
	}

	public void setIdentical(SuspectedComparisonResult identical) {
		this.identical = identical;
	}

	public SuspectedComparisonResult getMinorChanges() {
		return minorChanges;
	}

	public void setMinorChanges(SuspectedComparisonResult minorChanges) {
		this.minorChanges = minorChanges;
	}

	public SuspectedComparisonResult getRelatedMeaning() {
		return relatedMeaning;
	}

	public void setRelatedMeaning(SuspectedComparisonResult relatedMeaning) {
		this.relatedMeaning = relatedMeaning;
	}

	@Override
	public String toString() {
		return "Comparison [identical=" + identical + ", minorChanges=" + minorChanges + ", relatedMeaning="
				+ relatedMeaning + "]";
	}
	
}
