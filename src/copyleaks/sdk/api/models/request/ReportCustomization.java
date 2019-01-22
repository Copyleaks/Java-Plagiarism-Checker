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

public class ReportCustomization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7305621483644295391L;

	@SerializedName("Title")
	@Expose
	private Boolean Title;
	@SerializedName("LargeLogo")
	@Expose
	private String LargeLogo;
	@SerializedName("SmallLogo")
	@Expose
	private String SmallLogo;
	@SerializedName("RTL")
	@Expose
	private Boolean RTL;
	@SerializedName("Colors")
	@Expose
	private ReportCustomizationColors Colors;
		
	public ReportCustomization() {
		
	}
	
	public ReportCustomization(Boolean Title, String LargeLogo, String SmallLogo, Boolean RTL, ReportCustomizationColors Colors) {
		super();
		this.Title = Title;
		this.LargeLogo = LargeLogo;
		this.SmallLogo = SmallLogo;
		this.RTL = RTL;
		this.Colors = Colors;
	}

	public Boolean getTitle() {
		return Title;
	}

	public void setTitle(Boolean title) {
		Title = title;
	}

	public String getLargeLogo() {
		return LargeLogo;
	}

	public void setLargeLogo(String largeLogo) {
		LargeLogo = largeLogo;
	}

	public String getSmallLogo() {
		return SmallLogo;
	}

	public void setSmallLogo(String smallLogo) {
		SmallLogo = smallLogo;
	}

	public Boolean getRTL() {
		return RTL;
	}

	public void setRTL(Boolean rTL) {
		RTL = rTL;
	}

	public ReportCustomizationColors getColors() {
		return Colors;
	}

	public void setColors(ReportCustomizationColors colors) {
		Colors = colors;
	}

}
