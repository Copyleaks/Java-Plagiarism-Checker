<h2>Copyleaks SDK</h2>
<p>
Copyleaks SDK is a simple framework that allows you to perform plagiarism scans and track content distribution around the web, using Copyleaks cloud.
</p>
<p>
With Copyleaks SDK you can submit for scan:  
<ul>
<li>Webpages</li>
<li>Local files - pdf, doc, docx, rtf and more <a href="https://api.copyleaks.com/Documentation/TechnicalSpecifications/#non-textual-formats">(see full list)</a></li>
<li>OCR (Optical Character Recognition) - scanning pictures containing textual content <a href="https://api.copyleaks.com/Documentation/TechnicalSpecifications/#ocr-formats">(see full list)</a></li>
</ul>
Instructions for using the SDK are below. For a quick example demonstrating the SDK capabilities just look at the code examples under “examples”.
</p>
<h3>Configuration</h3>
Download the code from here, compile it and add reference to the assembly.
<pre>
Install-Package CopyleaksAPI
</pre>
</ol>
<h3>Signing Up and Getting Your API Key</h3>
 <p>To use Copyleaks API you need to be a registered user. Signing up is free of charge.</p>
 <p><a href="https://copyleaks.com/Account/Signup">Signup</a> to Copyleaks and confirm your account by clicking the link on the confirmation email. To get your API key, click your username in the upper right corner which will lead you to <i>‘Manage Account’</i> page. Press on <i>"Generate"</i> to get your own personal API key.</p>
 <p>For more information check out our <a href="https://api.copyleaks.com/Guides/HowToUse">API guide</a>.</p>
<h3>Example</h3>
<p>This code will show you where the textual content in the parameter ‘url’ has been used online:</p>
<pre>
public static void Scan(String email, String key, String url) {
		CopyleaksCloud copyleaks = new CopyleaksCloud();
		try {
			System.out.print("Login to Copyleaks cloud...");
			copyleaks.Login(email, key);
			System.out.println("Done!");

			System.out.print("Checking account balance...");
			int creditsBalance = copyleaks.getCredits();
			System.out.println("Done (" + creditsBalance + " credits)!");
			if (creditsBalance == 0) {
				System.out.println(
						"ERROR: You have insufficient credits for scanning content! (current credits balance = "
								+ creditsBalance + ")");
				return;
			}

			ProcessOptions scanOptions = new ProcessOptions();
			// scanOptions.setSandboxMode(true); // <------ Read more @
			// https://api.copyleaks.com/Documentation/RequestHeaders#sandbox-mode

			ResultRecord[] results;
			CopyleaksProcess createdProcess;

			createdProcess = copyleaks.CreateByUrl(new URI(url), scanOptions);

			// Waiting for process completion...
			System.out.println("Scanning...");
			int percents = 0;
			while (percents != 100 && (percents = createdProcess.getCurrentProgress()) <= 100) {
				System.out.println(percents + "%");

				if (percents != 100)
					Thread.sleep(4000);
			}

			results = createdProcess.GetResults();

			if (results.length == 0) {
				System.out.println("No results.");
			} else {
				for (int i = 0; i < results.length; ++i) {
					System.out.println();
					System.out.println(String.format("Result %1$s:", i + 1));
					System.out.println(String.format("Url: %1$s", results[i].getURL()));
					System.out.println(String.format("Percents: %1$s", results[i].getPercents()));
					System.out.println(String.format("CopiedWords: %1$s", results[i].getNumberOfCopiedWords()));
				}
			}
		} catch (CommandFailedException copyleaksException) {
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
<h5>Referenced Assemblies:</h5>
<ul>
<li><a href="https://commons.apache.org/proper/commons-cli/">commons-cli-1.3.1.jar</a></li>
<li><a href="https://github.com/google/gson">gson-2.4.jar</a></li>
<li><a href="https://github.com/FasterXML/jackson-annotations">jackson-annotations-2.6.0.jar</a></li>
</ul>

<h3>Read More</h3>
<ul>
<li><a href="https://api.copyleaks.com/Guides/HowToUse">Copyleaks API guide</a></li>
<li><a href="https://api.copyleaks.com/Documentation">Copyleaks API documentation</a></li>
</ul>
