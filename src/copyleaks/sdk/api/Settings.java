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

public class Settings
{
	public static final String Encoding = "UTF-8";
	
	public static final int NetworkReadTimeout = 30000; 
	public static final int NetworkConnectTimeout = 15000; 
	
	public static final String ServiceEntryPoint = "https://api.copyleaks.com"; // Service URL.
			
	public static final String ServiceVersion = "v1"; // Current version of the
														// API
	
	public static final String BusinessesServicePage = "businesses";
	public static final String AcademicServicePage = "academic";
	public static final String WebsitesServicePage = "websites";
	public static final String DownloadsServicePage = "downloads";
	
	public static final String USER_AGENT = "CopyleaksJavaSDK/1.1";// User agent
	public static final String DateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
}
