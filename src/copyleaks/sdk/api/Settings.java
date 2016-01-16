package copyleaks.sdk.api;

public class Settings
{
	public static final String Encoding = "UTF-8";
	public static final int RequestsTimeout = 30000; // In Milliseconds. Wait up
														// to 30 seconds for
														// response.
	
	public static final String ServiceEntryPoint = "https://api.copyleaks.com/"; // Service URL.
			
	public static final String ServiceVersion = "v1"; // Current version of the
														// API
	public static final String ServicePage = "detector";
	public static final String USER_AGENT = "CopyleaksSDK/1.0";// User agent
	public static final String DateFormat = "dd/MM/yyyy HH:mm:ss";
}
