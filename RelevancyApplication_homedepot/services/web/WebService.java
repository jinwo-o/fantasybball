package ca.homedepot.relevancy.services.web;

import java.net.URI;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface WebService {
	String getJson(URI uri);
	String postJson(URI uri);
	String getJson(URI uri, OAuth2AccessToken accessToken);
}
