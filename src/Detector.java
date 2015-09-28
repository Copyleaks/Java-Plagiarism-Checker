
import Models.LoginToken;
import Models.Requests.CreateCommandRequest;
import Models.Responses.BadResponse;

import java.io.FileNotFoundException;

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

import Exceptions.CommandFailedException;
import Models.Responses.CreateResourceResponse;

public class Detector {
	private LoginToken Token;

	public Detector(LoginToken token) {
		this.Token = token;
	}

	public ScannerProcess CreateByUrl(String url) throws Exception {
		// Token Validation
		if (this.Token == null)
			throw new Exception("Empty token!");
		else
			this.Token.Validate();

		HttpClient client = HttpClientBuilder.create().build();
		Gson gson = new GsonBuilder().create();
		CreateCommandRequest model = new CreateCommandRequest();
		model.setURL(url);
		// Post to the server
		HttpPost post = new HttpPost(
				Resources.ServiceEntryPoint + Resources.ServiceVersion + "/detector/create-by-url");
		post.setEntity(new StringEntity(gson.toJson(model)));
		post.setHeader("Content-Type", Content_Type.Json);
		post.setHeader("Accept", Content_Type.Json);
		post.setHeader("User-Agent", Resources.USER_AGENT);
		post.addHeader("Authorization", String.format("%1$s %2$s", "Bearer", this.Token.getToken()));
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
		return new ScannerProcess(this.Token, response.getProcessId());
	}

	public ScannerProcess CreateByFile(FileBody localfile) throws Exception {
		// Token Validation
		if (this.Token == null)
			throw new Exception("Empty token!");
		else
			this.Token.Validate();
		// File Validation
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
		post.addHeader("Accept", Content_Type.Json);
		post.setHeader("User-Agent", Resources.USER_AGENT);
		post.addHeader("Authorization", String.format("%1$s %2$s", "Bearer", this.Token.getToken()));
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
		return new ScannerProcess(this.Token, response.getProcessId());
	}

	public ScannerProcess CreateByOCR(FileBody localfile) throws Exception {
		// Token Validation
		if (this.Token == null)
			throw new Exception("Empty token!");
		else
			this.Token.Validate();
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
		post.addHeader("Authorization", String.format("Bearer %1$s", this.Token.getToken()));
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
		return new ScannerProcess(this.Token, response.getProcessId());
	}
}
