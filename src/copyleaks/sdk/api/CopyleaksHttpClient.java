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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import copyleaks.sdk.api.exceptions.CopyleaksException;

/**
 * 
 * An HTTP client to make HTTP requests to Copyleaks API
 *
 */
public class CopyleaksHttpClient {

	private static final String APPLICATION_JSON = "application/json";
	
	private HttpClient client;

	/*
	 * The Copyleaks token obtained by calling CopyleaksIdentityApi.login
	 */
	private String token;
	
	private Gson gson;

	/**
	 * Initialize a new HTTP Client to connect to Copyleaks API
	 */
	public CopyleaksHttpClient() {
		this.client = HttpClients.createDefault();
		this.gson = new Gson();
	}

	/**
	 * Create a new HTTP client with a client certificate
	 * 
	 * @param clientCertificatePath: an Optional to use with CopyleaksScanApi 
	 * @param certificatePassword: an optional certificate password
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 */
	public CopyleaksHttpClient(String clientCertificatePath, String certificatePassword) throws KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, UnrecoverableKeyException {
		this.client = this.getHttpClientWithCertificate(clientCertificatePath, certificatePassword);
		this.gson = new Gson();
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	/*
	 * Create an HTTP POST request to Copyleaks API
	 */
	public HttpResponse post(String url, Object body) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		configureHttpClient(post);
		StringEntity input = new StringEntity(gson.toJson(body));
		input.setContentType(APPLICATION_JSON);
		post.setEntity(input);
		return client.execute(post);
	}

	/*
	 * Create an HTTP GET request to Copyleaks API
	 */
	public HttpResponse get(String url) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);
		configureHttpClient(get);
		return client.execute(get);
	}

	/*
	 * Create an HTTP DELETE request to Copyleaks API
	 */
	public HttpResponse delete(String url) throws ClientProtocolException, IOException {
		HttpDelete delete = new HttpDelete(url);
		configureHttpClient(delete);
		return this.client.execute(delete);
	}

	/*
	 * Create an HTTP PUT request to Copyleaks API
	 */
	public HttpResponse put(String url, Object body) throws ClientProtocolException, IOException {
		HttpPut put = new HttpPut(url);
		configureHttpClient(put);
		String json = gson.toJson(body);
		StringEntity input = new StringEntity(json);
		input.setContentType(APPLICATION_JSON);
		put.setEntity(input);
		return client.execute(put);
	}

	/*
	 * Create an HTTP PATCH request to Copyleaks API
	 */
	public HttpResponse patch(String url, Object body) throws ClientProtocolException, IOException {
		HttpPatch patch = new HttpPatch(url);
		configureHttpClient(patch);
		StringEntity input = new StringEntity(gson.toJson(body));
		input.setContentType(APPLICATION_JSON);
		patch.setEntity(input);
		return client.execute(patch);
	}

	/*
	 * Return true if the HttpResponse is 2xx family  
	 */
	public boolean validateStatusCode(HttpResponse response){
		int statusCode = response.getStatusLine().getStatusCode();
		return validateStatusCode(statusCode);
	}

	private boolean validateStatusCode(int statusCode) {
		return statusCode >=200 && statusCode < 300;
	}
	
	/*
	 * Convert the HTTP response to Class of type T
	 */
	public <T> T deserelizeResponseBody(HttpResponse response, Type type)
			throws ParseException, IOException, CopyleaksException {
		HttpEntity responseEntity = response.getEntity();
		String responseString = null;
		int statusCode = response.getStatusLine().getStatusCode();
		
		try {
			responseString = EntityUtils.toString(responseEntity);
			if(!validateStatusCode(statusCode))
				throw new CopyleaksException(responseString, statusCode);
			return this.gson.fromJson(responseString, type);
		} catch (JsonSyntaxException e) {
			throw new CopyleaksException(responseString, statusCode);
		}
	}

	private void configureHttpClient(HttpRequestBase request) {
		this.setAuthorization(request);
		this.setTimeout(request);
	}

	private void setAuthorization(HttpRequestBase request) {
		if (getToken() != null)
			request.addHeader("Authorization", "Bearer " + token);
	}

	private void setTimeout(HttpRequestBase request) {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Settings.RequestsTimeoutMilis)
				.setConnectTimeout(Settings.RequestsTimeoutMilis)
				.setConnectionRequestTimeout(Settings.RequestsTimeoutMilis).build();

		request.setConfig(requestConfig);
	}

	private HttpClient getHttpClientWithCertificate(String certificatePath, String certificatePassword)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
			IOException, KeyManagementException, UnrecoverableKeyException {
		char[] password;
		if (certificatePassword == null)
			password = new char[0];
		else
			password = certificatePassword.toCharArray();

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(new FileInputStream(certificatePath), password);
		SSLContext sslContext = SSLContexts.custom()
				// .loadTrustMaterial(null, new TrustSelfSignedStrategy())
				.loadKeyMaterial(keyStore, password).build();
		return HttpClients.custom().setSSLContext(sslContext).build();
	}

}
