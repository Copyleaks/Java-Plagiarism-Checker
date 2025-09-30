
package example.functions;

import classes.Copyleaks;
import models.response.CopyleaksAuthToken;
import models.submissions.aidetection.CopyleaksNaturalLanguageSubmissionModel;
import models.response.aidetection.AIDetectionResponse;

/**
 * Example for submitting a text to Copyleaks AI Detection API.
 */
public class AIDetectionExample {

    /**
     * Submits a sample text for AI detection and prints the AI score.
     * @param token Copyleaks authentication token
     * @param scanId Unique scan identifier
     */
    public static void run(CopyleaksAuthToken token, String scanId) {

        // Sample text to be analyzed for AI-generated content
        String sampleText = "Lions are social animals, living in groups called prides, typically consisting of several females, their offspring, and a few males. Female lions are the primary hunters, working together to catch prey. Lions are known for their strength, teamwork, and complex social structures.";

        // Create the submission model and enable sandbox mode
        CopyleaksNaturalLanguageSubmissionModel naturalLanguageSubmissionModel = new CopyleaksNaturalLanguageSubmissionModel(sampleText);
        naturalLanguageSubmissionModel.setSandbox(true);

        try {
            // Submit the text for AI detection
            AIDetectionResponse naturalLanguageAiDetectionResponse = Copyleaks.aiDetectionClient.submitNaturalLanguage(token, scanId, naturalLanguageSubmissionModel);

            // Output the results
            System.out.println("\nText scanned for AI detection.");
            System.out.println("AI Score: " + naturalLanguageAiDetectionResponse.getSummary().getAi());
        } catch (Exception e) {
            System.out.println("Error during AI detection: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

}
