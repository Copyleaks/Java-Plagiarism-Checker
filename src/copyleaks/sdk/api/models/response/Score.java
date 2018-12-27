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

public class Score implements Serializable {

	@SerializedName("identicalWords")
	@Expose
	/**
	 * Number of exact words in the text
	 */
	private Integer identicalWords;
	@SerializedName("minorChangedWords")
	@Expose
	/**
	 * minorChangedWords: Number of nearly identical words with small differences
	 * like *slow* becomes *slowly*
	 */
	private Integer minorChangedWords;
	/**
	 * Number of paraphrased words stating similar ideas with different words
	 */

	@SerializedName("relatedMeaningWords")
	@Expose
	private Integer relatedMeaningWords;
	private final static long serialVersionUID = 4224240545560764138L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Score() {
	}

	/**
	 * The score for the scan
	 * 
	 * @param identicalWords: Number of exact words in the text
	 * @param minorChangedWords: Number of nearly identical words with small
	 *        differences like *slow* becomes *slowly*
	 * @param relatedMeaningWords: Number of paraphrased words stating similar ideas
	 *        with different words
	 */
	public Score(Integer identicalWords, Integer minorChangedWords, Integer relatedMeaningWords) {
		super();
		this.identicalWords = identicalWords;
		this.minorChangedWords = minorChangedWords;
		this.relatedMeaningWords = relatedMeaningWords;
	}

	public Integer getIdenticalWords() {
		return identicalWords;
	}

	public void setIdenticalWords(Integer identicalWords) {
		this.identicalWords = identicalWords;
	}

	public Integer getMinorChangedWords() {
		return minorChangedWords;
	}

	public void setMinorChangedWords(Integer minorChangedWords) {
		this.minorChangedWords = minorChangedWords;
	}

	public Integer getRelatedMeaningWords() {
		return relatedMeaningWords;
	}

	public void setRelatedMeaningWords(Integer relatedMeaningWords) {
		this.relatedMeaningWords = relatedMeaningWords;
	}

}
