package models.response.ImagePlagiarism;

/** The Image Plagiarism Detection result. */
public class CopyleaksImagePlagiarismResponseModel {

    private String developerPayload;

    private CopyleaksImagePlagiarismScannedImageModel scannedImage;

    private CopyleaksImagePlagiarismMatchesModel matches;

    /** Developer payload from the request, or null. */
    public String getDeveloperPayload() {
        return developerPayload;
    }

    /** Information about the submitted image and the scan. */
    public CopyleaksImagePlagiarismScannedImageModel getScannedImage() {
        return scannedImage;
    }

    /** Matches found on the web. */
    public CopyleaksImagePlagiarismMatchesModel getMatches() {
        return matches;
    }
}
