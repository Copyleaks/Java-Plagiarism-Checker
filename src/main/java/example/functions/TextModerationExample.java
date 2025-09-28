
package example.functions;

import classes.Copyleaks;
import models.request.TextModeration.CopyleaksTextModerationRequest;
import models.request.TextModeration.CopyleaksTextModerationLabel;
import models.constants.CopyleaksTextModerationConstants;
import models.constants.CopyleaksTextModerationLanguages;
import models.response.CopyleaksAuthToken;
import models.response.textModeration.CopyleaksTextModerationResponseModel;
import com.google.gson.Gson;

/**
 * Example for submitting text to Copyleaks Text Moderation API.
 */
public class TextModerationExample {

    /**
     * Submits a sample text for moderation and prints the response.
     * @param token Copyleaks authentication token
     * @param scanId Unique scan identifier
     * @param gson Gson instance for JSON output
     */
    public static void run(CopyleaksAuthToken token, String scanId, Gson gson) {

        // Define the moderation labels to check for
        CopyleaksTextModerationLabel[] labelsArray = new CopyleaksTextModerationLabel[] {
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.ADULT_V1),
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.TOXIC_V1),
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.VIOLENT_V1),
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.PROFANITY_V1),
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.SELF_HARM_V1),
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.HARASSMENT_V1),
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.HATE_SPEECH_V1),
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.DRUGS_V1),
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.FIREARMS_V1),
            new CopyleaksTextModerationLabel(CopyleaksTextModerationConstants.CYBERSECURITY_V1)
        };

        // Create the moderation request
        CopyleaksTextModerationRequest request = new CopyleaksTextModerationRequest(
            "This is some text to moderate.",
            true, // Enable sandbox mode for testing
            CopyleaksTextModerationLanguages.ENGLISH,
            labelsArray
        );

        try {
            // Submit the text for moderation
            CopyleaksTextModerationResponseModel textModerationResponse = Copyleaks.textModerationClient.submitText(token, scanId, request);

            // Output the results
            System.out.println("\nText scanned for Text Moderation.");
            System.out.println("Text Moderation Response: " + gson.toJson(textModerationResponse));
        } catch (Exception e) {
            System.out.println("Error during text moderation: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

}
