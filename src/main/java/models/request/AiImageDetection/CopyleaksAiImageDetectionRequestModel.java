package models.request.AiImageDetection;

import com.google.gson.annotations.SerializedName;

/**
 * AI Image Detection request with a base64-encoded image. To skip base64, use
 * {@link CopyleaksAiImageDetectionFileRequestModel}.
 */
public class CopyleaksAiImageDetectionRequestModel {

	/** The image, base64-encoded. 512x512 to 6000x4500 px, under 32 MB. PNG, JPEG, BMP, WebP or HEIC/HEIF. */
	private String base64;

	/** File name with extension (.png, .bmp, .jpg, .jpeg, .webp, .heic, .heif), max 255 chars. */
	@SerializedName("filename")
	private String fileName;

	/** See {@link models.constants.CopyleaksAiImageDetectionModels}. */
	private String model;

	/** Return mock results without using credits. */
	private boolean sandbox = false;

	/** "binary" (default), "heatmap" or "none", see {@link models.constants.CopyleaksAiImageMaskTypes}. */
	private String maskType;

	/** Objects to locate and segment, at most 3. Null turns the feature off. */
	private CopyleaksObjectDetectionItem[] objectDetection;

	public CopyleaksAiImageDetectionRequestModel(String base64, String fileName, String model, boolean sandbox) {
		this.base64 = base64;
		this.fileName = fileName;
		this.model = model;
		this.sandbox = sandbox;
	}

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

	public String getMaskType() {
		return maskType;
	}

	public void setMaskType(String maskType) {
		this.maskType = maskType;
	}

	public CopyleaksObjectDetectionItem[] getObjectDetection() {
		return objectDetection;
	}

	public void setObjectDetection(CopyleaksObjectDetectionItem[] objectDetection) {
		this.objectDetection = objectDetection;
	}
}
