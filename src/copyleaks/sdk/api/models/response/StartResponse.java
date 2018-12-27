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

public class StartResponse implements Serializable {

	/**
	 * A list of successfully started scan id's
	 */
	@SerializedName("success")
	@Expose
	private List<Success> success = null;
	@SerializedName("failed")
	/**
	 * A list of scan id's that failed to start
	 */
	@Expose
	private List<Failed> failed = null;
	private final static long serialVersionUID = 6059153193295851332L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public StartResponse() {
	}

	/**
	 * The response received when submitting a start request to Copyleaks API
	 * 
	 * @param failed: A list of scan id's that failed to start
	 * @param success: A list of successfully started scan id's
	 */
	public StartResponse(List<Success> success, List<Failed> failed) {
		super();
		this.success = success;
		this.failed = failed;
	}

	public List<Success> getSuccess() {
		return success;
	}

	public void setSuccess(List<Success> success) {
		this.success = success;
	}

	public List<Failed> getFailed() {
		return failed;
	}

	public void setFailed(List<Failed> failed) {
		this.failed = failed;
	}

}
