package ca.homedepot.relevancy.services.oauth;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import ca.homedepot.relevancy.model.ServerEnvironment;

public interface OAuthService {
	OAuth2AccessToken getAccessToken(ServerEnvironment server);
	OAuth2AccessToken refreshAccessToken(ServerEnvironment server);
}
