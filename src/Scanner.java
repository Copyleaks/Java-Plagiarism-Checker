import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;

import Exceptions.CommandFailedException;
import Models.LoginToken;
import Models.ResultRecord;

public class Scanner {
		public String Username;

		public String ApiKey;

		private LoginToken Token;

		public String getUsername() {
			return Username;
		}

		public void setUsername(String username) {
			Username = username;
		}

		public String getApiKey() {
			return ApiKey;
		}

		public void setApiKey(String apiKey) {
			ApiKey = apiKey;
		}

		public LoginToken getToken() {
			return Token;
		}

		public void setToken(LoginToken token) {
			Token = token;
		}

		public Scanner() {

		}

		public Scanner(String username, String APIKey)
				throws ClientProtocolException, IOException, JSONException, CommandFailedException {
			setToken(UserAuthentication.Login(username, APIKey)); // This
																	// security
																	// token
																	// can
																	// be
																	// use
																	// multiple
																	// times,
																	// until
																	// it
																	// will
																	// be
																	// expired
																	// (48
																	// hours).
		}

		public ResultRecord[] Scan(URI url) throws Exception {
			return Scan(url.toString());
		}

		public ResultRecord[] Scan(File file) throws Exception {
			if (!file.exists())
				throw new FileNotFoundException();

			return Scan(file.getAbsolutePath());
		}

		private ResultRecord[] Scan(String url) throws Exception {
			// Create a new process on server.
			FileBody fileBody = new FileBody(new File(url));
			Detector detector = new Detector(this.Token);
			ScannerProcess process = detector.CreateByOCR(fileBody);

			// Waiting to process to be finished.
			while (!process.IsCompleted())
				Thread.sleep(1000);

			// Getting results.
			return process.GetResults();
		}
	}