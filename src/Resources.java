import java.net.URI;
import java.net.URISyntaxException;

public class Resources {
	public static int RequestsTimeout = 600000; //In Milliseconds. Wait up to 10 minutes to response.
	public static String ServiceEntryPoint = "https://api.copyleaks.com/";	//Service URL
	//public static String ServiceEntryPoint = "http://localhost:56214/";	//Service URL
	public static String ServiceVersion = "v1";	//Current version of the API
	public static String USER_AGENT = "CopyleaksSDK/1.0";// User agent
	public static String LOGIN_PAGE = String.format("%1$s/account/login", Resources.ServiceVersion);
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
