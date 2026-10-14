
package example.functions;

import classes.Copyleaks;
import models.constants.CopyleaksAiVideoDetectionModels;
import models.request.CopyleaksAsyncWebhooksModel;
import models.request.AiVideoDetection.CopyleaksAiVideoDetectionRequestModel;
import models.response.CopyleaksAuthToken;

/**
 * Example for AI Video Detection. The result is sent to the webhook, see WebhookController.handleAiVideo.
 */
public class AiVideoDetectionExample {

    /**
     * Submits a video URL for AI Video Detection.
     * @param token Copyleaks authentication token
     * @param scanId Unique scan identifier
     */
    public static void run(CopyleaksAuthToken token, String scanId) {
        CopyleaksAiVideoDetectionRequestModel request = new CopyleaksAiVideoDetectionRequestModel(
                "https://example.com/my-video.mp4",
                "my-video.mp4",
                CopyleaksAiVideoDetectionModels.AI_VIDEO_1_PRO,
                new CopyleaksAsyncWebhooksModel("https://your.server/webhook/ai-video"));
        request.setSandbox(true); // Free, returns mock results. Turn it off in production.

        try {
            Copyleaks.aiVideoDetectionClient.submit(token, scanId, request);
            System.out.println("Video submitted. The result will be sent to the webhook.");
        } catch (Exception e) {
            System.out.println("Error during AI video detection: " + e.getMessage());
        }
    }
}
