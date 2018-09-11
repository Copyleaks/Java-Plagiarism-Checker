/********************************************************************************
 The MIT License(MIT)
 
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
********************************************************************************/

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
		Gson gson = new GsonBuilder().setDateFormat(Settings.DateFormat).create();

		// Open connection to Copyleaks and set properties
		URL url = new URL(String.format("%1$s/%2$s/account/login-api", Settings.ServiceEntryPoint, Settings.ServiceVersion));
		HttpURLConnection conn = null;

		try
		{
			conn = CopyleaksClient.getClient(url, null, RequestMethod.POST, HttpContentTypes.Json,
					HttpContentTypes.Json);

			// Add parameters
			Map<String, String> dic = new HashMap<String, String>();
			dic.put("email", email);
			dic.put("apiKey", apiKey);
			
			String body = gson.toJson(dic);

			CopyleaksClient.HandleString.attach(conn, body);

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
	public static int getCreditBalance(LoginToken token, eProduct product) throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(token);

		Gson gson = new GsonBuilder().create();
		String json;
		URL url;
		HttpURLConnection conn = null;
		try
		{
			url = new URL(String.format("%1$s/%2$s/%3$s/count-credits", 
					Settings.ServiceEntryPoint,
					Settings.ServiceVersion, 
					productToWalletName(product)));
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
	
	
	private static String productToWalletName(eProduct product)
	{
		switch(product)
		{
			case Academic:
				return Settings.AcademicServicePage;
			case Businesses:
				return Settings.BusinessesServicePage;
			case Websites:
				return Settings.WebsitesServicePage;
			default:
				throw new RuntimeException("Unknown product type.");
		}
	}
}
