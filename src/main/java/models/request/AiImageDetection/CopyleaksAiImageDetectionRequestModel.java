package models.request.AiImageDetection;

import com.google.gson.annotations.SerializedName;

/**
 * AI Image Detection request with a base64-encoded image. To skip base64, use
 * {@link CopyleaksAiImageDetectionFileRequestModel}.
 */
public class CopyleaksAiImageDetectionRequestModel {

	private String base64;

	@SerializedName("filename")
	private String fileName;

	private String model;

	private boolean sandbox = false;

	private String maskType;

	private CopyleaksObjectDetectionItem[] objectDetection;

	/**
	 * @param base64   the image, base64-encoded. 512x512 to 6000x4500 px (27 MP), under 32 MB;
	 *                 PNG, JPEG, BMP, WebP or HEIC/HEIF.
	 * @param fileName file name with extension (.png, .bmp, .jpg, .jpeg, .webp, .heic, .heif), max 255 chars.
	 * @param model    see {@link models.constants.CopyleaksAiImageDetectionModels}.
	 * @param sandbox  return mock results without using credits, for development only. Default: false.
	 */
	public CopyleaksAiImageDetectionRequestModel(String base64, String fileName, String model, boolean sandbox) {
		this.base64 = base64;
		this.fileName = fileName;
		this.model = model;
		this.sandbox = sandbox;
	}

	/**
	 * Sandbox mode off.
	 * @param base64   the image, base64-encoded. 512x512 to 6000x4500 px (27 MP), under 32 MB;
	 *                 PNG, JPEG, BMP, WebP or HEIC/HEIF.
	 * @param fileName file name with extension (.png, .bmp, .jpg, .jpeg, .webp, .heic, .heif), max 255 chars.
	 * @param model    see {@link models.constants.CopyleaksAiImageDetectionModels}.
	 */
	public CopyleaksAiImageDetectionRequestModel(String base64, String fileName, String model) {
		this(base64, fileName, model, false);
	}

	/** @return the base64-encoded image. */
	public String getBase64() {
		return base64;
	}

	/** Replaces the {@code base64} constructor argument. */
	public void setBase64(String base64) {
		this.base64 = base64;
	}

	/** @return the image file name. */
	public String getFileName() {
		return fileName;
	}

	/** Replaces the {@code fileName} constructor argument. */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/** @return the detection model. */
	public String getModel() {
		return model;
	}

	/** Replaces the {@code model} constructor argument. */
	public void setModel(String model) {
		this.model = model;
	}

	/** @return true when sandbox mode is on. */
	public boolean isSandbox() {
		return sandbox;
	}

	/** Replaces the {@code sandbox} constructor argument. */
	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}

	/** @return the mask type, or null for the server default. */
	public String getMaskType() {
		return maskType;
	}

	/** "binary" (default), "heatmap" or "none", see {@link models.constants.CopyleaksAiImageMaskTypes}. */
	public void setMaskType(String maskType) {
		this.maskType = maskType;
	}

	/** @return the objects to detect, or null. */
	public CopyleaksObjectDetectionItem[] getObjectDetection() {
		return objectDetection;
	}

	/** Objects to locate and segment, at most 3. Null turns the feature off. */
	public void setObjectDetection(CopyleaksObjectDetectionItem[] objectDetection) {
		this.objectDetection = objectDetection;
	}
}
