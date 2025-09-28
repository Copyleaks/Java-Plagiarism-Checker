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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

/**
 * Request model for Copyleaks AI image detection.
 * The request body is a JSON object containing the image to analyze.
 */
public class CopyleaksAiImageDetectionRequestModel {

	/**
	 * The base64-encoded image data to be analyzed for AI generation.
	 * <p>
	 * Requirements:
	 * - Minimum 512×512px, maximum 16 megapixels, less than 32MB
	 * - Supported formats: PNG, JPEG, BMP, WebP, HEIC/HEIF
	 * <p>
	 * Example: "aGVsbG8gd29ybGQ="
	 */
	@NonNull
	@JsonProperty("base64")
	private String base64;

	/**
	 * The name of the image file including its extension.
	 * <p>
	 * Requirements:
	 * - Supported extensions: .png, .bmp, .jpg, .jpeg, .webp, .heic, .heif
	 * - Maximum 255 characters
	 * <p>
	 * Example: "my-image.png"      
	 */
	@NonNull
	@JsonProperty("fileName")
	private String fileName;

	/**
	 * The AI detection model to use for analysis.
	 * You can use either the full model name or its alias.
	 * <p>
	 * Available models:
	 * - AI Image 1 Ultra: "ai-image-1-ultra-01-09-2025" (full name) or "ai-image-1-ultra" (alias)
	 *   AI image detection model. Produces an overlay of the detected AI segments.
	 * <p>
	 * Example: "ai-image-1-ultra-01-09-2025" or "ai-image-1-ultra"
	 */
	@NonNull
	@JsonProperty("model")
	private String model;

	/**
	 * Use sandbox mode to test your integration with the Copyleaks API without consuming any credits.
	 * <p>
	 * Submit images for AI detection and get returned mock results, simulating Copyleaks' API functionality
	 * to ensure you have successfully integrated the API.
	 * This feature is intended to be used for development purposes only.
	 * Default value is false.
	 * <p>
	 * Example: false
	 */
	@JsonProperty("sandbox")
	private boolean sandbox = false;

	/**
	 * Constructor for CopyleaksAiImageDetectionRequestModel.
	 *
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
	 */
	public CopyleaksAiImageDetectionRequestModel(String base64, String fileName, String model) {
		this(base64, fileName, model, false);
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public boolean isSandbox() {
		return sandbox;
	}

	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}
}
