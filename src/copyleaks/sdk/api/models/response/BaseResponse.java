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

public abstract class BaseResponse implements Serializable {

	private static final long serialVersionUID = 2493696770415368009L;

	/**
	 * Custom developer payload that was optionally add when submitting the scan
	 */
	@SerializedName("status")
	@Expose
	private eScanStatus status;
	/**
	 * The status of the scan
	 */
	@SerializedName("error")
	@Expose
	/**
	 * A section with the scan errors if any occurred
	 */
	private Error error;
	@SerializedName("developerPayload")
	@Expose
	/**
	 * Custom developer payload that was optionally add when submitting the scan
	 */
	private String developerPayload;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public BaseResponse() {
	}

	/**
	 * A basic response returned from Copyleaks API once a scan with registered
	 * callbacks is completed
	 * 
	 * @param developerPayload: Custom developer payload that was optionally add
	 *        when submitting the scan
	 * @param error: A section with the scan errors if any occurred
	 * @param status: The status of the scan
	 */
	public BaseResponse(eScanStatus status, Error error, String developerPayload) {
		super();
		this.status = status;
		this.error = error;
		this.developerPayload = developerPayload;
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
