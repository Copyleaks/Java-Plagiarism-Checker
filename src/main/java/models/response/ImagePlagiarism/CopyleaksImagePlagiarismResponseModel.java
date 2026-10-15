package models.response.ImagePlagiarism;

public class CopyleaksImagePlagiarismResponseModel {

    /** From the request, or null. */
    private String developerPayload;

    private CopyleaksImagePlagiarismScannedImageModel scannedImage;

    private CopyleaksImagePlagiarismMatchesModel matches;

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public CopyleaksImagePlagiarismScannedImageModel getScannedImage() {
        return scannedImage;
    }

    public CopyleaksImagePlagiarismMatchesModel getMatches() {
        return matches;
    }
}
