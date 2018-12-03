package ca.homedepot.relevancy.tasks;

import ca.homedepot.relevancy.converters.impl.SolrQueryConverter;
import ca.homedepot.relevancy.model.LanguageOption;
import ca.homedepot.relevancy.model.SolrReportParameters;
import ca.homedepot.relevancy.model.entries.IEntry;
import ca.homedepot.relevancy.model.entries.SolrReportEntry;
import ca.homedepot.relevancy.services.solr.SolrService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Weichen Zhou
 */
@Component
@Scope("prototype")
public class SolrReportTask extends ReportTask
{
	private SolrService solrService;
	private SolrQueryConverter solrQueryConverter;
	private SolrReportParameters parameters;

	@Autowired
	public SolrReportTask(final SolrQueryConverter solrQueryConverter, final SolrService solrService)
	{
		this.solrQueryConverter = solrQueryConverter;
		this.solrService = solrService;
	}

	@Override
	protected IEntry calculate(final IEntry searchQuery)
	{
		final SolrQuery solrQuery = solrQueryConverter.convert(parameters);
		solrQuery.setQuery(searchQuery.toString());
		solrQuery.setParam("qq", searchQuery.toString());

		final SolrReportEntry entry = new SolrReportEntry();
		entry.setQuery(searchQuery.toString());
		entry.setRelevancyA(0.0);
		entry.setRelevancyB(0.0);

		final List<String> searchResultsA = solrService.querySolrGetProductList(solrQuery, parameters.getServerA());
		if (CollectionUtils.isNotEmpty(searchResultsA))
		{
			// Calculate the relevancy for the search term using the list of skus returned by Solr.
			final double relevancyA = evaluator.evaluate(searchQuery.toString(), searchResultsA.toArray(new String[searchResultsA.size()]));
			entry.setRelevancyA(relevancyA);
		}

		solrQuery.setRequestHandler(
				parameters.getLanguageOption() == LanguageOption.FR ? "/testProductSearchPublishFr" : "/testProductSearchPublishEn");
		final List<String> searchResultsB = solrService.querySolrGetProductList(solrQuery, parameters.getServerB());
		if (CollectionUtils.isNotEmpty(searchResultsB))
		{
			final double relevancyB = evaluator.evaluate(searchQuery.toString(), searchResultsB.toArray(new String[searchResultsB.size()]));
			entry.setRelevancyB(relevancyB);
		}

		return entry;
	}

	public void setParameters(SolrReportParameters parameters)
	{
		this.parameters = parameters;
	}
}
