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

package copyleaks.sdk.api.models.request.builders;

import copyleaks.sdk.api.models.request.Author;
import copyleaks.sdk.api.models.request.Callbacks;
import copyleaks.sdk.api.models.request.Exclude;
import copyleaks.sdk.api.models.request.Filters;
import copyleaks.sdk.api.models.request.ScanProperties;
import copyleaks.sdk.api.models.request.Scanning;
import copyleaks.sdk.api.models.types.eSubmitAction;
import copyleaks.sdk.api.models.types.eSubmitOutputMode;

public class ScanPropertiesBuilder {
	private eSubmitAction action = eSubmitAction.Scan;
	private eSubmitOutputMode outputMode = eSubmitOutputMode.TXT;
	private String developerPayload = null;
	private Boolean sandbox = true;
	private Callbacks callbacks = new Callbacks();
	private Integer expiration = 2880;
	private Scanning scanning = new Scanning();
	private Exclude exclude = new Exclude();
	private Filters filters = new Filters();
	private Author author = new Author();

	public ScanPropertiesBuilder() {
	}

	/**
	 * Add action to the document
	 * 
	 * @param action
	 * @return ScanPropertiesBuilder
	 */
	public ScanPropertiesBuilder setAction(eSubmitAction action) {
		this.action = action;
		return this;
	}

	/**
	 * Add output mode to the document
	 * 
	 * @param outputMode
	 * @return ScanPropertiesBuilder
	 */
	public ScanPropertiesBuilder setOutputMode(eSubmitOutputMode outputMode) {
		this.outputMode = outputMode;
		return this;
	}

	/**
	 * Add developerPayload to the document
	 * 
	 * @param developerPayload
	 * @return ScanPropertiesBuilder
	 */
	public ScanPropertiesBuilder setDeveloperPayload(String developerPayload) {
		this.developerPayload = developerPayload;
		return this;
	}

	/**
	 * Add sandbox mode to the document
	 * 
	 * @param sandbox
	 * @return ScanPropertiesBuilder
	 */
	public ScanPropertiesBuilder setSandbox(Boolean sandbox) {
		this.sandbox = sandbox;
		return this;
	}

	/**
	 * Add callbacks to the document
	 * 
	 * @param callbacks
	 * @return ScanPropertiesBuilder
	 */
	public ScanPropertiesBuilder setCallbacks(Callbacks callbacks) {
		this.callbacks = callbacks;
		return this;
	}

	/**
	 * Add scan expiration to the document
	 * 
	 * @param expiration
	 * @return
	 */
	public ScanPropertiesBuilder setExperation(Integer expiration) {
		this.expiration = expiration;
		return this;
	}

	/**
	 * Add scanning section to the document
	 * 
	 * @param scanning
	 * @return ScanPropertiesBuilder
	 */
	public ScanPropertiesBuilder setScanning(Scanning scanning) {
		this.scanning = scanning;
		return this;
	}

	/**
	 * Add exclude section to the document
	 * 
	 * @param exclude
	 * @return ScanPropertiesBuilder
	 */
	public ScanPropertiesBuilder setExclude(Exclude exclude) {
		this.exclude = exclude;
		return this;
	}

	/**
	 * Add filters section to the document
	 * 
	 * @param filters
	 * @return ScanPropertiesBuilder
	 */
	public ScanPropertiesBuilder setFilters(Filters filters) {
		this.filters = filters;
		return this;
	}

	/**
	 * Add author to the document
	 * 
	 * @param author
	 * @return ScanPropertiesBuilder
	 */
	public ScanPropertiesBuilder setAuthor(Author author) {
		this.author = author;
		return this;
	}

	/**
	 * Build the ScanProperties
	 * 
	 * @return ScanProperties
	 */
	public ScanProperties build() {
		return new ScanProperties(action, outputMode, developerPayload, sandbox, callbacks, expiration, scanning,
				exclude, filters, author);
	}

}
