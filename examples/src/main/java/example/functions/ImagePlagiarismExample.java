
package example.functions;

import static java.nio.file.Files.readAllBytes;

import java.io.IOException;
import java.nio.file.Paths;

import classes.Copyleaks;
import models.request.ImagePlagiarism.CopyleaksImagePlagiarismRequestModel;
import models.response.CopyleaksAuthToken;
import models.response.ImagePlagiarism.CopyleaksImagePlagiarismInternetMatchModel;
import models.response.ImagePlagiarism.CopyleaksImagePlagiarismResponseModel;

/**
 * Example for Image Plagiarism Detection.
 */
public class ImagePlagiarismExample {

    /**
     * Uploads an image and prints where copies of it were found on the web.
     * @param token Copyleaks authentication token
     * @param scanId Unique scan identifier
     */
    public static void run(CopyleaksAuthToken token, String scanId) {
        byte[] imageBytes;
        try {
            imageBytes = readAllBytes(Paths.get("PATH TO YOUR IMAGE"));
        } catch (IOException e) {
            System.out.println("Failed to read image file: " + e.getMessage());
            return;
        }

        // Sandbox mode is free and returns mock results. Turn it off in production.
        CopyleaksImagePlagiarismRequestModel request = new CopyleaksImagePlagiarismRequestModel(imageBytes, "my-photo.jpg", true);

        try {
            CopyleaksImagePlagiarismResponseModel result = Copyleaks.imagePlagiarismClient.check(token, scanId, request);
            System.out.println("Total matches: " + result.getMatches().getScore().getTotalMatches());
            for (CopyleaksImagePlagiarismInternetMatchModel match : result.getMatches().getInternet()) {
                String type = match.getMatchType() == CopyleaksImagePlagiarismInternetMatchModel.MATCH_TYPE_FULL ? "full" : "partial";
                System.out.println(type + " match: " + match.getUrl());
            }
        } catch (Exception e) {
            System.out.println("Error during image plagiarism detection: " + e.getMessage());
        }
    }
}
