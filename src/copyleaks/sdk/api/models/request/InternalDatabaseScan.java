package copyleaks.sdk.api.models.request;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InternalDatabaseScan implements Serializable{

	private static final long serialVersionUID = 2052016720628430839L;

    @SerializedName("includeMySubmissions")
    @Expose
	private Boolean includeMySubmissions;
    @SerializedName("includeOthersSubmissions")
    @Expose
	private Boolean includeOthersSubmissions;
	
	public InternalDatabaseScan() {}
	
	/**
	 * Set which scans to compare against from Copyleaks internal database
	 * @param includeMySubmissions - Compare against your previous scan
	 * @param includeOthersSubmissions - Compare against other users previouse scans
	 */
	public InternalDatabaseScan(Boolean includeMySubmissions, Boolean includeOthersSubmissions) {
		super();
		this.includeMySubmissions = includeMySubmissions;
		this.includeOthersSubmissions = includeOthersSubmissions;
	}

	public Boolean getIncludeMySubmissions() {
		return includeMySubmissions;
	}

	public void setIncludeMySubmissions(Boolean includeMySubmissions) {
		this.includeMySubmissions = includeMySubmissions;
	}

	public Boolean getIncludeOthersSubmissions() {
		return includeOthersSubmissions;
	}

	public void setIncludeOthersSubmissions(Boolean includeOthersSubmissions) {
		this.includeOthersSubmissions = includeOthersSubmissions;
	}
	
}
