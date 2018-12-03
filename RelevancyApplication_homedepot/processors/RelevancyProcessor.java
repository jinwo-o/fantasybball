package ca.homedepot.relevancy.processors;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.evaluator.Evaluator;
import ca.homedepot.relevancy.model.entries.IEntry;
import ca.homedepot.relevancy.model.RelevancyReportParameters;
import ca.homedepot.relevancy.services.writer.PrintReportService;
import ca.homedepot.relevancy.tasks.ReportTask;
import ca.homedepot.relevancy.tasks.RelevancyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * The RelevancyProcessor uses the evaluator model to generate the relevancy values for the given list of search terms.
 * This class makes requests to Hybris to retrieve the Solr converters, which it then sends to Solr to retrieve the search results.
 * The processors will distribute the search terms list across a specified number of threads.
 * These threads will calculate relevancy in parallel.
 * The results are collected into the entries variable.
 */
@Component
@Scope("prototype")
public class RelevancyProcessor extends ReportProcessor
{
	private RelevancyReportParameters reportParameters;
	private ApplicationContext context;

	@Autowired
	public RelevancyProcessor(final ReportProperties reportProperties, final PrintReportService printReportService, final ApplicationContext context)
	{
		super(reportProperties, printReportService);
		this.context = context;
	}

	@Override
	protected ReportTask createTask(List<IEntry> inputEntries)
	{
		final Evaluator evaluator = reportParameters.getTimePeriod().getEvaluator();
		final RelevancyTask task = context.getBean(RelevancyTask.class);
		task.setInputEntries(inputEntries);
		task.setEvaluator(evaluator);
		task.setEntries(entries);
		task.setParameters(reportParameters);
		return task;
	}

	@Override
	protected String getHeadline()
	{
		return "Search Term,Relevancy,Occurrences,Engagement,Conversion";
	}

	public void setReportParameters(RelevancyReportParameters reportParameters)
	{
		this.reportParameters = reportParameters;
	}
}
