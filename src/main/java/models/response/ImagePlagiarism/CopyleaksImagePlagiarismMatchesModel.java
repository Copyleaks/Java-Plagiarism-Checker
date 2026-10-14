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
 * The matches found for an image. An empty internet array with zero scores means no copies were found.
 */
public class CopyleaksImagePlagiarismMatchesModel {

    /** The matching images on the web, full matches first. */
    private CopyleaksImagePlagiarismInternetMatchModel[] internet;

    /** The number of matches by type. */
    private CopyleaksImagePlagiarismScoreModel score;

    /** @return the matching images on the web, full matches first. */
    public CopyleaksImagePlagiarismInternetMatchModel[] getInternet() {
        return internet;
    }

    /** @return the number of matches by type. */
    public CopyleaksImagePlagiarismScoreModel getScore() {
        return score;
    }
}
