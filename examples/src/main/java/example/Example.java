package example;

import models.response.CopyleaksAuthToken;
import example.functions.AIDetectionExample;
import example.functions.AiImageDetectionExample;
import example.functions.AiVideoDetectionExample;
import example.functions.ImagePlagiarismExample;
import example.functions.AuthenticationExample;
import example.functions.PlagiarismScanExample;
import example.functions.TextModerationExample;
import example.functions.WritingAssistantExample;
import java.util.Random;
import com.google.gson.Gson;

/** Runs every product example. Set your account email and API key first. */
public class Example {

    // Get your API key from the dashboard at https://api.copyleaks.com.
    private static final String EMAIL_ADDRESS = "YOUR@EMAIL.HERE";
    private static final String KEY = "00000000-0000-0000-0000-000000000000";
    private static final Gson gson = new Gson();

    public static void main(String[] args) {

        // Receives the webhooks of the asynchronous examples.
        new Thread(() -> WebhookApplication.main(new String[] {})).start();
        System.out.println("Press Ctrl+C to shutdown the server");

        CopyleaksAuthToken token = AuthenticationExample.authenticate(EMAIL_ADDRESS, KEY);
        if (token == null) {
            System.out.println("Authentication failed. Exiting.");
            return;
        }

        String scanId = Integer.toString(getRandomNumberInRange(100, 100000));

        PlagiarismScanExample.run(token, scanId);
        AIDetectionExample.run(token, scanId);
        WritingAssistantExample.run(token, scanId);
        TextModerationExample.run(token, scanId, gson);
        AiImageDetectionExample.run(token, scanId, gson);
        ImagePlagiarismExample.run(token, scanId);
        // The AI video result arrives at the webhook.
        AiVideoDetectionExample.run(token, scanId);
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }

}