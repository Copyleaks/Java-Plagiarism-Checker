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

/**
 * Copyleaks AI Image Detection. Use the shared instance {@code Copyleaks.aiImageDetectionClient}.
 */
public class AiImageDetectionClient {

    private final CopyleaksRequestExecutor executor;
    private static final Gson gson = new Gson();

    /**
     * @param executor the request executor to send requests with.
     */
    public AiImageDetectionClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /**
     * @param httpClient the HTTP client to send requests with.
     * @param semaphore  limits how many requests run at the same time.
     * @deprecated Use the shared {@code Copyleaks.aiImageDetectionClient}, or
     *             {@link #AiImageDetectionClient(CopyleaksRequestExecutor)}.
     */
    @Deprecated
    public AiImageDetectionClient(HttpClient httpClient, Semaphore semaphore) {
        this(new CopyleaksRequestExecutor(httpClient, semaphore));
    }

    /**
     * Detects AI-generated content in a base64-encoded image, sent as JSON. The result is returned right away.
     * For large images prefer {@link #submitFile(CopyleaksAuthToken, String, CopyleaksAiImageDetectionFileRequestModel)},
     * which uploads the bytes without base64 encoding.
     * For more info: https://docs.copyleaks.com/reference/actions/ai-image-detector/check
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission The image and the detection options.
     * @return the detection result.
     * @throws ParseException Not thrown. Kept for source compatibility.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
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

    /**
     * Detects AI-generated content in an image uploaded as {@code multipart/form-data}. The result is returned
     * right away. This sends the image bytes without base64 encoding, which is faster for large images.
     * For more info: https://docs.copyleaks.com/reference/actions/ai-image-detector/check
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission The image bytes and the detection options.
     * @return the detection result.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
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
     * Submits an image URL for AI Image Detection. The call returns once the image is accepted, and the result
     * is sent to {@code webhooks.url} later. The webhook body has the same shape as the synchronous result,
     * so it can be parsed into {@link CopyleaksAiImageDetectionResponseModel}.
     * For more info: https://docs.copyleaks.com/reference/actions/ai-image-detector/submit
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission The image URL, the detection options and the webhook.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
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
