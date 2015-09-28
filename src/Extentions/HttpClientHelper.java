//package Extentions;
//
//import org.apache.http.client.*;
//import org.apache.http.client.methods.HttpPost;
//
//import Models.LoginToken;
//
//public class HttpClientHelper {
//	public void SetCopyleaksHttpClient(HttpPost post, String contentType)
//	{
//		post.addHeader("CopyleaksSDK/1.0");
//		client.DefaultRequestHeaders.Accept.Clear();
//		client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue(contentType));
//		client.DefaultRequestHeaders.UserAgent.ParseAdd("CopyleaksSDK/1.0");
//	}
//	public void SetCopyleaksClient(HttpClient client, String contentType, LoginToken SecurityToken)
//	{
//		client.SetCopyleaksHttpClient(contentType);
//		client.DefaultRequestHeaders.Add("Authorization", String.format("%1$s %2$s", "Bearer", SecurityToken.Token));
//	}
//}
