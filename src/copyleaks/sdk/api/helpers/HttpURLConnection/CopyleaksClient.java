package copyleaks.sdk.api.helpers.HttpURLConnection;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import copyleaks.sdk.api.RequestMethod;
import copyleaks.sdk.api.Resources;
import copyleaks.sdk.api.exceptions.SecurityTokenException;
import copyleaks.sdk.api.helpers.FileHelpers;
import copyleaks.sdk.api.models.LoginToken;

public class CopyleaksClient
{
	public static HttpsURLConnection getClient(URL url, RequestMethod methodType, String requestFormat, String responseFormat)
			throws SecurityTokenException, IOException
	{
		return getClient(url, null, methodType, requestFormat, responseFormat);
	}

	public static HttpsURLConnection getClient(URL url, LoginToken securityToken, RequestMethod methodType,
			String requestFormat, String responseFormat) 
					throws IOException, SecurityTokenException
	{
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.addRequestProperty("Accept-Charset", Resources.Encoding);
		conn.addRequestProperty("Content-Type", requestFormat);
		conn.addRequestProperty("Accept", responseFormat);
		conn.addRequestProperty("User-Agent", Resources.USER_AGENT);
		conn.setRequestProperty("Cache-Control", "no-cache");
		if (securityToken != null)
		{
			LoginToken.ValidateToken(securityToken);
			conn.addRequestProperty("Authorization",
					String.format("Bearer %1$s", securityToken.getTemporarySecurityCode()));
		}
		conn.setRequestMethod(methodType.toString());
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		return conn;
	}

	public static class HandleString
	{
		public static void attach(HttpsURLConnection conn, String content)
				throws IOException, UnsupportedEncodingException
		{
			try (
					DataOutputStream os = new DataOutputStream(conn.getOutputStream());
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, Resources.Encoding));
				)
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
		
		public static void attach(HttpsURLConnection conn, File file) 
				throws IOException, UnsupportedEncodingException
		{
			final String boundary =  "file." + FileHelpers.getFileExtension(file);
			final String attachmentName = FileHelpers.getFileName(file);
			final String attachmentFileName = file.getName();
			
			try (
					DataOutputStream os = new DataOutputStream(conn.getOutputStream());
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, Resources.Encoding));
					FileInputStream inputFile = new FileInputStream(file);
				)
			{
				// File prefix
				os.writeBytes(twoHyphens + boundary + crlf);
				os.writeBytes("Content-Disposition: form-data; name=\"" +
				    attachmentName + "\";filename=\"" + 
				    attachmentFileName + "\"" + crlf);
				os.writeBytes(crlf);
				
				// The file
				final int BUFFER_SIZE = 4 * 1024;
				byte[] buffer = new byte[BUFFER_SIZE];
				int real_buffer_size;
				while ((real_buffer_size = inputFile.read(buffer,  0,  BUFFER_SIZE)) > 0)
					os.write(buffer, 0, real_buffer_size);
				
				// File postfix
				os.writeBytes(crlf);
				os.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
				
				writer.flush();
			}
		}
	}
}
