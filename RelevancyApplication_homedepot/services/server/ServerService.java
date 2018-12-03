package ca.homedepot.relevancy.services.server;

import ca.homedepot.relevancy.model.ServerEnvironment;

public interface ServerService {

	public String getHybrisServer(ServerEnvironment environment);
	public String getSolrServer(ServerEnvironment environment);
}
