package ca.homedepot.relevancy.tasks;

import ca.homedepot.relevancy.converters.impl.SolrQueryConverter;
import ca.homedepot.relevancy.model.LanguageOption;
import ca.homedepot.relevancy.model.SolrReportParameters;
import ca.homedepot.relevancy.model.entries.IEntry;
import ca.homedepot.relevancy.model.entries.CategoryAnalyticsOutputEntry;
import ca.homedepot.relevancy.constants.CategoryAnalyticsConstants;
import ca.homedepot.relevancy.services.solr.SolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * @author Weichen Zhou
 */
@Component
@Scope("prototype")
public class CategoryReportTask extends ReportTask
{
	private SolrService solrService;
	private SolrQueryConverter solrQueryConverter;
	private SolrReportParameters parameters;

	@Autowired
	public CategoryReportTask(final SolrQueryConverter solrQueryConverter, final SolrService solrService)
	{
		this.solrQueryConverter = solrQueryConverter;
		this.solrService = solrService;
	}
	
	@Override
	protected IEntry calculate(final IEntry inputEntry)
	{
		final SolrQuery solrQuery = solrQueryConverter.convert(parameters);
		solrQuery.setQuery(inputEntry.get(CategoryAnalyticsConstants.SEARCH_QUERY));
		if(inputEntry.get(CategoryAnalyticsConstants.LANGUAGE).equals("fr")){
			parameters.setLanguageOption(LanguageOption.FR);
			solrQuery.setFilterQueries("categoryNames_fr_string_mv:" + inputEntry.get(CategoryAnalyticsConstants.CATEGORY_LEAF));
		}
		else {
			parameters.setLanguageOption(LanguageOption.EN);
			solrQuery.setFilterQueries("categoryNames_en_string_mv:" + inputEntry.get(CategoryAnalyticsConstants.CATEGORY_LEAF));
		}
		solrQuery.setRows(0);

		final CategoryAnalyticsOutputEntry outputEntry = new CategoryAnalyticsOutputEntry();
		outputEntry.put(CategoryAnalyticsConstants.SEARCH_QUERY, inputEntry.get(CategoryAnalyticsConstants.SEARCH_QUERY));
		outputEntry.put(CategoryAnalyticsConstants.CATEGORY_LEAF, inputEntry.get(CategoryAnalyticsConstants.CATEGORY_LEAF));
		outputEntry.put(CategoryAnalyticsConstants.SCORE, inputEntry.get(CategoryAnalyticsConstants.SCORE));
		outputEntry.put(CategoryAnalyticsConstants.LANGUAGE, inputEntry.get(CategoryAnalyticsConstants.LANGUAGE));
		solrQuery.setRequestHandler(
				parameters.getLanguageOption() == LanguageOption.FR ? "/productSearchPublishFr" : "/productSearchPublishEn");
		
		// ServerA is with SF
		long numFoundA = solrService.querySolrGetCount(solrQuery, parameters.getServerA());
		
		outputEntry.put(CategoryAnalyticsConstants.COUNT_OF_ARTICLES_BEFORE, String.valueOf(numFoundA));
		
		// ServerB is with SGF
		final long numFoundB = solrService.querySolrGetCount(solrQuery, parameters.getServerB());

		outputEntry.put(CategoryAnalyticsConstants.COUNT_OF_ARTICLES_AFTER, String.valueOf(numFoundB));

		return outputEntry;
	}

	public void setParameters(SolrReportParameters parameters)
	{
		this.parameters = parameters;
	}
}
