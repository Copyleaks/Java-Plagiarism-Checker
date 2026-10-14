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
import classes.clients.AiImageDetectionClient;
import classes.clients.AiVideoDetectionClient;
import classes.clients.ImagePlagiarismClient;
import classes.clients.TextModerationClient;
import classes.clients.WritingAssistantClient;
import helpers.CopyleaksClientHelper;
import helpers.CopyleaksRequestExecutor;
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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.text.ParseException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static helpers.CopyleaksRequestExecutor.pathSegment;

/**
 * Entry point of the Copyleaks SDK.
 * <p>
 * Static methods cover login, plagiarism scans (Authenticity), exports, account information and lookups.
 * The product clients are static fields: {@link #aiDetectionClient}, {@link #aiImageDetectionClient},
 * {@link #aiVideoDetectionClient}, {@link #imagePlagiarismClient}, {@link #writingAssistantClient} and
 * {@link #textModerationClient}.
 * <p>
 * All calls share one HTTP client and one set of limits, configured with {@link #setConnectTimeout(Duration)},
 * {@link #setRequestTimeout(Duration)}, {@link #setMaxConcurrentRequests(int)}, {@link #setMaxRetries(int)}
 * and {@link #setHttpClient(HttpClient)}.
 * <p>
 * Errors: a 429 response throws {@link RateLimitException}, a 503 response throws
 * {@link UnderMaintenanceException}, and any other non-2xx response throws {@link CommandException}.
 * Each carries the HTTP status code and the response body. Network errors and timeouts throw
 * {@link ExecutionException} with the I/O error as its cause. See
 * https://docs.copyleaks.com/concepts/performance/handling-failures for a retry strategy.
 */
public class Copyleaks {

    private static final CopyleaksRequestExecutor EXECUTOR = new CopyleaksRequestExecutor();

    private static final Gson gson = new Gson();

    private static final String JSON = "application/json";

    /** AI Text Detection. */
    public final static AIDetectionClient aiDetectionClient = new AIDetectionClient(EXECUTOR);
    /** Grammar Checker (Writing Assistant). */
    public final static WritingAssistantClient writingAssistantClient = new WritingAssistantClient(EXECUTOR);
    /** Text Moderation. */
    public final static TextModerationClient textModerationClient = new TextModerationClient(EXECUTOR);
    /** AI Image Detection, synchronous and asynchronous. */
    public final static AiImageDetectionClient aiImageDetectionClient = new AiImageDetectionClient(EXECUTOR);
    /** AI Video Detection. */
    public final static AiVideoDetectionClient aiVideoDetectionClient = new AiVideoDetectionClient(EXECUTOR);
    /** Image Plagiarism Detection. */
    public final static ImagePlagiarismClient imagePlagiarismClient = new ImagePlagiarismClient(EXECUTOR);

    /**
     * Changes the identity (login) server address. Default: https://id.copyleaks.com.
     * @param uri the server address, without a trailing slash.
     */
    public static void setIdentityUri(String uri){
        Consts.IDENTITY_SERVER_URI = Objects.requireNonNull(uri, "uri is null");
    }

    /**
     * Changes the API server address. Default: https://api.copyleaks.com.
     * @param uri the server address, without a trailing slash.
     */
    public static void setApiUri(String uri){
        Consts.API_SERVER_URI = Objects.requireNonNull(uri, "uri is null");
    }

    // ---- HTTP configuration ----------------------------------------------------------------------

    /**
     * Sets the time allowed to open a connection. Default: 30 seconds.
     * A client passed to {@link #setHttpClient(HttpClient)} keeps its own setting.
     * @param timeout a positive duration.
     */
    public static void setConnectTimeout(Duration timeout) {
        EXECUTOR.setConnectTimeout(timeout);
    }

    /**
     * @return the time allowed to open a connection.
     */
    public static Duration getConnectTimeout() {
        return EXECUTOR.getConnectTimeout();
    }

    /**
     * Sets the time allowed for each request: waiting for a free concurrency slot, sending the request
     * and reading the whole response. Default: 120 seconds. When it runs out, the call throws an
     * {@link ExecutionException} caused by a {@link java.net.http.HttpTimeoutException}.
     * @param timeout a positive duration.
     */
    public static void setRequestTimeout(Duration timeout) {
        EXECUTOR.setRequestTimeout(timeout);
    }

    /**
     * @return the time allowed for each request.
     */
    public static Duration getRequestTimeout() {
        return EXECUTOR.getRequestTimeout();
    }

    /**
     * Sets how many SDK requests may run at the same time in this JVM. Default: 20.
     * @param max a number of requests, at least 1.
     */
    public static void setMaxConcurrentRequests(int max) {
        EXECUTOR.setMaxConcurrentRequests(max);
    }

    /**
     * @return how many SDK requests may run at the same time.
     */
    public static int getMaxConcurrentRequests() {
        return EXECUTOR.getMaxConcurrentRequests();
    }

    /**
     * Sets how many times a failed request is retried. Default: 0 (no retries).
     * Any request is retried on 429 and 503. GET requests are also retried on 502, 504 and network errors.
     * The wait between attempts grows exponentially with jitter and honors the {@code Retry-After} header.
     * @param retries a number of retries, from 0 to 10.
     */
    public static void setMaxRetries(int retries) {
        EXECUTOR.setMaxRetries(retries);
    }

    /**
     * @return how many times a failed request is retried.
     */
    public static int getMaxRetries() {
        return EXECUTOR.getMaxRetries();
    }

    /**
     * Uses your own {@link HttpClient}, for example to set a proxy or an executor.
     * Its connect timeout applies instead of {@link #setConnectTimeout(Duration)}.
     * Pass null to go back to the client the SDK creates.
     * @param client the client to use, or null.
     */
    public static void setHttpClient(HttpClient client) {
        EXECUTOR.setHttpClient(client);
    }

    /**
     * @return the HTTP client the SDK sends requests with.
     */
    public static HttpClient getHttpClient() {
        return EXECUTOR.getHttpClient();
    }

    // ---- Account -----------------------------------------------------------------------------------

    /**
     * Login to Copyleaks authentication server.
     * For more info: https://docs.copyleaks.com/reference/actions/account/login
     *
     * @param email Copyleaks account email address.
     * @param key   Copyleaks account secret key.
     * @return CopyleaksAuthToken A authentication token that being expired after certain amount of time.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static CopyleaksAuthToken login(String email, String key)
            throws ExecutionException, UnderMaintenanceException, RateLimitException, CommandException, InterruptedException {
        Objects.requireNonNull(email, "email is null");
        Objects.requireNonNull(key, "key is null");

        Map<String, String> object = new HashMap<>();
        object.put("email", email);
        object.put("key", key);

        String body = EXECUTOR.send("POST", Consts.IDENTITY_SERVER_URI + "/v3/account/login/api",
                CopyleaksRequestExecutor.jsonBody(gson, object), JSON, null);
        return gson.fromJson(body, CopyleaksAuthToken.class);
    }

    // ---- Authenticity (plagiarism) scans --------------------------------------------------------------

    /**
     * Starting a new process by providing a file to scan.
     * For more info: https://docs.copyleaks.com/reference/actions/authenticity/submit-file
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission Submission properties
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static void submitFile(CopyleaksAuthToken authToken, String scanId, CopyleaksFileSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        put(authToken, "/v3/scans/submit/file/", scanId, submission);
    }

    /**
     * Starting a new process by providing a OCR image file to scan.
     * For more info: https://docs.copyleaks.com/reference/actions/authenticity/submit-ocr
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission Submission properties
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static void submitFileOcr(CopyleaksAuthToken authToken, String scanId, CopyleaksFileOcrSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        put(authToken, "/v3/scans/submit/ocr/", scanId, submission);
    }

    /**
     * Starting a new process by providing a URL to scan.
     * For more info: https://docs.copyleaks.com/reference/actions/authenticity/submit-url
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission Submission properties
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static void submitUrl(CopyleaksAuthToken authToken, String scanId, CopyleaksURLSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        put(authToken, "/v3/scans/submit/url/", scanId, submission);
    }

    private static void put(CopyleaksAuthToken authToken, String path, String scanId, Object submission)
            throws AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);
        EXECUTOR.send("PUT", Consts.API_SERVER_URI + path + pathSegment(scanId),
                CopyleaksRequestExecutor.jsonBody(gson, submission), JSON, authToken);
    }

    /**
     * Exporting scans artifact into your server.
     * For more info: https://docs.copyleaks.com/reference/actions/downloads/export
     *
     * @param authToken Your login token to Copyleaks server
     * @param scanId    The scan ID of the specific scan to export.
     * @param exportId  A new Id for the export process.
     * @param model     Request of which artifact should be exported.
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static void export(CopyleaksAuthToken authToken, String scanId, String exportId, CopyleaksExportModel model)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        Objects.requireNonNull(exportId, "exportId is null");
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(model, "model is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        EXECUTOR.send("POST", Consts.API_SERVER_URI + "/v3/downloads/" + pathSegment(scanId) + "/export/" + pathSegment(exportId),
                CopyleaksRequestExecutor.jsonBody(gson, model), JSON, authToken);
    }

    /**
     * Start scanning all the files you submitted for a price-check.
     * For more info: https://docs.copyleaks.com/reference/actions/authenticity/start
     *
     * @param authToken Your login token to Copyleaks server.
     * @param model     Include information about which scans should be started.
     * @return the scans that started and the scans that failed to start.
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     */
    public static CopyleaksStartResponse start(CopyleaksAuthToken authToken, CopyleaksStartRequest model) throws ParseException, AuthExpiredException, ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(model, "model is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        String body = EXECUTOR.send("PATCH", Consts.API_SERVER_URI + "/v3/scans/start",
                CopyleaksRequestExecutor.jsonBody(gson, model), JSON, authToken);
        return gson.fromJson(body, CopyleaksStartResponse.class);
    }

    /**
     * Delete the specific process from the server.
     * For more info: https://docs.copyleaks.com/reference/actions/authenticity/delete
     *
     * @param authToken Copyleaks authentication token
     * @param payloads  The scans to delete, and an optional completion webhook.
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     */
    public static void delete(CopyleaksAuthToken authToken, CopyleaksDeleteRequest payloads) throws ParseException, AuthExpiredException, ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(payloads, "payloads is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        EXECUTOR.send("PATCH", Consts.API_SERVER_URI + "/v3.1/scans/delete",
                CopyleaksRequestExecutor.jsonBody(gson, payloads), JSON, authToken);
    }

    /**
     * Resend status webhooks for existing scans.
     * For more info: https://docs.copyleaks.com/reference/actions/authenticity/resend-webhook
     *
     * @param authToken Copyleaks authentication token
     * @param scanId    Copyleaks scan Id
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static void resendWebhook(CopyleaksAuthToken authToken, String scanId) throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        EXECUTOR.send("POST", Consts.API_SERVER_URI + "/v3/scans/" + pathSegment(scanId) + "/webhooks/resend",
                HttpRequest.BodyPublishers.noBody(), JSON, authToken);
    }

    // ---- Account information --------------------------------------------------------------------------

    /**
     * Get current credits balance for the Copyleaks account.
     * For more info: https://docs.copyleaks.com/reference/actions/admin/check-credits
     *
     * @param authToken Copyleaks authentication token
     * @return the credits balance.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     */
    public static CreditsBalanceResponse getCreditsBalance(CopyleaksAuthToken authToken) throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException, ParseException, AuthExpiredException {
        Objects.requireNonNull(authToken, "token is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/scans/credits", null, null, authToken);
        return gson.fromJson(body, CreditsBalanceResponse.class);
    }

    /**
     * This endpoint allows you to export your usage history between two dates.
     * The output results will be exported to a csv file and it will be attached to the response.
     * For more info: https://docs.copyleaks.com/reference/actions/admin/usage-history
     *
     * @param authToken Copyleaks authentication token.
     * @param startDate The start date to collect usage history from. Date Format: `dd-MM-yyyy`.
     * @param endDate   The end date to collect usage history from. Date Format: `dd-MM-yyyy`.
     * @return the usage history as CSV text.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     */
    public static String getUsagesHistoryCsv(CopyleaksAuthToken authToken, String startDate, String endDate) throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException, ParseException, AuthExpiredException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(startDate, "startDate is null");
        Objects.requireNonNull(endDate, "endDate is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        return EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/scans/usages/history?start="
                + CopyleaksRequestExecutor.queryValue(startDate) + "&end=" + CopyleaksRequestExecutor.queryValue(endDate),
                null, null, authToken);
    }

    /**
     * Get information about a Private Cloud Hub repository, such as its credits, masking policy and status.
     * Requires the Admin or Super Admin role.
     * For more info: https://docs.copyleaks.com/reference/actions/private-cloud-hub/info
     *
     * @param authToken    Copyleaks authentication token.
     * @param repositoryId The repository ID, as shown in the Copyleaks Admin Dashboard.
     * @return the repository information.
     * @throws AuthExpiredException If the token has expired.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request, for example 403 without the required role.
     */
    public static RepositoryInfoResponse getRepositoryInfo(CopyleaksAuthToken authToken, String repositoryId)
            throws AuthExpiredException, ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(repositoryId, "repositoryId is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/repositories/repository/"
                + pathSegment(repositoryId) + "/info", null, null, authToken);
        return gson.fromJson(body, RepositoryInfoResponse.class);
    }

    // ---- Miscellaneous (no login needed) --------------------------------------------------------------

    /**
     * Get updates about copyleaks api release notes.
     *
     * @return List of release notes.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws CommandException If the server rejected the request.
     * @throws RateLimitException If too many requests were sent.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static ReleaseNotesResponse getReleaseNotes() throws UnderMaintenanceException, CommandException, RateLimitException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/release-logs.json", null, null, null);
        return gson.fromJson(body, ReleaseNotesResponse.class);
    }

    /**
     * Get a list of the file types supported by plagiarism scans.
     * For more info: https://docs.copyleaks.com/reference/actions/miscellaneous/supported-plagiarism-file-types
     *
     * @return List of supported file types.
     * @throws CommandException If the server rejected the request.
     * @throws RateLimitException If too many requests were sent.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static SupportedFileTypesResponse getSupportedFileTypes() throws CommandException, RateLimitException, UnderMaintenanceException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/miscellaneous/supported-plagiarism-file-types",
                null, null, null);
        return gson.fromJson(body, SupportedFileTypesResponse.class);
    }

    /**
     * Get a list of the file types supported by AI Text Detection.
     * For more info: https://docs.copyleaks.com/reference/actions/miscellaneous/supported-ai-text-detection-file-types
     *
     * @return the supported file extensions.
     * @throws CommandException If the server rejected the request.
     * @throws RateLimitException If too many requests were sent.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static SupportedAiTextDetectionFileTypesResponse getSupportedAiTextDetectionFileTypes() throws CommandException, RateLimitException, UnderMaintenanceException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/miscellaneous/supported-ai-text-detection-file-types",
                null, null, null);
        return gson.fromJson(body, SupportedAiTextDetectionFileTypesResponse.class);
    }

    /**
     * Get the source and result languages supported by cross-language plagiarism scans
     * ({@code properties.scanning.crossLanguages}). Language codes use ISO-639-1.
     * For more info: https://docs.copyleaks.com/reference/actions/miscellaneous/supported-cross-languages
     *
     * @return the allowed document and result languages.
     * @throws CommandException If the server rejected the request.
     * @throws RateLimitException If too many requests were sent.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static CrossLanguagesResponse getAllowedCrossLanguages() throws CommandException, RateLimitException, UnderMaintenanceException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/miscellaneous/allowed-cross-languages",
                null, null, null);
        return gson.fromJson(body, CrossLanguagesResponse.class);
    }

    /**
     * Get a list of the supported languages for OCR (this is not a list of supported languages for the api, but only for the OCR files scan).
     * For more info: https://docs.copyleaks.com/reference/actions/miscellaneous/ocr-supported-languages
     *
     * @return A list of supported OCR languages
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public static String[] getOCRSupportedLanguagesAsync() throws RateLimitException, CommandException, UnderMaintenanceException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/miscellaneous/ocr-languages-list", null, null, null);
        return gson.fromJson(body, String[].class);
    }
}
