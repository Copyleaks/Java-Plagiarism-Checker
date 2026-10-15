# Copyleaks SDK
The official [Copyleaks](https://copyleaks.com/) Java library, supporting Java versions:Java 11 or higher.

Copyleaks is the integrity layer for your tech stack that helps organizations and individuals understand what's human-made and original, AI-generated, or manipulated across text, image, audio and video.

This SDK gives you access to the following Copyleaks products:

*   AI Text Detection
*   AI Image Detection
*   AI Video Detection
*   Plagiarism Checker (Authenticity scans: file, URL, OCR)
*   Image Plagiarism Detection
*   Grammar Checker
*   Text Moderation

## 🚀 Getting Started
Before you start, ensure you have the following:

*   An active Copyleaks account. If you don’t have one, [Sign up for free](https://copyleaks.com/signup).
*   You can find your API key on the [API Dashboard](https://api.copyleaks.com/dashboard).

Once you have your account and API key:

**Install the SDK**: 

Add the dependency from [Maven Central Repository](https://search.maven.org/artifact/com.copyleaks.sdk/copyleaks-java-sdk):

```xml
<dependency>
    <groupId>com.copyleaks.sdk</groupId>
    <artifactId>copyleaks-java-sdk</artifactId>
    <version>6.0.0</version>
</dependency>
```

Gradle: `implementation 'com.copyleaks.sdk:copyleaks-java-sdk:6.0.0'`

The SDK depends only on Gson. Upgrading from 5.x? Read the upgrade notes in [CHANGELOG.md](CHANGELOG.md).

## 📚 Documentation
To learn more about how to use Copyleaks API please check out our [Documentation](https://docs.copyleaks.com/resources/sdks/java/). 

## 💡 Usage Examples
Here are some common usage examples for the Copyleaks SDK. Complete example programs, including a Spring Boot webhook receiver, are in the separate [examples](https://github.com/Copyleaks/Java-Plagiarism-Checker/tree/master/examples) project. It is not part of the published library.

### Get Authentication Token
This example demonstrates how to log in to the Copyleaks API and obtain an authentication token.

```java
import classes.Copyleaks;
import models.response.CopyleaksAuthToken;

public class ScanExample {
    // --- Your Credentials ---
    private static final String EMAIL_ADDRESS = "YOUR_EMAIL_ADDRESS";
    private static final String KEY = "YOUR_API_KEY";
    // --------------------

    public static void main(String[] args) {
        CopyleaksAuthToken token;

        // Log in to the Copyleaks API
        System.out.println("Authenticating...");
        token = Copyleaks.login(EMAIL_ADDRESS, KEY);
        System.out.println("✅ Logged in successfully!");
    }
}
```
For a detailed understanding of the authentication process, refer to the Copyleaks Login Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/account/login).
##
### Submit Text for Plagiarism Scan
This example shows how to prepare and submit raw text content for a plagiarism scan.

```java
import classes.Copyleaks;
import models.submissions.CopyleaksFileSubmissionModel;
import models.submissions.properties.SubmissionProperties;
import models.submissions.properties.SubmissionWebhooks;

public class ScanExample {
    // --- Your Credentials ---
    private static final String EMAIL_ADDRESS = "YOUR_EMAIL_ADDRESS";
    private static final String KEY = "YOUR_API_KEY";
    private static final String WEBHOOK_URL = "https://your-server.com/webhook/{STATUS}";
    // --------------------

    public static void main(String[] args) {
        String BASE64_FILE_CONTENT = Base64.getEncoder().encodeToString("Hello world".getBytes(StandardCharsets.UTF_8));
        String FILENAME = "hello.txt";
        String scanId = Integer.toString(getRandomNumberInRange(100, 100000));

        // Configure webhooks
        SubmissionWebhooks webhooks = new SubmissionWebhooks(WEBHOOK_URL);
        webhooks.setNewResult("https://your.server/webhook/new-results");

        // Create submission properties with enhanced configuration
        SubmissionProperties submissionProperties = new SubmissionProperties(webhooks);
        submissionProperties.setSandbox(true); // Turn on sandbox mode. Turn off on production.

        // Create the submission model
        CopyleaksFileSubmissionModel model = new CopyleaksFileSubmissionModel(BASE64_FILE_CONTENT, FILENAME, submissionProperties);
        Copyleaks.submitFile(token, scanId, model);
       
    }
}
```
For a full guide please refer to our step by step [Guide](https://docs.copyleaks.com/guides/authenticity/detect-plagiarism-text)

For a detailed understanding of the plagiarism detection process, refer to the Copyleaks Submit Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/scans/submit-file)
##
### Reading the AI alert from the completed webhook
When AI Text Detection is enabled for a scan (`aiGeneratedText.detect`), the completed webhook reports AI-generated text as a `suspected-ai-text` alert in `notifications.alerts`. The alert's `additionalData` is a JSON-encoded string, and the SDK decodes it into an `AIDetectionResponse`.

The handler below uses Spring Boot. The SDK does not depend on Spring, so add it to your own application if you use it.

```java
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import models.response.aidetection.AIDetectionResponse;
import models.response.aidetection.Result;
import models.submissions.Webhooks.CompletedWebhookModel;

@RestController
public class CompletedWebhookController {

    private static final Gson gson = new Gson();

    @PostMapping("/webhook/completed")
    public ResponseEntity<String> handleCompleted(@RequestBody String payload) {
        CompletedWebhookModel completed = gson.fromJson(payload, CompletedWebhookModel.class);
        try {
            // null when the scan produced no AI alert or the alert has no data;
            // throws JsonSyntaxException when additionalData is not valid JSON
            AIDetectionResponse aiResult = completed.getAIDetectionResult();
            if (aiResult != null && aiResult.getSummary() != null) {
                System.out.println("AI: " + aiResult.getSummary().getAi() + ", human: " + aiResult.getSummary().getHuman());
                if (aiResult.getResults() != null) {
                    for (Result section : aiResult.getResults()) {
                        // classification: 1 = human, 2 = AI
                        System.out.println("Classification: " + section.getClassification());
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Could not decode the AI alert: " + e.getMessage());
        }
        // Acknowledge the webhook even when the AI alert could not be decoded.
        return ResponseEntity.ok("Completed webhook received");
    }
}
```
`completed.getAIDetectionResult()` returns null when the completed webhook contains no suspected-ai-text alert.
- `completed.getAIDetectionAlert()` returns the alert itself (`AlertsModel`), and `AlertsModel.getAdditionalData()` still returns the raw string.
- Malformed `additionalData` throws Gson's `JsonSyntaxException`. Valid JSON that is not an object gives null.

For the alert fields, refer to the Copyleaks Scan Completed Webhook [Documentation](https://docs.copyleaks.com/reference/data-types/authenticity/webhooks/scan-completed)
##
### AI Text Detection
Use the AI detection client to determine if content was generated by artificial intelligence.

```java
import classes.Copyleaks;
import models.submissions.aidetection.CopyleaksNaturalLanguageSubmissionModel;
import models.response.aidetection.AIDetectionResponse;

public class ScanExample {
    // --- Your Credentials ---
    private static final String EMAIL_ADDRESS = "YOUR_EMAIL_ADDRESS";
    private static final String KEY = "YOUR_API_KEY";
    // --------------------

    public static void main(String[] args) {
        // This example is going to scan text for natural language AI detection.
        String sampleText = "Lions are social animals, living in groups called prides, typically consisting of several females, their offspring, and a few males. Female lions are the primary hunters, working together to catch prey. Lions are known for their strength, teamwork, and complex social structures.";
        
        CopyleaksNaturalLanguageSubmissionModel naturalLanguageSubmissionModel = new CopyleaksNaturalLanguageSubmissionModel(sampleText);
        naturalLanguageSubmissionModel.setSandbox(true);

        AIDetectionResponse naturalLanguageAiDetectionResponse = Copyleaks.aiDetectionClient.submitNaturalLanguage(token, scanId, naturalLanguageSubmissionModel);

        System.out.println("\nText scanned for AI detection.");
        System.out.println("AI Score: " + naturalLanguageAiDetectionResponse.getSummary().getAi());
    }
}
```
For a full guide please refer to our step by step [Guide](https://docs.copyleaks.com/guides/ai-detector/ai-text-detection/)

For a detailed understanding of the AI Text Detection process, refer to the Copyleaks detect natural language Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/writer-detector/check/)
##
### Grammar Checker (WritingAssistantClient)
Get intelligent suggestions for improving grammar, spelling, style, and overall writing quality.

```java
import classes.Copyleaks;
import models.submissions.writingassistant.ScoreWeights;
import models.submissions.writingassistant.CopyleaksWritingAssistantSubmissionModel;
import models.response.writingassitant.WritingAssistantResponse;

public class ScanExample {
    // --- Your Credentials ---
    private static final String EMAIL_ADDRESS = "YOUR_EMAIL_ADDRESS";
    private static final String KEY = "YOUR_API_KEY";
    // --------------------

    public static void main(String[] args) {
        // This example checks text for writing feedback.
        String writingFeedbackText = "Lions are the only cat that live in groups, called pride. A prides typically consists of a few adult males, several feales, and their offspring. This social structure is essential for hunting and raising young cubs. Female lions, or lionesses are the primary hunters of the prid. They work together in cordinated groups to take down prey usually targeting large herbiores like zbras, wildebeest and buffalo. Their teamwork and strategy during hunts highlight the intelligence and coperation that are key to their survival.";
        
        ScoreWeights scoreWeights = new ScoreWeights();
        scoreWeights.setGrammarScoreWeight(0.1);
        scoreWeights.setMechanicsScoreWeight(0.2);
        scoreWeights.setSentenceStructureScoreWeight(0.3);
        scoreWeights.setWordChoiceScoreWeight(0.4);
        
        CopyleaksWritingAssistantSubmissionModel writingAssistantSubmissionModel = new CopyleaksWritingAssistantSubmissionModel(writingFeedbackText);
        writingAssistantSubmissionModel.setScore(scoreWeights);
        writingAssistantSubmissionModel.setSandbox(true);


        WritingAssistantResponse writingAssistantResponse = Copyleaks.writingAssistantClient.submitText(token, scanId, writingAssistantSubmissionModel);

        System.out.println("\nText checked with Grammar Checker.");
        System.out.println("Grammar Score: " + writingAssistantResponse.getScore().getCorrections().getGrammarCorrectionsScore());
    }
}
```
For a full guide please refer to our step by step [Guide](https://docs.copyleaks.com/guides/writing/check-grammar/)

For a detailed understanding of the Grammar Checker (WritingAssistantClient) process, refer to the Copyleaks writing feedback Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/writing-assistant/check/)
##
### Text Moderation
Scan and moderate text content for unsafe, inappropriate, or policy-violating material across various categories.

```java
import classes.Copyleaks;
import models.request.TextModeration.CopyleaksTextModerationRequest;
import models.response.textModeration.CopyleaksTextModerationResponseModel;

public class ScanExample {
    // --- Your Credentials ---
    private static final String EMAIL_ADDRESS = "YOUR_EMAIL_ADDRESS";
    private static final String KEY = "YOUR_API_KEY";
    // --------------------

    public static void main(String[] args) {

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
                /* text */ "This is some text to moderate.",
                /* sandbox */ true,
                /* language */ CopyleaksTextModerationLanguages.ENGLISH,
                /* labels */ labelsArray
        );
        CopyleaksTextModerationResponseModel    textModerationResponse = Copyleaks.textModerationClient.submitText(token, scanId, request);

        System.out.println("\nText scanned for Text Moderation.");
        System.out.println("Text Moderation Response: " + gson.toJson(textModerationResponse));
    }
}
```
For a full guide please refer to our step by step [Guide](https://docs.copyleaks.com/guides/moderation/moderate-text/)

For a detailed understanding of the Text moderation process, refer to the Copyleaks text moderation Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/text-moderation/check/)
##

### AI Image Detection

Determine if a given image was generated or partially generated by an AI.

```java
import classes.Copyleaks;
import models.request.AiImageDetection.CopyleaksAiImageDetectionRequestModel;
import models.response.CopyleaksAuthToken;
import models.response.AiImageDetection.CopyleaksAiImageDetectionResponseModel;
import com.google.gson.Gson;
import static java.nio.file.Files.readAllBytes;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.IOException;
import models.constants.CopyleaksAiImageDetectionModels;

public class ScanExample {
    // --- Your Credentials ---
    private static final String EMAIL_ADDRESS = "YOUR_EMAIL_ADDRESS";
    private static final String KEY = "YOUR_API_KEY";
    // --------------------

    public static void main(String[] args) {

        // Path to the image file to be analyzed
        String imagePath = "PATH TO YOUR IMAGE";
        byte[] imageBytes;

        // Read the image file into a byte array
        try {
            imageBytes = readAllBytes(Paths.get(imagePath));
        } catch (IOException e) {
            System.out.println("Failed to read image file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Encode the image as Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Create the image detection request model
        CopyleaksAiImageDetectionRequestModel imageDetectionRequest = new CopyleaksAiImageDetectionRequestModel(
            base64Image,
            "my-image.png",
            CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA,
            true
        );
        
        try {
            // Submit the image for AI detection
            CopyleaksAiImageDetectionResponseModel imageDetectionResponse = Copyleaks.aiImageDetectionClient.submit(token, scanId, imageDetectionRequest);

            // Output the results
            System.out.println("\nImage scanned for AI Image Detection.");
            System.out.println("Image Detection Response: " + gson.toJson(imageDetectionResponse));
        } catch (Exception e) {
            System.out.println("Error during AI image detection: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }
}

```

For a full guide please refer to our step by step [Guide](https://docs.copyleaks.com/guides/ai-detector/ai-image-detection/)

For a detailed understanding of the AI Image Detection process, refer to the Copyleaks image detection Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/ai-image-detector/check/)
##
### AI Image Detection with a file upload
`submitFile` uploads the image bytes as `multipart/form-data`, without base64 encoding. It is faster for large images. Both `submit` and `submitFile` accept the `maskType` and `objectDetection` options.

```java
import classes.Copyleaks;
import models.constants.CopyleaksAiImageDetectionModels;
import models.constants.CopyleaksAiImageMaskTypes;
import models.request.AiImageDetection.CopyleaksAiImageDetectionFileRequestModel;
import models.request.AiImageDetection.CopyleaksObjectDetectionItem;
import models.response.AiImageDetection.CopyleaksAiImageDetectionResponseModel;

byte[] image = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("my-image.png"));
CopyleaksAiImageDetectionFileRequestModel request = new CopyleaksAiImageDetectionFileRequestModel(
        image, "my-image.png", CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA, true);
request.setMaskType(CopyleaksAiImageMaskTypes.HEATMAP);  // adds result.labels
request.setObjectDetection(new CopyleaksObjectDetectionItem[] { new CopyleaksObjectDetectionItem("face") });

CopyleaksAiImageDetectionResponseModel result = Copyleaks.aiImageDetectionClient.submitFile(token, scanId, request);
System.out.println("AI detected: " + result.getIsAiDetected());
```
Refer to the AI Image Detection Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/ai-image-detector/check)
##
### AI Image Detection for an image URL (async)
The call returns once the image is accepted. The result is sent to your webhook and has the same shape as the synchronous result, so you can parse it into `CopyleaksAiImageDetectionResponseModel`.

```java
import models.request.CopyleaksAsyncWebhooksModel;
import models.request.AiImageDetection.CopyleaksAiImageDetectionAsyncRequestModel;
import models.response.AiImageDetection.CopyleaksAiImageDetectionResponseModel;

CopyleaksAiImageDetectionAsyncRequestModel request = new CopyleaksAiImageDetectionAsyncRequestModel(
        "https://example.com/image1.png", "image1.png", CopyleaksAiImageDetectionModels.AI_IMAGE_1_ULTRA,
        new CopyleaksAsyncWebhooksModel("https://your-server.com/webhook/ai-image"));
request.setSandbox(true);
Copyleaks.aiImageDetectionClient.submitAsync(token, scanId, request);

// In your webhook handler:
CopyleaksAiImageDetectionResponseModel result = new Gson().fromJson(body, CopyleaksAiImageDetectionResponseModel.class);
if (result.getError() != null) {
    System.out.println("Scan failed: " + result.getError().getCode() + " " + result.getError().getMessage());
}
```
When the scan fails, the webhook body has an `error` (with `code`, `message`, and optionally `type`, `id`, `url` and `details`) instead of a result, so check `getError()` first. `getDeveloperPayload()` returns the `developerPayload` you sent.

Refer to the AI Image Detector Async Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/ai-image-detector/submit)
##
### AI Video Detection
Submit a video URL. The result is sent to your webhook; parse it into `CopyleaksAiVideoDetectionResponseModel`.

```java
import models.constants.CopyleaksAiVideoDetectionModels;
import models.request.CopyleaksAsyncWebhooksModel;
import models.request.AiVideoDetection.CopyleaksAiVideoDetectionRequestModel;
import models.response.AiVideoDetection.CopyleaksAiVideoDetectionResponseModel;

CopyleaksAiVideoDetectionRequestModel request = new CopyleaksAiVideoDetectionRequestModel(
        "https://example.com/my-video.mp4", "my-video.mp4", CopyleaksAiVideoDetectionModels.AI_VIDEO_1_PRO,
        new CopyleaksAsyncWebhooksModel("https://your-server.com/webhook/ai-video"));
request.setSandbox(true); // free, returns mock results
Copyleaks.aiVideoDetectionClient.submit(token, scanId, request);

// In your webhook handler:
CopyleaksAiVideoDetectionResponseModel result = new Gson().fromJson(body, CopyleaksAiVideoDetectionResponseModel.class);
if (result.getError() != null) {
    System.out.println("Scan failed: " + result.getError().getCode() + " " + result.getError().getMessage());
} else {
    System.out.println("Overall AI ratio: " + result.getSummary().getOverallAIRatio());
}
```
When the scan fails, the webhook body has an `error` instead of a result, so check `getError()` first. `getDeveloperPayload()` returns the `developerPayload` you sent.

Refer to the AI Video Detection Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/ai-video-detector/submit)
##
### Image Plagiarism Detection
Search the web for copies of an image. The image is uploaded as `multipart/form-data` and the matches are returned right away.

```java
import models.request.ImagePlagiarism.CopyleaksImagePlagiarismRequestModel;
import models.response.ImagePlagiarism.CopyleaksImagePlagiarismInternetMatchModel;
import models.response.ImagePlagiarism.CopyleaksImagePlagiarismResponseModel;

byte[] image = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("my-photo.jpg"));
CopyleaksImagePlagiarismRequestModel request = new CopyleaksImagePlagiarismRequestModel(image, "my-photo.jpg", true);
CopyleaksImagePlagiarismResponseModel result = Copyleaks.imagePlagiarismClient.check(token, scanId, request);

System.out.println("Total matches: " + result.getMatches().getScore().getTotalMatches());
for (CopyleaksImagePlagiarismInternetMatchModel match : result.getMatches().getInternet()) {
    System.out.println(match.getMatchType() == CopyleaksImagePlagiarismInternetMatchModel.MATCH_TYPE_FULL
            ? "Full match: " + match.getUrl() : "Partial match: " + match.getUrl());
}
```
Refer to the Image Plagiarism Detection Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/image-plagiarism-detector/check)
##
### AI Text Detection options and credits
```java
CopyleaksNaturalLanguageSubmissionModel model = new CopyleaksNaturalLanguageSubmissionModel(text, true, "en");
model.setExplain(true);    // AI Logic: the result includes getExplain()
model.setSensitivity(3);   // 1 to 3, default 2
AIDetectionResponse result = Copyleaks.aiDetectionClient.submitNaturalLanguage(token, scanId, model);

int credits = Copyleaks.aiDetectionClient.getCredits(token).getCredits();
```
Refer to the AI Text Detection Credits Endpoint [Documentation](https://docs.copyleaks.com/reference/actions/writer-detector/credits)
##
### Lookups
```java
// Public, no login needed
CrossLanguagesResponse languages = Copyleaks.getAllowedCrossLanguages();
String[] sources = languages.getDocumentLanguageCodes();      // for example "en", "es"
Map<String, String> targets = languages.getResultLanguages(); // language name to code, for example "albanian" -> "sq"
String[] aiFileTypes = Copyleaks.getSupportedAiTextDetectionFileTypes().getSupportedAiFileTypes();

// Private Cloud Hub repository (Admin or Super Admin role)
RepositoryInfoResponse repository = Copyleaks.getRepositoryInfo(token, "my-repository-id");
System.out.println(repository.getName() + ": " + repository.getCurrentCredits() + "/" + repository.getMaxCredits());
```
Refer to the [Cross-Language](https://docs.copyleaks.com/reference/actions/miscellaneous/supported-cross-languages), [AI Text Detection File Types](https://docs.copyleaks.com/reference/actions/miscellaneous/supported-ai-text-detection-file-types) and [Repository Information](https://docs.copyleaks.com/reference/actions/private-cloud-hub/info) documentation.
##
### More scan options
```java
SubmissionProperties properties = new SubmissionProperties(new SubmissionWebhooks(WEBHOOK_URL));
properties.setScanTimeZone("America/New_York");
properties.setReferences(new SubmissionReferences(true));                      // Citation Checker
properties.setInternalAiSourceMatch(new SubmissionInternalAiSourceMatch(true));
properties.setCustomMetadata(new SubmissionCustomMetadata[] { new SubmissionCustomMetadata("course", "bio-101") });
properties.setAction(SubmissionActions.IndexOnly);                            // sent as 2

// Delete scans and get notified when the removal is done
CopyleaksDeleteRequest delete = new CopyleaksDeleteRequest(
        new ScansRequests[] { new ScansRequests(scanId) }, false, "https://your-server.com/webhook/deleted");
Copyleaks.delete(token, delete);

// Export the AI Text Detection and overview reports; results and the crawled version are optional
CopyleaksExportModel export = new CopyleaksExportModel("https://your-server.com/webhook/export-done");
export.setAiDetection(new ExportAiDetection("https://your-server.com/export/ai", "POST", null));
export.setOverview(new ExportOverview("https://your-server.com/export/overview", "POST", null));
Copyleaks.export(token, scanId, exportId, export);
```
The completed webhook model also exposes `getReferencesValidation()` and `getResults().getInternalAIData()`.
##
### HTTP settings
All calls share one HTTP client. Every request is bounded by a timeout, so a call cannot hang forever.

```java
Copyleaks.setConnectTimeout(Duration.ofSeconds(10));   // default 30 seconds
Copyleaks.setRequestTimeout(Duration.ofMinutes(5));    // default 120 seconds per attempt; retries can make a call take longer
Copyleaks.setMaxConcurrentRequests(50);                 // default 20
Copyleaks.setMaxRetries(3);                             // default 0; retries 429 and 503, and GET on 502, 504 and network errors
Copyleaks.setHttpClient(myHttpClient);                  // optional: your own java.net.http.HttpClient (proxy, executor)
```
A timeout throws `ExecutionException` caused by `java.net.http.HttpTimeoutException`.
##
### Error handling
`RateLimitException` (429), `UnderMaintenanceException` (503) and `CommandException` (any other error status) carry the HTTP status and the response body. All three extend `CopyleaksApiException`.

```java
try {
    Copyleaks.submitFile(token, scanId, model);
} catch (RateLimitException e) {
    Duration wait = e.getRetryAfter();  // from the Retry-After header, or null
} catch (CommandException e) {
    System.out.println(e.getStatusCode() + ": " + e.getResponseBody());
}
```
##
### Further Resources
*   **Copyleaks API Dashboard:** Manage your API keys, monitor usage, and view analytics from your personalized dashboard. [Access Dashboard](https://api.copyleaks.com/dashboard)
*   **Copyleaks SDK Documentation:** Explore comprehensive guides, API references, and code examples for seamless integration. [Read Documentation](https://docs.copyleaks.com/resources/sdks/overview/)

##
### Support
* If you need assistance, please contact Copyleaks Support via our support portal: Contact Copyleaks [Support](https://help.copyleaks.com/s/contactsupport).
* To arrange a product demonstration, book a demo here: [Booking Link](https://copyleaks.com/book-a-demo).
