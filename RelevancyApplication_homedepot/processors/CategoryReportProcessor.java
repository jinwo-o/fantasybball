package ca.homedepot.relevancy.processors;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.model.SolrReportParameters;
import ca.homedepot.relevancy.services.writer.PrintReportService;
import ca.homedepot.relevancy.tasks.ReportTask;
import ca.homedepot.relevancy.tasks.CategoryReportTask;
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
public class CategoryReportProcessor extends ReportProcessor
{
	private SolrReportParameters reportParameters;
	private ApplicationContext context;

	@Autowired
	public CategoryReportProcessor(final ReportProperties reportProperties, final PrintReportService printReportService,
			ApplicationContext context)
	{
		super(reportProperties, printReportService);
		this.context = context;
	}

	@Override
	protected ReportTask createTask(final List<IEntry> inputEntry)
	{
		final CategoryReportTask task = context.getBean(CategoryReportTask.class);
		task.setInputEntries(inputEntry);
		task.setEntries(entries);
		task.setParameters(reportParameters);
		return task;
	}

	@Override
	protected String getHeadline()
	{
		return "Search Term,Category Leaf,Score,#SKU Before Changes,#SKU After Changes, Language";
	}

	public void setReportParameters(SolrReportParameters reportParameters)
	{
		this.reportParameters = reportParameters;
	}
}