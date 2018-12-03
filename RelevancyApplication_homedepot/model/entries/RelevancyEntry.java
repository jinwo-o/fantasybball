package ca.homedepot.relevancy.model.entries;

public class RelevancyEntry extends DefaultIEntry
{
	private String query;
	private Double relevancy;
	private Integer occurrences;
	private Double engagement;
	private Double conversion;

	public String getQuery()
	{
		return query;
	}

	@Override
	public String toString()
	{
		return String.format("%s,%f,%d,%f,%f", query, relevancy, occurrences, engagement, conversion);
	}

	public void setQuery(final String query)
	{
		this.query = query;
	}

	public Double getRelevancy()
	{
		return relevancy;
	}

	public void setRelevancy(final Double relevancy)
	{
		this.relevancy = relevancy;
	}

	public Integer getOccurrences()
	{
		return occurrences;
	}

	public void setOccurrences(final Integer occurrences)
	{
		this.occurrences = occurrences;
	}

	public Double getEngagement()
	{
		return engagement;
	}

	public void setEngagement(final Double engagement)
	{
		this.engagement = engagement;
	}

	public Double getConversion()
	{
		return conversion;
	}

	public void setConversion(final Double conversion)
	{
		this.conversion = conversion;
	}
}
