package copyleaks.sdk.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import copyleaks.sdk.api.helpers.HttpURLConnection.CopyleaksClient;
import copyleaks.sdk.api.helpers.HttpURLConnection.HttpURLConnectionHelper;
import copyleaks.sdk.api.RequestMethod;
import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.exceptions.SecurityTokenException;
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
	public static LoginToken Login(String username, String apiKey) throws IOException, CommandFailedException
	{
		LoginToken loginToken;
		String json;
		Gson gson = new GsonBuilder().create();

		// Open connection to Copyleaks and set properties
		URL url = new URL(Resources.SERVICE_URI + Resources.LOGIN_PAGE);
		HttpURLConnection conn = null;
		
		try
		{
			conn = CopyleaksClient.getClient(url, RequestMethod.POST, HttpContentTypes.UrlEncoded, HttpContentTypes.Json);
			
			// Add parameters
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("username", username));
			urlParameters.add(new BasicNameValuePair("apiKey", apiKey));
			CopyleaksClient.HandleString.attach(conn, HttpURLConnectionHelper.getQuery(urlParameters));
			// Get return message
			if (conn.getResponseCode() != 200)
			{
				String errorResponse = conn.getResponseMessage();
				BadLoginResponse response;
				switch (conn.getResponseCode())
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
					throw new RuntimeException("Unable to process server response.");
				else
					throw new CommandFailedException(conn.getResponseMessage(), conn.getResponseCode());
			}

			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream());)
			{
				json = HttpURLConnectionHelper.convertStreamToString(inputStream);
			}
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}

		loginToken = gson.fromJson(json, LoginToken.class);
		if (json == null || json.isEmpty())
			throw new RuntimeException("This request could not be processed.");

		if (loginToken == null)
			throw new RuntimeException("Unable to process server response.");
		return loginToken;
	}

	/**
	 * Get the user credits balance
	 * 
	 * @param token
	 *            Token for the server to identify the caller.
	 * @return The current credit balance. 
	 * @throws CommandFailedException 
	 * @throws Exception
	 */
	public static int getCreditBalance(LoginToken token) 
		throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(token);
		
		Gson gson = new GsonBuilder().create();
		String json;
		URL url;
		HttpURLConnection conn = null;
		try
		{
			url = new URL(
					String.format("%1$s%2$s/account/count-credits", Resources.SERVICE_URI, Resources.ServiceVersion));
			conn = CopyleaksClient.getClient(url, token, RequestMethod.GET, HttpContentTypes.Json, HttpContentTypes.Json);
			if (conn.getResponseCode() != 200)
			{
				String errorResponse = Integer.toString(conn.getResponseCode());
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

		if (json == null || json.isEmpty())
			throw new RuntimeException("Unable to process server response.");

		CountCreditsResponse res = gson.fromJson(json, CountCreditsResponse.class);

		return res.getAmount();
	}
}
