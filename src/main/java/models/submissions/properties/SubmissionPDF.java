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

public class SubmissionPDF {
    /**
     * Add a request to generate a customizable export of the scan report, in a pdf format.
     * Set to true in order to generate a pdf report for this scan.
     */
    private boolean create;
    /**
     * Customize the title for the PDF report.
     */
    private String title;
    /**
     * Customize the logo image in the PDF report.
     */
    private String largeLogo;
    /**
     * When set to true the text in the report will be aligned from right to left.
     */
    private boolean rtl;
    /**
     * 	PDF version to generate. 
	 *  By default version 1 will be generated as it our current stable version. 
	 *  Version 2 is our latest iteration of our PDF report.
     */
    private SubmissionPdfVersion version;
    /**
     * Customizable colors
     */
    private SubmissionPdfColors colors;

    public boolean isCreate() {
        return create;
    }
    public void setCreate(boolean create) {
        this.create = create;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLargeLogo() {
        return largeLogo;
    }
    public void setLargeLogo(String largeLogo) {
        this.largeLogo = largeLogo;
    }
    public boolean isRtl() {
        return rtl;
    }
    public void setRtl(boolean rtl) {
        this.rtl = rtl;
    }
    public SubmissionPdfVersion getVersion() {
        return version;
    }
    public void setVersion(SubmissionPdfVersion version) {
        this.version = version;
    }
    public SubmissionPdfColors getColors() {
        return colors;
    }
    public void setColors(SubmissionPdfColors colors) {
        this.colors = colors;
    }

}
