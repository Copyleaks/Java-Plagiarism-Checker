package copyleaks.sdk.api.exceptions;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import copyleaks.sdk.api.helpers.HttpURLConnection.HttpURLConnectionHelper;
import copyleaks.sdk.api.models.responses.BadResponse;

public class CommandFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	public final static short UNDEFINED_COPYLEAKS_ERROR_CODE = 9999;
	public final static int UNDEFINED_HTTP_ERROR_CODE = 9999;

	private Integer HttpErrorCode;

	public Integer getHttpErrorCode()
	{
		return HttpErrorCode;
	}

	public boolean IsManagedErrorCode()
	{
		return this.getCopyleaksErrorCode() == UNDEFINED_COPYLEAKS_ERROR_CODE;
	}
	
	private void setHttpErrorCode(Integer errorCode)
	{
		HttpErrorCode = errorCode;
	}

	private short CopyleaksErrorCode;

	public short getCopyleaksErrorCode()
	{
		return CopyleaksErrorCode;
	}

	private void setCopyleaksErrorCode(short errorCode)
	{
		CopyleaksErrorCode = errorCode;
	}

	public CommandFailedException(HttpURLConnection conn)
	{
		super(GetMessage(conn));

		try
		{
			this.setHttpErrorCode(conn.getResponseCode());
		}
		catch (IOException e)
		{
			this.setHttpErrorCode(UNDEFINED_HTTP_ERROR_CODE);
		}

		this.setCopyleaksErrorCode(GetCopyleaksErrorCode(conn));
	}

	private static String GetMessage(HttpURLConnection conn)
	{
		Gson gson = new GsonBuilder().create();
		String errorResponse;
		try (InputStream inputStream = new BufferedInputStream(conn.getErrorStream()))
		{
			errorResponse = HttpURLConnectionHelper.convertStreamToString(inputStream);
		}
		catch (IOException e)
		{
			return e.getMessage();
		}

		BadResponse model = null;
		try
		{
			model = gson.fromJson(errorResponse, BadResponse.class);
		}
		catch (JsonSyntaxException e)
		{
			if (GetCopyleaksErrorCode(conn) != UNDEFINED_COPYLEAKS_ERROR_CODE)
				return errorResponse;
		}

		if (model == null)
			return "The application has encountered an unknown error. Please try again later.";
		else
			return model.getMessage();
	}

	private static short GetCopyleaksErrorCode(HttpURLConnection conn)
	{
		final String COPYLEAKS_ERROR_CODE_HEADER_NAME = "Copyleaks-Error-Code";

		if (conn.getHeaderFields().containsKey(COPYLEAKS_ERROR_CODE_HEADER_NAME))
		{
			String value = conn.getHeaderField(COPYLEAKS_ERROR_CODE_HEADER_NAME);

			try
			{
				return Short.parseShort(value);
			}
			catch (NumberFormatException e)
			{
				// Doing nothing.
			}
		}
		return UNDEFINED_COPYLEAKS_ERROR_CODE;
	}
}
