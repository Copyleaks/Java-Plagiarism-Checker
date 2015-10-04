package copyleaks.sdk.api.exceptions;

import org.apache.http.HttpResponse;

public class CommandFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	public Integer ErrorCode;
	Integer getErrorCode() {
		return ErrorCode;
	}

	private void setErrorCode(Integer errorCode) {
		ErrorCode = errorCode;
	}

	String Description;
	String getDescription() {
		return Description;
	}

	private void setDescription(String description) {
		Description = description;
	}

	public CommandFailedException(HttpResponse msg)
	{
		super(String.format("HTTP Error code: %1$s", msg.getStatusLine().getStatusCode()));
		this.setErrorCode(msg.getStatusLine().getStatusCode());
	}
	public CommandFailedException(String message, HttpResponse msg)
	{
		super(String.format("Execution failed with the code %1$s:\n%2$s", msg.getStatusLine().getStatusCode(), message));
		this.ErrorCode = msg.getStatusLine().getStatusCode();
		this.setDescription(message);
	}
}
