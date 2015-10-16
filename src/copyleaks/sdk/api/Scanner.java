package copyleaks.sdk.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.exceptions.SecurityTokenException;
import copyleaks.sdk.api.models.LoginToken;
import copyleaks.sdk.api.models.ResultRecord;

public class Scanner
{
	private String Username;

	public String getUsername()
	{
		return Username;
	}

	public void setUsername(String username)
	{
		Username = username;
	}

	private String ApiKey;

	public String getApiKey()
	{
		return ApiKey;
	}

	public void setApiKey(String apiKey)
	{
		ApiKey = apiKey;
	}

	private LoginToken Token;

	public LoginToken getToken()
	{
		return Token;
	}

	public void setToken(LoginToken token)
	{
		Token = token;
	}

	public int getCredits() throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getToken());

		return UserAuthentication.getCreditBalance(this.getToken());
	}

	public Scanner(String username, String APIKey) throws IOException, CommandFailedException
	{
		setToken(UserAuthentication.Login(username, APIKey));
		// This security token can be use multiple times, until it will be
		// expired (48 hours).
	}

	public ResultRecord[] ScanLocalOcrFile(String filePath) 
			throws FileNotFoundException, CommandFailedException 
	{
		File file = new File(filePath);
		
		if (!file.exists())
			throw new FileNotFoundException();

		Detector detector = new Detector(this.Token);
		ScannerProcess process = detector.CreateByOCR(file);
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
		ScannerProcess process = detector.CreateByFile(file);
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
		ScannerProcess process = detector.CreateByUrl(url);

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