package copyleaks.sdk.api;

import java.io.FileNotFoundException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.models.LoginToken;
import copyleaks.sdk.api.models.requests.CreateCommandRequest;
import copyleaks.sdk.api.models.responses.BadResponse;
import copyleaks.sdk.api.models.responses.CreateResourceResponse;

/**
 * This class is the main gate for integration with Copyleaks servers.
 * 
 * With this class you can Scan a URL (for web-page or Internet file), or 
 * Upload and scan of supported local file.
 *
 */
public class Detector {
	/**
	 * Current user identity.
	 */
	private LoginToken Token;
	private LoginToken getToken()
	{
		return this.Token;
	}
	private void setToken(LoginToken token)
	{
		this.Token = token;
	}
	
	public Detector(LoginToken token) {
		this.setToken(token);
	}

	/**
	 * URL scan for copyrights infringement. This method start a scanning process on Copyleaks cloud. 
	 * @param url Path for information that accessible via the Internet.
	 * @return A server process identification to track. 
	 * @throws Exception
	 */
	public ScannerProcess CreateByUrl(URI url) throws Exception {
		// Token Validation
		if (this.getToken() == null)
			throw new Exception("Empty token!");
		else
			this.getToken().Validate();

		HttpClient client = HttpClientBuilder.create().build();
		Gson gson = new GsonBuilder().create();
		CreateCommandRequest model = new CreateCommandRequest();
		model.setURL(url.toString());
		// Post to the server
		HttpPost post = new HttpPost(
				Resources.ServiceEntryPoint + Resources.ServiceVersion + "/detector/create-by-url");
		post.setEntity(new StringEntity(gson.toJson(model)));
		post.setHeader("Content-Type", HttpContentTypes.Json);
		post.setHeader("Accept", HttpContentTypes.Json);
		post.setHeader("User-Agent", Resources.USER_AGENT);
		post.addHeader("Authorization", String.format("%1$s %2$s", "Bearer", this.getToken().getTemporarySecurityCode()));
		// Response from the server
		HttpResponse msg = client.execute(post);
		if (msg.getStatusLine().getStatusCode() != 200) {
			HttpEntity entity = msg.getEntity();
			String json_error = EntityUtils.toString(entity, "UTF-8");
			BadResponse error = gson.fromJson(json_error, BadResponse.class);
			if (json_error == null)
				throw new CommandFailedException(msg);
			else
				throw new CommandFailedException(error.Message, msg);
		}

		HttpEntity entity = msg.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		CreateResourceResponse response = gson.fromJson(json, CreateResourceResponse.class);
		return new ScannerProcess(this.getToken(), response.getProcessId());
	}

	/**
	 * Local file scan for copyrights infringement. This method start a scanning process on Copyleaks cloud.
	 * @param localfile Path for local file. This file must a textual file that it's format supported by Copyleaks.
	 * This file will be uploaded to Copyleaks server and will be remove at the end of the processing stage.
	 * @return A server process identification to track. 
	 * @throws Exception
	 */
	public ScannerProcess CreateByFile(FileBody localfile) throws Exception {
		// Token Validation
		if (this.getToken() == null)
			throw new Exception("Empty token!");
		else
			this.getToken().Validate();
		
		if (localfile == null)
			throw new FileNotFoundException();

		HttpClient client = HttpClientBuilder.create().build();
		Gson gson = new GsonBuilder().create();
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		// Post to the server
		HttpPost post = new HttpPost(
				Resources.ServiceEntryPoint + Resources.ServiceVersion + "/detector/create-by-file");
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart(localfile.getFilename(), localfile);
		post.addHeader("Accept", HttpContentTypes.Json);
		post.setHeader("User-Agent", Resources.USER_AGENT);
		post.addHeader("Authorization", String.format("%1$s %2$s", "Bearer", this.getToken().getTemporarySecurityCode()));
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		// Response from the server
		HttpResponse msg = client.execute(post);
		HttpEntity msgEntity = msg.getEntity();
		if (msg.getStatusLine().getStatusCode() != 200) {
			String json_error = EntityUtils.toString(entity, "UTF-8");
			BadResponse error = gson.fromJson(json_error, BadResponse.class);
			if (json_error == null)
				throw new CommandFailedException(msg);
			else
				throw new CommandFailedException(error.Message, msg);
		}

		String json = EntityUtils.toString(msgEntity, "UTF-8");
		CreateResourceResponse response = gson.fromJson(json, CreateResourceResponse.class);
		return new ScannerProcess(this.getToken(), response.getProcessId());
	}

	/**
	 * Image scan, with OCR, for copyrights infringement. This method start a scanning process on Copyleaks cloud.
	 * @param localfile Path for local image file. Expected image formats: jpg, jpeg. 
	 * This file will be uploaded to Copyleaks server and will be remove at the end of the processing stage.
	 * @return A server process identification to track.
	 * @throws Exception
	 */
	public ScannerProcess CreateByOCR(FileBody localfile) throws Exception {
		// Token Validation
		if (this.getToken() == null)
			throw new Exception("Empty token!");
		else
			this.getToken().Validate();
		// File Validation
		if (localfile == null)
			throw new FileNotFoundException();

		CreateCommandRequest req = new CreateCommandRequest();
		req.setURL(localfile.getFile().getPath());
		HttpClient client = HttpClientBuilder.create().build();
		Gson gson = new GsonBuilder().create();
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		// Post to the server
		HttpPost post = new HttpPost(Resources.ServiceEntryPoint + Resources.ServiceVersion + "/detector/create-by-file-ocr");
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("document", localfile);
		//post.addHeader("Accept", Content_Type.Json);
		post.setHeader("User-Agent", Resources.USER_AGENT);
		post.addHeader("Authorization", String.format("Bearer %1$s", this.getToken().getTemporarySecurityCode()));
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		// Response from the server
		HttpResponse msg = client.execute(post);
		HttpEntity msgEntity = msg.getEntity();
		if (msg.getStatusLine().getStatusCode() != 200) {
			String json_error = EntityUtils.toString(entity, "UTF-8");
			BadResponse error = gson.fromJson(json_error, BadResponse.class);
			if (json_error == null)
				throw new CommandFailedException(msg);
			else
				throw new CommandFailedException(error.Message, msg);
		}

		String json = EntityUtils.toString(msgEntity, "UTF-8");
		CreateResourceResponse response = gson.fromJson(json, CreateResourceResponse.class);
		return new ScannerProcess(this.getToken(), response.getProcessId());
	}
}
