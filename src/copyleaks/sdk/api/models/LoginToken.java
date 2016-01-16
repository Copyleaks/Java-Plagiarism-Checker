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

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

import copyleaks.sdk.api.exceptions.SecurityTokenException;

public class LoginToken implements Serializable
{
	/**
	 * For Serializable implementation.
	 */
	private static final long serialVersionUID = 1L;
	
	public LoginToken(String token, Date issued, Date expire)
	{
		setTemporarySecurityCode(token);
		setIssued(issued);
		setExpire(expire);
	}

	@SerializedName("access_token")
	private String TemporarySecurityCode;

	public String getTemporarySecurityCode()
	{
		return TemporarySecurityCode;
	}

	private void setTemporarySecurityCode(String token)
	{
		this.TemporarySecurityCode = token;
	}

	@SerializedName(".issued")
	private Date Issued;

	Date getIssued()
	{
		return Issued;
	}

	private void setIssued(Date issued)
	{
		this.Issued = issued;
	}

	@SerializedName(".expires")
	private Date Expire;

	public Date getExpire()
	{
		return Expire;
	}

	private void setExpire(Date expire)
	{
		this.Expire = expire;
	}

	@SerializedName("userName")
	private String UserName;

	public String getUserName()
	{
		return UserName;
	}

	@SerializedName("token_type")
	private String TokenType;

	public String getTokenType()
	{
		return TokenType;
	}

	void setUsername(String tokenType)
	{
		this.TokenType = tokenType;
	}

	/// <summary>
	/// Validate that the token is valid. If isn't valid, throw
	/// UnauthorizedAccessException.
	/// </summary>
	/// <exception cref="UnauthorizedAccessException">This token is
	/// expired</exception>
	private void Validate() 
		throws SecurityTokenException
	{
		/*
		Date currentDate = new Date();
		if (currentDate.after(this.Expire))
			throw new SecurityTokenException(this);
			*/
	}
	
	public static void ValidateToken(LoginToken token)
	{
		if (token == null)
			throw new SecurityTokenException();
		else
			token.Validate();
	}

	@Override
	public String toString()
	{
		return this.getTemporarySecurityCode();
	}
}
