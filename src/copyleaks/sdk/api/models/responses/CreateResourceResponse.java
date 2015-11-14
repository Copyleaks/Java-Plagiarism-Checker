package copyleaks.sdk.api.models.responses;

import java.util.Date;
import java.util.UUID;

public class CreateResourceResponse {
	private UUID ProcessId;
	public UUID getProcessId() {
		return ProcessId;
	}
	
	private Date CreationTimeUTC;
	public Date getCreationTimeUTC()
	{
		return CreationTimeUTC;
	}
}
