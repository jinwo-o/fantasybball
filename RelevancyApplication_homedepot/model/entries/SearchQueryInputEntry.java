package ca.homedepot.relevancy.model.entries;


public class SearchQueryInputEntry extends DefaultIEntry
{
    private String searchQuery;

    public SearchQueryInputEntry(String query)
    {
        this.searchQuery = query;
    }

    public String getSearchQuery()
    {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery)
    {
        this.searchQuery = searchQuery;
    }
    
	public String toString()
	{
		return String.format("%s", searchQuery);
	}
	
}