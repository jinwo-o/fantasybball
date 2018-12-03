package ca.homedepot.relevancy.services.oauth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import ca.homedepot.relevancy.config.properties.OAuthClientProperties;
import ca.homedepot.relevancy.model.ServerEnvironment;
import ca.homedepot.relevancy.services.server.ServerService;
import ca.homedepot.relevancy.services.web.WebService;

@Component
public class DefaultOAuthService implements OAuthService {

	private static Logger LOG = LoggerFactory.getLogger(DefaultOAuthService.class);
	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String GRANT_TYPE = "grant_type";
	private static final String CLIENT_CREDENTIALS = "client_credentials";

	private final String clientId;
	private final Map<ServerEnvironment, String> clientSecrets;
	private final Map<ServerEnvironment, OAuth2AccessToken> serverTokenMap;

	@Autowired
	private final WebService webService;

	@Autowired
	private final ServerService serverService;

	public DefaultOAuthService(final WebService webService, final ServerService serverService,
			final OAuthClientProperties oauthClientProperties) {
		this.webService = webService;
		this.serverService = serverService;
		this.clientId = oauthClientProperties.getId();
		final Properties oauthProperties = new Properties();
		
		try {
			final FileInputStream fileInput = new FileInputStream(new File(oauthClientProperties.getPath()));
			oauthProperties.load(fileInput);
		} catch (final IOException e) {
			LOG.error(e.getMessage());
		}
		
		clientSecrets = new EnumMap<>(ServerEnvironment.class);
		oauthProperties.forEach((k, v) -> clientSecrets.put(ServerEnvironment.valueOf(k.toString()), v.toString()));
		serverTokenMap = new EnumMap<>(ServerEnvironment.class);
		serverTokenMap.put(ServerEnvironment.LOCAL_ENVIRONMENT, null);
		serverTokenMap.put(ServerEnvironment.DEV_ENVIRONMENT, null);
	}

	@Override
	public synchronized OAuth2AccessToken refreshAccessToken(final ServerEnvironment server) {
		LOG.info("Retrieving OAuth token");
		
		if (!clientSecrets.containsKey(server)) {
			return null;
		}
		
		final String url = serverService.getHybrisServer(server) + "/homedepotcacommercewebservices/oauth/token";
		final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam(CLIENT_ID, clientId)
				.queryParam(CLIENT_SECRET, clientSecrets.get(server))
				.queryParam(GRANT_TYPE, CLIENT_CREDENTIALS);
		
		final URI uri = builder.build().encode().toUri();

		try {
			final String tokenJson = webService.postJson(uri);
			if (!StringUtils.isEmpty(tokenJson)) {
				final Gson gson = new Gson();
				final OAuth2AccessToken token = gson.fromJson(tokenJson, HomedepotcaOAuthAccessToken.class);
				serverTokenMap.put(server, token);
				return token;
			}
		} catch (final Exception e) {
			LOG.error("Retrieving OAuth token failed due to " + e.getMessage());
		}
		
		return null;
	}

	@Override
	public synchronized OAuth2AccessToken getAccessToken(final ServerEnvironment server) {
		OAuth2AccessToken token = serverTokenMap.get(server);
		if (token == null) {
			token = refreshAccessToken(server);
		}

		return token;
	}
}
