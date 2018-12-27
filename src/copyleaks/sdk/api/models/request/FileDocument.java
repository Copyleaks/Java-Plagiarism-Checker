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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileDocument extends Document {

	@SerializedName("base64")
	@Expose
	private String base64;
	@SerializedName("filename")
	@Expose
	private String filename;
	
	private final static long serialVersionUID = -5514176145361599314L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public FileDocument() {
	}

	/**
	 * Create a new file document for submission
	 * 
	 *  It is recommended to use FileDocumentBuilder when creating a new FileDocument object
	 * @param base64: Base64 file content. Bytes -> base64 string
	 * @param filename: The original filename.
	 * @param properties: The scan request properties
	 */
	public FileDocument(String base64, String filename, ScanProperties properties) {
		super(properties);
		this.base64 = base64;
		this.filename = filename;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}