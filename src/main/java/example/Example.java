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

import models.response.CopyleaksAuthToken;
import example.functions.AIDetectionExample;
import example.functions.AiImageDetectionExample;
import example.functions.AuthenticationExample;
import example.functions.PlagiarismScanExample;
import example.functions.TextModerationExample;
import example.functions.WritingAssistantExample;
import java.util.Random;
import com.google.gson.Gson;


/**
 * Main example class for demonstrating Copyleaks SDK usage.
 */
public class Example {

    // Register on https://api.copyleaks.com and grab your secret key (from the dashboard page).
    private static final String EMAIL_ADDRESS = "YOUR@EMAIL.HERE";
    private static final String KEY = "00000000-0000-0000-0000-000000000000";
    private static final Gson gson = new Gson();

    /**
     * Entry point for running all Copyleaks product examples.
     * Starts the webhook server, authenticates, and runs each product demo.
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Start the webhook server in a separate thread
        new Thread(() -> WebhookApplication.main(new String[] {})).start();
        System.out.println("Press Ctrl+C to shutdown the server");

        // Authenticate with Copyleaks
        CopyleaksAuthToken token = AuthenticationExample.authenticate(EMAIL_ADDRESS, KEY);
        if (token == null) {
            System.out.println("Authentication failed. Exiting.");
            return;
        }

        // Generate a random scan ID for this session
        String scanId = Integer.toString(getRandomNumberInRange(100, 100000));

        // Run product examples

        // 1. Plagiarism scan example
        PlagiarismScanExample.run(token, scanId);

        // 2. AI text detection example
        AIDetectionExample.run(token, scanId);

        // 3. Writing assistant feedback example
        WritingAssistantExample.run(token, scanId);

        // 4. Text moderation example
        TextModerationExample.run(token, scanId, gson);

        // 5. AI image detection example
        AiImageDetectionExample.run(token, scanId, gson);
    }

    /**
     * Generates a random integer in the given range (inclusive).
     * @param min Minimum value
     * @param max Maximum value
     * @return Random integer between min and max
     */
    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }

}