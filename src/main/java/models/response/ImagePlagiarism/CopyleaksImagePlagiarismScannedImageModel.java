package models.response.ImagePlagiarism;

import models.response.CopyleaksScanInfoModel;

/** Information about the submitted image and the scan. */
public class CopyleaksImagePlagiarismScannedImageModel extends CopyleaksScanInfoModel {

    private int width;

    private int height;

    private String filename;

    /** Image width in pixels. */
    public int getWidth() {
        return width;
    }

    /** Image height in pixels. */
    public int getHeight() {
        return height;
    }

    /** File name given in the request. */
    public String getFilename() {
        return filename;
    }
}
