package ca.homedepot.relevancy.processors;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.model.SolrReportParameters;
import ca.homedepot.relevancy.services.writer.PrintReportService;
import ca.homedepot.relevancy.tasks.ReportTask;
import ca.homedepot.relevancy.tasks.SolrReportTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ca.homedepot.relevancy.model.entries.IEntry;

import java.util.List;


/**
 * @author Weichen Zhou
 */
@Component
@Scope("prototype")
public class SolrReportProcessor extends ReportProcessor
{
	private SolrReportParameters reportParameters;
	private ApplicationContext context;

	@Autowired
	public SolrReportProcessor(final ReportProperties reportProperties, final PrintReportService printReportService,
			ApplicationContext context)
	{
		super(reportProperties, printReportService);
		this.context = context;
	}

	@Override
	protected ReportTask createTask(final List<IEntry> searchTerms)
	{
		final SolrReportTask task = context.getBean(SolrReportTask.class);
		task.setInputEntries(searchTerms);
		task.setEvaluator(reportParameters.getTimePeriod().getEvaluator());
		task.setEntries(entries);
		task.setParameters(reportParameters);
		return task;
	}

	@Override
	protected String getHeadline()
	{
		return "Search Term,Relevancy A,Relevancy B";
	}

	public void setReportParameters(SolrReportParameters reportParameters)
	{
		this.reportParameters = reportParameters;
	}
}
