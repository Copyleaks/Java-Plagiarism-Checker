
package example.functions;

import classes.Copyleaks;
import models.response.CopyleaksAuthToken;
import models.submissions.CopyleaksFileSubmissionModel;
import models.submissions.properties.SubmissionProperties;
import models.submissions.properties.SubmissionWebhooks;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Example for submitting a file to Copyleaks Plagiarism Scan API.
 */
public class PlagiarismScanExample {

    /**
     * Submits a sample file for plagiarism scanning and prints the result.
     * @param token Copyleaks authentication token
     * @param scanId Unique scan identifier
     */
    public static void run(CopyleaksAuthToken token, String scanId) {
        try {
            // Prepare the file content as Base64
            String BASE64_FILE_CONTENT = Base64.getEncoder().encodeToString("Hello world".getBytes(StandardCharsets.UTF_8));
            String FILENAME = "hello.txt";

            // Configure webhooks for scan status and new results
            SubmissionWebhooks webhooks = new SubmissionWebhooks("https://your.server/webhook/{STATUS}");
            webhooks.setNewResult("https://your.server/webhook/new-results");

            // Create submission properties and enable sandbox mode for testing
            SubmissionProperties submissionProperties = new SubmissionProperties(webhooks);
            submissionProperties.setSandbox(true); // Turn on sandbox mode. Turn off on production.

            // Create the file submission model
            CopyleaksFileSubmissionModel model = new CopyleaksFileSubmissionModel(BASE64_FILE_CONTENT, FILENAME, submissionProperties);

            // Submit the file for plagiarism scanning
            Copyleaks.submitFile(token, scanId, model);
            System.out.println("Plagiarism scan submitted successfully.");
        } catch (Exception e) {
            System.out.println("Error during plagiarism scan submission: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
