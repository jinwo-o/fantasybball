package ca.homedepot.relevancy.services.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.homedepot.relevancy.config.properties.HDCAServerProperties;
import ca.homedepot.relevancy.model.ServerEnvironment;
import ca.homedepot.relevancy.model.ServerOptions;

@Component
public class DefaultServerService implements ServerService {

	private HDCAServerProperties serverProperties;

	@Autowired
	public DefaultServerService(HDCAServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	@Override
	public String getHybrisServer(ServerEnvironment server) {
		return getMatchedServer(server, ServerOptions.HYBRIS);
	}

	@Override
	public String getSolrServer(ServerEnvironment server) {
		return getMatchedServer(server, ServerOptions.SOLR);
	}

	private String getMatchedServer(ServerEnvironment environment, ServerOptions server) {
		if (ServerOptions.HYBRIS == server) {
			return serverProperties.getHybrisServers().containsKey(environment)
					? serverProperties.getHybrisServers().get(environment)
					: null;
		} else {
			return serverProperties.getSolrServers().containsKey(environment)
					? serverProperties.getSolrServers().get(environment)
					: null;
		}
	}
}
