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

/**
 * Copyleaks Text Moderation. Use the shared instance {@code Copyleaks.textModerationClient}.
 */
public class TextModerationClient {

    private final CopyleaksRequestExecutor executor;
    private static final Gson gson = new Gson();

    /**
     * @param executor the request executor to send requests with.
     */
    public TextModerationClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /**
     * @param httpClient the HTTP client to send requests with.
     * @param semaphore  limits how many requests run at the same time.
     * @deprecated Use the shared {@code Copyleaks.textModerationClient}, or
     *             {@link #TextModerationClient(CopyleaksRequestExecutor)}.
     */
    @Deprecated
    public TextModerationClient(HttpClient httpClient, Semaphore semaphore) {
        this(new CopyleaksRequestExecutor(httpClient, semaphore));
    }

    /**
     * Moderate a text for unsafe or inappropriate content.
     * For more info: https://docs.copyleaks.com/reference/actions/text-moderation/check
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission The text and the labels to check.
     * @return the moderation result.
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
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
