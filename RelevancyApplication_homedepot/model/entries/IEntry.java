package ca.homedepot.relevancy.model.entries;

/**
 * @author Weichen Zhou
 */
public interface IEntry
{
	public void put(String fieldName, String fieldValue);
	
	public String get(String fieldName);
	
}
