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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import copyleaks.sdk.api.helpers.FileHelpers;
import copyleaks.sdk.api.helpers.HttpURLConnection.CopyleaksClient;
import copyleaks.sdk.api.helpers.HttpURLConnection.HttpURLConnectionHelper;
import copyleaks.sdk.api.RequestMethod;
import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.exceptions.SecurityTokenException;
import copyleaks.sdk.api.models.LoginToken;
import copyleaks.sdk.api.models.ProcessOptions;
import copyleaks.sdk.api.models.requests.CreateCommandRequest;
import copyleaks.sdk.api.models.responses.CreateResourceResponse;
import copyleaks.sdk.api.models.responses.ProcessInList;
import copyleaks.sdk.api.models.SupportedFiles;


/**
 * This class allows you to connect to Copyleaks cloud, scan for plagiarism and
 * get your Copyleaks account status.
 */
public class CopyleaksCloud
{
	public CopyleaksCloud(eProduct selectedProduct)
	{
		setProduct(selectedProduct);
	}
	
	private eProduct Product;
	protected eProduct getProduct()
	{
		return this.Product;
	}

	private void setProduct(eProduct product)
	{
		this.Product = product;
	}
	
	private LoginToken Token;

	protected LoginToken getToken()
	{
		return this.Token;
	}

	public void setToken(LoginToken token)
	{
		this.Token = token;
	}

	/**
	 * Get your active processes
	 * 
	 * @return Active processes
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occurred
	 *             during the processing of a command
	 * @throws SecurityTokenException
	 *             The login-token is undefined or expired
	 */
	public CopyleaksProcess[] getProcesses() throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getToken());

		URL url;
		HttpURLConnection conn = null;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		String json;
		try
		{
			url = new URL(String.format("%1$s/%2$s/%3$s/list", Settings.ServiceEntryPoint, Settings.ServiceVersion,
					this.productToServicePage()));
			conn = CopyleaksClient.getClient(url, this.getToken(), RequestMethod.GET, HttpContentTypes.Json,
					HttpContentTypes.TextPlain);

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

		ProcessInList[] response = gson.fromJson(json, ProcessInList[].class);
		CopyleaksProcess[] processes = new CopyleaksProcess[response.length];
		for (int i = 0; i < response.length; ++i)
			processes[i] = new CopyleaksProcess(productToServicePage(),this.getToken(), response[i]);

		Arrays.sort(processes, Collections.reverseOrder());

		return processes;
	}

	/**
	 * Get your current credit balance
	 * 
	 * @return Current credit balance
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occured
	 *             during the processing of a command
	 * @throws SecurityTokenException
	 *             The login-token is undefined or expired
	 */
	public int getCredits() throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getToken());

		return UserAuthentication.getCreditBalance(this.getToken(), this.getProduct());
	}

	/**
	 * Login to Copyleaks API
	 * 
	 * @param email
	 *            Account email
	 * @param APIKey
	 *            Copyleaks API key
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occured
	 *             during the processing of a command
	 * @throws IOException
	 *             This exception is throen if an exception situation occured
	 *             during the processing of an I/O operation.
	 */
	public void Login(String email, String APIKey) throws IOException, CommandFailedException
	{
		this.setToken(UserAuthentication.Login(email, APIKey));
	}

	/**
	 * Submitting URL to plagiarism scan
	 * 
	 * @param url
	 *            The URL containing the content to scan
	 * @param options
	 *            Process Options: include HTTP callback and add custom fields
	 *            to the process
	 * @return The newly created process
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occurred
	 *             during the processing of a command
	 * @throws SecurityTokenException
	 *             The login-token is undefined or expired
	 */
	public CopyleaksProcess CreateByUrl(URI url, ProcessOptions options)
			throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getToken()); // Token Validation

		String json;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		CreateCommandRequest model = new CreateCommandRequest();
		model.setURL(url.toString());
		URL reqUrl;
		HttpURLConnection conn = null;
		try
		{
			reqUrl = new URL(String.format("%1$s/%2$s/%3$s/create-by-url", Settings.ServiceEntryPoint,
					Settings.ServiceVersion, this.productToServicePage()));
			conn = CopyleaksClient.getClient(reqUrl, this.getToken(), RequestMethod.POST, HttpContentTypes.Json,
					HttpContentTypes.Json);

			if (options != null)
				options.addHeaders(conn);

			CopyleaksClient.HandleString.attach(conn, gson.toJson(model));

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

		CreateResourceResponse response = gson.fromJson(json, CreateResourceResponse.class);
		return new CopyleaksProcess(productToServicePage(), this.getToken(), response, response.getCustomFields());
	}
	
	/**
	 * Submitting text to plagiarism scan
	 * 
	 * @param text
	 *            The content to scan
	 * @param options
	 *            Process Options: include HTTP callback and add custom fields
	 *            to the process
	 * @return The newly created process
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occurred
	 *             during the processing of a command
	 * @throws SecurityTokenException
	 *             The login-token is undefined or expired
	 */
	public CopyleaksProcess CreateByText(String text, ProcessOptions options)
			throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getToken()); // Token Validation

		String json;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		URL reqUrl;
		HttpURLConnection conn = null;
		try
		{
			reqUrl = new URL(String.format("%1$s/%2$s/%3$s/create-by-text", Settings.ServiceEntryPoint,
					Settings.ServiceVersion, this.productToServicePage()));
			conn = CopyleaksClient.getClient(reqUrl, this.getToken(), RequestMethod.POST, HttpContentTypes.Json,
					HttpContentTypes.Json);

			if (options != null)
				options.addHeaders(conn);

			CopyleaksClient.HandleString.attach(conn, text);

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

		CreateResourceResponse response = gson.fromJson(json, CreateResourceResponse.class);
		return new CopyleaksProcess(productToServicePage(), this.getToken(), response, response.getCustomFields());
	}

	/**
	 * Submitting local file to plagiarism scan
	 * 
	 * @param localfile
	 *            Local file containing the content to scan
	 * @param options
	 *            Process Options: include HTTP callback and add custom fields
	 *            to the process
	 * @return The newly created process
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occurred
	 *             during the processing of a command
	 * @throws SecurityTokenException
	 *             The login-token is undefined or expired
	 * @throws FileNotFoundException
	 *             Occur when the localfile isn't exists.
	 */
	public CopyleaksProcess CreateByFile(File localfile, ProcessOptions options)
			throws SecurityTokenException, CommandFailedException, FileNotFoundException
	{
		return CreateByFile(new FileInputStream(localfile), FileHelpers.getFileName(localfile),
				FileHelpers.getFileExtension(localfile), options);
	}

	/**
	 * Submitting local file to plagiarism scan
	 * 
	 * @param stream
	 *            Stream to file you want to scan.
	 * @param filename
	 *            The file name of the file. WITHOUT extension.
	 * @param fileExtension
	 *            The file type. Without dot (".") before it. For example-
	 *            "pdf".
	 * @param options
	 *            Process Options: include HTTP callback and add custom fields
	 *            to the process
	 * @return The newly created process
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occurred
	 *             during the processing of a command
	 * @throws SecurityTokenException
	 *             The login-token is undefined or expired
	 */
	public CopyleaksProcess CreateByFile(InputStream stream, String filename, String fileExtension,
			ProcessOptions options) throws SecurityTokenException, CommandFailedException
	{
		LoginToken.ValidateToken(this.getToken()); // Token Validation

		String json;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		URL reqUrl;
		HttpURLConnection conn = null;
		try
		{
			reqUrl = new URL(String.format("%1$s/%2$s/%3$s/create-by-file", Settings.ServiceEntryPoint,
					Settings.ServiceVersion, this.productToServicePage()));
			conn = CopyleaksClient.getClient(reqUrl, this.getToken(), RequestMethod.POST,
					HttpContentTypes.Multipart + ";boundary=file." + fileExtension, HttpContentTypes.Json);

			if (options != null)
				options.addHeaders(conn);

			CopyleaksClient.HandleFile.attach(conn, stream, filename, fileExtension);

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

		CreateResourceResponse response = gson.fromJson(json, CreateResourceResponse.class);
		return new CopyleaksProcess(productToServicePage(), this.getToken(), response, response.getCustomFields());
	}

	/**
	 * Submitting picture, containing textual content, to plagiarism scan
	 * 
	 * @param localfile
	 *            The local picture containing the content to scan
	 * @param languageCode
	 * 			  The code of the language in the picture.
	 * 			  For more information: https://api.copyleaks.com/GeneralDocumentation/OcrLanguages
	 * @param options
	 *            Process Options: include HTTP callback and add custom fields
	 *            to the process
	 * @return The newly created process
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occurred
	 *             during the processing of a command
	 * @throws FileNotFoundException
	 *             When the requested file wasn't found.
	 */
	public CopyleaksProcess CreateByOCR(File localfile, String languageCode, ProcessOptions options)
			throws CommandFailedException, FileNotFoundException
	{
		return CreateByOCR(new FileInputStream(localfile), FileHelpers.getFileName(localfile),
				FileHelpers.getFileExtension(localfile), languageCode, options);
	}

	/**
	 * Submitting picture, containing textual content, to plagiarism scan
	 * 
	 * @param stream
	 *            Stream to the file that you want to scan
	 * @param filename
	 *            The file name of the file. WITHOUT extension.
	 * @param languageCode
	 * 			  The language code of the text in the picture. 
	 * 			  For more information: https://api.copyleaks.com/GeneralDocumentation/OcrLanguages
	 * @param fileExtension
	 *            The file type. Without dot (".") before it. For example-
	 *            "pdf".
	 * @param options
	 *            Process Options: include HTTP callback and add custom fields
	 *            to the process
	 * @return The newly created process
	 * @throws CommandFailedException
	 *             This exception is thrown if an exception situation occurred
	 *             during the processing of a command
	 * @throws FileNotFoundException
	 *             When the requested file wasn't found.
	 */
	public CopyleaksProcess CreateByOCR(InputStream stream, String filename, String fileExtension, String languageCode,
			ProcessOptions options) throws CommandFailedException
	{
		LoginToken.ValidateToken(this.getToken()); // Token Validation

		if (languageCode == null || languageCode.trim() == "")
			throw new RuntimeException("lang cannot be null or empty!");

		String json;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		URL reqUrl;
		HttpURLConnection conn = null;
		try
		{
			reqUrl = new URL(String.format("%1$s/%2$s/%3$s/create-by-file-ocr?language=%4$s",
					Settings.ServiceEntryPoint, Settings.ServiceVersion, this.productToServicePage(),
					URLEncoder.encode(languageCode, Settings.Encoding)));
			conn = CopyleaksClient.getClient(reqUrl, this.getToken(), RequestMethod.POST,
					HttpContentTypes.Multipart + ";boundary=file." + fileExtension, HttpContentTypes.Json);

			if (options != null)
				options.addHeaders(conn);

			CopyleaksClient.HandleFile.attach(conn, stream, filename, fileExtension);

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

		CreateResourceResponse response = gson.fromJson(json, CreateResourceResponse.class);
		return new CopyleaksProcess(productToServicePage(), this.getToken(), response, response.getCustomFields());
	}
	
	protected String productToServicePage()
	{
		switch (this.getProduct())
		{
			case Businesses:
				return Settings.BusinessesServicePage;
			case Academic:
				return Settings.AcademicServicePage;
			case Websites:
				return Settings.WebsitesServicePage;
			default:
				throw new RuntimeException("Unknown service page.");
		}
	}
	
	/**
	 * Get a list of supported file types to upload. 
	 * @return Supported file types list.
	 * @throws CommandFailedException
	 */
	public static SupportedFiles SupportedFiles() 
			throws CommandFailedException
	{
		String json;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		URL reqUrl;
		HttpURLConnection conn = null;
		try
		{
			reqUrl = new URL(String.format("%1$s/%2$s/miscellaneous/supported-file-types", Settings.ServiceEntryPoint, Settings.ServiceVersion));
			conn = CopyleaksClient.getClient(reqUrl, RequestMethod.GET, HttpContentTypes.Json, HttpContentTypes.Json);

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

		SupportedFiles response = gson.fromJson(json, SupportedFiles.class);
		return response;
	}
	
	/**
	 * Get a list of supported OCR languages. 
	 * @return List of supported languages. Include the language name and ID.
	 * @throws CommandFailedException
	 */
	public static String[] SupportedOcrLanguages() 
			throws CommandFailedException
	{
		String json;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		URL reqUrl;
		HttpURLConnection conn = null;
		try
		{
			reqUrl = new URL(String.format("%1$s/%2$s/miscellaneous/ocr-languages-list", Settings.ServiceEntryPoint, Settings.ServiceVersion));
			conn = CopyleaksClient.getClient(reqUrl, RequestMethod.GET, HttpContentTypes.Json, HttpContentTypes.Json);

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

		return gson.fromJson(json, String[].class);
	}
}
