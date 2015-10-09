package copyleaks.sdk.api.models.responses;

public class BadLoginResponse {
	
	public BadLoginResponse()
	{
		
	}
	
	public BadLoginResponse(String msg)
	{
		this.setMessage(msg);
	}
	
	private String Message;
	public String getMessage() {
		return Message;
	}
	private void setMessage(String msg)
	{
		this.Message = msg;
	}
}
