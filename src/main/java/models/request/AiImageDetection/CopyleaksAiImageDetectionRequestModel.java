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

import com.google.gson.annotations.SerializedName;

/**
 * Request model for Copyleaks AI image detection.
 * The request body is a JSON object containing the image to analyze.
 * To upload the image bytes without base64 encoding, use {@link CopyleaksAiImageDetectionFileRequestModel}.
 */
public class CopyleaksAiImageDetectionRequestModel {

	/**
	 * The base64-encoded image data to be analyzed for AI generation.
	 * <p>
	 * Requirements:
	 * - Minimum 512x512px, maximum 6000x4500px (27 megapixels), less than 32MB
	 * - Supported formats: PNG, JPEG, BMP, WebP, HEIC/HEIF
	 * <p>
	 * Example: "aGVsbG8gd29ybGQ="
	 */
	private String base64;

	/**
	 * The name of the image file including its extension. Sent as {@code filename}.
	 * <p>
	 * Requirements:
	 * - Supported extensions: .png, .bmp, .jpg, .jpeg, .webp, .heic, .heif
	 * - Maximum 255 characters
	 * <p>
	 * Example: "my-image.png"
	 */
	@SerializedName("filename")
	private String fileName;

	/**
	 * The AI detection model to use for analysis.
	 * <p>
	 * Available models:
	 * - AI Image 1 Ultra: "ai-image-1-ultra"
	 *   AI image detection model. Produces an overlay of the detected AI segments.
	 * <p>
	 * Example: "ai-image-1-ultra"
	 */
	private String model;

	/**
	 * Use sandbox mode to test your integration with the Copyleaks API without consuming any credits.
	 * <p>
	 * Submit images for AI detection and get returned mock results, simulating Copyleaks' API functionality
	 * to ensure you have successfully integrated the API.
	 * This feature is intended to be used for development purposes only.
	 * Default value is false.
	 */
	private boolean sandbox = false;

	/**
	 * The type of detection mask to return: "binary" (default), "heatmap" or "none".
	 * See {@link models.constants.CopyleaksAiImageMaskTypes}. Null leaves the server default.
	 */
	private String maskType;

	/**
	 * Objects to locate and segment in the image, at most 3. Null turns the feature off.
	 */
	private CopyleaksObjectDetectionItem[] objectDetection;

	/**
	 * Constructor for CopyleaksAiImageDetectionRequestModel.
	 * @param base64   The base64-encoded image data.
	 * @param fileName The name of the image file including its extension.
	 * @param model    The AI detection model to use for analysis.
	 * @param sandbox  Use sandbox mode (optional, default is false).
	 */
	public CopyleaksAiImageDetectionRequestModel(String base64, String fileName, String model, boolean sandbox) {
		this.base64 = base64;
		this.fileName = fileName;
		this.model = model;
		this.sandbox = sandbox;
	}

	/**
	 * Constructor with sandbox defaulting to false.
	 * @param base64   The base64-encoded image data.
	 * @param fileName The name of the image file including its extension.
	 * @param model    The AI detection model to use for analysis.
	 */
	public CopyleaksAiImageDetectionRequestModel(String base64, String fileName, String model) {
		this(base64, fileName, model, false);
	}

	/** @return the base64-encoded image. */
	public String getBase64() {
		return base64;
	}

	/** @param base64 the base64-encoded image. */
	public void setBase64(String base64) {
		this.base64 = base64;
	}

	/** @return the image file name. */
	public String getFileName() {
		return fileName;
	}

	/** @param fileName the image file name, including its extension. */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
