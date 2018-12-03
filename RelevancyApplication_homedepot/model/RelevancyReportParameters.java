package ca.homedepot.relevancy.model;

import ca.homedepot.relevancy.config.properties.ReportProperties;


public class RelevancyReportParameters
{
	public static final String NLP_PARAM_NAME = "nlp";
	public static final String BOOST_PARAM_NAME = "boost";
	public static final String ANALYTICS_PARAM_NAME = "analytics";
	public static final String STORE_PARAM_NAME = "store";
	public static final String LOCALIZED_POS_PARAM_NAME = "localizedPOS";
	public static final String LANG_PARAM_NAME = "lang";
	public static final String CATALOG_VERSION_PARAM_NAME = "catalogVersion";

	private String name;
	private String catalogVersion;
	private String localizedPOS;
	private boolean nlpEnable;
	private boolean boostBuryEnable;
	private boolean analyticsEnable;
	private String storeNumber;
	private ServerEnvironment hybrisServer;
	private ServerEnvironment solrServer;
	private SearchOption searchOption;
	private LanguageOption languageOption;
	private TimePeriod timePeriod;
	private final ServerEnvironment[] environments;

	public RelevancyReportParameters(final ReportProperties reportProperties)
	{
		this.timePeriod = TimePeriod.THREE_MONTHS;
		this.environments = reportProperties.getEnvironments();
	}

	public RelevancyReportParameters()
	{
		this.environments = null;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public boolean isNlpEnable()
	{
		return nlpEnable;
	}

	public void setNlpEnable(final boolean nlpEnable)
	{
		this.nlpEnable = nlpEnable;
	}

	public boolean isBoostBuryEnable()
	{
		return boostBuryEnable;
	}

	public void setBoostBuryEnable(final boolean boostBuryEnable)
	{
		this.boostBuryEnable = boostBuryEnable;
	}

	public boolean isAnalyticsEnable()
	{
		return analyticsEnable;
	}

	public void setAnalyticsEnable(final boolean analyticsEnable)
	{
		this.analyticsEnable = analyticsEnable;
	}

	public String getStoreNumber()
	{
		return storeNumber;
	}

	public void setStoreNumber(final String storeNumber)
	{
		this.storeNumber = storeNumber;
	}

	public ServerEnvironment getHybrisServer()
	{
		return hybrisServer;
	}

	public void setHybrisServer(final ServerEnvironment hybrisServer)
	{
		this.hybrisServer = hybrisServer;
	}

	public ServerEnvironment getSolrServer()
	{
		return solrServer;
	}

	public void setSolrServer(final ServerEnvironment solrServer)
	{
		this.solrServer = solrServer;
	}

	public SearchOption getSearchOption()
	{
		return searchOption;
	}

	public void setSearchOption(final SearchOption searchOption)
	{
		this.searchOption = searchOption;
	}

	public LanguageOption getLanguageOption()
	{
		return languageOption;
	}

	public void setLanguageOption(LanguageOption languageOption)
	{
		this.languageOption = languageOption;
	}

	public void setTimePeriod(final TimePeriod timePeriod)
	{
		this.timePeriod = timePeriod;
	}

	public TimePeriod getTimePeriod()
	{
		return timePeriod;
	}

	public ServerEnvironment[] getEnvironments()
	{
		return environments;
	}

	public String getCatalogVersion()
	{
		return catalogVersion;
	}

	public void setCatalogVersion(String catalogVersion)
	{
		this.catalogVersion = catalogVersion;
	}

	public String getLocalizedPOS()
	{
		return localizedPOS;
	}

	public void setLocalizedPOS(String localizedPOS)
	{
		this.localizedPOS = localizedPOS;
	}
}
