package copyleaks.sdk.api.helpers.HttpURLConnection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Scanner;

import copyleaks.sdk.api.Settings;

public final class HttpURLConnectionHelper
{
	public static String getQuery(Map<String, String> dic) throws UnsupportedEncodingException
	{
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (Map.Entry<String,String> entry : dic.entrySet())
		{
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(entry.getKey(), Settings.Encoding));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), Settings.Encoding));
		}

		return result.toString();
	}

	public static String convertStreamToString(java.io.InputStream is)
	{
		@SuppressWarnings("resource")
		java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";

	}
}
