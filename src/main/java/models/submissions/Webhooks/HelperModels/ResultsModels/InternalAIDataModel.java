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

package models.submissions.Webhooks.HelperModels.ResultsModels;

/**
 * A match found by Internal AI Source Match in the Copyleaks internal AI database.
 */
public class InternalAIDataModel {

    /*Short identifier of the match. */
    private String id;

    /*Title of the matched database entry. */
    private String title;

    /*A sample of the matched content. */
    private String introduction;

    /*Total number of matched words. */
    private int matchedWords;

    /*Number of identical words. */
    private int identicalWords;

    /*Number of similar but not identical words. */
    private int similarWords;

    /*Number of paraphrased words. */
    private int paraphrasedWords;

    /*Total number of words in the matched database entry. */
    private int totalWords;

    /*Tags object array */
    private TagsModel[] tags;

    /** @return the short identifier of the match. */
    public String getId() {
        return id;
    }

    /** @return the title of the matched database entry. */
    public String getTitle() {
        return title;
    }

    /** @return a sample of the matched content. */
    public String getIntroduction() {
        return introduction;
    }

    /** @return the total number of matched words. */
    public int getMatchedWords() {
        return matchedWords;
    }

    /** @return the number of identical words. */
    public int getIdenticalWords() {
        return identicalWords;
    }

    /** @return the number of similar but not identical words. */
    public int getSimilarWords() {
        return similarWords;
    }

    /** @return the number of paraphrased words. */
    public int getParaphrasedWords() {
        return paraphrasedWords;
    }

    /** @return the total number of words in the matched database entry. */
    public int getTotalWords() {
        return totalWords;
    }

    /** @return the result tags, or null. */
    public TagsModel[] getTags() {
        return tags;
    }
}
