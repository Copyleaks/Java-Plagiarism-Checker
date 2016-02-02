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
</ol>
<h3>Configuration</h3>
<p>You can configure Copyleaks SDK in one of two ways:<p/>
<ul>
<li>Download the code to your workspace – choose this option if you want to alter the code and add capabilities to match your own specific needs. Download the project from here, open it using Eclipse and choose ‘Import Project’ from your own workspace. Then, follow the next steps:
<ol>
<li>Go to ‘General’, choose ‘Existing projects into workspace’ and press next.</li>
<li>Fill in the details of the project you just downloaded under ‘Select root directory’.</li>
<li>Make sure that the project Copyleaks API is checked.</li>
<li>Press ‘Finish’.</li>
</ol>
<p>Now you have the project in your workspace and you can make alternations that fits your own specific needs. As default, the project includes the file ‘Main.java’. This file will allow you to easily run and check your project.</p></li>

<li>Download the JAR files – choose this option if you want to use the code as-is. Download the <a href="https://api.copyleaks.com/downloads/sdk/java/CopyleaksAPI.Java.JARs.v2.0.zip">JAR files</a> and then, follow the next steps:
<ol>
<li>Extract the files.</li>
<li>Open Eclipse and select the process you want to integrate with Copyleaks API.</li>
<li>Press the right mouse key and choose ‘Build Path’ -> ‘Configure Build Path’.</li>
<li>In the window that opened choose the tab ‘Libraries’.</li>
<li>Press on ‘Add External JAR’.</li>
<li>Browse to the libraries with your extracted JAR files and choose to add them.</li>
</ol>
</li>
</ul>
<p>Now you can use Copyleaks API in your project. If you are working with our latest version, you can also run the libraries using the code found down on this page.</p>
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
