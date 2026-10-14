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

/**
 * Copyleaks AI Video Detection. Use the shared instance {@code Copyleaks.aiVideoDetectionClient}.
 * <p>
 * Detection is asynchronous: {@link #submit} returns once the video URL is accepted, and the result is
 * sent to the webhook later. Parse the webhook body into
 * {@link models.response.AiVideoDetection.CopyleaksAiVideoDetectionResponseModel}.
 */
public class AiVideoDetectionClient {

    private final CopyleaksRequestExecutor executor;
    private static final Gson gson = new Gson();

    /**
     * @param executor the request executor to send requests with.
     */
    public AiVideoDetectionClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /**
     * Submits a video URL for AI Video Detection. The result is sent to {@code webhooks.url} when processing ends.
     * Video problems found after download (length, size, resolution, frame rate, codec) are reported to the webhook.
     * For more info: https://docs.copyleaks.com/reference/actions/ai-video-detector/submit
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission The video URL, the model and the webhook.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
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
