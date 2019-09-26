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

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import copyleaks.sdk.api.exceptions.CopyleaksException;
import copyleaks.sdk.api.models.request.FileOcrDocument;
import copyleaks.sdk.api.models.request.FileDocument;
import copyleaks.sdk.api.models.request.StartBatchRequest;
import copyleaks.sdk.api.models.request.StartRequest;
import copyleaks.sdk.api.models.request.UrlDocument;
import copyleaks.sdk.api.models.request.download.DownloadResponse;
import copyleaks.sdk.api.models.response.CountCreditsResponse;
import copyleaks.sdk.api.models.response.DeleteResponse;
import copyleaks.sdk.api.models.response.ProgressResponse;
import copyleaks.sdk.api.models.response.ResultResponse;
import copyleaks.sdk.api.models.response.StartResponse;
import copyleaks.sdk.api.models.response.SupportedTypesResponse;
import copyleaks.sdk.api.models.types.eProduct;

/**
 * Make requests to Copyleaks scan API, scan content and get the results of your scan
 */
public class CopyleaksScansApi extends CopyleaksBaseApi {

	private eProduct product;

	private String token;

	/**
	 * Initialize a new class to make request to Copyleaks API, scan content and get the results of your scan
	 */
	public CopyleaksScansApi(eProduct prodcut, String token) {
		this.product = prodcut;
		this.token = token;
	}
	
	/**
	 * Initialize a new class to make request to Copyleaks API with a client certificate.
	 * scan content and get the results of your scan
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
	public CopyleaksScansApi(String clientCertificatePath, String certificatePassword) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, UnrecoverableKeyException{
    	super(clientCertificatePath, certificatePassword);
    }
	
	/**
	 * Get your current credit balance
	 * @return Your current credit balance
	 * @throws Exception
	 */
	public int creditBalance() throws Exception {
		String url = String.format("%s%s/%s/credits", Settings.ApiEndPoint, Settings.ApiVersion, this.product);
		this.client.setToken(this.token);
		HttpResponse response = this.client.get(url);
		CountCreditsResponse countCreditsResponse = this.client.deserelizeResponseBody(response,
				CountCreditsResponse.class);
		return countCreditsResponse.getAmount();
	}

	/**
	 * Submitting URL to plagiarism scan
	 * @param scanId: The scan ID
	 * @param document: The content to scan
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws CopyleaksException
	 */
	public void submitUrl(String scanId, UrlDocument document)
			throws ClientProtocolException, IOException, ParseException, CopyleaksException {
		String url = String.format("%s%s/%s/submit/url/%s", Settings.ApiEndPoint, Settings.ApiVersion, this.product,
				scanId);
		this.client.setToken(this.token);
		HttpResponse response = this.client.put(url, document);
		this.client.validateStatusCode(response);
	}

	/**
	 * Submitting a file or free text to plagiarism scan
	 * @param scanId: The scan ID
	 * @param document: The content to scan
	 * @throws ParseException
	 * @throws IOException
	 * @throws CopyleaksException
	 */
	public void submitFile(String scanId, FileDocument document)
			throws ParseException, IOException, CopyleaksException {
		String url = String.format("%s%s/%s/submit/file/%s", Settings.ApiEndPoint, Settings.ApiVersion, this.product,
				scanId);
		this.client.setToken(this.token);
		HttpResponse response = this.client.put(url, document);
		this.client.validateStatusCode(response);
	}

	/**
	 * Submitting An OCR image with text to plagiarism scan 
	 * @param scanId: The scan ID
	 * @param document: The content to scan
	 * @throws ParseException
	 * @throws IOException
	 * @throws CopyleaksException
	 */
	public void submitImageOcrFile(String scanId, FileOcrDocument document)
			throws ParseException, IOException, CopyleaksException {
		String url = String.format("%s%s/%s/submit/ocr/%s", Settings.ApiEndPoint, Settings.ApiVersion, this.product,
				scanId);
		this.client.setToken(this.token);
		HttpResponse response = this.client.put(url, document);
		this.client.validateStatusCode(response);
	}

	/**
	 * Get the progress of the scan in percents
	 * @param scanId: The scan ID
	 * @return The scan progress in percents
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws CopyleaksException
	 */
	public int progress(String scanId)
			throws ClientProtocolException, IOException, ParseException, CopyleaksException {
		String url = String.format("%s%s/%s/%s/progress", Settings.ApiEndPoint, Settings.ApiVersion, this.product,
				scanId);
		this.client.setToken(this.token);
		HttpResponse response = this.client.get(url);
		ProgressResponse progrssResponse = this.client.deserelizeResponseBody(response, ProgressResponse.class);
		return progrssResponse.getPercents();
	}

	/**
	 * Deletes the process once it has finished running 
	 * @param scanId
	 * @return An object with the result of the delete request
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws CopyleaksException
	 */
	public DeleteResponse delete(String scanId)
			throws ClientProtocolException, IOException, ParseException, CopyleaksException {
		String url = String.format("%s%s/%s/%s/delete", Settings.ApiEndPoint, Settings.ApiVersion, this.product,
				scanId);
		this.client.setToken(this.token);
		HttpResponse response = this.client.delete(url);
		return this.client.deserelizeResponseBody(response, DeleteResponse.class);
	}

	/**
	 * Start a scan which is in 'price checked' status
	 * 
	 * @param startRequest: The details of the scan to start 
	 * @return The result for the start request
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws CopyleaksException
	 */
	public StartResponse start(StartRequest startRequest)
			throws ClientProtocolException, IOException, ParseException, CopyleaksException {
		String url = String.format("%s%s/%s/start", Settings.ApiEndPoint, Settings.ApiVersion, this.product);
		this.client.setToken(this.token);
		HttpResponse response = this.client.patch(url, startRequest);
		return this.client.deserelizeResponseBody(response, StartResponse.class);
	}

	/**
	 * Start multiple scans which are in 'price checked' status
	 * @param startBatchRequest: The details of the scans to start
	 * @return The result for the startBatch request
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws CopyleaksException
	 */
	public StartResponse startBatch(StartBatchRequest startBatchRequest)
			throws ClientProtocolException, IOException, ParseException, CopyleaksException {
		String url = String.format("%s%s/%s/batch/start", Settings.ApiEndPoint, Settings.ApiVersion, this.product);
		this.client.setToken(this.token);
		HttpResponse response = this.client.patch(url, startBatchRequest);
		return this.client.deserelizeResponseBody(response, StartResponse.class);
	}

	/**
	 * Get the scan results from Copyleaks servers.
	 * @param scanId: Your scan id
	 * @return The results for the scan
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws CopyleaksException
	 */
	public ResultResponse result(String scanId)
			throws ClientProtocolException, IOException, ParseException, CopyleaksException {
		String url = String.format("%s%s/%s/%s/result", Settings.ApiEndPoint, Settings.ApiVersion, this.product,
				scanId);
		this.client.setToken(this.token);
		HttpResponse response = this.client.get(url);
		return this.client.deserelizeResponseBody(response, ResultResponse.class);
	}

	/**
	 * Get a suspected result by result id
	 * @param scanId
	 * @param resultId
	 * @return The report of the result id
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws CopyleaksException
	 */
	public DownloadResponse download(String scanId, String resultId) throws ClientProtocolException, IOException, ParseException, CopyleaksException {
		String url = String.format("%s%s/downloads/%s/results/%s", Settings.ApiEndPoint, Settings.ApiVersion, scanId, resultId);
		this.client.setToken(this.token);
		HttpResponse response = this.client.get(url);
		return this.client.deserelizeResponseBody(response, DownloadResponse.class);
	}
	
	public InputStream DonwloadPdfReport(String scanId) throws ClientProtocolException, IOException, CopyleaksException {
		String url = String.format("%s%s/downloads/%s", Settings.ApiEndPoint, Settings.ApiVersion, scanId);
		this.client.setToken(this.token);
		HttpResponse response = this.client.get(url);
		return this.client.getResponseStream(response);
	}
	
	/**
	 * Get a full list of supported file types.
	 * @return Full list of supported file types.
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws CopyleaksException
	 */
	public SupportedTypesResponse getSupportedFileTypes() throws ClientProtocolException, IOException, ParseException, CopyleaksException {
		String url = String.format("%sv1/miscellaneous/supported-file-types", Settings.ApiEndPoint);
		HttpResponse response = this.client.get(url);
		return this.client.deserelizeResponseBody(response, SupportedTypesResponse.class);
	}
	
	/**
	 * Get a full list of supported file types.
	 * @return Full list of supported file types.
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws CopyleaksException
	 */
	public String[] GetOcrLanguageList() throws ClientProtocolException, IOException, ParseException, CopyleaksException {
		String url = String.format("%s/v1/miscellaneous/ocr-languages-list", Settings.ApiEndPoint);
		HttpResponse response = this.client.get(url);
		return this.client.deserelizeResponseBody(response, String[].class);
	}
	
	/**
	 * 
	 * @return The Copyleaks product you are using
	 */
	public eProduct getProduct() {
		return product;
	}
}
