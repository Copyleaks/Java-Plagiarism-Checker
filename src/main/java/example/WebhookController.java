package example;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import models.submissions.Webhooks.CompletedWebhookModel;
import models.submissions.Webhooks.CreditsCheckedWebhookModel;
import models.submissions.Webhooks.ErrorWebhookModel;
import models.submissions.Webhooks.IndexedWebhookModel;
import models.submissions.Webhooks.NewResultWebhookModel;

/**
 * Controller to handle webhook callbacks from the external service.
 * 
 * Provides separate endpoints for each webhook event status:
 * - /webhook/completed
 * - /webhook/error
 * - /webhook/indexed
 * - /webhook/creditsChecked
 * 
 * Each endpoint accepts a POST request with a JSON payload corresponding to the
 * event type.
 */
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Gson gson = new Gson();

    /**
     * Handles the "completed" webhook event.
     * 
     * @param payload the JSON payload sent by the webhook, representing a
     *                CompletedWebhook object
     * @return a simple acknowledgment message
     */
    @PostMapping("/completed")
    public String handleCompleted(@RequestBody String payload) {
        CompletedWebhookModel completedData = gson.fromJson(payload, CompletedWebhookModel.class);
        System.out
                .println("Scan completed with creation time: " + completedData.getScannedDocument().getCreationTime());
        return "Completed webhook received";
    }

    /**
     * Handles the "error" webhook event.
     * 
     * @param payload the JSON payload sent by the webhook, representing an
     *                ErrorWebhook object
     * @return a simple acknowledgment message
     */
    @PostMapping("/error")
    public String handleError(@RequestBody String payload) {
        ErrorWebhookModel errorData = gson.fromJson(payload, ErrorWebhookModel.class);
        System.out.println("Error event received: " + payload);
        return "Error webhook received";
    }

    /**
     * Handles the "indexed" webhook event.
     * 
     * @param payload the JSON payload sent by the webhook, representing an
     *                IndexedWebhook object
     * @return a simple acknowledgment message
     */
    @PostMapping("/indexed")
    public String handleIndexed(@RequestBody String payload) {
        IndexedWebhookModel indexedData = gson.fromJson(payload, IndexedWebhookModel.class);
        System.out.println("Indexed event received: " + payload);
        return "Indexed webhook received";
    }

    /**
     * Handles the "creditsChecked" webhook event.
     * 
     * @param payload the JSON payload sent by the webhook, representing a
     *                CreditsCheckedWebhook object
     * @return a simple acknowledgment message
     */
    @PostMapping("/creditsChecked")
    public String handleCreditsChecked(@RequestBody String payload) {
        CreditsCheckedWebhookModel creditsCheckedData = gson.fromJson(payload, CreditsCheckedWebhookModel.class);
        System.out.println("Credits checked event received: " + payload);
        return "CreditsChecked webhook received";
    }

    /**
     * Handles the "new-results" webhook event.
     * 
     * @param payload the JSON payload sent by the webhook, representing a
     *                NewResultWebhookModel object
     * @return a simple acknowledgment message
     */
    @PostMapping("/new-results")
    public String handleNewResults(@RequestBody String payload) {
        NewResultWebhookModel newResultsData = gson.fromJson(payload, NewResultWebhookModel.class);
        System.out.println("new-results event received: " + payload);
        return "new-results webhook received";
    }
}