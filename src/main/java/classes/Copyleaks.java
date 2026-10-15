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
 * Entry point of the SDK: login, plagiarism scans, exports and account calls. Other products are the static
 * client fields. All calls share one HTTP client and the limits set here. {@code scanId} is an ID you choose.
 * <p>Errors, here and in the clients: 429 throws {@link RateLimitException}, 503 {@link UnderMaintenanceException},
 * other non-2xx {@link CommandException} (each with the status code and body), an expired token
 * {@link AuthExpiredException}, and network errors and timeouts {@link ExecutionException} with the I/O error as
 * cause. {@link ParseException} is never thrown; it stays for source compatibility.
 * Retry strategy: https://docs.copyleaks.com/concepts/performance/handling-failures
 */
public class Copyleaks {

    private static final CopyleaksRequestExecutor EXECUTOR = new CopyleaksRequestExecutor();

    private static final Gson gson = new Gson();

    private static final String JSON = "application/json";

    /** AI Text Detection client. */
    public final static AIDetectionClient aiDetectionClient = new AIDetectionClient(EXECUTOR);
    /** Grammar Checker (Writing Assistant) client. */
    public final static WritingAssistantClient writingAssistantClient = new WritingAssistantClient(EXECUTOR);
    /** Text Moderation client. */
    public final static TextModerationClient textModerationClient = new TextModerationClient(EXECUTOR);
    /** AI Image Detection client, synchronous and asynchronous. */
    public final static AiImageDetectionClient aiImageDetectionClient = new AiImageDetectionClient(EXECUTOR);
    /** AI Video Detection client. */
    public final static AiVideoDetectionClient aiVideoDetectionClient = new AiVideoDetectionClient(EXECUTOR);
    /** Image Plagiarism Detection client. */
    public final static ImagePlagiarismClient imagePlagiarismClient = new ImagePlagiarismClient(EXECUTOR);

    /** Identity (login) server address, without a trailing slash. Default: https://id.copyleaks.com */
    public static void setIdentityUri(String uri){
        Consts.IDENTITY_SERVER_URI = Objects.requireNonNull(uri, "uri is null");
    }

    /** API server address, without a trailing slash. Default: https://api.copyleaks.com */
    public static void setApiUri(String uri){
        Consts.API_SERVER_URI = Objects.requireNonNull(uri, "uri is null");
    }

    /**
     * Time allowed to open a connection. Default: 30 seconds.
     * A client passed to {@link #setHttpClient(HttpClient)} keeps its own setting.
     * @param timeout a positive duration.
     */
    public static void setConnectTimeout(Duration timeout) {
        EXECUTOR.setConnectTimeout(timeout);
    }

    /** @return the time allowed to open a connection. */
    public static Duration getConnectTimeout() {
        return EXECUTOR.getConnectTimeout();
    }

    /**
     * Time allowed per request, including the wait for a concurrency slot and reading the whole response.
     * Default: 120 seconds. On timeout the call throws {@link ExecutionException} caused by
     * {@link java.net.http.HttpTimeoutException}.
     * @param timeout a positive duration.
     */
    public static void setRequestTimeout(Duration timeout) {
        EXECUTOR.setRequestTimeout(timeout);
    }

    /** @return the time allowed for each request. */
    public static Duration getRequestTimeout() {
        return EXECUTOR.getRequestTimeout();
    }

    /** How many SDK requests may run at the same time in this JVM, at least 1. Default: 20. */
    public static void setMaxConcurrentRequests(int max) {
        EXECUTOR.setMaxConcurrentRequests(max);
    }

    /** @return how many SDK requests may run at the same time. */
    public static int getMaxConcurrentRequests() {
        return EXECUTOR.getMaxConcurrentRequests();
    }

    /**
     * Retries for a failed request. Default: 0. Any request is retried on 429 and 503; GET requests also on
     * 502, 504 and network errors. Backoff is exponential with jitter and honors {@code Retry-After} (max 30 s).
     * @param retries a number of retries, from 0 to 10.
     */
    public static void setMaxRetries(int retries) {
        EXECUTOR.setMaxRetries(retries);
    }

    /** @return how many times a failed request is retried. */
    public static int getMaxRetries() {
        return EXECUTOR.getMaxRetries();
    }

    /**
     * Uses your own {@link HttpClient}, for example for a proxy. Its connect timeout replaces
     * {@link #setConnectTimeout(Duration)}.
     * @param client the client to use, or null to go back to the SDK's own client.
     */
    public static void setHttpClient(HttpClient client) {
        EXECUTOR.setHttpClient(client);
    }

    /** @return the HTTP client the SDK sends requests with. */
    public static HttpClient getHttpClient() {
        return EXECUTOR.getHttpClient();
    }

    /**
     * Logs in. https://docs.copyleaks.com/reference/actions/account/login
     * @param key the account API key.
     * @return a token for the other calls. It expires; log in again on {@link AuthExpiredException}.
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

    /** Submits a file for a plagiarism scan. https://docs.copyleaks.com/reference/actions/authenticity/submit-file */
    public static void submitFile(CopyleaksAuthToken authToken, String scanId, CopyleaksFileSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        put(authToken, "/v3/scans/submit/file/", scanId, submission);
    }

    /** Submits an image for OCR and a scan. https://docs.copyleaks.com/reference/actions/authenticity/submit-ocr */
    public static void submitFileOcr(CopyleaksAuthToken authToken, String scanId, CopyleaksFileOcrSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        put(authToken, "/v3/scans/submit/ocr/", scanId, submission);
    }

    /** Submits a URL for a plagiarism scan. https://docs.copyleaks.com/reference/actions/authenticity/submit-url */
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
     * Exports scan artifacts to your server. https://docs.copyleaks.com/reference/actions/downloads/export
     * @param exportId a new ID you choose for this export.
     * @param model    which artifacts to export, and where.
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
     * Starts scans submitted with the "check credits" action.
     * https://docs.copyleaks.com/reference/actions/authenticity/start
     * @return the scans that started and the scans that failed to start.
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
     * Deletes scans. https://docs.copyleaks.com/reference/actions/authenticity/delete
     * @param payloads the scans to delete, and an optional completion webhook.
     */
    public static void delete(CopyleaksAuthToken authToken, CopyleaksDeleteRequest payloads) throws ParseException, AuthExpiredException, ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(payloads, "payloads is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        EXECUTOR.send("PATCH", Consts.API_SERVER_URI + "/v3.1/scans/delete",
                CopyleaksRequestExecutor.jsonBody(gson, payloads), JSON, authToken);
    }

    /** Resends a scan's status webhooks. https://docs.copyleaks.com/reference/actions/authenticity/resend-webhook */
    public static void resendWebhook(CopyleaksAuthToken authToken, String scanId) throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        EXECUTOR.send("POST", Consts.API_SERVER_URI + "/v3/scans/" + pathSegment(scanId) + "/webhooks/resend",
                HttpRequest.BodyPublishers.noBody(), JSON, authToken);
    }

    /** Credit balance of the account. https://docs.copyleaks.com/reference/actions/admin/check-credits */
    public static CreditsBalanceResponse getCreditsBalance(CopyleaksAuthToken authToken) throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException, ParseException, AuthExpiredException {
        Objects.requireNonNull(authToken, "token is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/scans/credits", null, null, authToken);
        return gson.fromJson(body, CreditsBalanceResponse.class);
    }

    /**
     * Usage history between two dates. https://docs.copyleaks.com/reference/actions/admin/usage-history
     * @param startDate format {@code dd-MM-yyyy}.
     * @param endDate   format {@code dd-MM-yyyy}.
     * @return the usage history as CSV text.
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
     * Private Cloud Hub repository info. Requires the Admin or Super Admin role (403 otherwise).
     * https://docs.copyleaks.com/reference/actions/private-cloud-hub/info
     * @param repositoryId the repository ID shown in the Copyleaks Admin Dashboard.
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

    /** Copyleaks API release notes. */
    public static ReleaseNotesResponse getReleaseNotes() throws UnderMaintenanceException, CommandException, RateLimitException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/release-logs.json", null, null, null);
        return gson.fromJson(body, ReleaseNotesResponse.class);
    }

    /**
     * File types supported by plagiarism scans.
     * https://docs.copyleaks.com/reference/actions/miscellaneous/supported-plagiarism-file-types
     */
    public static SupportedFileTypesResponse getSupportedFileTypes() throws CommandException, RateLimitException, UnderMaintenanceException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/miscellaneous/supported-plagiarism-file-types",
                null, null, null);
        return gson.fromJson(body, SupportedFileTypesResponse.class);
    }

    /**
     * File types supported by AI Text Detection.
     * https://docs.copyleaks.com/reference/actions/miscellaneous/supported-ai-text-detection-file-types
     */
    public static SupportedAiTextDetectionFileTypesResponse getSupportedAiTextDetectionFileTypes() throws CommandException, RateLimitException, UnderMaintenanceException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/miscellaneous/supported-ai-text-detection-file-types",
                null, null, null);
        return gson.fromJson(body, SupportedAiTextDetectionFileTypesResponse.class);
    }

    /**
     * Languages for {@code properties.scanning.crossLanguages}, as ISO-639-1 codes.
     * https://docs.copyleaks.com/reference/actions/miscellaneous/supported-cross-languages
     */
    public static CrossLanguagesResponse getAllowedCrossLanguages() throws CommandException, RateLimitException, UnderMaintenanceException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/miscellaneous/allowed-cross-languages",
                null, null, null);
        return gson.fromJson(body, CrossLanguagesResponse.class);
    }

    /**
     * Languages supported by OCR file scans.
     * https://docs.copyleaks.com/reference/actions/miscellaneous/ocr-supported-languages
     */
    public static String[] getOCRSupportedLanguagesAsync() throws RateLimitException, CommandException, UnderMaintenanceException, ExecutionException, InterruptedException {
        String body = EXECUTOR.send("GET", Consts.API_SERVER_URI + "/v3/miscellaneous/ocr-languages-list", null, null, null);
        return gson.fromJson(body, String[].class);
    }
}
