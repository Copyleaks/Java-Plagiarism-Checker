package copyleaks.sdk.api.models.responses;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ProcessInList
{
	private UUID ProcessId; 	

	private Date CreationTimeUTC; 	

	private String Status;

	private HashMap<String, String> CustomFields;
	
	public UUID getProcessId()
	{
		return ProcessId;
	}

	public Date getCreationTimeUTC()
	{
		return CreationTimeUTC;
	}

	public String getStatus()
	{
		return Status;
	}
	
	public HashMap<String, String> getCustomFields()
	{
		return CustomFields;
	}
}
