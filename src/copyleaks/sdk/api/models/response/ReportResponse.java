package copyleaks.sdk.api.models.response;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import copyleaks.sdk.api.models.types.eReportStatus;

public class ReportResponse  implements Serializable{

	private static final long serialVersionUID = 886024608833052591L;

	@SerializedName("report")
	@Expose
	private String DownloadEndPoint;
	
	@SerializedName("status")
	@Expose
	private eReportStatus status;
}
