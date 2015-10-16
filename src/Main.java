
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import copyleaks.sdk.api.Scanner;
import copyleaks.sdk.api.models.ResultRecord;

public class Main
{
	private final static String ARG_USERNAME_KEY = "u", ARG_USERNAME_VALUE = "username";
	private final static String ARG_APIKEY_KEY = "k", ARG_APIKEY_VALUE = "key";
	private final static String ARG_URL_KEY = "l", ARG_URL_VALUE = "url";
	private final static String ARG_LOCALDOCUMENT_KEY = "f", ARG_LOCALDOCUMENT_VALUE = "local_document";
	private final static String ARG_HELP_KEY = "h", ARG_HELP_VALUE = "help";

	private static void SetupOptions(Options options)
	{
		options.addOption(ARG_USERNAME_KEY, ARG_USERNAME_VALUE, true, "Required. Copyleaks account username");
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
		String footer = "\n* Using one of those optionals is REQUIRED: '" + ARG_URL_VALUE + "', '" + ARG_LOCALDOCUMENT_KEY + "'.\n\n" +
				"Please report issues at https://Copyleaks.com/Support/ContactUs";
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("CopyleaksAPI.exe", header, options, footer, true);
		writer.close();
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
			if (!commandLine.hasOption(ARG_USERNAME_KEY))
			{
				System.err.println("Missing argument: \"username\".");
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
			Scanner scanner = new Scanner(commandLine.getOptionValue(ARG_USERNAME_KEY),
					commandLine.getOptionValue(ARG_APIKEY_KEY));
			
			int creditsBalance = scanner.getCredits();
			if (creditsBalance == 0)
			{
				System.out.println("ERROR: You have insufficient credits for scanning content! (current credits balance = " + creditsBalance + ")");
				return;
			}

			ResultRecord[] results;
			if (commandLine.hasOption(ARG_URL_KEY))
			{
				String val = commandLine.getOptionValue(ARG_URL_KEY);
				try
				{
					results = scanner.ScanUrl(val);
				}
				catch (URISyntaxException e)
				{
					System.err.println("Bad input: " + ARG_URL_KEY + " ('" + val + "')");
					return;
				}
			}
			else // commandLine.hasOption(ARG_LOCALDOCUMENT_KEY)
			{
				String val = commandLine.getOptionValue(ARG_LOCALDOCUMENT_KEY);
				results = scanner.ScanLocalOcrFile(val);
			}

			if (results.length == 0)
			{
				System.out.println("\tNo results.");
			}
			else
			{
				for (int i = 0; i < results.length; ++i)
				{
					System.out.println();
					System.out.println(String.format("Result %1$s:", i + 1));
					System.out.println(String.format("Domain: %1$s", results[i].getDomain()));
					System.out.println(String.format("Url: %1$s", results[i].getURL()));
					System.out.println(String.format("Precents: %1$s", results[i].getPrecents()));
					System.out.println(String.format("CopiedWords: %1$s", results[i].getNumberOfCopiedWords()));
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println("\tFailed!");
			System.out.println("Unhandled Exception");
			System.out.println(ex);
		}
	}
}