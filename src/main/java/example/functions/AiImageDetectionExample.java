
package example.functions;

import classes.Copyleaks;
import models.request.AiImageDetection.CopyleaksAiImageDetectionRequestModel;
import models.response.CopyleaksAuthToken;
import models.response.AiImageDetection.CopyleaksAiImageDetectionResponseModel;
import com.google.gson.Gson;

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
            imageBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(imagePath));
        } catch (java.io.IOException e) {
            System.out.println("Failed to read image file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Encode the image as Base64
        String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

        // Create the image detection request model
        CopyleaksAiImageDetectionRequestModel imageDetectionRequest = new CopyleaksAiImageDetectionRequestModel(
            base64Image,
            "image-name",
            "ai-image-1-ultra-01-09-2025",
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
