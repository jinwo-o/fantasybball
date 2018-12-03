package ca.homedepot.relevancy.tasks;

import ca.homedepot.relevancy.evaluator.Evaluator;
import ca.homedepot.relevancy.model.entries.IEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * @author Weichen Zhou
 */
public abstract class ReportTask implements Runnable
{
	private static final Logger LOG = LoggerFactory.getLogger(ReportTask.class);

	protected List<IEntry> inputEntries;
	protected Evaluator evaluator;
	protected Map<String, IEntry> entries;

	protected abstract IEntry calculate(IEntry inputEntry);

	@Override
	public void run()
	{
		for (final IEntry entry : inputEntries)
		{
			if (Thread.currentThread().isInterrupted())
			{
				break;
			}

			LOG.info("Calculating entry for " + entry);
			final IEntry calculationEntry = calculate(entry);
			entries.put(entry.toString(), calculationEntry);
		}
	}

	public void setInputEntries(final List<IEntry> inputEntries)
	{
		this.inputEntries = inputEntries;
	}

	public void setEvaluator(final Evaluator evaluator)
	{
		this.evaluator = evaluator;
	}

	public void setEntries(final Map<String, IEntry> entries)
	{
		this.entries = entries;
	}
}
