/*
 The MIT License(MIT)
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package models.response.ImagePlagiarism;

/**
 * The number of matches by type.
 */
public class CopyleaksImagePlagiarismScoreModel {

    /** The total number of matching images. */
    private int totalMatches;

    /** The number of full matches (matchType 0). */
    private int fullMatches;

    /** The number of partial matches (matchType 1). */
    private int partialMatches;

    /** @return the total number of matching images. */
    public int getTotalMatches() {
        return totalMatches;
    }

    /** @return the number of full matches (matchType 0). */
    public int getFullMatches() {
        return fullMatches;
    }

    /** @return the number of partial matches (matchType 1). */
    public int getPartialMatches() {
        return partialMatches;
    }
}
