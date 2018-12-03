package ca.homedepot.relevancy.util;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.evaluator.Evaluator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public final class SearchTermUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(SearchTermUtil.class);

	private SearchTermUtil()
	{
	}

	public static String[] parseSearchTerms(final MultipartFile file)
	{
		try
		{
			return new String(file.getBytes()).split(",");
		}
		catch (final IOException e)
		{
			LOG.error("Failed to parse user search terms", e);
			return null;
		}
	}

	public static List<List<String>> partitionThread(final ReportProperties reportProperties, final List<String> searchTerms)
	{
		final List<List<String>> partition = new ArrayList<>(reportProperties.getRelevancyProcessorThreadCount());

		for (int i = 0; i < reportProperties.getRelevancyProcessorThreadCount(); i++)
		{
			partition.add(new ArrayList<>());
		}

		for (int i = 0; i < searchTerms.size(); i++)
		{
			partition.get(i % reportProperties.getRelevancyProcessorThreadCount()).add(searchTerms.get(i));
		}
		return partition;
	}

	public static List<String> parseSearchTerms(final String[] searchTerms, final Evaluator... evaluator)
	{
		final List<String> searchQueue = new ArrayList<>(searchTerms.length);
		final Set<String> searchSet = new HashSet<>();

		if (searchTerms == null || searchTerms.length == 0)
		{
			return searchQueue;
		}

		for (int i = 0; i < searchTerms.length; ++i)
		{
			final String str = searchTerms[i];

			if (!StringUtils.isEmpty(str))
			{
				final String trimmed = str.trim().toLowerCase();

				// There is no point in making the HTTP calls if the model does not have the search term
				if (evaluator.length == 0)
				{
					searchSet.add(trimmed);
					searchTerms[i] = trimmed;
				}
				else if (!evaluator[0].getRelevancyModel().isEmptyTerm(trimmed))
				{
					searchSet.add(trimmed);
					searchTerms[i] = trimmed;
				}
			}
		}

		searchSet.forEach(str -> searchQueue.add(str));
		return searchQueue;
	}
}
