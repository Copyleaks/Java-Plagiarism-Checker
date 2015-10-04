package copyleaks.sdk.api;

import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.models.LoginToken;
import copyleaks.sdk.api.models.ResultRecord;
import copyleaks.sdk.api.models.responses.BadResponse;
import copyleaks.sdk.api.models.responses.CheckStatusResponse;

public class ScannerProcess {
	public UUID PID;

	public UUID getPID() {
		return PID;
	}

	private void setPID(UUID processId) {
		PID = processId;
	}

	private LoginToken SecurityToken;

	LoginToken getSecurityToken() {
		return SecurityToken;
	}

	private void setSecurityToken(LoginToken securityToken) {
		SecurityToken = securityToken;
	}

	public ScannerProcess(LoginToken authorizationToken, UUID processId) {
		setPID(processId);
		setSecurityToken(authorizationToken);
	}

	/// <summary>
	/// Checks if the operation is completed.
	/// </summary>
	/// <returns>Return True in case that the operation on is finished by the
	/// server</returns>
	/// <exception cref="UnauthorizedAccessException"></exception>
	public Boolean IsCompleted() throws Exception
	{
		this.SecurityToken.Validate(); // may throw an UnauthorizedAccessException.

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(String.format(Resources.ServiceEntryPoint + "%1$s/detector/%2$s/status", Resources.ServiceVersion, getPID()));
		request.setHeader("Accept", HttpContentTypes.Json);
		request.setHeader("Content-Type", HttpContentTypes.Json);
		request.setHeader("User-Agent", Resources.USER_AGENT);
		request.addHeader("Authorization", String.format("%1$s %2$s", "Bearer", this.SecurityToken.getTemporarySecurityCode()));
		Gson gson = new GsonBuilder().create();
		HttpResponse msg = client.execute(request);
		if (msg.getStatusLine().getStatusCode() != 200)
			{
				String errorResponse = msg.toString();
				BadResponse error = gson.fromJson(errorResponse, BadResponse.class);
				if (error == null)
					throw new JSONException("Unable to process server response.");
				else
					throw new CommandFailedException(error.Message, msg);
			}
		HttpEntity entity = msg.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		CheckStatusResponse response = gson.fromJson(json, CheckStatusResponse.class);
		return new String(response.getStatus()).equals("Finished") ;
	}
	/// <summary>
	/// Get the scanning resutls from server.
	/// </summary>
	/// <returns>Scanning results</returns>
	/// <exception cref="UnauthorizedAccessException"></exception>
	public ResultRecord[] GetResults() throws Exception {
		this.SecurityToken.Validate(); // may throw an
										// UnauthorizedAccessException.

		HttpClient client = HttpClientBuilder.create().build();
		Gson gson = new GsonBuilder().create();

		HttpGet request = new HttpGet(String.format(Resources.ServiceEntryPoint + "%1$s/detector/%2$s/result", Resources.ServiceVersion,
				getPID()));
		request.setHeader("Accept", HttpContentTypes.Json);
		request.setHeader("Content-Type", HttpContentTypes.Json);
		request.setHeader("User-Agent", Resources.USER_AGENT);
		request.addHeader("Authorization", String.format("%1$s %2$s", "Bearer", this.SecurityToken.getTemporarySecurityCode()));
		HttpResponse msg = client.execute(request);
		if (msg.getStatusLine().getStatusCode() != 200) {
			String errorResponse = msg.toString();
			BadResponse error = gson.fromJson(errorResponse, BadResponse.class);
			if (error == null)
				throw new JSONException("Unable to process server response.");
			else
				throw new CommandFailedException(error.Message, msg);
		}

		HttpEntity entity = msg.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		return gson.fromJson(json, ResultRecord[].class);
	}
	/// <summary>
	/// Delete finished process
	/// </summary>
	/// <exception cref="UnauthorizedAccessException"></exception>
	public void Delete() throws Exception {
		this.SecurityToken.Validate(); // may throw an
										// UnauthorizedAccessException.
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(String.format("detector/%1$s/delete", this.PID));
		request.setHeader("Accept", HttpContentTypes.Json);
		request.setHeader("User-Agent", Resources.USER_AGENT);
		HttpResponse msg = client.execute(request);
		if (msg.getStatusLine().getStatusCode() != 200)
			throw new CommandFailedException(msg.getStatusLine().toString(), msg);
	}
}
