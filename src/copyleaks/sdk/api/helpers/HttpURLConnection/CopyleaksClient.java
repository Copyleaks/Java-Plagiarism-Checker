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

package copyleaks.sdk.api.helpers.HttpURLConnection;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import copyleaks.sdk.api.RequestMethod;
import copyleaks.sdk.api.Settings;
import copyleaks.sdk.api.exceptions.SecurityTokenException;
import copyleaks.sdk.api.models.LoginToken;

public class CopyleaksClient
{
	private static String AcceptLanguage = "en-US"; // Default language

	public static String getLanguage()
	{
		return AcceptLanguage;
	}

	public static void setLanguage(String value)
	{
		AcceptLanguage = value;
	}

	public static HttpURLConnection getClient(URL url, RequestMethod methodType, String requestFormat,
			String responseFormat) throws SecurityTokenException, IOException
	{
		return getClient(url, null, methodType, requestFormat, responseFormat);
	}

	public static HttpURLConnection getClient(URL url, LoginToken securityToken, RequestMethod methodType,
			String requestFormat, String responseFormat) throws IOException, SecurityTokenException
	{
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.addRequestProperty("Accept-Charset", Settings.Encoding);
		conn.addRequestProperty("Content-Type", requestFormat);
		conn.addRequestProperty("Accept", responseFormat);
		conn.addRequestProperty("User-Agent", Settings.USER_AGENT);
		conn.setRequestProperty("Cache-Control", "no-cache");
		conn.setRequestProperty("Accept-Language", getLanguage());

		conn.setConnectTimeout(Settings.NetworkConnectTimeout);
		conn.setReadTimeout(Settings.NetworkReadTimeout);
		
		if (securityToken != null)
		{
			LoginToken.ValidateToken(securityToken);
			conn.addRequestProperty("Authorization",
					String.format("Bearer %1$s", securityToken.getTemporarySecurityCode()));
		}
		conn.setRequestMethod(methodType.toString());
		conn.setUseCaches(false);

		if (methodType.toString() == "POST") {
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
		}

		return conn;
	}

	public static class HandleString
	{
		public static void attach(HttpURLConnection conn, String content)
				throws IOException, UnsupportedEncodingException
		{
			try (DataOutputStream os = new DataOutputStream(conn.getOutputStream());
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, Settings.Encoding));)
			{
				if (content != null)
					writer.write(content);
				writer.flush();
			}
		}
	}

	public static class HandleFile
	{
		final static String crlf = "\r\n";
		final static String twoHyphens = "--";

		public static void attach(HttpURLConnection conn, InputStream stream, String filename, String fileExtension) 
				throws IOException, UnsupportedEncodingException
		{
			final String boundary = "file." + fileExtension;
			final String attachmentFileName = filename + "." + fileExtension;

			try (
					DataOutputStream os = new DataOutputStream(conn.getOutputStream());
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, Settings.Encoding));
				)
			{
				// File prefix
				os.writeBytes(twoHyphens + boundary + crlf);
				os.writeBytes("Content-Disposition: form-data; name=\"" + filename + "\";filename=\""
						+ attachmentFileName + "\"" + crlf);
				os.writeBytes(crlf);

				// The file
				final int BUFFER_SIZE = 4 * 1024;
				byte[] buffer = new byte[BUFFER_SIZE];
				int real_buffer_size;
				while ((real_buffer_size = stream.read(buffer, 0, BUFFER_SIZE)) > 0)
					os.write(buffer, 0, real_buffer_size);

				// File postfix
				os.writeBytes(crlf);
				os.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

				writer.flush();
			}
			finally
			{
				if (stream != null) stream.close();
			}
		}
	}
}
