package ca.homedepot.relevancy.processors;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.model.entries.IEntry;
import ca.homedepot.relevancy.services.writer.PrintReportService;
import ca.homedepot.relevancy.tasks.ReportTask;
import ca.homedepot.relevancy.util.DataInputUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class ReportProcessor implements Runnable
{
	private static final Logger LOG = LoggerFactory.getLogger(ReportProcessor.class);

	// Required dependencies.
	private final ReportProperties reportProperties;

	// ExecutorService that will manage the relevancy tasks.
	private final ExecutorService executor;

	private final PrintReportService printReportService;

	// Map containing the searchTerm and the corresponding relevancy result.
	protected final Map<String, IEntry> entries = new ConcurrentHashMap<>();

	private String filename;
	private List<IEntry> inputEntries;

	public ReportProcessor(final ReportProperties reportProperties, final PrintReportService printReportService)
	{
		this.reportProperties = reportProperties;
		this.printReportService = printReportService;
		this.executor = Executors.newFixedThreadPool(reportProperties.getRelevancyProcessorThreadCount());
	}

	/**
	 * Activate tasks for the given parameters.
	 * This method will distribute the search terms and start executing tasks to retrieve relevancy results.
	 */
	private void activateRelevancyTask()
	{
		// Distribute the list of search terms evenly into sub lists.
		final List<List<IEntry>> partition = DataInputUtil.partitionThread(reportProperties, inputEntries);

		// Each of the sublists will be passed to a relevancy task.
		for (int threadIndex = 0; threadIndex < reportProperties.getRelevancyProcessorThreadCount(); threadIndex++)
		{
			final ReportTask relevancyTask = createTask(partition.get(threadIndex));
			executor.submit(relevancyTask);
		}
	}

	private void calculateEntries()
	{
		activateRelevancyTask();

		while (!Thread.currentThread().isInterrupted() && entries.size() < inputEntries.size())
		{
		}

		deactivateRelevancyTasks();

		if (LOG.isDebugEnabled())
		{
			LOG.info("Number of search terms: " + inputEntries.size());
			LOG.info("Number of entries: " + entries.size());
		}
	}

	@Override
	public void run()
	{
		LOG.info("Begin generating report");
		calculateEntries();
		printReportService.writeReport(reportProperties, inputEntries, entries, filename, getHeadline());
	}

	public int getStatus()
	{
		if (CollectionUtils.isNotEmpty(inputEntries))
		{
			return entries.size() * 100 / inputEntries.size();
		}
		return 100;
	}

	protected abstract ReportTask createTask(List<IEntry> searchTerms);
	protected abstract String getHeadline();

	private void deactivateRelevancyTasks()
	{
		executor.shutdownNow();
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setInputEntries(List<IEntry> inputEntries)
	{
		this.inputEntries = inputEntries;
	}
}
