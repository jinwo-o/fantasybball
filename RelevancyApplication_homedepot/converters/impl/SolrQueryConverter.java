package ca.homedepot.relevancy.converters.impl;

import ca.homedepot.relevancy.model.LanguageOption;
import ca.homedepot.relevancy.model.SolrReportParameters;
import ca.homedepot.relevancy.converters.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Component;


/**
 * @author Weichen Zhou
 */
@Component
public class SolrQueryConverter implements Converter<SolrReportParameters, SolrQuery>
{
	@Override
	public SolrQuery convert(final SolrReportParameters reportParameters)
	{
		final SolrQuery solrQuery = new SolrQuery();

		final String requestHandler =
				reportParameters.getLanguageOption() == LanguageOption.FR ? "/productSearchPublishFr" : "/productSearchPublishEn";
		solrQuery.setRequestHandler(requestHandler);

		if (StringUtils.isNotBlank(reportParameters.getStoreId()))
		{
			final String filterQuery = String.format("posIds_string_mv:%s", reportParameters.getStoreId());
			solrQuery.addFilterQuery(filterQuery);
		}

		solrQuery.addField("code_string");

		return solrQuery;
	}
}
