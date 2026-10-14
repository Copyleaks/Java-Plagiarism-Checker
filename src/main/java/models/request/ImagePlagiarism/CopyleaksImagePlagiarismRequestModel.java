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

package models.request.ImagePlagiarism;

/**
 * Request model for Image Plagiarism Detection. The image is uploaded as {@code multipart/form-data}.
 */
public class CopyleaksImagePlagiarismRequestModel {

    private byte[] image;
    private String filename;
    private boolean sandbox;
    private String developerPayload;

    /**
     * @param image    the image file bytes. Less than 20MB and at most 75 megapixels.
     * @param filename the image file name including its extension (.jpg, .jpeg, .png, .gif, .bmp, .webp, .ico),
     *                 at most 255 characters.
     */
    public CopyleaksImagePlagiarismRequestModel(byte[] image, String filename) {
        this.image = image;
        this.filename = filename;
    }

    /**
     * @param image    the image file bytes.
     * @param filename the image file name including its extension.
     * @param sandbox  true to use sandbox mode, which is free and returns mock results.
     */
    public CopyleaksImagePlagiarismRequestModel(byte[] image, String filename, boolean sandbox) {
        this(image, filename);
        this.sandbox = sandbox;
    }

    /** @return the image bytes. */
    public byte[] getImage() {
        return image;
    }

    /** @param image the image bytes. */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /** @return the image file name. */
    public String getFilename() {
        return filename;
    }

    /** @param filename the image file name, including its extension. */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /** @return true when sandbox mode is on. */
    public boolean isSandbox() {
        return sandbox;
    }

    /** @param sandbox true to use sandbox mode. */
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    /** @return the developer payload, or null. */
    public String getDeveloperPayload() {
        return developerPayload;
    }

    /** @param developerPayload a string that is echoed back in the response. */
    public void setDeveloperPayload(String developerPayload) {
        this.developerPayload = developerPayload;
    }
}
