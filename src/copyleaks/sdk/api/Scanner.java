package copyleaks.sdk.api;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.http.entity.mime.content.FileBody;

import copyleaks.sdk.api.exceptions.CommandFailedException;
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

	public Scanner(String username, String APIKey)
			throws IOException, CommandFailedException
	{
		setToken(UserAuthentication.Login(username, APIKey));
		// This security token can be use multiple times, until it will be
		// expired (48 hours).
	}

	public ResultRecord[] ScanLocalOcrFile(File file) throws Exception
	{
		if (!file.exists())
			throw new FileNotFoundException();

		// Create a new process on server.
		FileBody fileBody = new FileBody(file);
		Detector detector = new Detector(this.Token);
		ScannerProcess process = detector.CreateByOCR(fileBody);

		// Waiting to process to be finished.
		while (!process.IsCompleted())
			Thread.sleep(1000);

		// Getting results.
		return process.GetResults();
	}
	
	public ResultRecord[] ScanLocalTextualFile(File file) throws Exception
	{
		if (!file.exists())
			throw new FileNotFoundException();

		// Create a new process on server.
		FileBody fileBody = new FileBody(file);
		Detector detector = new Detector(this.Token);
		ScannerProcess process = detector.CreateByFile(fileBody);

		// Waiting to process to be finished.
		while (!process.IsCompleted())
			Thread.sleep(1000);

		// Getting results.
		return process.GetResults();
	}

	public ResultRecord[] ScanUrl(URI url) throws Exception
	{
		// Create a new process on server.
		Detector detector = new Detector(this.Token);
		ScannerProcess process = detector.CreateByUrl(url);

		// Waiting to process to be finished.
		while (!process.IsCompleted())
			Thread.sleep(1000);

		// Getting results.
		return process.GetResults();
	}
}