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

public class DatabaseResult extends BasicInternalResult {

	private final static long serialVersionUID = 4240126821090762521L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public DatabaseResult() {
	}

	/**
	 * A compare result from Copyleaks internal database
	 * 
	 * @param id: Unique result id
	 * @param matchedWords: Number of matched words
	 * @param title: The results title
	 * @param scanId: The scan id
	 * @param introduction: The results introduction
	 */
	public DatabaseResult(String scanId, String id, String title, String introduction, Integer matchedWords) {
		super(scanId, id, title, introduction, matchedWords);
	}

	/**
	 * Is document uploaded in the past by the same user
	 * 
	 * @return true if the content was uploaded by me, false if was uploaded by
	 *         another user
	 */
	public boolean isUploadedByMe() {
		return getScanId() == null;
	}

}
