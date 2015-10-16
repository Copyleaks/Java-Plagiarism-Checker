package copyleaks.sdk.api;
import java.net.URI;
import java.net.URISyntaxException;

public class Resources {
	public static final String Encoding = "UTF-8";
	public static final int RequestsTimeout = 600000; //In Milliseconds. Wait up to 10 minutes to response.
	public static final String ServiceEntryPoint = "https://api.copyleaks.com/";	//Service URL
	public static final String ServiceVersion = "v1";	//Current version of the API
	public static final String USER_AGENT = "CopyleaksSDK/1.0";// User agent
	public static final String LOGIN_PAGE = String.format("%1$s/account/login", Resources.ServiceVersion);
	public static URI SERVICE_URI;
	static {
		try{
			SERVICE_URI = new URI(ServiceEntryPoint);
		}
		catch(URISyntaxException ex)
		{
			
		}
	}
}
