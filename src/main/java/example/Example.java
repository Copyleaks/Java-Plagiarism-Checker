/*
 The MIT License(MIT)
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/


package example;

import classes.Copyleaks;
import models.exceptions.AuthExpiredException;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.request.TextModeration.CopyleaksTextModerationRequest;
import models.request.TextModeration.Label;
import models.request.TextModeration.Label;
import models.response.CopyleaksAuthToken;
import models.response.aidetection.AIDetectionResponse;
import models.response.textModeration.CopyleaksTextModerationResponseModel;
import models.response.writingassitant.WritingAssistantResponse;
import models.submissions.CopyleaksFileSubmissionModel;
import models.submissions.aidetection.CopyleaksNaturalLanguageSubmissionModel;
import models.submissions.aidetection.CopyleaksSourceCodeSubmissionModel;
import models.submissions.properties.SubmissionProperties;
import models.submissions.properties.SubmissionWebhooks;
import models.submissions.writingassistant.CopyleaksWritingAssistantSubmissionModel;
import models.submissions.writingassistant.ScoreWeights;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;

public class Example {

    // Register on https://api.copyleaks.com and grab your secret key (from the dashboard page).
    private static final String EMAIL_ADDRESS = "YOUR@EMAIL.HERE";
    private static final String KEY = "00000000-0000-0000-0000-000000000000";
    private static Gson gson=new Gson();
    public static void main(String[] args) {
        CopyleaksAuthToken token;
        try {
            token = Copyleaks.login(EMAIL_ADDRESS, KEY);
        } catch (ExecutionException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (UnderMaintenanceException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (RateLimitException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (CommandException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }

        System.out.println("Logged successfully!\nToken:");
        System.out.print(token);

        // This example is going to scan a FILE for plagiarism.
        // Alternatively, you can scan a URL using the class `UrlDocument`.
        System.out.println("Submitting a new file...");
        String BASE64_FILE_CONTENT = Base64.getEncoder().encodeToString("Hello world".getBytes(StandardCharsets.UTF_8));
        String FILENAME = "hello.txt";
        String scanId = Integer.toString(getRandomNumberInRange(100, 100000));
        SubmissionProperties submissionProperties = new SubmissionProperties(new SubmissionWebhooks("https://your.server/webhook?event={{STATUS}}"));
        submissionProperties.setSandbox(true); //Turn on sandbox mode. Turn off on production.
        CopyleaksFileSubmissionModel model = new CopyleaksFileSubmissionModel(BASE64_FILE_CONTENT, FILENAME, submissionProperties);

        try {
            Copyleaks.submitFile(token, scanId, model);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (AuthExpiredException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (UnderMaintenanceException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (CommandException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (ExecutionException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }
        System.out.println("Send to scanning");
        System.out.printf("You will be notified, using your webhook, once the scan was completed.");

        // This example is going to scan text for natural language AI detection.
        String sampleText = "Lions are social animals, living in groups called prides, typically consisting of several females, their offspring, and a few males. Female lions are the primary hunters, working together to catch prey. Lions are known for their strength, teamwork, and complex social structures.";
        CopyleaksNaturalLanguageSubmissionModel naturalLanguageSubmissionModel = new CopyleaksNaturalLanguageSubmissionModel(sampleText);
        naturalLanguageSubmissionModel.setSandbox(true);
        AIDetectionResponse naturalLanguageAiDetectionResponse;
        try {
            naturalLanguageAiDetectionResponse = Copyleaks.aiDetectionClient.submitNaturalLanguage(token, scanId, naturalLanguageSubmissionModel);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (AuthExpiredException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (UnderMaintenanceException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (CommandException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (ExecutionException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }
        System.out.println("\nText scanned for AI detection.");
        System.out.println("AI Score: " + naturalLanguageAiDetectionResponse.getSummary().getAi());


        // This example is going to scan source code for AI detection.
        String sampleCode = "def add(a, b):\n" +
        "    return a + b\n" +
        "\n" +
        "def multiply(a, b):\n" +
        "    return a * b\n" +
        "\n" +
        "def main():\n" +
        "    x = 5\n" +
        "    y = 10\n" +
        "    sum_result = add(x, y)\n" +
        "    product_result = multiply(x, y)\n" +
        "    print(f'Sum: {sum_result}')\n" +
        "    print(f'Product: {product_result}')\n" +
        "\n" +
        "if __name__ == '__main__':\n" +
        "    main()";

        CopyleaksSourceCodeSubmissionModel sourceCodeSubmissionModel = new CopyleaksSourceCodeSubmissionModel(sampleCode, "sampleFile.py");
        sourceCodeSubmissionModel.setSandbox(true);
        AIDetectionResponse sourceCodeAiDetectionResponse;
        try {
            sourceCodeAiDetectionResponse = Copyleaks.aiDetectionClient.submitSourceCode(token, scanId, sourceCodeSubmissionModel);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (AuthExpiredException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (UnderMaintenanceException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (CommandException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (ExecutionException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }
        System.out.println("\nText scanned for AI detection.");
        System.out.println("AI Score: " + sourceCodeAiDetectionResponse.getSummary().getAi());


        // This example is going to text for writing feedback.
        String writingFeedbackText = "Lions are the only cat that live in groups, called pride. A prides typically consists of a few adult males, several feales, and their offspring. This social structure is essential for hunting and raising young cubs. Female lions, or lionesses are the primary hunters of the prid. They work together in cordinated groups to take down prey usually targeting large herbiores like zbras, wildebeest and buffalo. Their teamwork and strategy during hunts highlight the intelligence and coperation that are key to their survival.";
        ScoreWeights scoreWeights = new ScoreWeights();
        scoreWeights.setGrammarScoreWeight(0.1);
        scoreWeights.setMechanicsScoreWeight(0.2);
        scoreWeights.setSentenceStructureScoreWeight(0.3);
        scoreWeights.setWordChoiceScoreWeight(0.4);
        CopyleaksWritingAssistantSubmissionModel writingAssistantSubmissionModel = new CopyleaksWritingAssistantSubmissionModel(writingFeedbackText);
        writingAssistantSubmissionModel.setScore(scoreWeights);
        writingAssistantSubmissionModel.setSandbox(true);

        WritingAssistantResponse writingAssistantResponse;
        try {
            writingAssistantResponse = Copyleaks.writingAssistantClient.submitText(token, scanId, writingAssistantSubmissionModel);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (AuthExpiredException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (UnderMaintenanceException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (CommandException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (ExecutionException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }
        System.out.println("\nText scanned for AI detection.");
        System.out.println("Grammer Score: " + writingAssistantResponse.getScore().getCorrections().getGrammarCorrectionsScore());

        // Wait for completion webhook arrival...
        // Read more: https://api.copyleaks.com/documentation/v3/webhooks
        // Uncomment the following code to create an export task:
        // Once the webhooks arrived and the scan was completed successfully (see the `status` flag) you can
        // proceed to exporting all the artifacts related to your scan.

        // String[][] headers = new String[][]{
        //         new String[]{"key", "value"}, new String[]{"key2", "value2"}
        // };
        //
        // ExportResults results = new ExportResults(
        //         "2a1b402420",
        //         "https://your.server/webhook/export/result/2a1b402420",
        //         "POST",
        //         headers);
        // ExportResults[] exportResultsArray = new ExportResults[1];
        // exportResultsArray[0] = results;
        //
        // ExportCrawledVersion crawledVersion = new ExportCrawledVersion(
        //         "https://your.server/webhook/export/result/08338e505d",
        //         "POST",
        //         headers);
        // CopyleaksExportModel exportModel = new CopyleaksExportModel("https://your.server/webhook/export/result/2b42c39fba",
        //         exportResultsArray, crawledVersion);
        // try {
        //     Copyleaks.export(token, "2a1b402420", "08338e505d", exportModel); // 'exportId' value determined by you
        // } catch (ParseException e) {
        //     System.out.println(e.getMessage() + "\n");
        //     e.printStackTrace();
        //     return;
        // } catch (AuthExpiredException e) {
        //     System.out.println(e.getMessage() + "\n");
        //     e.printStackTrace();
        //     return;
        // } catch (UnderMaintenanceException e) {
        //     System.out.println(e.getMessage() + "\n");
        //     e.printStackTrace();
        //     return;
        // } catch (RateLimitException e) {
        //     System.out.println(e.getMessage() + "\n");
        //     e.printStackTrace();
        //     return;
        // } catch (CommandException e) {
        //     System.out.println(e.getMessage() + "\n");
        //     e.printStackTrace();
        //     return;
        // } catch (ExecutionException e) {
        //     System.out.println(e.getMessage() + "\n");
        //     e.printStackTrace();
        //     return;
        // } catch (InterruptedException e) {
        //     System.out.println(e.getMessage() + "\n");
        //     e.printStackTrace();
        //     return;
        // }

        // Wait while Copyleaks servers exporting artifacts...
        // Once process completed, you will get the "Export Completed" webhook.
        // Read more: https://api.copyleaks.com/documentation/v3/webhooks/export-completed
        CopyleaksTextModerationRequest request = new CopyleaksTextModerationRequest(
                /* text */ "This is some text to moderate.",
                /* sandbox */ true,
                /* language */ "en",
                        new Label[] {
                            new Label("other-v1"),
                            new Label("adult-v1"),
                            new Label("toxic-v1"),
                            new Label("violent-v1"),
                            new Label("profanity-v1"),
                            new Label("self-harm-v1"),
                            new Label("harassment-v1"),
                            new Label("hate-speech-v1"),
                            new Label("drugs-v1"),
                            new Label("firearms-v1"),
                            new Label("cybersecurity-v1")
                        });

        CopyleaksTextModerationResponseModel textModerationResponse;
        try {
            textModerationResponse = Copyleaks.textModerationClient.submitText(token, scanId, request);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (AuthExpiredException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (UnderMaintenanceException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (CommandException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (ExecutionException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }
        System.out.println("\nText scanned for Text Moderation.");
        System.out.println("Text Moderation Response: " + gson.toJson(textModerationResponse));
    }


    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }
}
