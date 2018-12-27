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
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportedTypesResponse implements Serializable {

	/**
	 * List of textal file types supported.
	 */
	@SerializedName("textual")
	@Expose
	private List<String> textual = null;
	/**
	 * List of file types supported using OCR
	 */
	@SerializedName("ocr")
	@Expose
	private List<String> ocr = null;
	private final static long serialVersionUID = 5518926385478748165L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public SupportedTypesResponse() {
	}

	/**
	 * Supported types Response. for more details see:
	 * https://api.copyleaks.com/documentation/specifications
	 * 
	 * @param textual:List of textual file types supported.
	 * @param ocr:List of file types supported using OCR
	 */
	public SupportedTypesResponse(List<String> textual, List<String> ocr) {
		super();
		this.textual = textual;
		this.ocr = ocr;
	}

	public List<String> getTextual() {
		return textual;
	}

	public void setTextual(List<String> textual) {
		this.textual = textual;
	}

	public List<String> getOcr() {
		return ocr;
	}

	public void setOcr(List<String> ocr) {
		this.ocr = ocr;
	}

}