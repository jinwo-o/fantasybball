package ca.homedepot.relevancy.model.entries;

import ca.homedepot.relevancy.constants.CategoryAnalyticsConstants;

public class CategoryAnalyticsOutputEntry extends DefaultIEntry
{
	
    @Override
    public String toString() {
    	return String.format("%s,%s,%s,%s,%s,%s", fieldValues.get(CategoryAnalyticsConstants.SEARCH_QUERY), fieldValues.get(CategoryAnalyticsConstants.CATEGORY_LEAF), fieldValues.get(CategoryAnalyticsConstants.SCORE),
    			fieldValues.get(CategoryAnalyticsConstants.COUNT_OF_ARTICLES_BEFORE), fieldValues.get(CategoryAnalyticsConstants.COUNT_OF_ARTICLES_AFTER), fieldValues.get(CategoryAnalyticsConstants.LANGUAGE));
    }
	
}