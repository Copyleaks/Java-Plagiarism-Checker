package classes.clients;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import com.google.gson.Gson;
import classes.Consts;
import helpers.CopyleaksClientHelper;
import helpers.CopyleaksRequestExecutor;
import models.exceptions.AuthExpiredException;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.request.AiVideoDetection.CopyleaksAiVideoDetectionRequestModel;
import models.response.CopyleaksAuthToken;

/** AI Video Detection; use {@code Copyleaks.aiVideoDetectionClient}. Errors: see {@link classes.Copyleaks}. */
public class AiVideoDetectionClient {

    private final CopyleaksRequestExecutor executor;
    private static final Gson gson = new Gson();

    /** @param executor the request executor to send requests with. */
    public AiVideoDetectionClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /**
     * Submits a video URL. The result goes to {@code webhooks.url} later; parse it into
     * {@link models.response.AiVideoDetection.CopyleaksAiVideoDetectionResponseModel}. Problems found after
     * download (length, size, resolution, frame rate, codec) are reported to the webhook, not thrown.
     * https://docs.copyleaks.com/reference/actions/ai-video-detector/submit
     */
    public void submit(CopyleaksAuthToken authToken, String scanId, CopyleaksAiVideoDetectionRequestModel submission)
            throws AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException,
            ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        executor.send("POST",
                Consts.API_SERVER_URI + "/v1/ai-video-detector/" + CopyleaksRequestExecutor.pathSegment(scanId) + "/submit",
                CopyleaksRequestExecutor.jsonBody(gson, submission), "application/json", authToken);
    }
}
