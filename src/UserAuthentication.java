import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

import Exceptions.CommandFailedException;
import Models.*;
import Models.Responses.*;

public class UserAuthentication{
	/// <summary>
	/// Log-in into copyleaks authentication system.
	/// </summary>
	/// <param name="username">User name</param>
	/// <param name="apiKey">Password</param>
	/// <returns>Login Token to use while accessing resources.</returns>
	/// <exception cref="ArgumentException">Occur when the username and\or
	/// password is empty</exception>
	/// <exception cref="JsonException">ALON</exception>
	public static LoginToken Login(String username, String apiKey) throws ClientProtocolException, IOException, JSONException, CommandFailedException {
		LoginToken loginToken;
		HttpClient client = HttpClientBuilder.create().build();
		Gson gson = new GsonBuilder().create();
		HttpPost request = new HttpPost(Resources.SERVICE_URI + Resources.LOGIN_PAGE);
		request.setHeader("Accept", Content_Type.UrlEncoded);
		request.setHeader("User-Agent", Resources.USER_AGENT);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("apiKey", apiKey));
		
		request.setEntity(new UrlEncodedFormEntity(urlParameters));
		HttpResponse msg = client.execute(request);
		if (msg.getStatusLine().getStatusCode() != 200)
		{
			String errorResponse = msg.getStatusLine().toString();
			BadLoginResponse response = gson.fromJson(errorResponse, BadLoginResponse.class);
			if (response == null)
				throw new JSONException("Unable to process server response.");
			else
				throw new CommandFailedException(msg.getStatusLine().toString(),msg);
		}
		HttpEntity entity = msg.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");

		if (StringIsNullOrEmpty(json))
			throw new JSONException("This request could not be processed.");
		loginToken = gson.fromJson(json, LoginToken.class);
		if (loginToken == null)
			throw new JSONException("Unable to process server response.");

		return loginToken;
	}

	public static int CountCredits(LoginToken token) throws Exception {
		token.Validate();
		Gson gson = new GsonBuilder().create();

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(String.format("%1$s/account/count-credits", Resources.ServiceVersion));
		request.setHeader("Accept", Content_Type.Json);
		request.setHeader("User-Agent", Resources.USER_AGENT);
		HttpResponse msg = client.execute(request);
		if (msg.getStatusLine().getStatusCode() != 200) {
			String errorResponse = msg.getStatusLine().toString();
			BadLoginResponse response = gson.fromJson(errorResponse, BadLoginResponse.class);
			if (response == null)
				throw new JSONException("Unable to process server response.");
			else
				throw new CommandFailedException(response.Message, msg);
		}

		HttpEntity entity = msg.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		if (StringIsNullOrEmpty(json))
			throw new JSONException("Unable to process server response.");

		CountCreditsResponse res = gson.fromJson(json, CountCreditsResponse.class);

		return res.getAmount();
	}

	public static boolean StringIsNullOrEmpty(String string) {
		if (string == null || string == "") {
			return true;
		} else {
			return false;
		}
	}
}
