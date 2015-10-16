package copyleaks.sdk.api.exceptions;

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

	public CommandFailedException(String message, int statusCode)
	{
		super(String.format("Execution failed with the code %1$s:\n%2$s", statusCode, message));
		this.setErrorCode(statusCode);
		this.setDescription(message);
	}
}
