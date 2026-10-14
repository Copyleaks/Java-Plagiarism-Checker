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

package models.response;

/**
 * Information about a Private Cloud Hub repository.
 */
public class RepositoryInfoResponse {

    /** The repository ID. */
    private String id;

    /** The repository name. */
    private String name;

    /** The repository description. */
    private String description;

    /** Your role in the repository: 1 Viewer, 2 Contributor, 3 Admin, 4 Super Admin. */
    private int permission;

    /** The repository status: 0 Running, 1 Pending For Deletion, 2 Error, 3 Updating, 4 Payment Required. */
    private int status;

    /** The maximum credits of the repository. */
    private long maxCredits;

    /** The current credits of the repository. */
    private long currentCredits;

    /** The masking policy: 0 no masking, 1 mask other users' documents, 2 mask all documents. */
    private int maskingPolicy;

    /** When the repository was created. */
    private String creationTime;

    /** @return the repository ID. */
    public String getId() {
        return id;
    }

    /** @return the repository name. */
    public String getName() {
        return name;
    }

    /** @return the repository description. */
    public String getDescription() {
        return description;
    }

    /** @return your role in the repository: 1 Viewer, 2 Contributor, 3 Admin, 4 Super Admin. */
    public int getPermission() {
        return permission;
    }

    /** @return the repository status: 0 Running, 1 Pending For Deletion, 2 Error, 3 Updating, 4 Payment Required. */
    public int getStatus() {
        return status;
    }

    /** @return the maximum credits of the repository. */
    public long getMaxCredits() {
        return maxCredits;
    }

    /** @return the current credits of the repository. */
    public long getCurrentCredits() {
        return currentCredits;
    }

    /** @return the masking policy: 0 no masking, 1 mask other users' documents, 2 mask all documents. */
    public int getMaskingPolicy() {
        return maskingPolicy;
    }

    /** @return when the repository was created. */
    public String getCreationTime() {
        return creationTime;
    }
}
