package copyleaks.sdk.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.exceptions.SecurityTokenException;
import copyleaks.sdk.api.helpers.HttpURLConnection.CopyleaksClient;
import copyleaks.sdk.api.helpers.HttpURLConnection.HttpURLConnectionHelper;
import copyleaks.sdk.api.models.LoginToken;
import copyleaks.sdk.api.models.ResultRecord;
import copyleaks.sdk.api.models.responses.BadLoginResponse;
import copyleaks.sdk.api.models.responses.ProcessInList;

public class CopyleaksAccount
{
	private LoginToken Token;

	private LoginToken getToken()
	{
		return Token;
	}

	private void setToken(LoginToken token)
	{
		Token = token;
	}

	public CopyleaksAccount(String username, String APIKey) 
			throws IOException, CommandFailedException
	{
		this.setToken(UserAuthentication.Login(username, APIKey));
		// This security token can be use multiple times, until it will be
		// expired (48 hours).
	}
	
	public int getCredits() 
			throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getToken());

		return UserAuthentication.getCreditBalance(this.getToken());
	}

	public CopyleaksProcess[] getProcesses()
			throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getToken());
		
		URL url;
		HttpsURLConnection conn = null;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		String json;
		try
		{
			url = new URL(Resources.ServiceEntryPoint + String.format("%1$s/detector/list", Resources.ServiceVersion));
			conn = CopyleaksClient.getClient(url, this.getToken(), RequestMethod.GET, 
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
		
		ProcessInList[] response = gson.fromJson(json, ProcessInList[].class);
		CopyleaksProcess[] processes = new CopyleaksProcess[response.length];
		for (int i= 0; i < response.length; ++i)
		{
			processes[i]= new CopyleaksProcess(this.getToken(), response[i]);
		}
		return processes;
	}

	public ResultRecord[] ScanLocalOcrFile(String filePath) 
			throws FileNotFoundException, CommandFailedException 
	{
		File file = new File(filePath);
		
		if (!file.exists())
			throw new FileNotFoundException();

		Detector detector = new Detector(this.Token);
		CopyleaksProcess process = detector.CreateByOCR(file);
		while (!process.IsCompleted())
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
			}
		}

		// Getting results.
		return process.GetResults();
	}

	public ResultRecord[] ScanLocalTextualFile(String filePath)
			throws SecurityTokenException, CommandFailedException, FileNotFoundException
	{
		File file = new File(filePath);
		if (!file.exists())
			throw new FileNotFoundException();

		Detector detector = new Detector(this.Token);
		CopyleaksProcess process = detector.CreateByFile(file);
		while (!process.IsCompleted())
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
			}
		}

		// Getting results.
		return process.GetResults();
	}

	public ResultRecord[] ScanUrl(String inputUrl) 
			throws SecurityTokenException, CommandFailedException, URISyntaxException
	{
		URI url = new URI(inputUrl);
		// Create a new process on server.
		Detector detector = new Detector(this.Token);
		CopyleaksProcess process = detector.CreateByUrl(url);

		// Waiting to process to be finished.
		while (!process.IsCompleted())
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
			}
		}

		// Getting results.
		return process.GetResults();
	}
}