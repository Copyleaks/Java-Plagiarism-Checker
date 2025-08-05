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


package classes;

import com.google.gson.Gson;

import classes.clients.AIDetectionClient;
import classes.clients.TextModerationClient;
import classes.clients.WritingAssistantClient;
import models.constants.SupportedFilesTypes;
import models.exceptions.AuthExpiredException;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.exports.CopyleaksExportModel;
import models.request.CopyleaksDeleteRequest;
import models.request.CopyleaksStartRequest;
import models.response.*;
import models.submissions.CopyleaksFileOcrSubmissionModel;
import models.submissions.CopyleaksFileSubmissionModel;
import models.submissions.CopyleaksURLSubmissionModel;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class Copyleaks {
    private static Semaphore semaphore = new Semaphore(20); // To limit the number of httpClient connections.

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .build();

    private static final Gson gson = new Gson();

    public final static AIDetectionClient aiDetectionClient = new AIDetectionClient(HTTP_CLIENT, semaphore);
    public final static WritingAssistantClient writingAssistantClient = new WritingAssistantClient(HTTP_CLIENT, semaphore);
    public final static TextModerationClient textModerationClient = new TextModerationClient(HTTP_CLIENT, semaphore);

    public static void setIdentityUri(String uri){
        Consts.IDENTITY_SERVER_URI = uri;
    }
    public static void setApiUri(String uri){
        Consts.API_SERVER_URI = uri;
    }

    /**
     * Login to Copyleaks authentication server.
     * For more info: https://api.copyleaks.com/documentation/v3/account/login.
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     *
     * @param email Copyleaks account email address.
     * @param key   Copyleaks account secret key.
     * @return CopyleaksAuthToken A authentication token that being expired after certain amount of time.
     * @throws ExecutionException, UnderMaintenanceException, RateLimitException, CommandException, InterruptedException
     */
    public static CopyleaksAuthToken login(String email, String key)
            throws ExecutionException, UnderMaintenanceException, RateLimitException, CommandException, InterruptedException {
        assert email != null : "email is null";
        assert key != null : "key is null";

        Map<String, String> object = new HashMap<>();
        object.put("email", email);
        object.put("key", key);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(object)))
                .uri(URI.create(Consts.IDENTITY_SERVER_URI + "/v3/account/login/api"))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .build();
        
        semaphore.acquire();
        try{
            CompletableFuture<HttpResponse<String>> response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();

            if (ensureHttpSuccess(statusCode)) {
                CopyleaksAuthToken token = gson.fromJson(body, CopyleaksAuthToken.class);
                return token;
            } else if (statusCode == 503) {
                throw new UnderMaintenanceException();
            } else {
                throw new CommandException("command failed with status Code:" + String.valueOf(statusCode) + "\n" + response.toString());
            }
        }
        finally{
            semaphore.release();
        }

    }

    /**
     * Starting a new process by providing a file to scan.
     * For more info:
     * https://api.copyleaks.com/documentation/v3/scans/submit/file
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission Submission properties
     */
    public static void submitFile(CopyleaksAuthToken authToken, String scanId, CopyleaksFileSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, CommandException, ExecutionException, InterruptedException {
        assert authToken != null : "token is null";
        assert scanId != null : "scanId is null";
        assert submission != null : "submission is null";
        
        // get file extension
        String fileExtension = getFileExtension(submission.getFilename());
        
        if (Arrays.asList(SupportedFilesTypes.SUPPORTED_CODE_EXTENSIONS).contains(fileExtension)) {
            DeprecationService.showDeprecationMessage();
        }

        verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(submission)))
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/scans/submit/file/" + scanId))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            
            
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
            if (ensureHttpSuccess(statusCode)) {
                return;
            } else if (statusCode == 503) {
                throw new UnderMaintenanceException();
            } else {
                throw new CommandException("command failed with status Code:" + String.valueOf(statusCode) + "\n" + response.toString());
            }
        }
        finally{
            semaphore.release();
        }
    }

    /**
     * Extracts the file extension from a given filename.
     * 
     * <p>This method returns the file extension without the dot separator,
     * converted to lowercase for consistent comparison. If no extension is found
     * or the filename is invalid, an empty string is returned.</p>
     * 
     * @param fileName the name of the file from which to extract the extension.
     *                 Can be a simple filename or a full file path.
     * @return the file extension in lowercase without the dot (e.g., "txt", "java", "pdf"),
     *         or an empty string if no extension exists or the filename is null/empty
     * 
     * @throws NullPointerException if fileName is null (handled gracefully by returning empty string)
     * **/
    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    /**
     * Starting a new process by providing a OCR image file to scan.
     * For more info:
     * https://api.copyleaks.com/documentation/v3/scans/submit/ocr
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission Submission properties
     */
    public static void submitFileOcr(CopyleaksAuthToken authToken, String scanId, CopyleaksFileOcrSubmissionModel submission) throws ParseException, AuthExpiredException, UnderMaintenanceException, CommandException, ExecutionException, InterruptedException {
        assert authToken != null : "token is null";
        assert scanId != null : "scanId is null";
        assert submission != null : "submission is null";

        verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(submission)))
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/scans/submit/ocr/" + scanId))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                return;
            } else if (statusCode == 503) {
                throw new UnderMaintenanceException();
            } else {
                throw new CommandException("command failed with status Code:" + String.valueOf(statusCode) + "\n" + response.toString());
            }
        }
        finally{
            semaphore.release();
        }
    }

    /**
     * Starting a new process by providing a URL to scan.
     * For more info:
     * https://api.copyleaks.com/documentation/v3/scans/submit/url
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission Submission properties
     */

    public static void submitUrl(CopyleaksAuthToken authToken, String scanId, CopyleaksURLSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, CommandException, ExecutionException, InterruptedException {
        assert authToken != null : "token is null";
        assert scanId != null : "scanId is null";
        assert submission != null : "submission is null";

        verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(submission)))
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/scans/submit/url/" + scanId))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                return;
            } else if (statusCode == 503) {
                throw new UnderMaintenanceException();
            } else {
                throw new CommandException("command failed with status Code:" + String.valueOf(statusCode) + "\n" + response.toString());
            }
        }
        finally{
            semaphore.release();
        }
    }

    /**
     * Exporting scans artifact into your server.
     * For more info:
     * https://api.copyleaks.com/documentation/v3/downloads/export
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     *
     * @param authToken Your login token to Copyleaks server
     * @param scanId    The scan ID of the specific scan to export.
     * @param exportId  A new Id for the export process.
     * @param model     Request of which artifact should be exported.
     */
    public static void export(CopyleaksAuthToken authToken, String scanId, String exportId, CopyleaksExportModel model)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        assert exportId != null : "exportId is null";
        assert authToken != null : "token is null";
        assert scanId != null : "scanId is null";
        assert model != null : "model is null";
        verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(model)))
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/downloads/" + scanId + "/export/" + exportId))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                return;
            } else if (statusCode == 503) {
                throw new UnderMaintenanceException();
            } else {
                throw new CommandException("command failed with status Code:" + String.valueOf(statusCode) + "\n" + response.toString());
            }
        }
        finally{
            semaphore.release();
        }
    }

    /**
     * Start scanning all the files you submitted for a price-check.
     * For more info:
     * https://api.copyleaks.com/documentation/v3/scans/start
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     *
     * @param authToken Your login token to Copyleaks server.
     * @param model     Include information about which scans should be started.
     * @return
     */
    public static CopyleaksStartResponse start(CopyleaksAuthToken authToken, CopyleaksStartRequest model) throws ParseException, AuthExpiredException, ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException {

        assert authToken != null : "token is null";
        assert model != null : "model is null";

        verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/scans/start"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(model)))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                CopyleaksStartResponse startResponse = gson.fromJson(body, CopyleaksStartResponse.class);
                return startResponse;
            } else if (statusCode == 503) {
                throw new UnderMaintenanceException();
            } else {
                throw new CommandException("command failed with status Code:" + String.valueOf(statusCode) + "\n" + response.toString());
            }
        }
        finally{
            semaphore.release();
        }
    }

    /**
     * Delete the specific process from the server.
     * For more info:
     * https://api.copyleaks.com/documentation/v3/scans/delete
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     *
     * @param authToken Copyleaks authentication token
     * @param payloads
     */
    public static void delete(CopyleaksAuthToken authToken, CopyleaksDeleteRequest payloads) throws ParseException, AuthExpiredException, ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException {

        assert authToken != null : "token is null";
        assert payloads != null : "payloads is null";

        verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Consts.API_SERVER_URI + "/v3.1/scans/delete"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(payloads)))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                return;
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

    /**
     * Resend status webhooks for existing scans.
     * For more info:
     * https://api.copyleaks.com/documentation/v3/scans/webhook-resend
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     *
     * @param authToken Copyleaks authentication token
     * @param scanId    Copyleaks scan Id
     */
    public static void resendWebhook(CopyleaksAuthToken authToken, String scanId) throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        assert authToken != null : "token is null";
        assert scanId != null : "scanId is null";

        verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/scans/" + scanId + "/webhooks/resend"))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                return;
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

    /**
     * Get current credits balance for the Copyleaks account.
     * For more info:
     * https://api.copyleaks.com/documentation/v3/scans/credits
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     * * RateLimitException: Too many requests. Please wait before calling again.
     *
     * @param authToken Copyleaks authentication token
     * @return
     */
    public static CreditsBalanceResponse getCreditsBalance(CopyleaksAuthToken authToken) throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException, ParseException, AuthExpiredException {
        assert authToken != null : "token is null";

        verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/scans/credits"))
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                CreditsBalanceResponse creditsBalance = gson.fromJson(body, CreditsBalanceResponse.class);
                return creditsBalance;
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

    /**
     * Verify that Copyleaks authentication token is exists and not expired.
     * * Exceptions:
     * * AuthExpiredException: authentication expired. Need to login again.
     *
     * @param authToken Copyleaks authentication token
     */
    private static void verifyAuthToken(CopyleaksAuthToken authToken) throws AuthExpiredException {
        Calendar date = Calendar.getInstance();
        Date currentDate = new Date(date.getTimeInMillis() + (5 * 60 * 1000));
        Date expireDate = Date.from(Instant.from(ZonedDateTime.parse(authToken.getExpires())));
        if (expireDate.getTime() <= currentDate.getTime())
            throw new AuthExpiredException();
    }

    private static boolean ensureHttpSuccess(int statusCode) {
        if (statusCode >= 200 && statusCode <= 299)
            return true;
        return false;
    }

    /**
     * This endpoint allows you to export your usage history between two dates.
     * The output results will be exported to a csv file and it will be attached to the response.
     * For more info:
     * https://api.copyleaks.com/documentation/v3/scans/usages/history
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     * * RateLimitException: Too many requests. Please wait before calling again.
     *
     * @param authToken Copyleaks authentication token.
     * @param startDate The start date to collect usage history from. Date Format: `dd-MM-yyyy`.
     * @param endDate   The end date to collect usage history from. Date Format: `dd-MM-yyyy`.
     * @return
     */
    public static String getUsagesHistoryCsv(CopyleaksAuthToken authToken, String startDate, String endDate) throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException, ParseException, AuthExpiredException {
        assert authToken != null : "token is null";
        assert startDate != null : "startDate is null";
        assert endDate != null : "endDate is null";

        verifyAuthToken(authToken);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/scans/usages/history?start=" + startDate + "&end=" + endDate))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .setHeader("Authorization", "Bearer " + authToken.getAccessToken())
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                return body;
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

    /**
     * Get updates about copyleaks api release notes.
     * For more info: https://api.copyleaks.com/documentation/v3/release-notes
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     * * RateLimitException: Too many requests. Please wait before calling again.
     *
     * @returns List of release notes.
     */
    public static ReleaseNotesResponse getReleaseNotes() throws UnderMaintenanceException, CommandException, RateLimitException, ExecutionException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/release-logs.json"))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                return gson.fromJson(body, ReleaseNotesResponse.class);
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

    /**
     * Get a list of the supported file types.
     * For more info: https://api.copyleaks.com/documentation/v3/specifications/supported-file-types
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     * * RateLimitException: Too many requests. Please wait before calling again.
     *
     * @returns List of supported file types.
     */
    public static SupportedFileTypesResponse getSupportedFileTypes() throws CommandException, RateLimitException, UnderMaintenanceException, ExecutionException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/miscellaneous/supported-file-types"))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                SupportedFileTypesResponse supportedFileTypes = gson.fromJson(body, SupportedFileTypesResponse.class);
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

    /**
     * Get a list of the supported languages for OCR (this is not a list of supported languages for the api, but only for the OCR files scan).
     * For more info: https://api.copyleaks.com/documentation/v3/specifications/ocr-languages/list
     * * Exceptions:
     * * CommandExceptions: Server reject the request. See response status code,
     * headers and content for more info.
     * * UnderMaintenanceException: Copyleaks servers are unavailable for maintenance.
     * We recommend to implement exponential backoff algorithm as described here:
     * https://api.copyleaks.com/documentation/v3/exponential-backoff
     * * RateLimitException: Too many requests. Please wait before calling again.
     *
     * @return A list of supported OCR languages
     */
    public static String[] getOCRSupportedLanguagesAsync() throws RateLimitException, CommandException, UnderMaintenanceException, ExecutionException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(Consts.API_SERVER_URI + "/v3/miscellaneous/ocr-languages-list"))
                .setHeader("Content-Type", "application/json")
                .setHeader("User-Agent", Consts.USER_AGENT)
                .build();

        CompletableFuture<HttpResponse<String>> response;
        semaphore.acquire();
        try{
            response = HTTP_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    
            String body = response.thenApply(HttpResponse::body).get();
            int statusCode = response.thenApply(HttpResponse::statusCode).get();
    
            if (ensureHttpSuccess(statusCode)) {
                String[] languages = gson.fromJson(body, String[].class);
                return languages;
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
