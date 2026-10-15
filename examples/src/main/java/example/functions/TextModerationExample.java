
package example.functions;

import classes.Copyleaks;
import models.request.TextModeration.CopyleaksTextModerationRequest;
import models.request.TextModeration.CopyleaksTextModerationLabel;
import models.constants.CopyleaksTextModerationConstants;
import models.constants.CopyleaksTextModerationLanguages;
import models.response.CopyleaksAuthToken;
import models.response.textModeration.CopyleaksTextModerationResponseModel;
import com.google.gson.Gson;

/** Submits a text to Text Moderation and prints the result. */
public class TextModerationExample {

    public static void run(CopyleaksAuthToken token, String scanId, Gson gson) {

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

        CopyleaksTextModerationRequest request = new CopyleaksTextModerationRequest(
            "This is some text to moderate.",
            true, // sandbox: mock results without using credits
            CopyleaksTextModerationLanguages.ENGLISH,
            labelsArray
        );

        try {
            CopyleaksTextModerationResponseModel textModerationResponse = Copyleaks.textModerationClient.submitText(token, scanId, request);

            System.out.println("\nText scanned for Text Moderation.");
            System.out.println("Text Moderation Response: " + gson.toJson(textModerationResponse));
        } catch (Exception e) {
            System.out.println("Error during text moderation: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

}
