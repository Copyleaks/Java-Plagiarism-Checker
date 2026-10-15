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
    private static final Gson gson = new Gson();

    public AIDetectionClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /** @deprecated Use {@code Copyleaks.aiDetectionClient}. */
    @Deprecated
    public AIDetectionClient(HttpClient httpClient, Semaphore semaphore) {
        this(new CopyleaksRequestExecutor(httpClient, semaphore));
    }

    /** Detects AI-written text. https://docs.copyleaks.com/reference/actions/writer-detector/check */
    public AIDetectionResponse submitNaturalLanguage(CopyleaksAuthToken authToken, String scanId, CopyleaksNaturalLanguageSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        String body = executor.send("POST",
                Consts.API_SERVER_URI + "/v2/writer-detector/" + CopyleaksRequestExecutor.pathSegment(scanId) + "/check",
                CopyleaksRequestExecutor.jsonBody(gson, submission), "application/json", authToken);
        return gson.fromJson(body, AIDetectionResponse.class);
    }

    /** AI Text Detection credit balance. https://docs.copyleaks.com/reference/actions/writer-detector/credits */
    public AIDetectionCreditsResponse getCredits(CopyleaksAuthToken authToken)
            throws AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        String body = executor.send("GET", Consts.API_SERVER_URI + "/v2/writer-detector/credits", null, null, authToken);
        return gson.fromJson(body, AIDetectionCreditsResponse.class);
    }
}
