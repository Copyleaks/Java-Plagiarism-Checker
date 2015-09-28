package Models.Responses;

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
