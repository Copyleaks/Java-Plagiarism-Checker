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
 * A matching image found on the web.
 */
public class CopyleaksImagePlagiarismInternetMatchModel {

    /** {@code matchType} value of a full match: an exact or near-exact copy. */
    public static final int MATCH_TYPE_FULL = 0;

    /** {@code matchType} value of a partial match: a cropped, resized, recolored or otherwise modified copy. */
    public static final int MATCH_TYPE_PARTIAL = 1;

    /** The URL of the matching image. */
    private String url;

    /** The match type: 0 (full) is an exact or near-exact copy, 1 (partial) is a cropped, resized or otherwise modified copy. */
    private int matchType;

    /** The web pages where the image was found, or null when none were found. */
    private CopyleaksImagePlagiarismWebPageModel[] webPages;

    /** @return the URL of the matching image. */
    public String getUrl() {
        return url;
    }

    /** @return the match type: 0 (full) is an exact or near-exact copy, 1 (partial) is a cropped, resized or otherwise modified copy. */
    public int getMatchType() {
        return matchType;
    }

    /** @return the web pages where the image was found, or null when none were found. */
    public CopyleaksImagePlagiarismWebPageModel[] getWebPages() {
        return webPages;
    }
}
