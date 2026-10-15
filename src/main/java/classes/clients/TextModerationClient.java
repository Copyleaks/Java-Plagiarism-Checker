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
import models.request.TextModeration.CopyleaksTextModerationRequest;
import models.response.CopyleaksAuthToken;
import models.response.textModeration.CopyleaksTextModerationResponseModel;

/** Text Moderation; use {@code Copyleaks.textModerationClient}. Errors: see {@link classes.Copyleaks}. */
public class TextModerationClient {

    private final CopyleaksRequestExecutor executor;
    private static final Gson gson = new Gson();

    /** @param executor the request executor to send requests with. */
    public TextModerationClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /**
     * Client with its own HTTP client and concurrency semaphore.
     * @deprecated Use {@code Copyleaks.textModerationClient}.
     */
    @Deprecated
    public TextModerationClient(HttpClient httpClient, Semaphore semaphore) {
        this(new CopyleaksRequestExecutor(httpClient, semaphore));
    }

    /** Checks a text for unsafe content. https://docs.copyleaks.com/reference/actions/text-moderation/check */
    public CopyleaksTextModerationResponseModel submitText(CopyleaksAuthToken authToken, String scanId,
            CopyleaksTextModerationRequest submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException,
            ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        String body = executor.send("POST",
                Consts.API_SERVER_URI + "/v1/text-moderation/" + CopyleaksRequestExecutor.pathSegment(scanId) + "/check",
                CopyleaksRequestExecutor.jsonBody(gson, submission), "application/json", authToken);
        return gson.fromJson(body, CopyleaksTextModerationResponseModel.class);
    }
}
