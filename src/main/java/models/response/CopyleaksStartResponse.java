package models.response;

public class CopyleaksStartResponse {
    private SuccessScansResponse[] success;
    private FailedScansResponse[] failed;

    public SuccessScansResponse[] getSuccess() {
        return success;
    }

    public FailedScansResponse[] getFailed() {
        return failed;
    }
}
