package ca.homedepot.relevancy.util;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.evaluator.Evaluator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import ca.homedepot.relevancy.model.entries.DefaultIEntry;
import ca.homedepot.relevancy.model.entries.IEntry;
import ca.homedepot.relevancy.model.entries.SearchQueryInputEntry;
import ca.homedepot.relevancy.constants.CategoryAnalyticsConstants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.opencsv.CSVReader;


public final class DataInputUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(SearchTermUtil.class);

	private DataInputUtil()
	{
	}

	public static IEntry[] parseInputEntries(final MultipartFile file)
	{
		try
		{
			final String[] searchTerms = new String(file.getBytes()).split(",");
			final IEntry[] inputEntries = new IEntry[searchTerms.length];
			for (int i = 0; i < searchTerms.length; i++)
			{
				inputEntries[i] = new SearchQueryInputEntry(searchTerms[i]);
			}
			return inputEntries;
		}
		catch (final IOException e)
		{
			LOG.error("Failed to parse user search terms", e);
			return null;
		}
	}

	public static List<List<IEntry>> partitionThread(final ReportProperties reportProperties, final List<IEntry> searchTerms)
	{
		final List<List<IEntry>> partition = new ArrayList<>(reportProperties.getRelevancyProcessorThreadCount());

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

	public static List<IEntry> parseInputEntries(final IEntry[] searchTerms, final Evaluator... evaluator)
	{
		final List<IEntry> searchQueue = new ArrayList<>(searchTerms.length);
		final Set<IEntry> searchSet = new HashSet<>();

		if (searchTerms == null || searchTerms.length == 0)
		{
			return searchQueue;
		}

		for (int i = 0; i < searchTerms.length; ++i)
		{
			final String searchQuery = ((SearchQueryInputEntry)searchTerms[i]).getSearchQuery();

			if (StringUtils.isNotEmpty(searchQuery))
			{
				final String trimmed = searchQuery.trim().toLowerCase();

				// There is no point in making the HTTP calls if the model does not have the search term
				if (evaluator.length == 0)
				{
					searchSet.add(new SearchQueryInputEntry(trimmed));
					searchTerms[i] = new SearchQueryInputEntry(trimmed);
				}
				else if (!evaluator[0].getRelevancyModel().isEmptyTerm(trimmed))
				{
					searchSet.add(new SearchQueryInputEntry(trimmed));
					searchTerms[i] = new SearchQueryInputEntry(trimmed);
				}
			}
		}

		searchSet.forEach(str -> searchQueue.add(str));
		return searchQueue;
	}
	
	public static List<IEntry> parseCSV(final MultipartFile file, HttpServletRequest request) throws IOException
	{
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator);
		File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
        try (InputStream is = file.getInputStream();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
            int i;
            //write file to server
            while ((i = is.read()) != -1) {
                stream.write(i);
            }
            stream.flush();
        }
        String[] line;
        List<IEntry> listIEntry = new ArrayList<IEntry>();
        FileReader fileReader = new FileReader(serverFile);
        CSVReader reader = new CSVReader(fileReader, ',', '\"', '\'', 1); {
            while ((line = reader.readNext()) != null) {
                IEntry inputEntry = new DefaultIEntry();
                inputEntry.put(CategoryAnalyticsConstants.SEARCH_QUERY, line[0]);
                inputEntry.put(CategoryAnalyticsConstants.CATEGORY_LEAF, line[1]);
                inputEntry.put(CategoryAnalyticsConstants.SCORE, line[2]);
                inputEntry.put(CategoryAnalyticsConstants.LANGUAGE, line[3]);
                listIEntry.add(inputEntry);
            }
        }
        return listIEntry;
	}
	
}
