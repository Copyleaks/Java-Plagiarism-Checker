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

import copyleaks.sdk.api.models.types.eScanPriority;
import copyleaks.sdk.api.models.types.eSubmitAction;
import copyleaks.sdk.api.models.types.eSubmitOutputMode;

/**
 * The Scan properties for Businesses product  
 */
public class BusinessesScanProperties extends ScanProperties {

	/**
	 * 
	 */
	private static final long serialVersionUID = -841251550678810053L;

	/**
	 * Defines which mediums to scan.
	 */
	@SerializedName("scanning")
	@Expose
	private Scanning scanning;
	
	public BusinessesScanProperties() {	}
	
	public BusinessesScanProperties(eSubmitAction action, eSubmitOutputMode outputMode, String developerPayload,
			Boolean sandbox, Callbacks callbacks, Integer experation, Exclude exclude,
			Filters filters, Author author, Scanning scanning, eScanPriority prioriy) {
		super(action, outputMode, developerPayload, sandbox, callbacks, experation, exclude, filters, author, prioriy);
		this.scanning = scanning;
	}
	
	public Scanning getScanning() {
		return scanning;
	}

	public void setScanning(Scanning scanning) {
		this.scanning = scanning;
	}

}
