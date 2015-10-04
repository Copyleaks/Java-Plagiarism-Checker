package copyleaks.sdk.api.exceptions;

import java.security.AccessControlException;

import copyleaks.sdk.api.models.LoginToken;

public class TokenExpiredException extends AccessControlException
{
	private static final long serialVersionUID = 1L;
	public TokenExpiredException(LoginToken token)
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