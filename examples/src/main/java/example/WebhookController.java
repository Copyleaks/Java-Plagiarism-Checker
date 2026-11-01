package example;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import models.response.AiImageDetection.CopyleaksAiImageDetectionResponseModel;
import models.response.AiVideoDetection.CopyleaksAiVideoDetectionResponseModel;
import models.response.aidetection.AIDetectionResponse;
import models.submissions.Webhooks.CompletedWebhookModel;
import models.submissions.Webhooks.CreditsCheckedWebhookModel;
import models.submissions.Webhooks.ErrorWebhookModel;
import models.submissions.Webhooks.IndexedWebhookModel;
import models.submissions.Webhooks.NewResultWebhookModel;

/**
 * Receives the Copyleaks webhooks, one POST endpoint per event: /webhook/completed, /error, /indexed,
 * /creditschecked, /new-results, /ai-image (AI Image Detection async) and /ai-video.
 */
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Gson gson = new Gson();

    @PostMapping("/completed")
    public String handleCompleted(@RequestBody String payload) {
        CompletedWebhookModel completedData = gson.fromJson(payload, CompletedWebhookModel.class);
        System.out
                .println("Scan completed with creation time: " + completedData.getScannedDocument().getCreationTime());

        // Null when the scan raised no AI alert. Catch the parse error so the webhook is still acknowledged.
        try {
            AIDetectionResponse aiDetectionResult = completedData.getAIDetectionResult();
            if (aiDetectionResult != null && aiDetectionResult.getSummary() != null) {
                System.out.println("AI text detected. AI score: " + aiDetectionResult.getSummary().getAi());
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Could not decode the AI alert data: " + e.getMessage());
        }
        return "Completed webhook received";
    }

    @PostMapping("/error")
    public String handleError(@RequestBody String payload) {
        ErrorWebhookModel errorData = gson.fromJson(payload, ErrorWebhookModel.class);
        System.out.println("Error event received: " + payload);
        return "Error webhook received";
    }

    @PostMapping("/indexed")
    public String handleIndexed(@RequestBody String payload) {
        IndexedWebhookModel indexedData = gson.fromJson(payload, IndexedWebhookModel.class);
        System.out.println("Indexed event received: " + payload);
        return "Indexed webhook received";
    }

    @PostMapping("/creditschecked")
    public String handleCreditsChecked(@RequestBody String payload) {
        CreditsCheckedWebhookModel creditsCheckedData = gson.fromJson(payload, CreditsCheckedWebhookModel.class);
        System.out.println("Credits checked event received: " + payload);
        return "CreditsChecked webhook received";
    }

    @PostMapping("/new-results")
    public String handleNewResults(@RequestBody String payload) {
        NewResultWebhookModel newResultsData = gson.fromJson(payload, NewResultWebhookModel.class);
        System.out.println("new-results event received: " + payload);
        return "new-results webhook received";
    }

    /** The async result has the same shape as the synchronous one. */
    @PostMapping("/ai-image")
    public String handleAiImage(@RequestBody String payload) {
        CopyleaksAiImageDetectionResponseModel result = gson.fromJson(payload, CopyleaksAiImageDetectionResponseModel.class);
        System.out.println("AI image result. AI detected: " + result.getIsAiDetected());
        return "AI image webhook received";
    }

    @PostMapping("/ai-video")
    public String handleAiVideo(@RequestBody String payload) {
        CopyleaksAiVideoDetectionResponseModel result = gson.fromJson(payload, CopyleaksAiVideoDetectionResponseModel.class);
        if (result.getSummary() != null) {
            System.out.println("AI video result. Overall AI ratio: " + result.getSummary().getOverallAIRatio());
        }
        return "AI video webhook received";
    }
}
