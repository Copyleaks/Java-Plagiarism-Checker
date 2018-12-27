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

package copyleaks.sdk.api.models.types;

import com.google.gson.annotations.SerializedName;

/**
 * The status of the scan request
 */
public enum eScanStatus {
	/**
	 * The scan request has completed successfully
	 */
	@SerializedName("0")
	CompletedSuccessfully,
	/**
	 * The scan request has completed in error
	 */
	@SerializedName("1")
	Error,
	/**
	 * The check scan credits request has completed successfully
	 */
	@SerializedName("2")
	CreditsChecked,
	/**
	 * The Scanned content has been indexed in Copyleaks internal database
	 */
	@SerializedName("3")
	Indexed,
	/**
	 * The Scan is in progress
	 */
	@SerializedName("4")
	InProgress
}
