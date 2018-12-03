package ca.homedepot.relevancy.tasks;

/**
 * @author Weichen Zhou
 */

import ca.homedepot.relevancy.model.RelevancyReportParameters;
import ca.homedepot.relevancy.model.entries.IEntry;
import ca.homedepot.relevancy.model.entries.RelevancyEntry;
import ca.homedepot.relevancy.services.hybris.HybrisService;
import ca.homedepot.relevancy.services.oauth.OAuthService;
import ca.homedepot.relevancy.services.solr.SolrService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * The RelevancyTask will iterate through a given list of searchTerms and create
 * RelevancyEntry models.
 */
@Component
@Scope("prototype")
public class RelevancyTask extends ReportTask
{
	private HybrisService hybrisService;
	private SolrService solrService;
	private OAuthService oauthService;
	private RelevancyReportParameters parameters;

	@Autowired
	public RelevancyTask(final HybrisService hybrisService, final SolrService solrService, final OAuthService oauthService)
	{
		this.hybrisService = hybrisService;
		this.solrService = solrService;
		this.oauthService = oauthService;
	}

	private double calculateRelevancyScore(final String searchQuery)
	{
		// Retrieve the access token once in order to make requests to Hybris.
		final OAuth2AccessToken accessToken = oauthService.getAccessToken(parameters.getHybrisServer());

		// Retrieve the SolrQuery from hybris.
		final SolrQuery solrQuery = hybrisService.getSolrQuery(searchQuery, parameters,
				accessToken);

		if (solrQuery == null)
		{
			return 0.0;
		}

		// Query solr and retrieve a list of skus.
		final List<String> searchResults = solrService.querySolrGetProductList(solrQuery, parameters.getSolrServer());
		if (CollectionUtils.isNotEmpty(searchResults))
		{
			// Calculate the relevancy for the search term using the list of skus returned
			// by Solr.
			return evaluator.evaluate(searchQuery, searchResults.toArray(new String[searchResults.size()]));
		}

		return 0.0;
	}

	@Override
	protected IEntry calculate(final IEntry inputEntry)
	{
		final RelevancyEntry relevancyEntry = new RelevancyEntry();
		final double relevancy = calculateRelevancyScore(inputEntry.toString());
		final int occurrences = evaluator.getRelevancyModel().getHitsForQuery(inputEntry.toString());
		final double engagement = evaluator.getRelevancyModel().getEngagement(inputEntry.toString());
		final double conversion = evaluator.getRelevancyModel().getConversion(inputEntry.toString());

		relevancyEntry.setQuery(inputEntry.toString());
		relevancyEntry.setRelevancy(relevancy);
		relevancyEntry.setOccurrences(occurrences);
		relevancyEntry.setEngagement(engagement);
		relevancyEntry.setConversion(conversion);

		return relevancyEntry;
	}

	public void setParameters(final RelevancyReportParameters parameters)
	{
		this.parameters = parameters;
	}
	
}
