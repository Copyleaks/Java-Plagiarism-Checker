
package example.functions;

import classes.Copyleaks;
import models.response.CopyleaksAuthToken;
import models.submissions.CopyleaksFileSubmissionModel;
import models.submissions.properties.SubmissionProperties;
import models.submissions.properties.SubmissionWebhooks;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/** Submits a file for a plagiarism scan. The results arrive at the webhooks. */
public class PlagiarismScanExample {

    public static void run(CopyleaksAuthToken token, String scanId) {
        try {
            String BASE64_FILE_CONTENT = Base64.getEncoder().encodeToString("Hello world".getBytes(StandardCharsets.UTF_8));
            String FILENAME = "hello.txt";

            SubmissionWebhooks webhooks = new SubmissionWebhooks("https://your.server/webhook/{STATUS}");
            webhooks.setNewResult("https://your.server/webhook/new-results");

            SubmissionProperties submissionProperties = new SubmissionProperties(webhooks);
            submissionProperties.setSandbox(true); // Mock results without using credits. Turn it off in production.

            CopyleaksFileSubmissionModel model = new CopyleaksFileSubmissionModel(BASE64_FILE_CONTENT, FILENAME, submissionProperties);

            Copyleaks.submitFile(token, scanId, model);
            System.out.println("Plagiarism scan submitted successfully.");
        } catch (Exception e) {
            System.out.println("Error during plagiarism scan submission: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
