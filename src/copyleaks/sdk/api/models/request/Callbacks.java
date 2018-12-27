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

/**
 * Register your HTTP callback endpoints with Copyleaks API
 */
public class Callbacks implements Serializable {

	/**
	 * The callback that Copyleaks API will return to once the scan is completed
	 */
	@SerializedName("completion")
	@Expose
	private String completion;
	/**
	 * The callback that Copyleaks API will return to every time the a new result is
	 * found for the scan
	 */
	@SerializedName("onNewResult")
	@Expose
	private String onNewResult;
	private final static long serialVersionUID = -4971361197062299247L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Callbacks() {
	}

	/**
	 * Create a new callback section to register callbacks to your scan
	 * 
	 * @param completion: The callback that Copyleaks API will return to once the
	 *        scan is completed
	 * @param onNewResult: The callback that Copyleaks API will return to every time
	 *        the a new result is found for the scan
	 */
	public Callbacks(String completion, String onNewResult) {
		super();
		this.completion = completion;
		this.onNewResult = onNewResult;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public String getOnNewResult() {
		return onNewResult;
	}

	public void setOnNewResult(String onNewResult) {
		this.onNewResult = onNewResult;
	}

}
