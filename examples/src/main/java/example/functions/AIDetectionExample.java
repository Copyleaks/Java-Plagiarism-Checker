
package example.functions;

import classes.Copyleaks;
import models.response.CopyleaksAuthToken;
import models.submissions.aidetection.CopyleaksNaturalLanguageSubmissionModel;
import models.response.aidetection.AIDetectionResponse;

/** Submits a text to AI Text Detection and prints the AI score. */
public class AIDetectionExample {

    public static void run(CopyleaksAuthToken token, String scanId) {

        String sampleText = "Lions are social animals, living in groups called prides, typically consisting of several females, their offspring, and a few males. Female lions are the primary hunters, working together to catch prey. Lions are known for their strength, teamwork, and complex social structures.";

        CopyleaksNaturalLanguageSubmissionModel naturalLanguageSubmissionModel = new CopyleaksNaturalLanguageSubmissionModel(sampleText);
        naturalLanguageSubmissionModel.setSandbox(true);

        try {
            AIDetectionResponse naturalLanguageAiDetectionResponse = Copyleaks.aiDetectionClient.submitNaturalLanguage(token, scanId, naturalLanguageSubmissionModel);

            System.out.println("\nText scanned for AI detection.");
            System.out.println("AI Score: " + naturalLanguageAiDetectionResponse.getSummary().getAi());
        } catch (Exception e) {
            System.out.println("Error during AI detection: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

}
