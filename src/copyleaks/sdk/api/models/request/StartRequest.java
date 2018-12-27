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
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import copyleaks.sdk.api.models.types.eErrorHandling;

public class StartRequest implements Serializable {

	/**
	 * The process id to start scanning
	 */
	@SerializedName("trigger")
	@Expose
	private List<String> trigger = null;
	/**
	 * Define what to do in case of error
	 */
	@SerializedName("errorHandling")
	@Expose
	private eErrorHandling errorHandling;
	private final static long serialVersionUID = 6468135872859235723L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public StartRequest() {
	}

	/**
	 * Start a request of a scan in 'price checked' status
	 * 
	 * @param trigger: The process id to start scanning
	 * @param errorHandling: Define what to do in case of error
	 */
	public StartRequest(List<String> trigger, eErrorHandling errorHandling) {
		super();
		this.trigger = trigger;
		this.errorHandling = errorHandling;
	}

	public List<String> getTrigger() {
		return trigger;
	}

	public void setTrigger(List<String> trigger) {
		this.trigger = trigger;
	}

	public eErrorHandling getErrorHandling() {
		return errorHandling;
	}

	public void setErrorHandling(eErrorHandling errorHandling) {
		this.errorHandling = errorHandling;
	}

}