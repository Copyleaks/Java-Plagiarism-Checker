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

import copyleaks.sdk.api.models.types.eSubmitAction;
import copyleaks.sdk.api.models.types.eSubmitOutputMode;

public class ScanProperties implements Serializable {

	/**
	 * The type of action to submit
	 */
	@SerializedName("action")
	@Expose
	private eSubmitAction action;
	/**
	 * The output of the scan results
	 */
	@SerializedName("outputMode")
	@Expose
	private eSubmitOutputMode outputMode;
	/**
	 * Custom developer payload that will be attached to the scan results
	 */
	@SerializedName("developerPayload")
	@Expose
	private String developerPayload;
	/**
	 * Set true to enable sandbox mode, used for development
	 */
	@SerializedName("sandbox")
	@Expose
	private Boolean sandbox;
	/**
	 * Register your HTTP callback endpoints with Copyleaks API
	 */
	@SerializedName("callbacks")
	@Expose
	private Callbacks callbacks;
	/**
	 * The expiration time of the scan, when expired the scan results will be
	 * deleted from Copyleak's servers The maximum allowed value is 2880 (~ 4 month)
	 */
	@SerializedName("experation")
	@Expose
	private Integer experation;
	/**
	 * Defines which mediums to scan.
	 */
	@SerializedName("scanning")
	@Expose
	private Scanning scanning;
	/**
	 * Exclude properties from scan
	 */
	@SerializedName("exclude")
	@Expose
	private Exclude exclude;
	/**
	 * Customizable filters
	 */
	@SerializedName("filters")
	@Expose
	private Filters filters;
	/**
	 * Represent the author of the submitted content
	 */
	@SerializedName("author")
	@Expose
	private Author author;
	private final static long serialVersionUID = 4244964270182073373L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public ScanProperties() {
	}

	/**
	 * The scan request properties
	 * 
	 * It is recommended to use ScanPropertiesBuilder when creating a new ScanProperties object
	 * 
	 * @param author :Represent the author of the submitted content
	 * @param        developerPayload: Custom developer payload that will be
	 *               attached to the scan results
	 * @param        scanning: Defines which mediums to scan.
	 * @param        outputMode: The output of the scan results
	 * @param        callbacks: Register your HTTP callback endpoints with Copyleaks
	 *               API
	 * @param        sandbox: Set true to enable sandbox mode, used for development
	 * @param        action: The type of action to submit
	 * @param        exclude: Exclude properties from scan
	 * @param        filters: Customizable filters
	 * @param        experation: The expiration time of the scan, when expired the
	 *               scan results will be deleted from Copyleak's servers The
	 *               maximum allowed value is 2880 (~ 4 month)
	 */
	public ScanProperties(eSubmitAction action, eSubmitOutputMode outputMode, String developerPayload, Boolean sandbox,
			Callbacks callbacks, Integer experation, Scanning scanning, Exclude exclude, Filters filters,
			Author author) {
		super();
		this.action = action;
		this.outputMode = outputMode;
		this.developerPayload = developerPayload;
		this.sandbox = sandbox;
		this.callbacks = callbacks;
		this.experation = experation;
		this.scanning = scanning;
		this.exclude = exclude;
		this.filters = filters;
		this.author = author;
	}

	public eSubmitAction getAction() {
		return action;
	}

	public void setAction(eSubmitAction action) {
		this.action = action;
	}

	public eSubmitOutputMode getOutputMode() {
		return outputMode;
	}

	public void setOutputMode(eSubmitOutputMode outputMode) {
		this.outputMode = outputMode;
	}

	public String getDeveloperPayload() {
		return developerPayload;
	}

	public void setDeveloperPayload(String developerPayload) {
		this.developerPayload = developerPayload;
	}

	public Boolean getSandbox() {
		return sandbox;
	}

	public void setSandbox(Boolean sandbox) {
		this.sandbox = sandbox;
	}

	public Callbacks getCallbacks() {
		return callbacks;
	}

	public void setCallbacks(Callbacks callbacks) {
		this.callbacks = callbacks;
	}

	public Integer getExperation() {
		return experation;
	}

	public void setExperation(Integer experation) {
		this.experation = experation;
	}

	public Scanning getScanning() {
		return scanning;
	}

	public void setScanning(Scanning scanning) {
		this.scanning = scanning;
	}

	public Exclude getExclude() {
		return exclude;
	}

	public void setExclude(Exclude exclude) {
		this.exclude = exclude;
	}

	public Filters getFilters() {
		return filters;
	}

	public void setFilters(Filters filters) {
		this.filters = filters;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

}
