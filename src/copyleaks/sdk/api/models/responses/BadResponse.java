package copyleaks.sdk.api.models.responses;

public class BadResponse {

	public String Message;

	public String getMessage() {
		return Message;
	}
	
	@Override
	public String toString()
	{
		return this.getMessage();
	}
}
