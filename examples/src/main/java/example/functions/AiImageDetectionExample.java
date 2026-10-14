
package example.functions;

import classes.Copyleaks;
import models.constants.CopyleaksAiImageDetectionModels;
import models.request.AiImageDetection.CopyleaksAiImageDetectionRequestModel;
import models.response.CopyleaksAuthToken;
import models.response.AiImageDetection.CopyleaksAiImageDetectionResponseModel;
import com.google.gson.Gson;
import static java.nio.file.Files.readAllBytes;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.IOException;
/**
 * Example for submitting an image to Copyleaks AI Image Detection API.
 */
public class AiImageDetectionExample {

    /**
     * Submits an image for AI image detection and prints the response.
     * @param token Copyleaks authentication token
     * @param scanId Unique scan identifier
     * @param gson Gson instance for JSON output
     */
    public static void run(CopyleaksAuthToken token, String scanId, Gson gson) {

        // Path to the image file to be analyzed
        String imagePath = "PATH TO YOUR IMAGE";
        byte[] imageBytes;

        // Read the image file into a byte array
        try {
            imageBytes = readAllBytes(Paths.get(imagePath));
        } catch (IOException e) {
            System.out.println("Failed to read image file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Encode the image as Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Create the image detection request model
        CopyleaksAiImageDetectionRequestModel imageDetectionRequest = new CopyleaksAiImageDetectionRequestModel(
            base64Image,
            "my-image.png",
            CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA,
            true
        );

        try {
            // Submit the image for AI detection
            CopyleaksAiImageDetectionResponseModel imageDetectionResponse = Copyleaks.aiImageDetectionClient.submit(token, scanId, imageDetectionRequest);

            // Output the results
            System.out.println("\nImage scanned for AI Image Detection.");
            System.out.println("Image Detection Response: " + gson.toJson(imageDetectionResponse));
        } catch (Exception e) {
            System.out.println("Error during AI image detection: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

}
