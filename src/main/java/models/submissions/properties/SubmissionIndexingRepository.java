package models.submissions.properties;

public class SubmissionIndexingRepository extends SubmissionRepository {

    private SubmissionMaskingPolicy maskingPolicy;

    public SubmissionMaskingPolicy getMaskingPolicy() {
        return maskingPolicy;
    }

    public void setMaskingPolicy(SubmissionMaskingPolicy maskingPolicy) {
        this.maskingPolicy = maskingPolicy;
    }
    
}
