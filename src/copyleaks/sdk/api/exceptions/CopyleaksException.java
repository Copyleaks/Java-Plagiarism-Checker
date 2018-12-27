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

package copyleaks.sdk.api.exceptions;

import com.google.gson.Gson;

import copyleaks.sdk.api.models.response.BadResponse;

public class CopyleaksException extends Exception {

	private static final int UnknownErrorCode = 9999;

	/**
	 * An exception for a failed HTTP call to Copyleaks API.
     * The exception includes the Copyleaks error code and the error message
     * A detailed list of Copyleaks error codes can be found at: https://api.copyleaks.com/documentation/errors
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The HTTP status code returned from Copyleaks API
	 */
	private int httpStatusCode;

	/**
	 * Copyleaks error code
	 * A detailed list of Copyleaks error codes can be found at: https://api.copyleaks.com/documentation/errors
	 * 
	 */
	private short copyleaksErrorCode = UnknownErrorCode;

	/**
	 * 
	 */
	private String message;

	private Gson gson;


	public CopyleaksException(String response, int statusCode) {
		this.gson = new Gson();
		this.parseResponse(response);
		this.setHttpStatusCode(statusCode);
	}

	private void parseResponse(String response) {
		BadResponse badResponse = this.gson.fromJson(response, BadResponse.class);
		this.setCopyleaksErrorCode(badResponse.getCopyleaksErrorCode());
		this.setMessage(badResponse.getMessage());
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public short getCopyleaksErrorCode() {
		return copyleaksErrorCode;
	}

	public void setCopyleaksErrorCode(short copyleaksErrorCode) {
		this.copyleaksErrorCode = copyleaksErrorCode;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("%d %s, copyleaks error code: %s", this.getHttpStatusCode(), this.getMessage(),
				this.getCopyleaksErrorCode());
	}
}
