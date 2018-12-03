package ca.homedepot.relevancy.services.oauth;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import com.google.gson.annotations.SerializedName;

public class HomedepotcaOAuthAccessToken implements OAuth2AccessToken {
	
	@SerializedName("access_token")
	private String value;
	
	@SerializedName("token_type")
	private String tokenType;
	
	@SerializedName("expires_in")
	private int expiresIn;
	
	public HomedepotcaOAuthAccessToken(String value, String tokenType, int expiresIn) {
		this.value = value;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
	}
	
	@Override
	public Map<String, Object> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuth2RefreshToken getRefreshToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTokenType() {
		return tokenType;
	}

	@Override
	public boolean isExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Date getExpiration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getExpiresIn() {
		return expiresIn;
	}

	@Override
	public String getValue() {
		return value;
	}
}
