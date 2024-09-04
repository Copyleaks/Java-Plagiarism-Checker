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

package classes.clients;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import com.google.gson.Gson;
import classes.Consts;
import helpers.CopyleaksClientHelper;
import models.exceptions.AuthExpiredException;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.response.CopyleaksAuthToken;
import models.response.CreditsBalanceResponse;
import models.response.SupportedFileTypesResponse;
import models.response.writingassitant.CorrectionTypesResponse;
import models.response.writingassitant.WritingAssistantResponse;
import models.submissions.writingassistant.CopyleaksWritingAssistantSubmissionModel;

public class WritingAssistantClient {

    private final HttpClient httpClient;
    private final Semaphore semaphore;
    private static final Gson gson = new Gson();

    public WritingAssistantClient(HttpClient httpClient, Semaphore semaphore) {
        this.httpClient = httpClient;
        this.semaphore = semaphore;
    }

    /**
     * Use Copyleaks Writing Assistant to generate grammar, spelling and sentence corrections for a given text.
     * 
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission Submission model
     */
    public WritingAssistantResponse submitText(CopyleaksAuthToken authToken, String scanId, CopyleaksWritingAssistantSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, CommandException, ExecutionException, InterruptedException {
        assert authToken != null : "token is null";
        assert scanId != null : "scanId is null";
        assert submission != null : "submission is null";

        CopyleaksClientHelper.verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(submission)))
                .uri(URI.create(Consts.API_SERVER_URI + "/v1/writing-feedback/" + scanId + "/check"))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try {
            response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();

            if (CopyleaksClientHelper.ensureHttpSuccess(statusCode)) {
                WritingAssistantResponse supportedFileTypes = gson.fromJson(body, WritingAssistantResponse.class);
                return supportedFileTypes;            
            } else if (statusCode == 503) {
                throw new UnderMaintenanceException();
            } else {
                throw new CommandException("command failed with status Code:" + String.valueOf(statusCode) + "\n" + response.toString());
            }
        } finally {
            semaphore.release();
        }
    }

    /**
     * Get a list of correction types supported within the Writing Assistant API. Correction types apply to all supported languages. 
     * The supplied language code for this request is used to determine the language of the texts returned.

     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     * * RateLimitException: Too many requests. Please wait before calling again.
     *
     * @param authToken Copyleaks authentication token
     * @param languageCode The language for the returned texts to be in. Language codes are in ISO 639-1 standard. Supported Values: en - English
     */
    public CorrectionTypesResponse getCorrectionTypes(CopyleaksAuthToken authToken, String languageCode) throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException, ParseException, AuthExpiredException {
        assert authToken != null : "token is null";

        CopyleaksClientHelper.verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(Consts.API_SERVER_URI + "/v1/writing-feedback/correction-types/" + languageCode))
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (CopyleaksClientHelper.ensureHttpSuccess(statusCode)) {
                CorrectionTypesResponse supportedFileTypes = gson.fromJson(body, CorrectionTypesResponse.class);
                return supportedFileTypes;              
            } else if (statusCode == 503) {
                throw new UnderMaintenanceException();
            } else if (statusCode == 429) {
                throw new RateLimitException();
            } else {
                throw new CommandException("command failed with status Code:" + String.valueOf(statusCode) + "\n" + response.toString());
            }
        }
        finally{
            semaphore.release();
        }
    }
}