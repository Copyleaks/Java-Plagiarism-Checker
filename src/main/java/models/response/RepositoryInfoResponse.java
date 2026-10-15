package models.response;

/** Information about a Private Cloud Hub repository. */
public class RepositoryInfoResponse {

    private String id;

    private String name;

    private String description;

    private int permission;

    private int status;

    private long maxCredits;

    private long currentCredits;

    private int maskingPolicy;

    private String creationTime;

    /** Repository ID. */
    public String getId() {
        return id;
    }

    /** Repository name. */
    public String getName() {
        return name;
    }

    /** Repository description. */
    public String getDescription() {
        return description;
    }

    /** Your role in the repository: 1 Viewer, 2 Contributor, 3 Admin, 4 Super Admin. */
    public int getPermission() {
        return permission;
    }

    /** Repository status: 0 Running, 1 Pending For Deletion, 2 Error, 3 Updating, 4 Payment Required. */
    public int getStatus() {
        return status;
    }

    /** Maximum credits of the repository. */
    public long getMaxCredits() {
        return maxCredits;
    }

    /** Current credits of the repository. */
    public long getCurrentCredits() {
        return currentCredits;
    }

    /** Masking policy: 0 no masking, 1 mask other users' documents, 2 mask all documents. */
    public int getMaskingPolicy() {
        return maskingPolicy;
    }

    /** When the repository was created. */
    public String getCreationTime() {
        return creationTime;
    }
}
