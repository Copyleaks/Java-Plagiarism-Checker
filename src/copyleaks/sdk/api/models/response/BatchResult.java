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

public class BatchResult extends BasicInternalResult {

	private final static long serialVersionUID = 2387359377720787345L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public BatchResult() {
	}

	/**
	 * The result for comparison between different documents
	 * 
	 * @param id: Unique result id
	 * @param matchedWords: Number of matched words
	 * @param title: Result title
	 * @param scanId: The scan id that was provided when submitting the batch
	 *        request
	 * @param comparison: downloadable comparison report
	 * @param introduction: The results introduction
	 */
	public BatchResult(String scanId, String id, String title, String introduction, Integer matchedWords) {
		super(scanId, id, title, introduction, matchedWords);

	}

}
