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

package Demo;

import java.util.UUID;

import copyleaks.sdk.api.CopyleaksIdentityApi;
import copyleaks.sdk.api.CopyleaksScansApi;
import copyleaks.sdk.api.exceptions.CopyleaksException;
import copyleaks.sdk.api.models.request.Callbacks;
import copyleaks.sdk.api.models.request.FileDocument;
import copyleaks.sdk.api.models.request.FileOcrDocument;
import copyleaks.sdk.api.models.request.LoginResponse;
import copyleaks.sdk.api.models.request.ScanProperties;
import copyleaks.sdk.api.models.request.UrlDocument;
import copyleaks.sdk.api.models.request.builders.FileDocumentBuilder;
import copyleaks.sdk.api.models.request.builders.FileOcrDocumentBuilder;
import copyleaks.sdk.api.models.request.builders.FreeTextDocumentBuilder;
import copyleaks.sdk.api.models.request.builders.ScanPropertiesBuilder;
import copyleaks.sdk.api.models.types.eProduct;
import copyleaks.sdk.api.models.types.eSubmitAction;

/**
 * 
 * An example of using the Copyleaks Java SDK and receiving a completion
 * callback with the results.
 *
 */
public class AsynchronousExample {

	enum SubmissionType {
		FILE, IMAGE, FREE_TEXT, URL
	};

	public static void main(String[] args) {
		/*
		 * Change to your account credentials. If you don't have an account yet visit
		 * https://copyleaks.com/Account/Register Your API-KEY is available at your
		 * dashboard on http://api.copyleaks.com of the product that you would like to
		 * use. Currently available products: Businesses, Education and Websites.
		 */
		String email = "<YOUR_EMAIL_HERE>";
		String apiKey = "<YOUR_API_KEY_HERE>";
		eProduct product = eProduct.Education;

		/*
		 * Create a unique scan id The scan id you provide is determined by you it
		 * usually matches the scan entity in your system Each scan you submit to
		 * Copyleaks must have a unique ID and in the following format: Allowed
		 * characters: all alpha numeric characters and any of the following:
		 * !@#$^&*-+%=_()[]{}<>'";:/?.,~`|\ Maximum length: 36 characters
		 */
		String scanId = UUID.randomUUID().toString();

		scan(email, apiKey, product, scanId);
	}

	public static void scan(String email, String apiKey, eProduct product, String scanId) {
		try {
			/*
			 * Obtain an access token from Copyleaks API
			 */
			CopyleaksIdentityApi identity = new CopyleaksIdentityApi();
			LoginResponse loginResponse;
			loginResponse = identity.Login(email, apiKey);

			CopyleaksScansApi api = new CopyleaksScansApi(product, loginResponse.getAccessToken());

			System.out.println(String.format("You've got %d Copyleaks %s API credits", api.creditBalance(),
					api.getProduct().toString()));

			/*
			 * Use this option to submit your document to full scan Other possible values:
			 * eSubmitAction.Index: Upload your content to Copyleaks internal database to be
			 * compared against in future scans eSubmitAction.checkCredits: Check the amount
			 * of credits your content submit request will consume
			 */
			eSubmitAction submitAction = eSubmitAction.Scan;

			Callbacks callbacks = new Callbacks("https://fake/completion/callbask/",
					"https://fake/newResult/callbask/");

			/*
			 * You can test the integration with Copyleaks API for free using the sandbox mode. You will be able to submit content to scan and get back mock results, simulating the way Copyleaks will work
			 */
			boolean sandboxMode = true;

			/*
			 * Add the properties to your scan
			 */
			ScanProperties properties = new ScanPropertiesBuilder().setAction(submitAction).setCallbacks(callbacks)
					.setSandbox(sandboxMode).build();

			/*
			 * Choose your content type for submission
			 */
			SubmissionType submitionType = SubmissionType.URL;
			switch (submitionType) {

			/*
			 * Submit url
			 */
			case URL:
				UrlDocument urlDocument = new UrlDocument("http://example.com", properties);
				api.submitUrl(scanId, urlDocument);
				break;

			/*
			 * submit free text
			 */
			case FREE_TEXT:
				FileDocument freeTextDocument = new FreeTextDocumentBuilder().setFilename("freeText.txt")
						.setFreeText("helo world").setScanProperties(properties).build();
				api.submitFile(scanId, freeTextDocument);
				break;
			/*
			 * Submit a textual file
			 */
			case FILE:
				String textFileName = "file.txt";
				String filePath = "c:\\path\\to\\your\\" + textFileName;
				FileDocument fileDocument = new FileDocumentBuilder().setFilename(textFileName).setFilePath(filePath)
						.setScanProperties(properties).build();
				api.submitFile(scanId, fileDocument);
				break;
			/*
			 * Submit an OCR image with text
			 */
			case IMAGE:
				String imageFileName = "imageWithText.png";
				String imagePath = "c:\\path\\to\\your\\" + imageFileName;
				FileOcrDocument fileOcrDocument = new FileOcrDocumentBuilder().setLanguageCode("en")
						.setFilePath(imagePath).setFilename(imageFileName).setScanProperties(properties).build();
				api.submitImageOcrFile(scanId, fileOcrDocument);
				break;
			}
			System.out.println("Submitted. You will receive a callback soon.");

		} catch (CopyleaksException copyleaksException) {
			System.out.println("Failed!");
			System.out.format("*** Error (%d):\n", copyleaksException.getCopyleaksErrorCode());
			System.out.println(copyleaksException.getMessage());
		} catch (Exception ex) {
			System.out.println("Failed!");
			System.out.println("Unhandled Exception");
			System.out.println(ex);
		}

	}
}
