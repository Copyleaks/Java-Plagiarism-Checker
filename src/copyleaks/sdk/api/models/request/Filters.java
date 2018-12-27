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

import copyleaks.sdk.api.models.types.eDomainsFilteringMode;

public class Filters implements Serializable {

	/**
	 * Enable/disable identical matches
	 */
	@SerializedName("idenitcalEnabled")
	@Expose
	private Boolean idenitcalEnabled;
	/**
	 * Enable/disable minor changes matches
	 */
	@SerializedName("minorChangedEnabled")
	@Expose
	private Boolean minorChangedEnabled;
	/**
	 * Enable/disable related meaning matches
	 */
	@SerializedName("relatedMeaningEnabled")
	@Expose
	private Boolean relatedMeaningEnabled;
	/**
	 * Set the minimum copied words to be considered as plagiarized content
	 */
	@SerializedName("minCopiedWords")
	@Expose
	private Integer minCopiedWords;
	/**
	 * When set to true the result will not include explicit results. Safe search is
	 * not 100% accurate but it can help avoid explicit and inappropriate search
	 * results
	 */
	@SerializedName("safeSearch")
	@Expose
	private Boolean safeSearch;
	/**
	 * A list of domain to include or exclude from sources
	 */
	@SerializedName("domains")
	@Expose
	private List<String> domains = null;
	/**
	 * Treat `domains` as include or exclude list
	 */
	@SerializedName("domainsMode")
	@Expose
	private eDomainsFilteringMode domainsMode;

	private final static long serialVersionUID = 1180725683536734752L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Filters() {
	}

	/**
	 * Create a new customizable filters
	 * 
	 * @param idenitcalEnabled: Enable/disable identical matches
	 * @param minorChangedEnabled: Enable/disable minor changes matches
	 * @param relatedMeaningEnabled: Enable/disable related meaning matches
	 * @param minCopiedWords: Set the minimum copied words to be considered as
	 * @param safeSearch: When set to true the result will not include explicit
	 *        results. Safe search is not 100% accurate but it can help avoid
	 *        explicit and inappropriate search results
	 * @param domains:A list of domain to include or exclude from sources
	 * @param domainsMode:Treat `domains` as include or exclude list
	 *        plagiarized content
	 */
	public Filters(Boolean idenitcalEnabled, Boolean minorChangedEnabled, Boolean relatedMeaningEnabled,
			Integer minCopiedWords, Boolean safeSearch, List<String> domains, eDomainsFilteringMode domainsMode) {
		super();
		this.idenitcalEnabled = idenitcalEnabled;
		this.minorChangedEnabled = minorChangedEnabled;
		this.relatedMeaningEnabled = relatedMeaningEnabled;
		this.minCopiedWords = minCopiedWords;
		this.safeSearch = safeSearch;
		this.domains = domains;
		this.domainsMode = domainsMode;
	}

	public Boolean getIdenitcalEnabled() {
		return idenitcalEnabled;
	}

	public void setIdenitcalEnabled(Boolean idenitcalEnabled) {
		this.idenitcalEnabled = idenitcalEnabled;
	}

	public Boolean getMinorChangedEnabled() {
		return minorChangedEnabled;
	}

	public void setMinorChangedEnabled(Boolean minorChangedEnabled) {
		this.minorChangedEnabled = minorChangedEnabled;
	}

	public Boolean getRelatedMeaningEnabled() {
		return relatedMeaningEnabled;
	}

	public void setRelatedMeaningEnabled(Boolean relatedMeaningEnabled) {
		this.relatedMeaningEnabled = relatedMeaningEnabled;
	}

	public Integer getMinCopiedWords() {
		return minCopiedWords;
	}

	public void setMinCopiedWords(Integer minCopiedWords) {
		this.minCopiedWords = minCopiedWords;
	}

	public Boolean getSafeSearch() {
		return safeSearch;
	}

	public void setSafeSearch(Boolean safeSearch) {
		this.safeSearch = safeSearch;
	}

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}

	public eDomainsFilteringMode getDomainsMode() {
		return this.domainsMode;
	}

	public void setDomainsMode(eDomainsFilteringMode domainsMode) {
		this.domainsMode = domainsMode;
	}

}
