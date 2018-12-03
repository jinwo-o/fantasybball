package ca.homedepot.relevancy.services.solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import ca.homedepot.relevancy.model.ServerEnvironment;

public interface SolrService {
	long querySolrGetCount(SolrQuery solrQuery, ServerEnvironment server);
	List<String> querySolrGetProductList(SolrQuery solrQuery, ServerEnvironment server);
}
