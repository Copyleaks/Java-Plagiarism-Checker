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

package copyleaks.sdk.api.models;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map.Entry;

public class ProcessOptions
{
	public ProcessOptions()
	{
		this.setCustomFields(new HashMap<String, String>());
	}

	private URI HttpCallback;

	public URI getHttpCallback()
	{
		return HttpCallback;
	}

	public void setHttpCallback(URI httpCallback)
	{
		HttpCallback = httpCallback;
	}
	
	private URI InProgressResultsHttpCallback;

	public URI getInProgressResultsHttpCallback()
	{
		return InProgressResultsHttpCallback;
	}

	public void setInProgressResultsHttpCallback(URI uri)
	{
		InProgressResultsHttpCallback = uri;
	}

	private HashMap<String, String> CustomFields;

	public HashMap<String, String> getCustomFields()
	{
		return CustomFields;
	}

	public void setCustomFields(HashMap<String, String> customFields)
	{
		CustomFields = customFields;
	}
	
	private boolean SandboxMode;
	
	public boolean isSandboxMode()
	{
		return SandboxMode;
	}

	public void setSandboxMode(boolean sandboxMode)
	{
		SandboxMode = sandboxMode;
	}
	
	private String EmailCallback;
	public String getEmailCallback()
	{
		return EmailCallback;
	}

	public void setEmailCallback(String emailCallback)
	{
		EmailCallback = emailCallback;
	}
	
	protected final String COPYLEAKS_HEADER_PREFIX = "copyleaks-";
	public void addHeaders(HttpURLConnection client)
	{
		if (this.getHttpCallback() != null)
		{
			client.addRequestProperty(COPYLEAKS_HEADER_PREFIX + "http-completion-callback",
					this.getHttpCallback().toString());
			// Add HTTP callback to the request header.
		}
		
		if (this.getInProgressResultsHttpCallback() != null)
		{
			client.addRequestProperty(COPYLEAKS_HEADER_PREFIX + "in-progress-new-result",
					this.getInProgressResultsHttpCallback().toString());
			// Add HTTP in progress result callback to the request header.
		}
		
		if (this.getEmailCallback() != null)
		{
			client.addRequestProperty(COPYLEAKS_HEADER_PREFIX + "email-callback",
					this.getEmailCallback().toString());
			// Add Email callback to the request header.
		}

		if (this.getCustomFields() != null)
		{
			final String CLIENT_CUSTOM_PREFIX = COPYLEAKS_HEADER_PREFIX + "client-custom-";
			for (Entry<String, String> entry : this.getCustomFields().entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				client.addRequestProperty(CLIENT_CUSTOM_PREFIX + key, value);
			}
		}

		if (this.isSandboxMode())
		{
			final String SANDBOX_MODE_HEADER = COPYLEAKS_HEADER_PREFIX + "sandbox-mode";
			client.addRequestProperty(SANDBOX_MODE_HEADER, "");
		}
	}
}
