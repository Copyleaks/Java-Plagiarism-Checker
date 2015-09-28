package Models;

import java.util.Date;

import com.fasterxml.jackson.annotation.*;
import com.google.gson.annotations.SerializedName;

public class LoginToken {
	public LoginToken(String token, Date issued, Date expire) {
		setToken(token);
		setIssued(issued);
		setExpire(expire);
	}

	@SerializedName("access_token")
	private String Token;

	public String getToken() {
		return Token;
	}

	private void setToken(String token) {
		this.Token = token;
	}

	@SerializedName(".issued")
	private Date Issued;

	Date getIssued() {
		return Issued;
	}

	private void setIssued(Date issued) {
		this.Issued = issued;
	}

	@SerializedName(".expires")
	private Date Expire;

	public Date getExpire() {
		return Expire;
	}

	private void setExpire(Date expire) {
		this.Expire = expire;
	}

	@SerializedName("userName")
	public String UserName;

	public String getUserName() {
		return UserName;
	}

	private void setUserName(String username) {
		this.UserName = username;
	}

	@SerializedName("token_type")
	public String TokenType;

	public String getTokenType() {
		return TokenType;
	}

	void setUsername(String tokenType) {
		this.TokenType = tokenType;
	}

	/// <summary>
	/// Validate that the token is valid. If isn't valid, throw
	/// UnauthorizedAccessException.
	/// </summary>
	/// <exception cref="UnauthorizedAccessException">This token is
	/// expired</exception>
	public void Validate() throws Exception {
		Date currentDate = new Date();
		if (currentDate.after(this.Expire))
			throw new Exception(String.format("This token expired on %1$s", this.Expire));

	}

	@Override
	public String toString() {
		return this.getToken();
	}
}
