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

/** Image Plagiarism Detection; use {@code Copyleaks.imagePlagiarismClient}. Errors: see {@link classes.Copyleaks}. */
public class ImagePlagiarismClient {

    private final CopyleaksRequestExecutor executor;
    private static final Gson gson = new Gson();

    /** @param executor the request executor to send requests with. */
    public ImagePlagiarismClient(CopyleaksRequestExecutor executor) {
        this.executor = Objects.requireNonNull(executor, "executor is null");
    }

    /**
     * Searches the web for copies of an image, uploaded as multipart; matches are returned right away.
     * https://docs.copyleaks.com/reference/actions/image-plagiarism-detector/check
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
