package copyleaks.sdk.api.exceptions;

import java.security.AccessControlException;

import copyleaks.sdk.api.models.LoginToken;

public class SecurityTokenException 
	extends AccessControlException
{
	private static final long serialVersionUID = 1L;
	public SecurityTokenException()
	{
		super("Security token is missing!");
		this.setToken(null);
	}
	public SecurityTokenException(LoginToken token)
	{
		super(String.format("This token expired on '%1$s'!", token.getExpire()));
		this.setToken(token);
	}

	public LoginToken Token;
	LoginToken getToken() {
		return Token;
	}

	private void setToken(LoginToken token) {
		Token = token;
	}
}