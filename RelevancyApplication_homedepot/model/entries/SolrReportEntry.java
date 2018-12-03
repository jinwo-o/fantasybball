package ca.homedepot.relevancy.model.entries;
import ca.homedepot.relevancy.model.entries.DefaultIEntry;

/**
 * @author Weichen Zhou
 */
public class SolrReportEntry extends DefaultIEntry
{
	private String query;
	private double relevancyA;
	private double relevancyB;

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}

	public double getRelevancyA()
	{
		return relevancyA;
	}

	public void setRelevancyA(double relevancyA)
	{
		this.relevancyA = relevancyA;
	}

	public double getRelevancyB()
	{
		return relevancyB;
	}

	public void setRelevancyB(double relevancyB)
	{
		this.relevancyB = relevancyB;
	}

	@Override
	public String toString()
	{
		return String.format("%s,%f,%f", query, relevancyA, relevancyB);
	}
}
