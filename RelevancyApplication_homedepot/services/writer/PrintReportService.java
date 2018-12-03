package ca.homedepot.relevancy.services.writer;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.model.entries.IEntry;
import ca.homedepot.relevancy.model.SolrTopSearchResult;
import ca.homedepot.relevancy.model.RelevancyReportParameters;

import java.util.List;
import java.util.Map;


public interface PrintReportService
{
	void writeReport(ReportProperties reportProperties, List<IEntry> searchTerms, Map<String, IEntry> entries, String filename,
			String headline);

	void writeTopResult(ReportProperties reportProperties, RelevancyReportParameters userReport,
			Map<String, SolrTopSearchResult> matchResults, List<String> searchTerms,
			String filename);
}
