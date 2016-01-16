package copyleaks.sdk.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import copyleaks.sdk.api.helpers.HttpURLConnection.CopyleaksClient;
import copyleaks.sdk.api.helpers.HttpURLConnection.HttpURLConnectionHelper;
import copyleaks.sdk.api.RequestMethod;
import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.exceptions.SecurityTokenException;
import copyleaks.sdk.api.models.*;
import copyleaks.sdk.api.models.responses.*;

public class UserAuthentication
{
	/**
	 * Login to Copyleaks authentication server.
	 * 
	 * @param email
	 *            Account email
	 * @param apiKey
	 *            Copyleaks API key
	 * @return Login Token to use while accessing the API services
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occurred
	 *             during the processing of a command
	 * @throws IOException
	 *             This exception is thrown if an exception situation occurred
	 *             during the processing of an I/O operation.
	 */
	public static LoginToken Login(String email, String apiKey) throws IOException, CommandFailedException
	{
		LoginToken loginToken;
		String json;
		Gson gson = new GsonBuilder().create();

		// Open connection to Copyleaks and set properties
		URL url = new URL(String.format("%1$s/%2$s/account/login-api", Settings.ServiceEntryPoint, Settings.ServiceVersion));
		HttpURLConnection conn = null;

		try
		{
			conn = CopyleaksClient.getClient(url, RequestMethod.POST, HttpContentTypes.UrlEncoded,
					HttpContentTypes.Json);

			// Add parameters
			Map<String, String> dic = new HashMap<String, String>();
			dic.put("email", email);
			dic.put("apiKey", apiKey);
			CopyleaksClient.HandleString.attach(conn, HttpURLConnectionHelper.getQuery(dic));

			if (conn.getResponseCode() != 200)
				throw new CommandFailedException(conn);
			
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
	 * Get your current credit balance
	 * 
	 * @return Login Token to use while accessing the API services
	 * @param token
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occured
	 *             during the processing of a command
	 * @throws SecurityTokenException
	 *             The login-token is undefined or expired
	 */
	public static int getCreditBalance(LoginToken token) throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(token);

		Gson gson = new GsonBuilder().create();
		String json;
		URL url;
		HttpURLConnection conn = null;
		try
		{
			url = new URL(String.format("%1$s/%2$s/account/count-credits", Settings.ServiceEntryPoint,
					Settings.ServiceVersion));
			conn = CopyleaksClient.getClient(url, token, RequestMethod.GET, HttpContentTypes.Json,
					HttpContentTypes.Json);
			if (conn.getResponseCode() != 200)
				throw new CommandFailedException(conn);

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
