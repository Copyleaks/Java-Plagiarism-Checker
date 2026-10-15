package classes.clients;

import java.net.http.HttpClient;
import java.text.ParseException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import com.google.gson.Gson;
import classes.Consts;
import helpers.CopyleaksClientHelper;
import helpers.CopyleaksRequestExecutor;
import helpers.MultipartBody;
import models.exceptions.AuthExpiredException;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.request.AiImageDetection.CopyleaksAiImageDetectionAsyncRequestModel;
import models.request.AiImageDetection.CopyleaksAiImageDetectionFileRequestModel;
import models.request.AiImageDetection.CopyleaksAiImageDetectionRequestModel;
import models.response.CopyleaksAuthToken;
import models.response.AiImageDetection.CopyleaksAiImageDetectionResponseModel;

/** AI Image Detection; use {@code Copyleaks.aiImageDetectionClient}. Errors: see {@link classes.Copyleaks}. */
public class AiImageDetectionClient {

    private final CopyleaksRequestExecutor executor;
    private static final Gson gson = new Gson();

    public AiImageDetectionClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /** @deprecated Use {@code Copyleaks.aiImageDetectionClient}. */
    @Deprecated
    public AiImageDetectionClient(HttpClient httpClient, Semaphore semaphore) {
        this(new CopyleaksRequestExecutor(httpClient, semaphore));
    }

    /**
     * Checks a base64-encoded image and returns the result. For large images prefer
     * {@link #submitFile(CopyleaksAuthToken, String, CopyleaksAiImageDetectionFileRequestModel)}, which skips base64.
     * https://docs.copyleaks.com/reference/actions/ai-image-detector/check
     */
    public CopyleaksAiImageDetectionResponseModel submit(CopyleaksAuthToken authToken, String scanId,
            CopyleaksAiImageDetectionRequestModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException,
            ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        String body = executor.send("POST", checkUrl(scanId),
                CopyleaksRequestExecutor.jsonBody(gson, submission), "application/json", authToken);
        return gson.fromJson(body, CopyleaksAiImageDetectionResponseModel.class);
    }

    /** Like {@link #submit}, but uploads the image bytes as multipart instead of base64. */
    public CopyleaksAiImageDetectionResponseModel submitFile(CopyleaksAuthToken authToken, String scanId,
            CopyleaksAiImageDetectionFileRequestModel submission)
            throws AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException,
            ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        Objects.requireNonNull(submission.getImage(), "image is null");
        Objects.requireNonNull(submission.getFilename(), "filename is null");
        Objects.requireNonNull(submission.getModel(), "model is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        MultipartBody form = new MultipartBody()
                .addFile("image", submission.getFilename(), MultipartBody.imageContentType(submission.getFilename()),
                        submission.getImage())
                .addField("filename", submission.getFilename())
                .addField("model", submission.getModel())
                .addField("sandbox", Boolean.toString(submission.isSandbox()));
        if (submission.getMaskType() != null) {
            form.addField("maskType", submission.getMaskType());
        }
        if (submission.getObjectDetection() != null) {
            form.addField("objectDetection", gson.toJson(submission.getObjectDetection()));
        }
        String body = executor.send("POST", checkUrl(scanId), form.build(), form.contentType(), authToken);
        return gson.fromJson(body, CopyleaksAiImageDetectionResponseModel.class);
    }

    /**
     * Submits an image URL. The result goes to {@code webhooks.url} later; parse it into
     * {@link CopyleaksAiImageDetectionResponseModel}.
     * https://docs.copyleaks.com/reference/actions/ai-image-detector/submit
     */
    public void submitAsync(CopyleaksAuthToken authToken, String scanId,
            CopyleaksAiImageDetectionAsyncRequestModel submission)
            throws AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException,
            ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        executor.send("PUT",
                Consts.API_SERVER_URI + "/v1/ai-image-detector-async/" + CopyleaksRequestExecutor.pathSegment(scanId) + "/submit",
                CopyleaksRequestExecutor.jsonBody(gson, submission), "application/json", authToken);
    }

    private static String checkUrl(String scanId) {
        return Consts.API_SERVER_URI + "/v1/ai-image-detector/" + CopyleaksRequestExecutor.pathSegment(scanId) + "/check";
    }
}
