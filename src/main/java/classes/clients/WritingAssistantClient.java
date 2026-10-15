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
import models.response.writingassitant.CorrectionTypesResponse;
import models.response.writingassitant.WritingAssistantResponse;
import models.submissions.writingassistant.CopyleaksWritingAssistantSubmissionModel;

/** Grammar Checker; use {@code Copyleaks.writingAssistantClient}. Errors: see {@link classes.Copyleaks}. */
public class WritingAssistantClient {

    private final CopyleaksRequestExecutor executor;

    /** @param executor the request executor to send requests with. */
    public WritingAssistantClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /**
     * Client with its own HTTP client and concurrency semaphore.
     * @deprecated Use {@code Copyleaks.writingAssistantClient}.
     */
    @Deprecated
    public WritingAssistantClient(HttpClient httpClient, Semaphore semaphore) {
        this(new CopyleaksRequestExecutor(httpClient, semaphore));
    }

    /** Grammar and spelling corrections. https://docs.copyleaks.com/reference/actions/writing-assistant/check */
    public WritingAssistantResponse submitText(CopyleaksAuthToken authToken, String scanId, CopyleaksWritingAssistantSubmissionModel submission)
            throws ParseException, AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException, ExecutionException, InterruptedException {
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        return executor.sendJson("POST",
                Consts.API_SERVER_URI + "/v1/writing-feedback/" + CopyleaksRequestExecutor.pathSegment(scanId) + "/check",
                authToken, submission, WritingAssistantResponse.class);
    }

    /**
     * Grammar Checker correction types.
     * https://docs.copyleaks.com/reference/actions/writing-assistant/correction-types
     * @param languageCode ISO 639-1 language of the returned texts. Supported: "en".
     */
    public CorrectionTypesResponse getCorrectionTypes(CopyleaksAuthToken authToken, String languageCode) throws ExecutionException, InterruptedException, UnderMaintenanceException, RateLimitException, CommandException, ParseException, AuthExpiredException {
        Objects.requireNonNull(languageCode, "languageCode is null");
        return executor.sendJson("GET", Consts.API_SERVER_URI + "/v1/writing-feedback/correction-types/"
                + CopyleaksRequestExecutor.pathSegment(languageCode), authToken, null, CorrectionTypesResponse.class);
    }
}
