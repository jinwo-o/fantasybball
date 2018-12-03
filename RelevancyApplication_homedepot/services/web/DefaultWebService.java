package ca.homedepot.relevancy.services.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ca.homedepot.relevancy.config.AppConfig;

@Component
public class DefaultWebService implements WebService {

	@Autowired
	private final AppConfig config;
	
	private static final String AUTHORIZATON = "Authorization";
	
	private String get(final URI uri, final HttpHeaders headers) {
	    final RestTemplate restTemplate = config.restTemplate();
	    final HttpEntity<?> entity = new HttpEntity<>(headers);	    
	    final ResponseEntity<String> response = restTemplate.<String>exchange(uri, HttpMethod.GET, entity, String.class);
	    return response.getBody();
	}
	
	private String post(final URI uri, final HttpHeaders headers) {
		final RestTemplate restTemplate = config.restTemplate();
		final HttpEntity<?> entity = new HttpEntity<>(headers);
		final ResponseEntity<String> response = restTemplate.<String> exchange(uri, HttpMethod.POST, entity, String.class);
		return response.getBody();
	}
	
	@Override
	public String getJson(final URI uri) {
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    return get(uri, headers);
	}
	
	@Override
	public String postJson(final URI uri) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return post(uri, headers);
	}
	
	@Override
	public String getJson(final URI uri, final OAuth2AccessToken accessToken) {
	    final HttpHeaders headers = new HttpHeaders();
	    headers.set(AUTHORIZATON, accessToken.getTokenType() + " " + accessToken.getValue());
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    return get(uri, headers);
	}
	
	public DefaultWebService(final AppConfig config) {
		this.config = config;
	}
}
