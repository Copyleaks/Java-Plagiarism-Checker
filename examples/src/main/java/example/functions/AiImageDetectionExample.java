
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
/** Submits an image to AI Image Detection and prints the result. */
public class AiImageDetectionExample {

    public static void run(CopyleaksAuthToken token, String scanId, Gson gson) {

        String imagePath = "PATH TO YOUR IMAGE";
        byte[] imageBytes;

        try {
            imageBytes = readAllBytes(Paths.get(imagePath));
        } catch (IOException e) {
            System.out.println("Failed to read image file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        CopyleaksAiImageDetectionRequestModel imageDetectionRequest = new CopyleaksAiImageDetectionRequestModel(
            base64Image,
            "my-image.png",
            CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA,
            true
        );

        try {
            CopyleaksAiImageDetectionResponseModel imageDetectionResponse = Copyleaks.aiImageDetectionClient.submit(token, scanId, imageDetectionRequest);

            System.out.println("\nImage scanned for AI Image Detection.");
            System.out.println("Image Detection Response: " + gson.toJson(imageDetectionResponse));
        } catch (Exception e) {
            System.out.println("Error during AI image detection: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

}
