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

package copyleaks.sdk.api;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import copyleaks.sdk.api.exceptions.CopyleaksException;
import copyleaks.sdk.api.models.request.LoginResponse;	

/**
 * 
 * Connect to Copyleaks identity server, get your token and perform readonly operations
 *
 */
public class CopyleaksIdentityApi extends CopyleaksBaseApi{

	/**
	 * Initialize a new client connection to Copyleaks identity API 
	 */
    public CopyleaksIdentityApi(){
    	super();
    }
    
    /**
     * Initialize a new secured connection to Copyleaks identity server with a client certificate
     */
    public CopyleaksIdentityApi(String clientCertificatePath, String certificatePassword) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
    	super(clientCertificatePath, certificatePassword);
    }

    /**
     * Login to Copyleaks API and get your access token using your email and api key
     */
    public LoginResponse Login(String email, String key) throws ClientProtocolException, IOException, ParseException, CopyleaksException {
    	String url = String.format("%s%s/account/login/api", Settings.IdentityEndPoint, Settings.ApiVersion);
    	Map<String, String> dic = new HashMap<String, String>();
		dic.put("email", email);
		dic.put("key", key);
		
		HttpResponse response = this.client.post(url, dic);
		return this.client.deserelizeResponseBody(response, LoginResponse.class);
    }

}