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

package models.request.AiImageDetection;

/**
 * Request model for AI Image Detection with the image uploaded as {@code multipart/form-data}.
 * The image bytes are sent as they are, without base64 encoding.
 */
public class CopyleaksAiImageDetectionFileRequestModel {

    private byte[] image;
    private String filename;
    private String model;
    private boolean sandbox;
    private String maskType;
    private CopyleaksObjectDetectionItem[] objectDetection;

    /**
     * @param image    the image file bytes. Minimum 512x512px, maximum 6000x4500px, less than 32MB.
     * @param filename the image file name including its extension (.png, .jpg, .jpeg, .bmp, .webp, .heic, .heif).
     * @param model    the detection model, for example {@code CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA}.
     */
    public CopyleaksAiImageDetectionFileRequestModel(byte[] image, String filename, String model) {
        this.image = image;
        this.filename = filename;
        this.model = model;
    }

    /**
     * @param image    the image file bytes.
     * @param filename the image file name including its extension.
     * @param model    the detection model.
     * @param sandbox  true to use sandbox mode, which is free and returns mock results.
     */
    public CopyleaksAiImageDetectionFileRequestModel(byte[] image, String filename, String model, boolean sandbox) {
        this(image, filename, model);
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

    /** @return the detection model. */
    public String getModel() {
        return model;
    }

    /** @param model the detection model. */
    public void setModel(String model) {
        this.model = model;
    }

    /** @return true when sandbox mode is on. */
    public boolean isSandbox() {
        return sandbox;
    }

    /** @param sandbox true to use sandbox mode. */
    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    /** @return the mask type, or null for the server default. */
    public String getMaskType() {
        return maskType;
    }

    /** @param maskType "binary", "heatmap" or "none". See {@link models.constants.CopyleaksAiImageMaskTypes}. */
    public void setMaskType(String maskType) {
        this.maskType = maskType;
    }

    /** @return the objects to detect, or null. */
    public CopyleaksObjectDetectionItem[] getObjectDetection() {
        return objectDetection;
    }

    /** @param objectDetection the objects to detect, at most 3; null turns the feature off. */
    public void setObjectDetection(CopyleaksObjectDetectionItem[] objectDetection) {
        this.objectDetection = objectDetection;
    }
}
