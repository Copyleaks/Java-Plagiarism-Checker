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

import copyleaks.sdk.api.models.types.eScanStatus;

public class ResultResponse implements Serializable {

	@SerializedName("scannedDocument")
	@Expose
	private ScannedDocument scannedDocument;
	@SerializedName("results")
	@Expose
	private Results results;
	@SerializedName("status")
	@Expose
	private eScanStatus status;
	@SerializedName("error")
	@Expose
	private Error error;
	@SerializedName("developerPayload")
	@Expose
	private String developerPayload;
	private final static long serialVersionUID = -5932783148906555529L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public ResultResponse() {
	}

	/**
	 * Returned from copyleaks API when requesting the results of a scan
	 * 
	 * @param developerPayload: Custom developer payload that was optionally add
	 *        when submitting the scan
	 * @param error: A section with the scan errors if any occurred
	 * @param results: A section with the scan results
	 * @param status: The status of the scan
	 * @param scannedDocument: The scan result
	 */
	public ResultResponse(ScannedDocument scannedDocument, Results results, eScanStatus status, Error error,
			String developerPayload) {
		super();
		this.scannedDocument = scannedDocument;
		this.results = results;
		this.status = status;
		this.error = error;
		this.developerPayload = developerPayload;
	}

	public ScannedDocument getScannedDocument() {
		return scannedDocument;
	}

	public void setScannedDocument(ScannedDocument scannedDocument) {
		this.scannedDocument = scannedDocument;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public eScanStatus getStatus() {
		return status;
	}

	public void setStatus(eScanStatus status) {
		this.status = status;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public String getDeveloperPayload() {
		return developerPayload;
	}

	public void setDeveloperPayload(String developerPayload) {
		this.developerPayload = developerPayload;
	}

}
