
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import Exceptions.CommandFailedException;
import Models.ResultRecord;

public class SampleCode {

	public static void main(String[] args)
			throws ClientProtocolException, IOException, JSONException, CommandFailedException, URISyntaxException {
		// For more information, visit Copyleaks How-To page:
		// https://api.copyleaks.com/Guides/HowToUse

		// Creating Copyleaks account: https://copyleaks.com/Account/Signup
		// Use your account information:
		String username = "<Your-Username>";
		String APIKey = "<Your-Key>"; // Generate your
																// API Key:
																// https://copyleaks.com/Account/Manage
		Scanner scanner = new Scanner(username, APIKey);
		URI url_to_scan = new URI("<Path-To-File>");

		try {
			ResultRecord[] results = scanner.Scan(url_to_scan);
			if (results.length == 0) {
				System.out.println("\tNo results.");
			} else {
				for (int i = 0; i < results.length; ++i) {
					System.out.println();
					System.out.println(String.format("Result %1$s:", i + 1));
					System.out.println(String.format("Domain: %1$s", results[i].getDomain()));
					System.out.println(String.format("Url: %1$s", results[i].getURL()));
					System.out.println(String.format("Precents: %1$s", results[i].getPrecents()));
					System.out.println(String.format("CopiedWords: %1$s", results[i].getNumberOfCopiedWords()));
				}
			}
			// } catch (CommandFailedException theError) {
			// System.out.println("\tFailed!");
			// System.out.println("+Error Description:");
			// System.out.println(String.format("%1$s", theError.getMessage()));
		} catch (Exception ex) {
			System.out.println("\tFailed!");
			System.out.println("Unhandled Exception");
			System.out.println(ex);
		}
	}
}