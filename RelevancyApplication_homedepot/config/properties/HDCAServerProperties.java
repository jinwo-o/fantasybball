package ca.homedepot.relevancy.config.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import ca.homedepot.relevancy.model.ServerEnvironment;

@Component
@ConfigurationProperties("hdca.server")
public class HDCAServerProperties {

	private String hybrisLocal;
	private String hybrisDev;
	private String solrLocal;
	private String solrDev;

	public String getHybrisLocal() {
		return hybrisLocal;
	}

	public void setHybrisLocal(String hybrisLocal) {
		this.hybrisLocal = hybrisLocal;
	}

	public String getHybrisDev() {
		return hybrisDev;
	}

	public void setHybrisDev(String hybrisDev) {
		this.hybrisDev = hybrisDev;
	}

	public String getSolrLocal() {
		return solrLocal;
	}

	public void setSolrLocal(String solrLocal) {
		this.solrLocal = solrLocal;
	}

	public String getSolrDev() {
		return solrDev;
	}

	public void setSolrDev(String solrDev) {
		this.solrDev = solrDev;
	}

	public Map<ServerEnvironment, String> getHybrisServers() {
		return new ImmutableMap.Builder<ServerEnvironment, String>()
				.put(ServerEnvironment.LOCAL_ENVIRONMENT, getHybrisLocal())
				.put(ServerEnvironment.DEV_ENVIRONMENT, getHybrisDev()).build();
	}

	public Map<ServerEnvironment, String> getSolrServers() {
		return new ImmutableMap.Builder<ServerEnvironment, String>()
				.put(ServerEnvironment.LOCAL_ENVIRONMENT, getSolrLocal())
				.put(ServerEnvironment.DEV_ENVIRONMENT, getSolrDev()).build();
	}
}
