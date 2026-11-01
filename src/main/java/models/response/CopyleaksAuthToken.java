package models.response;

import com.google.gson.annotations.SerializedName;

public class CopyleaksAuthToken {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName(".issued")
    private String issued;
    @SerializedName(".expires")
    private String expires;

    public CopyleaksAuthToken(String accessToken, String issued, String expires) {
        this.accessToken = accessToken;
        this.issued = issued;
        this.expires = expires;
    }

    public CopyleaksAuthToken(CopyleaksAuthToken authToken) {
        this.accessToken = authToken.accessToken;
        this.issued = authToken.issued;
        this.expires = authToken.expires;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
