package copyleaks.sdk.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.models.*;
import copyleaks.sdk.api.models.responses.*;

/**
 * Handle user operations (such as: authentication and retrieve user account
 * information).
 *
 */
public class UserAuthentication
{
	/**
	 * Log-in into Copyleaks authentication system.
	 * 
	 * @param username
	 *            User-name to login with.
	 * @param apiKey
	 *            User secret information.
	 * @return A token to be used for accessing Copyleaks services.
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 * @throws CommandFailedException
	 */
	public static LoginToken Login(String username, String apiKey)
			throws IOException, JSONException, CommandFailedException
	{
		LoginToken loginToken;
		HttpClient client = HttpClientBuilder.create().build();
		Gson gson = new GsonBuilder().create();
		HttpPost request = new HttpPost(Resources.SERVICE_URI + Resources.LOGIN_PAGE);
		request.setHeader("Accept", HttpContentTypes.UrlEncoded);
		request.setHeader("User-Agent", Resources.USER_AGENT);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("apiKey", apiKey));

		request.setEntity(new UrlEncodedFormEntity(urlParameters));
		HttpResponse msg = client.execute(request);
		if (msg.getStatusLine().getStatusCode() != 200)
		{
			String errorResponse = msg.getStatusLine().toString();
			BadLoginResponse response;
			switch (msg.getStatusLine().getStatusCode())
			{
				case 401: // Unauthorized
					response = new BadLoginResponse(errorResponse);
					break;
				default:
					try
					{
						response = gson.fromJson(errorResponse, BadLoginResponse.class);	
					}
					catch (Exception e)
					{
						response = null;
					}
					break;
			}

			if (response == null)
				throw new JSONException("Unable to process server response.");
			else
				throw new CommandFailedException(msg.getStatusLine().toString(), msg);
		}
		HttpEntity entity = msg.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");

		if (json == null || json.isEmpty())
			throw new JSONException("This request could not be processed.");

		loginToken = gson.fromJson(json, LoginToken.class);
		if (loginToken == null)
			throw new JSONException("Unable to process server response.");

		return loginToken;
	}

	/**
	 * Get the user credits balance
	 * 
	 * @param token
	 *            Token for the server to identify the caller.
	 * @return The current credit balance.
	 * @throws Exception
	 */
	public static int getCreditBalance(LoginToken token) throws Exception
	{
		token.Validate();
		Gson gson = new GsonBuilder().create();

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(String.format("%1$s/account/count-credits", Resources.ServiceVersion));
		request.setHeader("Accept", HttpContentTypes.Json);
		request.setHeader("User-Agent", Resources.USER_AGENT);
		HttpResponse msg = client.execute(request);
		if (msg.getStatusLine().getStatusCode() != 200)
		{
			String errorResponse = msg.getStatusLine().toString();
			BadLoginResponse response = gson.fromJson(errorResponse, BadLoginResponse.class);
			if (response == null)
				throw new JSONException("Unable to process server response.");
			else
				throw new CommandFailedException(response.getMessage(), msg);
		}

		HttpEntity entity = msg.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		if (json == null || json.isEmpty())
			throw new JSONException("Unable to process server response.");

		CountCreditsResponse res = gson.fromJson(json, CountCreditsResponse.class);

		return res.getAmount();
	}
}
