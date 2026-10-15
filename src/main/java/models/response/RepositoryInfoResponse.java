package models.response;

/** Information about a Private Cloud Hub repository. */
public class RepositoryInfoResponse {

    private String id;

    private String name;

    private String description;

    /** Your role in the repository: 1 Viewer, 2 Contributor, 3 Admin, 4 Super Admin. */
    private int permission;

    /** 0 Running, 1 Pending For Deletion, 2 Error, 3 Updating, 4 Payment Required. */
    private int status;

    private long maxCredits;

    private long currentCredits;

    /** 0 no masking, 1 mask other users' documents, 2 mask all documents. */
    private int maskingPolicy;

    private String creationTime;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPermission() {
        return permission;
    }

    public int getStatus() {
        return status;
    }

    public long getMaxCredits() {
        return maxCredits;
    }

    public long getCurrentCredits() {
        return currentCredits;
    }

    public int getMaskingPolicy() {
        return maskingPolicy;
    }

    public String getCreationTime() {
        return creationTime;
    }
}
