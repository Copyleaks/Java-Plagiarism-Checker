package classes.clients;

import java.net.http.HttpClient;
import java.text.ParseException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import classes.Consts;
import helpers.CopyleaksRequestExecutor;
import models.exceptions.AuthExpiredException;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.response.CopyleaksAuthToken;
import models.response.aidetection.AIDetectionCreditsResponse;
import models.response.aidetection.AIDetectionResponse;
import models.submissions.aidetection.CopyleaksNaturalLanguageSubmissionModel;

/** AI Text Detection; use {@code Copyleaks.aiDetectionClient}. Errors: see {@link classes.Copyleaks}. */
public class AIDetectionClient {

    private final CopyleaksRequestExecutor executor;

    /** @param executor the request executor to send requests with. */
    public AIDetectionClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /**
     * Client with its own HTTP client and concurrency semaphore.
     * @deprecated Use {@code Copyleaks.aiDetectionClient}.
     */
    @Deprecated
    public AIDetectionClient(HttpClient httpClient, Semaphore semaphore) {
        this(new CopyleaksRequestExecutor(httpClient, semaphore));
    }

    /** Detects AI-written text. https://docs.copyleaks.com/reference/actions/writer-detector/check */
    public AIDetectionResponse submitNaturalLanguage(CopyleaksAuthToken authToken, String scanId, CopyleaksNaturalLanguageSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        return executor.sendJson("POST",
                Consts.API_SERVER_URI + "/v2/writer-detector/" + CopyleaksRequestExecutor.pathSegment(scanId) + "/check",
                authToken, submission, AIDetectionResponse.class);
    }

    /** AI Text Detection credit balance. https://docs.copyleaks.com/reference/actions/writer-detector/credits */
    public AIDetectionCreditsResponse getCredits(CopyleaksAuthToken authToken)
            throws AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        return executor.sendJson("GET", Consts.API_SERVER_URI + "/v2/writer-detector/credits", authToken, null,
                AIDetectionCreditsResponse.class);
    }
}
