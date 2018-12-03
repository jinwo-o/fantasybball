package ca.homedepot.relevancy.services.solr.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ca.homedepot.relevancy.services.solr.SolrService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.homedepot.relevancy.model.ServerEnvironment;
import ca.homedepot.relevancy.services.server.ServerService;


@Component
public class DefaultSolrService implements SolrService
{

	private static final Logger LOG = LoggerFactory.getLogger(DefaultSolrService.class);

	private static final String CODE_STRING = "code_string";

	private final ServerService serverService;

	@Autowired
	public DefaultSolrService(final ServerService serverService)
	{
		this.serverService = serverService;
	}

	@Override
	public long querySolrGetCount(final SolrQuery solrQuery, final ServerEnvironment server) 
	{
		QueryResponse response = querySolr(solrQuery, server);
		final SolrDocumentList docs = response.getResults();
		return docs.getNumFound();
	}
	
	@Override
	public List<String> querySolrGetProductList(final SolrQuery solrQuery, final ServerEnvironment server){
		
		QueryResponse response = querySolr(solrQuery, server);
		final SolrDocumentList docs = response.getResults();
		return docs.stream().map(doc -> doc.getFieldValue(CODE_STRING)).filter(Objects::nonNull).map(Object::toString)
				.collect(Collectors.toList());
	}

	private QueryResponse querySolr(final SolrQuery solrQuery, final ServerEnvironment server)
	{

		final String URL = serverService.getSolrServer(server) + "/master_homedepotca_Product_default";
		final SolrClient client = (new HttpSolrClient.Builder(URL)).build();

		QueryResponse response = null;
		try
		{
			response = client.query(solrQuery);
			client.close();
		}
		catch (SolrServerException | IOException e)
		{
			LOG.error("There was an error querying Solr", e);
		}

		return response;
	}
	
}
