<h2>Copyleaks API Java SDK</h2>
<p>
Copyleaks SDK is a simple framework that allows you to perform plagiarism scans and track content distribution around the web, using the Copyleaks cloud.
</p>
<p>
With the Copyleaks SDK you can submit a scan for:  
<ul>
<li>Webpages</li>
<li>Local files - pdf, doc, docx, rtf and more <a href="https://api.copyleaks.com/GeneralDocumentation/TechnicalSpecifications#supportedfiletypes">(see full list)</a></li>
<li>Free text</li>
<li>OCR (Optical Character Recognition) - scanning pictures containing textual content <a href="https://api.copyleaks.com/GeneralDocumentation/TechnicalSpecifications#supportedfiletypes">(see full list)</a></li>
</ul>
Instructions for using the SDK are below. For a quick example demonstrating the SDK capabilities just look at the code examples under <a href="#example">example</a>.  You can also watch the following video demonstrating how to easily integrate with the API - <i>Coming soon</i> 
</p>
</ol>
<h3>Integration</h3>
<p>You can integrate with the Copyleaks SDK in one of two ways:<p/>
<ul>
<li>Download the code to your workspace – choose this option if you want to alter the code and add capabilities to match your own specific needs. Download the project from here, open it using Eclipse and choose ‘Import Project’ from your own workspace. Then, follow the next steps:
<ol>
<li>Go to ‘General’, choose ‘Existing projects into workspace’ and press next.</li>
<li>Fill in the details about the project you just downloaded under ‘Select root directory’.</li>
<li>Make sure that the project Copyleaks API is checked.</li>
<li>Press ‘Finish’.</li>
</ol>
<p>Now you have the project in your workspace and you can make alterations that fits your own specific needs. As default, the project includes the file ‘AsynchronousExample.java’. This file will allow you to easily run and check your project.</p></li>

<li>Download the JAR files – choose this option if you want to use the code as-is. Download the <a href="copyleaks-sdk.jar">JAR files</a> and then, follow the next steps:
<ol>
<li>Extract the files.</li>
<li>Open Eclipse and select the process you want to integrate with Copyleaks API.</li>
<li>Press the right mouse key and choose ‘Build Path’ -> ‘Configure Build Path’.</li>
<li>In the window that opened choose the tab ‘Libraries’.</li>
<li>Press on ‘Add External JAR’.</li>
<li>Browse the libraries with your extracted JAR files and choose to add them.</li>
</ol>
</li>
</ul>
<p>Now you can use the Copyleaks API in your project. If you are working with our latest version, you can also run the libraries using the code found down on this page.</p>
<h3>Signing Up and Getting Your API Key</h3>
 <p>To use Copyleaks API you need to be a registered user. Signing up is quick and free of charge.</p>
 <p><a href="https://copyleaks.com/account/register">Signup</a> to Copyleaks and confirm your account by clicking the link in the confirmation email. Generate your personal API key on your dashboard (<a href="https://api.copyleaks.com/businessesapi">Businesses dashboard/</a><a href="https://api.copyleaks.com/academicapi">Academic dashboard/</a><a href="https://api.copyleaks.com/websitesapi">Websites dashboard</a>) under 'Access Keys'. </p>
 <p>For more information check out our <a href="https://api.copyleaks.com/Guides/HowToUse">API guide</a>.</p>
<a name="example"><h3>Example</h3></a>
<p>This code will show you where the textual content in the parameter ‘url’ has been used online:</p>
<pre>
enum SubmissionType {
	FILE, IMAGE, FREE_TEXT, URL
};

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
</pre>
<h3>Dependencies:</h3>
<h5>Maven dependencies:</h5>
<ul>
<li><a href="https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5">gson 2.8.5</a></li>
<li><a href="https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.6">HttpClient 4.5.6</a></li>

</ul>

<h3>Read More</h3>
<ul>
<li><a href="https://api.copyleaks.com/">API Homepage</a></li>
<li><a href="https://api.copyleaks.com/Guides/HowToUse">Copyleaks API guide</a></li>
<li><a href="https://copyleaks.com/">Copyleaks Homepage</a></li>
</ul>
