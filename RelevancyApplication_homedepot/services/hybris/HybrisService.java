package ca.homedepot.relevancy.services.hybris;

import ca.homedepot.relevancy.model.TopSearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import ca.homedepot.relevancy.model.RelevancyReportParameters;

public interface HybrisService {
	SolrQuery getSolrQuery(String query, RelevancyReportParameters userReport, OAuth2AccessToken accessToken);
	TopSearchResult getTopSearchResult(String query, RelevancyReportParameters userReport, OAuth2AccessToken accessToken);
}
