package copyleaks.sdk.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import copyleaks.sdk.api.helpers.HttpURLConnection.CopyleaksClient;
import copyleaks.sdk.api.helpers.HttpURLConnection.HttpURLConnectionHelper;
import copyleaks.sdk.api.RequestMethod;
import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.exceptions.SecurityTokenException;
import copyleaks.sdk.api.models.LoginToken;
import copyleaks.sdk.api.models.ResultRecord;
import copyleaks.sdk.api.models.responses.BadLoginResponse;
import copyleaks.sdk.api.models.responses.CheckStatusResponse;

public class ScannerProcess
{
	public UUID PID;

	public UUID getPID()
	{
		return PID;
	}

	private void setPID(UUID processId)
	{
		PID = processId;
	}

	private LoginToken SecurityToken;

	LoginToken getSecurityToken()
	{
		return SecurityToken;
	}

	private void setSecurityToken(LoginToken securityToken)
	{
		SecurityToken = securityToken;
	}

	public ScannerProcess(LoginToken authorizationToken, UUID processId)
	{
		setPID(processId);
		setSecurityToken(authorizationToken);
	}

	/// <summary>
	/// Checks if the operation is completed.
	/// </summary>
	/// <returns>Return True in case that the operation on is finished by the
	/// server</returns>
	/// <exception cref="UnauthorizedAccessException"></exception>
	public Boolean IsCompleted() throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getSecurityToken());

		URL url;
		HttpsURLConnection conn = null;
		Gson gson = new GsonBuilder().create();
		String json;
		try
		{
			url = new URL(String.format(Resources.ServiceEntryPoint + "%1$s/detector/%2$s/status",
					Resources.ServiceVersion, getPID()));
			conn = CopyleaksClient.getClient(url, this.getSecurityToken(), RequestMethod.GET, 
					HttpContentTypes.Json, HttpContentTypes.TextPlain);
			if (conn.getResponseCode() != 200)
			{
				String errorResponse;
				try (InputStream inputStream = new BufferedInputStream(conn.getErrorStream()))
				{
					errorResponse = HttpURLConnectionHelper.convertStreamToString(inputStream);
				}
				BadLoginResponse response = gson.fromJson(errorResponse, BadLoginResponse.class);
				if (response == null)
					throw new RuntimeException("Unable to process server response.");
				else
					throw new CommandFailedException(response.getMessage(), conn.getResponseCode());
			}

			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				json = HttpURLConnectionHelper.convertStreamToString(inputStream);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}
		CheckStatusResponse response = gson.fromJson(json, CheckStatusResponse.class);
		return new String(response.getStatus()).equals("Finished");
	}

	/// <summary>
	/// Get the scanning resutls from server.
	/// </summary>
	/// <returns>Scanning results</returns>
	/// <exception cref="UnauthorizedAccessException"></exception>
	public ResultRecord[] GetResults() throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getSecurityToken());

		String json;
		URL url;
		HttpsURLConnection conn = null;
		Gson gson = new GsonBuilder().create();
		try
		{
			url = new URL(String.format(Resources.ServiceEntryPoint + "%1$s/detector/%2$s/result",
					Resources.ServiceVersion, getPID()));
			conn = CopyleaksClient.getClient(url, this.getSecurityToken(), RequestMethod.GET, HttpContentTypes.Json, HttpContentTypes.Json);
			if (conn.getResponseCode() != 200)
			{
				String errorResponse;
				try (InputStream inputStream = new BufferedInputStream(conn.getErrorStream()))
				{
					errorResponse = HttpURLConnectionHelper.convertStreamToString(inputStream);
				}
				BadLoginResponse response = gson.fromJson(errorResponse, BadLoginResponse.class);
				if (response == null)
					throw new RuntimeException("Unable to process server response.");
				else
					throw new CommandFailedException(response.getMessage(), conn.getResponseCode());
			}

			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				json = HttpURLConnectionHelper.convertStreamToString(inputStream);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}
		// HttpClient client = HttpClientBuilder.create().build();
		// Gson gson = new GsonBuilder().create();
		//
		// HttpGet request = new
		// HttpGet(String.format(Resources.ServiceEntryPoint +
		// "%1$s/detector/%2$s/result", Resources.ServiceVersion,
		// getPID()));
		// request.setHeader("Accept", HttpContentTypes.Json);
		// request.setHeader("Content-Type", HttpContentTypes.Json);
		// request.setHeader("User-Agent", Resources.USER_AGENT);
		// request.addHeader("Authorization", String.format("%1$s %2$s",
		// "Bearer", this.SecurityToken.getTemporarySecurityCode()));
		// HttpResponse msg = client.execute(request);
		// if (msg.getStatusLine().getStatusCode() != 200) {
		// String errorResponse = msg.toString();
		// BadResponse error = gson.fromJson(errorResponse, BadResponse.class);
		// if (error == null)
		// throw new RuntimeException("Unable to process server response.");
		// else
		// throw new CommandFailedException(error.Message, msg);
		// }
		//
		// HttpEntity entity = msg.getEntity();
		// String json = EntityUtils.toString(entity, "UTF-8");
		return gson.fromJson(json, ResultRecord[].class);
	}

	/// Delete finished process
	public void Delete() throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getSecurityToken());

		URL url;
		HttpsURLConnection conn = null;
		Gson gson = new GsonBuilder().create();
		try
		{
			url = new URL(String.format("detector/%1$s/delete", this.PID));
			conn = CopyleaksClient.getClient(url, this.getSecurityToken(), RequestMethod.POST, HttpContentTypes.Json, HttpContentTypes.Json);
			if (conn.getResponseCode() != 200)
			{
				String errorResponse = Integer.toString(conn.getResponseCode());
				BadLoginResponse response = gson.fromJson(errorResponse, BadLoginResponse.class);
				if (response == null)
					throw new RuntimeException("Unable to process server response.");
				else
					throw new CommandFailedException(response.getMessage(), conn.getResponseCode());
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}
		// HttpClient client = HttpClientBuilder.create().build();
		// HttpPost request = new HttpPost(String.format("detector/%1$s/delete",
		// this.PID));
		// request.setHeader("Accept", HttpContentTypes.Json);
		// request.setHeader("User-Agent", Resources.USER_AGENT);
		// HttpResponse msg = client.execute(request);
		// if (msg.getStatusLine().getStatusCode() != 200)
		// throw new CommandFailedException(msg.getStatusLine().toString(),
		// msg);
	}
}
