package ca.homedepot.relevancy.model;

import ca.homedepot.relevancy.config.properties.ReportProperties;

import java.io.Serializable;


/**
 * @author Weichen Zhou
 */
public class SolrReportParameters implements Serializable
{
	private String name;
	private ServerEnvironment serverA;
	private ServerEnvironment serverB;
	private ServerEnvironment[] environments;
	private TimePeriod timePeriod;
	private String storeId;
	private LanguageOption languageOption;

	public SolrReportParameters(final ReportProperties reportProperties)
	{
		this();
		this.environments = reportProperties.getEnvironments();
	}

	public SolrReportParameters()
	{
		timePeriod = TimePeriod.THREE_MONTHS;
		languageOption = LanguageOption.EN;
	}

	public ServerEnvironment[] getEnvironments()
	{
		return environments;
	}

	public void setEnvironments(final ServerEnvironment[] environments)
	{
		this.environments = environments;
	}

	public TimePeriod getTimePeriod()
	{
		return timePeriod;
	}

	public void setTimePeriod(final TimePeriod timePeriod)
	{
		this.timePeriod = timePeriod;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public ServerEnvironment getServerA()
	{
		return serverA;
	}

	public void setServerA(final ServerEnvironment serverA)
	{
		this.serverA = serverA;
	}

	public ServerEnvironment getServerB()
	{
		return serverB;
	}

	public void setServerB(ServerEnvironment serverB)
	{
		this.serverB = serverB;
	}

	public String getStoreId()
	{
		return storeId;
	}

	public void setStoreId(String storeId)
	{
		this.storeId = storeId;
	}

	public LanguageOption getLanguageOption()
	{
		return languageOption;
	}

	public void setLanguageOption(LanguageOption languageOption)
	{
		this.languageOption = languageOption;
	}
}
