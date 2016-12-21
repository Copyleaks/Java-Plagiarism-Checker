
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

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URLDecoder;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import copyleaks.sdk.api.*;
import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.models.ComparisonResult;
import copyleaks.sdk.api.models.ProcessOptions;
import copyleaks.sdk.api.models.ResultRecord;

public class Main
{
	private final static String ARG_EMAIL_KEY = "e", ARG_EMAIL_VALUE = "email";
	private final static String ARG_APIKEY_KEY = "k", ARG_APIKEY_VALUE = "key";
	private final static String ARG_URL_KEY = "l", ARG_URL_VALUE = "url";
	private final static String ARG_LOCALDOCUMENT_KEY = "f", ARG_LOCALDOCUMENT_VALUE = "local_document";
	private final static String ARG_HELP_KEY = "h", ARG_HELP_VALUE = "help";

	private static void SetupOptions(Options options)
	{
		options.addOption(ARG_EMAIL_KEY, ARG_EMAIL_VALUE, true, "Required. Copyleaks account email address");
		options.addOption(ARG_APIKEY_KEY, ARG_APIKEY_VALUE, true, "Required. Copyleaks account API key");
		options.addOption(ARG_URL_KEY, ARG_URL_VALUE, true, "Optional*. URL for scanning");
		options.addOption(ARG_LOCALDOCUMENT_KEY, ARG_LOCALDOCUMENT_VALUE, true,
				"Optional*. Local document file to upload. Allowed formats (txt, rtf, doc, docx, pdf, ...).");
		options.addOption(ARG_HELP_KEY, ARG_HELP_VALUE, false, "Prints usage options.");
	}

	/**
	 * Print usage information to provided OutputStream.
	 * 
	 * @param applicationName
	 *            Name of application to list in usage.
	 * @param options
	 *            Command-line options to be part of usage.
	 * @param out
	 *            OutputStream to which to write the usage information.
	 */
	public static void printUsage(final String applicationName, final Options options, final OutputStream out)
	{
		final PrintWriter writer = new PrintWriter(out);
		String header = "\nInput Parameters:\n\n";
		String footer = "\n* Using one of those optionals is REQUIRED: '" + ARG_URL_VALUE + "', '"
				+ ARG_LOCALDOCUMENT_KEY + "'.\n\n" + "Please report issues at https://Copyleaks.com/Support/ContactUs";
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("CopyleaksAPI.exe", header, options, footer, true);
		writer.close();
	}

	static String DisplayBar(int i)
	{
		StringBuilder sb = new StringBuilder();

		int x = i / 2;
		sb.append("|");
		for (int k = 0; k < 50; k++)
			sb.append(((x <= k) ? " " : "="));
		sb.append("|");

		return sb.toString();
	}

	public static void main(String[] args)
	{
		// For more information, visit Copyleaks How-To page:
		// https://api.copyleaks.com/Guides/HowToUse
		final CommandLineParser cmdLineGnuParser = new DefaultParser();
		final Options options = new Options();
		SetupOptions(options);
		CommandLine commandLine;
		try
		{
			commandLine = cmdLineGnuParser.parse(options, args);
			if (commandLine.hasOption(ARG_HELP_KEY))
			{
				printUsage("SampleCode.exe", options, System.out);
				return;
			}
			if (!commandLine.hasOption(ARG_EMAIL_KEY))
			{
				System.err.println("Missing argument: \"email\".");
				return;
			}
			else if (!commandLine.hasOption(ARG_APIKEY_KEY))
			{
				System.err.println("Missing argument: \"key\".");
				return;
			}
			else if (!commandLine.hasOption(ARG_URL_KEY) && !commandLine.hasOption(ARG_LOCALDOCUMENT_KEY))
			{
				System.err.println("Missing mandatory argument. Use one of those: \"url\" OR \"local-document\".");
				return;
			}
			else if (commandLine.hasOption(ARG_URL_KEY) && commandLine.hasOption(ARG_LOCALDOCUMENT_KEY))
			{
				System.err
						.println("Conflicting arguments. Only ONE of those is allowed: \"url\" OR \"local-document\".");
				return;
			}
		}
		catch (ParseException e)
		{
			System.err.println(
					"Unable to parse input arguments. Please try agian or USER \"--help\" for more information.");
			return;
		}

		try
		{
			CopyleaksCloud copyleaks = new CopyleaksCloud(eProduct.Websites);
			System.out.print("Login to Copyleaks cloud...");
			copyleaks.Login(commandLine.getOptionValue(ARG_EMAIL_KEY), commandLine.getOptionValue(ARG_APIKEY_KEY));
			System.out.println("Done!");

			System.out.print("Checking account balance...");
			int creditsBalance = copyleaks.getCredits();
			System.out.println("Done (" + creditsBalance + " credits)!");
			if (creditsBalance == 0)
			{
				System.out.println(
						"ERROR: You have insufficient credits for scanning content! (current credits balance = "
								+ creditsBalance + ")");
				return;
			}

			ProcessOptions scanOptions = new ProcessOptions();

			// In Sandbox scan you don't need credits.
			// Read more @
			// https://api.copyleaks.com/Documentation/RequestHeaders#sandbox-mode
			// After you finish the integration with Copyleaks, remove this
			// line.
			scanOptions.setSandboxMode(true);

			ResultRecord[] results;
			CopyleaksProcess createdProcess;

			if (commandLine.hasOption(ARG_URL_KEY))
			{
				String val = commandLine.getOptionValue(ARG_URL_KEY);
				createdProcess = copyleaks.CreateByUrl(new URI(val), scanOptions);
			}
			else // commandLine.hasOption(ARG_LOCALDOCUMENT_KEY)
			{
				String val = commandLine.getOptionValue(ARG_LOCALDOCUMENT_KEY);
				createdProcess = copyleaks.CreateByFile(new File(val), scanOptions);
			}

			// Waiting for process completion...
			System.out.println("Scanning...");
			int percents = 0;
			while (percents != 100 && (percents = createdProcess.getCurrentProgress()) <= 100)
			{
				System.out.print("\r" + DisplayBar(percents) + " " + percents + "%");

				if (percents != 100)
					Thread.sleep(5000);
			}
			System.out.println();

			results = createdProcess.GetResults();

			if (results.length == 0)
			{
				System.out.println("No results.");
			}
			else
			{
				for (int i = 0; i < results.length; ++i)
				{
					System.out.println();
					System.out.println("------------------------------------------------");
					System.out.println(String.format("Title: %1$s", results[i].getTitle()));
					System.out.println(String.format("Information: %1$s copied words (%2$s%%)",
							results[i].getNumberOfCopiedWords(), results[i].getPercents()));
					System.out.println(String.format("Introduction: %1$s", results[i].getIntroduction()));
					System.out.println(String.format("Url: %1$s", results[i].getURL()));
					System.out.println(String.format("Comparison link: %1$s", results[i].getEmbededComparison()));
					
					// Optional: Download result full text. Uncomment to activate
					// System.out.println("Result full-text:");
					// System.out.println("*****************");
					// System.out.println(createdProcess.DownloadResultText(results[i]));
				}
			}

			// Optional: Download source full text. Uncomment to activate.
			//System.out.println("Source full-text:");
			//System.out.println("*****************");
			//System.out.println(createdProcess.DownloadSourceText());
		}
		catch (CommandFailedException copyleaksException)
		{
			System.out.println("Failed!");
			System.out.format("*** Error (%d):\n", copyleaksException.getCopyleaksErrorCode());
			System.out.println(copyleaksException.getMessage());
		}
		catch (Exception ex)
		{
			System.out.println("Failed!");
			System.out.println("Unhandled Exception");
			System.out.println(ex);
		}
	}
}