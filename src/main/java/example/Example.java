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
import models.misc.Products;
import models.response.CopyleaksAuthToken;
import models.submissions.CopyleaksFileSubmissionModel;
import models.submissions.properties.SubmissionProperties;
import models.submissions.properties.SubmissionWebhooks;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Example {

    // Register on https://api.copyleaks.com and grab your secret key (from the dashboard page).
    private static final String EMAIL_ADDRESS = "YOUR@EMAIL.HERE";
    private static final String KEY = "00000000-0000-0000-0000-000000000000";
    private static final String PRODUCT = Products.EDUCATION; // BUSINESSES or EDUCATION, depending on your Copyleaks account type.

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
            Copyleaks.submitFile(PRODUCT, token, scanId, model);
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
    }


    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }
}
