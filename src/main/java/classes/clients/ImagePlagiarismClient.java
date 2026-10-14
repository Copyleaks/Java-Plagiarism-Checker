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
import helpers.MultipartBody;
import models.exceptions.AuthExpiredException;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.request.ImagePlagiarism.CopyleaksImagePlagiarismRequestModel;
import models.response.CopyleaksAuthToken;
import models.response.ImagePlagiarism.CopyleaksImagePlagiarismResponseModel;

/**
 * Copyleaks Image Plagiarism Detection. Use the shared instance {@code Copyleaks.imagePlagiarismClient}.
 */
public class ImagePlagiarismClient {

    private final CopyleaksRequestExecutor executor;
    private static final Gson gson = new Gson();

    /**
     * @param executor the request executor to send requests with.
     */
    public ImagePlagiarismClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /**
     * Searches the web for copies of an image. The image is uploaded as {@code multipart/form-data}
     * and the matches are returned right away.
     * For more info: https://docs.copyleaks.com/reference/actions/image-plagiarism-detector/check
     *
     * @param authToken  Copyleaks authentication token
     * @param scanId     Attach your own scan Id
     * @param submission The image bytes, its file name and the options.
     * @return the scanned image details and the matches found.
     * @throws AuthExpiredException If the token has expired.
     * @throws UnderMaintenanceException If Copyleaks servers are unavailable for maintenance.
     * @throws RateLimitException If too many requests were sent.
     * @throws CommandException If the server rejected the request.
     * @throws ExecutionException If the HTTP request fails or times out.
     * @throws InterruptedException If the thread is interrupted while waiting for the response.
     */
    public CopyleaksImagePlagiarismResponseModel check(CopyleaksAuthToken authToken, String scanId,
            CopyleaksImagePlagiarismRequestModel submission)
            throws AuthExpiredException, UnderMaintenanceException, RateLimitException, CommandException,
            ExecutionException, InterruptedException {
        Objects.requireNonNull(authToken, "token is null");
        Objects.requireNonNull(scanId, "scanId is null");
        Objects.requireNonNull(submission, "submission is null");
        Objects.requireNonNull(submission.getImage(), "image is null");
        Objects.requireNonNull(submission.getFilename(), "filename is null");
        CopyleaksClientHelper.verifyAuthToken(authToken);

        MultipartBody form = new MultipartBody()
                .addFile("image", submission.getFilename(), MultipartBody.imageContentType(submission.getFilename()),
                        submission.getImage())
                .addField("filename", submission.getFilename())
                .addField("sandbox", Boolean.toString(submission.isSandbox()));
        if (submission.getDeveloperPayload() != null) {
            form.addField("developerPayload", submission.getDeveloperPayload());
        }
        String body = executor.send("POST",
                Consts.API_SERVER_URI + "/v1/image-plagiarism-detector/" + CopyleaksRequestExecutor.pathSegment(scanId) + "/check",
                form.build(), form.contentType(), authToken);
        return gson.fromJson(body, CopyleaksImagePlagiarismResponseModel.class);
    }
}
