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

package models.submissions.properties;

public class SubmissionScanningExclude {
    /**
     * Exclude your submissions from results if their id matches the supplied pattern. Matched submissions will be excluded from batch, internal database and repositories results.
     * * Supported pattern wildcards:
     * * '*' Matches any, zero or more, characters.
     * * '.' Matches a single (non whitespace) character
     */
    private String idPattern = null;

    /**
     * Exclude results that contain backlinks to these domains. At most 10 domains, each 1 to 256 characters.
     */
    private String[] backlinksDomains = null;

    /**
     * Exclude results that contain any of these phrases. At most 5 phrases, each 1 to 30 characters.
     */
    private String[] text = null;

    /**
     * @return the ID pattern, or null.
     */
    public String getIdPattern() {
        return idPattern;
    }

    /**
     * @param idPattern exclude submissions whose ID matches this pattern. {@code *} matches any characters
     *                  and {@code .} matches one non-whitespace character.
     */
    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }

    /**
     * @return the backlink domains to exclude, or null.
     */
    public String[] getBacklinksDomains() {
        return backlinksDomains;
    }

    /**
     * @param backlinksDomains exclude results that contain backlinks to these domains (at most 10).
     */
    public void setBacklinksDomains(String[] backlinksDomains) {
        this.backlinksDomains = backlinksDomains;
    }

    /**
     * @return the phrases to exclude, or null.
     */
    public String[] getText() {
        return text;
    }

    /**
     * @param text exclude results that contain any of these phrases (at most 5, each 1 to 30 characters).
     */
    public void setText(String[] text) {
        this.text = text;
    }
}
