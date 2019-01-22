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

public class ReportCustomizationColors implements Serializable{

	private static final long serialVersionUID = -2674643243898640950L;

	@SerializedName("MainStrip")
	@Expose
	private String MainStrip;
	@SerializedName("titles")
	@Expose
	private String titles;
	@SerializedName("Identical")
	@Expose
	private String Identical;
	@SerializedName("MinorChanges")
	@Expose
	private String MinorChanges;
	@SerializedName("RelatedMeaning")
	@Expose
	private String RelatedMeaning;
	
	public ReportCustomizationColors() {}
	
	public ReportCustomizationColors(String MainStrip, String titles, String Identical, String MinorChanges, String RelatedMeaning) {
		super();
		this.MainStrip = MainStrip;
		this.titles = titles;
		this.Identical = Identical;
		this.MinorChanges = MinorChanges;
		this.RelatedMeaning = RelatedMeaning;
	}

	public String getMainStrip() {
		return MainStrip;
	}

	public void setMainStrip(String mainStrip) {
		MainStrip = mainStrip;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getIdentical() {
		return Identical;
	}

	public void setIdentical(String identical) {
		Identical = identical;
	}

	public String getMinorChanges() {
		return MinorChanges;
	}

	public void setMinorChanges(String minorChanges) {
		MinorChanges = minorChanges;
	}

	public String getRelatedMeaning() {
		return RelatedMeaning;
	}

	public void setRelatedMeaning(String relatedMeaning) {
		RelatedMeaning = relatedMeaning;
	}

}
