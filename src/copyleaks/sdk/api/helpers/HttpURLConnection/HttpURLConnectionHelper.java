package copyleaks.sdk.api.helpers.HttpURLConnection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import org.apache.http.NameValuePair;

public final class HttpURLConnectionHelper
{
	public static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params)
		{
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
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
