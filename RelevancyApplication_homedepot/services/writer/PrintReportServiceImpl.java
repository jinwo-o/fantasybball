package ca.homedepot.relevancy.services.writer;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.model.entries.IEntry;
import ca.homedepot.relevancy.model.SolrTopSearchResult;
import ca.homedepot.relevancy.model.RelevancyReportParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Service
public class PrintReportServiceImpl implements PrintReportService
{
	private static final String EXT = ".csv";
	private static final LocalTime startTime = LocalTime.now();
	private static final Logger LOG = LoggerFactory.getLogger(PrintReportServiceImpl.class);

	@Override
	public void writeReport(final ReportProperties reportProperties, final List<IEntry> inputEntries,
			final Map<String, IEntry> entries, final String filename, final String headline)
	{
		try (final PrintWriter writer = new PrintWriter(reportProperties.getDirectoryFullPath() + filename + EXT,
				"UTF-8"))
		{
			writer.println(headline);
			for (final IEntry inputEntry : inputEntries)
			{
				final IEntry entry = entries.get(inputEntry.toString());
				if (entry != null)
				{
					entry.put("searchQuery", '\"' + entry.get("searchQuery") + '\"');
					entry.put("categoryLeaf", '\"' + entry.get("categoryLeaf") + '\"');
					writer.println(entry.toString());
				}
				else if (LOG.isDebugEnabled())
				{
					LOG.debug(String.format("%s was not processed", inputEntry));
				}
			}

			LOG.info("Report generated");
			final LocalTime endTime = LocalTime.now();

			if (LOG.isInfoEnabled())
			{
				LOG.info("Start time: " + startTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
				LOG.info("End time: " + endTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
			}
		}
		catch (final Throwable t)
		{
			LOG.error("There was an issue with report generation", t);
		}
	}

	@Override
	public void writeTopResult(final ReportProperties reportProperties, final RelevancyReportParameters userReport,
			final Map<String, SolrTopSearchResult> matchResults, final List<String> inputEntries,
			final String filename)
	{
		try
		{
			final PrintWriter writer = new PrintWriter(reportProperties.getDirectoryFullPath() + filename + EXT,
					"UTF-8");
			writer.println("Search Term,Not Matched SKU Code,Search Result");
			for (final String query : inputEntries)
			{
				String skuCode = null;
				String searchResult = null;
				final SolrTopSearchResult solrTopSearchResult = matchResults.get(query);
				if (solrTopSearchResult != null)
				{
					skuCode = solrTopSearchResult.getSkuCode();
					searchResult = solrTopSearchResult.getSearchResult();
				}
				writer.println(query + "," + skuCode + "," + searchResult);
			}

			writer.close();

			LOG.info("Report generated");
			final LocalTime endTime = LocalTime.now();
			LOG.info("Start time: " + startTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
			LOG.info("End time: " + endTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
		}
		catch (final Throwable t)
		{
			LOG.error("There was an issue with report generation", t);
		}
	}
}
